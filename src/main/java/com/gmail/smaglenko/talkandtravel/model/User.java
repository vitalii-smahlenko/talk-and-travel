package com.gmail.smaglenko.talkandtravel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 16, message = "The username must be at least 2 "
            + "and no more than 16 characters long")
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userEmail;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
    @OneToOne
    private Avatar avatar;
}
