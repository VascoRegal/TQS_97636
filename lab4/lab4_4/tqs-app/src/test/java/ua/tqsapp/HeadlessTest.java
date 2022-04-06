// Generated by Selenium IDE
package ua.tqsapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;	

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class HeadlessTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new HtmlUnitDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testdemo1() {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(1920, 1007));
    driver.findElement(By.name("fromPort")).click();
    {
      WebElement dropdown = driver.findElement(By.name("fromPort"));
      dropdown.findElement(By.xpath("//option[. = 'Portland']")).click();
    }
    {
      WebElement element = driver.findElement(By.name("fromPort"));
      String value = element.getAttribute("value");
      String locator = String.format("option[@value='%s']", value);
      String selectedText = element.findElement(By.xpath(locator)).getText();
      assertEquals(selectedText, "Portland");
    }
    driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(4)")).click();
    driver.findElement(By.name("toPort")).click();
    {
      WebElement dropdown = driver.findElement(By.name("toPort"));
      dropdown.findElement(By.xpath("//option[. = 'London']")).click();
    }
    {
      WebElement element = driver.findElement(By.name("toPort"));
      String value = element.getAttribute("value");
      String locator = String.format("option[@value='%s']", value);
      String selectedText = element.findElement(By.xpath(locator)).getText();
      assertEquals(selectedText, "London");
    }
    driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertEquals(driver.findElement(By.cssSelector("h3")).getText(), "Flights from Portland to London:");
    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Your flight from TLV to SFO has been reserved.");
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).sendKeys("Vasco");
    {
      String value = driver.findElement(By.id("inputName")).getAttribute("value");
      assertEquals(value, "Vasco");
    }
    driver.findElement(By.id("address")).sendKeys("123 ");
    driver.findElement(By.id("city")).click();
    driver.findElement(By.id("city")).sendKeys("Teste");
    driver.findElement(By.id("state")).sendKeys("Lobao");
    driver.findElement(By.id("zipCode")).sendKeys("1234");
    driver.findElement(By.id("creditCardNumber")).click();
    driver.findElement(By.id("creditCardNumber")).sendKeys("1235215321412");
    driver.findElement(By.id("nameOnCard")).click();
    driver.findElement(By.id("nameOnCard")).sendKeys("Vregal");
    driver.findElement(By.cssSelector(".checkbox")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(1)")).click();
    assertEquals(driver.getTitle(), "BlazeDemo Confirmation");
  }


}
