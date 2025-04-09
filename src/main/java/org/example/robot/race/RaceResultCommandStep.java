package org.example.robot.race;

import lombok.Data;
import org.example.robot.command.RobotCommand;

@Data
public class RaceResultCommandStep {

    private final RobotCommand command;
    private final String name;
    private final double timeTook;

}
