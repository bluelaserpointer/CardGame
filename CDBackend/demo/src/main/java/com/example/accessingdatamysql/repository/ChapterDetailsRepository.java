package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ChapterDetails;
import com.example.accessingdatamysql.entity.ChapterDetailsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface ChapterDetailsRepository extends JpaRepository<ChapterDetails, ChapterDetailsEntityId> {
    @Modifying
    @Transactional
    void deleteChapterDetailsByCardIdEquals(Integer chapterId);
}
