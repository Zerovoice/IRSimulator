package com.hisense.autotest.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Element;

public class verifyLog {
	private static Logger logger = Logger.getLogger(ExecScript.class);

	protected String logFilePath = "";
	protected static String runErrMsg = "";
	private FileInputStream in = null;
	private int befFileLen = 0;
	private File f;
	private String partFileContent = "";
	private boolean globalIsExist = false;

	public verifyLog(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	/**
	 * 单步LOG验证
	 * 
	 * @param verElm
	 * @return
	 * @throws Exception
	 */
	public boolean doStepVerify(Element verElm) throws Exception {
		logger.debug("log验证开始。");
		if (verElm == null || "".equals(verElm)) {
			return true;
		}
		String expVal = verElm.getText();
		boolean isExist = true;
		if ("false".equalsIgnoreCase(verElm.attributeValue("isexist"))) {
			isExist = false;
		}
		readFile(expVal);
		logger.debug("expectValue=" + expVal);
		logger.debug("isExist=" + isExist);
		boolean assRst = matchPartFileContent(expVal);
		if (!isExist) {
			assRst = !assRst;
		}
		if (!assRst) {
			runErrMsg += "log验证失败，" + expVal + ".";
		}
		logger.debug("log验证结果：" + assRst);
		return assRst;
	}

	/**
	 * 读取本步骤对应的partlog保存到partFileContent中，如果过大则判断超长部分是否含有期望字符，并改变globalIsExist值。
	 * 
	 * @param expVal
	 */
	private void readFile(String expVal) {
		try {
			partFileContent = "";
			if (in == null) {
				f = new File(logFilePath);
				in = new FileInputStream(f);
			}
			if (in == null) {
				return;
			}
			Long fileLength = f.length();
			int partLogcatLen = fileLength.intValue() - befFileLen;
			boolean doGlobalCheck = false;
			if (partLogcatLen > 8500000) {
				doGlobalCheck = true;
				partLogcatLen = 8500000;
			}
			byte[] tempFileBytes = new byte[befFileLen];
			in.read(tempFileBytes, 0, befFileLen);
			tempFileBytes = new byte[partLogcatLen];
			in.read(tempFileBytes, 0, partLogcatLen);
			partFileContent = new String(tempFileBytes, "UTF-8");
			if (doGlobalCheck) {
				GlobalCheck(fileLength, expVal);
			}
			befFileLen = fileLength.intValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
				in = null;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 检查超出长度部分LOG是否包含字符串
	 * @param fileLength
	 * @param expVal
	 * @throws IOException
	 */
	public void GlobalCheck(long fileLength,String expVal) {
		Pattern patternExpVal = Pattern.compile("[\\s\\S]" + expVal
				+ "[\\s\\S]*");
		Long leftfileLength = fileLength - 8500000;
		Long i = null;
		if (leftfileLength > 8500000) {
			for (i = (long) 0; i < leftfileLength - 8500000; i += 8500000) {
				checkExpVal(8500000,patternExpVal);
				}
				checkExpVal(leftfileLength.intValue()
						- i.intValue(),patternExpVal);
		} else {
			checkExpVal(leftfileLength.intValue(),patternExpVal);
			}
	}
	/**
	 * 检查strLength长度内是否包含目标字符串
	 * @param strLength
	 * @param patternExpVal
	 */
	public void checkExpVal(int strLength, Pattern patternExpVal) {
		byte[] filecontent;
		filecontent = new byte[strLength];
		try {
			in.read(filecontent, 0, strLength);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		String tmppartFileContent = new String(filecontent);
		if (!globalIsExist) {
			globalIsExist = patternExpVal.matcher(tmppartFileContent).matches();
		}
	}
	

	/**
	 * 判断PartFileContent中是否含有目标字符串
	 * 
	 * @param expectValue
	 * @return
	 */
	public boolean matchPartFileContent(String expectValue) {
		String expVal = expectValue;
		if (!expVal.startsWith("[\\s\\S]*")) {
			expVal = "[\\s\\S]*" + expVal;
		}
		if (!expVal.endsWith("[\\s\\S]*")) {
			expVal = expVal + "[\\s\\S]*";
		}
		// 如果超出长度中含有字符，则直接返回true。
		if (globalIsExist) {
			return true;
		}
		Pattern pattern = Pattern.compile(expVal);
		return pattern.matcher(partFileContent).matches();
	}
}
