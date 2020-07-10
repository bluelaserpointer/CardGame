package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ChatHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ChatHistoryRepository extends MongoRepository<ChatHistory, Integer> {
    Optional<ChatHistory> findChatHistoryByFromIdEquals(Integer fromId);

    Optional<ChatHistory> findChatHistoryByToIdEquals(Integer toId);
}