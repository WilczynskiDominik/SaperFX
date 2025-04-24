package com.example.saperfx;

import com.example.saperfx.Saper.SaperController;
import com.example.saperfx.Saper.SaperModel;
import com.example.saperfx.Saper.SaperView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws IOException {
        /*
        SaperModel saperModel = new SaperModel();
        SaperController saperController = new SaperController(saperModel);
        mainStage.setTitle("Saper by Dominik Wilczynski");
        mainStage.setResizable(false);
        mainStage.setScene(new Scene(ViewBuilder.loadView("SaperViewEasyMode.fxml", saperController)));
        mainStage.show();
        */
        SaperModel saperModel = new SaperModel();
        SaperController saperController = new SaperController(saperModel);
        SaperView saperView = new SaperView(saperController, saperModel);

        Scene scene = new Scene(saperView.asParent());
        mainStage.setScene(scene);
        mainStage.setTitle("Saper by Dominik Wilczynski");
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

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
