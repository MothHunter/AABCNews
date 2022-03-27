package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppController {
    String query;
    private List<Article> articles;

    @FXML
    private Label welcomeText;

    @FXML
    private Button quitButton;

    //TODO: 1. replace "onHelloButtonClick" with "onGetNewsButtonClick" method
    //         Name must also be changed in menu-view.xml for the "onAction" property of the button
    //         (switch to "Text" display)
    //      2. implement functionality for the button (filter news list and display requested items)
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //TODO: 1. write "onQuitButtonClick" method and add as "onAction" property in menu-view.xml
    //      2. implement functionality for the button (close program)
    @FXML
    protected void onQuitButtonClick() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }


    public AppController() {
      this.articles = generateMockList();
    }
    public void setArticles(List<Article> articles){
        this.articles = articles;
    }

    public int getArticleCount() {
        // TODO (A2) re-enable method part for articles == null
        //      -> run test
        //      -> commit & push
        /*
        if(articles == null) {
            return 0;
        }

         */
        // TODO (A4) re-enable method part for returning number of articles in the list
        //      -> run test
        //      -> commit & push
        // return articles.size();
        return 0;
    }

    public void getTopHeadlinesAustria(){
        // TODO (B2) write code to return an empty list when articles is set to null
        //      -> run test
        //      -> commit & push


        // TODO (B4) write code to return the entire list of articles if it is not null
        //      -> run test
        //      -> commit & push

    }

    public void getAllNewsBitcoin() {

        // TODO (C2) write code to return an empty list when articles is set to null
        //      -> run test
        //      -> commit & push


        // TODO (C4) write code to return the entire list of articles if it is not null
        //      -> run test
        //      -> commit & push

    }

    public List<Article> filterList(String query, List<Article> articles) {
        List<Article> foundArticles = new ArrayList<Article>();
        for (int i = 0; i < articles.size(); i++) {
            Article a = articles.get(i);
            if (a.getTitle().toLowerCase().contains(query.toLowerCase())) {
                foundArticles.add(a);
            }
        }

        return foundArticles;
    }

    private List<Article> generateMockList(){
        List<Article> mockList = new ArrayList<>();
        mockList.add(new Article("Florian Bodner","Warum heute zutage gehen die Kinder gern in die Schule"));
        mockList.add(new Article("Lea M. Christa", "Tiger momming for the childless"));
        mockList.add(new Article("Florian Bodner", "Why chocolate should count as a vegetable"));
        mockList.add(new Article("M. O. Tivation", "Ich bin dann mal weg..."));
        mockList.add(new Article("Navid Dariya", "Bitcoin price: Who controls Bitcoin price?"));
        mockList.add(new Article("Navid Dariya", "Wundambulanz Austria präsentiert ihre frisch gebackenen Absolventen im Wundmanagement"));
        return mockList;
    }

}
