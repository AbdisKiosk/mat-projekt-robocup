package org.example.robot.command;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobotEntity;

@AllArgsConstructor
public class TurnRobotCommand implements RobotCommand {

    private final double turnRads;

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        entity.setPosYawRad(entity.getPosYawRad() + turnRads);

        double secPerRad = 1 / entity.getRetroRobot().getRotationSpeedRad();
        double timeSpent = Math.abs(secPerRad * turnRads);

        return timeSpent;
    }
}
