package com.hisense.autotest.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jfree.data.time.Millisecond;

import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.device.AdbOperation;
import com.hisense.autotest.util.Utils;

public class RecordResrcTh extends Thread {
	private static Logger logger = Logger.getLogger(ExecRScriptTh.class);
	private Utils utils = new Utils();
	private AdbOperation adbOpe = new AdbOperation();

	private static boolean execStatus = false;
	private static boolean startRun = false;
	private volatile boolean stopRun = false;

	private static int fInterval = 0;
	private float ratioCPU;
	private float ratioMem;
	private String cpuInfo;
	private String memInfo;
	private String deviceIp = "";
	private int optType;

	private String cpufilePath;
	private String memfilePath;
	private String cpuDetailfilePath;
	private String memDetailfilePath;
	private BufferedWriter bwCPU = null;
	private BufferedWriter bwMem = null;
	private BufferedWriter bwCPUDetail = null;
	private BufferedWriter bwMemDetail = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public RecordResrcTh(String deviceIp, int interval, int optType, String testRstTimePath) {
		cpufilePath = testRstTimePath + File.separator + "Resource" + File.separator + "cpu.txt";
		memfilePath = testRstTimePath + File.separator + "Resource" + File.separator + "memory.txt";
		cpuDetailfilePath = testRstTimePath + File.separator + "Resource" + File.separator + "cpuDetail.txt";
		memDetailfilePath = testRstTimePath + File.separator + "Resource" + File.separator + "memoryDetail.txt";
		this.deviceIp = deviceIp;
		fInterval = interval;
		this.optType = optType;

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
		logger.debug("折线图进程 开始运行。");
		execStatus = true;
		startRun = true;
		try {
			while (true) {
				if (stopRun) {
					break;
				}
				getRatio();
				if (ratioCPU > 0 && ratioMem > 0) {
					addGraph();
				}
				if (stopRun) {
					break;
				}
				utils.pause(fInterval);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			execStatus = false;
			endWriteResrc();
		}
	}

	/*
	 * 获取cpu、内存占用信息
	 */
	private void getRatio() {
		ratioCPU = 0;
		ratioMem = 0;
		cpuInfo = adbOpe.getCpuInfo(deviceIp);
		memInfo = adbOpe.getMemInfo(deviceIp);
		int idxCpuTotal = cpuInfo.indexOf("% TOTAL:");
		int idxMemTotal = memInfo.indexOf("K total,");
		int idxMemFreel = memInfo.indexOf("K free,");
		if (idxCpuTotal == -1 || idxMemTotal == -1 || idxMemFreel == -1) {
			return;
		}
		for (int i = idxCpuTotal; i > 0; i--) {
			if (cpuInfo.charAt(i) == '\n') {
				ratioCPU = Float.parseFloat(cpuInfo.substring(i + 1, idxCpuTotal));
				break;
			}
		}
		int total = 0;
		int free = 0;
		for (int i = idxMemTotal; i > 0; i--) {
			if (memInfo.charAt(i) == ' ') {
				total = Integer.parseInt(memInfo.substring(i + 1, idxMemTotal));
				break;
			}
		}
		for (int i = idxMemFreel; i > 0; i--) {
			if (memInfo.charAt(i) == ' ') {
				free = Integer.parseInt(memInfo.substring(i + 1, idxMemFreel));
				break;
			}
		}
		if (total > 0 && free > 0) {
			ratioMem = 100 * Float.parseFloat(String.format("%.3f", (total - free) / (float) total));
		}
	}

	/*
	 * 刷新折线图
	 */
	private void addGraph() {
		Millisecond mili = new Millisecond();
		if (optType == Resources.MODE_MANUAL) {
			SmartAuto.rtcMCPU.addSery(mili, ratioCPU);
			SmartAuto.rtcMMem.addSery(ratioMem);
			writeCPUResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioCPU), cpuInfo);
			writeMemResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioMem), memInfo);
		} else if (optType == Resources.MODE_RANDOM) {
			SmartAuto.rtcRCPU.addSery(ratioCPU);
			SmartAuto.rtcRMem.addSery(ratioMem);
			writeCPUResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioCPU), cpuInfo);
			writeMemResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioMem), memInfo);
		} else if (optType == Resources.MODE_EXCUTE) {
			SmartAuto.rtcECPU.addSery(ratioCPU);
			SmartAuto.rtcEMem.addSery(ratioMem);
			writeCPUResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioCPU), cpuInfo);
			writeMemResrc(sdf.format(mili.getFirstMillisecond()), String.valueOf(ratioMem), memInfo);
		}
	}

	/*
	 * 资源信息输出到文件
	 */
	private void writeCPUResrc(String strDateTime, String strRatio, String detailInfo) {
		try {
			if (bwCPU == null) {
				if(!new File(cpufilePath).exists()){
					new File(cpufilePath).getParentFile().mkdirs();
				}
				bwCPU = new BufferedWriter(new FileWriter(cpufilePath, true));
			} else {
				bwCPU.write(strDateTime);
				bwCPU.write("\t");
				bwCPU.write(strRatio);
				bwCPU.write("\n");
				bwCPU.flush();
			}
			if (bwCPUDetail == null) {
				if(!new File(cpuDetailfilePath).exists()){
					new File(cpuDetailfilePath).getParentFile().mkdirs();
				}
				bwCPUDetail = new BufferedWriter(new FileWriter(cpuDetailfilePath, true));
			} else {
				bwCPUDetail.write(strDateTime);
				bwCPUDetail.write("\t");
				bwCPUDetail.write(strRatio);
				bwCPUDetail.write("\n");
				bwCPUDetail.write(detailInfo);
				bwCPUDetail.write("\n");
				bwCPUDetail.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 资源信息输出到文件
	 */
	private void writeMemResrc(String strDateTime, String strRatio, String detailInfo) {
		try {
			if (bwMem == null) {
				if(!new File(memfilePath).exists()){
					new File(memfilePath).getParentFile().mkdirs();
				}
				bwMem = new BufferedWriter(new FileWriter(memfilePath, true));
			} else {
				bwMem.write(strDateTime);
				bwMem.write("\t");
				bwMem.write(strRatio);
				bwMem.write("\n");
				bwMem.flush();
			}
			if (bwMemDetail == null) {
				if(!new File(memDetailfilePath).exists()){
					new File(memDetailfilePath).getParentFile().mkdirs();
				}
				bwMemDetail = new BufferedWriter(new FileWriter(memDetailfilePath, true));
			} else {
				bwMemDetail.write(strDateTime);
				bwMemDetail.write("\t");
				bwMemDetail.write(strRatio);
				bwMemDetail.write("\n");
				bwMemDetail.write(detailInfo);
				bwMemDetail.write("\n");
				bwMemDetail.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 终止资源信息输出
	 */
	private void endWriteResrc() {
		try {
			if (bwCPU != null) {
				bwCPU.close();
			}
			if (bwMem != null) {
				bwMem.close();
			}
			if (bwCPUDetail != null) {
				bwCPUDetail.close();
			}
			if (bwMemDetail != null) {
				bwMemDetail.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		bwCPU = null;
		bwMem = null;
		bwCPUDetail = null;
		bwMemDetail = null;
	}

	public void stopRun() {
		logger.debug("折线图进程 停止运行。");
		stopRun = true;
	}

}
