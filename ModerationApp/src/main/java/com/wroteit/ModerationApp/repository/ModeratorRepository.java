package com.wroteit.ModerationApp.repository;

import com.wroteit.ModerationApp.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    Optional<Moderator> findByReportId(Long reportId);
}
