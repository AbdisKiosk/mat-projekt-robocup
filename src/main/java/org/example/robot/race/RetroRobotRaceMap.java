package org.example.robot.race;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobot;
import org.example.robot.RetroRobotEntity;
import org.example.robot.RetroRobotPathFinder;
import org.example.robot.command.MoveRobotCommand;
import org.example.robot.command.RobotCommand;
import org.example.robot.command.TurnRobotCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        System.out.println(robot.getName() + ":");
        for(String resultPart : getPrettyResult(results.getFirst())) {
            System.out.println(resultPart);
        }
        System.out.println(" - " + results.getFirst().getTimeSpent() + "s");
        exportResultsToCSV(results, robot.getName());
        System.out.println("Eksportet til CSV fil");
    }

    protected @NonNull List<String> getPrettyResult(@NonNull RaceResult result) {
        List<String> stepStrings = new ArrayList<>();
        for (RaceResultStep step : result.getSteps()) {
            StringBuilder stepsString = new StringBuilder();
            stepsString.append("  - ")
                    .append(step.getStartPointName())
                    .append(" -> ")
                    .append(step.getEndPointName())
                    .append(" (")
                    .append(step.getTimeTook())
                    .append("s)");
            for (RaceResultCommandStep command : step.getCommands()) {
                stepsString.append("\n      ")
                        .append(command.getName())
                        .append(" (")
                        .append(command.getTimeTook())
                        .append("s)");
            }
            stepStrings.add(stepsString.toString());
        }
        return stepStrings;
    }

    public @NonNull RaceResult findCommandsForPath(@NonNull RetroRobot robot,
                                                   @NonNull List<RetroRobotRaceObjective> raceObjectives) {
        List<RetroRobotRaceObjective> objectives = new ArrayList<>(raceObjectives);
        objectives.add(new RetroRobotRaceObjective("O", 0, 0));

        RetroRobotPathFinder pathFinder = new RetroRobotPathFinder(robot);
        RetroRobotRaceObjective first = raceObjectives.get(0);

        double initX = 0;
        double initY = 0;
        double initRad = Math.atan2(first.getPosY(), first.getPosX());

        RetroRobotEntity entity = new RetroRobotEntity(robot, initX, initY, initRad);
        List<RaceResultStep> steps = new ArrayList<>();
        String lastObjectiveName = "O";
        for (RetroRobotRaceObjective raceObjective : objectives) {
            double startX = entity.getPosX();
            double startY = entity.getPosY();
            double startRad = entity.getPosYawRad();
            List<RobotCommand> objectiveCommands = pathFinder.getCommandsForPath(startX, startY, startRad,
                    raceObjective.getPosX(), raceObjective.getPosY());

            List<RaceResultCommandStep> objectiveCommandSteps = new ArrayList<>();
            double timeSpent = 0;
            for (RobotCommand command : objectiveCommands) {
                double cmdTime = command.execute(entity);
                timeSpent += cmdTime;
                String commandName = command.getClass().getSimpleName();

                if (command instanceof TurnRobotCommand) {
                    // Assuming TurnRobotCommand has a getTurnRads() method returning the yaw difference.
                    double yawDiff = ((TurnRobotCommand) command).getTurnRads();
                    commandName += " (yaw-diff: " + yawDiff + ")";
                } else if (command instanceof MoveRobotCommand) {
                    // Assuming MoveRobotCommand has a getMoveMeters() method returning the meter distance.
                    double meterDist = ((MoveRobotCommand) command).getMoveMeters();
                    commandName += " (meter-dist: " + meterDist + ")";
                }

                objectiveCommandSteps.add(new RaceResultCommandStep(command, commandName, cmdTime));
            }

            double endX = entity.getPosX();
            double endY = entity.getPosY();
            double endRad = entity.getPosYawRad();

            steps.add(new RaceResultStep(
                    lastObjectiveName,
                    startX,
                    startY,
                    startRad,
                    raceObjective.getName(),
                    endX,
                    endY,
                    endRad,
                    timeSpent,
                    objectiveCommandSteps
            ));
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

    public void exportResultsToCSV(@NonNull List<RaceResult> results, String robotName) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Robot;FullPath;TotalTimeSpent\n");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("da-DK"));
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);

        for (RaceResult result : results) {
            StringBuilder fullPath = new StringBuilder();
            for (RaceResultStep step : result.getSteps()) {
                fullPath.append(step.getStartPointName());
            }
            fullPath.append(result.getSteps().get(result.getSteps().size() - 1).getEndPointName());

            double totalTimeSpent = result.getTimeSpent();

            csvBuilder.append(robotName).append(";")
                    .append(fullPath).append(";")
                    .append(decimalFormat.format(totalTimeSpent)).append("sek").append("\n");
        }

        try (FileWriter writer = new FileWriter(robotName + "_results.csv")) {
            writer.write(csvBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
