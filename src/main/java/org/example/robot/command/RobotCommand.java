package org.example.robot.command;

import lombok.NonNull;
import org.example.robot.RetroRobotEntity;

public interface RobotCommand {

    /**
     * @return hvor længe det tog robotten at udføre det
     */
    double execute(@NonNull RetroRobotEntity entity);

}
