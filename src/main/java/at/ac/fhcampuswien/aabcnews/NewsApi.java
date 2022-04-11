package at.ac.fhcampuswien.aabcnews;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NewsApi {
    OkHttpClient client;
    final String BASE_URL = "https://newsapi.org/v2/";
    final String API_KEY; // muss noch eine erstellen auf News Api
    public enum category{title, description, content} //searchIn parameter
    public enum language{ar,de,en,es,fr,he,it,nl,no,pt,ru,se,ud,zh} // language parameter
    public enum endpoint{everything, topheadlines}
    public enum sortBy {relevancy, popularity, publishedAt}



    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url( "https://newsapi.org/v2/everything?")
                .build();
        client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
        public String getTopHeadlines() {

        }
        /*
        public void whenGetRequestWithQueryParameter_thenCorrect()
  throws IOException {

    HttpUrl.Builder urlBuilder
      = HttpUrl.parse(BASE_URL + "/ex/bars").newBuilder();
    urlBuilder.addQueryParameter("id", "1");

    String url = urlBuilder.build().toString();

    Request request = new Request.Builder()
      .url(url)
      .build();
         */

    }
}
