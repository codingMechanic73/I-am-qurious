package com.example.qurious.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_DETAILS")
public class UserDetailsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailsId;

    @NotBlank(message = "email is required")
    @Email(message = "Provided invalid email")
    private String email;

    @NotBlank(message = "first name is required")
    private String firstName;

    private String lastName;

    private Instant createdOn;

    private String profileUrl;

}
