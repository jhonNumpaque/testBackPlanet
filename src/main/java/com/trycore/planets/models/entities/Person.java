package com.trycore.planets.models.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "names")
    private String name;

    @Column(name = "document")
    private String document;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private String weight;

    @Column(name = "height")
    private String height;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "views")
    private int views;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planetId")
    private Planet planet;
}
