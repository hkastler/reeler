package com.hkstlr.reeler.weblogger.entities;

public class TagStat implements java.io.Serializable {

    private static final long serialVersionUID = 1142064841813545198L;

    private String name;

    private int count;
    
    private int intensity;

    public TagStat() {
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

    public String toString() {
        StringBuilder str = new StringBuilder("{");

        str.append("name=" + name + " " + "count=" + count);
        str.append('}');

        return (str.toString());
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
