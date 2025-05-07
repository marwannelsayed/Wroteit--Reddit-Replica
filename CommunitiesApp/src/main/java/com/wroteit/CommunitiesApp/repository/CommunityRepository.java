package com.wroteit.CommunitiesApp.repository;
// src/main/java/com/wroteit/communities/repository/CommunityRepository.java

import com.wroteit.CommunitiesApp.model.Community;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends CrudRepository<Community, Long> {
    Optional<Community> findById(Long id);
    List<Community> findByTagsIn(List<String> tags);
    boolean existsByName(String name);
    void deleteById(Long id);
    @Override
    <S extends Community> S save(S community);
    @Override
    List<Community> findAll();
}

