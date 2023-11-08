package com.gmail.smaglenko.talkandtravel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String userEmail;
    @OneToOne
    private Avatar avatar;
}
