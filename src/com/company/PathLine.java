package com.company;

import javax.vecmath.*;

public class PathLine {
    private Vector3d pos;
    private float size;
    private boolean rotate;

    public PathLine(Vector3d pos, float size){
        this.pos = pos;
        this.size = size;
        this.rotate = false;
    }

    public PathLine(Vector3d pos, float size, boolean rotate){
        this.pos = pos;
        this.size = size;
        this.rotate = rotate;
    }

    public Vector3d getPos(){
        return this.pos;
    }

    public float getSize(){
        return this.size;
    }

    public boolean getRotate(){
        return  this.rotate;
    }
}
