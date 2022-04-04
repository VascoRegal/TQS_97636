package ua.tqsapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private WebDriver webDriver;


    @BeforeEach
    public void startDriver() {
        this.webDriver = new FirefoxDriver();
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        webDriver.get(sutUrl);
        String title = webDriver.getTitle();
        System.out.println(String.format("The title of %s is %s", sutUrl, title));

        assertEquals(title, "Hands-On Selenium WebDriver with Java");
    }

    @AfterEach
    public void closeDriver() {
        webDriver.quit();
    }
}
