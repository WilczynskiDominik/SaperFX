package com.example.saperfx.Layout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ButtonController {

    final private Button BUTTON;
    final private int HEIGHT;

    public ButtonController(Button button, int height){
        this.BUTTON = button;
        this.HEIGHT = height;

        this.BUTTON.setPrefWidth(HEIGHT);
        this.BUTTON.setPrefHeight(HEIGHT);
        this.BUTTON.setAlignment(Pos.CENTER);
    }
}
