package se.addinit.genera.generatree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GeneraTree extends Application {

	  public static void main(String[] args) {
		    launch(args);
		  }

		  @Override
		  public void start(Stage stage) throws Exception {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GeneraTree.fxml"));
		    Parent root = fxmlLoader.load();
		    Scene scene = new Scene(root, 2000, 1400);

		    stage.setOnShown(windowEvent -> fxmlLoader.<GeneraTreeController>getController().onLoaded());

		    stage.setTitle("GeneraTree");
		    stage.setScene(scene);
		    stage.show();
		  }
}
