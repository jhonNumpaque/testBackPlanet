package com.trycore.planets.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTO {
    private String name;
    private String document;
    private String gender;
    private int age;
    private String weight;
    private String height;
    private String birthdate;
    private int views;
    private int planet;
}
