package com.example.saperfx.Layout.EndGame;

import com.example.saperfx.Saper.GameDifficulty;
import com.example.saperfx.Saper.SaperView;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndGameStageController {

    private final SaperView saperView;
    private Stage endGameStage;

    private final int HEIGHT = 100;
    private final int WIDTH = 250;

    public EndGameStageController(SaperView saperView){
        this.saperView = saperView;
        Scene endGameScene = new Scene(createEndGameScene());
        this.endGameStage = new Stage();
        this.endGameStage.setScene(endGameScene);
        this.endGameStage.setTitle("End game");
        this.endGameStage.setResizable(false);
        this.endGameStage.show();
    }
    private Parent createEndGameScene(){
        Pane pane = new Pane();
        pane.setPrefHeight(HEIGHT);
        pane.setPrefWidth(WIDTH);
        addChildren(pane);
        return pane;
    }
    private void addChildren(Pane pane){
        VBox vbox = new VBox();
        int vboxHeight = HEIGHT;
        int vboxWidth = WIDTH;
        new VBoxEndGameController(vbox,vboxHeight, vboxWidth);
        addLabel(vbox);
        addChoiceButtons(vbox);
        pane.getChildren().add(vbox);
    }
    private void addLabel(VBox mainvBox){
        VBox vbox = new VBox();
        int vboxHeight = HEIGHT/3;
        int vboxWidth = WIDTH;
        new VBoxEndGameController(vbox,vboxHeight, vboxWidth);
        Label endGame = new Label("End game");
        new LabelEndGameController(endGame);
        Label question = new Label("Do you want to ");
        new LabelEndGameController(question);
        vbox.getChildren().add(endGame);
        vbox.getChildren().add(question);
        mainvBox.getChildren().add(vbox);
    }
    private void addChoiceButtons(VBox mainvBox){
        HBox hBox = new HBox();
        new HBoxEndGameController(hBox);
        Button playAgainButton = new Button("Play again");
        new ButtonEndGameController(playAgainButton);
        listenToPlayAgain(playAgainButton);
        Button exitButton = new Button("Exit game");
        new ButtonEndGameController(exitButton);
        listenToExitGame(exitButton);
        hBox.getChildren().add(playAgainButton);
        hBox.getChildren().add(exitButton);
        mainvBox.getChildren().add(hBox);
    }
    private void listenToPlayAgain(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saperView.restartGame(saperView.getGameDifficulty());
                endGameStage.close();
            }
        });
    }
    private void listenToExitGame(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saperView.exit();
            }
        });
    }

}
