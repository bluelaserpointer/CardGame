package com.example.accessingdatamysql.Schedule;

import com.example.accessingdatamysql.service.OnlineCountRecordService;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataCollectionSchedule {
    @Autowired
    private OnlineCountRecordService onlineCountRecordService;
    @Autowired
    private UserLoginRecordService userLoginRecordService;

    //3.添加定时任务
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate = 30000)
    private void saveCount() {
        onlineCountRecordService.saveCount();
    }

    @Scheduled(fixedRate = 30000)
    private void calculateLogout() {
        userLoginRecordService.calculateLogout();
    }
}
