
package com.hisense.autotest.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Pattern;

import com.hisense.autotest.common.Resources;

public class Utils {

    private static Logger logger = Logger.getLogger(Utils.class);
    private static boolean isButSelected = false ;

    /**
     * 读取配置文件properties
     */
    public Properties getProperties(String propFile) {
        String prop = propFile;
        Properties properties = new Properties();
        try {
            if (prop != null && new File(prop).exists()) {
                prop = new File(prop).getCanonicalPath();
            }

            InputStreamReader is = new InputStreamReader(new FileInputStream(prop), "UTF-8");
            properties.load(is);
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            // logger.warn(e.getMessage(), e);
        }
        return properties;
    }

    /**
     * 读取配置文件
     */
    public Element readXMLConfigs(String confFile) {

        if (!new File(confFile).exists()) {
            logger.debug("没有配置文件。" + confFile);
            return null;
        }
        try {
            Document confDoc = new SAXReader().read(new File(confFile));
            Element suiteElm = confDoc.getRootElement();
            // 读取SETTINGS信息
            return suiteElm.element("SETTINGS");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 添加一个序列到配置文件sequence.xml
     */
    public boolean addSequenceXML(Table tbl_edit,String name) {
        File xmlFile = new File(Resources.sequencePath);
        if (!xmlFile.exists()) {
            logger.debug("sequence.xml文件不存在！");
            return false;
        }
        try {
            Element newStep = null ;
            Element singleStep = null ;
            Document Doc = new SAXReader().read(xmlFile);
            Element suiteElm = Doc.getRootElement();
            newStep = suiteElm.addElement("STEPS");
            newStep.addAttribute("name", name);
            TableItem[] items = tbl_edit.getItems();
            if(items.length==0){
                logger.debug("表中没有序列");
                return false;
            }
            for (int i = 0; i < items.length; i++) {
                TableItem item = items[i];
                singleStep = newStep.addElement("STEP");
                singleStep.addAttribute("index",item.getText(Resources.SCRIPT_COL_INDEX));
                singleStep.addAttribute("note", item.getText(Resources.SCRIPT_COL_CONTENT));
                singleStep.addAttribute("value", item.getText(Resources.SCRIPT_COL_NAME));
                singleStep.addAttribute("interval", item.getText(Resources.SCRIPT_COL_INTERVAL));
            }
            // 生成文件
            writeDocument(Doc, xmlFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        logger.debug("序列保存成功");
        return true ;
    }

    /**
     * 等待
     */
    public synchronized void pause(float seconds) throws InterruptedException {
//        try {
        Thread.sleep((int) (seconds * 1000));
//        } catch (InterruptedException e) {
//            logger.error(e.getMessage(), e);
//        }
    }

    /**
     * 等待
     */
    public synchronized void pauseWithE(float seconds) throws InterruptedException {
        Thread.sleep((int) (seconds * 1000));
    }

    /**
     * this function execute a command
     * 
     * @param command
     *            : command what to execute
     */
    public String excuteCmd(String command) {
        StringBuffer log = new StringBuffer();
        log.append("");
        Process process = null;
        try {
//			process = Runtime.getRuntime().exec(command);
            process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", command });
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = "";
            try {
                do {
                    line = bufferedReader.readLine();
                    logger.debug("执行cmd: "+command+" 的输出："+line);
                    log.append(line);
                    log.append("\n");
                } while (line != null);
            } finally {
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return log.toString();
    }

    /**
     * this function execute a command
     * 
     * @param command
     *            : command what to execute
     */
    public int excuteCmdWait(String command) {
        int status = -1;
        Process process = null;
        try {
//          process = Runtime.getRuntime().exec(command);
            process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", command });
            // 启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            // 获取进程的标准输入流
            readProcStream(process.getInputStream());
            // 获取进城的错误流
            readProcStream(process.getErrorStream());
            status = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return status;
    }

    private void readProcStream(final InputStream is) {
        new Thread() {

            public void run() {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
                try {
                    while ((br1.readLine()) != null) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * this function execute a command
     * 
     * @param command
     *            : command what to execute
     */
    public Process getExecCmdProc(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
//			process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return process;
    }

    // 获取当前时间
    public String getCurrTime(String strFormat) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        return sdf.format(now);
    }

    // 保留小数
    public String retainDecimal(String val, int digit) {
        return String.format("%." + digit + "f", val);
    }

    /**
     * 读取脚本文件列表
     */
    public ArrayList<String> getScriptFiles(String fileDir) {
        ArrayList<String> filelist = new ArrayList<String>();
        try {
            File dir = new File(fileDir);
            File[] files = dir.listFiles();

            if (files == null) {
                return filelist;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    filelist.addAll(getScriptFiles(files[i].getAbsolutePath()));
                } else {
                    String strFileName = files[i].getAbsolutePath();
                    if (strFileName.toLowerCase().endsWith(".csv")) {
                        logger.debug(strFileName);
                        filelist.add(strFileName);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return filelist;
    }

    /**
     * 写文件
     */
    public void writeDocument(Document document, String savePath) {
        XMLWriter writer = null;
        OutputFormat format = OutputFormat.createPrettyPrint();
        try {
            format.setEncoding("UTF-8");
            if (savePath != null && !savePath.equalsIgnoreCase("")) {
                // lets write to a file
                writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(savePath),
                        "UTF-8"), format);
            }
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取键值
     */
    public String getKeyEncode(Properties properties, int encode, String key, boolean isFac,Hashtable<String, String> htSyscodeMap) {
        String propKey = "";
        if (encode == Resources.ENCODE_RC5) {
            propKey = Resources.ENCODE_PREFIX[Resources.ENCODE_RC5] + key;
        } else if (encode == Resources.ENCODE_NEC) {
            propKey = Resources.ENCODE_PREFIX[Resources.ENCODE_NEC] + key;
        } else if (encode == Resources.ENCODE_KEYCODE) {
            propKey = Resources.ENCODE_PREFIX[Resources.ENCODE_KEYCODE] + key;
        } else {
            return "";
        }
        String strKey3 = properties.getProperty(propKey);// 按键码
        if (strKey3 == null || "".equals(strKey3)) {
            logger.debug("配置文件中没有设定相应的key值。" + propKey);
            return "";
        }
//        logger.debug("配置文件设置信息 " + propKey + " = " + strKey3);
        if (encode == Resources.ENCODE_KEYCODE) {
            // 编码选择keycode
            return strKey3;
        }

        String strKey0 = "AA";// 引导码
        String strKey1 = "";// 用户码
        String strKey2 = "";// 系统码
        if (encode == Resources.ENCODE_RC5) {
            strKey1 = "A8";// RC5 encode
        } else if (encode == Resources.ENCODE_NEC) {
            strKey1 = "A7";// NEC encode
        }
        if (isFac) {
            strKey2 = htSyscodeMap.get(Resources.SYSCODE_FAC);
        } else if (encode == Resources.ENCODE_RC5) {
            strKey2 = htSyscodeMap.get(Resources.SYSCODE_RC5);
        } else if (encode == Resources.ENCODE_NEC) {
            strKey2 = htSyscodeMap.get(Resources.SYSCODE_NEC);
        }
        //SX6 UHD用户码 SX6电视遥控器系统编码比较特殊，临时处理上，先硬编码FB
        if(propKey.contains("_SX6_")){
        	strKey2 = "FB";
        }
        if (strKey2 == null || "".equals(strKey2)) {
            logger.debug("配置文件中没有设定红外遥控的系统码，请确认。");
            return "";
        }
        // 校验码
        int iKey4 = Integer.parseInt(strKey0, 16) + Integer.parseInt(strKey1, 16)
                + Integer.parseInt(strKey2, 16) + Integer.parseInt(strKey3, 16);
        while (iKey4 > 256) {
            iKey4 -= 256;
        }
        String strKey4 = Integer.toHexString(256 - iKey4).toUpperCase();
        StringBuffer sb = new StringBuffer();
        sb.append(strKey0);
        sb.append("-");
        sb.append(strKey1);
        sb.append("-");
        sb.append(strKey2);
        sb.append("-");
        sb.append(strKey3);
        sb.append("-");
        sb.append(strKey4);
        return sb.toString();
    }
    
    /**
     * 判断某一个radio Button 是否被选中
     */
    public static boolean isSelected(final Button but){
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
               if(but.getSelection()){
                   isButSelected=true ;
               }
            }
        });
        return isButSelected ;
    }
    /**
     * 验证字符串是否为数字
     * 
     */
    public boolean isNumericAndLetter(String str) {
        Pattern pattern = Pattern.compile("[0-9a-fA-F]*");
        return pattern.matcher(str).matches();
    }
}
