package com.example.myapplicationtest1.utils;

public class Urls {
    public static String token = null;
    public static final String identifyUser() {
        return "user/identifyUser";
    }
    public static final String getUser() {
        return "user/getUser?userId=" + Utils.getUserId();
    }
    public static final String getChapterDetail(int chapterId) {
        return "chapter/getChapterDetailsByChapter?chapterId=" + chapterId;
    }
    public static final String getCard(int cardId) {
        return "card/getCard?cardId=" + cardId;
    }
    public static final String getAllOwnCard() {
        return "ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId();
    }
    public static final String getAllChapters() {
        return "chapter/getAllChapters";
    }
}
