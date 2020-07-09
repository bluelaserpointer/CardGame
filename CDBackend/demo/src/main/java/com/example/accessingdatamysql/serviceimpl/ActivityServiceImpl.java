package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.*;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao ActivityDao;

    @Override
    public Activity getOneActivity(Integer ActivityId) {
        return ActivityDao.getOneActivity(ActivityId);
    }

    public String addNewActivity(String type, String activityName, String activityImg, String activityDescription,
            Timestamp start) {
        return ActivityDao.addNewActivity(type, activityName, activityImg, activityDescription, start);
    }

    public String updateActivity(Integer ActivityId, String type, String activityName, String activityImg,
            String activityDescription, Timestamp start) {
        return ActivityDao.updateActivity(ActivityId, type, activityName, activityImg, activityDescription, start);
    }

    public List<Activity> getAllActivities() {
        System.out.println("Inside service");
        return ActivityDao.getAllActivities();
    }

    public String deleteActivities(List<Integer> ActivityIds) {
        return ActivityDao.deleteActivities(ActivityIds);
    }

    public String deleteAll() {
        return ActivityDao.deleteAll();
    }

    public List<Activity> deleteActivity(Integer activityId) {
        return ActivityDao.deleteActivity(activityId);
    }
}