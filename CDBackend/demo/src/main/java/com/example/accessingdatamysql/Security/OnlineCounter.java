package com.example.accessingdatamysql.Security;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineCounter {

    private final Map<String, Long> userIdStrAndLastActiveTime = new ConcurrentHashMap<>();

    /**
     * 更新用户最后活跃时间，用于在线用户数统计
     * @param userIdStr 用户ID文字列
     */

    public void updateUserLastActiveTime(String userIdStr) {
        //用户ID文字列为key，当前时间+60s为value存入countMap中
        userIdStrAndLastActiveTime.put(userIdStr, System.currentTimeMillis() + 60 * 1000);
    }

    /**
     * 获取当前在线用户数
     * @return 在线用户数
     */
    public Integer getOnlineCount() {
        int onlineCount = 0;
        //获取countMap的迭代器
        for (Map.Entry<String, Long> entry : userIdStrAndLastActiveTime.entrySet()) {
            if (entry.getValue() > System.currentTimeMillis()) {
                //过期时间大于当前时间则没有过期
                ++onlineCount;
            } else {
                userIdStrAndLastActiveTime.remove(entry.getKey());
            }
        }
        return onlineCount;
    }
}
