package com.example.saperfx.Saper;

import com.example.saperfx.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SaperController {

    final private SaperModel saperModel;
    private GameStatus gameStatus;
    private String[][] bord;
    private boolean isFirstPointSelected;
    final private Map<Point, String> pointsMap = new HashMap<>();

    public SaperController(SaperModel saperModel){
        this.saperModel = saperModel;
        prepareGame();
    }

    public void setSaperDifficulty(GameDifficulty gameDifficulty){
        this.saperModel.setGameDifficulty(gameDifficulty);
        isFirstPointSelected = false;
        prepareGame();

    }
    private void prepareGame(){
        this.gameStatus = GameStatus.ONGOING;
        createEmptyBord();
    }
    public GameStatus getGameStatus(){
        return gameStatus;
    }
    public int getNumberOfRows(){ return this.saperModel.getRows();}
    public int getNumberOfColumns(){ return this.saperModel.getColumns();}
    public String getBordData(Point point){
        return saperModel.getBordsPointData(point.getX(), point.getY());
    }
    public Map<Point, String> getPointsMap(){
        return this.pointsMap;
    }
    public void setPointsMap(Point point, String string){
        pointsMap.put(point,string);
    }
    public boolean isFirstPointSelected(){
        return isFirstPointSelected;
    }

    private void createEmptyBord(){
        this.bord = new String[saperModel.getRows()][saperModel.getColumns()];
        for(String[] cell : this.bord) {
            Arrays.fill(cell, " ");
        }
    }
    public void setGame(Point firstPoint){
        saperModel.setUserFirstPoint(firstPoint);
        isFirstPointSelected = true;
        saperModel.setGame();
        //uncoverPartOfBord(firstPoint);
    }

    public void uncoverPartOfBord(Point point){
        if(isPointOutOfBorder(point)){
            return;
        }
        if(isPointABomb(point)){
            endGame();
            return;
        }
        pointsMap.put(point, saperModel.getBordsPointData(point.getX(), point.getY()));
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
            if(!pointsMap.containsKey(leftPoint)){
                uncoverPartOfBord(leftPoint);
            }
            //Right
            if(!pointsMap.containsKey(rightPoint)){
                uncoverPartOfBord(rightPoint);
            }
            //Up
            if(!pointsMap.containsKey(upPoint)){
                uncoverPartOfBord(upPoint);
            }
            //Down
            if(!pointsMap.containsKey(downPoint)){
                uncoverPartOfBord(downPoint);
            }
        }
    }
    private boolean isPointOutOfBorder(Point point){
        return point.getX() < 0
               || point.getX() >= saperModel.getColumns()
               || point.getY() < 0
               || point.getY() >= saperModel.getRows();
    }
    private boolean isPointABomb(Point point){
        String pointData = saperModel.getBordsPointData(point.getX(), point.getY());
        return pointData.equals("B");
    }
    public void endGame(){
        gameStatus = GameStatus.END;
    }
    private boolean hasPointBombsAround(Point point){
        int value = Integer.parseInt(saperModel.getBordsPointData(point.getX(), point.getY()));
        //In ASCII 66 equal B, in this program B is a bomb
        return value > 0 && value != 66;
    }
    private boolean isPointEmpty(Point point){
        return saperModel.getBordsPointData(point.getX(), point.getY()).equals("0");
    }
    private void overWriteBord(Point point){
        bord[point.getY()][point.getX()] = saperModel.getBordsPointData(point.getX(), point.getY());
    }

    public void showBordUncovered(){
        System.out.println("ODKRYTA");
        System.out.println(saperModel.toString());
    }
    public void showBord(){
        for(int i = 0; i < this.bord.length; i++){
            System.out.print("|\t");
            for(int j = 0; j < this.bord[i].length; j++){
                System.out.print(this.bord[i][j] + "\t");
            }
            System.out.print("|\n");
        }
    }
}
