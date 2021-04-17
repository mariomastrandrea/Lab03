package it.polito.tdp.spellchecker;

import java.util.List;

import it.polito.tdp.spellchecker.checkstrategies.*;
import it.polito.tdp.spellchecker.model.*;
import it.polito.tdp.spellchecker.model.SpellCheckerManager.DictionaryType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene_lab03.fxml"));
        Parent root = loader.load();
		FXMLController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
       
        //set here dictionary data structure and the type of search
        DictionaryType dictionaryType = DictionaryType.HashSet;
        SpellCheckingStrategy checkingType = new ContainsChecking();
        
        //dependency injection
        SpellCheckerManager spellCheckerModel = new SpellCheckerManager(dictionaryType, checkingType);
        
        //set here dictionary languages
        List<String> languages = List.of("English","Italian");
        controller.setModel(spellCheckerModel, languages);
        
        String title = String.format("Lab03 - %s in %s dictionary", 
        								checkingType.getClass().getSimpleName(), dictionaryType.toString());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }

}
