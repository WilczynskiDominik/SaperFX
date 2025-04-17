package com.example.saperfx;

import com.example.saperfx.Saper.GameStatus;
import com.example.saperfx.Saper.SaperController;
import com.example.saperfx.Saper.SaperModel;

import java.util.Scanner;

import static com.example.saperfx.Saper.GameDifficulty.EASY;

/*
public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws IOException {
        SaperView view = new SaperView();
        Scene scene = new Scene(view.asParent(), 320, 240);
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
 */

/*
public class Main {
    public static void main(String[] args) {
        SaperModel saperModel = new SaperModel(EASY);
        SaperController saperController = new SaperController(saperModel);
        saperController.showBord();
        Scanner scanner = new Scanner(System.in);
        int firstX = scanner.nextInt();
        int firstY = scanner.nextInt();
        Point firstPoint = new Point(firstX,firstY);
        saperController.setGame(firstPoint);
        saperController.showBordUncovered();
        saperController.showBord();
        while(saperController.getGameStatus().equals(GameStatus.ONGOING)){
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            saperController.uncoverPartOfBord(new Point(x, y));
            if(saperController.getGameStatus().equals(GameStatus.ONGOING)){
                saperController.showBord();
            }
        }
    }
}
*/
