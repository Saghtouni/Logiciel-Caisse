package application;
	
import java.io.IOException;

import java.lang.reflect.Method;
import java.sql.SQLException;

import java.sql.Connection;

import DataBase.ConnectionDB;
import Fenetre.AnalyseControl;
import Fenetre.CaisseControl;
import Fenetre.EnregUtilControl;
import Fenetre.EnregisterProControl;
import Fenetre.LoginControl;
import Fenetre.MenuControl;
import Fenetre.ModifierProdControl;
import Fenetre.ModifierUtilControl;
import Fenetre.ProduitsControl;
import Fenetre.RemarqueUtilControl;
import Fenetre.SwitchControl;
import Fenetre.UtilisateursControl;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	static Double nbr;

	@Override
	public void start(Stage primaryStage) throws SQLException {
		ConnectionDB DBB = new ConnectionDB();
		Connection DB =  DBB.getDb();
		UtilisateursControl utiliControl = new UtilisateursControl(DB);
		LoginControl login = new LoginControl(DB);
        primaryStage.setScene(new Scene(login));
        primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login ITS-CASH");
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}