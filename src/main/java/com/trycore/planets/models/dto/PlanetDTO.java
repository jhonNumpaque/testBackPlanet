package com.trycore.planets.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PlanetDTO {
    private String name;
    private int views;
    private String rotationPeriod;
    private String diameter;
    private String weather;
    private String ground;
}
