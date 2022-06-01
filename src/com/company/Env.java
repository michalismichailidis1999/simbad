package com.company;

import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class Env extends EnvironmentDescription{
    private final Vector3f boxSize = new Vector3f(0.5f, 0.5f, 0.5f);
    private final Vector3f wallSize = new Vector3f(7f, 1.25f, 0.5f);
    private final Color3f obstacleColor = new Color3f(0f, 1f, 1f);

    public Env(ArrayList<PathLine> linePaths, ArrayList<Obstacle> obstacles){
        light1SetPosition(0, 4, 0);
        light1IsOn = true;
        light2IsOn = false;

        boxColor = obstacleColor;

        setWorldSize(20);
        showAxis(true);

        this.initializeObstacles(obstacles);
        this.initializeLinePaths(linePaths);

        add(new MyRobot(new Vector3d(-8, 0, -8), "robot 1"));
    }

    private void initializeObstacles(ArrayList<Obstacle> obstacles){
        for(Obstacle obstacle: obstacles){
            if(obstacle.getType() == ObstacleType.BOX){
                add(new Box(obstacle.getPos(), this.boxSize,this));
            }else{
                Wall wall = new Wall(obstacle.getPos(), this.wallSize.x, this.wallSize.z, this.wallSize.y, this);
                if(obstacle.getRotate()) wall.rotate90(1);
                add(wall);
            }
        }
    }

    private void initializeLinePaths(ArrayList<PathLine> path){
        for(PathLine pathLine: path){
            Line line = new Line(pathLine.getPos(), pathLine.getSize(), this);
            if(pathLine.getRotate()) line.rotate90(1);
            add(line);
        }
    }
}
