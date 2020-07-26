package com.example.accessingdatamysql.service;

import java.sql.Timestamp;
import com.example.accessingdatamysql.entity.Activity;

import java.util.List;

public interface ActivityService {
        Activity getOneActivity(Integer activityId);

        Activity addNewActivity(Activity newActivity);

        Activity updateActivity(Activity updateActivity);

        List<Activity> getAllActivities();

        String deleteActivities(List<Integer> ActivityIds);

        String deleteAll();

        List<Activity> deleteActivity(Integer activityId);
}
