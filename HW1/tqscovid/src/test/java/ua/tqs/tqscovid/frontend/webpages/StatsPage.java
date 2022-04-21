package ua.tqs.tqscovid.frontend.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StatsPage {
    private WebDriver webDriver;
    private String BASE_URL = "http://localhost/webapp/cache.html";

    public StatsPage(WebDriver driver) {
        this.webDriver = driver;
        this.webDriver.get(BASE_URL);
    }

    @FindBy(id="totalDeaths")
    private WebElement totalDeaths;

    @FindBy(id="totalCases")
    private WebElement totalCases;

    @FindBy(id="activeCases")
    private WebElement activeCases;

    public String getTotalDeaths() {
        return this.totalDeaths.getText();
    }

    public String getTotalCases() {
        return this.totalCases.getText();
    }

    public String getActiveCases() {
        return this.activeCases.toString();
    }
}
