package com.hisense.autotest.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import org.eclipse.swt.widgets.MessageBox;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import com.hisense.autotest.bean.SequenceInfo;
import com.hisense.autotest.automation.PgRIR;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.bean.ExecCondInfo;
import com.hisense.autotest.bean.RandomCondInfo;
import com.hisense.autotest.bean.TestResult;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.Utils;

public class ExecRScriptTh extends ExecScript {

	private static Logger logger = Logger.getLogger(ExecRScriptTh.class);

	private DSerialPort spIR;
	private DSerialPort spDev;
	private Table tblRScript;

	private ExecMScriptTh execMScriptTh = null;// 重复执行时，与录制回放模式的执行相同
	private RecordResrcTh recordResrcTh = null;// 折线图进程
	private LogRecorderTh logRecorderTh = null;// 记录日志的进程
	private LogcatParserTh logParserTh = null;// logcat验证进程

	private ExecCondInfo execCondInfoM = new ExecCondInfo();

	private int encode = 0;
	private int loopTimes = 0;
	private RandomCondInfo randCondInfo;
	protected static int sequenceCnt =-1 ;   //记录测试报告中的index

	private Random rnd = new Random();
	private static String randKeyEncode = "";
	private static String randKeyName = "";
	private static String randInterval = "";
	private Hashtable<String, String> htKeymap = null;

	private static float fIntervalTotal = 0;
	private static boolean isLimitTime = false;// 是否限制时间
	private static int limitSteps = 0;// 限制次数
	private static int limitTime = 0;// 限制时间，单位为秒
	private int refreshRscInt;
	private String logFilePath = "";
	private ArrayList<String> gAssertList = new ArrayList<String>();
	private ArrayList<String> gAssertExistFlag = new ArrayList<String>();
	private int ucInterval;

	private int mode;
	private static boolean execStatus = false;
	private static boolean startRun = false;
	private volatile boolean stopRun = false;
	private volatile boolean flag = true;
    private int stepCnt;

	public ExecRScriptTh(ExecCondInfo execCondInfo, RandomCondInfo rcInfo) {
		super(execCondInfo);
		spIR = execCondInfo.getSpIR();
		spDev = execCondInfo.getSpDev();
		logFilePath = execCondInfo.getLogFilePath();
		encode = execCondInfo.getEncode();
		loopTimes = execCondInfo.getLoopTimes();
		refreshRscInt = execCondInfo.getRefreshRscInt();
		mode = execCondInfo.getMode();
		gAssertList = execCondInfo.getgAssertList();
		gAssertExistFlag = execCondInfo.getgAssertExistFlag();
		ucInterval = execCondInfo.getUcInterval();
		htKeymap = execCondInfo.getHtKeymap();
		isLinuxTV = execCondInfo.isLinuxTV();// KENNETH
		tblRScript = execCondInfo.getTblScript();
		randCondInfo = rcInfo;
		limitSteps = rcInfo.getStepCnt();
		limitTime = rcInfo.getMaxTime();
		fIntervalTotal = 0;
		testRst = new TestResult(0);
		testRptPath = "";
		currStepIndex = -1;

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
		logger.debug("随机操作模式 执行进程 开始运行。");
		try {
			runErrMsg = "";
			prepareExec();// 执行前的准备工作
			rnd.setSeed(randCondInfo.getRandNum());
			int minTime10 = (int) (randCondInfo.getMinInterval() * 10);
			int maxTime10 = (int) (randCondInfo.getMaxInterval() * 10);

			if (Resources.SCR_LIMIT_TIME == randCondInfo.getScriptLimit()) {
				isLimitTime = true;
			} else {
				isLimitTime = false;
			}
			if (logParserTh != null && !logParserTh.getAssertRst()) {
				stopRun = true;
			}
			if (stopRun) {
				return;
			}

			// 循环执行随机脚本
			if (!runRandScript(minTime10, maxTime10 - minTime10)) {// 随机按键生成并执行
				return;
			}
			 currStepIndex = sequenceCnt -1; 
			int loop = -1;
			if (loopTimes == 0) {// 选中循环，并且循环次数为0，此时一直循环执行table中的按键操作
				loop = 0;
			} else if (loopTimes > 1) {// 选中循环，并且循环次数>1，循环执行table中的按键操作
				loop = loopTimes - 1;
			}
			if (loop >= 0) {
				utils.pause(ucInterval);
				selTableTopItem();
				setExecCondInfoM(loop);
				String[] step = testRst.getRstStepNodes(currStepIndex);
				step[Resources.SCRIPT_COL_INTERVAL - 1] = String.valueOf(Float
						.parseFloat(step[Resources.SCRIPT_COL_INTERVAL - 1])
						+ ucInterval);
				testRst.setRstStepNodes(currStepIndex, step);
				initTestRptDup(tblRScript);
				execMScriptTh = new ExecMScriptTh(execCondInfoM);
				execMScriptTh.start();
				waifForScriptRun();
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			runErrMsg += e.getMessage();
		} finally {
			teardown();
			SmartAuto.amTh.clearMonitorDevices();
			logger.debug("随机操作模式 执行进程 结束运行。");
		}
	}

	/*
	 * 执行结束前的操作
	 */
	private void teardown() {
		if (logParserTh != null) {
			runErrMsg += logParserTh.getRstErrMsg();
		}
		thsStopRun();// 停止进程的运行
		if (mode == Resources.MODE_RANDOM) {
			int testRstType = Resources.TEST_RST_NOTRUN;
			String screenshotPath = "";
			if (runErrMsg != null && !"".equals(runErrMsg)) {
				testRstType = Resources.TEST_RST_FAIL;
				if (deviceIp != null && !"".equals(deviceIp)) {
					if (!new File(testRstScreenFolder).exists()) {
						new File(testRstScreenFolder).mkdirs();
					}
					screenshotPath = testRstScreenFolder
							+ utils.getCurrTime(Resources.FORMAT_TIME_PATH)
							+ ".png";
					// adbOpr.takeScreenshot(deviceIp, screenshotPath);
				}
			} else {
				testRstType = Resources.TEST_RST_PASS;
			}
			writeTestRpt("tmp.xml", testRst);
			writeSummary(testRstType, runErrMsg, screenshotPath);
			PgRIR.setRExecStatus(false, runErrMsg);
		}
		execStatus = false;
	}

	private void setExecCondInfoM(int loop) {
		execCondInfoM.setSpIR(spIR);
		execCondInfoM.setSpDev(spDev);
		execCondInfoM.setTblScript(tblRScript);
		execCondInfoM.setLoopTimes(loop);
		execCondInfoM.setFixInterval(0);
		execCondInfoM.setLogFilePath(logFilePath);
		execCondInfoM.setDeviceIp(deviceIp);
		execCondInfoM.setRefreshRscInt(refreshRscInt);
		execCondInfoM.setTestRstTimePath(testRstTimePath);
		execCondInfoM.setMode(mode);
		execCondInfoM.setgAssertList(gAssertList);
		execCondInfoM.setgAssertExistFlag(gAssertExistFlag);
		execCondInfoM.setUcInterval(ucInterval);
	}

	/*
	 * 执行前的准备：页面控件、其他操作线程
	 */
	private void prepareExec() throws Exception {
		if (mode != Resources.MODE_RANDOM) {
			return;
		}
		// 页面控件可用性设置
		PgRIR.setRExecStatus(true, "");
		// 资源监控
		if (deviceIp != null && !"".equals(deviceIp)) {
			SmartAuto.amTh.addMonitorDevice(deviceIp);
			// 清空设备logcat
			adbOpr.clearLogcat(deviceIp);
			recordResrcTh = new RecordResrcTh(deviceIp, refreshRscInt,
					Resources.MODE_RANDOM, testRstTimePath);
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
		}
		if (recordResrcTh != null) {
			recordResrcTh.stopRun();
		}
		if (execMScriptTh != null) {
			execMScriptTh.stopRun();
			// 等待执行
			while (true) {
				if (execMScriptTh.getStartRun()
						&& !execMScriptTh.getExecStatus()) {
					break;
				}
			}
		}
		if (logParserTh != null) {
			logParserTh.stopRun();
		}
		logRecorderTh = null;
		recordResrcTh = null;
		execMScriptTh = null;
		logParserTh = null;
	}

	/*
	 * 等待脚本执行
	 */
	private void waifForScriptRun() throws InterruptedException {
		if (execMScriptTh == null) {
			return;
		}
		while (true) {
			if (logParserTh != null && !logParserTh.getAssertRst()) {
				stopRun = true;
			}
			if (stopRun) {
				if (execMScriptTh != null) {
					execMScriptTh.stopRun();
				}
				break;
			}
			if (execMScriptTh.getStopRun()
					|| (execMScriptTh.getStartRun() && !execMScriptTh
							.getExecStatus())) {
				break;
			}
			utils.pauseWithE(1);
		}
	}

	  /**
     * 执行随机脚本
     */
    private boolean runRandScript(int minTime10, int rngTime10)
            throws InterruptedException {
        String[] keys = randCondInfo.getRangeKeys();
        String[] keysName = randCondInfo.getRangeKeysName();
        ArrayList<SequenceInfo[]> sequenceList = randCondInfo
                .getRangeSequence();
        boolean result = true;
        result = runRand(sequenceList, keys, keysName, minTime10, rngTime10);
        return result;
    }

    /**
     * 执行随机的键值
     * 
     * @return
     * @throws InterruptedException
     * @throws NumberFormatException
     */
    private boolean runRand(ArrayList<SequenceInfo[]> sequenceList,
            String[] keys, String[] keysName, int minTime10, int rngTime10)
            throws NumberFormatException, InterruptedException {
        int keyIndex = 0;
        boolean runRst = true;
        int keyCnt;
        if (Utils.isSelected(PgRIR.rdoSequence)) { // 随机选择配置的序列
            int sequenceLength = sequenceList.size();
            if (keys.length == 1 && "".equals(keys[0])) {
                keyCnt = sequenceLength;
            } else {
                keyCnt = sequenceLength + keys.length;
            }
            stepCnt = 0;
            sequenceCnt = 0;
            while (true) {
                currStepIndex++;
                keyIndex = rnd.nextInt(keyCnt);
                if (sequenceLength == 0) { // 没有选择随机序列
                    if (!runRandKeys(minTime10, rngTime10, keyIndex, keys,
                            keysName)) {
                        runRst = flag;
                        break;
                    }
                } else if (keyIndex >= sequenceLength) { // 选择的是发送按键
                    keyIndex = keyIndex - sequenceLength;
                    if (!runRandKeys(minTime10, rngTime10, keyIndex, keys,
                            keysName)) {
                        runRst = flag;
                        break;
                    }
                } else {        //发送序列
                    String code = "";
                    String name = "";
                    String interval = "";
                    for (int i = 0; i < sequenceList.get(keyIndex).length; i++) {
                        code += htKeymap.get(Resources.ENCODE_PREFIX[encode]
                                + sequenceList.get(keyIndex)[i].getValue())
                                + ",";
                        name += sequenceList.get(keyIndex)[i].getValue() + ",";
                        interval += sequenceList.get(keyIndex)[i].getInterval()
                                + ",";
                    }
                    if(!runRandSequence(code, name, interval)){
                        runRst = flag;
                        break ;
                    }
                }
            }
        } else {  //发送其他选项
            keyCnt = keys.length;
            stepCnt = 0;
            sequenceCnt = 0;
            while (true) {
                currStepIndex++;
                keyIndex = rnd.nextInt(keyCnt);
                if (!runRandKeys(minTime10, rngTime10, keyIndex, keys, keysName)) {
                    runRst = flag;
                    break;
                }
            }
        }
        return runRst;
    }

    /**
     * 执行随机序列测试
     */
    private boolean runRandSequence(String code, String names, String interval)
            throws NumberFormatException,InterruptedException {
        String[] keyValue = code.split(",");
        String[] name = names.split(",");
        String[] time = interval.split(",");
        for (int i = 0; i < keyValue.length; i++) {
            if (i==(keyValue.length-1) || "".equals(time[i])) {
                randInterval = "1.0";
            } else {
                randInterval = time[i];
            }
            randKeyEncode = keyValue[i];
            randKeyName = name[i];
            if("".equals(randKeyEncode)||"null".equals(randKeyEncode)){
                String tip =randKeyName+"键的"+Resources.ENCODES[encode]+"获取失败";
                showMsg(tip);
                flag = false ;
                return false;
            }
            if (encode == Resources.ENCODE_KEYCODE) {
                if (isLinuxTV) {// Kenneth
                    spDev.write("set_ir_mode " + randKeyEncode);
                    logger.debug("发送的键值为 set_ir_mode " + randKeyEncode);
                } else {
                    adbOpr.keyevent(deviceIp, keyValue[i]);
                }
            } else {
                    inputIRKey();
            }
            addToTable();
            testRst.addStep();
            testRst.setRstStepNodes(sequenceCnt, new String[] {
                    randKeyEncode, randKeyName, randInterval, "" });
            testRst.setStepFlag(sequenceCnt, true);
            utils.pauseWithE(Float.parseFloat(randInterval));
            sequenceCnt ++ ;
        }
        return endSend();
    }

    /**
     * 随机按键
     * 
     * @throws InterruptedException
     * @throws NumberFormatException
     */
    private boolean runRandKeys(int minTime10, int rngTime10, int keyIndex,
            String[] keys, String[] keysName) throws NumberFormatException,InterruptedException {
        if (rngTime10 == 0) {
            randInterval = String.format("%.1f", minTime10 / 10.0);
        } else {
            randInterval = String.format("%.1f", (minTime10 + rnd.nextFloat()
                    * rngTime10) / 10.0);
        }
        randKeyEncode = htKeymap.get(Resources.ENCODE_PREFIX[encode]
                + keys[keyIndex]);
        randKeyName = keysName[keyIndex];
        if(randKeyEncode==null || "".equals(randKeyEncode)){
            String tip =randKeyName+"键的"+Resources.ENCODES[encode]+"获取失败";
            showMsg(tip);
            flag = false ;
            return false;
        }
        if (encode == Resources.ENCODE_KEYCODE) {
            if (isLinuxTV) {// Kenneth
                spDev.write("set_ir_mode " + randKeyEncode);
                logger.debug("发送的键值为 set_ir_mode " + randKeyEncode);
            } else {
                adbOpr.keyevent(deviceIp, randKeyEncode);
            }
        } else {
             inputIRKey();
        }
        addToTable();
        testRst.addStep();
        testRst.setRstStepNodes(sequenceCnt, new String[] { randKeyEncode,
                randKeyName, randInterval, "" });
        testRst.setStepFlag(sequenceCnt, true);
        utils.pauseWithE(Float.parseFloat(randInterval));
        sequenceCnt ++ ;
        return endSend();
    }

    /**
     * 发送键值后的判断操作
     */
    private boolean endSend() throws NumberFormatException,
            InterruptedException {
        if (logParserTh != null && !logParserTh.getAssertRst()) {
            stopRun = true;
        }
        if (stopRun) {
            flag = false;
            return false;
        }
        if (isLimitTime) {// 限制时间
            fIntervalTotal += Float.parseFloat(randInterval);
            if (fIntervalTotal > limitTime) {
                return false;
            }
        } else {// 限制次数
            stepCnt++;
            if (stepCnt >= limitSteps) {
                return false;
            }
        }
        return true;
    }

	/**
	 * 按下红外键值
	 */
	private void inputIRKey() {
		String[] currItemKeys = randKeyEncode.split("-");
		byte[] inputvalues = new byte[5];
		inputvalues[0] = (byte) (Integer.parseInt(currItemKeys[0], 16));
		inputvalues[1] = (byte) (Integer.parseInt(currItemKeys[1], 16));
		inputvalues[2] = (byte) (Integer.parseInt(currItemKeys[2], 16));
		inputvalues[3] = (byte) (Integer.parseInt(currItemKeys[3], 16));
		inputvalues[4] = (byte) (Integer.parseInt(currItemKeys[4], 16));
		spIR.write(inputvalues);
	}

	/**
	 * 在列表中添加随机操作
	 */
	private void addToTable() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				int itemCnt = tblRScript.getItemCount();
				TableItem tableItem = new TableItem(tblRScript, SWT.NONE);
				tableItem.setText(new String[] { String.valueOf(itemCnt + 1),
						randKeyEncode, randKeyName, randInterval });
				tblRScript.setTopIndex(itemCnt + 1);
				tblRScript.setSelection(itemCnt);
			}
		});
	}

	/**
	 * 选中第一行
	 */
	private void selTableTopItem() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				tblRScript.setTopIndex(0);
				tblRScript.setSelection(0);
			}
		});
	}

	public void stopRun() {
		if (mode == Resources.MODE_RANDOM) {
			logger.debug("随机操作模式 执行进程 停止运行。");
		}
		stopRun = true;
		if (recordResrcTh != null) {
			recordResrcTh.stopRun();
		}
		if (execMScriptTh != null) {
			execMScriptTh.stopRun();
		}
	}
	/**
     * 显示提示信息
     */
    private void showMsg(final String msg) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                MessageBox msgBox = new MessageBox(PgRIR.shell, SWT.OK
                        | SWT.CENTER);
                msgBox.setText("提示信息");
                msgBox.setMessage(msg);
                msgBox.open();
            }
        });
    }

}
