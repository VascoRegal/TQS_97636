package ua.tqsapp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage extends WebPage {
    private static String PAGE_URL = "https://blazedemo.com/purchase.php";


    @FindBy(id="inputName")
    private WebElement name;

    @FindBy(id="address")
    private WebElement address;

    @FindBy(id="city")
    private WebElement city;

    @FindBy(id="state")
    private WebElement state;

    @FindBy(id="zipCode")
    private WebElement zipCode;

    @FindBy(id="creditCardNumber")
    private WebElement card_number;

    @FindBy(id="nameOnCard")
    private WebElement card_name;

    @FindBy(css=".checkbox")
    private WebElement rememberMe;

    @FindBy(css=".btn-primary")
    private WebElement submit;


    @FindBy(css="h2")
    private WebElement h2;

    public PurchasePage(WebDriver driver) {
        super(driver);
    }

    public PurchasePage(WebDriver driver, boolean reload) {
        super(driver, PAGE_URL);
    }

    public void input_name(String name) {
        this.name.click();
        this.name.sendKeys(name);
    }

    public String get_input_name() {
        return this.name.getAttribute("value");
    }

    public void input_city(String city) {
        this.city.click();
        this.city.sendKeys(city);
    }

    public String get_input_city() {
        return this.city.getAttribute("value");
    }

    public void input_state(String state) {
        this.state.click();
        this.state.sendKeys(state);
    }

    public void input_zip(String zip) {
        this.zipCode.click();
        this.zipCode.sendKeys(zip);
    }

    public void input_card_number(String card_number) {
        this.card_number.click();
        this.card_number.sendKeys(card_number);
    }

    public void rememberMe() {
        this.rememberMe.click();
    }

    public void submit() {
        this.submit.click();
    }

    public String getHeaderText() {
        return this.h2.getText();
    }
}
