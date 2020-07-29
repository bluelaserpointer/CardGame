package com.example.myapplicationtest1.utils;

public class Urls {
    public static String token = null;
    /////////////
    //Users
    /////////////
    public static String identifyUser() {
        return "user/login";
    }
    public static String getUser() {
        return "user/getUser?userId=" + Utils.getUserId();
    }
    /////////////
    //Cards
    /////////////
    public static String getCard(int cardId) {
        return "card/getCard?cardId=" + cardId;
    }
    public static String getAllCards() {
        return "card/getAllCards";
    }
    public static String getAllOwnCard() {
        return "ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId();
    }
    /////////////
    //Chapters
    /////////////
    public static String getAllChapters() {
        return "chapter/getAllChapters";
    }
    public static String getChapterDetail(int chapterId) {
        return "chapter/getChapterDetailsByChapter?chapterId=" + chapterId;
    }
    /////////////
    //CrashReports
    /////////////
    public static String uploadCrashReport() {
        return "crashReports/add";
    }
    //TODO: 以下还未使用，先写在这
    /////////////
    //Records
    /////////////
    public static String getOnlineCount() {
        return "record/getOnlineCount";
    }
    public static String getAllPveRecords() {
        return "record/pveRecord/getAllPveRecordsByUser?userId=" + Utils.getUserId();
    }
    /////////////
    //Activities
    /////////////
    public static String getAllActivities() {
        return "activity/getAllActivities";
    }
    public static String getActivity(int activityId) {
        return "activity/getActivity?activityId=" + activityId;
    }
}
