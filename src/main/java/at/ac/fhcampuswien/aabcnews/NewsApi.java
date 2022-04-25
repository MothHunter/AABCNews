package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NewsApi {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String API_KEY = "2f20ff17ff8049bb825b2274de93bc36";

    public NewsResponse giveAllArticles(String suchWort ){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addPathSegment("everything");
        urlBuilder.addQueryParameter("q",suchWort);
        urlBuilder.addQueryParameter("apiKey",API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String responseString =response.body().string();// string ist methode, respons ist object, body ist methode die gibt uns object von typ responseBody
            System.out.println(responseString);
            Gson gson = new Gson();
            NewsResponse newsResponse= gson.fromJson(responseString,NewsResponse.class);

            return newsResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    public NewsResponse giveGetTopHeadLinesAustria(String country){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addPathSegment("top-headlines");
        urlBuilder.addQueryParameter("country", country);
        urlBuilder.addQueryParameter("apiKey",API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String responseString =response.body().string();// string ist methode, respons ist object, body ist methode die gibt uns object von typ responseBody
            System.out.println(responseString);
            Gson gson = new Gson();
            NewsResponse newsResponse= gson.fromJson(responseString,NewsResponse.class);

            return newsResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }


}
