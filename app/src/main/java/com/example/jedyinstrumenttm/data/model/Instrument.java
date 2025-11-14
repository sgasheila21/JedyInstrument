package com.example.jedyinstrumenttm.data.model;

public class Instrument {
    public int instrumentID;
    public double instrumentRating, instrumentPrice;
    public String instrumentName, instrumentImagePath, instrumentDescription;

    public Instrument(int instrumentID, String instrumentName, double instrumentRating, double instrumentPrice, String instrumentImagePath, String instrumentDescription) {
        this.instrumentID = instrumentID;
        this.instrumentName = instrumentName;
        this.instrumentRating = instrumentRating;
        this.instrumentPrice = instrumentPrice;
        this.instrumentImagePath = instrumentImagePath;
        this.instrumentDescription = instrumentDescription;
    }
}
