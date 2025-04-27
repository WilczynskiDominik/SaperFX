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
        clearPointsMap();
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

    private void clearPointsMap(){
        this.pointsMap.clear();
    }
}
