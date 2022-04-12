package ua.tqsapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;

public class BlazedemoSteps {
    private WebDriver driver = new FirefoxDriver();

    @Given("I am on the BlazeDemo Homepage")
    public void homepage() {
        this.driver.get("https://blazedemo.com/");
    }

    @When("I choose departure {string}")
    public void departure(String l) {
        driver.findElement(By.name("fromPort")).click();
        {
        WebElement dropdown = driver.findElement(By.name("fromPort"));
        dropdown.findElement(By.xpath("//option[. = '" + l + "']")).click();
        }
    }

    @Then("departure is {string}")
    public void departureis(String l) {
        {
            WebElement element = driver.findElement(By.name("fromPort"));
            String value = element.getAttribute("value");
            String locator = String.format("option[@value='%s']", value);
            String selectedText = element.findElement(By.xpath(locator)).getText();
            assertEquals(selectedText, l);
        }
    }

    @When("I choose destination {string}")
    public void destination(String l) {
        driver.findElement(By.name("fromPort")).click();
        {
        WebElement dropdown = driver.findElement(By.name("toPort"));
        dropdown.findElement(By.xpath("//option[. = '" + l + "']")).click();
        }
    }

    @Then("destination is {string}")
    public void destinationis(String l) {
        {
            WebElement element = driver.findElement(By.name("toPort"));
            String value = element.getAttribute("value");
            String locator = String.format("option[@value='%s']", value);
            String selectedText = element.findElement(By.xpath(locator)).getText();
            assertEquals(selectedText, l);
        }
    }

    @When("I find flights")
    public void find() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("page title is {string}")
    public void page_title_is(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(driver.getTitle(), string);
    }

    @When("I choose flight {int}")
    public void chooseflight(int pos) {
        driver.findElement(By.cssSelector("tr:nth-child("+ pos +") .btn")).click();
    }

    @When("I input name {string}")
    public void inputName(String s) {
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys(s);
    }

    @Then("name is {string}")
    public void nameis(String s) {
        {
            String value = driver.findElement(By.id("inputName")).getAttribute("value");
            assertEquals(value, s);
        }
    }

    @When("I input address {string}")
    public void inputAddress(String a) {
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(a);
    }

    @Then("address is {string}")
    public void addressis(String a) {
        {
            String value = driver.findElement(By.id("address")).getAttribute("value");
            assertEquals(value, a);
        }
    }

    @After
    public void quite() {
        this.driver.quit();
    }
}
