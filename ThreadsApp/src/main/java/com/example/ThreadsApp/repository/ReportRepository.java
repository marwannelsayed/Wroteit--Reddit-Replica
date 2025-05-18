package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findByReporterIdAndTargetTypeAndTargetId(String reporterId, String targetType, String targetId);
}
