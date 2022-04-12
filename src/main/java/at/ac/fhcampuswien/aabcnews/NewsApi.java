package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NewsApi {
    OkHttpClient client;
    final String BASE_URL = "https://newsapi.org/v2/";
    final String API_KEY; // muss noch eine erstellen auf News Api

    public enum CATEGORY {business,entertainment,general,health,science,sports,technology}
// don't mix it with souce Param
    public enum LANGUAGE {ar, de, en, es, fr, he, it, nl, no, pt, ru, se, ud, zh} // language parameter

    public enum ENDPOINT {everything, topheadlines}

    public enum SORT_BY {relevancy, popularity, publishedAt}
    public enum COUNTRY {ae,ar,at,au,be,bg,br,ca,ch,cn,co,cu,cz,de,eg,fr,gb,gr,hk,hu,id,ie,il,
        in,it,jp,kr,lt,lv,ma,mx,my,ng,nl,no,nz,ph,pl,pt,ro,rs,ru,sa,se,sg,si,sk,th,tr,tw,ua,us,ve,za}

    public NewsResponse requestTopHeadlines(COUNTRY country) throws IOException {
        country.name();
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + "top-headlines?").newBuilder();
        urlBuilder.addQueryParameter("q=", "1");
        urlBuilder.addQueryParameter("country", "1");
        urlBuilder.addQueryParameter("API_KEY", "1");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        /*Request request = new Request.Builder()
                .url("https://newsapi.org/v2/everything")
                .build();*/
        client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            NewsResponse ns = deserialization("corona");// ich befülle das Objekt mit das return von desearlize
            response.body().string();
            return ns;
        }
    }
    public NewsResponse requestEverything(LANGUAGE language) throws IOException {
        language.name();
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + "everything?").newBuilder();
        urlBuilder.addQueryParameter("q=", "1");
        urlBuilder.addQueryParameter("de", "1");
        urlBuilder.addQueryParameter("API_KEY", "1");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            NewsResponse ns = deserialization("bitcoin");// ich befülle das Objekt mit das return von desearlize
            response.body().string();
            return ns;
        }
    }

        public NewsResponse deserialization (String qFilter) {
                Gson gson = new Gson();
        NewsResponse object = gson.fromJson(qFilter,NewsResponse.class);
        return object;
        }


}

