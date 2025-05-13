package com.wroteit.ModerationApp.repository;

import com.wroteit.ModerationApp.model.Moderator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeratorRepository extends CrudRepository<Moderator, Long> {
    List<Moderator> findByCommunityId(Long communityId);
    List<Moderator> findByUserId(Long userId);
    Optional<Moderator> findByUserIdAndCommunityId(Long userId, Long communityId);
    boolean existsByUserIdAndCommunityId(Long userId, Long communityId);
}