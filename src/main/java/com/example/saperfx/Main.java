package com.example.saperfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.saperfx.GameDifficulty.EASY;

/*
public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws IOException {
        SaperView view = new SaperView();
        Scene scene = new Scene(view.asParent(), 320, 240);
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
 */
public class Main {
    public static void main(String[] args) {
        SaperModel saperModel = new SaperModel(EASY);
        saperModel.setUserFirstPoint(3,3);
        saperModel.placingBombsOnBord();
        saperModel.placingNumbers();
        System.out.println(saperModel.toString());
    }
}
