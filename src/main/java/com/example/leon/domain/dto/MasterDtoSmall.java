package com.example.leon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterDtoSmall {

    private Long id;
    private String firstName;
    private String lastName;

}
