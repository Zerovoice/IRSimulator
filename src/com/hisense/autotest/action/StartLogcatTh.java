package com.hisense.autotest.action;

import org.apache.log4j.Logger;

import com.hisense.autotest.automation.PgIRSimulator;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.Utils;

public class StartLogcatTh extends Thread {
    private static Logger logger = Logger.getLogger(StartLogcatTh.class);

	private DSerialPort serialPort;
	private volatile boolean stopRun = false;
	private boolean isLogcatPrinted = true;

	public StartLogcatTh(DSerialPort port) {
		serialPort = port;
		stopRun = false;
		isLogcatPrinted=!SmartAuto.isLinuxTV();
	}
	//增加是否输出logcat打印的参数
	public StartLogcatTh(DSerialPort port,boolean isLogcatPrinted) {
		serialPort = port;
		stopRun = false;
		this.isLogcatPrinted=isLogcatPrinted;
	}


	public void run() {
		Utils utils = new Utils();
		try {
			while (!stopRun) {
				//对于linux电视，直接在串口中输出日志，不需要logcat
				if(isLogcatPrinted){
					serialPort.write("logcat -v threadtime '*:V'");	
				}else{
//					logger.debug("linux电视，使用串口输出日志");
				}
				
				if (stopRun) {
					break;
				}
				utils.pauseWithE(PgIRSimulator.logcatInterval);
			}
		} catch (InterruptedException e) {
		} finally {
			if (serialPort != null) {
				serialPort.stopRead();
			}
		}
	}

	public void stopRun() {
		stopRun = true;
	}

}
