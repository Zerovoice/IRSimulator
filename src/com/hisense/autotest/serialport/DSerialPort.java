package com.hisense.autotest.serialport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.Vector;
import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import org.apache.log4j.Logger;
import com.hisense.autotest.action.ExecReceiveTh;
import com.hisense.autotest.common.Resources;

/**
 * @项目名称 :illegalsms
 * @文件名称 :SerialPort.java
 * @所在包 :org.serial
 * @功能描述 : 串口类
 * @创建者 :集成显卡 1053214511@qq.com
 * @创建日期 :2012-9-13
 * @修改记录 :
 */
public class DSerialPort implements SerialPortEventListener {

    private static Logger logger = Logger.getLogger(DSerialPort.class);

    private int type;

    private String appName = "串口通讯测试[红外遥控器]";
    private int timeout = 2000;// open 端口时的等待时间
    // private int threadTime = 0;

    private static int oldSpCnt = 0;
    private static int currSpCnt = 0;
    private static CommDriver[] commdrivers = null;
    private CommPortIdentifier commPort;
    private SerialPort serialPort;
    // private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader brSerial;
    private boolean stopIRReceiver = true; // 空调接收小板是否停止录制

    private String logFile = "D:\\outlog.tmp";
    private BufferedWriter bwLogcat;

    private ArrayList<String[]> arrReceivedIRs = new ArrayList<String[]>();
    private InputStream inIRReceiver = null;
    private String intervalTime="";
    private ExecReceiveTh receiveTh = null;

    public DSerialPort(int type) {
        this.type = type;
        if (commdrivers == null) {
            oldSpCnt = 0;
            loadDriver();
        }
    }

    /**
     * @方法名称 :listPort
     * @功能描述 :列出所有可用的串口
     * @返回值类型 :void
     */
    @SuppressWarnings("rawtypes")
    public String[] listPort() {
        ArrayList<String> portList = new ArrayList<String>();
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        // System.out.println("now to list all Port of this PC：" + en);

        int spCnt = 0;
        while (en.hasMoreElements()) {
            cpid = (CommPortIdentifier) en.nextElement();
            // 过滤原来既有的端口
            if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL
                    && (++spCnt > oldSpCnt)) {
                System.out.println(cpid.getName() + ", "
                        + cpid.getCurrentOwner());
                portList.add(cpid.getName());
            }
        }
        currSpCnt = portList.size();
        String[] ports = new String[currSpCnt];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = portList.get(i);
        }
        return ports;
    }

    /**
     * @方法名称 :selectPort
     * @功能描述 :选择一个端口，比如：COM1
     * @返回值类型 :void
     * @param portName
     */
    @SuppressWarnings("rawtypes")
    public boolean selectPort(String portName) {

        this.commPort = null;
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        int spCnt = 0;
        while (en.hasMoreElements()) {
            cpid = (CommPortIdentifier) en.nextElement();
            if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL
                    && (++spCnt > oldSpCnt) && cpid.getName().equals(portName)) {
                this.commPort = cpid;
                break;
            }
        }
        return openPort();
    }

    /**
     * @方法名称 :openPort
     * @功能描述 :打开SerialPort
     * @返回值类型 :void
     */
    private boolean openPort() {
        if (commPort == null) {
            log(String.format("无法找到串口！"));
        } else {
            log("端口选择成功，当前端口：" + commPort.getName() + ",现在实例化 SerialPort:");

            try {
                serialPort = (SerialPort) commPort.open(appName, timeout);
                // serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
                // SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                // serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                log("实例 SerialPort 成功！");
                return true;
            } catch (PortInUseException e) {
                throw new RuntimeException(String.format("端口'%1$s'正在使用中！",
                        commPort.getName()));
            }
        }
        return false;
    }

    /**
     * @方法名称 :SetPortParameters
     * @功能描述 :更改SerialPort默认参数
     * @返回值类型 :void
     */
    public void SetPortParameters(int Burdrate, int databits, int stopbits,
            int parity) {
        if (commPort == null) {
            log(String.format("无法找到串口！"));
        } else {
            log("现在更改端口默认参数");

            try {
                serialPort.setSerialPortParams(Burdrate, databits, stopbits,
                        parity);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                log("端口参数更改成功");
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @方法名称 :checkPort
     * @功能描述 :检查端口是否正确连接
     * @返回值类型 :void
     */
    private void checkPort() {
        if (commPort == null)
            throw new RuntimeException("没有选择端口，请使用 "
                    + "selectPort(String portName) 方法选择端口");

        if (serialPort == null) {
            throw new RuntimeException("SerialPort 对象无效！");
        }
    }

    /**
     * @方法名称 :write
     * @功能描述 :向端口发送数据，请在调用此方法前 先选择端口，并确定SerialPort正常打开！
     * @返回值类型 :void
     * @param message
     */
    public void write(String message) {
        checkPort();

        try {
            outputStream = new BufferedOutputStream(
                    serialPort.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
        }

        try {
            outputStream.write(message.getBytes());
            outputStream.write("\n".getBytes());
            // log("信息发送成功！");
        } catch (IOException e) {
            throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }
    
    //增加整形参数，26 对应Ctrl + Z,可以用于终端串口中的logcat进程
    /**
     * @方法名称 :write
     * @功能描述 :向端口发送数据，请在调用此方法前 先选择端口，并确定SerialPort正常打开！
     * @返回值类型 :void
     * @param message
     */
    public void write(int message) {
        checkPort();

        try {
            outputStream = new BufferedOutputStream(
                    serialPort.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
        }
        try {
            outputStream.write(message);
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * @方法名称 :write
     * @功能描述 :向端口发送数据，请在调用此方法前 先选择端口，并确定SerialPort正常打开！
     * @返回值类型 :void
     * @param value
     */
    public void write(byte[] value) {
        checkPort();

        try {
            outputStream = new BufferedOutputStream(
                    serialPort.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
        }

        try {
            outputStream.write(value);
            log("信息发送成功！");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
        } finally {
            try {
//                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 修改串口参数（电视串口 波特率：115200）
     */
    public void setDevPortParameters() {
        checkPort();
        SetPortParameters(Resources.serialPortBaud, SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        // try {
        // serialPort.addEventListener(this);
        // } catch (TooManyListenersException e) {
        // throw new RuntimeException(e.getMessage());
        // }
    }

    /**
     * 修改串口参数（红外接收小板、红外发射小板 波特率：9600）
     */
    public void setIRReceiverPortParameters() {
        checkPort();
        SetPortParameters(Resources.serialReceiveBaud, SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);// 波特率可选值：4800/9600/19200/57600
    }

    /**
     * 添加对串口的监控（红外接收小板的端口），开始读取串口数据
     */
    public void startIRReceive() {
        try {
            // 判断是否录制过
            if (inIRReceiver == null) {
                inIRReceiver = serialPort.getInputStream();
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
            }
        } catch (TooManyListenersException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 停止读取串口数据
     */
    public void stopIRReceive() {
        try {
            if (inIRReceiver != null) {
                inIRReceiver.close();
            }
            if (serialPort != null) {
                serialPort.removeEventListener();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @方法名称 :startRead
     * @功能描述 :开始监听从端口中接收的数据
     * @返回值类型 :void
     * @param time
     *            监听程序的存活时间，单位为秒，0 则是一直监听
     */
    public void startRead(String fileName) {
        logFile = fileName;
        try {
//            InputStream inputStream = new BufferedInputStream(
//                    serialPort.getInputStream());
            brSerial = new BufferedReader(new InputStreamReader(new BufferedInputStream(
                    serialPort.getInputStream()),
                    "UTF-8"));
            if (!(new File(logFile).exists())) {
                new File(logFile).getParentFile().mkdirs();
                new File(logFile).createNewFile();
            }
            bwLogcat = new BufferedWriter(new FileWriter(logFile, true));

            while (bwLogcat != null) {
                String line = brSerial.readLine();
                // System.out.println("line=" + line);
                if ("".equals(line)) {
                    continue;
                }
                bwLogcat.write(line + "\n");
                bwLogcat.flush();
            }
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }

        // serialPort.notifyOnDataAvailable(true);

        // log(String.format("开始监听来自'%1$s'的数据--------------",
        // commPort.getName()));
        // if(time > 0){
        // this.threadTime = time*1000;
        // Thread t = new Thread(this);
        // t.start();
        // log(String.format("监听程序将在%1$d秒后关闭。。。。", threadTime));
        // }
    }

    /**
     * @方法名称 :stopRead
     * @功能描述 :停止监听从端口中接收的数据
     * @返回值类型 :void
     */
    public void stopRead() {
    	try{
    	    checkPort();
    	    write("");// 输入回车，保证串口有输出
    	}catch (Exception e) {
        }
        try {
            if (bwLogcat != null) {
                bwLogcat.close();
            }
            if (brSerial != null) {
                brSerial.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        brSerial = null;
        bwLogcat = null;
    }

    /**
     * @方法名称 :close
     * @功能描述 :关闭 SerialPort
     * @返回值类型 :void
     */
    public void close() {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
        stopRead();
        // commPort = null;
    }

    public void log(String msg) {
        System.out.println(appName + " --> " + msg);
    }

    /**
     * 数据接收的监听处理函数
     */
    @Override
    public void serialEvent(SerialPortEvent arg0) {
        switch (arg0.getEventType()) {
        case SerialPortEvent.BI:/* Break interrupt,通讯中断 */
            logger.error("SerialPortEvent.BI:Break interrupt,通讯中断");
            break;
        case SerialPortEvent.OE:/* Overrun error，溢位错误 */
            logger.error("SerialPortEvent.OE:Overrun error，溢位错误");
            break;
        case SerialPortEvent.FE:/* Framing error，传帧错误 */
            logger.error("SerialPortEvent.FE:Framing error，传帧错误");
            break;
        case SerialPortEvent.PE:/* Parity error，校验错误 */
            logger.error("SerialPortEvent.PE:Parity error，校验错误");
            break;
        case SerialPortEvent.CD:/* Carrier detect，载波检测 */
        case SerialPortEvent.CTS:/* Clear to send，清除发送 */
        case SerialPortEvent.DSR:/* Data set ready，数据设备就绪 */
        case SerialPortEvent.RI:/* Ring indicator，响铃指示 */
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*
                                                  * Output buffer is
                                                  * empty，输出缓冲区清空
                                                  */
            break;
        case SerialPortEvent.DATA_AVAILABLE:/*
                                             * Data available at the serial
                                             * port，端口有可用数据。读到缓冲数组，输出到终端
                                             */
            try {
                // TODO 设置间隔时间，当前时间设置一个全局变量
                // int tmpSize = arrReceivedIRs.size();
                // if(记录实时间隔时间 && tmpSize > 0){
                // arrReceivedIRs.set(tmpSize-1,new
                // String[]{arrReceivedIRs.get(tmpSize-1)[0],"真实时间间隔"});
                // }
                StringBuffer buf = new StringBuffer();// 缓存一条最新信息
                buf.append("A1-F1");
                int b;
                while (inIRReceiver.available() > 0) {
                    b=inIRReceiver.read();
                    if (stopIRReceiver) {
                        continue;
                    }
                        buf.append("-");
                        if(b>=0 && b<=15){
                            buf.append("0");
                        }
                        buf.append(Integer.toHexString(b));
                }
                if (!stopIRReceiver) {
                    // 判断当前按键是否是固定时间间隔
                    arrReceivedIRs.add(new String[] { buf.toString(),
                            intervalTime });// TODO
                }
            }catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean isStopIRReceiver() {
        return stopIRReceiver;
    }

    public void setStopIRReceiver(boolean stopIRReceiver) {
        this.stopIRReceiver = stopIRReceiver;
    }

    public int getType() {
        return type;
    }

    public ArrayList<String[]> getReceivedIRs() {
        return arrReceivedIRs;
    }
    
    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }
    

    public ExecReceiveTh getReceiveTh() {
        return receiveTh;
    }

    public void setReceiveTh(ExecReceiveTh receiveTh) {
        this.receiveTh = receiveTh;
    }

    //
    // @Override
    // public void run() {
    // try{
    // Thread.sleep(20000);
    // serialPort.close();
    // log(String.format("端口''监听关闭了！", commPort.getName()));
    // }catch(Exception e){
    // e.printStackTrace();
    // }
    // }

    /**
     * 初始化driver，把当前设备上的所有端口注册给CommPortIdentifier，
     * CommPortIdentifier会把每一次注册的端口都当作不同的端口分配CommPortIdentifier对象，
     * 但是它们的端口名和类型是相同的，所以，查找端口列表时需要过滤原来既有的端口。
     */
    public void initialDriver() {
        if (commdrivers == null) {
            return;
        }
        oldSpCnt += currSpCnt;
        for (CommDriver commdriver : commdrivers) {
            commdriver.initialize();
        }
    }

    /**
     * comm.jar中CommPortIdentifier.java部分源码 加载配置文件中配置的driver
     */
    private void loadDriver() {
        String s;
        if ((s = System.getProperty("javax.comm.properties")) != null)
            System.err.println("Comm Drivers: " + s);
        String s1 = System.getProperty("java.home") + File.separator + "lib"
                + File.separator + "javax.comm.properties";
        try {
            loadDriver(s1);
        } catch (IOException e) {
            String propfilename = findPropFile();
            try {
                if (propfilename != null) {
                    loadDriver(propfilename);
                }
            } catch (IOException ioexception) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * comm.jar中CommPortIdentifier.java部分源码. 加载配置文件中配置的driver
     */
    private void loadDriver(String s) throws IOException {
        File file = new File(s);
        BufferedInputStream bufferedinputstream = new BufferedInputStream(
                new FileInputStream(file));
        String as[] = parsePropsFile(bufferedinputstream);
        if (as != null) {
            commdrivers = new CommDriver[as.length];
            for (int i = 0; i < as.length; i++)
                if (as[i].regionMatches(true, 0, "driver=", 0, 7)) {
                    String s1 = as[i].substring(7);
                    s1.trim();
                    try {
                        // System.out.println(s1);
                        commdrivers[i] = (CommDriver) Class.forName(s1)
                                .newInstance();
                        // commdriver.initialize();
                    } catch (Throwable throwable) {
                        logger.error("Caught " + throwable
                                + " while loading driver " + s1);
                    }
                }

        }
    }

    /**
     * comm.jar中CommPortIdentifier.java部分源码. 查找配置文件 javax.comm.properties
     */
    private static String findPropFile() {
        String s = System.getProperty("java.class.path");
        StreamTokenizer streamtokenizer = new StreamTokenizer(new StringReader(
                s));
        streamtokenizer.whitespaceChars(File.pathSeparatorChar,
                File.pathSeparatorChar);
        streamtokenizer.wordChars(File.separatorChar, File.separatorChar);
        streamtokenizer.ordinaryChar(46);
        streamtokenizer.wordChars(46, 46);
        try {
            while (streamtokenizer.nextToken() != -1) {
                int i = -1;
                if (streamtokenizer.ttype == -3
                        && (i = streamtokenizer.sval.indexOf("comm.jar")) != -1) {
                    String s1 = new String(streamtokenizer.sval);
                    File file = new File(s1);
                    if (file.exists()) {
                        String s2 = s1.substring(0, i);
                        if (s2 != null)
                            s2 = s2 + "." + File.separator
                                    + "javax.comm.properties";
                        else
                            s2 = "." + File.separator + "javax.comm.properties";
                        File file1 = new File(s2);
                        if (file1.exists())
                            return new String(s2);
                        else
                            return null;
                    }
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    /*
     * comm.jar中CommPortIdentifier.java部分源码. 解析配置文件 javax.comm.properties
     */
    @SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
    private String[] parsePropsFile(InputStream inputstream) {
        Vector vector = new Vector();
        try {
            byte abyte0[] = new byte[4096];
            int i = 0;
            boolean flag = false;
            int k;
            while ((k = inputstream.read()) != -1)
                switch (k) {
                case 9: // '\t'
                case 32: // ' '
                    break;

                case 10: // '\n'
                case 13: // '\r'
                    if (i > 0) {
                        String s = new String(abyte0, 0, 0, i);
                        vector.addElement(s);
                    }
                    i = 0;
                    flag = false;
                    break;

                case 35: // '#'
                    flag = true;
                    if (i > 0) {
                        String s1 = new String(abyte0, 0, 0, i);
                        vector.addElement(s1);
                    }
                    i = 0;
                    break;

                default:
                    if (!flag && i < 4096)
                        abyte0[i++] = (byte) k;
                    break;
                }
        } catch (Throwable throwable) {
            logger.error("parse prop file error. " + throwable.getMessage(),
                    throwable);
        }
        if (vector.size() > 0) {
            String as[] = new String[vector.size()];
            for (int j = 0; j < vector.size(); j++)
                as[j] = (String) vector.elementAt(j);

            return as;
        } else {
            return null;
        }
    }

}