package com.example.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Lectures")
@Getter
@Setter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int time; // 10, 12 ,14
    private int path; // 1, 2, 3

    @OneToMany(mappedBy = "lecture")
    private Set<Booking> bookingSet = new HashSet<>();

    @Override
    public String toString() {
        return "godzina- " + time + ":00"+
                ", ścieżka- '" + path + "'";
    }
}
