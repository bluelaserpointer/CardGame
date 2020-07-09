package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.ChatHistory;

import java.util.List;
import java.util.Optional;

public interface ChatHistoryService {
        Optional<ChatHistory> getOneChatHistory(Integer fromId, Integer toId);

        String addNewChatHistory(Integer fromId, Integer toId);

        String updateChatHistory(Integer fromId, Integer toId, List<String> messages);

        List<ChatHistory> getAllChatHistorys();

        String deleteChatHistorys(List<Integer> fromIds);

        String deleteAll();
}