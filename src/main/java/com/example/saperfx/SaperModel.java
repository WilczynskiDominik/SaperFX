package com.example.saperfx;

import java.util.Arrays;
import java.util.Random;

public class SaperModel {
    private int bombs;
    private int xCells;
    private int yCells;
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

    public SaperModel(GameDifficulty gameDifficulty){
        switch (gameDifficulty){
            case EASY -> {
                this.bombs = 10;
                this.xCells = 9;
                this.yCells = 9;
                createBord();
            }
            case NORMAL -> {
                this.bombs = 40;
                this.xCells = 16;
                this.yCells = 16;
                createBord();
            }
            case HARD -> {
                this.bombs = 99;
                this.xCells = 30;
                this.yCells = 16;
                createBord();
            }
        }
    }
    private void createBord(){
        this.bord = new String[this.yCells][this.xCells];
        for(String[] cell : this.bord) {
            Arrays.fill(cell, " ");
        }
    }

    public void setUserFirstPoint(int x, int y){
        this.userFirstPoint = new Point(x, y);
    }

    public void placingBombsOnBord(){
        Random randomNumber = new Random();
        for(int i = 0; i < this.bombs; i++){
            selectingCellWhereBombWillBe(randomNumber);
        }
    }
    private void selectingCellWhereBombWillBe(Random randomNumber){
        if(hasAllBombsBeenPlaced()){
            return;
        }
        int randXCell = randomNumber.nextInt(this.xCells);
        int randYCell = randomNumber.nextInt(this.yCells);
        Point cellWhereWillBeBomb = new Point(randXCell, randYCell);
        if(checkIfRandomPointIsEqualToFirstSelectedPointByUser(randomNumber, cellWhereWillBeBomb)){
            return;
        }
        if(checkIfRandomPointIsAlreadyBombed(randomNumber, cellWhereWillBeBomb)){
            return;
        }
        this.bord[randYCell][randXCell] = this.bombName;
        bombsPlaced += 1;
    }
    private boolean checkIfRandomPointIsEqualToFirstSelectedPointByUser(Random randomNumber,Point cellWhereWillBeBomb){
        if(userFirstPoint.isEqual(cellWhereWillBeBomb)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        return false;
    }
    private boolean checkIfRandomPointIsAlreadyBombed(Random randomNumber,Point cellWhereWillBeBomb){
        if(this.bord[cellWhereWillBeBomb.getY()][cellWhereWillBeBomb.getX()].equals(this.bombName)){
            selectingCellWhereBombWillBe(randomNumber);
            return true;
        }
        return false;
    }
    private boolean hasAllBombsBeenPlaced(){
        return this.bombsPlaced == this.bombs;
    }
    public void placingNumbers(){
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
