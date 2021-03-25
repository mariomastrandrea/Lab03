package it.polito.tdp.spellchecker.model;

import java.util.Collection;
import java.util.List;

public class DichotomicChecking implements SpellCheckingStrategy
{
	@Override
	public boolean isCorrect(String word, Collection<String> dictionary)
	{
		//TODO: control method
		
		if(dictionary == null || dictionary.isEmpty())
			return false;
		
		else if(!(dictionary instanceof List))
			return dictionary.contains(word);
		
		else 
		{
			List<String> listDictionary = (List<String>)dictionary;
			int lowerIndex = 0;
			int upperIndex = listDictionary.size()-1;
			
			do
			{
				int middleIndex = (upperIndex + lowerIndex)/2;
				String middleWord = listDictionary.get(middleIndex);
				int compare = word.compareTo(middleWord);
				
				if(compare == 0)
					return true; 
				
				else if(compare < 0)
					upperIndex = middleIndex-1;
				
				else //compare > 0
					lowerIndex = middleIndex+1;
			}
			while(lowerIndex <= upperIndex);
			
			return false;
		}
	}
}
