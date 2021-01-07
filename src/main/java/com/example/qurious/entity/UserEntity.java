package com.example.qurious.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 30, message = "Username must have a length between 2 to 30 characters")
    private String userName;

    @ToString.Exclude
    @NotBlank(message = "password is required")
    private String password;

    private boolean isVerified;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserDetailsEntity userDetails;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
