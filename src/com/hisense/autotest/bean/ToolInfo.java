package com.hisense.autotest.bean;

public class ToolInfo {

	private String comIR;
	private String comIRStatus;
	private boolean comIRConnEnabled;
	private boolean comIRDisConnEnabled;
	private String comDev;
	private String comDevStatus;
	private boolean comDevConnEnabled;
	private boolean comDevDisConnEnabled;
	private String deviceIp;
	private boolean irEncodeENCSel;
	private boolean irEncodeRC5Sel;
    private boolean keycodeSel;
//	private String[] irComs;
//	private String[] devComs;
//	private String[] devices;
    //不再单独提 whale tv、sx6 uhd tv，统一使用linux tv
    protected  boolean isLinuxTV = false;

    //增添 Kenneth 支持loewe whale电视的电视串口测试
//    protected  boolean isWhaleSendIRCode=false;
    //增添 Kenneth 支持SX6电视的电视串口测试
////    protected  boolean isSX6SendIRCode=false;//取消支持SX6 UHD
//
//	public boolean isSX6SendIRCode() {
//		return isSX6SendIRCode;
//	}
//
//	public void setSX6SendIRCode(boolean isSX6SendIRCode) {
//		this.isSX6SendIRCode = isSX6SendIRCode;
//	}
//
//	public boolean isWhaleSendIRCode() {
//		return isWhaleSendIRCode;
//	}
//
//	public void setWhaleSendIRCode(boolean isWhaleSendIRCode) {
//		this.isWhaleSendIRCode = isWhaleSendIRCode;
//	}

	public boolean isLinuxTV() {
		return isLinuxTV;
	}

	public void setLinuxTV(boolean isLinuxTV) {
		this.isLinuxTV = isLinuxTV;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public boolean isIrEncodeENCSel() {
		return irEncodeENCSel;
	}

	public void setIrEncodeENCSel(boolean irEncodeENCSel) {
		this.irEncodeENCSel = irEncodeENCSel;
	}

	public boolean isIrEncodeRC5Sel() {
		return irEncodeRC5Sel;
	}

	public void setIrEncodeRC5Sel(boolean irEncodeRC5Sel) {
		this.irEncodeRC5Sel = irEncodeRC5Sel;
	}

	public String getComIR() {
		return comIR;
	}

	public void setComIR(String comIR) {
		this.comIR = comIR;
	}

	public String getComIRStatus() {
		return comIRStatus;
	}

	public void setComIRStatus(String comIRStatus) {
		this.comIRStatus = comIRStatus;
	}

	public boolean isComIRDisConnEnabled() {
		return comIRDisConnEnabled;
	}

	public void setComIRDisConnEnabled(boolean comIRDisConnEnabled) {
		this.comIRDisConnEnabled = comIRDisConnEnabled;
	}

	public boolean isComIRConnEnabled() {
		return comIRConnEnabled;
	}

	public void setComIRConnEnabled(boolean comIRConnEnabled) {
		this.comIRConnEnabled = comIRConnEnabled;
	}

	public String getComDevStatus() {
		return comDevStatus;
	}

	public void setComDevStatus(String comDevStatus) {
		this.comDevStatus = comDevStatus;
	}

	public String getComDev() {
		return comDev;
	}

	public void setComDev(String comDev) {
		this.comDev = comDev;
	}

	public boolean isComDevConnEnabled() {
		return comDevConnEnabled;
	}

	public void setComDevConnEnabled(boolean comDevConnEnabled) {
		this.comDevConnEnabled = comDevConnEnabled;
	}

	public boolean isComDevDisConnEnabled() {
		return comDevDisConnEnabled;
	}

	public void setComDevDisConnEnabled(boolean comDevDisConnEnabled) {
		this.comDevDisConnEnabled = comDevDisConnEnabled;
	}

    public boolean isKeycodeSel() {
        return keycodeSel;
    }

    public void setKeycodeSel(boolean keycodeSel) {
        this.keycodeSel = keycodeSel;
    }

//	public String[] getIRComs() {
//		return irComs;
//	}
//
//	public void setIRComs(String[] irComs) {
//		this.irComs = irComs;
//	}
//
//	public String[] getDevComs() {
//		return devComs;
//	}
//
//	public void setDevComs(String[] devComs) {
//		this.devComs = devComs;
//	}
//
//	public String[] getDevices() {
//		return devices;
//	}
//
//	public void setDevices(String[] devices) {
//		this.devices = devices;
//	}
}
