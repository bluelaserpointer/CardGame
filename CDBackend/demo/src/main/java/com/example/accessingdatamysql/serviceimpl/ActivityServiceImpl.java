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

    public Activity addNewActivity(Activity addNewActivity) {
        return ActivityDao.addNewActivity(addNewActivity);
    }

    public Activity updateActivity(Activity updateActivity) {
        return ActivityDao.updateActivity(updateActivity);
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

    @Override
    public List<Activity> ListPage(ListRequest listRequest) {
        return ActivityDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
