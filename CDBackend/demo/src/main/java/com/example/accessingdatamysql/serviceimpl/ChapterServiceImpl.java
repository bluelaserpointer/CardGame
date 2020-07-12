package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.ChapterDao;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.service.ChapterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public List<ChapterDetails> updateChapterByPhase(Integer chapterId, Integer phaseId, String phaseData) throws JsonProcessingException {
        return chapterDao.updateChapterByPhase(chapterId, phaseId, phaseData);
    };

    public List<Chapter> getAllChapters(){
        System.out.println("In service");
        return chapterDao.getAllChapters();
    }

    public List<Chapter> deleteChapter(Integer chapterId) {
        return chapterDao.deleteChapter(chapterId);
    }

    public List<ChapterDetails> getChapterDetailsByChapterAndByPhase(Integer chapterId, Integer phaseId) {
        return chapterDao.getChapterDetailsByChapterAndByPhase(chapterId, phaseId);
    }

    public List<ChapterDetails> getChapterDetailsByChapter(Integer chapterId) {
        return chapterDao.getChapterDetailsByChapter(chapterId);
    }

    public List<ChapterDetails> getAllChapterDetails() {
        return chapterDao.getAllChapterDetails();
    }
}
