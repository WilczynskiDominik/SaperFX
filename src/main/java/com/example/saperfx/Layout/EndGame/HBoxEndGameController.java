package com.example.saperfx.Layout.EndGame;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HBoxEndGameController {

    private final HBox hBox;

    public HBoxEndGameController(HBox hBox){
        this.hBox = hBox;
        setAlignment();
        setSpacing();
    }
    private void setAlignment(){
        this.hBox.setAlignment(Pos.CENTER);
    }
    private void setSpacing(){
        this.hBox.setSpacing(10);
    }
}
