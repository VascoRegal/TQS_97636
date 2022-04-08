package ua.tqs.tqscovid.adapter;

import java.util.Date;
import java.util.List;

import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;

public interface IExternalAPIAdapter {
    public List<Country> getCountries();
    public List<DailyStats> getStatsByCountry(String country);
    public List<DailyStats> getAllStats();
    public DailyStats getStatsByDay(Date date);
}
