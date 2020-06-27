package com.xiaoming.utils;

import java.util.ArrayList;
import java.util.List;

public class Parameter{
    private Camera camera;
    private String integrator;
    private List<String> filename = new ArrayList<>(15);
    private String bsdf;

    public Parameter() { super();}

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getIntegrator() {
        return integrator;
    }

    public void setIntegrator(String integrator) {
        this.integrator = integrator;
    }

    public List<String> getFilename() {
        return filename;
    }

    public String getFilename(int index){
        return this.filename.get(index);
    }

    public void setFilename(String filename,int index) {
        if(this.filename.size() == 0 ){
            this.filename.add(index,filename);
        }
        if(!filename.contains(filename)){
            this.filename.add(index,filename);
        }else {
            this.filename.set(index, filename);
        }
        //this.filename.set(index,filename);
    }

    public String getBsdf() {
        return bsdf;
    }

    public void setBsdf(String bsdf) {
        this.bsdf = bsdf;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "camera=" + camera +
                ", integrator='" + integrator + '\'' +
                ", filename=" + filename +
                ", bsdf='" + bsdf + '\'' +
                '}';
    }
}
