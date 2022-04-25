package at.ac.fhcampuswien.aabcnews;

import java.lang.reflect.Array;

public class Article {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    Source source = new Source();


    public Article(String author,String title){
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

