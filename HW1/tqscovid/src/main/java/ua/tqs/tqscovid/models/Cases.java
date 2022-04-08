package ua.tqs.tqscovid.models;

public class Cases {
    private int balance, active, critical, recovered, per_million, total;

    public Cases() {}

    public Cases(int balance, int active, int critical, int recovered, int per_million, int total) {
        this.balance = balance;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.per_million = per_million;
        this.total = total;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return this.active;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getCritical() {
        return this.critical;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getRecovered() {
        return this.recovered;
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


