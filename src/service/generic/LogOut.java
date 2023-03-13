package service.generic;

import application.Main;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class LogOut <T>{
	
	public void logOut (T root) {
		Main m = new Main ();
		try {
			m.start(new Stage ());
			Stage stage = (Stage) ((Parent) root).getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
