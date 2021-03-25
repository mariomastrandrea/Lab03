package it.polito.tdp.spellchecker.checkstrategies;

import java.util.Collection;
import java.util.List;

public class LinearChecking implements SpellCheckingStrategy
{
	@Override
	public boolean isCorrect(String word, Collection<String> dictionary)
	{
		if(dictionary == null || dictionary.isEmpty())
			return false;
		
		else if(!(dictionary instanceof List))
			return dictionary.contains(word);
		
		else 
		{
			List<String> listDictionary = (List<String>)dictionary;
			for(String s : listDictionary)
				if(s.equals(word))
					return true;
			
			return false;
		}
	}
}
