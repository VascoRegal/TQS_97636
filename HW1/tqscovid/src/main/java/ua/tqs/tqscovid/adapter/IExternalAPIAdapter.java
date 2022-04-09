package ua.tqs.tqscovid.adapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.parser.ParseException;

import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;

public interface IExternalAPIAdapter {
    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException;
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException;
    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException;
    public DailyStats getStatsByDay(LocalDate date, String country) throws URISyntaxException, ParseException, IOException;
}
