package at.ac.fhcampuswien.aabcnews;

public class NewsApiException extends Exception{
    public NewsApiException(){
        super("A problem has happened while using the api");
    }

    public NewsApiException(String message){
        super(message);
    }
}
