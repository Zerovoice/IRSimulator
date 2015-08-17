package com.hisense.autotest.action;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.util.ArrayList;

import com.hisense.autotest.automation.PgEIR;
import com.hisense.autotest.automation.PgReadMTKKey;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.bean.ExecCondInfo;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;

public class ExecEScriptTh extends ExecScript {

	private static Logger logger = Logger.getLogger(ExecEScriptTh.class);

	private LogRecorderTh logRecorderTh = null;// 记录日志的进程
	private RecordResrcTh recordResrcTh = null;// 折线图进程
	private ExecMScriptTh execMScriptTh = null;// 重复执行时，与录制回放模式的执行相同
	private LogcatParserTh logParserTh = null;// logcat验证进程

	private ExecCondInfo execCondInfoM = new ExecCondInfo();

	private DSerialPort spIR;
	private DSerialPort spDev;
	private Table tblScriptFiles;
	private Table tblScript;
	private int scriptFilesCnt = 0;
	private int defaultFileIndex = -1;
	private int loopTimes = 0;
	private String logFilePath = "";
	private int refreshRscInt;
	private int currSelIndex = 0;
	private ArrayList<String> gAssertList = new ArrayList<String>();
	private ArrayList<String> gAssertExistFlag = new ArrayList<String>();
	private String currUCPath = "";
	private int ucInterval;
	private String toolChkMsg = "";
	private static boolean readUCRst = true;
	private String scriptRootPath = "";

	private int mode;
	private static boolean execStatus = false;
	private static boolean startRun = false;
	private volatile boolean stopRun = false;

	public ExecEScriptTh(ExecCondInfo execCondInfo) {
		super(execCondInfo);
		tblScriptFiles = execCondInfo.getTblScriptFiles();
		tblScript = execCondInfo.getTblScript();
		loopTimes = execCondInfo.getLoopTimes();
		this.spIR = execCondInfo.getSpIR();
		this.spDev = execCondInfo.getSpDev();
		this.logFilePath = execCondInfo.getLogFilePath();
		this.refreshRscInt = execCondInfo.getRefreshRscInt();
		this.mode = execCondInfo.getMode();
		this.gAssertList = execCondInfo.getgAssertList();
		this.gAssertExistFlag = execCondInfo.getgAssertExistFlag();
		this.ucInterval = execCondInfo.getUcInterval();
		this.scriptRootPath = execCondInfo.getScriptRootPath();
		this.isLinuxTV = execCondInfo.isLinuxTV();// Kenneth
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				scriptFilesCnt = tblScriptFiles.getItemCount();
				defaultFileIndex = tblScriptFiles.getSelectionIndex();
			}
		});
		testRptPath = "";

		execStatus = false;
		startRun = false;
		stopRun = false;
	}

	public boolean getExecStatus() {
		return execStatus;
	}

	public boolean getStartRun() {
		return startRun;
	}

	public void run() {
		if (stopRun || execStatus) {
			return;
		}
		execStatus = true;
		startRun = true;
		logger.debug("读取执行模式 执行进程 开始运行。");
		try {
			setExecCondInfoM();
			prepareExec();// 执行前的准备工作
			int actLoop = 0;
			while (!stopRun && (loopTimes == 0 || actLoop < loopTimes)) {
				actLoop++;
				logger.debug("\n\n\n\n…………………………………………………………………………………………………………………………循环执行，执行第 "
						+ actLoop + " 次");
				for (currSelIndex = 0; currSelIndex < scriptFilesCnt; currSelIndex++) {
					runErrMsg = "";
					toolChkMsg = "";
					if (stopRun) {
						break;
					}
					if (currSelIndex < defaultFileIndex) {
						continue;
					} else {
						defaultFileIndex = -1;
					}
					if (actLoop > 1 || currSelIndex > 0) {
						utils.pause(ucInterval);
					}
					try {
						testRst = null;
						// 获取页面数据
						if (getDataFromPg()) {
							currStepIndex = -1;
							runScript();// 执行单个用例
						}
					} finally {
						teardownM();
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			thsStopRun();// 停止进程的运行
			execStatus = false;
            if (mode == Resources.MODE_EXCUTE || mode == Resources.MODE_MTK_READ) {
				PgEIR.setEExecStatus(false);
                PgReadMTKKey.setEExecStatus(false);
				SmartAuto.amTh.clearMonitorDevices();
				logger.debug("读取执行模式 执行进程 结束运行。");
			}
		}
	}

	/*
	 * 单个用例执行结束前的操作
	 */
	private void teardownM() {
		if (execMScriptTh != null) {
			execMScriptTh.stopRun();
			execMScriptTh.interrupt();
			// 等待执行
			while (true) {
				if (execMScriptTh.getStartRun()
						&& !execMScriptTh.getExecStatus()) {
					break;
				}
			}
		}
		execMScriptTh = null;
		if (logParserTh != null) {
			runErrMsg += logParserTh.getRstErrMsg();
		}
		if (!"".equals(runErrMsg)) {
			logger.error("执行中发生错误。file=" + currUCPath + "       errMsg="
					+ runErrMsg);
		}
		if (mode == Resources.MODE_EXCUTE) {
			String errMsg = "";
			String screenshotPath = "";
			int testRstType = Resources.TEST_RST_PASS;
			if (!"".equals(toolChkMsg)) {
				errMsg = toolChkMsg;
				testRstType = Resources.TEST_RST_FAIL;
			} else if (runErrMsg != null && !"".equals(runErrMsg)) {
				testRstType = Resources.TEST_RST_FAIL;
				errMsg = runErrMsg;
				if (deviceIp != null && !"".equals(deviceIp)) {
					if (!new File(testRstScreenFolder).exists()) {
						new File(testRstScreenFolder).mkdirs();
					}
					screenshotPath = testRstScreenFolder
							+ utils.getCurrTime(Resources.FORMAT_TIME_PATH)
							+ ".png";
					// adbOpr.takeScreenshot(deviceIp, screenshotPath);
				}
			}
			if (testRst != null) {
				testRst.addErrorMsg(errMsg);
				writeTestRpt(currUCPath.substring(scriptRootPath.length()),
						testRst);
			}
			writeSummary(testRstType, toolChkMsg, screenshotPath);
		}
	}

	/*
	 * 开始执行单个用例
	 */
	private boolean getDataFromPg() {
		if (stopRun) {
			return false;
		}
		readUCRst = true;
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				tblScriptFiles.select(currSelIndex);
				currUCPath = tblScriptFiles.getItem(currSelIndex).getText(
						Resources.SCRIPTFILE_COL_PATH);
				logger.debug("开始执行用例：" + currUCPath);
				if (PgEIR.readScriptFromFile(currUCPath, false)) {
					for (TableItem ti : tblScript.getItems()) {
						if (!ti.getText(Resources.SCRIPT_COL_KEY).contains("-")) {
							if ("".equals(deviceIp) && !isLinuxTV) {// Whale电视例外
																			// Kenneth
								toolChkMsg = "用例中有keycode按键，请先选择电视IP。";
								break;
							}
							if (isLinuxTV && spDev == null) {
								toolChkMsg = "Whale电视选择KeyCode时，执行前，请选择电视串口。";
								break;
							}
						} else {
							if (spIR == null) {
								toolChkMsg = "用例中有红外按键，请先连接遥控器串口。";
								break;
							}
						}
					}
				} else {
					readUCRst = false;
				}
			}
		});
		if (!readUCRst) {
			logger.error("用例未执行。读取用例失败。");
			return false;
		} else {
			initTestRpt(tblScript);
			if (!"".equals(toolChkMsg)) {
				logger.error("用例未执行。" + toolChkMsg);
				PgEIR.showMsg(toolChkMsg);// Kenneth 错误信息显示在界面上
				return false;
			}
		}
		return true;
	}

	private void setExecCondInfoM() {
		execCondInfoM.setSpIR(spIR);
		execCondInfoM.setSpDev(spDev);
		execCondInfoM.setTblScript(tblScript);
		execCondInfoM.setLoopTimes(1);
		execCondInfoM.setFixInterval(0);
		execCondInfoM.setLogFilePath(logFilePath);
		execCondInfoM.setDeviceIp(deviceIp);
		execCondInfoM.setRefreshRscInt(refreshRscInt);
		execCondInfoM.setTestRstTimePath(testRstTimePath);
		execCondInfoM.setMode(mode);
		execCondInfoM.setgAssertList(gAssertList);
		execCondInfoM.setgAssertExistFlag(gAssertExistFlag);
		execCondInfoM.setUcInterval(ucInterval);
		execCondInfoM.setLinuxTV(isLinuxTV);;// Kenneth

	}

	/*
	 * 执行前的准备：页面控件、其他操作线程
	 */
	private void prepareExec() throws Exception {
		if (mode != Resources.MODE_EXCUTE) {
			return;
		}
		// 页面控件可用性设置
		PgEIR.setEExecStatus(true);
        PgReadMTKKey.setEExecStatus(true);
		// 资源监控
		if (deviceIp != null && !"".equals(deviceIp)) {
			SmartAuto.amTh.addMonitorDevice(deviceIp);
			// 清空设备logcat
			adbOpr.clearLogcat(deviceIp);
			recordResrcTh = new RecordResrcTh(deviceIp, refreshRscInt,
					Resources.MODE_EXCUTE, testRstTimePath);
			recordResrcTh.start();
		}
		if (logFilePath != null && !"".equals(logFilePath)) {
			File f = new File(logFilePath);
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			// 记录日志
			if (spDev != null) {
				logRecorderTh = new LogRecorderTh(spDev, logFilePath);
			} else if (deviceIp != null && !"".equals(deviceIp)) {
				logRecorderTh = new LogRecorderTh(deviceIp, logFilePath);
			}
			if (logRecorderTh != null) {
				logRecorderTh.start();
				// 日志验证
				if (gAssertList != null && gAssertList.size() > 0) {
					logParserTh = new LogcatParserTh(logFilePath, gAssertList,
							gAssertExistFlag);
					logParserTh.start();
				}
			}
		}
	}

	/*
	 * 停止所有进程
	 */
	private void thsStopRun() {
		if (logRecorderTh != null) {
			logRecorderTh.stopRun();
			logRecorderTh.interrupt();
		}
		if (recordResrcTh != null) {
			recordResrcTh.stopRun();
			recordResrcTh.interrupt();
		}
		if (execMScriptTh != null) {
			execMScriptTh.stopRun();
			execMScriptTh.interrupt();
		}
		if (logParserTh != null) {
			logParserTh.stopRun();
			logParserTh.interrupt();
		}
		logRecorderTh = null;
		recordResrcTh = null;
		execMScriptTh = null;
		logParserTh = null;
	}

	/*
	 * 脚本执行
	 */
	private void runScript() throws InterruptedException {
		if (stopRun) {
			return;
		}
		if (logParserTh != null) {
			logParserTh.clearRstErrInfo();
		}
		execMScriptTh = new ExecMScriptTh(execCondInfoM);
		execMScriptTh.start();
		// 等待执行
		while (true) {
			if (logParserTh != null && !logParserTh.getAssertRst()) {
				logger.error("出现logcat验证错误，中止当前脚本执行。");
				break;
				// stopRun = true;
			}
			if (stopRun) {
				break;
			}
			if (execMScriptTh.getStopRun()
					|| (execMScriptTh.getStartRun() && !execMScriptTh
							.getExecStatus())) {
				break;
			}
			utils.pauseWithE(1);
		}
		logger.debug("用例执行结束：" + currUCPath);
	}

	public void stopRun() {
		logger.debug("读取执行模式 执行进程 停止运行。");
		stopRun = true;
	}

}
