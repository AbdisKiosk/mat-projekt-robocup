package org.example.robot.race;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.robot.RetroRobot;
import org.example.robot.RetroRobotEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class RetroRobotRaceMap {

    private final List<RetroRobotRaceObjective> objectives;

    public void findFastestPath(@NonNull RetroRobot robot) {
        RetroRobotEntity entity = new RetroRobotEntity(robot, 0, 0, 0);
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
