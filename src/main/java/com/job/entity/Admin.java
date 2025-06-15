package com.job.entity;

import jakarta.persistence.*; 
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    // Constructor without id for creating new admins
    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}