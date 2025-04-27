package com.example.saperfx.Layout.Bord;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ButtonBordController {

    final private Button BUTTON;
    final private int HEIGHT;

    public ButtonBordController(Button button, int height){
        this.BUTTON = button;
        this.HEIGHT = height;

        this.BUTTON.setPrefWidth(HEIGHT);
        this.BUTTON.setPrefHeight(HEIGHT);
        this.BUTTON.setAlignment(Pos.CENTER);
    }
}
