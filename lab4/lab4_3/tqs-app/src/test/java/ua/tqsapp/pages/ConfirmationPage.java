package ua.tqsapp.pages;

import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends WebPage {
    private static String PAGE_URL = "https://blazedemo.com/confirmation.php";


    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmationPage(WebDriver driver, boolean reload) {
        super(driver, PAGE_URL);
    }
}
