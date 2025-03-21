package com.example.saperfx.Saper;

import com.example.saperfx.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SaperController {

    final private SaperModel saperModel;
    private String[][] bord;

    public SaperController(SaperModel saperModel){
        this.saperModel = saperModel;
        createEmptyBord();
    }
    private void createEmptyBord(){
        this.bord = new String[saperModel.getYCells()][saperModel.getXCells()];
        for(String[] cell : this.bord) {
            Arrays.fill(cell, " ");
        }
    }
    public void setGame(Point firstPoint){
        saperModel.setUserFirstPoint(firstPoint);
        saperModel.setGame();
        Map<Point, String> pointsMap = new HashMap<>();
        uncoverPartOfBord(firstPoint, pointsMap);
    }

    public void uncoverPartOfBord(Point point, Map<Point, String> pointsMap){
        if(isPointOutOfBorder(point)){
            return;
        }
        if(isPointABomb(point)){
            //TODO
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
                uncoverPartOfBord(leftPoint, pointsMap);
            }
            //Right
            if(!pointsMap.containsKey(rightPoint)){
                uncoverPartOfBord(rightPoint, pointsMap);
            }
            //Up
            if(!pointsMap.containsKey(upPoint)){
                uncoverPartOfBord(upPoint, pointsMap);
            }
            //Down
            if(!pointsMap.containsKey(downPoint)){
                uncoverPartOfBord(downPoint, pointsMap);
            }
        }
    }
    private boolean isPointOutOfBorder(Point point){
        return point.getX() < 0
               || point.getX() >= saperModel.getXCells()
               || point.getY() < 0
               || point.getY() >= saperModel.getYCells();
    }
    private boolean isPointABomb(Point point){
        //TODO
        return false;
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
