package com.example.stampsaver;

public class Model {
    public String stitle;
    public String slink;
    public String sstamp;
    public String sthumb;


    public Model(String stitle, String slink, String sstamp, String sthumb) {
        this.stitle = stitle;
        this.slink = slink;
        this.sstamp = sstamp;
        this.sthumb = sthumb;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public String getSlink() {
        return slink;
    }

    public void setSlink(String slink) {
        this.slink = slink;
    }

    public String getSstamp() {
        return sstamp;
    }

    public void setSstamp(String sstamp) {
        this.sstamp = sstamp;
    }

    public String getSthumb() {
        return sthumb;
    }

    public void setSthumb(String sthumb) {
        this.sthumb = sthumb;
    }

}
