package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.ActivityDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
// import java.io.Console;
import java.util.*;

@Repository
public class ActivityDaoImpl implements ActivityDao {
    @Autowired
    private ActivityRepository ActivityRepository;
    @Autowired
    private ActivityDetailsRepository ActivityDetailsRepository;

    @Override
    public Activity getOneActivity(Integer ActivityId) {
        Activity Activity = ActivityRepository.getOne(ActivityId);
        Optional<ActivityDetails> ActivityDetails = ActivityDetailsRepository
                .findActivityDetailsByActivityIdEquals(ActivityId);
        ActivityDetails.ifPresent(Activity::setActivityDetails);
        return Activity;
    }

    public String addNewActivity(String type, String activityName, String activityImg, String activityDescription,
            Timestamp start) {

        Activity Activity = new Activity(type, activityName);
        // System.out.println("new Activity has an Id of : " + n.getActivityId());
        ActivityRepository.save(Activity);
        ActivityDetails ActivityDetails = new ActivityDetails(Activity.getActivityId(), activityImg,
                activityDescription, start);
        ActivityDetailsRepository.save(ActivityDetails);
        return "Saved Activity";
    }

    public String updateActivity(Integer activityId, String type, String activityName, String activityImg,
            String activityDescription, Timestamp start) {

        Activity Activity = ActivityRepository.getOne(activityId);
        // System.out.println("old Activity has an Id of : " + n.getActivityId());
        Activity.setActivity(type, activityName);

        ActivityRepository.updateActivityStatus(Activity, activityId);

        Optional<ActivityDetails> optActivityDetails = ActivityDetailsRepository
                .findActivityDetailsByActivityIdEquals(activityId);
        ActivityDetails activityDetails = new ActivityDetails(activityId, "", "", null);

        if (optActivityDetails.isPresent()) {
            System.out.println("Activity Exists");
            activityDetails = optActivityDetails.get();
        } else {
            System.out.println("Activity doesn't exist");
        }

        activityDetails.setActivityDescription(activityDescription);
        activityDetails.setActivityImg(activityImg);
        activityDetails.setStart(start);
        ActivityDetailsRepository.save(activityDetails);
        return "modified Activity: " + Activity.getActivityName();

    }

    public List<Activity> getAllActivities() {
        List<Activity> Activities = ActivityRepository.findAll();
        for (int i = 0; i < Activities.size(); i++) {
            Activity Activity = Activities.get(i);
            Optional<ActivityDetails> ActivityDetails = ActivityDetailsRepository
                    .findActivityDetailsByActivityIdEquals(Activity.getActivityId());
            ActivityDetails.ifPresent(Activity::setActivityDetails);
            Activities.set(i, Activity);
        }
        return Activities;
    }

    public String deleteActivities(List<Integer> ActivityIds) {
        for (int i = 0; i < ActivityIds.size(); i++) {
            ActivityRepository.deleteById(ActivityIds.get(i));
            ActivityDetailsRepository.deleteActivityDetailsByActivityIdEquals(ActivityIds.get(i));
        }
        return "Deleted Activities by id";
    }

    public String deleteAll() {
        ActivityRepository.deleteAll();
        ActivityDetailsRepository.deleteAll();
        return "Deleted All Activities";
    }

    public List<Activity> deleteActivity(Integer activityId) {
        ActivityRepository.deleteById(activityId);
        ActivityDetailsRepository.deleteActivityDetailsByActivityIdEquals(activityId);
        return getAllActivities();
    }

}
