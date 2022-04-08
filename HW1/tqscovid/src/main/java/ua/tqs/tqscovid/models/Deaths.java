package ua.tqs.tqscovid.models;

public class Deaths {
    private int balance, per_million, total;

    public Deaths() {}

    public Deaths(int balance,int per_million, int total) {
        this.balance = balance;
        this.per_million = per_million;
        this.total = total;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
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


