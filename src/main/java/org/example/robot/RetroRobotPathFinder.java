package org.example.robot;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.command.MoveRobotCommand;
import org.example.robot.command.RobotCommand;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
public class RetroRobotPathFinder {

    private final RetroRobot retroRobot;

    public @NonNull List<RobotCommand> getCommandsForPath(double xStart, double yStart, double radStart,
                                                          double xEnd, double yEnd) {
        Double distance = Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));
        Double angle = Math.atan2(yEnd - yStart, xEnd - xStart) - radStart;

        RobotCommand moveCommand = new MoveRobotCommand(distance);
        RobotCommand turnCommand = new MoveRobotCommand(angle);
        return List.of(moveCommand, turnCommand);
    }
}
