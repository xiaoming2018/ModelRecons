package com.xiaoming.utils;

import java.io.*;

/**
 * @author sun xiaoming
 * @Title: CommandWaitForThread
 * @ProjectName Reconstruction
 * @date 2019/10/17 16:25
 */
public class CommandWaitForThread extends Thread {

    private String cmd;
    private boolean finish = false;
    private int exitValue = -1;
    private static final String executeShellLogFile =  "/home/xiaoming/log/" + "executeShell.log";

    public CommandWaitForThread(String cmd){
        this.cmd = cmd;
    }

    public void run(){

        try {
            ProcessBuilder builder = new ProcessBuilder("/bin/chmod","777","/home/xiaoming/shellCommand");
            Process processBuilder = builder.start();
            processBuilder.waitFor();

            Process process = Runtime.getRuntime().exec(cmd);
            //信息输出检测
            BufferedReader readerTest = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String data;

            while ((data = readerTest.readLine()) != null) {
                System.out.println(data);
            }

            // 写出脚本执行的过程信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()),1024);
            if (reader != null) {
                OutputStreamWriter outputStreamWriter = null;
                try {
                    //将Shell的执行情况输出到日志文件中
                    OutputStream outputStream = new FileOutputStream(executeShellLogFile);
                    outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    outputStreamWriter.write(reader.toString());
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    outputStreamWriter.close();
                }
            }
            this.exitValue = process.waitFor();
        }catch (Throwable e){
            exitValue = 110;
        }finally {
            this.setFinish(true);
        }
    }

    public boolean isFinish(){
        return finish;
    }

    public void setFinish(boolean finish){
        this.finish = finish;
    }

    public int getExitValue(){
        return exitValue;
    }
}
