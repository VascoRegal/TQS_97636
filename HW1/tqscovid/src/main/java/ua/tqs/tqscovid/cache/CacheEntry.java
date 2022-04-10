package ua.tqs.tqscovid.cache;

import java.util.Date;

public class CacheEntry {
    private Object value;
    private Date ts;

    public CacheEntry(Object val) {
        this.value = val;
        this.ts = new Date();
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object val) {
        this.value = val;
    }

    public Date getTs() {
        return this.ts;
    }

    public void setTs(Date d) {
        this.ts = d;
    }
}
