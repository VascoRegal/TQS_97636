
package ua.tqs.tqscovid.frontend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

/*
public class WebpagetestTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testdemo1() {
    driver.get("http://20.53.251.194/webapp/");
    //driver.manage().window().setSize(new Dimension(1920, 1007));
    driver.findElement(By.id("linear")).click();
    System.out.println(driver.findElement(By.id("linear")).getText());
    driver.findElement(By.id("linear")).click();
    driver.findElement(By.id("totalDeaths")).click();
    driver.findElement(By.id("totalCases")).click();
    driver.findElement(By.id("activeCases")).click();
    driver.findElement(By.id("casesLinear")).click();
    driver.findElement(By.id("deathsLinear")).click();
    driver.findElement(By.id("countrySelect")).click();
    {
      WebElement dropdown = driver.findElement(By.id("countrySelect"));
      dropdown.findElement(By.xpath("//option[. = 'Portugal']")).click();
    }
    driver.findElement(By.cssSelector("option:nth-child(169)")).click();
    driver.findElement(By.cssSelector("#filter > .fas")).click();
    driver.findElement(By.id("linear")).click();
    driver.findElement(By.id("sDate")).click();
    driver.findElement(By.id("eDate")).click();
    driver.findElement(By.id("filter")).click();
    driver.findElement(By.id("linear")).click();
    driver.findElement(By.id("linear")).click();
    driver.findElement(By.id("linear")).click();
    driver.findElement(By.id("testsLinear")).click();
    driver.findElement(By.cssSelector(".nav-item:nth-child(2) p")).click();
    driver.findElement(By.id("total")).click();
    driver.findElement(By.cssSelector(".col-lg-3:nth-child(1) .card-title")).click();
    driver.findElement(By.id("cached")).click();
    driver.findElement(By.cssSelector(".col-lg-3:nth-child(2) .card-title")).click();
    driver.findElement(By.id("avgNoCache")).click();
    driver.findElement(By.cssSelector(".col-lg-3:nth-child(3) .card-title")).click();
    driver.findElement(By.id("avgCache")).click();
    driver.findElement(By.cssSelector(".col-lg-3:nth-child(4) .card-title")).click();
  }
}
*/
