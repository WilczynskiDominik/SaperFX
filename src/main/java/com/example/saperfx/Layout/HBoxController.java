package com.example.saperfx.Layout;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HBoxController {

    final private HBox HBOX;
    final private int HEIGHT;
    final private int WIDTH;

    public HBoxController(HBox hBox, int height, int width){
        this.HBOX = hBox;
        this.HEIGHT = height;
        this.WIDTH = width;

        this.HBOX.setPrefWidth(WIDTH);
        this.HBOX.setPrefHeight(HEIGHT);
        this.HBOX.setAlignment(Pos.CENTER);
    }
}
