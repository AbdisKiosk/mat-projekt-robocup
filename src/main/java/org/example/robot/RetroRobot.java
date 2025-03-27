package org.example.robot;

import lombok.Data;

@Data
public class RetroRobot {

    private final String name;
    private final int movingVelocityMS;
    private final double rotationSpeedRad;
    private final int stopTime;

}