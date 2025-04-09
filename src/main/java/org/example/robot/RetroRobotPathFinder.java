package org.example.robot;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.command.MoveRobotCommand;
import org.example.robot.command.RobotCommand;
import org.example.robot.command.TurnRobotCommand;

import java.util.List;

@AllArgsConstructor
public class RetroRobotPathFinder {

    private final RetroRobot retroRobot;

    public @NonNull List<RobotCommand> getCommandsForPath(double xStart, double yStart, double radStart,
                                                          double xEnd, double yEnd) {
        double angle = Math.atan2(yEnd - yStart, xEnd - xStart) - radStart;
        double distance = Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));

        RobotCommand turnCommand = new TurnRobotCommand(angle);
        RobotCommand moveCommand = new MoveRobotCommand(distance);
        return List.of(turnCommand, moveCommand);
    }
}
