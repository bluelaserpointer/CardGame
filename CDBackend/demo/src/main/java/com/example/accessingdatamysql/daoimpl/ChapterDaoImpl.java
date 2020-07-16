package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.ChapterDao;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.entity.ChapterPhase;
import com.example.accessingdatamysql.entity.ChapterPhaseEntityId;
import com.example.accessingdatamysql.repository.ChapterDetailsRepository;
import com.example.accessingdatamysql.repository.ChapterPhaseRepository;
import com.example.accessingdatamysql.repository.ChapterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChapterDaoImpl implements ChapterDao {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ChapterPhaseRepository chapterPhaseRepository;
    @Autowired
    private ChapterDetailsRepository chapterDetailsRepository;

    public List<ChapterDetails> updateChapterPhaseStrategy(Integer chapterId, Integer phaseId, String phaseData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<List<Integer>> mappedPhaseData = objectMapper.readValue(phaseData, typeFactory.constructCollectionType(List.class, List.class));
        for (List<Integer> currPos : mappedPhaseData) {
            ChapterDetails chapterDetails = new ChapterDetails();
            chapterDetails.setChapterId(chapterId);
            chapterDetails.setPhaseId(phaseId);
            chapterDetails.setPositionId(currPos.get(0));
            chapterDetails.setCardId(currPos.get(1));
            chapterDetailsRepository.save(chapterDetails);
        }
        return getChapterDetailsByChapter(chapterId);
    };

    public List<ChapterPhase> updateChapterPhaseAwards(Integer chapterId, Integer phaseId, String awardItems, String awardCards) throws JsonProcessingException {
        Map<Integer, Integer> mappedAwardItems = parseAwardItems(awardItems);
        List<Integer> mappedAwardCards = parseAwardCards(awardCards);

        Optional<ChapterPhase> optChapterPhase = chapterPhaseRepository.findChapterPhaseByChapterIdEqualsAndPhaseIdEquals(chapterId, phaseId);
        if (optChapterPhase.isPresent())
        {
            ChapterPhase chapterPhase = optChapterPhase.get();
            chapterPhase.setAwardItems(mappedAwardItems);
            chapterPhase.setAwardCards(mappedAwardCards);
            chapterPhaseRepository.updateChapterPhaseStatus(chapterPhase, chapterId, phaseId);
        }
        return getAllChapterPhases();
    }

    public List<ChapterPhase> getAllChapterPhases(){
        return chapterPhaseRepository.findAll();
    }

    public Map<Integer, Integer> parseAwardItems(String awardItems)
    {
        String testItems = "{1:2, 2:3, 3:4, 4:5, 5:6}";
        JSONObject parseObject = JSONArray.parseObject(awardItems);
        Map<Integer, Integer> parseMap = parseObject.toJavaObject(Map.class);
        System.out.println(parseMap);
        Map<Integer, Integer> transMap = new HashMap<>();
        Set<Map.Entry<Integer, Integer>> entrySet = parseMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            transMap.put(Integer.parseInt(String.valueOf(entry.getKey())), Integer.parseInt(String.valueOf(entry.getValue())));
        }
        System.out.println(transMap);
        return transMap;
    }

    public List<Integer> parseAwardCards(String awardCards) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(awardCards, typeFactory.constructCollectionType(List.class, Integer.class));
    }

    public List<Chapter> updateChapterAwards(Integer chapterId, String awardItems, String awardCards) throws JsonProcessingException {
        Map<Integer, Integer> mappedAwardItems = parseAwardItems(awardItems);
        List<Integer> mappedAwardCards = parseAwardCards(awardCards);
        Optional<Chapter> optChapter = chapterRepository.findById(chapterId);
        if(optChapter.isPresent())
        {
            Chapter chapter = optChapter.get();
            chapter.setAwardCards(mappedAwardCards);
            chapter.setAwardItems(mappedAwardItems);
            chapterRepository.updateChapterStatus(chapter, chapterId);
        }
        return getAllChapters();
    }

    public List<Chapter> getAllChapters(){
        return chapterRepository.findAll();
    }

    public List<Chapter> deleteChapter(Integer chapterId){
        chapterRepository.deleteChapterByChapterIdEquals(chapterId);
        chapterDetailsRepository.deleteChapterDetailsByCardIdEquals(chapterId);
        return getAllChapters();
    }

    public List<ChapterDetails> getChapterDetailsByChapterAndByPhase(Integer chapterId, Integer phaseId) {
        return chapterDetailsRepository.getChapterDetailsByChapterIdEqualsAndPhaseIdEquals(chapterId, phaseId);
    }

    public List<ChapterDetails> getChapterDetailsByChapter(Integer chapterId) {
        return chapterDetailsRepository.getChapterDetailsByChapterIdEquals(chapterId);
    }

    public List<ChapterDetails> getAllChapterDetails() {
        return chapterDetailsRepository.findAll();
    }
}
