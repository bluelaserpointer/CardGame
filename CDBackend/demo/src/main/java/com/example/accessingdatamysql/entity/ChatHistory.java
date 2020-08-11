package com.example.accessingdatamysql.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ChatHistory")
public class ChatHistory {
    // 这里应该需要进行大量修改，首先要考虑到发送次序问题，需要记录用户发出每一条信息的时间，可能需要再建立一个实体集
    private Integer fromId;

    private Integer toId;

    private List<String> messages;

    public ChatHistory(){}

    public ChatHistory(Integer fromId, Integer toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    @Id
    private ObjectId id;

    public List<String> getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
