package at.ac.fhcampuswien.aabcnews;

public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    private Source source;

    public Article(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author != null ? author : "n/a";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description != null? description : "";
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public Source getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", " +
                "Author: " + author;
    }
}

