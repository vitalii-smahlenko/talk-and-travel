package com.gmail.smaglenko.talkandtravel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 1000, message = "The maximum number of characters for a message is 1000")
    private String content;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
}
