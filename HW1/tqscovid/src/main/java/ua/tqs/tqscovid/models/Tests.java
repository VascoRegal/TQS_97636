package ua.tqs.tqscovid.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.lang.Nullable;

@JsonInclude(Include.ALWAYS)
public class Tests {
    @Nullable
    private Integer per_million, total;

    public Tests() {}

    public Tests(Integer per_million, Integer total) {
        this.per_million = per_million;
        this.total = total;
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


