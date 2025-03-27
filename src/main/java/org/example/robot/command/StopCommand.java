package org.example.robot.command;

import lombok.NonNull;
import org.example.robot.RetroRobotEntity;

public class StopCommand implements RobotCommand {

    @Override
    public double execute(@NonNull RetroRobotEntity entity) {
        // Mens robotten stopper, bevæger den sig så, eller står den bare stille?
        return entity.getRetroRobot().getStopTime();
    }
}