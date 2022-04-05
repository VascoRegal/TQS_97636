package ua.tqsapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends WebPage {

    private static String PAGE_URL = "https://blazedemo.com/";

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    public WelcomePage(WebDriver driver, boolean reload) {
        super(driver, PAGE_URL);
    }

    @FindBy(name="fromPort")
    private WebElement from;

    @FindBy(name="toPort")
    private WebElement to;

    @FindBy(className="btn-primary")
    private WebElement find_bttn;

    public void setFromPort(String from) {
        this.from.click();
        this.from.findElement(By.xpath("//option[. = '" +  from + "']")).click();
    }

    public void setToPort(String to) {
        this.to.click();
        this.to.findElement(By.xpath("//option[. = '" +  to + "']")).click();
    }

    public String getFromPort() {
        String val, loc;
        val= this.from.getAttribute("value");
        loc = String.format("option[@value='%s']", val);
        return this.from.findElement(By.xpath(loc)).getText();
    }

    public String getToPort() {
        String val, loc;
        val= this.to.getAttribute("value");
        loc = String.format("option[@value='%s']", val);
        return this.to.findElement(By.xpath(loc)).getText();
    }

    public void findFlights() {
        this.find_bttn.click();
    }
}
