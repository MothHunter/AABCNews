package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
    public enum Category {business, entertainment, general, health, science, sports, technology}
    public enum Country {ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr,
        hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs,
        ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za}
    public enum Language {ar, de, en, es, fr, he, it, nl, no, pt, ru, se, ud, zh}
    public enum SortBy {relevancy, popularity, publishedAt}
    //grds immer public enum, Name,{Konstanten}-->quasi extrem reduzierte Klasse
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

    public NewsResponse requestAllNews(String query, Language language){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("everything");

        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("language", language.toString());
        return handleRequest(urlBuilder);

    }

    public NewsResponse requestTopHeadlines(String country){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("top-headlines");

        urlBuilder.addQueryParameter("country", country);

       return handleRequest(urlBuilder);
    }
    public NewsResponse handleRequest(HttpUrl.Builder urlBuilder){
        urlBuilder.addQueryParameter("apiKey", apiKey);



        Request request = new Request.Builder().url(urlBuilder.build()).build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            String responseString = response.body().string();
            NewsResponse newsResponse = gson.fromJson(responseString, NewsResponse.class);
            return newsResponse;
        } catch (Exception e) {
            System.out.println("The http request failed!");
            e.printStackTrace();
            return null;
        }

    }
}
