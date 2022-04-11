package at.ac.fhcampuswien.aabcnews;


public class NewsApi {
    private static NewsApi instance;

    private NewsApi(){
    }

    public static NewsApi getInstance() {
        if (instance == null) {
            instance = new NewsApi();
        }
        return instance;
    }

}
