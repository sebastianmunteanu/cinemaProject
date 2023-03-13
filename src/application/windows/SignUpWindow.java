package application.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignUpWindow {
	public void displayForm() {
		try {
			Stage window = new Stage();
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/application/layout/SignUpWindow.fxml"));
			Scene scene = new Scene(root, 400, 750);
			scene.getStylesheets().add(getClass().getResource("/application/style/signUpWindow.css").toExternalForm());
			window.setScene(scene);
			window.initModality(Modality.APPLICATION_MODAL);
			window.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
