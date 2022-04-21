package ua.tqs.tqscovid.frontend.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CachePage {
    private WebDriver webDriver;
    private String BASE_URL = "http://localhost/webapp/cache.html";

    public CachePage(WebDriver driver) {
        this.webDriver = driver;
        this.webDriver.get(BASE_URL);
    }

    @FindBy(id="total")
    private WebElement total;

    @FindBy(id="cached")
    private WebElement cached;

    @FindBy(id="avgNoCache")
    private WebElement avgNoCache;

    @FindBy(id="avgCache")
    private WebElement avgCache;

    public String getTotal() {
        return this.total.getText();
    }

    public String getCached() {
        return this.cached.getText();
    }

    public String getAvgNoCache() {
        return this.avgNoCache.toString();
    }

    public String getAvgCache() {
        return this.avgCache.toString();
    }
}
