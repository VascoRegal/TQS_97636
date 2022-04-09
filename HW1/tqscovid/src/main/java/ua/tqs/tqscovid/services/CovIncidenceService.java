package ua.tqs.tqscovid.services;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import ua.tqs.tqscovid.adapter.IExternalAPIAdapter;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CovIncidenceService {

    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        return null;
    }

    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException {
        return null;

    };
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        return null;
    };

    public DailyStats getStatsByDay(String date, String country) throws URISyntaxException, ParseException, IOException {
        return null;
    };
 
}
