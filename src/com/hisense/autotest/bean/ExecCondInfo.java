
package com.hisense.autotest.bean;

import org.eclipse.swt.widgets.Table;

import java.util.ArrayList;
import java.util.Hashtable;

import com.hisense.autotest.serialport.DSerialPort;

public class ExecCondInfo {

    @Override
	public String toString() {
		return "ExecCondInfo [scriptRootPath=" + scriptRootPath + ", spIR="
				+ spIR + ", spDev=" + spDev + ", tblScriptFiles="
				+ tblScriptFiles + ", tblScript=" + tblScript + ", loopTimes="
				+ loopTimes + ", deviceIp=" + deviceIp + ", refreshRscInt="
				+ refreshRscInt + ", logFilePath=" + logFilePath
				+ ", testRstTimePath=" + testRstTimePath + ", gAssertList="
				+ gAssertList + ", gAssertExistFlag=" + gAssertExistFlag
				+ ", ucInterval=" + ucInterval + ", mode=" + mode + ", encode="
				+ encode + ", fixInterval=" + fixInterval + ", htKeymap="
				+ htKeymap + ", isLinuxTV=" + isLinuxTV + "]";
	}

	private String scriptRootPath;
    private DSerialPort spIR;
    private DSerialPort spDev;
    private Table tblScriptFiles;
    private Table tblScript;
    private int loopTimes;
    private String deviceIp;
    private int refreshRscInt;
    private String logFilePath;
    private String testRstTimePath;
    private ArrayList<String> gAssertList;
    private ArrayList<String> gAssertExistFlag;
    private int ucInterval;
    private int mode;
    private int encode;
    private float fixInterval;
    private Hashtable<String , String> htKeymap = new Hashtable<String, String>();
    //增添 Kenneth 支持loewe whale电视的电视串口测试
//    private boolean isWhaleSendIRCode=false;
    private boolean isLinuxTV=false;

    public boolean isLinuxTV() {
		return isLinuxTV;
	}

	public void setLinuxTV(boolean isLinuxTV) {
		this.isLinuxTV = isLinuxTV;
	}

//	public boolean isWhaleSendIRCode() {
//		return isWhaleSendIRCode;
//	}
//
//	public void setWhaleSendIRCode(boolean isWhaleSendIRCode) {
//		this.isWhaleSendIRCode = isWhaleSendIRCode;
//	}

	public DSerialPort getSpIR() {
        return spIR;
    }

    public void setSpIR(DSerialPort spIR) {
        this.spIR = spIR;
    }

    public DSerialPort getSpDev() {
        return spDev;
    }

    public void setSpDev(DSerialPort spDev) {
        this.spDev = spDev;
    }

    public Table getTblScriptFiles() {
        return tblScriptFiles;
    }

    public void setTblScriptFiles(Table tblScriptFiles) {
        this.tblScriptFiles = tblScriptFiles;
    }

    public Table getTblScript() {
        return tblScript;
    }

    public void setTblScript(Table tblScript) {
        this.tblScript = tblScript;
    }

    public int getLoopTimes() {
        return loopTimes;
    }

    public void setLoopTimes(int loopTimes) {
        this.loopTimes = loopTimes;
    }

    public int getRefreshRscInt() {
        return refreshRscInt;
    }

    public void setRefreshRscInt(int refreshRscInt) {
        this.refreshRscInt = refreshRscInt;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getTestRstTimePath() {
        return testRstTimePath;
    }

    public void setTestRstTimePath(String testRstTimePath) {
        this.testRstTimePath = testRstTimePath;
    }

    public ArrayList<String> getgAssertList() {
        return gAssertList;
    }

    public void setgAssertList(ArrayList<String> gAssertList) {
        this.gAssertList = gAssertList;
    }
    public ArrayList<String> getgAssertExistFlag() {
    	return gAssertExistFlag;
    }
    
    public void setgAssertExistFlag(ArrayList<String> gAssertExistFlag) {
    	this.gAssertExistFlag = gAssertExistFlag;
    }

    public int getUcInterval() {
        return ucInterval;
    }

    public void setUcInterval(int ucInterval) {
        this.ucInterval = ucInterval;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getEncode() {
        return encode;
    }

    public void setEncode(int encode) {
        this.encode = encode;
    }

    public float getFixInterval() {
        return fixInterval;
    }

    public void setFixInterval(float fixInterval) {
        this.fixInterval = fixInterval;
    }

    public Hashtable<String , String> getHtKeymap() {
        return htKeymap;
    }

    public void setHtKeymap(Hashtable<String , String> htKeymap) {
        this.htKeymap = htKeymap;
    }

    public String getScriptRootPath() {
        return scriptRootPath;
    }

    public void setScriptRootPath(String scriptRootPath) {
        this.scriptRootPath = scriptRootPath;
    }

}
