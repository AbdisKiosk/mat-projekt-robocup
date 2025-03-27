package org.example.robot;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.command.RobotCommand;

import java.util.List;

@AllArgsConstructor
public class RetroRobotPathFinder {

    private final RetroRobot retroRobot;

    public @NonNull List<RobotCommand> getCommandsForPath(double xStart, double yStart, double radStart,
                                                          double xEnd, double yEnd) {

    }

}
