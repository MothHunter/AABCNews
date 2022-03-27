package at.ac.fhcampuswien.aabcnews;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals("Rincewind the Wizzard", testArticle.getAuthor());
    }

    @Test
    public void getTitleTest() {
        assertEquals("The From of running", testArticle.getTitle());
    }

    @Test
    public void articleToStringTest() {
        assertEquals("Title: The From of running, Author: Rincewind the Wizzard",
                                testArticle.toString());
    }
}
