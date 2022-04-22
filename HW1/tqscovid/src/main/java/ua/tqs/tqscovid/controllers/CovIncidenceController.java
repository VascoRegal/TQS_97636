package ua.tqs.tqscovid.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import ua.tqs.tqscovid.models.CacheStats;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.services.CovIncidenceService;

import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class CovIncidenceController {

    private CovIncidenceService covIncidenceService;

    public CovIncidenceController(CovIncidenceService service) {
        this.covIncidenceService = service;
    }


    @Operation(summary = "Get all Coutries")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Country List", 
        content = @Content(mediaType = "application/json"))
    //@ApiResponse(responseCode = "404", description = "Country not found", 
    //    content = @Content)
    })
    @GetMapping("/countries")
    public List<Country> countries() throws URISyntaxException, ParseException, IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CONTROLLER> GET {0}", "/countries");
        return this.covIncidenceService.getCountries();
    }


    @Operation(summary = "Get all stats")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "All stats", 
        content = @Content(mediaType = "application/json"))
    //@ApiResponse(responseCode = "404", description = "Country not found", 
    //    content = @Content)
    })
    @GetMapping("/stats")
    public List<DailyStats> stats() throws ParseException, IOException, URISyntaxException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CONTROLLER> GET {0}", "/stats");
        return this.covIncidenceService.getAllStats();
    }

    @Operation(summary = "Get all stats by country")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Country's stats", 
        content = @Content(mediaType = "application/json"))
    //@ApiResponse(responseCode = "404", description = "Country not found", 
    //    content = @Content)
    })
    @GetMapping("/stats/country")
    public List<DailyStats> countrystats(@RequestParam String country) throws ParseException, IOException, URISyntaxException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CONTROLLER> GET {0}", "/stats/country?country=" + country);
        return this.covIncidenceService.getStatsByCountry(country);
    }

    @Operation(summary = "Get stats on day (YYYY-MM-DD)")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Between date stat list", 
        content = @Content(mediaType = "application/json"))
    //@ApiResponse(responseCode = "404", description = "Country not found", 
    //    content = @Content)
    })
    @GetMapping("/stats/day")
    public List<DailyStats> dailystats(@RequestParam String day, String country) throws URISyntaxException, ParseException, IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CONTROLLER> GET {0}", "/stats/day?day=" + day + "&country=" + country);
        return this.covIncidenceService.getStatsByDay(day, country);
    }

    @Operation(summary = "Get caching info")
    @GetMapping("/cache")
    public CacheStats cacheStats() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CONTROLLER> GET {0}", "/cache");
        return this.covIncidenceService.getCacheStats();
    }
}
