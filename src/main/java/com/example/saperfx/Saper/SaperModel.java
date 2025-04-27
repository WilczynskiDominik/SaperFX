package com.example.saperfx.Saper;

import com.example.saperfx.Point;

import java.util.Arrays;
import java.util.Random;

public class SaperModel {
    private int bombs;
    private int columns;
    private int rows;
    private String[][] bord;
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
            Arrays.fill(cell, " ");
        }
    }

    public void setUserFirstPoint(Point point){
        this.userFirstPoint = point;
    }

    public void setGame(){
        placingBombsOnBord();
        placingNumbers();
    }
    private void placingBombsOnBord(){
        Random randomNumber = new Random();
        for(int i = 0; i < this.bombs; i++){
            selectingCellWhereBombWillBe(randomNumber);
        }
    }
    private void selectingCellWhereBombWillBe(Random randomNumber){
        if(hasAllBombsBeenPlaced()){
            return;
        }
        int randXCell = randomNumber.nextInt(this.columns);
        int randYCell = randomNumber.nextInt(this.rows);
        Point cellWhereWillBeBomb = new Point(randXCell, randYCell);
        if(isRandomPointInNonBombedArea(randomNumber, cellWhereWillBeBomb)){
            return;
        }
        if(isRandomPointAlreadyBombed(randomNumber, cellWhereWillBeBomb)){
            return;
        }
        this.bord[randYCell][randXCell] = this.bombName;
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
        for(int i = 0; i < this.bord.length; i++){
            for(int j = 0; j < this.bord[i].length; j++){
                if(this.bord[i][j].equals(this.bombName)){
                    j++;
                }
                try{
                    int bombsAround = bombsDetector(j , i);
                    this.bord[i][j] = String.valueOf(bombsAround);
                }catch (Exception e){
                    break;
                }
            }
        }
    }
    private int bombsDetector(int x, int y){
        int[] bombsAround = {0};
        upBombCheck(x, y, bombsAround);
        upAndRightBombCheck(x, y, bombsAround);
        rightBombCheck(x, y, bombsAround);
        rightAndDownBombCheck(x, y, bombsAround);
        downBombCheck(x, y, bombsAround);
        downAndLeftBombCheck(x, y, bombsAround);
        leftBombCheck(x, y, bombsAround);
        leftAndUpBombCheck(x, y, bombsAround);
        return bombsAround[0];
    }
    private void upBombCheck(int x, int y, int[] i){
        String contentFromUpperCell;
        try{
            contentFromUpperCell = this.bord[y+1][x];
        }catch(Exception e){
            return;
        }
        if(contentFromUpperCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void upAndRightBombCheck(int x, int y, int[] i){
        String contentFromUpAndRightCell;
        try{
            contentFromUpAndRightCell = this.bord[y+1][x+1];
        }catch(Exception e){
            return;
        }
        if(contentFromUpAndRightCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void rightBombCheck(int x, int y, int[] i){
        String contentFromRightCell;
        try{
            contentFromRightCell = this.bord[y][x+1];
        }catch(Exception e){
            return;
        }
        if(contentFromRightCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void rightAndDownBombCheck(int x, int y, int[] i){
        String contentFromRightAndDownCell;
        try{
            contentFromRightAndDownCell = this.bord[y-1][x+1];
        }catch(Exception e){
            return;
        }
        if(contentFromRightAndDownCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void downBombCheck(int x, int y, int[] i){
        String contentFromDownCell;
        try{
            contentFromDownCell = this.bord[y-1][x];
        }catch(Exception e){
            return;
        }
        if(contentFromDownCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void downAndLeftBombCheck(int x, int y, int[] i){
        String contentFromDownAndLeftCell;
        try{
            contentFromDownAndLeftCell = this.bord[y-1][x-1];
        }catch(Exception e){
            return;
        }
        if(contentFromDownAndLeftCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void leftBombCheck(int x, int y, int[] i){
        String contentFromLeftCell;
        try{
            contentFromLeftCell = this.bord[y][x-1];
        }catch(Exception e){
            return;
        }
        if(contentFromLeftCell.equals(this.bombName)){
            i[0]++;
        }
    }
    private void leftAndUpBombCheck(int x, int y, int[] i){
        String contentFromLeftAndUpCell;
        try{
            contentFromLeftAndUpCell = this.bord[y+1][x-1];
        }catch(Exception e){
            return;
        }
        if(contentFromLeftAndUpCell.equals(this.bombName)){
            i[0]++;
        }
    }
}
