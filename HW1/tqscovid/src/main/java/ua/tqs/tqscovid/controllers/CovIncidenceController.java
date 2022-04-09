package ua.tqs.tqscovid.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.services.CovIncidenceService;

@RestController
@RequestMapping("/api/")
public class CovIncidenceController {

    private CovIncidenceService covIncidenceService;

    public CovIncidenceController(CovIncidenceService service) {
        this.covIncidenceService = service;
    }

    @GetMapping("/countries")
    public List<Country> countries() throws URISyntaxException, ParseException, IOException {
        return this.covIncidenceService.getCountries();
    }

    @GetMapping("/stats")
    public List<DailyStats> stats() throws ParseException, IOException, URISyntaxException {
        return this.covIncidenceService.getAllStats();
    }

    @GetMapping("/stats/country")
    public List<DailyStats> countrystats(@RequestParam String country) throws ParseException, IOException, URISyntaxException {
        return this.covIncidenceService.getStatsByCountry(country);
    }

    @GetMapping("/stats/day")
    public DailyStats dailystats(@RequestParam String day, String country) throws URISyntaxException, ParseException, IOException {
        return this.covIncidenceService.getStatsByDay(day, country);
    }
}
