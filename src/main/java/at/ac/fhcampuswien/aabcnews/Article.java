package at.ac.fhcampuswien.aabcnews;

public class Article {
    private String author;
    private String title;
    private String status;
    private int totalResults;
    private String[] articles;
    //private String source;
   // private String id;
    //private String name;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public Article(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", " +
                "Author: " + author;


    }
}

