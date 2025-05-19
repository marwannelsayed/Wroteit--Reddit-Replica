package com.wroteit.ModerationApp.repository;

import com.wroteit.ModerationApp.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    boolean existsByUserIdAndCommunityId(Long userId, String communityId);


    List<Moderator> findByUserId(Long userId);
    @Query("SELECT m.userId FROM Moderator m WHERE m.communityId = :communityId")
    List<Long> findUserIdsByCommunityId(@Param("communityId") String communityId);

}
