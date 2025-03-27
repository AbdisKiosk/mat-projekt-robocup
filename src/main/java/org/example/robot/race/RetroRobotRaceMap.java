package org.example.robot.race;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobot;
import org.example.robot.RetroRobotEntity;
import org.example.robot.RetroRobotPathFinder;
import org.example.robot.command.RobotCommand;

import java.util.*;

@AllArgsConstructor
public class RetroRobotRaceMap {

    private final List<RetroRobotRaceObjective> objectives;

    public void findFastestPath(@NonNull RetroRobot robot) {
        List<RaceResult> results = new ArrayList<>();
        for (List<RetroRobotRaceObjective> robotRaceObjectives : allPossibleObjectiveSequences()) {
            RaceResult result = findCommandsForPath(robot, robotRaceObjectives);
            System.out.println("Result: " + result);
            results.add(result);
        }
        results.sort(Comparator.comparingDouble(RaceResult::getTimeSpent));
        System.out.println(robot.getName() + " " + results.toString());
    }

    public @NonNull RaceResult findCommandsForPath(@NonNull RetroRobot robot,
                                                           @NonNull List<RetroRobotRaceObjective> raceObjectives) {
        List<RetroRobotRaceObjective> objectives = new ArrayList<>(raceObjectives);
        objectives.add(new RetroRobotRaceObjective("O",0, 0));

        RetroRobotPathFinder pathFinder = new RetroRobotPathFinder(robot);
        RetroRobotRaceObjective first = raceObjectives.getFirst();

        double initX = 0;
        double initY = 0;
        double initRad = Math.atan2(first.getPosY(), first.getPosX());

        RetroRobotEntity entity = new RetroRobotEntity(robot, initX, initY, initRad);
        List<RaceResultStep> steps = new ArrayList<>();
        String lastObjectiveName = "O";
        for(RetroRobotRaceObjective raceObjective : objectives) {
            double startX = entity.getPosX();
            double startY = entity.getPosY();
            double startRad = entity.getPosYawRad();
            List<RobotCommand> objectiveCommands
                    = pathFinder.getCommandsForPath(startX, startY, startRad, raceObjective.getPosX(), raceObjective.getPosY());

            double timeSpent = objectiveCommands.stream()
                    .mapToDouble(command -> command.execute(new RetroRobotEntity(robot, 0, 0, 0))).sum();

            double endX = entity.getPosX();
            double endY = entity.getPosY();
            double endRad = entity.getPosYawRad();

            steps.add(
                    new RaceResultStep(
                            lastObjectiveName,
                            startX,
                            startY,
                            startRad,
                            raceObjective.getName(),
                            endX,
                            endY,
                            endRad,
                            timeSpent,
                            objectiveCommands
                    )
            );
            lastObjectiveName = raceObjective.getName();
        }
        return new RaceResult(steps);
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
