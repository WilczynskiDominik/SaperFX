package com.example.saperfx.Layout;

import com.example.saperfx.Saper.EndGameStatus;
import com.example.saperfx.Saper.SaperController;
import com.example.saperfx.Saper.SaperView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class DataPanelController {

    private final HBox PLAYER_PANEL;
    private final SaperController SAPER_CONTROLLER;
    private final SaperView SAPER_VIEW;
    private Label flagsCounterLabel;
    private Label endGameText;
    private Label timerCounterLabel;
    private Timer timer;
    private int seconds;
    private int minutes;

    public DataPanelController(HBox hBox, SaperController saperController, SaperView saperView){
        this.PLAYER_PANEL = hBox;
        this.SAPER_CONTROLLER = saperController;
        this.SAPER_VIEW = saperView;
        setSize();
    }
    public void updateFlagCounter(){
        int bombsInGame = this.SAPER_CONTROLLER.getBombs();
        int flagsPlaced = this.SAPER_CONTROLLER.getFlaggedMap().size();
        int flagsCounter = bombsInGame - flagsPlaced;
        String flagsCounterText = Integer.toString(flagsCounter);
        this.flagsCounterLabel.setText(flagsCounterText);
    }
    public void updateDataPanelController(){
        createChildren();
    }
    public void createEndGameText(EndGameStatus endGameStatus){
        switch (endGameStatus){
            case WIN -> this.endGameText.setText("YOU WIN");
            case LOOSE -> this.endGameText.setText("YOU LOOSE");
        }
    }
    public void restartEndGameText(){
        this.endGameText.setText(" ");
    }
    public void startTimer(){
        this.minutes = 0;
        this.seconds = 0;
        this.timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(seconds == 59){
                    minutes++;
                }
                if(seconds == 60){
                    seconds = 0;
                }
                Platform.runLater(() -> {
                    timerCounterLabel.setText(minutes + "." + seconds);
                });
                seconds++;
            }
        };
        this.timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    public void stopTimer(){
        this.timer.cancel();
    }

    private void setSize(){
        int height = 75;
        this.PLAYER_PANEL.setPrefHeight(height);
        this.PLAYER_PANEL.setAlignment(Pos.CENTER);
    }
    private void createChildren(){
        createFlagCounter();
        createRestartButtonAndEndText();
        createTimeCounter();
    }

    private void createFlagCounter(){
        VBox vBox = vBoxSettlement();

        Text flagLeftText = new Text("FLAG TO USE");

        this.flagsCounterLabel = new Label();
        int bombsInGame = this.SAPER_CONTROLLER.getBombs();
        int flagsPlaced = this.SAPER_CONTROLLER.getFlaggedMap().size();
        int flagsCounter = bombsInGame - flagsPlaced;
        String flagsCounterText = Integer.toString(flagsCounter);
        this.flagsCounterLabel.setText(flagsCounterText);
        vBox.getChildren().add(flagLeftText);
        vBox.getChildren().add(this.flagsCounterLabel);
        this.PLAYER_PANEL.getChildren().add(vBox);
    }

    private void createRestartButtonAndEndText(){
        VBox vBox = vBoxSettlement();
        Button restartButton = new Button("RESTART");
        restartButtonListener(restartButton);
        this.endGameText = new Label(" ");

        vBox.getChildren().add(restartButton);
        vBox.getChildren().add(this.endGameText);
        this.PLAYER_PANEL.getChildren().add(vBox);
    }
    private void restartButtonListener(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SAPER_VIEW.restartGameView(SAPER_CONTROLLER.getSaperDifficulty());
            }
        });
    }

    private void createTimeCounter(){
        VBox vBox = vBoxSettlement();
        Label timerLabel = new Label();
        timerLabel.setText("TIMER");
        this.timerCounterLabel = new Label();
        this.timerCounterLabel.setText("0.0");

        vBox.getChildren().add(timerLabel);
        vBox.getChildren().add(this.timerCounterLabel);
        this.PLAYER_PANEL.getChildren().add(vBox);
    }

    private VBox vBoxSettlement(){
        VBox vBox = new VBox();
        double prefWidth = getWidth() / 3;
        vBox.setPrefWidth(prefWidth);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
    private double getWidth(){
        int widthOfButton = 30;
        int numbersOfColumns;
        int finalWidth;
        switch (this.SAPER_CONTROLLER.getSaperDifficulty()){
            case EASY -> numbersOfColumns = 9;
            case NORMAL -> numbersOfColumns = 16;
            case HARD -> numbersOfColumns = 30;
            default -> numbersOfColumns = 0;
        }
        finalWidth = numbersOfColumns * widthOfButton;
        return finalWidth;
    }
}
