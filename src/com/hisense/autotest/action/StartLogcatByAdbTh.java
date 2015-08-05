
package com.hisense.autotest.action;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hisense.autotest.device.AdbOperation;

public class StartLogcatByAdbTh extends Thread {

    private static Logger logger = Logger.getLogger(StartLogcatByAdbTh.class);
    private AdbOperation adbOperation = new AdbOperation();

    private static String deviceIp = "";
    private Process logcatProc = null;
    private BufferedWriter bwLogcat = null;
    private volatile boolean endWriteLog = false;

    public StartLogcatByAdbTh(String strIp, String filePath) {
        deviceIp = strIp;

        try {
            if (!(new File(filePath).exists())) {
                if (!new File(filePath).getParentFile().exists()) {
                    new File(filePath).getParentFile().mkdirs();
                    new File(filePath).createNewFile();
                }
            }
            bwLogcat = new BufferedWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            logger.error("logcat文件写入异常。", e);
        }
    }

    public void run() {
        try {
            logcatProc = adbOperation.getLogcatProc(deviceIp);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    logcatProc.getInputStream(), "UTF-8"));
            String line = "";
            while (!endWriteLog) {
                line = bufferedReader.readLine();
                if (line == null || "".equals(line)) {
                    continue;
                }
                bwLogcat.write(line + "\n");
                bwLogcat.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (logcatProc != null) {
                    logcatProc.destroy();
                }
                if (bwLogcat != null) {
                    bwLogcat.close();
                }
            } catch (IOException e) {
            }
            logcatProc = null;
            bwLogcat = null;
        }
    }

    public void stopRun() {
        endWriteLog = true;
        try {
            if (logcatProc != null) {
                logcatProc.destroy();
            }
            if (bwLogcat != null) {
                bwLogcat.close();
            }
        } catch (IOException e) {
        }
    }

}
