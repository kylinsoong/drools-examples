package com.sample.domain.cep;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FireAlarm {
    
    private boolean activated;
    
    private Date timestamp;
    
    private String desc;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");

    @Override
    public String toString() {
        String base = "FireAlarm [activated=" + activated;
        if(null != timestamp) {
            base += ", timestamp=" + format.format(timestamp);
        }
        if(null != desc) {
            base += ", desc=" + desc ;
        }
        base += "]";
        return base ;
    }
    
}
