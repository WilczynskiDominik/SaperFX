package com.example.saperfx.Layout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ButtonController {

    final private Button BUTTON;
    final private int HEIGHT;
    final private int WIDTH;

    public ButtonController(Button button, int height, int width){
        this.BUTTON = button;
        this.HEIGHT = height;
        this.WIDTH = width;

        this.BUTTON.setPrefWidth(WIDTH);
        this.BUTTON.setPrefHeight(HEIGHT);
        this.BUTTON.setAlignment(Pos.CENTER);
    }
}
