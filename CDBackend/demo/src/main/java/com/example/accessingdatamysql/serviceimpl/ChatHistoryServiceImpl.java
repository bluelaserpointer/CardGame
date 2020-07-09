package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.ChatHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {
    @Autowired
    private ChatHistoryDao ChatHistoryDao;

    @Override
    public Optional<ChatHistory> getOneChatHistory(Integer fromId, Integer toId) {
        return ChatHistoryDao.getOneChatHistory(fromId, toId);
    }

    public String addNewChatHistory(Integer fromId, Integer toId) {
        return ChatHistoryDao.addNewChatHistory(fromId, toId);
    }

    public String updateChatHistory(Integer fromId, Integer toId, List<String> messages) {
        return ChatHistoryDao.updateChatHistory(fromId, toId, messages);
    }

    public List<ChatHistory> getAllChatHistorys() {
        return ChatHistoryDao.getAllChatHistorys();
    }

    public String deleteChatHistorys(List<Integer> ChatHistoryIds) {
        return ChatHistoryDao.deleteChatHistorys(ChatHistoryIds);
    }

    public String deleteAll() {
        return ChatHistoryDao.deleteAll();
    }
}