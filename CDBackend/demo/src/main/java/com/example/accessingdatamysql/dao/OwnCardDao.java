package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface OwnCardDao {
        OwnCard getOneOwnCard(Integer OwncardId);

        String addNewOwnCard(Integer userId, Integer cardId);

        List<OwnCard> getAllOwnCards();

        List<OwnCard> getAllOwnCardsByUserId(Integer userId);

        String deleteOwnCards(List<Integer> OwncardIds);

        String deleteAll();

        List<OwnCard> deleteOwnCard(Integer userId, Integer cardId);

}
