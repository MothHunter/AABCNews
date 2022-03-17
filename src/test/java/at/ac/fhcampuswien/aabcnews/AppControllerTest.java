package at.ac.fhcampuswien.aabcnews;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.util.List;

public class AppControllerTest {


    @Test
    public void getArticlesTest(){
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("setArticles");
        } catch (Exception e) {
            Assertions.fail("Method setArticles not found!");
        }
    }

    @Test public void getAllNewsBitcoin(){ AppController obj = new AppController();
        try{ Method m = AppController.class.getMethod("getAllNewsBitcoin"); } catch(Exception e) {Assertions.fail("Method setArticles not found!");}}

    @Test
    private List generateMocklist(){
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("generateMocklist");
        } catch (Exception e) {
            Assertions.fail("Method generateMocklist not found!");
        }
        return null;
    }

    @Test
    public List getTopHeadlinesAustria(){
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getTopHeadlinesAustria");
        } catch (Exception e) {
            Assertions.fail("Method getTopHeadlinesAustria not found!");
        }
        return null;
    }

    }

