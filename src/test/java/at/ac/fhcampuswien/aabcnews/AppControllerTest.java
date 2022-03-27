package at.ac.fhcampuswien.aabcnews;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AppControllerTest {

    @Test
    public void setArticlesTest() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("setArticles", List.class);
        } catch (Exception e) {
            fail("Method setArticles not found or does not take correct parameters!");
        }
    }

    @Test
    public void getArticleCountTest_1() {

        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getArticleCount");
            assertEquals (0,obj.getArticleCount(), "result of articleCount must not be negative!");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }
    }

    //TODO: (A1) write getArticleCountTest_2 to check for expected return value after setting the article
    //      List to null.
    //      -> commit & push


    //TODO: (A3) write getArticleCountTest_3 to check for expected return value after setting the article to a new List.
    //      -> commit & push
    //      (we should have done both of those before writing the functionality in the getArticleCount method)

    @Test
    public void getAllNewsBitcoinTest() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getAllNewsBitcoin");
        } catch (Exception e) {
            fail("Method getAllNewsBitcoin not found!");
        }
    }


    @Test
    public void getTopHeadlinesAustriaTest_1() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getTopHeadlinesAustria");
        } catch (Exception e) {
            fail("Method getTopHeadlinesAustria not found!");
        }
    }

    //TODO: (B1) write test for getTopHeadlinesAustria: after setting the list of articles to null through setArticles
    //      it should return an empty list
    //      -> commit & push

    //TODO: (B3) write test for getTopHeadlinesAustria: after setting a new list of articles through setArticles
    //      it should return the entire list
    //      -> commit & push



    //TODO: (C1) write test for getAllNewsBitcoin: after setting the list of articles to null through setArticles
    //      it should return an empty list
    //      -> commit & push

    //TODO: (C3) write test for getAllNewsBitcoin: after setting a new list of articles through setArticles
    //      it should return the entire list
    //      -> commit & push



    @Test
    public void filterListTest_1() {

        AppController appController = new AppController();
        try {
            Method m = AppController.class.getMethod("filterList", String.class, List.class);
        } catch (Exception e) {
            fail("Method filterList not found or expects incorrect parameters");
        }
    }

    // we may have to break up this test for filterList into more, smaller tests
    @Test
    public void filterListTest_2() {
        AppController appController = new AppController();
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            List<Article> actualList = appController.filterList("this", articles);
            if(actualList == null) {
                fail("filterList returned null!");
            } else {
                assertEquals(2, actualList.size());
                assertEquals("A", actualList.get(0).getAuthor());
                assertEquals("B", actualList.get(1).getAuthor());
                assertEquals("This is a1", actualList.get(0).getTitle());
                assertEquals("This is b1", actualList.get(1).getTitle());
            }
        } catch (Exception e) {
            fail("Method filterList not found");
        }
    }

}

