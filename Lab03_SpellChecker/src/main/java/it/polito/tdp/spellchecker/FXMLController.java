package it.polito.tdp.spellchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.SpellCheckerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;


public class FXMLController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label computationTimeLabel;

    @FXML
    private ComboBox<String> languagesComboBox;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private Button spellCheckButton;

    @FXML
    private TextArea wrongWordsArea;

    @FXML
    private Label errorsLabel;

    @FXML
    private Button clearTextButton;

    private SpellCheckerManager model;
    
    
    public void setModel(SpellCheckerManager newModel, Collection<String> languages)
    {
    	this.model = newModel;
    	this.languagesComboBox.getItems().addAll(languages);
    }
    
    @FXML
    void handleClearText(ActionEvent event) 
    {
    	this.inputTextArea.clear();
    	this.wrongWordsArea.clear();
    	this.wrongWordsArea.setDisable(true);
    	this.clearTextButton.setDisable(true);
    	this.spellCheckButton.setDisable(true);
    	this.computationTimeLabel.setVisible(false);
    	this.errorsLabel.setVisible(false);
    }
    
    @FXML
    void handleOnKeyTyped(KeyEvent event) 
    {
    	if(this.inputTextArea.getText().isBlank())
    	{
    		if(this.wrongWordsArea.getText().isEmpty())
    		{
    			this.wrongWordsArea.setDisable(true);
            	this.clearTextButton.setDisable(true);
    		}
        	this.spellCheckButton.setDisable(true);
    	}
    	else 
    	{
    		this.wrongWordsArea.setDisable(false);
        	this.clearTextButton.setDisable(false);
        	this.spellCheckButton.setDisable(false);
		}
    }
    
    @FXML
    void handleSelectLanguage(ActionEvent event) 
    {
    	String languageSelected = this.languagesComboBox.getValue();
    	String errorMessage;
    	
    	try
		{
			this.model.setDictionaryLanguage(languageSelected); //set and load the dictionary 
		}
		catch(FileNotFoundException fnfe)	//dictionary file does not exists
		{
			errorMessage = fnfe.getMessage();
			this.errorsLabel.setText(errorMessage);
			this.errorsLabel.setVisible(true);
			return;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			errorMessage = "An I/O error occured";
			System.err.println(errorMessage);
			this.errorsLabel.setText(errorMessage);
			this.errorsLabel.setVisible(true);
			return;
		}
    	
    	this.inputTextArea.setDisable(false);
    }

    @FXML
    void handleSpellCheck(ActionEvent event) 
    {
    	this.wrongWordsArea.setText("");
    	
    	if(!isLanguageSelected())
    	{
    		this.errorsLabel.setText("Select a language!");
    		this.errorsLabel.setVisible(true);
    		return;
    	}
    	
    	String inputText = this.inputTextArea.getText().toLowerCase();  	
    	inputText = inputText.replaceAll("[.\\?,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", " ");
    	
    	if(inputText.isBlank())
    	{
    		this.errorsLabel.setText("Insert almost one word!");
    		this.errorsLabel.setVisible(true);
    		return;
    	}
    	
    	long startInstant = System.nanoTime();
    	Collection<String> wrongWords = this.model.checkWrongWords(inputText);
    	long endInstant = System.nanoTime();
    	
    	this.showTimeInMilliseconds(endInstant - startInstant);
    	
    	if(wrongWords.isEmpty())
    	{
    		this.wrongWordsArea.setText("All words are spelled correctly! :)");
    		this.errorsLabel.setVisible(false);
    	}
    	else 
    	{
			this.showWrongWords(wrongWords);
	    	this.showNumberErrors(wrongWords.size());
    	}
    }
    
    private void showNumberErrors(int numErrors)
	{
    	String statement = String.format("The text contains: %d errors", numErrors);
    	this.errorsLabel.setText(statement);
    	this.errorsLabel.setVisible(true);
	}

	private void showWrongWords(Collection<String> wrongWords)
	{
		StringBuilder list = new StringBuilder();
		for(String w : wrongWords)
		{
			if(list.length() > 0)
				list.append(", ");
			
			list.append('\"').append(w).append('\"');
		}
		this.wrongWordsArea.setText(list.toString());
	}

	private void showTimeInMilliseconds(long nanoseconds)
	{
    	double milliseconds = ((double)nanoseconds) / Math.pow(10, 6);
		String statement = String.format("> Spell check completed in %.6f ms", milliseconds);
		this.computationTimeLabel.setText(statement);
		this.computationTimeLabel.setVisible(true);
	}
	
	private boolean isLanguageSelected() 
    { 
    	return this.model.getLanguage() != null;
    }

	@FXML
    void initialize() 
    {
        assert computationTimeLabel != null : "fx:id=\"computationTimeLabel\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert languagesComboBox != null : "fx:id=\"languagesComboBox\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert inputTextArea != null : "fx:id=\"inputTextArea\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert spellCheckButton != null : "fx:id=\"spellCheckButton\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert wrongWordsArea != null : "fx:id=\"wrongWordsArea\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert errorsLabel != null : "fx:id=\"errorsLabel\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
        assert clearTextButton != null : "fx:id=\"clearTextButton\" was not injected: check your FXML file 'Scene_lab03.fxml'.";
    }

}



