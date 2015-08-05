package com.hisense.autotest.action;

import java.util.ArrayList;

import com.hisense.autotest.util.Utils;

import org.apache.log4j.Logger;

public class AdbMonitorThread extends Thread {
	private static Logger logger = Logger.getLogger(AdbMonitorThread.class);

	private static Utils utils = new Utils();
	private static int sleepInterval = 10000;

	private static ArrayList<String> ipsToBeMonitor = new ArrayList<String>();
	private static boolean uptMonitorIps = true;

	public AdbMonitorThread() {
		this.start();
	}

	public void addMonitorDevice(String strDevice) throws InterruptedException {
		while (true) {
			if (uptMonitorIps) {
				ipsToBeMonitor.add(strDevice);
				logger.debug("添加adb监控：device=" + strDevice);
				break;
			}
			utils.pause(1);
		}
	}

	public void removeMonitorDevice(String strDevice) {
		while (true) {
			if (uptMonitorIps) {
				ipsToBeMonitor.remove(strDevice);
				logger.debug("移除adb监控：device=" + strDevice);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void clearMonitorDevices() {
		while (true) {
			if (uptMonitorIps) {
				ipsToBeMonitor.clear();
				logger.debug("清空adb监控。");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public ArrayList<String> getMonitorDevices() {
		return ipsToBeMonitor;
	}

	public static ArrayList<String> findDevices() {
		String[] cmdOutput = utils.excuteCmd("adb devices").split("\n");
		ArrayList<String> deviceConnected = new ArrayList<String>();
        String[] deviceInfo;
        int index = -1;
        for (int i = 1; i < cmdOutput.length; i++) {
            deviceInfo = cmdOutput[i].split("\t");
            if (deviceInfo.length > 1 && "device".equals(deviceInfo[1])) {
                index = deviceInfo[0].lastIndexOf(":");
                if (index > 0) {
                    deviceConnected.add(deviceInfo[0].substring(0, index));
                } else {
                    deviceConnected.add(deviceInfo[0]);
                }
            }
        }
		return deviceConnected;
	}

	public void run() {
		while (true) {
			if (ipsToBeMonitor.size() > 0) {
				ArrayList<String> devicesHasConnected = findDevices();
//				System.out.println(devicesHasConnected);
				uptMonitorIps = false;
				for (String deviceip : ipsToBeMonitor) {
					if (!devicesHasConnected.contains(deviceip)) {
						logger.debug("监控到设备 " + deviceip + " 的adb连接已丢失。正在重新连接......");
						utils.excuteCmdWait("adb disconnect " + deviceip);
						utils.excuteCmdWait("adb connect " + deviceip);
					}
				}
				uptMonitorIps = true;
			}
			try {
				Thread.sleep(sleepInterval);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AdbMonitorThread awTh = new AdbMonitorThread();
			//添加设备时，不能带端口号，只能使用ip
			awTh.addMonitorDevice("192.168.137.195");
			System.out.println("等待第一个10s...");
			Thread.sleep(10000);
			awTh.clearMonitorDevices();
			System.out.println("等待第二个10s...");
			Thread.sleep(10000);
			awTh.addMonitorDevice("192.168.137.195");
			System.out.println("等待第三个10s...");
			Thread.sleep(10000);
			System.out.println("主线程结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}

}
