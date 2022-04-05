package ua.tqsapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import ua.tqsapp.pages.ConfirmationPage;
import ua.tqsapp.pages.PurchasePage;
import ua.tqsapp.pages.ReservePage;
import ua.tqsapp.pages.WelcomePage;

@ExtendWith(SeleniumJupiter.class)
public class DockerTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setup() {
        this.webDriver = new FirefoxDriver();
    }

    @Test
    public void blazeDemo_Testing() {
        WelcomePage welcomePage = new WelcomePage(this.webDriver, true);

        welcomePage.setFromPort("Portland");
        assertEquals("Portland", welcomePage.getFromPort());

        welcomePage.setToPort("London");
        assertEquals("London", welcomePage.getToPort());

        welcomePage.findFlights();


        ReservePage reservePage = new ReservePage(this.webDriver);

        assertEquals("Flights from Portland to London:", reservePage.getHeaderText());
        
        reservePage.clickNthButton(3);

        PurchasePage purchasePage = new PurchasePage(this.webDriver);

        assertEquals("Your flight from TLV to SFO has been reserved.", purchasePage.getHeaderText());

        purchasePage.input_name("Vasco");
        assertEquals("Vasco", purchasePage.get_input_name());

        purchasePage.input_city("Teste");
        assertEquals("Teste", purchasePage.get_input_city());

        purchasePage.input_state("Lobao");
        purchasePage.input_zip("1234");

        purchasePage.rememberMe();
        purchasePage.submit();

        ConfirmationPage confirmationPage = new ConfirmationPage(this.webDriver);

        assertEquals("BlazeDemo Confirmation", confirmationPage.getTitle());
    }


    @AfterEach
    public void close() {
        this.webDriver.quit();
    }
}
