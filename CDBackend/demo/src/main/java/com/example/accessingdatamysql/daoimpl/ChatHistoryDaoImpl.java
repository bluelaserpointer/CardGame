package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.ChatHistoryDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class ChatHistoryDaoImpl implements ChatHistoryDao {
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @Override
    public Optional<ChatHistory> getOneChatHistory(Integer fromId, Integer toId) {
        Optional<ChatHistory> chatHistory = chatHistoryRepository.findChatHistoryByFromIdEquals(fromId);
        if (chatHistory.isPresent()) {
            return chatHistory;
        }
        if (chatHistoryRepository.findChatHistoryByToIdEquals(toId).isPresent()) {
            chatHistory = chatHistoryRepository.findChatHistoryByToIdEquals(toId);
            return chatHistory;
        }
        return chatHistory;
    }

    // public void modifyStorage(ChatHistory ChatHistory) {
    // ChatHistoryRepository.updateStorageStatus(ChatHistory.getStorage(),
    // ChatHistory.getChatHistoryId());
    // return;
    // }

    // public ChatHistoryDetails findOneDetail(Integer id) {
    // return ChatHistoryDetailsRepository.findById(id);
    // }

    public String addNewChatHistory(Integer fromId, Integer toId) {

        ChatHistory ChatHistory = new ChatHistory(fromId, toId);
        // GlobalConstants.printIfDoDebug("new ChatHistory has an Id of : " + n.getChatHistoryId());
        chatHistoryRepository.save(ChatHistory);
        return "Saved ChatHistory";

    }

    public String updateChatHistory(Integer fromId, Integer toId, List<String> messages) {

        Optional<ChatHistory> ChatHistory = getOneChatHistory(fromId, toId);
        // GlobalConstants.printIfDoDebug("old ChatHistory has an Id of : " + n.getChatHistoryId());
        if (ChatHistory.isPresent()) {
            ChatHistory.get().setMessages(messages);
            chatHistoryRepository.save(ChatHistory.get());
        }

        return "modified ChatHistory: from " + ChatHistory.get().getFromId() + ChatHistory.get().getToId();

    }

    public List<ChatHistory> getAllChatHistorys() {
        List<ChatHistory> ChatHistorys = chatHistoryRepository.findAll();
        return ChatHistorys;
    }

    public String deleteChatHistorys(List<Integer> ChatHistoryIds) {
        for (int i = 0; i < ChatHistoryIds.size(); i++) {
            chatHistoryRepository.deleteById(ChatHistoryIds.get(i));
        }
        return "Deleted ChatHistorys by id";
    }

    public String deleteAll() {
        chatHistoryRepository.deleteAll();
        return "Deleted All ChatHistorys";
    }
}
