package it.polito.tdp.spellchecker.checkstrategies;

import java.util.Collection;

public interface SpellCheckingStrategy
{
	boolean isCorrect(String word, Collection<String> dictionary);
}
