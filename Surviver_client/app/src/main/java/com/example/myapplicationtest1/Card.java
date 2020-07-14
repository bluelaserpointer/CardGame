package com.example.myapplicationtest1;

public class Card {
    private String mImageUrl;
    private String mCardName;

    public Card(String imageUrl,String cardName){
        mImageUrl = imageUrl;
        mCardName = cardName;
    }

    public  String getImageUrl(){
        return mImageUrl;
    }

    public  String getCardName(){
        return  mCardName;
    }

}
