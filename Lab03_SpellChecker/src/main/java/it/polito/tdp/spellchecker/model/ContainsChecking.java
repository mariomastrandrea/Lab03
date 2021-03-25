package it.polito.tdp.spellchecker.model;

import java.util.Collection;

public class ContainsChecking implements SpellCheckingStrategy
{
	@Override
	public boolean isCorrect(String word, Collection<String> dictionary)
	{
		if(dictionary == null || dictionary.isEmpty())
			return false;
		else return dictionary.contains(word);
	}
}
