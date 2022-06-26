package at.ac.fhcampuswien.aabcnews.downloader;

import at.ac.fhcampuswien.aabcnews.NewsApiException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

// Class is needed for exercise 4 - ignore for exercise 2 solution
public abstract class Downloader {

    public static final String HTML_EXTENSION = ".html";
    public static final String DIRECTORY_DOWNLOAD = "./download/";

    public abstract int process(List<String> urls)  throws NewsApiException;

    public String saveUrl2File(String urlString) {
        InputStream is = null;
        OutputStream os = null;
        String fileName = "";
        if (urlString.contains("www.npr.org") || urlString.contains("www.dlh.net")) { // there seems to be a problem with downloading from npr
            return "";
        }
        try {
            URL url4download = new URL(urlString);  // Convert string to URL
            is = url4download.openStream();
            fileName = urlString.substring(urlString.lastIndexOf('/') + 1); // extract filename

            if (fileName.isEmpty()) {
                fileName = url4download.getHost() + HTML_EXTENSION; // if no filename could be extracted use the URL host and .html extension
            }
            // added max length for file name to avoid file system errors
            if (fileName.length() > 100) {
                fileName = fileName.substring(0, 89) + (fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "");
            }
            fileName = fileName.replaceAll("[!?=&#*%]",""); // replace illegal symbols in file name

            //System.out.println("next: " + fileName); // for debugging

            os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName);   // write to /download/<filename>

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {   // read byte for byte and write it to file
                os.write(b, 0, length);
            }
        } catch (IOException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                if(is != null)
                    is.close();
                if(os != null)
                    os.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}