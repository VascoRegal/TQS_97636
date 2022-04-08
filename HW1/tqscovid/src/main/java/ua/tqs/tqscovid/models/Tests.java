package ua.tqs.tqscovid.models;

public class Tests {
    private int per_million, total;

    public Tests() {}

    public Tests(int per_million, int total) {
        this.per_million = per_million;
        this.total = total;
    }

    public void setPer_million(int per_million) {
        this.per_million = per_million;
    }

    public int getPer_million() {
        return this.per_million;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }
}


