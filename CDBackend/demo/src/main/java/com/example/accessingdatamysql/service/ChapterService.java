package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterDetails;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ChapterService {
    List<ChapterDetails> updateChapterByPhase(Integer chapterId, Integer phaseId, String phaseData) throws JsonProcessingException;
    List<Chapter> getAllChapters();
    List<Chapter> deleteChapter(Integer chapterId);

}
