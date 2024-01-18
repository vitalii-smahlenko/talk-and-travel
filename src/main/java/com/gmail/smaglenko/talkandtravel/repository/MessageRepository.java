package com.gmail.smaglenko.talkandtravel.repository;

import com.gmail.smaglenko.talkandtravel.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCountryIdOrderByCreationDateDesc(Long countryId);
}
