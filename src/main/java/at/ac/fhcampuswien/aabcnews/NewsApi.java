package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.RealConnection;

import java.io.IOException;

public class NewsApi {
    public enum Category {business, entertainment, general, health, science, sports, technology}
    public enum Country {ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr,
        hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs,
        ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za}
    public enum Language {ar, de, en, es, fr, he, it, nl, no, pt, ru, se, ud, zh}
    public enum SortBy {relevancy, popularity, publishedAt}
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

    public NewsResponse requestAllNews(String query, Language language) throws NewsApiException{
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("everything");

        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("language", language.toString());
        return handleRequest(urlBuilder);
    }

    public NewsResponse requestTopHeadlines(String country) throws NewsApiException{
        HttpUrl.Builder urlBuilder = HttpUrl.parse(root).newBuilder();
        urlBuilder.addPathSegment("top-headlines");

        urlBuilder.addQueryParameter("country", country);

       return handleRequest(urlBuilder);
    }
    public NewsResponse handleRequest(HttpUrl.Builder urlBuilder) throws NewsApiException{
        urlBuilder.addQueryParameter("pageSize", "100");
        urlBuilder.addQueryParameter("apiKey", apiKey);

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Gson gson = new Gson();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new NewsApiException("Received error response: "
                        + (response.body() !=null ? response.body().string() : "no further information available!"),
                        NewsApiException.EXCEPTION_CODE.badResponse);
            }
            else if (response.body() == null) {
                throw new NewsApiException("Response had code \"ok\", but contained no body. Can this even happen?",
                        NewsApiException.EXCEPTION_CODE.badResponse);
            }
            String responseString = response.body().string();
            return gson.fromJson(responseString, NewsResponse.class);

        } catch (IOException e)  {
            throw new NewsApiException("Call execution for news request failed!", checkInternet() ?
                    NewsApiException.EXCEPTION_CODE.webserviceUnreachable :
                    NewsApiException.EXCEPTION_CODE.noConnection);
        } catch (NullPointerException e){
            throw new NewsApiException("Response contained body but it could not be converted to a string. " +
                    "This should not happen!");
        } catch (JsonSyntaxException e){
            throw new NewsApiException("Webservice returned json object of unexpected structure or bad syntax." +
                    "This should not happen!");
        }

    }

    //Todo: richtig implementieren
    public boolean checkInternet () {
        HttpUrl googleUrl = HttpUrl.parse("http://www.google.com");
        Request request = new Request.Builder().url(googleUrl).build();
        try {
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            return false;
        }

        //final URLConnection conn = url.openConnection();
        return true;
    }
}
