package com.xiaoming.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUpload {

    //获得文件名字
    public String getFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        String[] suffixNameArr = fileName.split("\\.");
//       String suffixName = suffixNameArr[suffixNameArr.length - 1];
        //return getTime() + "." + suffixName;
        return fileName;
    }

    //文件上传,返回文件路径
    public String uploadFile(MultipartFile file, HttpSession session) throws IOException {
        String FILE_PATH = session.getServletContext().getRealPath("/") + "resource";
        String fileName = getFileName(file);
        File tempFile = new File(FILE_PATH, fileName);

        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdir();
        }
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        file.transferTo(tempFile);  //将上传文件写到服务器上指定的文件。
        return "resource\\" + tempFile.getName();
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String nowTime = df.format(date);
        return nowTime;
    }
}
