package org.example.robot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.example.robot.RetroRobotEntity;

@AllArgsConstructor @ToString
public class TurnRobotCommand implements RobotCommand {

    @Getter
    private final double turnRads;

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        entity.setPosYawRad(entity.getPosYawRad() + turnRads);

        double secPerRad = 1 / entity.getRetroRobot().getRotationSpeedRad();
        double timeSpent = Math.abs(secPerRad * turnRads);

        return timeSpent;
    }
}
