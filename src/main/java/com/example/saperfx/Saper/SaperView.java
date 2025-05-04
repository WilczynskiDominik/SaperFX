package com.example.saperfx.Saper;

import com.example.saperfx.Layout.Bord.ButtonBordController;
import com.example.saperfx.Layout.Bord.HBoxBordController;
import com.example.saperfx.Layout.MenuBarController;
import com.example.saperfx.Layout.DataPanelController;
import com.example.saperfx.Point;
import javafx.application.Platform;
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
    private GameDifficulty gameDifficulty;
    private EndGameStatus endGameStatus;
    private StackPane mainView;
    private VBox mainVBox;
    private VBox playerPanel;
    private HBox dataPanel;
    private DataPanelController dataPanelController;
    private VBox bordPanel;

    public SaperView (SaperController saperController){
        this.saperController = saperController;
        this.gameDifficulty = GameDifficulty.EASY;
        this.saperController.setSaperDifficulty(this.gameDifficulty);
        createView();
    }
    public Parent asParent(){
        return this.mainView;
    }
    //create game view
    private void createView(){
        this.mainView = new StackPane();
        this.mainVBox = new VBox();
        createMenuBar();
        createPlayerPanel();
        this.mainView.getChildren().add(this.mainVBox);
    }
    private void createMenuBar(){
        MenuBar menuBar = new MenuBar();
        new MenuBarController(menuBar, this);
        this.mainVBox.getChildren().addAll(menuBar);
    }
    private void createPlayerPanel(){
        this.playerPanel = new VBox();
        this.dataPanel = new HBox();
        this.bordPanel = new VBox();
        createDataPanel();
        createGamePanel();
        this.mainVBox.getChildren().add(this.playerPanel);
        dataPanelController.updateDataPanelController();
    }
    private void createDataPanel(){
        this.dataPanelController = new DataPanelController(this.dataPanel, this.saperController, this);

        this.playerPanel.getChildren().add(this.dataPanel);
    }
    private void createGamePanel(){
        createBord(this.bordPanel);
        this.playerPanel.getChildren().add(this.bordPanel);
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
    //restart game view
    public void restartGameView(GameDifficulty gameDifficulty){
        this.bordPanel.setDisable(false);
        clearView();
        this.dataPanelController.restartEndGameText();
        this.gameDifficulty = gameDifficulty;
        this.saperController.setSaperDifficulty(this.gameDifficulty);
        createMenuBar();
        createPlayerPanel();
        this.mainView.getScene().getWindow().sizeToScene();
        this.mainView.getScene().getWindow().centerOnScreen();
    }
    private void clearView(){
        this.mainVBox.getChildren().clear();
        this.playerPanel.getChildren().clear();
        this.dataPanel.getChildren().clear();
        this.bordPanel.getChildren().clear();
    }

    private void listenButton(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                rightClick(button);
            }
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                leftClick(button);
            }
        });
    }
    //RPM
    private void rightClick(Button button){
        Point point = makePoint(button);
        if(!saperController.isFirstPointSelected()) {
            return;
        }
        if(saperController.getFlaggedMap().containsKey(point)){
            setQuestionMarkOnPoint(point);
            updateDataPanel();
            return;
        }
        if(saperController.getQuestionMarkMap().containsKey(point)){
            removeQuestionMarkOnPoint(point);
            return;
        }
        if(saperController.getPointsMap().containsKey(point)){
            return;
        }
        setFlagOnPoint(point);
        updateDataPanel();
    }
    private void updateDataPanel(){
        this.dataPanelController.updateFlagCounter();
    }
    private void setFlagOnPoint(Point point){
        saperController.setPointsMap(point, saperController.getBordData(point));
        saperController.setFlaggedMap(point, saperController.getBordData(point));
        Button button = (Button) this.mainView.lookup("#" + point.getY() + "-" + point.getX());
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
        this.endGameStatus = EndGameStatus.WIN;
        endGame();
    }
    private boolean isBombUnFlagged(Point point){
        return !saperController.getFlaggedMap().containsKey(point);
    }
    private void setQuestionMarkOnPoint(Point point){
        saperController.removeFlaggedMap(point, saperController.getBordData(point));
        saperController.setQuestionMarkMap(point, saperController.getBordData(point));
        Button button = (Button) this.mainView.lookup("#" + point.getY() + "-" + point.getX());
        button.setText("?");
    }
    private void removeQuestionMarkOnPoint(Point point){
        saperController.removePointsMap(point, saperController.getBordData(point));
        saperController.removeQuestionMarkMap(point, saperController.getBordData(point));
        Button button = (Button) this.mainView.lookup("#" + point.getY() + "-" + point.getX());
        button.setText(" ");
        checkIfAllFlagsAreOnBombs();
    }
    //LPM
    private void leftClick(Button button){
        Point point = makePoint(button);
        if(!saperController.isFirstPointSelected()) {
            saperController.setGame(point);
            this.dataPanelController.startTimer();
        }
        if(saperController.getPointsMap().containsKey(point)){
            return;
        }
        uncoverPartOfBord(point);
    }
    private Point makePoint(Button button){
        String[] id = button.getId().split("-");
        int x = Integer.parseInt(id[1]);
        int y = Integer.parseInt(id[0]);
        return new Point(x, y);
    }
    public void uncoverPartOfBord(Point point){
        //saperController.printBord();
        if(isPointOutOfBorder(point)){
            return;
        }
        if(isPointABomb(point)){
            this.endGameStatus = EndGameStatus.LOOSE;
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
        Button button = (Button) this.mainView.lookup("#" + point.getY() + "-" + point.getX());
        String data = saperController.getBordData(point);
        if(data.equals("0")){
            button.setText(" ");
            button.setDisable(true);
        }else{
            button.setText(data);
        }
    }
    //end game
    private void endGame() {
        showBomb();
        this.dataPanelController.createEndGameText(this.endGameStatus);
        this.dataPanelController.stopTimer();
        this.bordPanel.setDisable(true);
    }
    private void showBomb(){
        List<Point> bombTab = this.saperController.getBombTab();
        for(Point point: bombTab){
            Button button = (Button) this.mainView.lookup("#" + point.getY() + "-" + point.getX());
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
