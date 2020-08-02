package com.example.accessingdatamysql.service;

import java.sql.Timestamp;
import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.ListRequest;

import java.util.List;

public interface ActivityService {

        // 使用ID获取一个活动数据
        Activity getOneActivity(Integer activityId);

        // 添加一个新的活动
        Activity addNewActivity(Activity newActivity);

        // 更新一个活动
        Activity updateActivity(Activity updateActivity);

        // 获取指定页数的Activity
        List<Activity> ListPage(ListRequest listRequest);

        // 获取所有活动
        List<Activity> getAllActivities();

        // 删除活动
        String deleteActivities(List<Integer> ActivityIds);

        // 删除所有活动
        String deleteAll();

        // 删除活动
        List<Activity> deleteActivity(Integer activityId);
}
