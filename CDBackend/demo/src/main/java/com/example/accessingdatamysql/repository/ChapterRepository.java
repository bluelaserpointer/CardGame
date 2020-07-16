package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    @Modifying
    @Transactional
    void deleteChapterByChapterIdEquals(Integer chapterId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE Chapter u SET u = :newChapter WHERE u.chapterId = :ChapterId")
    int updateChapterStatus(@Param("newChapter") Chapter newChapter, @Param("ChapterId") Integer ChapterId);

}
