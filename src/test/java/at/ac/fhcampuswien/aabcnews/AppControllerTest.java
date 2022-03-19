package at.ac.fhcampuswien.aabcnews;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Method;

public class AppControllerTest {

    @Test
    public void setArticlesTest() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("setArticles", List.class);
        } catch (Exception e) {
            Assertions.fail("Method setArticles not found!");
        }
        try {
            obj.setArticles(new ArrayList<Article>());
        } catch (Exception e) {
            Assertions.fail("setArticles failed to accept parameter from ArrayList type");
        }
    }

    @Test
    public void getArticleCount() {

        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getArticleCount");
        } catch (Exception e) {
            Assertions.fail("Method getArticleCount not found");

        }
        assert (obj.getArticleCount() >= 0);

    }

    @Test
    public void getAllNewsBitcoinTest() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getAllNewsBitcoin");
        } catch (Exception e) {
            Assertions.fail("Method setArticles not found!");
        }
    }


    @Test
    public void getTopHeadlinesAustriaTest() {
        AppController obj = new AppController();
        try {
            Method m = AppController.class.getMethod("getTopHeadlinesAustria");
        } catch (Exception e) {
            Assertions.fail("Method getTopHeadlinesAustria not found!");
        }
    }

    //TODO: write test for filterList method
    @Test
    public void filterListTest() {

        AppController appController = new AppController();
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            Method m = AppController.class.getMethod("filterList", String.class, List.class);
            List<Article> actualList = appController.filterList("this", articles);

            Assertions.assertEquals(2, actualList.size());
            Assertions.assertEquals("A", actualList.get(0).getAuthor());
            Assertions.assertEquals("B", actualList.get(1).getAuthor());
            Assertions.assertEquals("This is a1", actualList.get(0).getTitle());
            Assertions.assertEquals("This is b1", actualList.get(1).getTitle());

        } catch (Exception e) {
            Assertions.fail("Method filterList not found");

        }
    }

}

