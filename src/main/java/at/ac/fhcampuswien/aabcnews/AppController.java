package at.ac.fhcampuswien.aabcnews;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class AppController {
    String query;
    private List<Article> articles;
    private static final int LIST_TEXT_BORDER = 20;

    private static final String EXIT_MESSAGE = "Thank you for using ABCD News!";
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
    protected void onGetNewsButtonClick() throws NewsApiException {

        String choice = choiceBox.getValue();
        List<Article> selectedList = new ArrayList<>();
        if (choice.equals("All News Bitcoin")) {
            selectedList = getAllNewsBitcoin();

        } else if  (choice.equals("Top News Austria")){
            selectedList = getTopHeadlinesAustria();
        } else if  (choice.equals("Source with Most Articles")){
            Source source = getSourceWithMostArticles();
            resultLabel.setText("Src with most articles: " + source.getName());
        } else if  (choice.equals("Author with the Longest Name")){
            String author = getAuthorWithLongestName();
            resultLabel.setText("Author: " + author);
        } else if  (choice.equals("Count of New York Times")){
            Long count = getCountOfNewYorkTimesSource();
            resultLabel.setText("Count: " + count);
        } else {
            selectedList = getArticlesWithTitleLessThan15Chars();
        }

        listView.getItems().clear();
        if(selectedList != null && !selectedList.isEmpty()) {
            for (int i = 0; i < selectedList.size(); i++) {
                Text item = new Text(selectedList.get(i).toString());
                item.setWrappingWidth(listView.getWidth() - LIST_TEXT_BORDER);
                listView.getItems().add(item);
            }
        }

        countLabel.setText("I found " + selectedList.size() + " article(s).");
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
        this.articles = generateMockList();
    }

    /*  initialize ist eine von javaFX definierte Methode, die aufgerufen wird nachdem der Constructor fertig ist und
        die mit @FXML markierten Referenzen "befüllt" wurden.
        Wir brauchen sie um GUI-Elemente zu initialisieren, auf die wir im Constructor noch nicht zugreifen können.
     */

    @FXML
    public void initialize() {
        choiceBox.getItems().add("All News Bitcoin"); // gets list of items and adds a new one
        choiceBox.getItems().add("Top News Austria");
        choiceBox.getItems().add("Source with Most Articles");
        choiceBox.getItems().add("Author with the Longest Name");
        choiceBox.getItems().add("Count of New York Times");
        choiceBox.getItems().add("Articles with Title less than 15 Chars");
        choiceBox.getSelectionModel().select(1); // sets the item at position 1 as the one selected at the beginning
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

    /*
        Which provider (= source) delivers the most articles?
        Which author has the longest name?
        How many articles come from the source "New York Times"?
        Which articles have a title that consists of less than 15 characters?
        Sort the articles by the length of their description in ascending order. If the
        descriptions of articles are of the same length, the sorting should be alphabetical.
     */

    public Source getSourceWithMostArticles() {
        List<Article> topArticles = getTopHeadlinesAustria();
        Source sourceWithMostArticles = topArticles.stream().collect(Collectors.groupingBy(Article::getSource, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
        return sourceWithMostArticles;
    }

    public String getAuthorWithLongestName() throws NewsApiException {
        List<Article> topArticles = getTopHeadlinesAustria();
        Optional<String> authorWithLongestName = topArticles.stream().map(Article::getAuthor).max(Comparator.comparing(String::length));
        return authorWithLongestName.orElseThrow(() -> new NewsApiException("There are no articles in the list"));
    }

    public long getCountOfNewYorkTimesSource() {
        List<Article> bitcoinArticles = getAllNewsBitcoin();
        long count = bitcoinArticles.stream().filter(a -> a.getSource().getName().equalsIgnoreCase("New York Times")).count();
        return count;
    }

    public List<Article> getArticlesWithTitleLessThan15Chars() {
        List<Article> topArticles = getTopHeadlinesAustria();
        List<Article> list = topArticles.stream().filter(a -> a.getTitle().length() < 15).collect(Collectors.toList());
        return list;
    }

    public List<Article> getTopHeadlinesAustria() {

        NewsResponse newsResponse = NewsApi.getInstance().requestTopHeadlines("AT");
        if (newsResponse == null) {
            return new ArrayList<>();
        } else {

            return newsResponse.getArticles();
        }

    }

    public List<Article> getAllNewsBitcoin() {
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
