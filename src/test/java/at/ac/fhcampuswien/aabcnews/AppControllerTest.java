package at.ac.fhcampuswien.aabcnews;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AppControllerTest {

    AppController appController;//referenz


    @BeforeEach
    public void initialize() {
        appController = new AppController();// great an instance of AppController class
    }


    @Test
    @DisplayName("Testing if method setArticles exists and has correct signature")//ob überhaupt exsisiert
    public void setArticlesTest() {
        try {
            Method m = AppController.class.getMethod("setArticles", List.class);//getMethod eine methode welche andere mathoden als Rückgabe gibt
        } catch (Exception e) {
            fail("Method setArticles not found or does not take correct parameters!");
        }
    }

    @Test
    @DisplayName("Testing if getArticleCount exists and returns non-negative value")
    public void getArticleCountTest_1() {
        try {
            Method m = AppController.class.getMethod("getArticleCount");
            assertTrue(appController.getArticleCount() >= 0, "result of articleCount must not be negative!");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }
    }

    @Test
    @DisplayName("Testing if getArticleCount returns 0 when list of articles is null")
    public void getArticleCountTest_2() {
        try {
            appController.setArticles(null);
            assertEquals(0, appController.getArticleCount(),
                    "Return value was not 0 when articles was set to null");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }

    }

    @Test
    @DisplayName("Testing if getArticleCount returns correct number of articles")
    public void getArticleCountTest_3() {
        try {
            List<Article> testList = new ArrayList<>();
            testList.add(new Article("Dickens", "Great Expectations"));
            testList.add(new Article("Jules Verne", "20.000 leagues under the Seas"));
            testList.add(new Article("Dostoievski", " The Idiot"));
            appController.setArticles(testList);
            assertEquals(testList.size(), appController.getArticleCount(), "The number of articles in the list" +
                    "was not correct");
        } catch (Exception e) {
            fail("Method getArticleCount not found");
        }
    }

    @Test
    @DisplayName("Testing if method getAllNewsBitcoin exists")
    public void getAllNewsBitcoinTest() {
        try {
            Method m = AppController.class.getMethod("getAllNewsBitcoin");
        } catch (Exception e) {
            fail("Method getAllNewsBitcoin not found!");
        }
    }


    @Test
    @DisplayName("Testing if method getTopHeadlinesAustria exists")
    public void getTopHeadlinesAustriaTest_1() {
        try {
            Method m = AppController.class.getMethod("getTopHeadlinesAustria");
        } catch (Exception e) {
            fail("Method getTopHeadlinesAustria not found!");
        }
    }

    @Test
    @DisplayName("Testing if getTopHeadlinesAustria has non-null return value when articles list is null")
    public void getTopHeadlinesAustriaTest_2() {
        try{
            appController.setArticles(null); //object from type AppController which can be used
            assertNotEquals(null,appController.getTopHeadlinesAustria(), //null =no object saved in ref.
                    "getTopHeadlinesAustria should never return null!");
        } catch (Exception e) {
            fail("Method getTopHeadlinesAustria not found!");
        }
    }


    @Test
    @DisplayName("Testing if getTopHeadlinesAustria returns list of size 0 when list of articles is null")
    public void getTopHeadlinesAustriaTest_3() {
        try {
            appController.setArticles(null);
            assertEquals(0, appController.getTopHeadlinesAustria().size(),
                    "the return value of the list should be of the length 0.");

        } catch (Exception e) {
            fail("Method getTopHeadlinesAustria not found!");
        }


    }

    @Test
    @DisplayName("Testing if getTopHeadlinesAustria returns the complete list of articles")
    public void getTopHeadlinesAustriaTest_4() {
        try {
            List<Article> testList = new ArrayList<>();
            testList.add(new Article("a", "f"));
            testList.add(new Article("w", "k"));
            appController.setArticles(testList);
            assertTrue(testList.equals(appController.getTopHeadlinesAustria()),
                    "return List is not the same the set list");

        } catch (Exception e) {
            fail("Not found!");
        }
    }



    @Test
    @DisplayName("Testing if getAllNewsBitcoin has non-null return value when articles list is null")
    public void getAllNewsBitcoinTest_1() {
        try {
            appController.setArticles(null);
            assertNotEquals(null, appController.getAllNewsBitcoin(),
                    " getAllNewsBitcoin should never return null");

        } catch (Exception e) {
            fail("Not found!");
        }
    }


    @Test
    @DisplayName("Testing if getAllNewsBitcoin returns the expected articles")
    public void getAllNewsBitcoinTest_2() {
        try {
            List<Article> testList = new ArrayList<>();
            testList.add(new Article("a", "f"));
            testList.add(new Article("w", "k"));
            testList.add(new Article("bitpanda", "bitcoin is reaching its peak!"));
            List<Article> expectedList = new ArrayList<>();
            expectedList.add(testList.get(2));

            appController.setArticles(testList);

            assertEquals(expectedList, appController.getAllNewsBitcoin(),
                    "The returned list was not correct!");


        } catch (Exception e) {
            fail("Not found!");

        }

    }

    @Test
    @DisplayName("Testing if filterList returns the expected number of articles")
    public void filterListTest_1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            List<Article> actualList = AppController.filterList("this", articles);
            if (actualList == null) {
                fail("filterList returned null!");
            } else {
                assertEquals(2, actualList.size(), "return list is not of expected size");

            }
        } catch (Exception e) {
            fail("Method filterList not found");
        }

    }

    @Test
    @DisplayName("Testing if filterList returns an empty list for a query not contained in articles list")
    public void filterListTest_2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            List<Article> actualList = AppController.filterList("java", articles);
            if (actualList == null) {
                fail("filterList returned null!");
            } else {
                assertEquals(0, actualList.size(), "return list is not of expected size");

            }
        } catch (Exception e) {
            fail("Method filterList not found");
        }

    }

    @Test
    @DisplayName("Testing if filterList correctly returns the article matching the query")
    public void filterListTest_3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("A", "This is a1"));
        articles.add(new Article("B", "This is b1"));
        articles.add(new Article("C", "These are c1"));
        articles.add(new Article("D", "These are d1"));
        try {
            List<Article> actualList = AppController.filterList("c1", articles);
            if (actualList == null) {
                fail("filterList returned null!");
            } else {
                assertEquals(articles.get(2).toString(), actualList.get(0).toString(),
                        "filterList not return expected artikel");

            }
        } catch (Exception e) {
            fail("Method filterList not found");
        }
    }
}