package org.example.robot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.example.robot.RetroRobotEntity;

@AllArgsConstructor @ToString
public class MoveRobotCommand implements RobotCommand {

    @Getter
    private final double moveMeters;

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        double metersMovedX = Math.cos(entity.getPosYawRad()) * moveMeters;
        double metersMovedY = Math.sin(entity.getPosYawRad()) * moveMeters;
        entity.setPosX(entity.getPosX() + metersMovedX);
        entity.setPosY(entity.getPosY() + metersMovedY);

        double maxSpeed = entity.getRetroRobot().getMovingVelocityMS();
        double stopTimeRobot = entity.getRetroRobot().getStopTime();

        double acceleration = maxSpeed / stopTimeRobot;

        double dAccelerate = (maxSpeed * maxSpeed) / (2 * acceleration);
        double totalAccelerationDistance = 2 * dAccelerate;

        double totalTime;
        if (moveMeters < totalAccelerationDistance) {
            totalTime = 2 * Math.sqrt(moveMeters / acceleration);
        } else {
            double tAccelerate = maxSpeed / acceleration;
            double tDecelerate = tAccelerate;
            double fullSpeedDistance = moveMeters - totalAccelerationDistance;
            double tFullSpeed = fullSpeedDistance / maxSpeed;
            totalTime = tAccelerate + tFullSpeed + tDecelerate;
        }

        return totalTime;
    }
}
