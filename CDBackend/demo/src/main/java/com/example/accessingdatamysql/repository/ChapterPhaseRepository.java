package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ChapterPhase;
import com.example.accessingdatamysql.entity.ChapterPhaseEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterPhaseRepository extends JpaRepository<ChapterPhase, ChapterPhaseEntityId> {
    Optional<ChapterPhase> findChapterPhaseByChapterIdEqualsAndPhaseIdEquals(Integer chapterId, Integer phaseId);
}
