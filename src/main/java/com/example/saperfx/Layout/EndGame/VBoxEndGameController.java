package com.example.saperfx.Layout.EndGame;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBoxEndGameController {

    private final VBox vBox;

    public VBoxEndGameController(VBox vBox, int height, int width){
        this.vBox = vBox;
        vBox.setPrefHeight(height);
        vBox.setPrefWidth(width);
        setAlignment();
    }
    private void setAlignment(){
        this.vBox.setAlignment(Pos.CENTER);
    }
}
