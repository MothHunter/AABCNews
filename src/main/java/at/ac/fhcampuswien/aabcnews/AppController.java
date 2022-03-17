package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class AppController {
    String query;
    private List<Article> articles;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public AppController() {




    }
    public void setArticles(){}
    public void getArticleCount(){}
    public void getTopHeadlingesAustria(){}
    public void filterList(String query, List<Article> articles){
    }

    public void getAllNewsBitcoin() {
    }
}
