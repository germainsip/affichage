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
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {
    //public static Path ressources = Paths.get(String.valueOf(Main.class.getResource("docs/affiche.docx")));
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Locale.setDefault(Locale.FRENCH);
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/menu.fxml")));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/menu.fxml")));
        Scene scene = new Scene(root);
       // primaryStage.initStyle(StageStyle.UNDECORATED);
       stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public static Stage getPrimaryStage() {
        return stage;
    }
}
