package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application
{
	
	public static PersonBag personbag = new PersonBag();
	public static TextbookBag textbookbag = new TextbookBag();
	public static MasterCourseBag mastercoursebag = new MasterCourseBag();

	public static void main(String[] args) 
	{
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Suffolk County Community College");
		MainPane mainPane = new MainPane();
		Scene scene = new Scene(mainPane);
		
		textbookbag.load();
		personbag.load();
		mastercoursebag.load();
		
		setUserAgentStylesheet(STYLESHEET_CASPIAN);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(true);
		
		primaryStage.setOnCloseRequest(event -> {
			textbookbag.update();
			personbag.update();
			mastercoursebag.update();
		});
	}

}

//#484544
//#500000
