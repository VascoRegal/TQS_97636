package ua.tqs.tqscovid.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.lang.Nullable;

@JsonInclude(Include.ALWAYS)
public class Cases {
    @Nullable
    private Integer balance, active, critical, recovered, per_million, total;
    public Cases() {}

    public Cases(Integer balance, Integer active, Integer critical, Integer recovered, Integer per_million, Integer total) {
        this.balance = balance;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.per_million = per_million;
        this.total = total;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getActive() {
        return this.active;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getCritical() {
        return this.critical;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getRecovered() {
        return this.recovered;
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


