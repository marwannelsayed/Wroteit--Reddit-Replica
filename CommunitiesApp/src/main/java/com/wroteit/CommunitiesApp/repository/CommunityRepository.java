package com.wroteit.CommunitiesApp.repository;
// src/main/java/com/wroteit/CommunitiesApp/repository/CommunityRepository.java

import com.wroteit.CommunitiesApp.model.Community;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends CrudRepository<Community, Long> {
    List<Community> findByTagsIn(List<String> tags);
    List<Community> findByTagsContaining(String subTag);
    boolean existsByName(String name);
    List<Community> findAll();
}