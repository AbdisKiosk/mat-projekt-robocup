package org.example.robot.race;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobot;
import org.example.robot.RetroRobotEntity;
import org.example.robot.RetroRobotPathFinder;
import org.example.robot.command.RobotCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class RetroRobotRaceMap {

    private final List<RetroRobotRaceObjective> objectives;

    public void findFastestPath(@NonNull RetroRobot robot) {
        for(List<RetroRobotRaceObjective> robotRaceObjectives : allPossibleObjectiveSequences()) {
            double timeSpent = findCommandsForPath(robot, robotRaceObjectives);
            System.out.println("Total time for path " + robotRaceObjectives + " is " + timeSpent);
        }
    }

    public @NonNull double findCommandsForPath(@NonNull RetroRobot robot,
                                                           @NonNull List<RetroRobotRaceObjective> raceObjectives) {
        RetroRobotPathFinder pathFinder = new RetroRobotPathFinder(robot);
        RetroRobotRaceObjective first = raceObjectives.getFirst();

        double startX = 0;
        double startY = 0;
        double startRad = Math.atan2(first.getPosY(), first.getPosX());

        RetroRobotEntity entity = new RetroRobotEntity(robot, startX, startY, startRad);
        double timeSpent = 0;
        for(RetroRobotRaceObjective raceObjective : raceObjectives) {
            List<RobotCommand> objectiveCommands = pathFinder.getCommandsForPath(entity.getPosX(), entity.getPosY(), entity.getPosYawRad(), raceObjective.getPosX(), raceObjective.getPosY());
            timeSpent += objectiveCommands.stream().mapToDouble(command -> command.execute(new RetroRobotEntity(robot, 0, 0, 0))).sum();
        }

        return timeSpent;
    }

    //det her lort er lavet af chat
    public final Collection<List<RetroRobotRaceObjective>> allPossibleObjectiveSequences() {
        List<List<RetroRobotRaceObjective>> result = new ArrayList<>();
        allPermutations(new ArrayList<>(objectives), 0, result);
        return result;
    }

    private void allPermutations(List<RetroRobotRaceObjective> list, int start, List<List<RetroRobotRaceObjective>> result) {
        if (start == list.size() - 1) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            Collections.swap(list, start, i);
            allPermutations(list, start + 1, result);
            Collections.swap(list, start, i); // backtrack
        }
    }


}
