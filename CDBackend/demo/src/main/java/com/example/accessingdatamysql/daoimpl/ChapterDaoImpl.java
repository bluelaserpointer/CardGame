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
        int row = mappedPhaseData.size();
        int col = mappedPhaseData.get(0).size();
        int pos = 0;
        Integer card = 0;
        for(int i = 0; i < row; i++)
        {
            System.out.println("Row " + i);
            List<Integer> currRow = mappedPhaseData.get(i);
            for(int j = 0; j < col; j++)
            {
                System.out.println("Col " + j);
                card = currRow.get(j);
                System.out.println(card);
                if(card == -1)
                    continue;
                pos = i * row + j;
                ChapterDetails chapterDetails = new ChapterDetails();
                chapterDetails.setChapterId(chapterId);
                chapterDetails.setPhaseId(phaseId);
                chapterDetails.setCardId(card);
                chapterDetails.setPositionId(pos);
                chapterDetailsRepository.save(chapterDetails);
            }

        }
        System.out.println("Finished loop");
        System.out.println(chapterDetailsRepository.findAll());
        return chapterDetailsRepository.findAll();
    };

    public List<Chapter> getAllChapters(){
        System.out.println("In dao");
        System.out.println(chapterRepository.findAll());
        return chapterRepository.findAll();
    }

    public List<Chapter> deleteChapter(Integer chapterId){
        chapterRepository.deleteChapterByChapterIdEquals(chapterId);
        chapterDetailsRepository.deleteChapterDetailsByCardIdEquals(chapterId);
        return getAllChapters();
    }
}
