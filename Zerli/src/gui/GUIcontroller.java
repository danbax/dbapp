package gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIcontroller {
	private static Scene currentScene;
	private  Stage primaryStage;
	private  static Stage currentStage;
	private  FXMLLoader loader;
	private  Pane root;
	
	public GUIcontroller(){
		
	}
	
	public void loadFxml(String fxmlFile) throws IOException
	{
		currentScene.getWindow().hide(); //hiding primary window
		primaryStage = new Stage();
		GUIcontroller.setCurrentStage(primaryStage);
		loader = new FXMLLoader();
		root = loader.load(getClass().getResource("/main/resources/"+fxmlFile).openStream());
		
		//scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
		Scene scene = new Scene(root);	
		GUIcontroller.currentScene = scene;
		primaryStage.setScene(scene); 		
		primaryStage.show(); 
	}

	public static Scene getCurrentScene() {
		return currentScene;
	}

	public static void setCurrentScene(Scene currentScene) {
		GUIcontroller.currentScene = currentScene;
	}

	public static Stage getCurrentStage() {
		return currentStage;
	}

	public static void setCurrentStage(Stage currentStage) {
		GUIcontroller.currentStage = currentStage;
	}
}
