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

    public List<ChapterDetails> updateChapterPhaseStrategy(Integer chapterId, Integer phaseId, String phaseData)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<List<Integer>> mappedPhaseData = objectMapper.readValue(phaseData,
                typeFactory.constructCollectionType(List.class, List.class));
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

    public List<ChapterPhase> updateChapterPhaseAwards(Integer chapterId, Integer phaseId, String awardItems,
            String awardCards) throws JsonProcessingException {
        Map<Integer, Integer> mappedAwardItems = parseAwardItems(awardItems);
        List<Integer> mappedAwardCards = parseAwardCards(awardCards);

        Optional<ChapterPhase> optChapterPhase = chapterPhaseRepository
                .findChapterPhaseByChapterIdEqualsAndPhaseIdEquals(chapterId, phaseId);
        ChapterPhase chapterPhase;
        if (optChapterPhase.isPresent()) {
            chapterPhase = optChapterPhase.get();
        } else {
            chapterPhase = new ChapterPhase();
            chapterPhase.setChapterId(chapterId);
            chapterPhase.setPhaseId(phaseId);
        }
        chapterPhase.setAwardItems(mappedAwardItems);
        chapterPhase.setAwardCards(mappedAwardCards);
        chapterPhaseRepository.save(chapterPhase);

        return getChapterPhasesByChapter(chapterId);
    }

    public List<ChapterPhase> getAllChapterPhases() {
        return chapterPhaseRepository.findAll();
    }

    // 将 String类型的 "[[2,1], [3,2], [4,3]]" 等 List<List> 形式转换为 Map
    public Map<Integer, Integer> parseAwardItems(String awardItems) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<List<Integer>> mappedList = objectMapper.readValue(awardItems,
                typeFactory.constructCollectionType(List.class, List.class));
        Map<Integer, Integer> transMap = new HashMap<>();
        for (int i = 0; i < mappedList.size(); i++) {
            Integer id = mappedList.get(i).get(0);
            Integer quantity = mappedList.get(i).get(1);
            transMap.put(id, quantity);
        }
        if (transMap.size() <= 0) {
            return null;
        }
        return transMap;
    }

    // 将 String类型的 "[1,2,3,4]" 转换成 List
    public List<Integer> parseAwardCards(String awardCards) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(awardCards, typeFactory.constructCollectionType(List.class, Integer.class));
    }

    public List<Chapter> updateChapterAwards(Integer chapterId, String awardItems, String awardCards)
            throws JsonProcessingException {
        Map<Integer, Integer> mappedAwardItems = parseAwardItems(awardItems);
        List<Integer> mappedAwardCards = parseAwardCards(awardCards);
        Optional<Chapter> optChapter = chapterRepository.findById(chapterId);
        if (optChapter.isPresent()) {
            Chapter chapter = optChapter.get();
            chapter.setAwardCards(mappedAwardCards);
            chapter.setAwardItems(mappedAwardItems);
            chapterRepository.updateChapterStatus(chapter, chapterId);
        }
        return getAllChapters();
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public List<Chapter> deleteChapter(Integer chapterId) {

        // 同时删除其余联动的数据
        chapterRepository.deleteChaptersByChapterIdEquals(chapterId);
        chapterPhaseRepository.deleteChapterPhasesByChapterIdEquals(chapterId);
        chapterDetailsRepository.deleteChapterDetailsByChapterIdEquals(chapterId);

        return getAllChapters();
    }

    public List<Chapter> updateChapter(Integer chapterId, Integer phaseNo, Integer phaseType) {
        Optional<Chapter> optChapter = chapterRepository.findById(chapterId);
        Chapter chapter;
        if (optChapter.isPresent()) {
            chapter = optChapter.get();
        } else {
            chapter = new Chapter();
            chapter.setChapterId(chapterId);
        }
        chapter.setPhaseNo(phaseNo);
        chapter.setPhaseType(phaseType);
        chapterRepository.save(chapter);

        // 其余关联的数据被更新，例如将 PhaseNo: 3 -> 1，需将 Phase3 与 Phase2 的数据更新(清除)
        chapterPhaseRepository.deleteChapterPhasesByChapterIdEqualsAndPhaseIdGreaterThan(chapterId, phaseNo);
        chapterDetailsRepository.deleteChapterDetailsByChapterIdEqualsAndPhaseIdGreaterThan(chapterId, phaseNo);

        return getAllChapters();
    }

    public List<ChapterDetails> getChapterDetailsByChapterAndByPhase(Integer chapterId, Integer phaseId) {
        return chapterDetailsRepository.getChapterDetailsByChapterIdEqualsAndPhaseIdEquals(chapterId, phaseId);
    }

    public List<ChapterDetails> getChapterDetailsByChapter(Integer chapterId) {
        return chapterDetailsRepository.getChapterDetailsByChapterIdEquals(chapterId);
    }

    public List<ChapterPhase> getChapterPhasesByChapter(Integer chapterId) {
        return chapterPhaseRepository.findChapterPhasesByChapterIdEquals(chapterId);
    }

    public List<ChapterDetails> getAllChapterDetails() {
        return chapterDetailsRepository.findAll();
    }

    public List<Chapter> addChapter(Integer phaseNo, Integer phaseType) {
        Chapter chapter = new Chapter();
        chapter.setPhaseNo(phaseNo);
        chapter.setPhaseType(phaseType);
        chapterRepository.save(chapter);
        return getAllChapters();
    }
}
