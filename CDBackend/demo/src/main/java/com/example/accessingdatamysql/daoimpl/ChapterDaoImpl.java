package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.ChapterDao;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.repository.ChapterDetailsRepository;
import com.example.accessingdatamysql.repository.ChapterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChapterDaoImpl implements ChapterDao {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ChapterDetailsRepository chapterDetailsRepository;

    public List<ChapterDetails> updateChapterByPhase(Integer chapterId, Integer phaseId, String phaseData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<List<Integer>> mappedPhaseData = objectMapper.readValue(phaseData, typeFactory.constructCollectionType(List.class, List.class));
        System.out.println(mappedPhaseData);
        Integer posCount = mappedPhaseData.size();
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

    public List<Chapter> getAllChapters(){
        System.out.println(chapterRepository.findAll());
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
