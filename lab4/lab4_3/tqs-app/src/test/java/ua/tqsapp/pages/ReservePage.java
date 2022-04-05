package ua.tqsapp.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class ReservePage extends WebPage {
    private static String PAGE_URL = "https://blazedemo.com/reserve.php";

    @FindBys(@FindBy(className="btn-small"))
    private List<WebElement> bttns;

    @FindBy(css="h3")
    private WebElement h3;

    public ReservePage(WebDriver driver) {
        super(driver);
    }

    public ReservePage(WebDriver driver, boolean reload) {
        super(driver, PAGE_URL);
    }

    public String getHeaderText() {
        return this.h3.getText();
    }

    public void clickNthButton(int n) {
        bttns.get(n).click();
    }

}
