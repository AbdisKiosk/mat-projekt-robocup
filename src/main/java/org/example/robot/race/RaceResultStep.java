package org.example.robot.race;

import lombok.Data;
import org.example.robot.command.RobotCommand;

import java.util.List;

@Data
public class RaceResultStep {

    private final String startPointName;
    private final double xStart;
    private final double yStart;
    private final double radStart;
    private final String endPointName;

    private final double xEnd;
    private final double yEnd;
    private final double radEnd;

    private final double timeTook;
    private final List<RobotCommand> commands;

}
