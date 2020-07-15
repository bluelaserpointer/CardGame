package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    @Modifying
    @Transactional
    void deleteChapterByChapterIdEquals(Integer chapterId);
}
