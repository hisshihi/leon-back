package com.example.leon.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Masters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "post", nullable = false)
    private String post;
    @Column(name = "phone")
    private String phone;
    @Column(name = "telegram")
    private String telegram;
    @Column(name = "inst")
    private String inst;

    @Lob
    @Column(name = "iamge")
    private byte[] image;

//    security
    private String userName;
    private String password;
    private String role = "master";
    private Boolean enabled = true;

}
