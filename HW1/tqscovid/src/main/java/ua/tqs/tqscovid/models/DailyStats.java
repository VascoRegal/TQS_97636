package ua.tqs.tqscovid.models;

import java.time.LocalDate;


public class DailyStats {
    private LocalDate day;
    private Country country;
    private Cases cases;
    private Deaths deaths;
    private Tests tests;

    public DailyStats() {}

    public DailyStats(LocalDate day, Country country, Cases cases, Deaths deaths, Tests tests) {
        this.day = day;
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.tests = tests;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Cases getCases() {
        return this.cases;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public Deaths getDeaths() {
        return this.deaths;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    public Tests getTests() {
        return this.tests;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        
        DailyStats other = (DailyStats) o;
        return ( (this.getCountry().getName().equals(other.getCountry().getName())) && (this.getDay().toString().equals(other.getDay().toString()))  );
    }
}
