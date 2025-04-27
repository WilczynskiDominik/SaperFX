package com.example.saperfx.Layout;

import com.example.saperfx.Saper.GameDifficulty;
import com.example.saperfx.Saper.SaperController;
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
        Menu subMenu = new Menu("Game difficulty");
        addGameDifficultyItems(subMenu);
        menu.getItems().add(subMenu);
    }
    private void addGameDifficultyItems(Menu subMenu){
        RadioMenuItem easy = new RadioMenuItem("Easy");
        listen(easy, GameDifficulty.EASY);
        RadioMenuItem normal = new RadioMenuItem("Normal");
        listen(normal, GameDifficulty.NORMAL);
        RadioMenuItem hard = new RadioMenuItem("Hard");
        listen(hard, GameDifficulty.HARD);

        ToggleGroup gameDifficulty = new ToggleGroup();
        gameDifficulty.getToggles().add(easy);
        gameDifficulty.getToggles().add(normal);
        gameDifficulty.getToggles().add(hard);

        subMenu.getItems().add(easy);
        subMenu.getItems().add(normal);
        subMenu.getItems().add(hard);
    }
    private void listen(RadioMenuItem radioItem, GameDifficulty gameDifficulty){
        radioItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saperView.restartGame(gameDifficulty);
            }
        });
    }
}
