package com.example.myapplicationtest1.utils;

public class Urls {
    public static String token = null;
    /////////////
    //Users
    /////////////
    public static String login() {
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
    public static String drawCard(int chi, int mat, int eng) {
        return "mechanism/drawCard?chi=" + chi + "&mat=" + mat + "&eng=" + eng;
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
    /////////////
    //Records
    /////////////
    public static String getOnlineCount() {
        return "record/getOnlineCount";
    }
    public static String getAllPveRecords() {
        return "record/pveRecord/getAllPveRecordsByUser?userId=" + Utils.getUserId();
    }
    public static String getUserLoginRecords() {
        return "record/userLoginRecord/getUserLoginRecordsByUserId?userId=" + Utils.getUserId();
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
    /////////////
    //Missions
    /////////////
    public static String getAllMissions() {
        return "mission/getAllMissions";
    }
    public static String getMission(int missionId) {
        return "mission/getMission?missionId=" + missionId;
    }
}
