
package com.hisense.autotest.action;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.hisense.autotest.bean.ExecCondInfo;
import com.hisense.autotest.bean.TestResult;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.device.AdbOperation;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.Utils;

public class ExecScript extends Thread {

    private static Logger logger = Logger.getLogger(ExecScript.class);

    protected static AdbOperation adbOpr = new AdbOperation();
    protected static Utils utils = new Utils();

    protected static TestResult testRst = null;
    protected int currSelIndex = 0;
    protected static int currStepIndex = -1;

	protected DSerialPort spDev;
    protected String deviceIp;
    protected String testRootPath;
    protected String testRstTimePath;
    protected String testRstScreenFolder;
    protected static String runErrMsg = "";
    protected static String testRptPath = "";
    protected String summaryFile = "";
//    protected boolean isWhaleSendIRCode=false; //kenneth
    protected boolean isLinuxTV=false; //kenneth

    
    protected verifyLog verifylog;
    protected String logFilePath = "";

    public ExecScript(ExecCondInfo execCondInfo) {
        deviceIp = execCondInfo.getDeviceIp();
        spDev = execCondInfo.getSpDev();
        logFilePath = execCondInfo.getLogFilePath();
        verifylog = new verifyLog(logFilePath);
//        if (deviceIp != null && !"".equals(deviceIp)) {
//            adbOpr.connDev(deviceIp);
//        }
        testRstTimePath = execCondInfo.getTestRstTimePath();
        summaryFile = testRstTimePath + "index.xml";
        testRootPath = testRstTimePath.substring(0,
                testRstTimePath.indexOf(Resources.TEST_RST_PATH));
        testRstScreenFolder = testRstTimePath + "screenshot" + File.separator;
    }

    @SuppressWarnings("unchecked")
    protected boolean doStepAsserts(String assertPoints) {
        if (assertPoints == null || "".equals(assertPoints)) {
            return true;
        }
        	//------------添加串口录制LOG时的验证，改变验证方法。
        if ((deviceIp == null || "".equals(deviceIp))&& spDev == null) {
            return true;
        }
        boolean assRst = true;
        try {
            Element elmAssert = DocumentHelper.parseText(assertPoints).getRootElement();
            List<Element> assElms = elmAssert.elements("assertlog");
            if (assElms != null && assElms.size() > 0) {
                boolean logAssRst = true;
                for (Element assElm : assElms) {
                    logAssRst = verifylog.doStepVerify(assElm);
                    assRst = logAssRst ? assRst : false;
                }
            }
            assElms = elmAssert.elements("assertsqlite");
            if (assElms != null && assElms.size() > 0) {
                boolean sqliteAssRst = true;
                for (Element assElm : assElms) {
                    sqliteAssRst = doSqliteAssert(assElm);
                    assRst = sqliteAssRst ? assRst : false;
                }
            }
            assElms = elmAssert.elements("screenshot");
            if (assElms != null && assElms.size() > 0) {
                boolean screenAssRst = true;
                for (Element assElm : assElms) {
                    screenAssRst = doScreenshotAssert(assElm);
                    assRst = screenAssRst ? assRst : false;
                }
            }
        } catch (Exception e) {
            runErrMsg += e.getMessage();
            logger.error(e.getMessage(), e);
            assRst = false;
        }
        return assRst;
    }

    /**
     * log验证
     */
//    private boolean doLogAssert(Element elm) {
//        String expVal = elm.getText();
//        boolean isExist = true;
//        if ("false".equalsIgnoreCase(elm.attributeValue("isexist"))) {
//            isExist = false;
//        }
//        logger.debug("expectValue=" + expVal);
//        logger.debug("isExist=" + isExist);
//        String strLogcat = adbOpr.getLogcat(deviceIp);
//        adbOpr.clearLogcat(deviceIp);
//        boolean assRst = Pattern.compile("[\\s\\S]*" + expVal + "[\\s\\S]*").matcher(strLogcat)
//                .matches();
//        if (!isExist) {
//            assRst = !assRst;
//        }
//        if (!assRst) {
//            runErrMsg += "log验证失败，" + expVal + ".";
//        }
//        logger.debug("log验证结果：" + assRst);
//        return assRst;
//    }

    /**
     * sqlite验证
     */
    private boolean doSqliteAssert(Element elm) {
        boolean assRst = false;
        try {
            String dbPath = elm.elementText("dbpath");
            String selectSql = elm.elementText("selectsql");
            String expectVal = elm.elementText("expectval");
            boolean isEqual = true;
            String equalVal = "";
            if (elm.attribute("isequal") != null) {
                equalVal = elm.attributeValue("isequal");
            } else if (elm.element("expectval").attribute("isequal") != null) {
                equalVal = elm.element("expectval").attributeValue("isequal");
            } else {
                equalVal = "true";
            }
            if ("false".equalsIgnoreCase(equalVal)) {
                isEqual = false;
            }
            logger.debug("dbPath=" + dbPath);
            logger.debug("selectSql=" + selectSql);
            logger.debug("expectVal=" + expectVal);
            logger.debug("isEqual=" + isEqual);
            String locDBPath = testRstTimePath + "temp" + File.separator
                    + dbPath.substring(dbPath.lastIndexOf("/") + 1, dbPath.lastIndexOf(".db"))
                    + "_" + utils.getCurrTime(Resources.FORMAT_TIME_SUFFIX) + ".db";
            if (!new File(locDBPath).getParentFile().exists()) {
                new File(locDBPath).getParentFile().mkdirs();
            }
            // 数据库拷贝到本地
            adbOpr.copyFile2Local(deviceIp, dbPath, locDBPath);
            // 数据库连接
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + "./"
                    + locDBPath.substring(testRootPath.length()));
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(selectSql);// 查询数据
            if (rs != null && rs.next() && expectVal.equals(rs.getString(1))) {
                assRst = true;
            }
            if (!isEqual) {
                assRst = !assRst;
            }
            if (!assRst) {
                runErrMsg += "sqlite验证失败，" + expectVal + ".";
            }
        } catch (Exception e) {
            runErrMsg += "sqlite验证失败，" + e.getMessage();
            logger.error(e.getMessage(), e);
            assRst = false;
        }
        logger.debug("sqlite验证结果：" + assRst);
        return assRst;
    }

    /**
     * screenshot验证
     */
    private boolean doScreenshotAssert(Element elm) {
        String screenName = elm.elementText("name") + utils.getCurrTime("yyyyMMddHHmmss") + ".png";
        String screenPath = testRstScreenFolder + screenName;
        logger.debug("截图：" + screenPath);
        adbOpr.takeScreenshot(deviceIp, screenPath);
        testRst.addPic(currSelIndex, "./" + screenPath.substring(testRstTimePath.length()));
        return true;
    }

    /**
     * 初始化测试报告
     */
    protected void initTestRpt(final Table tblScript) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                int stepCnt = tblScript.getItemCount();
                if (stepCnt > 0) {
                    testRst = new TestResult(stepCnt);
                    TableItem tblItem;
                    for (int i = 0; i < stepCnt; i++) {
                        tblItem = tblScript.getItem(i);
                        testRst.setRstStepNodes(i,
                                new String[] { tblItem.getText(Resources.SCRIPT_COL_KEY),
                                        tblItem.getText(Resources.SCRIPT_COL_CONTENT),
                                        tblItem.getText(Resources.SCRIPT_COL_INTERVAL),
                                        tblItem.getText(Resources.SCRIPT_COL_ASSERT) });
                    }
                }
            }
        });
    }

    /**
     * 初始化测试报告，重复执行
     */
    protected void initTestRptDup(final Table tblScript) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                int stepCnt = tblScript.getItemCount();
                if (stepCnt > 0) {
                    int testRstStepCnt = testRst.getTestRstStepCnt();
                    testRst.initDupTestRst(stepCnt);
                    TableItem tblItem;
                    int index = 0;
                    for (int i = 0; i < stepCnt; i++) {
                        tblItem = tblScript.getItem(i);
                        index = testRstStepCnt + i;
                        testRst.setRstStepNodes(
                                index,
                                new String[] { tblItem.getText(Resources.SCRIPT_COL_KEY),
                                        tblItem.getText(Resources.SCRIPT_COL_CONTENT),
                                        tblItem.getText(Resources.SCRIPT_COL_INTERVAL),
                                        tblItem.getText(Resources.SCRIPT_COL_ASSERT) });
                    }
                }
            }
        });
    }

    /**
     * 写测试报告
     */
    protected void writeTestRpt(String scriptSubPath, TestResult testRst) {
        if (testRst == null) {
            return;
        }
        try {

            testRptPath = testRstTimePath + scriptSubPath;
            testRptPath = testRptPath.substring(0, testRptPath.length() - 4)
                    + utils.getCurrTime(Resources.FORMAT_TIME_SUFFIX) + ".xml";

            File rptFile = new File(testRptPath);
            if (!rptFile.getParentFile().exists()) {
                rptFile.getParentFile().mkdirs();
            }
            String rstXslFile = testRstTimePath + "utils/detail.xsl";
            if (!new File(rstXslFile).exists()) {
                String xslFile = Resources.resourcesPath +File.separator + "detail.xsl";
                FileUtils.copyFile(new File(xslFile), new File(rstXslFile));
            }
            SAXReader reader = new SAXReader();
            Document document = null;
            Element testCaseRoot = null;
            if (new File(testRptPath).exists()) {
                document = reader.read(new File(testRptPath));
                testCaseRoot = (Element) (document.selectSingleNode("/TEST/STEPS"));
            } else {
                if (!new File(testRptPath).getParentFile().exists()) {
                    new File(testRptPath).getParentFile().mkdirs();
                    logger.debug("已创建测试报告文件的目录:" + testRptPath);
                }
                document = DocumentHelper.createDocument();

                String dep = "./";
                while (scriptSubPath.contains(File.separator)) {
                    dep += "../";
                    scriptSubPath = scriptSubPath
                            .substring(scriptSubPath.indexOf(File.separator) + 1);
                }

                document.addProcessingInstruction("xml-stylesheet", "type='text/xsl' href='" + dep
                        + "utils/detail.xsl'");
                Element indexRoot = document.addElement("TEST");
                String testName = new File(testRptPath).getName();
                indexRoot.addAttribute("name",
                        testName.substring(0, testName.length() - ".xml".length()));
                testCaseRoot = indexRoot.addElement("STEPS");
            }
            testCaseRoot.addAttribute("erro", testRst.getErrorMsg());
            if (testRst.getGlobalFlag()) {
                testCaseRoot.addAttribute("runflag", "Pass");
            } else {
                testCaseRoot.addAttribute("runflag", "Fail");
            }
            int stepCnt = testRst.getTestRstStepCnt();
            Element testStepElm;
            String[] step;
            List<String> screenList;
            Element screenElm;
            for (int i = 0; i < stepCnt; i++) {
                step = testRst.getRstStepNodes(i);
                if (step == null) {
                    break;
                }
                testStepElm = testCaseRoot.addElement("STEP");
                testStepElm.addAttribute("index", String.valueOf(i + 1));
                testStepElm.addAttribute("key", step[0]);
                testStepElm.addAttribute("name", step[1]);
                testStepElm.addAttribute("interval", step[2]);
                if (testRst.getStepFlag(i) == null) {
                    testStepElm.addAttribute("runflag", "Not Run");
                } else if (testRst.getStepFlag(i)) {
                    testStepElm.addAttribute("runflag", "Pass");
                } else {
                    testStepElm.addAttribute("runflag", "Fail");
                }
                screenList = testRst.getPicList(i);
                for (String screenPath : screenList) {
                    screenElm = testStepElm.addElement("imgsrc");
                    screenElm.setText(screenPath);
                }
            }
            utils.writeDocument(document, testRptPath);
            String html = testRptPath.replace("xml", "html");
            File xslFile = new File(Resources.resourcesPath +File.separator+"detail.xsl");
            File xmlFile =new File(testRptPath);
            xmlToHtml(xmlFile,xslFile , html, true);
            logger.debug("测试结果文件的位置:" + testRptPath);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 写测试报告汇总文件
     */
    protected void writeSummary(int testRst, String rstMsg, String screenPath) {
        if (summaryFile == null || "".equals(summaryFile)) {
            logger.debug("汇总文件路径无效。summaryPath=" + summaryFile);
            return;
        }
        try {
            String rstXslFile = testRstTimePath + "utils/index.xsl";
            if (!new File(rstXslFile).exists()) {
                String xslFile = Resources.resourcesPath +File.separator+ "index.xsl";
                FileUtils.copyFile(new File(xslFile), new File(rstXslFile));
            }
            SAXReader reader = new SAXReader();
            Document document = null;
            Element testCaseRoot = null;
            if (new File(summaryFile).exists()) {
                document = reader.read(new File(summaryFile));
                testCaseRoot = (Element) (document.selectSingleNode("/TestResultSummary/TestCases"));
            } else {
                if (!new File(summaryFile).getParentFile().exists()) {
                    new File(summaryFile).getParentFile().mkdirs();
                    logger.debug("已创建测试结果汇总文件的目录:" + summaryFile);
                }
                document = DocumentHelper.createDocument();
                document.addProcessingInstruction("xml-stylesheet",
                        "type='text/xsl' href='./utils/index.xsl'");
                Element indexRoot = document.addElement("TestResultSummary");
                testCaseRoot = indexRoot.addElement("TestCases");
            }
            Element testRstsElm = testCaseRoot.addElement("TEST");
            String fileName = new File(testRptPath).getName();
            testRstsElm.addAttribute("name", fileName.substring(0, fileName.lastIndexOf(".")));
            if (testRst == Resources.TEST_RST_PASS) {
                testRstsElm.addAttribute("runflag", "Pass");
            } else if (testRst == Resources.TEST_RST_FAIL) {
                testRstsElm.addAttribute("runflag", "Fail");
            } else {
                testRstsElm.addAttribute("runflag", "Not Run");
            }
            String resultPath = testRptPath.substring(testRstTimePath.length());
            testRstsElm.addAttribute("detailfile",
                    "./" + resultPath.replace("xml", "html"));
            testRstsElm.addAttribute("testrstmsg", rstMsg);
            if (screenPath != null && !"".equals(screenPath)) {
                testRstsElm.addElement("imgsrc");
                testRstsElm.element("imgsrc").setText(screenPath);
            }
            utils.writeDocument(document, summaryFile);
            String html = testRstTimePath+File.separator + "index.html";
            File xslFile = new File(Resources.resourcesPath +File.separator+"index.xsl");
            File xmlFile =new File(summaryFile);
            xmlToHtml(xmlFile,xslFile , html, false);
            logger.debug("测试结果汇总文件的位置:" + html);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 将XML文件通过样式表转换HTML文件
     */
    public static void xmlToHtml(File xmlFile , File xslFile, String html, boolean isDelXMLFile){
        try {
            if(xslFile.exists()){
            File htmlFile = new File(html);
            htmlFile.createNewFile();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(
                    xslFile));
            
            //将xml文件转换成html文件
            transformer.transform(new StreamSource(
                    xmlFile), new StreamResult(
                    new FileOutputStream(htmlFile)));
            if(isDelXMLFile && xmlFile!=null){
                xmlFile.delete();  //删除XML文件
            }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

}
