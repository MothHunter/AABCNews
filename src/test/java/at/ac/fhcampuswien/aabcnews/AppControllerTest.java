package at.ac.fhcampuswien.aabcnews;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AppControllerTest {

    AppController appController;


    @BeforeEach
    public void initialize() {
        appController = new AppController();
    }


    @Test
    public void setArticlesTest() {
        try {
            Method m = AppController.class.getMethod("setArticles", List.class);
        } catch (Exception e) {
            fail("Method setArticles not found or does not take correct parameters!");
        }
    }

    @Test
    public void getArticleCountTest_1() {
        try {
            Method m = AppController.class.getMethod("getArticleCount");
            assertEquals(0, appController.getArticleCount(), "result of articleCount must not be negative!");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }
    }

    @Test
    public void getArticleCountTest_2() {
        try {
            appController.setArticles(null);
            assertEquals(0, appController.getArticleCount(),
                    "Return value was not 0 when Articles was set to null");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }

    }

    //TODO: (A3) write getArticleCountTest_3 to check for expected return value after setting the article to a new List.
    //      -> commit & push
    //      (we should have done both of those before writing the functionality in the getArticleCount method)

    @Test
    public void getAllNewsBitcoinTest() {
        try {
            Method m = AppController.class.getMethod("getAllNewsBitcoin");
        } catch (Exception e) {
            fail("Method getAllNewsBitcoin not found!");
        }
    }


    @Test
    public void getTopHeadlinesAustriaTest_1() {
        try {
            Method m = AppController.class.getMethod("getTopHeadlinesAustria");
        } catch (Exception e) {
            fail("Method getTopHeadlinesAustria not found!");
        }
    }

    //TODO: (B1) write test for getTopHeadlinesAustria: after setting the list of articles to null through setArticles
    //      it should return an empty list
    //      -> commit & push

    @Test
    public void getTopHeadlinesAustriaTest_2() {
        try{
            appController.setArticles(null); //object from type AppController which can be used
            assertNotEquals(null,appController.getTopHeadlinesAustria(), //null =no object saved in ref.
                    "getTopHeadlinesAustria should never return null!");
        }
        catch (Exception e){
            fail("Method getTopHeadlinesAustria not found!");
        }
    }


    //ob wir null zurückbekommen sondern ob die liste die wir testen länge 0 ... .size
    //assertEquals...
    // size...message...die rückgb list should be of the length. 0
    @Test
    public void getTopHeadlinesAustriaTest_3() {
        try{
            appController.setArticles(null); //object from type AppController which can be used
            assertEquals(0,appController.getTopHeadlinesAustria().size(),
                    "the return value of the list should be of the length 0.");

        }
        catch (Exception e){
            fail("Method getTopHeadlinesAustria not found!");
        }


    }
    @Test
    public void getTopHeadlinesAustriaTest_4() {
        try {
         List<Article> testList  = new ArrayList<>();
         testList.add(new Article("a","f"));
         testList.add(new Article("w","k"));
            appController.setArticles(testList);
            assertTrue(testList.equals(appController.getTopHeadlinesAustria()),
                    "return List is not the same the set list");

        }
        catch (Exception e){
            fail("Not found!");
        }
    }
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
        try {
            Method m = AppController.class.getMethod("filterList", String.class, List.class);
        } catch (Exception e) {
            fail("Method filterList not found or expects incorrect parameters");
        }
    }

    // we may have to break up this test for filterList into more, smaller tests
    @Test
    public void filterListTest_2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            List<Article> actualList = appController.filterList("this", articles); // es wird die gefilterte Liste
            // in actualList gespeichert
            if (actualList == null) {
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

