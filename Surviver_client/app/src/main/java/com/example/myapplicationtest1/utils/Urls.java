package com.example.myapplicationtest1.utils;

public class Urls {
    public static String token = null;
    public static String identifyUser() {
        return "user/identifyUser";
    }
    public static String getUser() {
        return "user/getUser?userId=" + Utils.getUserId();
    }
    public static String getChapterDetail(int chapterId) {
        return "chapter/getChapterDetailsByChapter?chapterId=" + chapterId;
    }
    public static String getCard(int cardId) {
        return "card/getCard?cardId=" + cardId;
    }
    public static String getAllOwnCard() {
        return "ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId();
    }
    public static String getAllChapters() {
        return "chapter/getAllChapters";
    }
    public static String uploadCrashReport() {
        return "crashReports/add";
    }
}
