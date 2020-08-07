package com.example.myapplicationtest1;

public enum CardStatusEnum {
    HP(R.id.cardHP, R.id.cardHPEnhanced),
    ATK(R.id.cardATK, R.id.cardATKEnhanced),
    DEF(R.id.cardDEF, R.id.cardDEFEnhanced),
    ATKRange(R.id.cardATKRange, R.id.cardATKRangeEnhanced),
    CD(R.id.cardCD, R.id.cardCDEnhanced),
    SPD(R.id.cardSpeed, R.id.cardSpeedEnhanced);

    public final int valTextViewId, upgPtTextViewId;
    CardStatusEnum(int valTextViewId, int upgPtTextViewId) {
        this.valTextViewId = valTextViewId;
        this.upgPtTextViewId = upgPtTextViewId;
    }
}
