package com.company;

import javax.vecmath.Vector3d;

public class Obstacle {
    private Vector3d pos;
    private boolean rotate;
    private ObstacleType type;

    public Obstacle(Vector3d pos, ObstacleType type){
        this.pos = pos;
        this.type = type;
        this.rotate = false;
    }

    public Obstacle(Vector3d pos, ObstacleType type, boolean rotate){
        this.pos = pos;
        this.type = type;
        this.rotate = rotate;
    }

    public Vector3d getPos(){
        return this.pos;
    }

    public boolean getRotate(){
        return  this.rotate;
    }

    public ObstacleType getType() { return this.type; }
}
