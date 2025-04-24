package com.example.saperfx.Saper;

import com.example.saperfx.Layout.ButtonController;
import com.example.saperfx.Layout.HBoxController;
import com.example.saperfx.Point;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SaperView {

    private final SaperController saperController;
    private final SaperModel saperModel;
    private Pane view;

    public SaperView (SaperController saperController, SaperModel saperModel){
        this.saperController = saperController;
        this.saperModel = saperModel;
        this.saperController.setSaperDifficulty(GameDifficulty.EASY);

        createView();
    }

    public Parent asParent(){
        return this.view;
    }
    ///
    private void createView(){
        this.view = new Pane();
        createChildren();
    }
    private void createChildren(){
        int heightOfHBox = 30;
        int wightOfHBox = heightOfHBox * saperController.getNumberOfColumns();
        int wightOfButton = heightOfHBox;

        VBox vBox = new VBox();

        for(int i = 0; i < saperController.getNumberOfRows(); i++){
            HBox hBox = new HBox();
            new HBoxController(hBox, heightOfHBox, wightOfHBox);
            for(int j = 0; j < saperController.getNumberOfColumns(); j++){
                Button button = new Button(" ");
                button.setId(i + "-" + j);
                listenButton(button);
                new ButtonController(button, heightOfHBox, wightOfButton);
                hBox.getChildren().add(button);
            }
            vBox.getChildren().add(hBox);
        }
        this.view.getChildren().add(vBox);
    }

    private void listenButton(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateGame(button);
            }
        });
    }
    private void updateGame(Button button){
        System.out.println(button.getId());
        String[] id = button.getId().split("-");
        int x = Integer.parseInt(id[1]);
        int y = Integer.parseInt(id[0]);
        Point point = new Point(x, y);
        if(!saperController.isFirstPointSelected()) {
            saperController.setGame(point);
        }
        uncoverBord(point);
    }
    private void uncoverBord(Point point){
        uncoverPartOfBord(point);
    }
    public void uncoverPartOfBord(Point point){
        if(isPointOutOfBorder(point)){
            return;
        }
        if(isPointABomb(point)){
            saperController.endGame();
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
}
