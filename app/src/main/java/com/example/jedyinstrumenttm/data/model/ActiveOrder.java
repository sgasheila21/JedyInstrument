package com.example.jedyinstrumenttm.data.model;

public class ActiveOrder {
    public int activeOrderID, userID, instrumentID, instrumentQuantity;

    public ActiveOrder(int activeOrderID, int userID, int instrumentID, int instrumentQuantity) {
        this.activeOrderID = activeOrderID;
        this.userID = userID;
        this.instrumentID = instrumentID;
        this.instrumentQuantity = instrumentQuantity;
    }
}