package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	private static Stage mainWindow;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			mainWindow = primaryStage;
			Parent root = (GridPane) FXMLLoader.load(getClass().getResource("/application/layout/MainWindow.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("/application/style/mainWindow.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene (String source, String title, double width, double height) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(source));
		mainWindow.setWidth(width);
		mainWindow.setHeight(height);
		mainWindow.getScene().setRoot(pane);
		mainWindow.centerOnScreen();
		mainWindow.setTitle(title);
	}
	
	public void close () {
		mainWindow.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
