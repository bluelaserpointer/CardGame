package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.entity.ChapterPhase;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ChapterService {
    List<ChapterDetails> updateChapterPhaseStrategy(Integer chapterId, Integer phaseId, String phaseData)
            throws JsonProcessingException;

    List<ChapterPhase> updateChapterPhaseAwards(Integer chapterId, Integer phaseId, String awardItems,
                                                String awardCards) throws JsonProcessingException;

    List<Chapter> updateChapterAwards(Integer chapterId, String awardItems, String awardCards)
            throws JsonProcessingException;

    List<Chapter> getAllChapters();

    List<Chapter> deleteChapter(Integer chapterId);

    List<Chapter> updateChapter(Integer chapterId, Integer phaseNo, Integer phaseType);

    List<Chapter> addChapter(Integer phaseNo, Integer phaseType);

    List<ChapterDetails> getChapterDetailsByChapterAndByPhase(Integer chapterId, Integer phaseId);

    List<ChapterDetails> getChapterDetailsByChapter(Integer chapterId);

    List<ChapterDetails> getAllChapterDetails();

    List<ChapterPhase> getChapterPhasesByChapter(Integer chapterId);

    Chapter getOneChapter(Integer chapterId);

    ChapterPhase getOneChapterPhase(Integer chapterId, Integer chapterPhaseId);

    String phaseClear(Integer userId, Integer chapterId, Integer phaseId, Integer result, String usedCardsIdAndPosString);
}
