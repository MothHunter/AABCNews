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
    final String API_KEY = "51edc3af6f714021a6d970a3a2ba44ec";

    public enum CATEGORY {business,entertainment,general,health,science,sports,technology}
// don't mix it with souce Param
    public enum LANGUAGE {ar, de, en, es, fr, he, it, nl, no, pt, ru, se, ud, zh} // language parameter

    public enum ENDPOINT {everything, topheadlines}

    public enum SORT_BY {relevancy, popularity, publishedAt}
    public enum COUNTRY {ae,ar,at,au,be,bg,br,ca,ch,cn,co,cu,cz,de,eg,fr,gb,gr,hk,hu,id,ie,il,
        in,it,jp,kr,lt,lv,ma,mx,my,ng,nl,no,nz,ph,pl,pt,ro,rs,ru,sa,se,sg,si,sk,th,tr,tw,ua,us,ve,za}

    public NewsResponse requestTopHeadlines(COUNTRY country){
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + "top-headlines").newBuilder();
        urlBuilder.addQueryParameter("q", "corona");
        urlBuilder.addQueryParameter("country", COUNTRY.at.toString()); // name des Parameteres
        urlBuilder.addQueryParameter("apiKey", API_KEY); // die Zeichen ? & = macht add Parameters automatisch
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            String responseJson = response.body().string();
            NewsResponse ns = deserialization(responseJson);// ich befülle das Objekt mit das return von desearlize
            //response.body().string(); // die übertragenen Daten in Response- das Json Objekt aus den
            // Body der Response und macht daraus ein string
            //String responseJson = response.body().string(); // hier bekommt das Objekt ein Name
            System.out.println(responseJson);
            return ns;
        }catch (IOException e) {
            return null;
            }
        }
    public NewsResponse requestEverything(LANGUAGE language){
        language.name();
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + "everything").newBuilder();
        urlBuilder.addQueryParameter("q", "bitcoin");
        urlBuilder.addQueryParameter("language", LANGUAGE.de.toString());
        urlBuilder.addQueryParameter("apiKey", API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            String responseJson = response.body().string();
            NewsResponse ns = deserialization(responseJson);// ich befülle das Objekt mit das return von desearlize
            //response.body().string(); // die übertragenen Daten in Response- das Json Objekt aus den
            // Body der Response und macht daraus ein string
            //String responseJson = response.body().string(); // hier bekommt das Objekt ein Name
            return ns;
        }catch (IOException e) {
        return null;
    }
    }

        public NewsResponse deserialization (String json) {
                Gson gson = new Gson();
        NewsResponse object = gson.fromJson(json,NewsResponse.class);
        return object;
        }


}

