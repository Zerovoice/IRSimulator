package com.hisense.autotest.util;

import java.util.HashMap;
import org.apache.log4j.Logger;

//通过注册表查询串口
public class SPortRegQuery {
	private static Logger logger = Logger.getLogger(SPortRegQuery.class);
	public static final String COMType_TV = "VCP";// 电视串口
	public static final String COMType_IR = "ProlificSerial";// 遥控器、继电器串口
	// 从注册表获取串口号及其类型
	public static HashMap<String, String> get() {
		HashMap<String, String> map = new HashMap<String, String>();
		String regQueryStr = "";
		String[] regArray = {};
		String key = "";
		String value = "";
		// 执行串口查询
		regQueryStr = new Utils()
				.excuteCmd("REG QUERY HKLM\\HARDWARE\\DEVICEMAP\\SERIALCOMM");
		// 把查询结果保存到map对象
		if (regQueryStr != null) {
			regArray = regQueryStr.split("\n");
			for (String line : regArray) {
				if (line.contains("\\Device\\")) {
					key = line.substring(line.indexOf("REG_SZ") + 6).trim();
					value = line.substring(line.indexOf("\\Device\\") + 8,
							line.indexOf("REG_SZ")).trim();
					logger.debug("串口号key=" + key + "  串口类型value=" + value);
					map.put(key, value);
				}

			}
		}
		return map;
	}
	public static void main(String[] s) {
		SPortRegQuery.get();
	}
}
