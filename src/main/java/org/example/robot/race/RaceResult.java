package org.example.robot.race;

import lombok.Data;
import org.example.robot.command.RobotCommand;

import java.util.List;
import java.util.Map;

@Data
public class RaceResult {

    private final List<RaceResultStep> steps;

    public double getTimeSpent() {
        return steps.stream().mapToDouble(RaceResultStep::getTimeTook).sum();
    }
}
