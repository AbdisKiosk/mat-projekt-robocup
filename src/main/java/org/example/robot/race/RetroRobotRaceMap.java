package org.example.robot.race;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobot;
import org.example.robot.RetroRobotEntity;
import org.example.robot.RetroRobotPathFinder;
import org.example.robot.command.RobotCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class RetroRobotRaceMap {

    private final List<RetroRobotRaceObjective> objectives;

    public void findFastestPath(@NonNull RetroRobot robot) {
        for(List<RetroRobotRaceObjective> robotRaceObjectives : allPossibleObjectiveSequences()) {
            List<RobotCommand> commands = findCommandsForPath(robot, robotRaceObjectives);
            double totalTime = commands.stream().mapToDouble(command -> command.execute(new RetroRobotEntity(robot, 0, 0, 0))).sum();
            System.out.println("Total time for path " + robotRaceObjectives + " is " + totalTime);
        }
    }

    public @NonNull List<RobotCommand> findCommandsForPath(@NonNull RetroRobot robot,
                                                           @NonNull List<RetroRobotRaceObjective> raceObjectives) {
        RetroRobotPathFinder pathFinder = new RetroRobotPathFinder(robot);
        RetroRobotRaceObjective first = raceObjectives.getFirst();

        double startX = 0;
        double startY = 0;
        double startRad = Math.atan2(first.getPosY(), first.getPosX());

        RetroRobotEntity entity = new RetroRobotEntity(robot, startX, startY, startRad);
        List<RobotCommand> commands = new ArrayList<>();
        for(RetroRobotRaceObjective raceObjective : raceObjectives) {
            List<RobotCommand> objectiveCommands = pathFinder.getCommandsForPath(entity.getPosX(), entity.getPosY(), entity.getPosYawRad(), raceObjective.getPosX(), raceObjective.getPosY());
            commands.addAll(objectiveCommands);
        }

        return commands;
    }

    //det her lort er lavet af chat
    public final Collection<List<RetroRobotRaceObjective>> allPossibleObjectiveSequences() {
        List<List<RetroRobotRaceObjective>> result = new ArrayList<>();
        int n = objectives.size();

        // Generate all subsets using bit manipulation
        for (int i = 0; i < (1 << n); i++) {
            List<RetroRobotRaceObjective> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(objectives.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }


}
