
package com.hisense.autotest.action;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.widgets.Display;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import com.hisense.autotest.common.Resources;
import com.hisense.autotest.util.Utils;

public class ExecTScriptTh extends Thread {

    private static Logger logger = Logger.getLogger(ExecTScriptTh.class);
    private static Utils utils = new Utils();

    private org.eclipse.swt.widgets.List listTTransList = null;
    private ArrayList<String> scriptList;
    private String csvRootPath;
    private String xmlRootPath;
    private String srcEncodePrefix = "";
    private String desEncodePrefix = "";
    private Hashtable<String, String> htEncode;

    private String desCsvFilePath = "";
    private String desXmlFilePath = "";
    private int xmlStepCnt = 0;

    public ExecTScriptTh(String transType, org.eclipse.swt.widgets.List listTTransList,
            ArrayList<String> scriptList, String saveRootPath, int srcEncode, int desEncode,
            Hashtable<String, String> htEncode) {
        this.listTTransList = listTTransList;
        this.scriptList = scriptList;
        srcEncodePrefix = Resources.ENCODE_PREFIX[srcEncode];
        desEncodePrefix = Resources.ENCODE_PREFIX[desEncode];
        this.htEncode = htEncode;
        String now = utils.getCurrTime(Resources.FORMAT_TIME_PATH);
        csvRootPath = new File(saveRootPath + File.separator + now + "_" + transType
                + File.separator + "csv").getAbsolutePath();
        xmlRootPath = new File(saveRootPath + File.separator + now + "_" + transType
                + File.separator + "xml").getAbsolutePath();
    }

    public void run() {
        logger.debug("脚本中遥控器按键编码转换开始。");
        for (String path : scriptList) {
            if (new File(path).isDirectory()) {
                transScriptEncode(path, new File(path).getParent());
            } else {
                transScriptEncode(path, null);
            }
        }
        logger.debug("脚本中遥控器按键编码转换结束。");
    }

    private void transScriptEncode(String scriptPath, String rootPath) {
        if (scriptPath.toLowerCase().endsWith(".csv") && new File(scriptPath).exists()) {
            transKeyEncode(scriptPath, rootPath);
        } else if (new File(scriptPath).isDirectory()) {
            ArrayList<String> scriptList = utils.getScriptFiles(scriptPath);
            if (scriptList == null || scriptList.size() == 0) {
                return;
            }
            for (String path : scriptList) {
                transScriptEncode(path, rootPath);
            }
        }
    }

    /*
     * 读取csv文件，转换遥控器按键编码
     */
    private void transKeyEncode(String filePath, String rootPath) {
        if (!new File(filePath).exists()) {
            logger.error("文件不存在。" + filePath);
            return;
        }
        logger.debug("开始转换：" + filePath);
        BufferedReader bf = null;
        BufferedWriter bwScript = null;
        Document document = null;
        Element rootElm = null;
        try {
            String fileName = new File(filePath).getName();
            String fileSubPath = "";
            if (rootPath != null) {
                fileSubPath = filePath.substring(rootPath.length() + 1);
            } else {
                fileSubPath = fileName;
            }
            fileName = fileName.substring(0, fileName.length() - ".csv".length());
            desCsvFilePath = csvRootPath + File.separator + fileSubPath + ".csv";
            desXmlFilePath = xmlRootPath + File.separator + fileSubPath + ".xml";
            if (!new File(desCsvFilePath).getParentFile().exists()) {
                new File(desCsvFilePath).getParentFile().mkdirs();
            }
            if (!new File(desXmlFilePath).getParentFile().exists()) {
                new File(desXmlFilePath).getParentFile().mkdirs();
            }

            bf = new BufferedReader(new FileReader(filePath));
            bwScript = new BufferedWriter(new FileWriter(desCsvFilePath));
            document = DocumentHelper.createDocument();
            // 设置处理指令
            document.addProcessingInstruction("xml-stylesheet",
                    "type='text/xsl' href='./UseCaseXSLT.xsl'");
            rootElm = document.addElement("SUITE");
            rootElm.addAttribute("verbose", "10");
            rootElm.addAttribute("name", "SIT Smart Automation Framework");
            rootElm = rootElm.addElement("TEST");
            rootElm.addAttribute("name", fileName);
            rootElm.addAttribute("loop", "1");
            rootElm = rootElm.addElement("STEPS");
            xmlStepCnt = 0;

            String strLine;
            StringTokenizer st;
            String[] scriptVals = new String[] { "", "", "", "" };
            int index = 0;
            String colVal = "";
            while ((strLine = bf.readLine()) != null) {
                if ("".equals(strLine)) {
                    continue;
                }
                index = 0;
                st = new StringTokenizer(strLine, ",");
                scriptVals = new String[] { "", "", "", "" };
                while (st.hasMoreTokens()) {
                    colVal = st.nextToken();
                    if (index == 0) {
                        colVal = getDesEncode(colVal);
                    }
                    scriptVals[index] = colVal;
                    index++;
                }
                for (int i = 0; i < scriptVals.length; i++) {
                    bwScript.write(scriptVals[i]);
                    if (i < scriptVals.length - 1) {
                        bwScript.write(",");
                    } else {
                        bwScript.write("\n");
                    }
                }
                addSteps(rootElm, scriptVals[0], scriptVals[1], scriptVals[2], scriptVals[3]);
            }
            utils.writeDocument(document, desXmlFilePath);
            logger.debug("csv文件转换成功。" + desCsvFilePath);
            logger.debug("xml文件转换成功。" + desXmlFilePath);
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    listTTransList.add("转换成功：" + desCsvFilePath);
                    listTTransList.add("转换成功：" + desXmlFilePath);
                }
            });
        } catch (Exception e) {
            logger.error("转换失败" + e.getMessage(), e);
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    listTTransList.add("转换失败：" + desCsvFilePath);
                    listTTransList.add("转换失败：" + desXmlFilePath);
                }
            });
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception e1) {
            }
            try {
                if (bwScript != null) {
                    bwScript.close();
                }
            } catch (Exception e1) {
            }
            bf = null;
            bwScript = null;
        }
        return;
    }

    @SuppressWarnings("unchecked")
    private void addSteps(Element rootElm, String key, String name, String interval, String vers)
            throws DocumentException {

        Element stepElm = null;
        Element paraElm = null;
        // 按键的step类型为click
        stepElm = rootElm.addElement("STEP");
        stepElm.addAttribute("index", String.valueOf(++xmlStepCnt));
        stepElm.addAttribute("type", "click");
        stepElm.addAttribute("name", name);
        stepElm.addAttribute("target", "tv1");
        if (!key.contains("-")) {
            paraElm = stepElm.addElement("keycode");
        } else {
            paraElm = stepElm.addElement("ircode");
        }
        paraElm.setText(key);
        // interval等待
        if (!"".equals(interval)) {
            stepElm = rootElm.addElement("STEP");
            stepElm.addAttribute("index", String.valueOf(++xmlStepCnt));
            stepElm.addAttribute("type", "sleep");
            stepElm.addAttribute("name", "等待");
            stepElm.addAttribute("target", "tv1");
            paraElm = stepElm.addElement("value");
            paraElm.setText(interval);
        }
        // 验证点
        if (!"".equals(vers)) {
            Element elmVers = DocumentHelper.parseText(vers).getRootElement();
            List<Element> assElmList = elmVers.elements();
            if (assElmList != null) {
                for (Element elm : assElmList) {
                    // 截图需要添加step
                    if ("screenshot".equals(elm.getName())) {
                        Element screenElm = rootElm.addElement("STEP");
                        screenElm.addAttribute("index", String.valueOf(++xmlStepCnt));
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
                            } else if (sqliteElm.element("expectval").attribute("isequal") != null) {
                                isEqual = sqliteElm.element("expectval").attributeValue("isequal");
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
                                        sqliteElm.element("expectval").attribute("isequal"));
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

    private String getDesEncode(String srcEncode) {
        String desEncode = null;
        Enumeration<String> keys = htEncode.keys();
        String key = "";
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            if (key.startsWith(srcEncodePrefix) && srcEncode.equals(htEncode.get(key))) {
                desEncode = htEncode.get(key.replace(srcEncodePrefix, desEncodePrefix));
                break;
            }
        }
        if (desEncode == null) {
            desEncode = srcEncode;
        }
        return desEncode;
    }

}
