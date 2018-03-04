package com.example.haryono.lowkost;

/**
 * Created by haryono on 3/4/2018.
 */

public class Kostan {
    private String kostanId;
    private String kostanName;
    private int kostanRating;

    public Kostan(){

    }

    public Kostan(String kostanId, String kostanName, int kostanRating) {
        this.kostanId = kostanId;
        this.kostanName = kostanName;
        this.kostanRating = kostanRating;
    }

    public String getKostanId() {
        return kostanId;
    }

    public String getKostanName() {
        return kostanName;
    }

    public int getKostanRating() {
        return kostanRating;
    }
}
