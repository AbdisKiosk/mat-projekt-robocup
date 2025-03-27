package org.example.robot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class RetroRobotEntity {

    private final RetroRobot retroRobot;
    private double posX;
    private double posY;
    private double posYawRad;

}
