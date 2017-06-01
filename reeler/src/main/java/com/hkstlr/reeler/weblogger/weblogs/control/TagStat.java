package com.hkstlr.reeler.weblogger.weblogs.control;

import com.hkstlr.reeler.app.control.StringPool;

public class TagStat implements java.io.Serializable {

    private static final long serialVersionUID = 1142064841813545198L;

    private String name;

    private int count;
    
    private int intensity;

    public TagStat() {
        //constructor
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(StringPool.OPEN_CURLY_BRACE);

        str.append("name=").append(name).append(" count=").append(count);
        
        str.append(StringPool.CLOSE_CURLY_BRACE);

        return str.toString();
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
