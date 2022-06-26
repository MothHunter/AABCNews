package at.ac.fhcampuswien.aabcnews;

import java.util.List;

public class NewsAPIUrlBuilder{
/*
1.) Variablen erstellen
 */
    private final String root;
    private final String endpoint;
    private final String apiKey;
/*
final ist unveränderbar.
 */
    private  String country;
    private  String q;
    private  String language;
    private  String category;
    private  String sources;
    private  String pageSize;
    private  String page;

    /*
    2.) Konstruktor erstellen und Objekt zu bauen.
     */

    public NewsAPIUrlBuilder (String root, String endpoint, String apiKey){
        this.root = root; //Root wird vom Programm übergeben
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }

        /*
    3.) Methoden für optionale Variablen.
     */

    public NewsAPIUrlBuilder addCountry(String country) {
        this.country = country;
        return this;
    }

    public NewsAPIUrlBuilder addQ(String q){
        this.q = q;
        return this;
    }


    public NewsAPIUrlBuilder addCategory(String category){
        this.category = category;
        return this;
    }
    public NewsAPIUrlBuilder addPage( String page){
        this.page =page;
        return this;
    }
    public NewsAPIUrlBuilder addPageSize( String pageSize){
        this.pageSize =pageSize;
        return this;
    }
    public NewsAPIUrlBuilder addSource( String sources){
        this.sources =sources;
        return this;
    }
    public NewsAPIUrlBuilder addLanguage( String language){
        this.language =language;
        return this;
    }
    /*
    4. hier liefern wir Strings zurück
     */

    public String build(){
        // Build our base url and check if another parameters exited or not?
        String url = this.root + this.endpoint + "?" +  "apikey=" + this.apiKey;

        if(this.q != null && this.q != ""){
            url = url + "&" + "q=" + this.q; //bei query nutzen wir & zeichen bei URL
        }

        if(this.country != null && this.country != ""){
            url = url + "&" + "country=" + this.country;
        }

        if(this.category != null && this.category != ""){
            url = url + "&" + "category=" + this.category;
        }

        if(this.language != null && this.language != ""){
            url = url + "&" + "language=" + this.language;
        }
        if(this.sources != null && this.sources != ""){
            url = url + "&" + "sources=" + this.sources;
        }
        if(this.pageSize != null && this.pageSize != ""){
            url = url + "&" + "pageSize=" + this.pageSize;
        }
        if(this.page != null && this.page != ""){
            url = url + "&" + "page=" + this.page;
        }

        System.out.println(url);
        return url;
    }


}
