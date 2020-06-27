package com.xiaoming.utils;

import java.util.Arrays;

public class Camera{

    private float[] target = new float[3];
    private float[] origin = new float[3];
    private float[] up = new float[3];

    Camera(){ super(); }

    public String getTarget(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < target.length - 1; i++) {
            stringBuilder.append(target[i] + ", ");
        }
        stringBuilder.append(target[2]);
        String s = stringBuilder.toString();
        return s;
    }

    public String getOrigin(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < origin.length-1; i++) {
            stringBuilder.append(origin[i] + ", ");
        }
        stringBuilder.append(origin[2]);
        String s = stringBuilder.toString();
        return s;
    }

    public String getUp() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < up.length - 1; i++) {
            stringBuilder.append(up[i] + ", ");
        }
        stringBuilder.append(up[2]);
        String s = stringBuilder.toString();
        return s;
    }

    public void setTarget(String targetStr){
        this.target[0] = Float.parseFloat(targetStr.split(",")[0]);
        this.target[1] = Float.parseFloat(targetStr.split(",")[1]);
        this.target[2] = Float.parseFloat(targetStr.split(",")[2]);
    }

    public void setOrigin(String originStr){
        this.origin[0] = Float.parseFloat(originStr.split(",")[0]);
        this.origin[1] = Float.parseFloat(originStr.split(",")[1]);
        this.origin[2] = Float.parseFloat(originStr.split(",")[2]);
    }

    public void setUp(String upStr){
        this.up[0] = Float.parseFloat(upStr.split(",")[0]);
        this.up[1] = Float.parseFloat(upStr.split(",")[1]);
        this.up[2] = Float.parseFloat(upStr.split(",")[2]);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "target=" + Arrays.toString(target) +
                ", origin=" + Arrays.toString(origin) +
                ", up=" + Arrays.toString(up) +
                '}';
    }
}
