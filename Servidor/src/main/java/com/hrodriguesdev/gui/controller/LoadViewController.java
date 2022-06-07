package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadViewController implements Initializable {
	
	private Timeline timeline;
	private static Stage stage;	
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = new Stage();
		beginTimer();
	}

	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(6), ev -> {
			if (MainApp.springStart) {
				//System.out.println("spring Start true ");
				try {
					AnchorPane anchorPane = (AnchorPane) NewView.loadFXML("mainView", MainApp.viewController);
					NewView.getNewView("Controle de Caixote", new Scene(anchorPane), stage);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				MainApp.stage.close();
				timeline.stop();
			}
		}));

	timeline.setCycleCount(10);
	timeline.play();

	}
	
	public static Stage getStage() {
		return stage;
	}
	
}
