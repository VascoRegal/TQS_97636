
package ua.tqs.tqscovid.frontend;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import ua.tqs.tqscovid.adapter.APISportsAdapter;
import ua.tqs.tqscovid.cache.CacheServiceImpl;
import ua.tqs.tqscovid.frontend.webpages.CachePage;
import ua.tqs.tqscovid.frontend.webpages.StatsPage;
import ua.tqs.tqscovid.http.CovIncidenceHttpClient;
import ua.tqs.tqscovid.models.CacheStats;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.services.CovIncidenceService;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/*
@ExtendWith(SeleniumJupiter.class)
public class WebpagetestTest {
  private WebDriver driver;
  private CovIncidenceService service;

  @BeforeEach
  public void setUp() {
    this.driver = new FirefoxDriver();
    this.service = new CovIncidenceService(new APISportsAdapter(new CovIncidenceHttpClient()), new CacheServiceImpl());
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void test() throws ParseException, IOException, URISyntaxException {

    StatsPage statsPage = new StatsPage(driver);
    List<DailyStats> allstats = service.getAllStats();
    DailyStats today = allstats.get(allstats.size());
    assertTrue(statsPage.getTotalCases() == String.valueOf(today.getCases().getTotal()));
    assertTrue(statsPage.getActiveCases() == String.valueOf(today.getCases().getActive()));
    assertTrue(statsPage.getTotalDeaths() == String.valueOf(today.getDeaths().getTotal()));


    CachePage page = new CachePage(this.driver);
    CacheStats cacheStats = service.getCacheStats();
    assertTrue(page.getTotal() == String.valueOf(cacheStats.getTotal()));
    assertTrue(page.getCached() == String.valueOf(cacheStats.getCached()));
    assertTrue(page.getAvgCache() == String.valueOf(cacheStats.getCacheTime()));
    assertTrue(page.getAvgNoCache() == String.valueOf(cacheStats.getNoCacheTIme()));
  }
}
*/
