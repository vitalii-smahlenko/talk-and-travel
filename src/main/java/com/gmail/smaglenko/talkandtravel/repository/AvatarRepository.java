package com.gmail.smaglenko.talkandtravel.repository;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByUserId(Long userId);
}
