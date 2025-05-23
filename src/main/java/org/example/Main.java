package org.example;

import org.example.robot.RetroRobot;
import org.example.robot.race.RetroRobotRaceMap;
import org.example.robot.race.RetroRobotRaceObjective;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<RetroRobotRaceObjective> objectives = List.of(
                new RetroRobotRaceObjective("A", 3, 4),
                new RetroRobotRaceObjective("B",5, 3),
                new RetroRobotRaceObjective("C",6, -2),
                new RetroRobotRaceObjective("D", 4, -3)
        );
        List<RetroRobot> robots = List.of(
                new RetroRobot("Dragen", 10,0.1,2),
                new RetroRobot("Elefanten", 5,0.15,4),
                new RetroRobot("Krokodillen", 15,0.05,6)

        );
        RetroRobotRaceMap map = new RetroRobotRaceMap(objectives);
        for (RetroRobot robot : robots) {
            map.findFastestPath(robot);
        }
    }
}