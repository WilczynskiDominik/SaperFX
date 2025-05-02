package com.example.saperfx;

import com.example.saperfx.Saper.SaperController;
import com.example.saperfx.Saper.SaperModel;
import com.example.saperfx.Saper.SaperView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) {
        SaperModel saperModel = new SaperModel();
        SaperController saperController = new SaperController(saperModel);
        SaperView saperView = new SaperView(saperController);

        Scene scene = new Scene(saperView.asParent());
        mainStage.setScene(scene);
        mainStage.setTitle("Saper by Dominik Wilczynski");
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
