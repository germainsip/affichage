package org.germain;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    //public static Path ressources = Paths.get(String.valueOf(Main.class.getResource("docs/affiche.docx")));
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/menu.fxml")));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/menu.fxml")));
        Scene scene = new Scene(root);
       // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }


}
