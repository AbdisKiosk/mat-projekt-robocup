package org.example.robot.command;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobotEntity;

@AllArgsConstructor
public class MoveRobotCommand implements RobotCommand {

    private final double moveMeters;

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        double metersMovedX = Math.cos(entity.getPosYawRad()) * moveMeters;
        double metersMovedY = Math.sin(entity.getPosYawRad()) * moveMeters;

        entity.setPosX(entity.getPosX() + metersMovedX);
        entity.setPosY(entity.getPosY() + metersMovedY);

        return entity.getRetroRobot().getMovingVelocityMS() / moveMeters;
    }
}
