package at.ac.fhcampuswien.aabcnews;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class NewsApi {
    public enum Category {business, entertainment, general, health, science, sports, technology}

    public enum Country {
        ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr,
        hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs,
        ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za
    }

    public enum Language {ar, de, en, es, fr, he, it, nl, no, pt, ru, se, ud, zh}

    public enum SortBy {relevancy, popularity, publishedAt}

    private static final String root = "https://newsapi.org/v2/";
    private static final String apiKey = "0eb47479ee9b40829604c68ff2adb858";
    private static final String topHeadlinesEndpoint = "top-headlines";
    private static final String everythingEndpoint = "everything";
    private static NewsApi instance;
    private OkHttpClient client;

    private NewsApi() {
        client = new OkHttpClient();
    }

    public static NewsApi getInstance() {
        if (instance == null) {
            instance = new NewsApi();
        }
        return instance;
    }

    public NewsResponse requestAllNews(String query, Language language) throws NewsApiException {
        NewsAPIUrlBuilder urlBuilder;
        try {
            urlBuilder = new NewsAPIUrlBuilder(root, everythingEndpoint, apiKey);
        } catch (NullPointerException e) {
            throw new NewsApiException("Url Builder instantiation returned null",
                    NewsApiException.EXCEPTION_CODE.requestConstructionFailed, e);
        }

        urlBuilder.addQ(query);
        // urlBuilder.addQueryParameter("language", language.toString());
        // urlBuilder.addLanguage(language.toString())
        return handleRequest(urlBuilder);
    }

    public NewsResponse requestTopHeadlines(String country) throws NewsApiException {
        NewsAPIUrlBuilder urlBuilder;
        try {
            urlBuilder = new NewsAPIUrlBuilder(root, topHeadlinesEndpoint, apiKey);
        } catch (NullPointerException e) {
            throw new NewsApiException("Url Builder instantiation returned null",
                    NewsApiException.EXCEPTION_CODE.requestConstructionFailed, e);
        }

        urlBuilder.addCountry(country);

        return handleRequest(urlBuilder);
    }

    private NewsResponse handleRequest(NewsAPIUrlBuilder urlBuilder) throws NewsApiException {
        //urlBuilder.pageSize("100");

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Gson gson = new Gson();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new NewsApiException("Received error response: "
                        + (response.body() != null ? response.body().string() : "no further information available!"),
                        NewsApiException.EXCEPTION_CODE.badResponse);
            } else if (response.body() == null) {
                throw new NewsApiException("Response had code \"ok\", but contained no body. Can this even happen?",
                        NewsApiException.EXCEPTION_CODE.badResponse);
            }
            String responseString = response.body().string();
            return gson.fromJson(responseString, NewsResponse.class);

        } catch (IOException e) {
            throw new NewsApiException("Call execution for news request failed!", checkInternet() ?
                    NewsApiException.EXCEPTION_CODE.webserviceUnreachable :
                    NewsApiException.EXCEPTION_CODE.noConnection, e);
        } catch (NullPointerException e) {
            throw new NewsApiException("Response contained a body but it could not be converted to a string. " +
                    "This should not happen!", NewsApiException.EXCEPTION_CODE.badResponse, e);
        } catch (JsonSyntaxException e) {
            throw new NewsApiException("Webservice returned json object of unexpected structure or bad syntax." +
                    "This should not happen!", NewsApiException.EXCEPTION_CODE.badResponse, e);
        }

    }

    //Todo: richtig implementieren
    public boolean checkInternet() {
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

    public boolean downloadText(Article article) throws NewsApiException {
        if (article.getUrl() == null) {
            throw new NewsApiException("The article object did not contain a URL",
                    NewsApiException.EXCEPTION_CODE.badDownloadURL);
        }
        Request request = new Request.Builder().url(article.getUrl()).build();
        String text;

        try {
            Response response = client.newCall(request).execute();
            text = response.body().string();
        } catch (IOException e) {
            throw new NewsApiException("The download attempt failed", NewsApiException.EXCEPTION_CODE.badDownloadURL, e);
        } catch (NullPointerException e) {
            throw new NewsApiException("Body for text download was empty or could not be processed!",
                    NewsApiException.EXCEPTION_CODE.badResponse, e);
        }

        String[] fragments = text.split("</p>");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fragments.length - 1; i++) {
            int stringIndex;
            if (fragments[i].contains("<p>")) {
                String[] split = fragments[i].split("<p>");
                if (split.length > 1) {
                    fragments[i] = split[1];
                }
            } else if ((stringIndex = fragments[i].indexOf("<p ")) >= 0) {
                fragments[i] = fragments[i].substring(fragments[i].indexOf(">", stringIndex) + 1);
            }
            fragments[i] = fragments[i].replaceAll("<br>", System.lineSeparator());
            fragments[i] = fragments[i].strip();
            builder.append(fragments[i]);
            builder.append(System.lineSeparator());
        }
        text = builder.toString();
        builder.setLength(0);

        String title = article.getTitle();
        int nameLength = Math.min(title.length(), 50);
        for (int i = 0; i < nameLength; i++) {
            if (Character.isAlphabetic(title.charAt(i))) {
                builder.append(Character.toLowerCase(title.charAt(i)));
            }
        }
        builder.append(".txt");
        String fileName = builder.toString();

        File file = new File("downloads/" + fileName);
        try (FileWriter writer = new FileWriter("downloads/" + fileName)) {
            writer.write(text);
        } catch (IOException e) {
            throw new NewsApiException("File creation failed!", NewsApiException.EXCEPTION_CODE.fileCreationFailed, e);
        }
        return true;
    }
}
