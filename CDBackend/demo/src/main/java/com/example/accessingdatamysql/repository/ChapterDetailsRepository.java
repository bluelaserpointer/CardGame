package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.entity.ChapterDetailsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface ChapterDetailsRepository extends JpaRepository<ChapterDetails, ChapterDetailsEntityId> {
    List<ChapterDetails> getChapterDetailsByChapterIdEqualsAndPhaseIdEquals(Integer chapterId, Integer phaseId);
    List<ChapterDetails> getChapterDetailsByChapterIdEquals(Integer chapterId);


    @Modifying
    @Transactional
    void deleteChapterDetailsByChapterIdEquals(Integer chapterId);

    @Modifying
    @Transactional
    void deleteChapterDetailsByChapterIdEqualsAndPhaseIdGreaterThan(Integer chapterId, Integer phaseId);
}
