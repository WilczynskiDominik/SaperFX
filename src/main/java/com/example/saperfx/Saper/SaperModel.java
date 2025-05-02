package com.example.saperfx.Saper;

import com.example.saperfx.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SaperModel {
    private int bombs;
    private int columns;
    private int rows;
    private String[][] bord;
    private List<Point> bombTab;
    private Point userFirstPoint;
    private int bombsPlaced;
    private final String bombName = "B";

    @Override
    public String toString() {
        final StringBuilder bordInString = new StringBuilder();
        for(int i = 0; i < this.bord.length; i++){
            bordInString.append("|\t");
            for(int j = 0; j < this.bord[i].length; j++){
                bordInString.append(this.bord[i][j]).append("\t");
            }
            bordInString.append("|\n");
        }
        return bordInString.toString();
    }

    public SaperModel(){}
    public SaperModel(GameDifficulty gameDifficulty){

        setGameDifficulty(gameDifficulty);
    }
    public int getColumns(){
        return this.columns;
    }
    public int getRows(){
        return this.rows;
    }
    public String getBordsPointData(int x, int y){
        return bord[y][x];
    }
    public List<Point> getBombTab(){
        return this.bombTab;
    }
    public void clearBombTab(){
        try{
            if(!this.bombTab.isEmpty()){
                this.bombTab.clear();
            }
        }catch(Exception e){
            return;
        }
    }

    void setGameDifficulty(GameDifficulty gameDifficulty){
        switch (gameDifficulty){
            case EASY -> {
                this.bombs = 10;
                this.columns = 9;
                this.rows = 9;
                createBord();
            }
            case NORMAL -> {
                this.bombs = 40;
                this.columns = 16;
                this.rows = 16;
                createBord();
            }
            case HARD -> {
                this.bombs = 99;
                this.columns = 30;
                this.rows = 16;
                createBord();
            }
        }
    }

    private void createBord(){
        this.bord = new String[this.rows][this.columns];
        for(String[] cell : this.bord) {
            Arrays.fill(cell, "0");
        }
    }

    public void setUserFirstPoint(Point point){
        this.userFirstPoint = point;
    }
    public void setGame(){
        bombsPlaced = 0;
        placingBombsOnBord();
        placingNumbers();
    }

    private void placingBombsOnBord(){
        Random randomNumber = new Random();
        bombTab = new ArrayList<>();
        for(int i = 0; i < this.bombs; i++){
            selectingCellWhereBombWillBe(randomNumber);
        }
    }
    private void selectingCellWhereBombWillBe(Random randomNumber){
        if(hasAllBombsBeenPlaced()){
            return;
        }
        int randColumn = randomNumber.nextInt(this.columns);
        int randRow = randomNumber.nextInt(this.rows);
        Point PointWhereWillBeBomb = new Point(randColumn, randRow);
        if(isRandomPointInNonBombedArea(randomNumber, PointWhereWillBeBomb)){
            return;
        }
        if(isRandomPointAlreadyBombed(randomNumber, PointWhereWillBeBomb)){
            return;
        }
        this.bord[randRow][randColumn] = this.bombName;
        this.bombTab.add(new Point(randColumn, randRow));
        bombsPlaced += 1;
    }
    //nonbomb area is area around first point 3x3, in the middle is first point
    private boolean isRandomPointInNonBombedArea(Random randomNumber, Point cellWhereWillBeBomb){
        if(userFirstPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        //area around the first point
        Point leftUpPoint = new Point(userFirstPoint.getX() - 1, userFirstPoint.getY() - 1);
        if(leftUpPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point upPoint = new Point(userFirstPoint.getX(), userFirstPoint.getY() - 1);
        if(upPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point rightUpPoint = new Point(userFirstPoint.getX() + 1, userFirstPoint.getY() - 1);
        if(rightUpPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point rightPoint = new Point(userFirstPoint.getX() + 1, userFirstPoint.getY());
        if(rightPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point rightDownPoint = new Point(userFirstPoint.getX() + 1, userFirstPoint.getY() + 1);
        if(rightDownPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point downPoint = new Point(userFirstPoint.getX(), userFirstPoint.getY() + 1);
        if(downPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point leftDownPoint = new Point(userFirstPoint.getX() - 1, userFirstPoint.getY() + 1);
        if(leftDownPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        Point leftPoint = new Point(userFirstPoint.getX() - 1, userFirstPoint.getY());
        if(leftPoint.equals(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        return false;
    }
    private boolean isRandomPointAlreadyBombed(Random randomNumber,Point cellWhereWillBeBomb){
        if(this.bord[cellWhereWillBeBomb.getY()][cellWhereWillBeBomb.getX()].equals(this.bombName)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        return false;
    }
    private boolean hasAllBombsBeenPlaced(){
        return this.bombsPlaced == this.bombs;
    }
    private void placingNumbers(){
        for(Point point : this.bombTab){
            addNumber(new Point(point.getX(), point.getY() - 1));
            addNumber(new Point(point.getX() + 1, point.getY() - 1));
            addNumber(new Point(point.getX() + 1, point.getY()));
            addNumber(new Point(point.getX() + 1, point.getY() + 1));
            addNumber(new Point(point.getX(), point.getY() + 1));
            addNumber(new Point(point.getX() - 1, point.getY() + 1));
            addNumber(new Point(point.getX() - 1, point.getY()));
            addNumber(new Point(point.getX() - 1, point.getY() - 1));
        }
    }
    private void addNumber(Point point){
        int column = point.getX();
        int row = point.getY();
        try{
            int data = Integer.parseInt(this.bord[row][column]);
            data++;
            this.bord[row][column] = Integer.toString(data);
        }catch (Exception e){
            return;
        }
    }
}
