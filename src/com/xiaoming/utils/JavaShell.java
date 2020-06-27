package com.xiaoming.utils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sun xiaoming
 * @Title: JavaShell
 * @ProjectName Reconstruction
 * @date 2019/10/14 19:05
 */
public class JavaShell {
    // 基本路径（绝对路径）
    private static final String basePath = "/home/xiaoming";

    // 记录shell执行情况的日志文件的位置（绝对路径）
    private static final String executeShellLogFile = basePath + "/log/" + "executeShell.log";

    // 字符数组 command 执行
    public int executeShell(String shellCommand) throws IOException {
        int success;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            stringBuffer.append(dateFormat.format(new Date())).append("准备执行shell命令").append(shellCommand).append("\r\n");
            Process pid;
            String[] cmd = {"bash", "-c", "mitsuba /home/xiaoming/imdir/pic/"};
            // 执行shell 命令
            pid = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pid.getInputStream()));
            String data;
            while ((data = reader.readLine()) != null) {
                System.out.println(data);
            }
            if (pid != null) {
                stringBuffer.append("进程号：").append(pid.toString()).append("\r\n");
                //bufferedReader用于读取Shell的输出内容
                bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
                pid.waitFor();
            } else {
                stringBuffer.append("没有pid\r\n");
            }
            stringBuffer.append(dateFormat.format(new Date())).append("Shell命令执行完毕\r\n执行结果为：\r\n");
            String line;
            //读取Shell的输出内容，并添加到stringBuffer中
            while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\r\n");
            }
        } catch (Exception ioe) {
            stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage()).append("\r\n");
        } finally {
            if (bufferedReader != null) {
                OutputStreamWriter outputStreamWriter = null;
                try {
                    bufferedReader.close();
                    //将Shell的执行情况输出到日志文件中
                    OutputStream outputStream = new FileOutputStream(executeShellLogFile);
                    outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    outputStreamWriter.write(stringBuffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    outputStreamWriter.close();
                }
            }
            success = 1;
        }
        return success;
    }

    // reconstruct 脚本文件执行 以及脚本执行检测
    public int callScript(String script) {
        try {
            String cmd = "sh " + script;
            // 启动独立线程等待process 执行完成
            CommandWaitForThread commandWaitForThread = new CommandWaitForThread(cmd);
            commandWaitForThread.start();
            while (!commandWaitForThread.isFinish()) {
                Thread.sleep(10000);
            }
            if (commandWaitForThread.getExitValue() != 0) {
                throw new Exception("shell " + script + "执行失败,exitValue =" + commandWaitForThread.getExitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 400;
        }
        return 200;
    }

    // 图片仓库清空
    public int clearPic(String shellCommand) throws IOException {
        String command = "sh " + shellCommand;
        int success = 0;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        BufferedReader stdError = null;
        // 格式化日期，记录日志时使用
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        try {
            stringBuffer.append(dateFormat.format(new Date())).append("准备执行Shell命令 ").append(shellCommand).append("\r\n");
            Process process = null;
            process = Runtime.getRuntime().exec(command);
            if (process != null) {
                stringBuffer.append("进程号：").append(process.toString()).append("\r\n");
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                int status = process.waitFor();
                if (status != 0) {
                    stringBuffer.append("shell脚本执行失败！");
                } else {
                    stringBuffer.append("shell脚本执行成功！");
                }
            } else {
                stringBuffer.append("process创建失败！\r\n");
            }
            stringBuffer.append(dateFormat.format(new Date())).append("shell命令执行完毕\r\n 执行结果为：\r\n");
            // 标准输入流的内容写到stringBuffer中
            String line1 = null;
            while (bufferedReader != null && (line1 = bufferedReader.readLine()) != null) {
                stringBuffer.append(line1).append("\r\n");
            }
            String line2 = null;
            while (stdError != null && (line2 = stdError.readLine()) != null) {
                stringBuffer.append(line2).append("\r\n");
            }
            success = 200;
        } catch (Exception e) {
            stringBuffer.append("执行shell命令时发生异常：\r\n").append(e.getMessage()).append("\r\n");
            success = 400;
        } finally {
            if (bufferedReader != null) {
                OutputStreamWriter outputStreamWriter = null;
                try {
                    bufferedReader.close();
                    // shell 执行结果输出到日志文件中
                    OutputStream outputStream = new FileOutputStream(executeShellLogFile);
                    outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    outputStreamWriter.write(stringBuffer.toString());
                    System.out.println("stringBuffer.toString() :" + stringBuffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    success = 400;
                } finally {
                    outputStreamWriter.close();
                }
            }
            return success;
        }
    }
}
