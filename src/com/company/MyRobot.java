package com.company;

import  simbad.sim.*;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

public class MyRobot extends Agent{
    // Robot Motion Constants
    double ZERO = 0.001;
    double ROBOT_VELOCITY = 0.5;
    double K1 = 3;
    double K2 = 0.4;
    double K3 = 3;
    double SAFETY = 0.3;
    double SMALLEST_DISTANCE_FROM_SENSOR_TO_OBSTACLE = 1;
    double DISTANCE_ERROR = 0.3;
    double GO_BACKWARDS_TIME = 1;
    double ROBOT_IS_STUCK_TIME = 0.5;
    double CAN_SEARCH_FOR_LINE = 1.5;

    // Robot Sensors
    private final RangeSensorBelt bumpers;
    private final RangeSensorBelt sonars;
    private final LineSensor line;
//    private final LightSensor leftLight;
//    private final LightSensor rightLight;
    private final LightSensor light;

    // Camera
    CameraSensor camera;

    // Robot Motion Variables
    private int currentSonarSensor = -1;
    private boolean stuck = false;
    private double stuckTime = 0;
    private int currentBumperSensor = -1;
    private double currentHitRobotAngle = 0;
    private double hitTime = 0;
    private boolean obstacleAvoided = false;
    private double firstDetectionTime = 0;

    public MyRobot (Vector3d position, String name) {
        super(position,name);

        sonars = RobotFactory.addSonarBeltSensor(this, 12);
        bumpers = RobotFactory.addBumperBeltSensor(this, 8);
        line = RobotFactory.addLineSensor(this,15);
//        leftLight = RobotFactory.addLightSensorLeft(this);
//        rightLight = RobotFactory.addLightSensorRight(this);
        light = RobotFactory.addLightSensor(this);

        camera = RobotFactory.addCameraSensor(this);
        camera.rotateZ(-Math.PI/4);
    }

    public void initBehavior() {
        setTranslationalVelocity(ROBOT_VELOCITY);
    }

    public void performBehavior() {
        if(reachedTarget()){
            setRotationalVelocity(0);
            setTranslationalVelocity(0);
            return;
        }

        int sonarSensorIndex = detectSonarSensorHitFromRange();

        if(checkIfRobotStuck()) return;

        if(
                currentSonarSensor == -1 && sonarSensorIndex != -1 &&(
                        (sonars.getMeasurement(sonarSensorIndex) < SMALLEST_DISTANCE_FROM_SENSOR_TO_OBSTACLE &&
                                sensorIsInFrontFaceOfRobot(sonarSensorIndex)) || !lineFound())
        ){
            firstDetectionTime = getLifeTime();
            currentSonarSensor = sonarSensorIndex;
        }

        if(currentSonarSensor != -1 && !obstacleAvoided){
            if(getLifeTime() - firstDetectionTime >= CAN_SEARCH_FOR_LINE && lineFound()){
                obstacleAvoided = true;
                currentSonarSensor = -1;
                return;
            }

            if(
                    sonarSensorIndex != -1 && currentSonarSensor != sonarSensorIndex && obstacleIsNotAlreadyAvoided(sonarSensorIndex) &&
                            sonars.getMeasurement(sonarSensorIndex) <= sonars.getMeasurement(currentSonarSensor) - DISTANCE_ERROR){
                currentSonarSensor = sonarSensorIndex;
            }

            avoidObstacle(currentSonarSensor);
        } else{
            followLine();

            obstacleAvoided = false;
        }
    }

    private boolean obstacleIsNotAlreadyAvoided(int sensorIndex){
        return Math.abs(sonars.getSensorAngle(sensorIndex) - sonars.getSensorAngle(currentSonarSensor)) <= Math.PI / 2;
    }

    private boolean sensorIsInFrontFaceOfRobot(int sensorIndex){
        return sonars.getSensorAngle(sensorIndex) <= Math.PI / 3 || sonars.getSensorAngle(sensorIndex) >= 5 * Math.PI / 3;
    }

    private boolean checkIfRobotStuck() {
        int bumperSensorIndex = this.detectBumperSensorHit();

        if(bumperSensorIndex != -1 && !stuck){
            if(currentBumperSensor == -1 || bumperSensorIndex != currentBumperSensor){
                currentBumperSensor = bumperSensorIndex;
                currentHitRobotAngle = getAngle();
                hitTime = getLifeTime();
            }else{
                if(getLifeTime() - hitTime >= ROBOT_IS_STUCK_TIME){
                    if(Math.abs(currentHitRobotAngle - getAngle()) <= 0.001){
                        stuck = true;
                        stuckTime = getLifeTime();
                    }

                    currentBumperSensor = -1;
                    currentHitRobotAngle = 0;
                }
            }
        }

        if(stuck){
            setTranslationalVelocity(-ROBOT_VELOCITY);

            if(getLifeTime() - stuckTime >= GO_BACKWARDS_TIME){
                stuck = false;
                stuckTime = -1;
                setTranslationalVelocity(ROBOT_VELOCITY);
                return false;
            }

            return true;
        }

        return false;
    }

    private boolean lineFound(){
        for(int i = 0; i < line.getNumSensors(); i++){
            if(line.hasHit(i)){
                return true;
            }
        }

        return false;
    }

    private void followLine(){
        int left = 0, right = 0;
        float k = 0;
        for (int i = 0; i < line.getNumSensors() / 2; i++)
        {
            left += line.hasHit(i) ? 1 : 0;
            right += line.hasHit(line.getNumSensors() - i - 1) ? 1 : 0;
            k++;
        }

        if (left == 0 && right == 0)
        {
            setRotationalVelocity(0);
            setTranslationalVelocity(ROBOT_VELOCITY);
        }
        else setRotationalVelocity(5 * (left - right) / k);
    }

    private void avoidObstacle(int sensorIndex){
        double phLin = sonars.getSensorAngle(sensorIndex) - Math.PI / 2;

        double phRot = Math.atan(K3 * (sonars.getMeasurement(sensorIndex) - SAFETY));

        if(Double.isNaN(phLin)) phLin = ZERO;
        if(Double.isNaN(phRot)) phRot = ZERO;

        double phRef = wrapToPi(phLin + phRot);

        if(Double.isNaN(phRef)) phRef = ZERO;

        setRotationalVelocity(K1 * phRef);
        setTranslationalVelocity(K2 * Math.cos(phRef));
    }

    private int detectSonarSensorHitFromRange(){
        int sensorWithMinDistanceFromObstacle = -1;
        double minMeasurement = 100;

        for(int i = 0; i < sonars.getNumSensors(); i++){
            double currentMeasurement = sonars.getMeasurement(i);
            if(sonars.hasHit(i) && currentMeasurement < minMeasurement){
                minMeasurement = currentMeasurement;
                sensorWithMinDistanceFromObstacle = i;
            }
        }

        return sensorWithMinDistanceFromObstacle;
    }

    private int detectBumperSensorHit(){
        for(int i = 0; i < bumpers.getNumSensors(); i++){
            if(bumpers.hasHit(i))
                return i;
        }

        return -1;
    }

    private double wrapToPi(double a) {
        return a > Math.PI ? a - 2 * Math.PI : a <= -Math.PI ? a + 2 * Math.PI : a;
    }

    private double getAngle() {
        double angle;

        Transform3D m_Transform3D = new Transform3D();
        this.getRotationTransform(m_Transform3D);
        Matrix3d m1 = new Matrix3d();
        m_Transform3D.get(m1);

        double msin = m1.getElement( 2, 0 );
        double mcos = m1.getElement( 0, 0 );

        angle = msin < 0 ? Math.acos(mcos) : mcos < 0 ? 2 * Math.PI - Math.acos(mcos) : -Math.asin(msin);

        while (angle < 0)
            angle += 2 * Math.PI;

        return angle;
    }

    private boolean reachedTarget() {
//        return leftLight.getAverageLuminance() <= 0.9 || rightLight.getAverageLuminance() <= 0.9;
        return false;
    }
}
