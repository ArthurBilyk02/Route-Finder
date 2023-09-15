package com.example.route_finding_algorithm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600, 760);
		stage.setTitle("London Underground Route-Finder");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}}
