package com.example.accessingdatamysql.service;

import java.sql.Timestamp;
import com.example.accessingdatamysql.entity.Activity;

import java.util.List;

public interface ActivityService {
        Activity getOneActivity(Integer activityId);

        String addNewActivity(String type, String activityName, String activityImg, String activityDescription,
                              Timestamp start);

        String updateActivity(Integer ActivityId, String type, String activityName, String activityImg,
                              String activityDescription, Timestamp start);

        List<Activity> getAllActivities();

        String deleteActivities(List<Integer> ActivityIds);

        String deleteAll();

        List<Activity> deleteActivity(Integer activityId);
}
