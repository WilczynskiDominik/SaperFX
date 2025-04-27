package com.example.saperfx.Layout.EndGame;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class LabelEndGameController {

    private final Label label;

    public LabelEndGameController(Label label){
        this.label = label;
        setPadding();
    }
    private void setPadding(){
        this.label.setPadding(new Insets(5, 5, 5, 5));
    }
}
