package com.xiaoming.controller;

import com.xiaoming.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class ListenerController {

    JavaShell javaShell = new JavaShell();
    FileUpload fileUpload = new FileUpload();

    public static String filename = "cow.xml";
    public static String objFileName = "cow.obj";
    public static Parameter parameter = new Parameter();
    public static String filePath = "/home/xiaoming/apache-tomcat-8.5.46/webapps/ModelRecons/resource/cow.xml";

    @RequestMapping("/shell")
    @ResponseBody
    public Msg test(){
        int flag = 0;
        try {
            flag = javaShell.clearPic("/home/xiaoming/shellCommand/test.sh " + filename);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return Msg.fail().add("message", e.getMessage());
        }
        return Msg.success().add("filename", filename).add("objFileName",objFileName);
    }

    @RequestMapping("/fileload")
    @ResponseBody
    public Msg fileUplaod(MultipartFile file, HttpSession session){
        try {
            fileUpload.uploadFile(file,session);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return Msg.fail().add("message", e.getMessage());
        }
        this.filename = fileUpload.getFileName(file);
        filePath = "/home/xiaoming/apache-tomcat-8.5.46/webapps/ModelRecons/resource/" + filename;
        parameter = DomReader.readXML(filePath);
        objFileName = parameter.getFilename(0); // 获取 obj file name
        return Msg.success().add("filename", filename).add("objFileName",objFileName);
    }

    @GetMapping("/render")
    @ResponseBody
    public Msg renderParam(String render , String type){
        System.out.println(render + " : " + type) ;
        parameter = DomReader.readXML(filePath);
        parameter.setBsdf(type);
        parameter.setIntegrator(render);
        DomReader.updateXML(parameter, filePath, filePath);
        return Msg.success();
    }

}
