package com.example.saperfx.Saper;

import com.example.saperfx.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaperController {

    final private SaperModel saperModel;
    private boolean isFirstPointSelected;
    final private Map<Point, String> pointsMap = new HashMap<>();
    final private Map<Point, String> flagedMap = new HashMap<>();
    final private Map<Point, String> questionMarkMap = new HashMap<>();

    public SaperController(SaperModel saperModel){
        this.saperModel = saperModel;
    }

    public void printBord(){
        System.out.println(saperModel.toString());
    }

    public void setSaperDifficulty(GameDifficulty gameDifficulty){
        clearGameData();
        this.saperModel.setGameDifficulty(gameDifficulty);
        isFirstPointSelected = false;
    }
    public GameDifficulty getSaperDifficulty(){
        return saperModel.getGameDifficulty();
    }
    private void clearGameData(){
        clearPointsMap();
        this.flagedMap.clear();
        this.saperModel.clearBombTab();
    }
    public int getNumberOfRows(){ return this.saperModel.getRows();}
    public int getNumberOfColumns(){ return this.saperModel.getColumns();}
    public String getBordData(Point point){
        return saperModel.getBordsPointData(point.getX(), point.getY());
    }
    //MAPS
    public Map<Point, String> getPointsMap(){
        return this.pointsMap;
    }
    public void setPointsMap(Point point, String string){
        pointsMap.put(point,string);
    }
    public void removePointsMap(Point point, String string){
        pointsMap.remove(point,string);
    }
    public Map<Point, String> getFlaggedMap(){
        return this.flagedMap;
    }
    public void setFlaggedMap(Point point, String string){
        flagedMap.put(point,string);
    }
    public void removeFlaggedMap(Point point, String string){
        flagedMap.remove(point,string);
    }
    public Map<Point, String> getQuestionMarkMap(){
        return this.questionMarkMap;
    }
    public void setQuestionMarkMap(Point point, String string){
        questionMarkMap.put(point,string);
    }
    public void removeQuestionMarkMap(Point point, String string){
        questionMarkMap.remove(point,string);
    }

    public boolean isFirstPointSelected(){
        return isFirstPointSelected;
    }
    public int getBombs(){
        return this.saperModel.getBombs();
    }
    public List<Point> getBombTab(){
        return this.saperModel.getBombTab();
    }
    public void setGame(Point firstPoint){
        saperModel.setUserFirstPoint(firstPoint);
        isFirstPointSelected = true;
        saperModel.setGame();
    }

    private void clearPointsMap(){
        this.pointsMap.clear();
    }
}
