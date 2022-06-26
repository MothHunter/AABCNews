package at.ac.fhcampuswien.aabcnews.downloader;

import java.util.List;

// Class is needed for exercise 4 - ignore for exercise 2 solution
public class SequentialDownloader extends Downloader {

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls){
        int count = 0;
        for (String url : urls) {
            String fileName = saveUrl2File(url);
            if(fileName != null)
                count++;
            //System.out.println(count);
        }
        return count;
    }
}
