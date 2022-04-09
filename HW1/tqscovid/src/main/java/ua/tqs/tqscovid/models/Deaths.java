package ua.tqs.tqscovid.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.lang.Nullable;

@JsonInclude(Include.ALWAYS)
public class Deaths {
    @Nullable
    private Integer balance, per_million, total;

    public Deaths() {}

    public Deaths(Integer balance,Integer per_million, Integer total) {
        this.balance = balance;
        this.per_million = per_million;
        this.total = total;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setPer_million(Integer per_million) {
        this.per_million = per_million;
    }

    public Integer getPer_million() {
        return this.per_million;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return this.total;
    }
}


