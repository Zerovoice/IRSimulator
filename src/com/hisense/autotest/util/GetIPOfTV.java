package com.hisense.autotest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.hisense.autotest.action.LogRecorderTh;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.util.Utils;

public class GetIPOfTV {
    private static Logger logger = Logger.getLogger(GetIPOfTV.class);
	private BufferedReader br = null;
	private LogRecorderTh rec = null;
	private Utils utils = new Utils();
	private boolean isWifiNetwork = false;

	public String get() {
		String s = "";
		File tmp = null;
		try {
			tmp = File.createTempFile("IPLOg", ".txt");
			logger.debug("临时文件tmp=" + tmp);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		pause(0.5f);

		// 发送ctrl+c，万一logcat在运行 需要关闭
		byte fb[] = { 0x03 };
		SmartAuto.getSpDev().write(fb);
		//发送ctrl+z， 如果存在logcat，则杀死
		SmartAuto.getSpDev().write(26);

		//使用线程记录串口日志
		rec = new LogRecorderTh(SmartAuto.getSpDev(), tmp.getAbsolutePath(),false);
		rec.setName("串口日志解析IP线程");
		rec.start();
		//查询IP，保存在串口日志
		SmartAuto.getSpDev().write("su");
		pause(0.5f);
		SmartAuto.getSpDev().write("netcfg");
		pause(0.5f);
		//解析IP
		s = fileParser(tmp);
	    //Start 解决串口readLine 无响应的问题
		SmartAuto.getSpDev().write("");
        // END
		rec.stopRun();
		rec.interrupt();
		return s;
	}

	private String fileParser(File tmp) {
		String strLine = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					tmp)));
			while ((strLine = br.readLine()) != null) {
				//一般返回结果为：有线：eth0     UP                             192.168.137.150/24  0x00001043 00:63:18:00:68:01
				if (strLine.contains("eth0")&&strLine.contains("UP")) {
					isWifiNetwork=false;
					try{
						int start = strLine.indexOf("UP");
						int end = strLine.indexOf("/24");
						strLine = strLine.substring(start + 2, end)
								.trim();	
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
					break;
				}
				//无线：wlan0    UP                              192.168.246.10/24  0x00001043 78:45:61:01:30:80
				if (strLine.contains("wlan0")&&strLine.contains("UP")) {
					isWifiNetwork=true;
					try{
						int start = strLine.indexOf("UP");
						int end = strLine.indexOf("/24");
						strLine = strLine.substring(start + 2, end)
								.trim();	
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				br = null;
			}
		}
		// 判断是否为IP
		// IP的正则表达式
		String IP_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
		Pattern p = Pattern.compile(IP_PATTERN);
		if (strLine==null||!p.matcher(strLine).matches()) {
			strLine = "";
		}
		return strLine;
	}

	public boolean isWifiNetwork() {
		return isWifiNetwork;
	}

	private void pause(float f) {
		try {
			utils.pauseWithE(f);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
