package com.gmail.smaglenko.talkandtravel.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 16, message = "The username must be at least 2 "
            + "and no more than 16 characters long")
    private String userName;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Avatar avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Size(min = 10, max = 1000, message = "Maximum number of characters for About 1000")
    private String about;
    @OneToMany(mappedBy = "user")
    private List<Participant> participatedCountries;
}
