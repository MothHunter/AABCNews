package at.ac.fhcampuswien.aabcnews;

import at.ac.fhcampuswien.aabcnews.downloader.Downloader;
import at.ac.fhcampuswien.aabcnews.downloader.ParallelDownloader;
import at.ac.fhcampuswien.aabcnews.downloader.SequentialDownloader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.security.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppController {
    String query;
    private List<Article> articles;
    private final String[] analysisOptions = {
            "Source with most articles.",
            "Author with longest name",
            "Nr of articles by BBC News",
            "Title < 15 Chars",
            "Sort by description length"
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
    protected void onGetNewsButtonClick() { // hier fangen wir die Exception die von NewsApi

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
            informUser(e);
        }
        setArticles(selectedList);
        displayArticles(selectedList, false);
    }
    @FXML
    protected void onDownloadButtonClick () {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String selected = listView.getSelectionModel().getSelectedItem().getText();
        System.out.println(selected);
        selected = selected.split(", Author: ")[0].replace("Title: ", "");
        System.out.println(selected);
        Article article = null;
        for (int i = 0; i < articles.size(); i++) {
            if(articles.get(i).getTitle().equals(selected)) {
                article = articles.get(i);
            }
        }
        if (article == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("The Article could not be downloaded");
            alert.setContentText("could not find the selected Article in the list, sorry!");
            return;
        }
        try {
            NewsApi.getInstance().downloadText(article);
        } catch (NewsApiException e) {
            System.out.println(e.getMessage() + ": " + e.exceptionCode);
            informUser(e);
        }
    }

    @FXML
    protected void onDownloadAllButtonClick () {
        try {
            Date date = new Date();
            long beforeDownloads =date.getTime();

            int resultSequential = downloadURLs(new SequentialDownloader());
            // TODO print time in ms it took to download URLs sequentially
            long afterSeqDownloads = date.getTime();
            System.out.println("Time used for sequential download:" + (afterSeqDownloads-beforeDownloads));
            int resultParallel = downloadURLs(new ParallelDownloader());
            // TODO print time in ms it took to download URLs parallel
            long afterParDownloads = date.getTime();
            System.out.println("Time used for parallel download: " +(afterParDownloads-afterSeqDownloads));
        } catch (NewsApiException e){
            System.out.println(e.getMessage());
            informUser(e);
        }
    }

    private int downloadURLs(Downloader downloader) throws NewsApiException{
        if( articles == null)
            throw new NewsApiException("No articles found to download!");

        List<String> urls = articles.stream().map(Article::getUrl).collect(Collectors.toList());

        // TODO extract urls from articles with java stream

        return downloader.process(urls);
    }

    private void informUser(NewsApiException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An Error has occurred");

        switch (e.exceptionCode) {
            case badResponse:
                alert.setHeaderText("Bad response from server");
                alert.setContentText("Data request was rejected or corrupted. Please try again in a few minutes. " +
                        "If the problem persists please contact support");
                break;
            case badList:
                alert.setHeaderText("Analysis could not be completed!");
                alert.setContentText("You selected an analysis that could not be completed on the current list. " +
                        "If the list contains only very few and malformed entries this might be the reason. " +
                        "Try to re-request the news or select a different topic / option");
                break;
            case noConnection:
                alert.setHeaderText("Could not connect to the internet!");
                alert.setContentText("Please check your internet connection and firewall settings.");
                break;
            case webserviceUnreachable:
                alert.setHeaderText("News server unreachable!");
                alert.setContentText("The NewsApi server could not be reached, though your internet connection seems " +
                        "to be fine. Please try again in a few minutes or try to restart your router. If the Problem " +
                        "persists please contact customer support.");
                break;
            case requestConstructionFailed:
                alert.setHeaderText("Internal error with request creation");
                alert.setContentText("The process of creating an HTTP request failed. Either someone messed with the " +
                        "root URL for requests, or you are completely out of memory, in which case this error is " +
                        "probably the least of your concerns.");
                break;
            default:
                alert.setHeaderText("Error: " + e.exceptionCode);
                alert.setContentText(e.getMessage());
        }
        alert.showAndWait();
    }

    @FXML
    protected void onAnalyzeButtonClick() {
        String myChoice = analysisChoice.getValue();
        if (myChoice == null || getArticleCount() == 0) {
            return;
        }
        resultLabel.setText("");
        try {
            if (myChoice.equals(analysisOptions[0])) {
                Source source = getSourceWithMostArticles();
                resultLabel.setText("The source with the most articles was: " +
                        System.lineSeparator() + source.getName());
            } else if (myChoice.equals(analysisOptions[1])) {
                String longestName = getAuthorWithLongestName();
                resultLabel.setText("the longest author name was: " +
                        System.lineSeparator() + longestName);
            } else if (myChoice.equals(analysisOptions[2])) {
                long countNYT = getCountOfBBCNewsSource();
                resultLabel.setText("including " + countNYT + " from BBC News");
            } else if (myChoice.equals(analysisOptions[3])) {
                List<Article> newList = getArticlesWithTitleLessThan15Chars();
                displayArticles(newList, false);
            } else if (myChoice.equals(analysisOptions[4])) {
                List<Article> newList = sortArticlesByLength();
                displayArticles(newList, true);
            }
        } catch (NewsApiException e) {
            System.out.println(e.getMessage() + ": " + e.exceptionCode);
            informUser(e);
        }
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
        analysisChoice.getSelectionModel().select(0);
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
        NewsResponse newsResponse = NewsApi.getInstance().requestAllNews("bitcoin", NewsApi.Language.en);
        if (newsResponse == null) {
            return new ArrayList<>();
        } else {
            return newsResponse.getArticles();
        }
    }

        /*
        Which provider (= source) delivers the most articles?
        Which author has the longest name?
        How many articles come from the source "New York Times"?
        Which articles have a title that consists of less than 15 characters?
        Sort the articles by the length of their description in ascending order. If the
        descriptions of articles are of the same length, the sorting should be alphabetical.
     */

    public Source getSourceWithMostArticles() throws NewsApiException {
        Source sourceWithMostArticles = articles.stream().collect(Collectors.groupingBy(Article::getSource, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElseThrow(() -> new NewsApiException("There are no valid sources in the list.",
                        NewsApiException.EXCEPTION_CODE.badList));
        return sourceWithMostArticles;
    }

    public String getAuthorWithLongestName() throws NewsApiException {
        Optional<String> authorWithLongestName = articles.stream().map(Article::getAuthor).max(Comparator.comparing(String::length));
        return authorWithLongestName.orElseThrow(() -> new NewsApiException("There are no articles with authors in the list.",
                NewsApiException.EXCEPTION_CODE.badList));
    }

    public long getCountOfBBCNewsSource() {
        long count = articles.stream().filter(a -> a.getSource().getName().equalsIgnoreCase("BBC News")).count();
        return count;
    }

    public List<Article> getArticlesWithTitleLessThan15Chars() {
        List<Article> list = articles.stream().filter(a -> a.getTitle().length() < 15).collect(Collectors.toList());
        return list;
    }

    private List<Article> sortArticlesByLength() {
        List<Article> sortedArticles = articles.stream().sorted((a1, a2) -> a1.getDescription().compareTo(a2.getDescription()))
                .sorted((a1, a2) -> Integer.compare(a1.getDescription().length(), a2.getDescription().length()))
                .toList();
        return sortedArticles;
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
                    System.lineSeparator() + "Description length: " + art.getDescription().length()) : ""));
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
