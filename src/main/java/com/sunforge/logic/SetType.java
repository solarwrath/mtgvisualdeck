package com.sunforge.logic;

public enum SetType {
    W17,
    M19,
    DAR,
    RIX,
    XLN,
    HOU,
    AKH,
    AER,
    KLD;

    public SetType getSetFromString(String givenString){
        return SetType.valueOf(givenString);
    }
}
