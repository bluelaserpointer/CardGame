package com.example.accessingdatamysql.daoimpl;

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
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            chapterPhaseRepository.save(chapterPhase);
        }
        return getAllChapterPhases();
    }

    public List<ChapterPhase> getAllChapterPhases(){
        return chapterPhaseRepository.findAll();
    }

    public Map<Integer, Integer> parseAwardItems(String awardItems)
    {
        Gson gson = new Gson();
        Map<Integer, Integer> mappedAwardItems = new HashMap<Integer, Integer>();
        return gson.fromJson(awardItems, mappedAwardItems.getClass());
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
            chapterRepository.save(chapter);
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
