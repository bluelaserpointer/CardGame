package com.example.myapplicationtest1.utils;

public class Urls {
    public static final boolean IS_LOCAL_MODE = true;
    //Edward: http://192.168.175.1:8080/
    //Jun: http://192.168.254.1:8080/
    //Online: http://ec2-35-173-219-114.compute-1.amazonaws.com:8080/
    public static final String URLHead = IS_LOCAL_MODE ? "http://192.168.254.1:8080/" : "http://35.174.18.246:8080/";
    /////////////
    //Users
    /////////////
    public static String login() {
        return "user/login";
    }
    public static String getUser() {
        return "user/getUser?userId=" + Cache.userId;
    }
    public static String register() {
        return "user/register";
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
        return "ownCard/getAllOwnCardsByUserName?userName=" + Cache.userName;
    }
    public static String drawCard(int chi, int mat, int eng) {
        return "mechanism/drawCard?userName=" + Cache.userName + "&chi=" + chi + "&mat=" + mat + "&eng=" + eng;
    }
    public static String redistributeUpgrades() {
        return "ownCard/redistributeUpgrades";
    }
    /////////////
    //Chapters
    /////////////
    public static String getAllChapters() {
        return "chapter/getAllChapters";
    }
    public static String getChapterPhaseDetails(int chapterId, int phaseId) {
        return "chapter/getChapterDetailsByChapterAndByPhase?chapterId=" + chapterId + "&phaseId=" + phaseId;
    }
    public static String phaseClear(int chapterId, int phaseId, int result) {
        System.out.println("putting |" + MapStringUtil.mapToString(Cache.formation) + "|");
        return "chapter/phaseClear?userName=" + Cache.userName
                + "&chapterId=" + chapterId
                + "&phaseId=" + phaseId
                + "&result=" + result;
    }
    /////////////
    //CrashReports
    /////////////
    public static String uploadCrashReport() {
        return "crashReports/add?userId=" + Cache.userId + "&clientVersion=" + Utils.CLIENT_VERSION;
    }
    /////////////
    //Records
    /////////////
    public static String getOnlineCount() {
        return "record/getOnlineCount";
    }
    public static String getAllPveRecords() {
        return "record/pveRecord/getAllPveRecordsByUser?userId=" + Cache.userId;
    }
    public static String getUserLoginRecords() {
        return "record/userLoginRecord/getUserLoginRecordsByUserId?userId=" + Cache.userId;
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
    //Mails
    /////////////
    public static String getMailBox() {
        return  "user/getMailBox?userName=" + Cache.userName;
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
