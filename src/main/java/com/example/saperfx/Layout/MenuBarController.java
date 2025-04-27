package com.example.saperfx.Layout;

import com.example.saperfx.Saper.GameDifficulty;
import com.example.saperfx.Saper.SaperView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class MenuBarController {

    private final MenuBar menuBar;
    private final SaperView saperView;

    public MenuBarController(MenuBar menuBar, SaperView saperView){
        this.menuBar = menuBar;
        this.saperView = saperView;
        addMenus();
    }
    private void addMenus(){
        Menu options = new Menu("Options");
        addSubMenu(options);
        this.menuBar.getMenus().add(options);
    }
    private void addSubMenu(Menu menu){
        Menu gameDifficultyMenu = new Menu("Game difficulty");
        MenuItem exit = new Menu("Exit");
        exitGame(exit);
        addGameDifficultyItems(gameDifficultyMenu);
        menu.getItems().add(gameDifficultyMenu);
        menu.getItems().add(exit);
    }
    private void addGameDifficultyItems(Menu subMenu){
        MenuItem easy = new MenuItem("Easy");
        changeGameDifficulty(easy, GameDifficulty.EASY);
        MenuItem normal = new MenuItem("Normal");
        changeGameDifficulty(normal, GameDifficulty.NORMAL);
        MenuItem hard = new MenuItem("Hard");
        changeGameDifficulty(hard, GameDifficulty.HARD);

        subMenu.getItems().add(easy);
        subMenu.getItems().add(normal);
        subMenu.getItems().add(hard);
    }
    private void changeGameDifficulty(MenuItem menuItem, GameDifficulty gameDifficulty){
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saperView.restartGame(gameDifficulty);
            }
        });
    }
    private void exitGame(MenuItem menuItem){
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saperView.exit();
            }
        });
    }
}
