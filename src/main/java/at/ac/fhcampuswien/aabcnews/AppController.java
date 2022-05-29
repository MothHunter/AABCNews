package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

public class AppController {
    String query;
    private List<Article> articles;
    private final String[] analysisOptions = {
            "Source w most Art.",
            "Author w longest Name",
            "Nr of Art. by NYT",
            "Title < 15 Chars",
            "Sort by Descr. Length"
    };
    private static final int LIST_TEXT_BORDER = 20;

    private static final String EXIT_MESSAGE = "Thank you for using AABCD News!";
    private static final String EXIT_MESSAGE_2 = "Hope to see you again soon!";
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ListView<Text> listView;

    @FXML
    private Button quitButton;
    @FXML
    private Label countLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Button analyzeButton;
    @FXML
    private ChoiceBox<String> analysisChoice;

    @FXML
    protected void onGetNewsButtonClick() {

        String choice = choiceBox.getValue();
        List<Article> selectedList;
        try {
            if (choice.equals("All News Bitcoin")) {
                selectedList = getAllNewsBitcoin();

            } else {
                selectedList = getTopHeadlinesAustria();
            }
        } catch (NewsApiException e) {
            selectedList = new ArrayList<>();
            System.out.println(e.getMessage() + ": " + e.exceptionCode);
        }
        articles = selectedList;
        displayArticles(selectedList, false);
    }

    @FXML
    protected void onAnalyzeButtonClick() {
        String myChoice = analysisChoice.getValue();
        if (myChoice.equals(analysisOptions[0])) {
            //toDo

        } else if (myChoice.equals(analysisOptions[1])) {


        } else if (myChoice.equals(analysisOptions[2])) {

        } else if (myChoice.equals(analysisOptions[3])) {

        } else if (myChoice.equals(analysisOptions[4])) {
            sortArticlesByLength();
        }
    }
    private void sortArticlesByLength(){
        List sortedArticles = articles.stream().sorted((a1,a2) -> a1.getDescription().compareTo(a2.getDescription()))
                .sorted((a1, a2) -> Integer.compare(a1.getDescription().length(), a2.getDescription().length()))
                .toList();
        displayArticles(sortedArticles, true);
    }
    @FXML
    protected void onQuitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Goodbye!");
        alert.setHeaderText(EXIT_MESSAGE);
        alert.setContentText(EXIT_MESSAGE_2);
        alert.showAndWait();
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    public AppController() {
        this.articles = new ArrayList<>();
    }

    /*  initialize ist eine von javaFX definierte Methode, die aufgerufen wird nachdem der Constructor fertig ist und
        die mit @FXML markierten Referenzen "befüllt" wurden.
        Wir brauchen sie um GUI-Elemente zu initialisieren, auf die wir im Constructor noch nicht zugreifen können.
     */

    @FXML
    public void initialize() {
        choiceBox.getItems().add("All News Bitcoin"); // gets list of items and adds a new one
        choiceBox.getItems().add("Top News Austria");
        choiceBox.getSelectionModel().select(1);// sets the item at position 1 as the one selected at the beginning
        for (int i = 0; i < analysisOptions.length; i++) {
            analysisChoice.getItems().add(analysisOptions[i]);
        }
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getArticleCount() {
        if (articles == null) {
            return 0;
        }
        return articles.size();
    }

    public List<Article> getTopHeadlinesAustria() throws NewsApiException {

        NewsResponse newsResponse = NewsApi.getInstance().requestTopHeadlines("AT");
        if (newsResponse == null) {
            return new ArrayList<>();
        } else {
            return newsResponse.getArticles();
        }
    }

    public List<Article> getAllNewsBitcoin() throws NewsApiException {
        NewsResponse newsResponse = NewsApi.getInstance().requestAllNews("bitcoin", NewsApi.Language.de);
        if (newsResponse == null) {
            return new ArrayList<>();
        } else {
            return newsResponse.getArticles();
        }
    }

    protected static List<Article> filterList(String query, List<Article> articles) {
        List<Article> foundArticles = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            Article a = articles.get(i);
            if (a.getTitle().toLowerCase().contains(query.toLowerCase())) {
                foundArticles.add(a);
            }
        }
        return foundArticles;
    }

    private void displayArticles(List<Article> list, boolean withDescription) {
        listView.getItems().clear();
        for (int i = 0; i < list.size(); i++) {
            Article art = list.get(i);
            Text item = new Text(art.toString() + (withDescription ? (System.lineSeparator() + art.getDescription() +
                            System.lineSeparator() + "(Description length: " + art.getDescription().length()) : ""));
            item.setWrappingWidth(listView.getWidth() - LIST_TEXT_BORDER);
            listView.getItems().add(item);
        }
        countLabel.setText("I found " + list.size() + " article(s).");
    }

    private List<Article> generateMockList() {
        List<Article> mockList = new ArrayList<>();
        mockList.add(new Article("Florian Bodner", "Warum heute zutage gehen die Kinder gern in die Schule"));
        mockList.add(new Article("Lea M. Christa", "Tiger momming for the childless"));
        mockList.add(new Article("Florian Bodner", "Why chocolate should count as a vegetable"));
        mockList.add(new Article("M. O. Tivation", "Ich bin dann mal weg..."));
        mockList.add(new Article("Navid Dariya", "Bitcoin price: Who controls Bitcoin price?"));
        mockList.add(new Article("Navid Dariya", "Wundambulanz Austria präsentiert ihre frisch gebackenen Absolventen im Wundmanagement"));
        return mockList;
    }

}
