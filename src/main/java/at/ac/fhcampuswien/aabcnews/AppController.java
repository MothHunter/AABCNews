package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class AppController {
    private List<Article> articles;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public AppController() {

    }
}
