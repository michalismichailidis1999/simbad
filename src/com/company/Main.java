package com.company;

import javax.vecmath.*;
import simbad.gui.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Initialize obstacles
        ArrayList<Obstacle> obstacles = new ArrayList();
        obstacles.add(new Obstacle(new Vector3d(-4, 0, -8), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3.5, 0, -7.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(5, 0, -7.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-7.5, 0, 0), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, -0.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, 0), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, 0.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, 1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, 1.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2.5, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1.5, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2.5, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1.5, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 0, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2.5, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1.5, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, -0.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, -1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, 0), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, 0.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, 1), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, 1.5), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-3, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2.5, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-2, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1.5, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-1, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(0, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(0.5, 0, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(0, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(0.5, 1, 2), ObstacleType.BOX));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 0, -3), ObstacleType.WALL));
        obstacles.add(new Obstacle(new Vector3d(2.75, 0, 0.5), ObstacleType.WALL, true));
        obstacles.add(new Obstacle(new Vector3d(-0.5, 0, 4), ObstacleType.WALL));
        obstacles.add(new Obstacle(new Vector3d(3, 0, 7), ObstacleType.WALL));
        obstacles.add(new Obstacle(new Vector3d(-8, 0, 4), ObstacleType.WALL, true));

        // Initialize path
        ArrayList<PathLine> path = new ArrayList();
        path.add(new PathLine(new Vector3d(-8, 0, -8), 4, true));
        path.add(new PathLine(new Vector3d(-3.5, 0, -7.5), 7, true));
        path.add(new PathLine(new Vector3d(5, 0, -7.5), 13));
        path.add(new PathLine(new Vector3d(-7, 0, 7), 7, true));
        path.add(new PathLine(new Vector3d(-7.5, 0, 0), 10, true));

        Simbad frame = new Simbad(new Env(path, obstacles) ,false);
    }
}
