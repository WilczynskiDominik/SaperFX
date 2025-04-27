package com.example.saperfx.Layout.EndGame;

import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class ButtonEndGameController {

    private final Button button;

    public ButtonEndGameController(Button button){
        this.button = button;
        setPadding();
    }
    private void setPadding(){
        this.button.setPadding(new Insets(5));
    }
}
