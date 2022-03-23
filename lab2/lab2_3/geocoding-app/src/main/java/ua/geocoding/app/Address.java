package ua.geocoding.app;

public class Address {
    private String road, state, cirty, zio, houseNumber;

    public Address(String road, String state, String citry, String zio, String houseNumber) {
        this.road = road;
        this.state = state;
        this.cirty = citry;
        this.zio = zio;
        this.houseNumber = houseNumber;
    }

    public String getRoad() {
        return this.road;
    }


    public String getState() {
        return this.state;
    }


    public String getCitry() {
        return this.cirty;
    }


    public String getZio() {
        return this.zio;
    }


    public String getHouseNumber() {
        return this.houseNumber;
    }
}
