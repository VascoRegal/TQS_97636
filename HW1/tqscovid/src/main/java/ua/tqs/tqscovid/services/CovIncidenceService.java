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
    
    private IExternalAPIAdapter externalAPIAdapter;

    public CovIncidenceService(IExternalAPIAdapter externalAPIAdapter) {
        this.externalAPIAdapter = externalAPIAdapter;
    }

    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        return this.externalAPIAdapter.getAllStats();
    }

    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException {
        return this.externalAPIAdapter.getCountries();

    };
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        return this.externalAPIAdapter.getStatsByCountry(country);
    };

    public DailyStats getStatsByDay(String date, String country) throws URISyntaxException, ParseException, IOException {
        LocalDate ldate;
        try {
            ldate = LocalDate.parse(date);
        } catch(Exception e) {
            return new DailyStats(null, null, null, null, null);
        }

        return this.externalAPIAdapter.getStatsByDay(ldate, country);
    };
 
}
