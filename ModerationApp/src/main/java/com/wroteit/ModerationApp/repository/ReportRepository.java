package com.wroteit.ModerationApp.repository;

import com.wroteit.ModerationApp.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


//import com.moderationapp.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCommunityId(String communityId);
}
