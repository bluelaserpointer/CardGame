package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.Classes.GameState;
import com.example.accessingdatamysql.dao.ChapterDao;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    UserService userService;
    @Autowired
    OwnCardService ownCardService;
    @Autowired
    OwnItemService ownItemService;
    @Autowired
    PveRecordService pveRecordService;

    @Override
    public List<ChapterDetails> updateChapterPhaseStrategy(Integer chapterId, Integer phaseId, String phaseData)
            throws JsonProcessingException {
        return chapterDao.updateChapterPhaseStrategy(chapterId, phaseId, phaseData);
    };

    public List<ChapterPhase> updateChapterPhaseAwards(Integer chapterId, Integer phaseId, String awardItems,
            String awardCards) throws JsonProcessingException {
        return chapterDao.updateChapterPhaseAwards(chapterId, phaseId, awardItems, awardCards);
    }

    public List<Chapter> updateChapterAwards(Integer chapterId, String awardItems, String awardCards)
            throws JsonProcessingException {
        return chapterDao.updateChapterAwards(chapterId, awardItems, awardCards);
    }

    public List<Chapter> getAllChapters() {
        return chapterDao.getAllChapters();
    }

    public List<Chapter> deleteChapter(Integer chapterId) {
        return chapterDao.deleteChapter(chapterId);
    }

    public List<Chapter> updateChapter(Integer chapterId, Integer phaseNo, Integer phaseType) {
        return chapterDao.updateChapter(chapterId, phaseNo, phaseType);
    }

    public List<ChapterDetails> getChapterDetailsByChapterAndByPhase(Integer chapterId, Integer phaseId) {
        return chapterDao.getChapterDetailsByChapterAndByPhase(chapterId, phaseId);
    }

    public List<ChapterDetails> getChapterDetailsByChapter(Integer chapterId) {
        return chapterDao.getChapterDetailsByChapter(chapterId);
    }

    public List<ChapterPhase> getChapterPhasesByChapter(Integer chapterId) {
        return chapterDao.getChapterPhasesByChapter(chapterId);
    }

    @Override
    public Chapter getOneChapter(Integer chapterId) {
        return chapterDao.getOneChapter(chapterId);
    }

    public List<ChapterDetails> getAllChapterDetails() {
        return chapterDao.getAllChapterDetails();
    }

    public List<Chapter> addChapter(Integer phaseNo, Integer phaseType) {
        return chapterDao.addChapter(phaseNo, phaseType);
    }

    public ChapterPhase getOneChapterPhase(Integer chapterId, Integer chapterPhaseId) {
        return chapterDao.getOneChapterPhase(chapterId, chapterPhaseId);
    }

    @Override
    public String phaseClear(Integer userId, Integer chapterId, Integer phaseId, Integer result, String usedCardsIdAndPosString) {
        //TODO: Need a check logic for if user is cheating.
        //TODO: seems redundant to get a userId by userName
        final Chapter chapter = chapterDao.getOneChapter(chapterId);
        final GameState state = GameState.values()[result];
        final LinkedList<Integer> awardCardsId = new LinkedList<>();
        final HashMap<Integer, Integer> awardItemsIdAndAmount = new HashMap<>();
        int awardExp = 0;
        if(state == GameState.WIN) { //TODO: determine awards for case of lose
            //return phase awards
            //TODO: Need calculate fixed drop items/cards/resources/EXP for each enemy.
            final ChapterPhase phase = chapterDao.getOneChapterPhase(chapterId, phaseId);
            awardCardsId.addAll(phase.getAwardCards());
            awardItemsIdAndAmount.putAll(phase.getAwardItems());
            //return chapter awards
            if (chapter.getPhaseNo().equals(phaseId)) {
                awardCardsId.addAll(chapter.getAwardCards());
                awardItemsIdAndAmount.putAll(chapter.getAwardItems());
            }
        }
        //automatically add pve record
        final PveRecord pveRecord = pveRecordService.addPveRecord(userId, chapterId, phaseId, state.ordinal(), usedCardsIdAndPosString);
        //automatically addExp to users and cards
        userService.addExp(userId, awardExp);
        for(Map.Entry<Integer, Integer> cardAndPos : pveRecord.getPosRecord().entrySet()) {
            //TODO: add exp for cards should use a function provided by Service
            final OwnCard ownCard = ownCardService.getOneOwnCard(cardAndPos.getKey());
            ownCard.setCardCurExp(ownCard.getCardCurExp() + awardExp);
        }
        //automatically add items to user
        for(Map.Entry<Integer, Integer> itemEntry : awardItemsIdAndAmount.entrySet())
            ownItemService.addNewOwnItem(userId, itemEntry.getKey(), itemEntry.getValue());
        //toJSONString
        final JSONObject response = new JSONObject();
        response.put("awardCardsId", awardCardsId);
        response.put("awardItems", awardItemsIdAndAmount);
        response.put("exp", awardExp);
        return response.toString();
    }

}
