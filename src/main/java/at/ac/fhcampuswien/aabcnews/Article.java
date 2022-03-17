package at.ac.fhcampuswien.aabcnews;

public class Article {
    private String author;
    private String title;

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

