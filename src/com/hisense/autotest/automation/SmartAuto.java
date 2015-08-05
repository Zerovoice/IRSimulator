
package com.hisense.autotest.automation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.hisense.autotest.action.AdbMonitorThread;
import com.hisense.autotest.action.ExecEScriptTh;
import com.hisense.autotest.action.ExecMScriptTh;
import com.hisense.autotest.action.ExecRScriptTh;
import com.hisense.autotest.action.ExecTScriptTh;
import com.hisense.autotest.action.RecordResrcTh;
import com.hisense.autotest.bean.ExecCondInfo;
import com.hisense.autotest.bean.RandomCondInfo;
import com.hisense.autotest.bean.ToolInfo;
import com.hisense.autotest.chart.RealTimeChartECPU;
import com.hisense.autotest.chart.RealTimeChartEMem;
import com.hisense.autotest.chart.RealTimeChartMCPU;
import com.hisense.autotest.chart.RealTimeChartMMem;
import com.hisense.autotest.chart.RealTimeChartRCPU;
import com.hisense.autotest.chart.RealTimeChartRMem;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.device.AdbOperation;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.Utils;

public class SmartAuto {

    private static Logger logger = Logger.getLogger(SmartAuto.class);

    protected static Utils utils = new Utils();

    protected static String rootPath = System.getProperty("user.dir") + File.separator;
    protected static String testRstsPath = rootPath + Resources.TEST_RST_PATH + File.separator;
    public static String testRstTimePath = testRstsPath + File.separator
            + Resources.FORMAT_TIME_PATH + File.separator;
    protected static String screenshotPath = testRstTimePath + File.separator + "screenshot"
            + File.separator;
    protected static String logFilePath = "";

    public static AdbMonitorThread amTh = new AdbMonitorThread();// 设备adb连接监控
    protected static DSerialPort spIR = new DSerialPort(Resources.TYPE_COM_IR);
    protected static DSerialPort spDev = new DSerialPort(Resources.TYPE_COM_DEV);
    public static DSerialPort getSpDev() {
		return spDev;
	}

	protected static String[] comIR = null;
    protected static String[] comDev = null;
    protected static AdbOperation adbOperation = new AdbOperation();

    protected static Properties properties = null;
    protected static Element settings = null;
    protected static Hashtable<String, String> htEncodeMap = new Hashtable<String, String>();
    protected static Hashtable<String, String> htSyscodeMap = new Hashtable<String, String>();
    protected static ExecMScriptTh execMTh = null;
    protected static ExecRScriptTh execRTh = null;
    protected static ExecEScriptTh execETh = null;
    protected static ExecTScriptTh execTTh = null;
    protected static RecordResrcTh recordResrcTh = null;
    protected static ToolInfo toolInfo = new ToolInfo();// 工具设置信息
    protected static RandomCondInfo randCondInfo = new RandomCondInfo();// 随机条件设置信息
    protected static ArrayList<String> gAssertList = new ArrayList<String>();// 全局验证信息
    protected static ArrayList<String> gAssertExistFlag = new ArrayList<String>();// 全局验证信息标志位

    public static RealTimeChartMCPU rtcMCPU = null;
    public static RealTimeChartMMem rtcMMem = null;
    public static RealTimeChartRCPU rtcRCPU = null;
    public static RealTimeChartRMem rtcRMem = null;
    public static RealTimeChartECPU rtcECPU = null;
    public static RealTimeChartEMem rtcEMem = null;
    protected static int refreshRscInt = 1;

    protected static boolean connIRComSuccss = false;
    protected static boolean connDevComSuccss = false;
    protected static Date pressKeyTime = null;
    protected static String selIRSerialPort = "";
    protected static String selDevSerialPort = "";
    protected static String settedDeviceIp = "";
    protected static String scriptRootPath = "";

    // 增添 Kenneth 支持loewe whale电视的电视串口测试
//    protected static boolean isWhaleSendIRCode = false;
	// 增添 Kenneth 支持SX6 UHD电视的电视串口测试
//    protected static boolean isSX6SendIRCode = false;//取消支持SX6 UHD
    protected static boolean isLinuxTV = false;

    public static boolean isLinuxTV() {
		return isLinuxTV;
	}

	public static void setLinuxTV(boolean isLinuxTV) {
		SmartAuto.isLinuxTV = isLinuxTV;
	}

//	public static boolean isWhaleSendIRCode() {
//		return isWhaleSendIRCode;
//	}
//
//	public static void setWhaleSendIRCode(boolean isWhaleSendIRCode) {
//		SmartAuto.isWhaleSendIRCode = isWhaleSendIRCode;
//		SmartAuto.isLinuxTV=isWhaleSendIRCode;
//	}
	// shaodong
    protected static ArrayList<String> row = new ArrayList<String>();
    protected static ArrayList<String> column = new ArrayList<String>();

    /**
     * 记录按键操作
     */
    public static String recordKeyEvent(Table tblScript, String deviceIp, String strFixedInt,
            int encode, String propKey, String name) {
        String errMsg = "";
        try {
            String key = Resources.ENCODE_PREFIX[encode] + propKey;
            String keyEncode = htEncodeMap.get(key);
            if (keyEncode == null || "".equals(keyEncode)) {
                errMsg = "键值获取失败。" + key;
                return errMsg;
            }
            String strInt = "";
            Date now = new Date();
            int itemCnt = tblScript.getItemCount();
            if (pressKeyTime != null && itemCnt > 0) {
                if (strFixedInt != null && !"".equals(strFixedInt)) {
                    strInt = strFixedInt;
                } else {
                    strInt = String.format("%.1f",
                            (now.getTime() - pressKeyTime.getTime()) / 1000.0);
                }
                TableItem lastTableItem = tblScript.getItem(itemCnt - 1);
                int intervalColIndex = Resources.SCRIPT_COL_INTERVAL;// 时间间隔列
                if (lastTableItem.getText(intervalColIndex) == null
                        || "".equals(lastTableItem.getText(intervalColIndex))) {
                    lastTableItem.setText(intervalColIndex, strInt);
                }
            }
            pressKeyTime = now;
            TableItem tableItem = new TableItem(tblScript, SWT.NONE);
            tableItem.setText(new String[] { String.valueOf(itemCnt + 1), keyEncode, name, "", "",
                    propKey });
            tblScript.setTopIndex(itemCnt + 1);

            // 同步执行按键操作
            if (encode == Resources.ENCODE_KEYCODE) {
                if (isLinuxTV) {// KENNETH whale TV
                    if (connDevComSuccss) {
                        spDev.write("set_ir_mode " + keyEncode);
                        logger.debug("发送的键值为 set_ir_mode " + keyEncode);
                    }
                } else {
                    if (!"".equals(deviceIp)) {
                        adbOperation.keyevent(deviceIp, keyEncode);
                    }
                }

            } else if (encode == Resources.ENCODE_NEC || encode == Resources.ENCODE_RC5) {
                if (connIRComSuccss) {
                    String[] currItemKeys = tableItem.getText(Resources.SCRIPT_COL_KEY).split("-");
                    byte[] inputvalues = new byte[5];
                    inputvalues[0] = (byte) (Integer.parseInt(currItemKeys[0], 16));
                    inputvalues[1] = (byte) (Integer.parseInt(currItemKeys[1], 16));
                    inputvalues[2] = (byte) (Integer.parseInt(currItemKeys[2], 16));
                    inputvalues[3] = (byte) (Integer.parseInt(currItemKeys[3], 16));
                    inputvalues[4] = (byte) (Integer.parseInt(currItemKeys[4], 16));
                    spIR.write(inputvalues);
                }
            }
        } catch (Exception e) {
            errMsg = e.getMessage();
            logger.error(errMsg, e);
        }
        return errMsg;
    }

    /**
     * 记录接收器的按键操作
     */
    public static String receivedKeyEvent(Table tblScript, String strFixedInt, String keyEncode) {
        String errMsg = "";
        try {
            String strInt = "";
            Date now = new Date();
            int itemCnt = tblScript.getItemCount();
            if (pressKeyTime != null && itemCnt > 0) {
                if (strFixedInt != null && !"".equals(strFixedInt)) {
                    strInt = strFixedInt;
                } else {
                    strInt = String.format("%.1f",
                            (now.getTime() - pressKeyTime.getTime()) / 1000.0);
                }
                TableItem lastTableItem = tblScript.getItem(itemCnt - 1);
                int intervalColIndex = Resources.SCRIPT_COL_INTERVAL;// 时间间隔列
                if (lastTableItem.getText(intervalColIndex) == null
                        || "".equals(lastTableItem.getText(intervalColIndex))) {
                    lastTableItem.setText(intervalColIndex, strInt);
                }
            }
            pressKeyTime = now;
            TableItem tableItem = new TableItem(tblScript, SWT.NONE);
            tableItem
                    .setText(new String[] { String.valueOf(itemCnt + 1), keyEncode, "", "", "", "" });
            tblScript.setTopIndex(itemCnt + 1);
        } catch (Exception e) {
            errMsg = e.getMessage();
            logger.error(errMsg, e);
        }
        return errMsg;
    }

    /**
     * 获取三个系统码，存入htSyscodeMap
     */
    protected static void getSysMapInfo() {
        htSyscodeMap.clear();
        Enumeration<Object> propKeys = properties.keys();
        String propKey = "";
        while (propKeys.hasMoreElements()) {
            propKey = (String) propKeys.nextElement();
            if (propKey.endsWith("_ALL_KEYS") || propKey.endsWith("_PART_KEYS")
                    || propKey.endsWith("_ALL_KEYS_NAME") || propKey.endsWith("_PART_KEYS_NAME")) {
                continue;
            }
            htSyscodeMap.put(propKey, properties.getProperty(propKey));
        }

    }

    /**
     * 获取所有properties文件中的键值信息，存入htEncodeMap和htEditcodeMap中。
     */
    protected static void getKeyMapInfo(String propPath) {
        htEncodeMap.clear();
        getAllOtherKeyMap(properties);
        for (int i = 0; i < column.size(); i++) {
            Properties otherProp = utils.getProperties(propPath + File.separator + column.get(i)
                    + ".properties");
            getAllOtherKeyMap(otherProp);
        }
    }

    /**
     * 读取遥控器编码信息
     */
    public static void getAllOtherKeyMap(Properties properties) {
        Enumeration<Object> propKeys = properties.keys();
        String propKey = "";
        while (propKeys.hasMoreElements()) {
            propKey = (String) propKeys.nextElement();
            if (propKey.endsWith("_ALL_KEYS") || propKey.endsWith("_PART_KEYS")
                    || propKey.endsWith("_ALL_KEYS_NAME") || propKey.endsWith("_PART_KEYS_NAME")) {
                continue;
            } else if (propKey.startsWith("NEC_FAC_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_NEC,
                                propKey.substring("NEC_".length()), true, htSyscodeMap));
            } else if (propKey.startsWith("RC5_FAC_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_RC5,
                                propKey.substring("RC5_".length()), true, htSyscodeMap));
            } else if (propKey.startsWith("KEYCODE_FAC_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_KEYCODE,
                                propKey.substring("KEYCODE_".length()), true, htSyscodeMap));
            } else if (propKey.startsWith("NEC_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_NEC,
                                propKey.substring("NEC_".length()), false, htSyscodeMap));
            } else if (propKey.startsWith("RC5_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_RC5,
                                propKey.substring("RC5_".length()), false, htSyscodeMap));
            } else if (propKey.startsWith("KEYCODE_")) {
                htEncodeMap.put(
                        propKey,
                        utils.getKeyEncode(properties, Resources.ENCODE_KEYCODE,
                                propKey.substring("KEYCODE_".length()), false, htSyscodeMap));
            }
        }

    }

    /**
     * 读取配置文件信息
     */
    @SuppressWarnings("unchecked")
    protected static void getSettingsInfo() {
        String devTmpDir = "";
        try {
            devTmpDir = settings.element("DevTmpDir").getTextTrim();
        } catch (Exception e) {
            logger.warn("配置文件Settings.xml中没有配置DevTmpDir，或者配置信息错误，使用默认值 /sdcard。");
//            logger.error(e.getMessage(), e);
            devTmpDir = "/sdcard";
        }
        AdbOperation.setTmpDevDir(devTmpDir);
        try {
            refreshRscInt = Integer.parseInt(settings.element("ResrcWatchRate").getTextTrim());
        } catch (Exception e) {
            logger.warn("配置文件Settings.xml中配置ResrcWatchRate信息错误，使用默认值 1。");
            logger.error(e.getMessage(), e);
            refreshRscInt = 1;
        }
        try {
            List<Element> assList = settings.element("AssertList").elements("assertlog");
            if (assList != null && assList.size() > 0) {
                for (int i = 0; i < assList.size(); i++) {
                    gAssertList.add((assList.get(i)).getText());
                    gAssertExistFlag.add(assList.get(i).attributeValue("isexist"));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 保存脚本
     */
    protected boolean saveScript(Shell shell, Table tblScript, String filePath, int mode) {
        BufferedWriter bwScript = null;
        try {
            if (mode != Resources.MODE_EXCUTE && new File(filePath).exists()) {
                if (!showSelMsg(shell, "用例文件已存在，是否覆盖？", SWT.ICON_INFORMATION)) {
                    return false;
                }
            }
//            bwScript = new BufferedWriter(new FileWriter(filePath));
            bwScript = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),
                    "UTF-8"));
            TableItem[] tiScript = tblScript.getItems();
            int clnCnt = tblScript.getColumnCount();
            for (TableItem ti : tiScript) {
                for (int i = 1; i < clnCnt; i++) {
                    bwScript.write(ti.getText(i));
                    if (i < clnCnt - 1) {
                        bwScript.write(",");
                    } else {
                        bwScript.write("\n");
                    }
                }
            }
            bwScript.close();
            bwScript = null;
            logger.debug("xml测试用例文件的保存位置:" + filePath);
            String xmlFilePath = filePath.substring(0, filePath.length() - ".csv".length())
                    + ".xml";
            createXmlUC(tblScript, xmlFilePath);
            showMsg(shell, "保存成功。", SWT.ICON_INFORMATION);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            showMsg(shell, "保存失败。\n" + e.getMessage(), SWT.ICON_INFORMATION);
            return false;
        } finally {
            try {
                if (bwScript != null) {
                    bwScript.close();
                }
            } catch (IOException e1) {
            }
        }
        return true;
    }

    /**
     * 生成xml文件
     */
    @SuppressWarnings("unchecked")
    protected static void createXmlUC(Table tblScript, String xmlFilePath) {
        Document document = null;
        Element rootElm = null;
        try {

            document = DocumentHelper.createDocument();
            // 设置处理指令
            document.addProcessingInstruction("xml-stylesheet",
                    "type='text/xsl' href='./UseCaseXSLT.xsl'");
            rootElm = document.addElement("SUITE");
            rootElm.addAttribute("verbose", "10");
            rootElm.addAttribute("name", "SIT Smart Automation Framework");
            rootElm = rootElm.addElement("TEST");
            String tempS = new File(xmlFilePath).getName();
            String ucName = tempS.substring(0, tempS.toLowerCase().indexOf(".xml"));
            rootElm.addAttribute("name", ucName);
            rootElm.addAttribute("loop", "1");
            rootElm = rootElm.addElement("STEPS");

            if (tblScript.getItemCount() > 1) {
                TableItem[] tiScript = tblScript.getItems();
                String strKey = "";
                Element stepElm = null;
                Element paraElm = null;
                int stepCnt = 0;
                for (TableItem ti : tiScript) {
                    // 按键的step类型为click
                    stepElm = rootElm.addElement("STEP");
                    stepElm.addAttribute("index", String.valueOf(++stepCnt));
                    stepElm.addAttribute("type", "click");
                    stepElm.addAttribute("name", ti.getText(Resources.SCRIPT_COL_CONTENT));
                    stepElm.addAttribute("target", "tv1");
                    strKey = ti.getText(Resources.SCRIPT_COL_KEY);
                    if (!strKey.contains("-")) {
                        paraElm = stepElm.addElement("keycode");
                    } else {
                        paraElm = stepElm.addElement("ircode");
                    }
                    paraElm.setText(strKey);
                    // interval等待
                    if (!"".equals(ti.getText(Resources.SCRIPT_COL_INTERVAL))) {
                        stepElm = rootElm.addElement("STEP");
                        stepElm.addAttribute("index", String.valueOf(++stepCnt));
                        stepElm.addAttribute("type", "sleep");
                        stepElm.addAttribute("name", "等待");
                        stepElm.addAttribute("target", "tv1");
                        paraElm = stepElm.addElement("value");
                        paraElm.setText(ti.getText(Resources.SCRIPT_COL_INTERVAL));
                    }
                    // 验证点
                    if (!"".equals(ti.getText(Resources.SCRIPT_COL_ASSERT))) {
                        Element elmVers = DocumentHelper.parseText(
                                ti.getText(Resources.SCRIPT_COL_ASSERT)).getRootElement();
                        List<Element> assElmList = elmVers.elements();
                        if (assElmList != null) {
                            for (Element elm : assElmList) {
                                // 截图需要添加step
                                if ("screenshot".equals(elm.getName())) {
                                    Element screenElm = rootElm.addElement("STEP");
                                    screenElm.addAttribute("index", String.valueOf(++stepCnt));
                                    screenElm.addAttribute("type", "screenshot");
                                    screenElm.addAttribute("name", "屏幕截图");
                                    screenElm.addAttribute("target", "tv1");
                                    paraElm = screenElm.addElement("name");
                                    paraElm.setText(elm.elementText("name"));
                                } else {
                                    if (elm.getName().endsWith("sqlite")) {
                                        Element sqliteElm = (Element) elm.clone();
                                        String isEqual = "";
                                        if (sqliteElm.attribute("isequal") != null) {
                                            isEqual = sqliteElm.attributeValue("isequal");
                                        } else if (sqliteElm.element("expectval").attribute(
                                                "isequal") != null) {
                                            isEqual = sqliteElm.element("expectval")
                                                    .attributeValue("isequal");
                                        } else {
                                            isEqual = "true";
                                        }
                                        if (sqliteElm.attribute("isequal") == null) {
                                            sqliteElm.addAttribute("isequal", isEqual);
                                        } else {
                                            sqliteElm.attribute("isequal").setValue(isEqual);
                                        }
                                        if (sqliteElm.element("expectval").attribute("isequal") != null) {
                                            sqliteElm.element("expectval").remove(
                                                    sqliteElm.element("expectval").attribute(
                                                            "isequal"));
                                        }
                                        stepElm.add(sqliteElm);
                                    } else {
                                        stepElm.add((Element) (elm.clone()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            utils.writeDocument(document, xmlFilePath);
            logger.debug("xml测试用例文件的保存位置:" + xmlFilePath);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 执行脚本--录制模式
     */
    protected static void execMScript(Table tblScript, Button chbExecLoop, Text txtLoop,
            Button rdoExecFixedInt, Text txtExecFixedInt, String deviceIp, int ucInterval) {
        try {
            int loopTimes = 1;
            if (chbExecLoop.getSelection()) {
                if ("".equals(txtLoop.getText())) {
                    loopTimes = 0;
                } else {
                    loopTimes = Integer.parseInt(txtLoop.getText());
                }
            }
            logger.debug("执行测试次数 loop=" + loopTimes);
            float fixedInt = 0;
            if (rdoExecFixedInt.getSelection()) {
                try {
                    fixedInt = Float.parseFloat(txtExecFixedInt.getText());
                    logger.debug("固定间隔时间 interval=" + fixedInt);
                } catch (Exception e) {
                    logger.error("固定间隔时间格式错误。" + e.getMessage(), e);
                    fixedInt = 0;
                }
            }

            ExecCondInfo execCondInfo = new ExecCondInfo();
//            execCondInfo.setWhaleSendIRCode(isWhaleSendIRCode);// Kenneth Whale
            execCondInfo.setLinuxTV(isLinuxTV);
            if (connIRComSuccss) {
                execCondInfo.setSpIR(spIR);
            } else {
                execCondInfo.setSpIR(null);
            }
            if (connDevComSuccss) {
                execCondInfo.setSpDev(spDev);
            } else {
                execCondInfo.setSpDev(null);
            }
            execCondInfo.setTblScript(tblScript);
            execCondInfo.setLoopTimes(loopTimes);
            execCondInfo.setFixInterval(fixedInt);
            execCondInfo.setLogFilePath(logFilePath);
            execCondInfo.setDeviceIp(deviceIp);
            execCondInfo.setRefreshRscInt(refreshRscInt);
            execCondInfo.setTestRstTimePath(testRstTimePath);
            execCondInfo.setMode(Resources.MODE_MANUAL);
            execCondInfo.setgAssertList(gAssertList);
            execCondInfo.setgAssertExistFlag(gAssertExistFlag);
            execCondInfo.setUcInterval(ucInterval);
            execMTh = new ExecMScriptTh(execCondInfo);
            execMTh.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            stopExec(Resources.MODE_MANUAL);
        }
    }

    /**
     * 执行脚本--随机模式
     */
    protected void execRScript(Table tblRScript, int encode, Button chbRExecLoop, Text txtRLoop,
            String deviceIp, int ucInterval) {
        try {
            int loopTimes = 1;
            if (chbRExecLoop.getSelection()) {
                if ("".equals(txtRLoop.getText())) {
                    loopTimes = 0;
                } else {
                    loopTimes = Integer.parseInt(txtRLoop.getText());
                }
            }
            logger.debug("执行测试次数 loop=" + loopTimes);

            ExecCondInfo execCondInfo = new ExecCondInfo();
//            execCondInfo.setWhaleSendIRCode(isWhaleSendIRCode);// Kenneth Whale
            execCondInfo.setLinuxTV(isLinuxTV);
            if (connIRComSuccss) {
                execCondInfo.setSpIR(spIR);
            } else {
                execCondInfo.setSpIR(null);
            }
            if (connDevComSuccss) {
                execCondInfo.setSpDev(spDev);
            } else {
                execCondInfo.setSpDev(null);
            }
            execCondInfo.setTblScript(tblRScript);
            execCondInfo.setEncode(encode);
            execCondInfo.setLoopTimes(loopTimes);
            execCondInfo.setDeviceIp(deviceIp);
            execCondInfo.setRefreshRscInt(refreshRscInt);
            execCondInfo.setLogFilePath(logFilePath);
            execCondInfo.setTestRstTimePath(testRstTimePath);
            execCondInfo.setMode(Resources.MODE_RANDOM);
            execCondInfo.setgAssertList(gAssertList);
            execCondInfo.setgAssertExistFlag(gAssertExistFlag);
            execCondInfo.setUcInterval(ucInterval);
            execCondInfo.setHtKeymap(htEncodeMap);
            execRTh = new ExecRScriptTh(execCondInfo, randCondInfo);
            execRTh.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            stopExec(Resources.MODE_RANDOM);
        }
    }

    /**
     * 执行脚本--读取执行模式
     */
    protected static void execEScript(Table tblScriptFiles, Table tblScript, Button chbExecLoop,
            Text txtLoop, String deviceIp, int ucInterval) {
        try {
            int loopTimes = 1;
            if (chbExecLoop.getSelection()) {
                if ("".equals(txtLoop.getText())) {
                    loopTimes = 0;
                } else {
                    loopTimes = Integer.parseInt(txtLoop.getText());
                }
            }
            logger.debug("执行测试次数 loop=" + loopTimes);
            ExecCondInfo execCondInfo = new ExecCondInfo();
            if (connIRComSuccss) {
                execCondInfo.setSpIR(spIR);
            } else {
                execCondInfo.setSpIR(null);
            }
            if (connDevComSuccss) {
                execCondInfo.setSpDev(spDev);
            } else {
                execCondInfo.setSpDev(null);
            }
            execCondInfo.setTblScriptFiles(tblScriptFiles);
            execCondInfo.setTblScript(tblScript);
            execCondInfo.setLoopTimes(loopTimes);
            execCondInfo.setLogFilePath(logFilePath);
            execCondInfo.setDeviceIp(deviceIp);
            execCondInfo.setRefreshRscInt(refreshRscInt);
            execCondInfo.setTestRstTimePath(testRstTimePath);
            execCondInfo.setMode(Resources.MODE_EXCUTE);
            execCondInfo.setgAssertList(gAssertList);
            execCondInfo.setgAssertExistFlag(gAssertExistFlag);
            execCondInfo.setUcInterval(ucInterval);
            execCondInfo.setScriptRootPath(scriptRootPath);
//            execCondInfo.setWhaleSendIRCode(isWhaleSendIRCode);// Kenneth
            execCondInfo.setLinuxTV(isLinuxTV);
            execETh = new ExecEScriptTh(execCondInfo);
            execETh.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            stopExec(Resources.MODE_EXCUTE);
        }
    }

    /**
     * 停止脚本运行
     */
    protected static void stopExec(int mode) {
        if (Resources.MODE_MANUAL == mode) {
            if (execMTh != null) {
                execMTh.stopRun();
                execMTh.interrupt();
            }
        } else if (Resources.MODE_RANDOM == mode) {
            if (execRTh != null) {
                execRTh.stopRun();
                execRTh.interrupt();
            }
        } else if (Resources.MODE_EXCUTE == mode) {
            if (execETh != null) {
                execETh.stopRun();
                execETh.interrupt();
            }
        }
    }

    /**
     * 工具断开连接
     */
    protected void disConnectCom(DSerialPort sp, Label lComStatus, Button btnConnCom,
            Button btnDisConnCom, boolean isIR) {
        if (sp == null) {
            return;
        }
        // 停止红外接收小板线程
        if (sp.getReceiveTh() != null) {
            sp.getReceiveTh().stopRun();
            sp.setReceiveTh(null);
        }
        if (isIR) {
            sp.stopIRReceive();
            sp.close();
            logger.debug("串口断开连接。");
            lComStatus.setText(Resources.TEXT_OFF_CONN);
            btnConnCom.setEnabled(true);
            btnDisConnCom.setEnabled(false);
            toolInfo.setComIRStatus(Resources.TEXT_OFF_CONN);
            toolInfo.setComIRConnEnabled(true);
            toolInfo.setComIRDisConnEnabled(false);
        } else {
            sp.close();
            logger.debug("串口断开连接。");
            lComStatus.setText(Resources.TEXT_OFF_CONN);
            btnConnCom.setEnabled(true);
            btnDisConnCom.setEnabled(false);
            toolInfo.setComDevStatus(Resources.TEXT_OFF_CONN);
            toolInfo.setComDevConnEnabled(true);
            toolInfo.setComDevDisConnEnabled(false);
        }
    }

    /**
     * 显示提示信息
     */
    protected static void showMsg(Shell shell, String msg, int level) {
        MessageBox msgBox = new MessageBox(shell, SWT.OK | level | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        msgBox.open();
    }

    /**
     * 显示提示信息
     */
    protected static boolean showSelMsg(Shell shell, String msg, int level) {
        MessageBox msgBox = new MessageBox(shell, SWT.OK | level | SWT.CANCEL | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        if (msgBox.open() == SWT.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取配置文件，设置行列标题的值
     * 
     * @param column
     * @param row
     */
    protected static void setColumnAndRow(String keytypeFilepath) {
        FileInputStream fin = null;
        BufferedReader br = null;
        String tmpString = "";
        try {
            fin = new FileInputStream(keytypeFilepath);
            br = new BufferedReader(new InputStreamReader(fin));
            while (!"#Column".equals(br.readLine())) {
            }
            tmpString = br.readLine();
            while (!"#Row".equals(tmpString)) {
                column.add(tmpString);
                tmpString = br.readLine();
            }
            tmpString = br.readLine();
            while (!"#End".equals(tmpString)) {
                row.add(tmpString);
                tmpString = br.readLine();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                br.close();
                fin.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
