package com.hisense.autotest.action;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogcatParserTh extends Thread {
	private static Logger logger = Logger.getLogger(LogcatParserTh.class);

	private BufferedReader br = null;
	private ArrayList<String> assertList = new ArrayList<String>();
	private ArrayList<String> assertExistFlag = new ArrayList<String>();
	private String filePath;
	private static boolean assertRst = true;
	private static String rstErrMsg = "";

	private static boolean execStatus = false;
	private static boolean startRun = false;
	private volatile boolean stopRun = false;

	public boolean getExecStatus() {
		return execStatus;
	}

	public boolean getStartRun() {
		return startRun;
	}

	public boolean getStopRun() {
		return stopRun;
	}

	public boolean getAssertRst() {
		return assertRst;
	}

	public String getRstErrMsg() {
		return rstErrMsg;
	}

	public void clearRstErrInfo() {
		rstErrMsg = "";
		assertRst = true;
	}

	public LogcatParserTh(String strFilePath, ArrayList<String> assertList,
			ArrayList<String> assertExistFlag) {
		filePath = strFilePath;
		this.assertList = assertList;
		this.assertExistFlag = assertExistFlag;

		assertRst = true;
		execStatus = false;
		startRun = false;
		stopRun = false;
		closeBR();
	}

	public void run() {
		if (stopRun || execStatus) {
			return;
		}
		execStatus = true;
		startRun = true;
		logger.debug("log分析进程 开始运行。");
		try {
			fileParser();
			logger.debug("log分析结果：" + assertRst);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("log分析进程 结束运行。");
			execStatus = false;
			closeBR();
		}
	}

	/*
	 * 关闭br
	 */
	public void closeBR() {
		try {
			if (br != null) {
				br.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		br = null;
	}

	/*
	 * 读取文件
	 */
	public void fileParser() {
		try {
			if (br == null) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(filePath))));
			}

			String strLine;
			//判断settings.xml文件中的isexist字段,重新组件validAssertList，
			//validAssertList中包含需要验证的字符串序列
			ArrayList<String> validAssertList = new ArrayList<String>();
			for (int i = 0; i < assertList.size(); i++) {
				if ("true".equals(assertExistFlag.get(i))) {
					continue;
				}
				validAssertList.add(assertList.get(i));
			}
			while (true) {
				if (stopRun) {
					return;
				}
				strLine = br.readLine();
				if (strLine == null) {
					continue;
				}
				for (String errLog : validAssertList) {
					if (strLine.contains(errLog)) {
						rstErrMsg = "log中出现信息：" + errLog;
						logger.error(rstErrMsg);
						assertRst = false;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return;
	}

	public void stopRun() {
		logger.debug("log分析进程 停止运行。");
		stopRun = true;
	}

}
