package at.ac.fhcampuswien.aabcnews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NewsApiTest {
    @Test
    public void getAllNewsTest_1() {
        NewsApi.getInstance().requestAllNews("Discworld", NewsApi.Language.en);
    }
}
