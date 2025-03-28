package org.example.robot.command;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.example.robot.RetroRobotEntity;

@AllArgsConstructor @ToString
public class MoveRobotCommand implements RobotCommand {

    private final double moveMeters;

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        double metersMovedX = Math.cos(entity.getPosYawRad()) * moveMeters;
        double metersMovedY = Math.sin(entity.getPosYawRad()) * moveMeters;

        entity.setPosX(entity.getPosX() + metersMovedX);
        entity.setPosY(entity.getPosY() + metersMovedY);

        double brakeAcceleration =
                (double) entity.getRetroRobot().getMovingVelocityMS() / entity.getRetroRobot().getStopTime();


        double accelerationAndBrakingDistance = moveMeters / 2;
        double stopTime = Math.sqrt((2 * accelerationAndBrakingDistance) / brakeAcceleration);
        double accelerationTime = Math.sqrt((2 * accelerationAndBrakingDistance) / brakeAcceleration);

        return stopTime + accelerationTime;
    }
}
