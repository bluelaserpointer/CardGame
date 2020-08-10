package com.example.accessingdatamysql.dao;

import java.util.List;
import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface ChatHistoryDao {
        Optional<ChatHistory> getOneChatHistory(Integer fromId, Integer toId);

        String addNewChatHistory(Integer fromId, Integer toId);

        String updateChatHistory(Integer fromId, Integer toId, List<String> messages);

        List<ChatHistory> getAllChatHistorys();

        String deleteChatHistorys(List<Integer> fromIds);

        String deleteAll();

}
