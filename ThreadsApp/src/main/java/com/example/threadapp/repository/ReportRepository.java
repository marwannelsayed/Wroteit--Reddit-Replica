package com.example.threadapp.repository;

import com.example.threadapp.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findByReporterIdAndTargetTypeAndTargetId(String reporterId, String targetType, String targetId);
}
