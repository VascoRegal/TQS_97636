package ua.tqs.tqscovid.models;

public class Country {
    private String name;

    public Country() {}

    public Country(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("[<COUNTRY> name=%s]", this.name);
    }
}
