package at.ac.fhcampuswien.aabcnews.downloader;

import at.ac.fhcampuswien.aabcnews.NewsApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Class is needed for exercise 4 - ignore for exercise 2 solution
public class ParallelDownloader extends Downloader{

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls)  throws NewsApiException {
        // TODO implement download function using multiple threads
        // Hint: use ExecutorService with Callables

        List<Callable<String>> downloads = new ArrayList<>();

        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("available processors: " + numberOfProcessors);
        ExecutorService pool = Executors.newFixedThreadPool(numberOfProcessors);
        /*
        1.) erstelle zwei Variablen : before after download und hole von ihnen den timestamp.
         */
        long getTimeBeforeDownload = getTime();

        /*

        befor wir in die forSchleife gehen möchte ich die Millisekunden wissen.
         */
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            Callable<String> download = () -> saveUrl2File(url);
            downloads.add((download));
        }
/*
es wurde gedownloaded und im download hinzugefügt. es ist abgeschlossen.
Ich möchte wieder die Millisekunden wissen.
 */
        long getAfterDownload = getTime();//in Milliseconds


        /*
        nun subtrahiere ich die timestamps 5 Sekunden-3 Sekunden und gebe aus, dass es 2 Sekunden gedauert hat.
         */
        System.out.println("The milliseconds used for the download were" + (getAfterDownload-getTimeBeforeDownload));
        List<Future<String>> completedDownloads = null;
        List<String> fileNames = new ArrayList<>();
        try {
            completedDownloads = pool.invokeAll(downloads);

            for (int i = 0; i < completedDownloads.size(); i++) {
               fileNames.add(completedDownloads.get(i).get());
            }
        } catch (InterruptedException e) {
            throw new NewsApiException("A download thread was interrupted!", NewsApiException.EXCEPTION_CODE.none, e);
        } catch (ExecutionException e) {
            throw new NewsApiException("The result auf an interrupted download was accessed; " +
                    "this is probably not the first exception you get about this", NewsApiException.EXCEPTION_CODE.none, e);
        }

        //Todo: comment this out for time measurements
     /*   System.out.println("printing list of files:");
        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println(fileNames.get(i));
        }*/

        return fileNames.size();
    }
}
