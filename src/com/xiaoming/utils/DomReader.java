package com.xiaoming.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DomReader {
//    public static void main(String[] args) throws Exception {
//        int index = 0;
//        Camera camera = new Camera();
//        Parameter parameter = new Parameter();
//        SAXReader reader = new SAXReader();
//        Document document = reader.read(new File("src/source/dragon1.xml"));
//        Element rootElement = document.getRootElement();
//        Iterator iterator =  rootElement.elementIterator();
//        while (iterator.hasNext()){
//            Element temp = (Element) iterator.next();
//            List<Attribute> attributes = temp.attributes();
//            for (Attribute attribute : attributes){
//                if(temp.getName() == "integrator" && attribute.getName() == "type"){
//                    parameter.setIntegrator(attribute.getValue());
//                }
//            }
//            boolean flag = false;
//            Iterator iterator1 = temp.elementIterator();
//            while(iterator1.hasNext()){
//                Element tempChild = (Element) iterator1.next();
//                List<Attribute> attributes1 = tempChild.attributes();
//                for (Attribute attribute:attributes1){
//                    if(attribute.getName().equals("name") && attribute.getValue().equals("filename")){
//                        flag = true;
//                    }
//                    if(tempChild.getName() == "bsdf" && attribute.getName() == "type"){
//                        parameter.setBsdf(attribute.getValue());
//                    }
//                    if(flag && attribute.getName() == "value"){
//                        parameter.setFilename(attribute.getValue(),index++);
//                        flag = false;
//                    }
//                }
//                Iterator iterator2 = tempChild.elementIterator();
//                while(iterator2.hasNext()){
//                    Element temp2Child = (Element) iterator2.next();
//                    List<Attribute> attributes2 = temp2Child.attributes();
//                    for (Attribute attribute:attributes2){
//                        if(attribute.getName() == "target"){
//                            camera.setTarget(attribute.getValue());
//                        } else if(attribute.getName() == "origin"){
//                            camera.setOrigin(attribute.getValue());
//                        } else if(attribute.getName() == "up"){
//                            camera.setUp(attribute.getValue());
//                        }
//                    }
//                }
//            }
//        }
//        parameter.setCamera(camera);
//        System.out.println(parameter.toString());
//
//        parameter.setBsdf("1111");
//        parameter.setFilename("sunxm",0);
//        boolean bool = updateXML(parameter,"src/source/dragon.xml", "src/source/dragon1.xml");
//        System.out.println(bool);
//    }

    public static Parameter readXML(String xmlFile){
        int index = 0;
        Camera camera = new Camera();
        Parameter parameter = new Parameter();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(xmlFile));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        Iterator iterator =  rootElement.elementIterator();
        while (iterator.hasNext()){
            Element temp = (Element) iterator.next();
            List<Attribute> attributes = temp.attributes();
            for (Attribute attribute : attributes){
                if(temp.getName() == "integrator" && attribute.getName() == "type"){
                    parameter.setIntegrator(attribute.getValue());
                }
            }
            boolean flag = false;
            Iterator iterator1 = temp.elementIterator();
            while(iterator1.hasNext()){
                Element tempChild = (Element) iterator1.next();
                List<Attribute> attributes1 = tempChild.attributes();
                for (Attribute attribute:attributes1){
                    if(attribute.getName().equals("name") && attribute.getValue().equals("filename")){
                        flag = true;
                    }
                    if(tempChild.getName() == "bsdf" && attribute.getName() == "type"){
                        parameter.setBsdf(attribute.getValue());
                    }
                    if(flag && attribute.getName() == "value"){
                        parameter.setFilename(attribute.getValue(),index++);
                        flag = false;
                    }
                }
                Iterator iterator2 = tempChild.elementIterator();
                while(iterator2.hasNext()){
                    Element temp2Child = (Element) iterator2.next();
                    List<Attribute> attributes2 = temp2Child.attributes();
                    for (Attribute attribute:attributes2){
                        if(attribute.getName() == "target"){
                            camera.setTarget(attribute.getValue());
                        } else if(attribute.getName() == "origin"){
                            camera.setOrigin(attribute.getValue());
                        } else if(attribute.getName() == "up"){
                            camera.setUp(attribute.getValue());
                        }
                    }
                }
            }
        }
        parameter.setCamera(camera);
        return parameter;
    }

    public static boolean updateXML(Parameter parameter, String oldFile, String newFile){

        int index = 0;
        Document document = null;
        Camera camera = new Camera();
        camera = parameter.getCamera();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(oldFile));
            Element rootElement = document.getRootElement();
            Iterator iterator =  rootElement.elementIterator();
            while (iterator.hasNext()){
                Element temp = (Element) iterator.next();
                List<Attribute> attributes = temp.attributes();
                for (Attribute attribute : attributes){
                    if(temp.getName() == "integrator" && attribute.getName() == "type"){
                        //parameter.setIntegrator(attribute.getValue());
                        attribute.setValue(parameter.getIntegrator());
                    }
                }
                boolean flag = false;
                Iterator iterator1 = temp.elementIterator();
                while(iterator1.hasNext()){
                    Element tempChild = (Element) iterator1.next();
                    List<Attribute> attributes1 = tempChild.attributes();
                    for (Attribute attribute:attributes1){
                        if(attribute.getName().equals("name") && attribute.getValue().equals("filename")){
                            flag = true;
                        }
                        if(tempChild.getName() == "bsdf" && attribute.getName() == "type"){
                            //parameter.setBsdf(attribute.getValue());
                            attribute.setValue(parameter.getBsdf());
                        }
                        if(flag && attribute.getName() == "value"){
                            //parameter.setFilename(attribute.getValue(),index++);
                            attribute.setValue(parameter.getFilename(index));
                            flag = false;
                        }
                    }
                    Iterator iterator2 = tempChild.elementIterator();
                    while(iterator2.hasNext()){
                        Element temp2Child = (Element) iterator2.next();
                        List<Attribute> attributes2 = temp2Child.attributes();
                        for (Attribute attribute:attributes2){
                            if(attribute.getName() == "target"){
                                //camera.setTarget(attribute.getValue());
                                attribute.setValue(camera.getTarget());
                            } else if(attribute.getName() == "origin"){
                                //camera.setOrigin(attribute.getValue());
                                attribute.setValue(camera.getOrigin());
                            } else if(attribute.getName() == "up"){
                                //camera.setUp(attribute.getValue());
                                attribute.setValue(camera.getUp());
                            }
                        }
                    }
                }
            }
            System.out.println("new parameter:" + parameter.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println();
        // xml文件输出
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(new File(newFile)));
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
