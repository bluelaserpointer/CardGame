package com.example.accessingdatamysql.service;

// import java.sql.Timestamp;

// import java.util.List;

import com.example.accessingdatamysql.entity.User;

public interface MechanismService {

        Integer drawCard(CardService cardService, OwnCardService ownCardService, User user, Integer chi, Integer mat, Integer eng);
}
