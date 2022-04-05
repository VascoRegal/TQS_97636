package ua.tqsapp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class WebPage {
    private WebDriver webDriver;

    public WebPage(WebDriver driver) {
        this.webDriver = driver;
        PageFactory.initElements(this.webDriver, this);
    }

    public WebPage(WebDriver driver, String url) {
        this.webDriver = driver;
        this.webDriver.get(url);
        PageFactory.initElements(this.webDriver, this);
    }

    public String getTitle() {
        return this.webDriver.getTitle();
    }
}
