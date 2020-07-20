package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Chapter;
import com.example.accessingdatamysql.entity.ChapterPhase;
import com.example.accessingdatamysql.entity.ChapterPhaseEntityId;
import com.example.accessingdatamysql.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ChapterPhaseRepository extends JpaRepository<ChapterPhase, ChapterPhaseEntityId> {
    Optional<ChapterPhase> findChapterPhaseByChapterIdEqualsAndPhaseIdEquals(Integer chapterId, Integer phaseId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE ChapterPhase u SET u = :newChapterPhase WHERE u.chapterId = :ChapterId and u.phaseId = :PhaseId")
    int updateChapterPhaseStatus(@Param("newChapterPhase") ChapterPhase newChapterPhase, @Param("ChapterId") Integer ChapterId, @Param("PhaseId") Integer PhaseId);

}
