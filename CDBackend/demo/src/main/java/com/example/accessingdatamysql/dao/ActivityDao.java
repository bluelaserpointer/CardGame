package com.example.accessingdatamysql.dao;

import java.sql.Timestamp;
// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface ActivityDao {
        Activity getOneActivity(Integer activityId);

        String addNewActivity(String type, String activityName, String activityImg, String activityDescription,
                        Timestamp start);

        String updateActivity(Integer activityId, String type, String activityName, String activityImg,
                        String activityDescription, Timestamp start);

        List<Activity> getAllActivities();

        String deleteActivities(List<Integer> ActivityIds);

        String deleteAll();

        List<Activity> deleteActivity(Integer activityId);
}
