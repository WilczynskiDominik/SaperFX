package com.example.saperfx.Saper;

import com.example.saperfx.Layout.ButtonController;
import com.example.saperfx.Layout.HBoxController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SaperView {

    final SaperController saperController;
    final SaperModel saperModel;
    private Pane view;

    public SaperView (SaperController saperController, SaperModel saperModel){
        this.saperController = saperController;
        this.saperModel = saperModel;
        this.saperController.setSaperDifficulty(GameDifficulty.EASY);

        createStackPane();
    }

    public Parent asParent(){
        return this.view;
    }

    private void createStackPane(){
        this.view = new Pane();
        configuratorStackPane();
    }
    private void configuratorStackPane(){
        VBox vBox = new VBox();
        for(int i = 0; i < saperController.getNumberOfRows(); i++){
            HBox hBox = new HBox();
            new HBoxController(hBox, 400/9, 400);
            for(int j = 0; j < saperController.getNumberOfColumns(); j++){
                Button button = new Button(" ");
                new ButtonController(button, 400/9, 400/saperController.getNumberOfColumns());
                hBox.getChildren().add(button);
            }
            vBox.getChildren().add(hBox);
        }
        this.view.getChildren().add(vBox);
    }
}
