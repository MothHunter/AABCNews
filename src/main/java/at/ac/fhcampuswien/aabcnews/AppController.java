package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    String query;
    private List<Article> articles;

    @FXML
    private Label welcomeText;

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



    public AppController() {
      articles = generateMockList();


    }
    public void setArticles(List<Article> articles){}
    public int getArticleCount(){


    return articles.size();
    }
    public void getTopHeadlinesAustria(){}
    public void filterList(String query, List<Article> articles){
    }

    public void getAllNewsBitcoin() {
    }

    //TODO: write generateMockList method to create dummy list of articles
    private List<Article> generateMockList(){
        List<Article> mockList = new ArrayList<>();
        mockList.add(new Article("Florian Bodner","Warum heute zutage gehen die Kinder gern in die Schule"));
        return mockList;
    }

}
