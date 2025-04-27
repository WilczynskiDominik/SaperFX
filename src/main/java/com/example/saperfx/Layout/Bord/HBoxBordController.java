package com.example.saperfx.Layout.Bord;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HBoxBordController {

    final private HBox HBOX;
    final private int HEIGHT;
    final private int WIDTH;

    public HBoxBordController(HBox hBox, int height, int width){
        this.HBOX = hBox;
        this.HEIGHT = height;
        this.WIDTH = width;

        this.HBOX.setPrefWidth(WIDTH);
        this.HBOX.setPrefHeight(HEIGHT);
        this.HBOX.setAlignment(Pos.CENTER);
    }
}
