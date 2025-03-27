package org.example;

import lombok.Data;

@Data
public class RetroRobot {

    private final String name;
    private final int velocityMS;
    private final int rotationSpeedRad;
    private final int stopTime;

}