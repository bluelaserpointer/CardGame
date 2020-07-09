package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface OwnCardService {
        OwnCard getOneOwnCard(Integer OwncardId);

        String addNewOwnCard(Integer userId, Integer cardId);

        List<OwnCard> getAllOwnCards();

        List<OwnCard> getAllOwnCardsByUserId(Integer userId);

        String deleteOwnCards(List<Integer> OwncardIds);

        String deleteAll();

        List<OwnCard> deleteOwnCard(Integer userId, Integer cardId);
}
