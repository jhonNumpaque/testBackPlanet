package com.trycore.planets.models.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "views")
    private int views;

    @Column(name = "rotationPeriod")
    private String rotationPeriod;

    @Column(name = "diameter")
    private String diameter;

    @Column(name = "weather")
    private String weather;

    @Column(name = "groud")
    private String ground;
}
