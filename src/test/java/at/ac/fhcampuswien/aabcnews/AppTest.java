package at.ac.fhcampuswien.aabcnews;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void printWelcomeTest(){
        String welcomeMassage="*****************************"+System.lineSeparator()+
                              "  *  welcome to AABCNews  *  "+System.lineSeparator()+
                              "*****************************";
    assertEquals(welcomeMassage,App.printwelcome);
    }
}
