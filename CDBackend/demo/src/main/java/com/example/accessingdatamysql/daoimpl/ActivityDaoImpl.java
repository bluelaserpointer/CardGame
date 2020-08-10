package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.ActivityDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ActivityDaoImpl implements ActivityDao {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityDetailsRepository activityDetailsRepository;

    @Override
    public Activity getOneActivity(Integer ActivityId) {
        Activity Activity = activityRepository.getOne(ActivityId);
        Optional<ActivityDetails> ActivityDetails = activityDetailsRepository
                .findActivityDetailsByActivityIdEquals(ActivityId);
        ActivityDetails.ifPresent(Activity::setActivityDetails);
        return Activity;
    }

    public Activity addNewActivity(Activity newActivity) {

        Activity Activity = new Activity(newActivity.getType(), newActivity.getActivityName(), newActivity.getStart());
        // System.out.println("new Activity has an Id of : " + n.getActivityId());
        activityRepository.save(Activity);
        ActivityDetails ActivityDetails = new ActivityDetails(Activity.getActivityId(),
                newActivity.getActivityDetails().getActivityImg(),
                newActivity.getActivityDetails().getActivityDescription());
        activityDetailsRepository.save(ActivityDetails);
        return Activity;
    }

    public Activity updateActivity(Activity updateActivity) {

        Activity Activity = activityRepository.getOne(updateActivity.getActivityId());

        // System.out.println("old Activity has an Id of : " + n.getActivityId());
        Activity.setActivity(updateActivity.getType(), updateActivity.getActivityName(), updateActivity.getStart());

        activityRepository.updateActivityStatus(Activity, updateActivity.getActivityId());

        Optional<ActivityDetails> optActivityDetails = activityDetailsRepository
                .findActivityDetailsByActivityIdEquals(updateActivity.getActivityId());
        ActivityDetails activityDetails = new ActivityDetails(updateActivity.getActivityId(), "", "");

        if (optActivityDetails.isPresent()) {
            System.out.println("Activity Exists");
            activityDetails = optActivityDetails.get();
        } else {
            System.out.println("Activity doesn't exist");
        }

        activityDetails.setActivityDescription(updateActivity.getActivityDetails().getActivityDescription());
        activityDetails.setActivityImg(updateActivity.getActivityDetails().getActivityImg());
        activityDetailsRepository.save(activityDetails);
        Activity.setActivityDetails(activityDetails);
        return Activity;

    }

    public List<Activity> getAllActivities() {
        List<Activity> Activities = activityRepository.findAll();
        for (int i = 0; i < Activities.size(); i++) {
            Activity Activity = Activities.get(i);
            Optional<ActivityDetails> ActivityDetails = activityDetailsRepository
                    .findActivityDetailsByActivityIdEquals(Activity.getActivityId());
            ActivityDetails.ifPresent(Activity::setActivityDetails);
            Activities.set(i, Activity);
        }
        return Activities;
    }

    public String deleteActivities(List<Integer> ActivityIds) {
        for (Integer activityId : ActivityIds) {
            activityRepository.deleteById(activityId);
            activityDetailsRepository.deleteActivityDetailsByActivityIdEquals(activityId);
        }
        return "Deleted Activities by id";
    }

    public String deleteAll() {
        activityRepository.deleteAll();
        activityDetailsRepository.deleteAll();
        return "Deleted All Activities";
    }

    public List<Activity> deleteActivity(Integer activityId) {
        activityRepository.deleteById(activityId);
        activityDetailsRepository.deleteActivityDetailsByActivityIdEquals(activityId);
        return getAllActivities();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, activityRepository, activity -> {
            activityDetailsRepository
                    .findActivityDetailsByActivityIdEquals(activity.getActivityId())
                    .ifPresent(activity::setActivityDetails);
            return activity;
        });
    }

}
