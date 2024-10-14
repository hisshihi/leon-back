package com.example.leon.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "black_list_phone")
@NoArgsConstructor
@AllArgsConstructor
public class BlackListPhone {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ToString.Include
    private String phone;
    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlackListPhone that = (BlackListPhone) o;
        return Objects.equals(id, that.id) && Objects.equals(phone, that.phone) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, firstName, lastName);
    }
}