package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface ChapterRepository extends JpaRepository<Chapter, ChapterEntityId> {
    @Modifying
    @Transactional
    void deleteChapterByChapterIdEquals(Integer chapterId);
}
