
package com.hisense.autotest.device;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hisense.autotest.util.Utils;

public class AdbOperation {

    private static Logger logger = Logger.getLogger(AdbOperation.class);

    private Utils utils = new Utils();
    private static String tmpDevDir = "";
//    private static int adbConnStatus = -1;

    public AdbOperation() {
//        if (adbConnStatus == -1) {
//            adbConnStatus = utils.excuteCmdWait("adb start-server");
//        }
    }

    /*
     * 判断设备是否已连接
     */
    public boolean findDevice(String deviceIp) {
        boolean isFound = false;
        String[] cmdOutput = utils.excuteCmd("adb devices").split("\n");
        String[] deviceInfo;
        for (int i = 1; i < cmdOutput.length; i++) {
            deviceInfo = cmdOutput[i].split("\t");
            if (deviceInfo.length > 1 && "device".equals(deviceInfo[1])
                    && deviceIp.equals(deviceInfo[0])) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    /*
     * 获取已连接的设备IP数组
     */
    public String[] getDevices() {
        ArrayList<String> devList = new ArrayList<String>();
        String[] cmdOutput = utils.excuteCmd("adb devices").split("\n");
        String[] deviceInfo;
        for (int i = 1; i < cmdOutput.length; i++) {
            deviceInfo = cmdOutput[i].split("\t");
            if (deviceInfo.length > 1 && "device".equals(deviceInfo[1])
                    && deviceInfo[0].contains(":")) {
                devList.add(deviceInfo[0].substring(0, deviceInfo[0].indexOf(":")));
            }
        }
        int devCnt = devList.size();
        if (devCnt > 0) {
            String[] devices = new String[devList.size() + 1];
            devices[0] = "";
            ;
            for (int i = 0; i < devCnt; i++) {
                devices[i + 1] = devList.get(i);
            }
            return devices;
        } else {
            return new String[] { "" };
        }
    }

    /*
     * 设备连接adb
     */
    public void connDev(String deviceIp) {
        utils.excuteCmdWait("adb connect " + deviceIp);
        utils.excuteCmdWait("adb -s " + deviceIp + ":5555 root");
        utils.excuteCmdWait("adb connect " + deviceIp);
    }

    /*
     * 设备断开adb连接
     */
    public void disConnDev(String deviceIp) {
        utils.excuteCmdWait("adb disconnect " + deviceIp);
    }

    /*
     * 清空设备logcat
     */
    public void clearLogcat(String deviceIp) {
        if (deviceIp == null || "".equals(deviceIp)) {
            utils.excuteCmdWait("adb logcat -c");
        } else {
            utils.excuteCmdWait("adb -s " + deviceIp + ":5555 logcat -c");
        }
    }

    /*
     * 清空设备logcat
     */
    public Process getLogcatProc(String deviceIp) {
        if (deviceIp == null || "".equals(deviceIp)) {
            return utils.getExecCmdProc("adb logcat -v threadtime '*:V'");
        } else {
            return utils.getExecCmdProc("adb -s " + deviceIp + ":5555 logcat -v threadtime '*:V'");
        }
    }

    /*
     * 获取当前CPU使用信息
     */
    public String getCpuInfo(String deviceIp) {
        if (deviceIp == null || "".equals(deviceIp)) {
            return utils.excuteCmd("adb shell dumpsys cpuinfo");
        } else {
            return utils.excuteCmd("adb -s " + deviceIp + ":5555 shell dumpsys cpuinfo");
        }
    }

    /*
     * 获取当前内存信息
     */
    public String getMemInfo(String deviceIp) {
        if (deviceIp == null || "".equals(deviceIp)) {
            return utils.excuteCmd("adb shell procrank");
        } else {
            return utils.excuteCmd("adb -s " + deviceIp + ":5555 shell procrank");
        }
    }

    /*
     * 抓图
     */
    public void takeScreenshot(String deviceIp, String filePath) {
        try {
            if (!new File(filePath).getParentFile().exists()) {
                new File(filePath).getParentFile().mkdirs();
            }
            String tmpPath = tmpDevDir + "/tmpScreenshot.png";
//            logger.debug(tmpPath);
            if (deviceIp == null || "".equals(deviceIp)) {
                utils.excuteCmdWait("adb shell /system/bin/screencap -p " + tmpPath);
                utils.excuteCmdWait("adb pull " + tmpPath + " " + filePath);
                utils.excuteCmdWait("adb shell rm " + tmpPath);
            } else {
                utils.excuteCmdWait("adb -s " + deviceIp + ":5555 shell /system/bin/screencap -p "
                        + tmpPath);
                utils.excuteCmdWait("adb -s " + deviceIp + ":5555 pull " + tmpPath + " " + filePath);
                utils.excuteCmdWait("adb -s " + deviceIp + ":5555 shell rm " + tmpPath);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * 获取logcat缓冲内容
     */
    //-----------TODO--old 读取文件模式
    public String getLogcat(String deviceIp) {
        if (deviceIp == null || "".equals(deviceIp)) {
            return utils.excuteCmd("adb logcat -d -v threadtime '*:V'");
        } else {
            return utils.excuteCmd("adb -s " + deviceIp + ":5555 logcat -d -v threadtime '*:V'");
        }
    }

    /*
     * 将设备上的文件拷贝到本地
     */
    public void copyFile2Local(String deviceIp, String srcPath, String localPath) {
        if (deviceIp == null || "".equals(deviceIp)) {
            utils.excuteCmdWait("adb pull " + srcPath + " " + localPath);
        } else {
            utils
                    .excuteCmdWait("adb -s " + deviceIp + ":5555 pull " + srcPath + " " + localPath);
        }
    }

    /*
     * 输入键值
     */
    public void keyevent(String deviceIp, String keycode) {
        if (deviceIp == null || "".equals(deviceIp)) {
            utils.excuteCmdWait("adb shell input keyevent " + keycode);
        } else {
            utils.excuteCmdWait("adb -s " + deviceIp + ":5555 shell input keyevent " + keycode);
        }
    }

    public String getTmpDevDir() {
        return tmpDevDir;
    }

    public static void setTmpDevDir(String tmpDir) {
        tmpDevDir = tmpDir;
    }

}
