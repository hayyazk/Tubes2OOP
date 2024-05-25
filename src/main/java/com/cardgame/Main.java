package com.cardgame;

import com.cardgame.controller.StateController;
import com.cardgame.state.State;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    private StateController stateController;

    @Override
    public void start(Stage stage) throws IOException {
        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = fxmlLoader.load();
        stateController = fxmlLoader.getController();
        stateController.setState(new State());
        stateController.updateState();
        stateController.initDragDrop();
        stage.setTitle("KelolaKerajaan");
        stage.setScene(new Scene(root));
    }
    public static void main(String[] args) {
        launch();
    }
}