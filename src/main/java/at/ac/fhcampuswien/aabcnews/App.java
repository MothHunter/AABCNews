package at.ac.fhcampuswien.aabcnews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("AABCD News");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("The fxmlLoader can't load the page! Try again!");
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        launch();
    }
}