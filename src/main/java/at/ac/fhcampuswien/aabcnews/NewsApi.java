package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
    private static final String root = "https://newsapi.org/v2/";
    private static final String apiKey = "0eb47479ee9b40829604c68ff2adb858";
    private static NewsApi instance;
    private OkHttpClient client;

    private NewsApi(){
        client = new OkHttpClient();
    }

    public static NewsApi getInstance() {
        if (instance == null) {
            instance = new NewsApi();
        }
        return instance;
    }

    public NewsResponse requestAllNews(String query, String language){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("everything");

        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("language", language);

        urlBuilder.addQueryParameter("apiKey", apiKey);

        //nur zum testen!!
        System.out.println(urlBuilder.build());

        Request request = new Request.Builder().url(urlBuilder.build()).build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            String responseString = response.body().string();
            System.out.println(responseString);
            NewsResponse newsResponse = gson.fromJson(responseString, NewsResponse.class);
            return newsResponse;
        } catch (Exception e) {
            System.out.println("The http request failed!");
            e.printStackTrace();
            return null;
        }
    }

    public NewsResponse requestTopHeadlines(String country){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("top-headlines");

        urlBuilder.addQueryParameter("country", country);

        urlBuilder.addQueryParameter("apiKey", apiKey);

        //nur zum testen!!
        System.out.println(urlBuilder.build());

        Request request = new Request.Builder().url(urlBuilder.build()).build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            String responseString = response.body().string();
            System.out.println(responseString);
            NewsResponse newsResponse = gson.fromJson(responseString, NewsResponse.class);
            return newsResponse;
        } catch (Exception e) {
            System.out.println("The http request failed!");
            e.printStackTrace();
            return null;
        }
    }
}
