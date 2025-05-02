package com.example.saperfx.Layout;

import com.example.saperfx.Saper.SaperController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DataPanelController {

    private final HBox PLAYER_PANEL;
    private final SaperController SAPER_CONTROLLER;

    public DataPanelController(HBox hBox, SaperController saperController){
        this.PLAYER_PANEL = hBox;
        this.SAPER_CONTROLLER = saperController;
        setSize();
        createChildren();
    }
    private void setSize(){
        int height = 75;
        this.PLAYER_PANEL.setPrefHeight(height);
    }
    private void createChildren(){
        HBox container = new HBox();
        createFlagCounter(container);
        createRestartButton(container);
        createTimeCounter(container);
        this.PLAYER_PANEL.getChildren().add(container);
    }
    private void createFlagCounter(HBox hBox){
        VBox vBox = new VBox();
        double maxWidth = this.PLAYER_PANEL.getMaxWidth();
        vBox.setMaxWidth(maxWidth);
        vBox.setAlignment(Pos.CENTER);

        Text flagLeftText = new Text("FLAG TO USE");

        Label flagsCounterLabel = new Label();
        int bombsInGame = this.SAPER_CONTROLLER.getBombs();
        int flagsPlaced = this.SAPER_CONTROLLER.getFlaggedMap().size();
        int flagsCounter = bombsInGame - flagsPlaced;
        String flagsCounterText = Integer.toString(flagsCounter);
        flagsCounterLabel.setText(flagsCounterText);
        vBox.getChildren().add(flagLeftText);
        vBox.getChildren().add(flagsCounterLabel);
        hBox.getChildren().add(vBox);
    }
    private void createRestartButton(HBox hBox){
    //TODO
    }
    private void createTimeCounter(HBox hBox){
    //TODO
    }
}
