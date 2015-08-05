package com.hisense.autotest.action;

import com.hisense.autotest.serialport.DSerialPort;

public class LogRecorderTh extends Thread {

    private String deviceName;
	private DSerialPort serialPort = null;
	private String filePath;

	// public static final String PORT_NAME = "COM9";
	// public static final String OS = "window";//操作系统，可以是 window，linux
	private static StartLogcatTh logcatBySp;
    private static StartLogcatByAdbTh logcatByAdb;
	private boolean isLogcatPrinted = true;


	public LogRecorderTh(DSerialPort port, String file) {
		serialPort = port;
		filePath = file;
	}
	public LogRecorderTh(DSerialPort port, String file,boolean isLogcatPrinted) {
		serialPort = port;
		filePath = file;
		this.isLogcatPrinted=isLogcatPrinted;
	}

    public LogRecorderTh(String deviceName, String file) {
        this.deviceName = deviceName;
        filePath = file;
    }

	public void run() {

		// DSerialPort sp = new DSerialPort();
		// sp.listPort();
		// sp.selectPort(PORT_NAME);
		// sp.SetPortParameters(115200, 8, 1, 0);
		//
		// serialPort.write("su");
		// serialPort.write("input keyevent 22");
		// serialPort.write("input keyevent 21");
		//
		// serialPort.write("input keyevent 22");
		// serialPort.write("input keyevent 22");
        if (serialPort != null) {
            logcatBySp = new StartLogcatTh(serialPort,isLogcatPrinted);
            logcatBySp.setName("使用串口记录日志");
            logcatBySp.start();
            serialPort.startRead(filePath);
        } else if (deviceName != null && !"".equals(deviceName)) {
            logcatByAdb = new StartLogcatByAdbTh(deviceName, filePath);
            logcatByAdb.start();
        }

		// sp.close();

	}

	public void stopRun() {
        if (logcatBySp != null) {
            logcatBySp.stopRun();
            logcatBySp.interrupt();
        }
        if (serialPort != null) {
        	serialPort.write("\n");
            serialPort.stopRead();
        }
        if (logcatByAdb != null) {
            logcatByAdb.stopRun();
            logcatByAdb.interrupt();
        }
	}

}