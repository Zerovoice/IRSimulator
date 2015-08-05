package com.hisense.autotest.action;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.hisense.autotest.automation.PgMIR;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.bean.ExecCondInfo;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;

public class ExecMScriptTh extends ExecScript {

    private static Logger logger = Logger.getLogger(ExecMScriptTh.class);

    private LogRecorderTh logRecorderTh = null;// 记录日志的进程
    private RecordResrcTh recordResrcTh = null;// 折线图进程
    private LogcatParserTh logParserTh = null;// logcat验证进程

    private DSerialPort spIR;
    private DSerialPort spDev;
    private Table tblScript;
    private int scriptItemCnt = 0;
    private int defaultFileIndex = -1;
    private int loopTimes = 0;
    private float fixedInterval = 0;
    private String logFilePath = "";
    private int refreshRscInt;
    private ArrayList<String> gAssertList = new ArrayList<String>();
    private ArrayList<String> gAssertExistFlag = new ArrayList<String>();
    private int ucInterval;

    private float actInterval = 0;
    private String currItemKey;
    private String currItemCont;
    private String currItemInt = "0";
    private String currItemAss;

    private int mode;
    private static boolean execStatus = false;
    private static boolean startRun = false;
    private volatile boolean stopRun = false;

    public ExecMScriptTh(ExecCondInfo execCondInfo) {
        super(execCondInfo);
        tblScript = execCondInfo.getTblScript();
        loopTimes = execCondInfo.getLoopTimes();
        spIR = execCondInfo.getSpIR();
        spDev = execCondInfo.getSpDev();
        fixedInterval = execCondInfo.getFixInterval();
        logFilePath = execCondInfo.getLogFilePath();
        refreshRscInt = execCondInfo.getRefreshRscInt();
        mode = execCondInfo.getMode();
        gAssertList = execCondInfo.getgAssertList();
        gAssertExistFlag = execCondInfo.getgAssertExistFlag();
        ucInterval = execCondInfo.getUcInterval();
        isLinuxTV = execCondInfo.isLinuxTV();
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                scriptItemCnt = tblScript.getItemCount();
                defaultFileIndex = tblScript.getSelectionIndex();
            }
        });
        if (mode == Resources.MODE_MANUAL) {
            currStepIndex = -1;
            initTestRpt(tblScript);
        }
        testRptPath = "";

        execStatus = false;
        startRun = false;
        stopRun = false;
    }

    public boolean getExecStatus() {
        return execStatus;
    }

    public boolean getStartRun() {
        return startRun;
    }

    public boolean getStopRun() {
        return stopRun;
    }

    public void run() {
        if (stopRun || execStatus) {
            return;
        }
        execStatus = true;
        startRun = true;
        if (mode == Resources.MODE_MANUAL) {
            logger.debug("录制回放模式 执行进程 开始运行。");
        }
        try {
            prepareExec();// 执行前的准备工作
            int actLoop = 0;
            runErrMsg = "";
            while (!stopRun && (loopTimes == 0 || actLoop < loopTimes)) {
                actLoop++;
                if (actLoop > 1) {
                    utils.pause(ucInterval);
                    String[] step = testRst.getRstStepNodes(currStepIndex);
                    step[Resources.SCRIPT_COL_INTERVAL - 1] = String
                            .valueOf(Float
                                    .parseFloat(step[Resources.SCRIPT_COL_INTERVAL - 1])
                                    + ucInterval);
                    testRst.setRstStepNodes(currStepIndex, step);
                    initTestRptDup(tblScript);
                }
                for (currSelIndex = 0; currSelIndex < scriptItemCnt; currSelIndex++) {
                    currStepIndex++;
                    if (logParserTh != null && !logParserTh.getAssertRst()) {
                        stopRun = true;
                    }
                    if (stopRun) {
                        break;
                    }
                    if (currSelIndex < defaultFileIndex) {
                        continue;
                    } else {
                        defaultFileIndex = -1;
                    }
                    getDataFromPg();
                    if (!currItemKey.contains("-")) {
                        if (isLinuxTV) {
                            spDev.write("set_ir_mode " + currItemKey);
                            logger.debug("发送的键值为 set_ir_mode " + currItemKey);
                        } else {
                            adbOpr.keyevent(deviceIp, currItemKey);
                        }

                    } else {
                        inputIRKey();
                    }
                    if (logParserTh != null && !logParserTh.getAssertRst()) {
                        stopRun = true;
                    }
                    if (stopRun) {
                        break;
                    }
                    if (fixedInterval > 0) {
                        logger.debug("interval = " + fixedInterval);
                        utils.pauseWithE(fixedInterval);
                    } else if (actInterval > 0) {
                        logger.debug("interval = " + actInterval);
                        utils.pauseWithE(actInterval);
                    }
                    if (logParserTh != null && !logParserTh.getAssertRst()) {
                        stopRun = true;
                    }
                    if (stopRun) {
                        break;
                    }
                    // STEP验证
                    if (!doStepAsserts(currItemAss)) {
                        testRst.setStepFlag(currStepIndex, false);
                        break;
                    } else {
                        testRst.setStepFlag(currStepIndex, true);
                    }
                }
                //回到表中的第一个
                Display.getDefault().syncExec(new Runnable() {

                    @Override
                    public void run() {
                        tblScript.select(0);
                    }
                });
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            runErrMsg += e.getMessage();
        } finally {
            teardown();
            if (mode == Resources.MODE_MANUAL) {
                SmartAuto.amTh.clearMonitorDevices();
                logger.debug("录制回放模式 执行进程 结束运行。");
            }
        }
    }

    /**
     * 获取页面控件中的数据
     */
    private void getDataFromPg() {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                tblScript.select(currSelIndex);
                tblScript.setTopIndex(currSelIndex);
                TableItem currTI = tblScript.getItem(currSelIndex);
                currItemKey = currTI.getText(Resources.SCRIPT_COL_KEY);
                currItemCont = currTI.getText(Resources.SCRIPT_COL_CONTENT);
                logger.debug(currItemKey + "         " + currItemCont);
                currItemInt = currTI.getText(Resources.SCRIPT_COL_INTERVAL);
                currItemAss = currTI.getText(Resources.SCRIPT_COL_ASSERT);
            }
        });
        if (!"".equals(currItemInt)) {
            actInterval = Float.parseFloat(currItemInt);
        } else {
            actInterval = 0;
        }
        testRst.setRstStepNodes(currStepIndex, new String[] { currItemKey,
                currItemCont, String.valueOf(actInterval), currItemAss });
    }

    /*
     * 按下红外键
     */
    private void inputIRKey() {
        String[] currKeys = currItemKey.split("-");
        byte[] inputvalues = new byte[5];
        inputvalues[0] = (byte) (Integer.parseInt(currKeys[0], 16));
        inputvalues[1] = (byte) (Integer.parseInt(currKeys[1], 16));
        inputvalues[2] = (byte) (Integer.parseInt(currKeys[2], 16));
        inputvalues[3] = (byte) (Integer.parseInt(currKeys[3], 16));
        inputvalues[4] = (byte) (Integer.parseInt(currKeys[4], 16));
        spIR.write(inputvalues);
    }

    /*
     * 执行结束前的操作
     */
    private void teardown() {
        if (logParserTh != null) {
            runErrMsg += logParserTh.getRstErrMsg();
        }
        thsStopRun();// 停止进程的运行
        if (mode == Resources.MODE_MANUAL) {
            testRst.addErrorMsg(runErrMsg);
            String screenshotPath = "";
            int testRstType = Resources.TEST_RST_NOTRUN;
            if (runErrMsg != null && !"".equals(runErrMsg)) {
                testRstType = Resources.TEST_RST_FAIL;
                if (deviceIp != null && !"".equals(deviceIp)) {
                    screenshotPath = testRstScreenFolder
                            + utils.getCurrTime(Resources.FORMAT_TIME_PATH)
                            + ".png";
                    if (!new File(testRstScreenFolder).exists()) {
                        new File(testRstScreenFolder).mkdirs();
                    }
                    // adbOpr.takeScreenshot(deviceIp, screenshotPath);
                    testRst.addPic(
                            currStepIndex,
                            "./"
                                    + screenshotPath.substring(testRstTimePath
                                            .length()));
                }
            } else {
                testRstType = Resources.TEST_RST_PASS;
                testRst.setGlobalFlag(true);
            }
            writeTestRpt("tmp.xml", testRst);
            writeSummary(testRstType, runErrMsg, screenshotPath);
            PgMIR.setMExecStatus(false, runErrMsg);
        }
        execStatus = false;
    }

    /*
     * 执行前的准备：页面控件、其他操作线程
     */
    private void prepareExec() throws Exception {
        if (mode != Resources.MODE_MANUAL) {
            return;
        }
        // 页面控件可用性设置
        PgMIR.setMExecStatus(true, "");
        // 资源监控
        if (deviceIp != null && !"".equals(deviceIp)) {
            SmartAuto.amTh.addMonitorDevice(deviceIp);
            // 清空设备logcat
            adbOpr.clearLogcat(deviceIp);
            recordResrcTh = new RecordResrcTh(deviceIp, refreshRscInt,
                    Resources.MODE_MANUAL, testRstTimePath);
            recordResrcTh.start();
        }
        if (logFilePath != null && !"".equals(logFilePath)) {
            File f = new File(logFilePath);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            // 记录日志
            if (spDev != null) {
                logRecorderTh = new LogRecorderTh(spDev, logFilePath);
            } else if (deviceIp != null && !"".equals(deviceIp)) {
                logRecorderTh = new LogRecorderTh(deviceIp, logFilePath);
            }
            if (logRecorderTh != null) {
                logRecorderTh.start();
                // 日志验证
                if (gAssertList != null && gAssertList.size() > 0) {
                    logParserTh = new LogcatParserTh(logFilePath, gAssertList,
                            gAssertExistFlag);
                    logParserTh.start();
                }
            }
        }
    }

    /*
     * 停止所有进程
     */
    private void thsStopRun() {
        if (logRecorderTh != null) {
            logRecorderTh.stopRun();
            logRecorderTh.interrupt();
        }
        if (recordResrcTh != null) {
            recordResrcTh.stopRun();
            recordResrcTh.interrupt();
        }
        if (logParserTh != null) {
            logParserTh.stopRun();
            logParserTh.interrupt();
        }
        logRecorderTh = null;
        recordResrcTh = null;
        logParserTh = null;
    }

    public void stopRun() {
        if (mode == Resources.MODE_MANUAL) {
            logger.debug("录制回放模式 执行进程 停止运行。");
        }
        stopRun = true;
    }

}
