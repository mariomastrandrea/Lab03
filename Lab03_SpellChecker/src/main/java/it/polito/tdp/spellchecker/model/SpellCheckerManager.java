package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.checkstrategies.SpellCheckingStrategy;

public class SpellCheckerManager
{
	private String languageSelected;
	private Map<String, Collection<String>> dictionariesByName;
	private DictionaryType dictionaryType;
	private SpellCheckingStrategy checkingStrategy;
	private Set<String> wrongWords;
	
	
	public SpellCheckerManager(DictionaryType dictionaryType, SpellCheckingStrategy checkingStrategy) 
	{
		this.languageSelected = null;
		this.dictionariesByName = new HashMap<>();
		this.dictionaryType = dictionaryType;
		this.wrongWords = new HashSet<>();
		this.checkingStrategy = checkingStrategy;
	}
	
	public enum DictionaryType
	{
		ArrayList, LinkedList, HashSet;
	}
	
	public String getLanguage() { return this.languageSelected; }

	public void setDictionaryLanguage(String newLanguageSelected) throws FileNotFoundException, IOException 
	{
		if(newLanguageSelected.equals(this.languageSelected))
			return;		//same language selected
		
		if(this.dictionariesByName.containsKey(newLanguageSelected))
		{
			this.languageSelected = newLanguageSelected;
			return;
		}
		else //load new language...
		{
			String filePath = String.format("src/main/resources/%s.txt", newLanguageSelected);
			try(BufferedReader buffer = new BufferedReader(new FileReader(filePath)))
			{
				Collection<String> newEmptyDictionary = this.createNewDictionary(this.dictionaryType);
				
				String line;	
				while( (line = buffer.readLine()) != null)
					newEmptyDictionary.add(line);
				
				this.dictionariesByName.put(newLanguageSelected, newEmptyDictionary);
			}
			catch (FileNotFoundException fnfe) 
			{ 
				System.err.println(String.format("There is no \"%s\" dictionary in path %s !", 
													newLanguageSelected, filePath));
				String errorMessage = String.format("There is no \"%s.txt\" dictionary!", newLanguageSelected);
				throw new FileNotFoundException(errorMessage); 
			}
			catch (IOException ioe)	{ throw ioe; }
			
			this.languageSelected = newLanguageSelected;
		}
	}
	
	public Set<String> checkWrongWords(String inputText)
	{
		this.wrongWords.clear();
		Collection<String> dictionary = this.dictionariesByName.get(this.languageSelected);
		StringTokenizer tokenizer = new StringTokenizer(inputText);
		
		String word;
		while(tokenizer.hasMoreTokens())
		{
			word = tokenizer.nextToken();
			
			if(!isCorrect(word, dictionary))
				this.wrongWords.add(word);
		}
		return this.wrongWords;
	}
	
	private boolean isCorrect(String word, Collection<String> dictionary)
	{
		return this.checkingStrategy.isCorrect(word, dictionary);
	}

	private Collection<String> createNewDictionary(DictionaryType dictionaryType)
	{
		switch(this.dictionaryType)
		{
			case ArrayList:
				return new ArrayList<String>();
				
			case LinkedList:
				return new LinkedList<String>();
				
			case HashSet:
				return new HashSet<String>();
				
			default:
				return null;
		}
	}
	
}
