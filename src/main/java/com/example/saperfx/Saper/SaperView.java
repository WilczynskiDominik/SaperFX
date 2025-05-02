package com.example.saperfx.Saper;

import com.example.saperfx.Layout.Bord.ButtonBordController;
import com.example.saperfx.Layout.EndGame.EndGameStageController;
import com.example.saperfx.Layout.Bord.HBoxBordController;
import com.example.saperfx.Layout.MenuBarController;
import com.example.saperfx.Point;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

public class SaperView {

    private final SaperController saperController;
    private StackPane view;
    private VBox mainVBox;
    private GameDifficulty gameDifficulty;

    public SaperView (SaperController saperController){
        this.saperController = saperController;
        this.gameDifficulty = GameDifficulty.EASY;
        this.saperController.setSaperDifficulty(this.gameDifficulty);
        createView();
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public Parent asParent(){
        return this.view;
    }
    public void restartGame(GameDifficulty gameDifficulty){
        this.view.setDisable(false);
        clearView();
        this.gameDifficulty = gameDifficulty;
        this.saperController.setSaperDifficulty(this.gameDifficulty);
        createMenuBar(this.mainVBox);
        createPlayerPanel();
        createGameView();
        this.view.getScene().getWindow().sizeToScene();
    }
    private void createView(){
        this.view = new StackPane();
        this.mainVBox = new VBox();
        createMenuBar(this.mainVBox);
        createPlayerPanel();
        createGameView();
        this.view.getChildren().add(this.mainVBox);
    }
    private void createMenuBar(VBox vBox){
        MenuBar menuBar = new MenuBar();
        new MenuBarController(menuBar, this);
        vBox.getChildren().addAll(menuBar);
    }
    private void createPlayerPanel(){
        VBox playerPanel = new VBox();
        int height = 75;
        playerPanel.setPrefHeight(height);
        this.mainVBox.getChildren().add(playerPanel);
    }
    private void createGameView(){
        VBox bordVBox = new VBox();
        createBord(bordVBox);
        this.mainVBox.getChildren().add(bordVBox);
    }
    private void clearView(){
        this.mainVBox.getChildren().clear();
    }
    private void createBord(VBox vBox){
        int heightOfHBox = 30;
        int wightOfHBox = heightOfHBox * saperController.getNumberOfColumns();

        for(int i = 0; i < saperController.getNumberOfRows(); i++){
            HBox hBox = new HBox();
            new HBoxBordController(hBox, heightOfHBox, wightOfHBox);
            for(int j = 0; j < saperController.getNumberOfColumns(); j++){
                Button button = new Button(" ");
                button.setId(i + "-" + j);
                listenButton(button);
                new ButtonBordController(button, heightOfHBox);
                hBox.getChildren().add(button);
            }
            vBox.getChildren().add(hBox);
        }
    }

    private void listenButton(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.SECONDARY){
                    rightClick(button);
                }
                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    leftClick(button);
                }
            }
        });
    }
    private void rightClick(Button button){
        Point point = makePoint(button);
        if(!saperController.isFirstPointSelected()) {
            return;
        }
        if(saperController.getFlaggedMap().containsKey(point)){
            unFlagPoint(point);
            return;
        }
        if(saperController.getPointsMap().containsKey(point)){
            return;
        }
        flagPoint(point);
    }
    private void flagPoint(Point point){
        saperController.setPointsMap(point, saperController.getBordData(point));
        saperController.setFlaggedMap(point, saperController.getBordData(point));
        Button button = (Button) this.view.lookup("#" + point.getY() + "-" + point.getX());
        button.setText("\uD83D\uDEA9");
        checkIfAllFlagsAreOnBombs();
    }
    private void checkIfAllFlagsAreOnBombs(){
        for(Point point : saperController.getBombTab()){
            if(isBombUnFlagged(point)){
               return;
            }
        }
        int flaggedPoints = saperController.getFlaggedMap().size();
        int bombs = saperController.getBombTab().size();
        if(flaggedPoints != bombs){
            return;
        }
        endGame();
    }
    private boolean isBombUnFlagged(Point point){
        return !saperController.getFlaggedMap().containsKey(point);
    }
    private void unFlagPoint(Point point){
        saperController.removePointsMap(point, saperController.getBordData(point));
        saperController.removeFlaggedMap(point, saperController.getBordData(point));
        Button button = (Button) this.view.lookup("#" + point.getY() + "-" + point.getX());
        button.setText(" ");
        checkIfAllFlagsAreOnBombs();
    }
    private void leftClick(Button button){
        Point point = makePoint(button);
        if(!saperController.isFirstPointSelected()) {
            saperController.setGame(point);
        }
        if(saperController.getPointsMap().containsKey(point)){
            return;
        }
        uncoverBord(point);
    }
    private Point makePoint(Button button){
        String[] id = button.getId().split("-");
        int x = Integer.parseInt(id[1]);
        int y = Integer.parseInt(id[0]);
        return new Point(x, y);
    }
    private void uncoverBord(Point point){
        uncoverPartOfBord(point);
        saperController.printBord();
    }
    public void uncoverPartOfBord(Point point){
        if(isPointOutOfBorder(point)){
            return;
        }
        if(isPointABomb(point)){
            endGame();
            return;
        }
        saperController.setPointsMap(point, saperController.getBordData(point));
        if(hasPointBombsAround(point)){
            overWriteBord(point);
            return;
        }
        if(isPointEmpty(point)){
            overWriteBord(point);

            Point leftPoint = new Point(point.getX() - 1, point.getY());
            Point rightPoint = new Point(point.getX() + 1, point.getY());
            Point downPoint = new Point(point.getX(), point.getY() + 1);
            Point upPoint = new Point(point.getX(), point.getY() - 1);

            //Left
            if(!saperController.getPointsMap().containsKey(leftPoint)){
                uncoverPartOfBord(leftPoint);
            }
            //Right
            if(!saperController.getPointsMap().containsKey(rightPoint)){
                uncoverPartOfBord(rightPoint);
            }
            //Up
            if(!saperController.getPointsMap().containsKey(upPoint)){
                uncoverPartOfBord(upPoint);
            }
            //Down
            if(!saperController.getPointsMap().containsKey(downPoint)){
                uncoverPartOfBord(downPoint);
            }
        }
    }
    private boolean isPointOutOfBorder(Point point){
        return point.getX() < 0
                || point.getX() >= saperController.getNumberOfColumns()
                || point.getY() < 0
                || point.getY() >= saperController.getNumberOfRows();
    }
    private boolean isPointABomb(Point point){
        String pointData = saperController.getBordData(point);
        return pointData.equals("B");
    }
    private boolean hasPointBombsAround(Point point){
        int value = Integer.parseInt(saperController.getBordData(point));
        //In ASCII 66 equal B, in this program B is a bomb
        return value > 0 && value != 66;
    }
    private boolean isPointEmpty(Point point){
        return saperController.getBordData(point).equals("0");
    }
    private void overWriteBord(Point point){
        Button button = (Button) this.view.lookup("#" + point.getY() + "-" + point.getX());
        String data = saperController.getBordData(point);
        if(data.equals("0")){
            button.setText(" ");
            button.setDisable(true);
        }else{
            button.setText(data);
        }
    }
    private void endGame() {
        showBomb();
        this.view.setDisable(true);
        new EndGameStageController(this);
    }
    private void showBomb(){
        List<Point> bombTab = this.saperController.getBombTab();
        for(Point point: bombTab){
            Button button = (Button) this.view.lookup("#" + point.getY() + "-" + point.getX());
            Text textContained = new Text(button.getText());
            Text bombEmoji = new Text("\uD83D\uDCA3");
            bombEmoji.setOpacity(0.3);
            StackPane buttonStack = new StackPane(textContained, bombEmoji);
            button.setText("");
            button.setGraphic(buttonStack);
        }
    }
    public void exit(){
        Platform.exit();
    }
}
