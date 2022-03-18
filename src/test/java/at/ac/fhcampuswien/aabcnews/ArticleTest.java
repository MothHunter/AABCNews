package at.ac.fhcampuswien.aabcnews;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArticleTest {
    Article testArticle;

    @BeforeEach
    public void init() {
        testArticle = new Article("Rincewind the Wizzard", "The From of running");
    }

    @Test
    public void getAuthorTest() {
        Assertions.assertEquals("Rincewind the Wizzard", testArticle.getAuthor());
    }

    @Test
    public void getTitleTest() {
        Assertions.assertEquals("The From of running", testArticle.getTitle());
    }

    @Test
    public void articleToStringTest() {
        Assertions.assertEquals("Title: The From of running, Author: Rincewind the Wizzard",
                                testArticle.toString());
    }
}
