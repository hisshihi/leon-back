package com.example.leon.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String post;
    private String phone;
    private String telegram;
    private String inst;

    private byte[] image;

}
