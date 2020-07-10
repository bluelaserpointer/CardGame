package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.OwnCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnCardServiceImpl implements OwnCardService {
    @Autowired
    private OwnCardDao OwnCardDao;

    @Override
    public OwnCard getOneOwnCard(Integer OwnCardId) {
        return OwnCardDao.getOneOwnCard(OwnCardId);
    }

    public String addNewOwnCard(Integer userId, Integer cardId) {
        return OwnCardDao.addNewOwnCard(userId, cardId);
    }

    public List<OwnCard> getAllOwnCardsByUserId(Integer userId) {
        return OwnCardDao.getAllOwnCardsByUserId(userId);
    }

    public List<OwnCard> getAllOwnCards() {
        return OwnCardDao.getAllOwnCards();
    }

    public String deleteOwnCards(List<Integer> OwnCardIds) {
        return OwnCardDao.deleteOwnCards(OwnCardIds);
    }

    public String deleteAll() {
        return OwnCardDao.deleteAll();
    }

    public List<OwnCard> deleteOwnCard(Integer userId, Integer cardId){
        return OwnCardDao.deleteOwnCard(userId, cardId);
    }
}
