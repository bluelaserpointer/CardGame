package com.example.myapplicationtest1;

public enum CardStatusEnum {
    HP("HP", R.id.cardHP, R.id.cardHPEnhanced),
    ATK("Attack", R.id.cardATK, R.id.cardATKEnhanced),
    DEF("Defence", R.id.cardDEF, R.id.cardDEFEnhanced),
    ATKRange("AttackRange", R.id.cardATKRange, R.id.cardATKRangeEnhanced),
    CD("CD", R.id.cardCD, R.id.cardCDEnhanced),
    SPD("Speed", R.id.cardSpeed, R.id.cardSpeedEnhanced);

    public final String str;
    public final int valTextViewId, upgPtTextViewId;
    CardStatusEnum(String str, int valTextViewId, int upgPtTextViewId) {
        this.str = str;
        this.valTextViewId = valTextViewId;
        this.upgPtTextViewId = upgPtTextViewId;
    }
}
