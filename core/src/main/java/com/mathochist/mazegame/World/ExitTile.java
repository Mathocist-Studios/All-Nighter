package com.mathochist.mazegame.World;

public class ExitTile {

    private float x;
    private float y;
    private String targetMap;
    private float targetX;
    private float targetY;
    private ExitConditions conditions;

    public ExitTile(float x, float y, String targetMap, float targetX, float targetY, ExitConditions conditions) {
        this.x = x;
        this.y = y;
        this.targetMap = targetMap;
        this.targetX = targetX;
        this.targetY = targetY;
        this.conditions = conditions;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getTargetMap() {
        return targetMap;
    }

    public void setTargetMap(String targetMap) {
        this.targetMap = targetMap;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public ExitConditions getConditions() {
        return conditions;
    }

    public void setConditions(ExitConditions conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "ExitTile{ X: " + getX() + ", Y: " + getY() + ", TargetMap: " + getTargetMap() + ", TargetX: " + getTargetX() + ", TargetY: " + getTargetY();
    }

}
