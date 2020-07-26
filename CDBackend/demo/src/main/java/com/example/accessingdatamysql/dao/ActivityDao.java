package com.example.accessingdatamysql.dao;

// import java.sql.Timestamp;
// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface ActivityDao {
        Activity getOneActivity(Integer activityId);

        Activity addNewActivity(Activity newActivity);

        Activity updateActivity(Activity updateActivity);

        List<Activity> getAllActivities();

        String deleteActivities(List<Integer> ActivityIds);

        String deleteAll();

        List<Activity> deleteActivity(Integer activityId);
}
