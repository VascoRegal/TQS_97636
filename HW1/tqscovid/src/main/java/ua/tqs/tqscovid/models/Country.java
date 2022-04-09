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

    @Override
    public boolean equals(Object other) {
        if (other.getClass() == Country.class) { return (this.name.equals(((Country) other).name)); };
        return false;
    }
}
