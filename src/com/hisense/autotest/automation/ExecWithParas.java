
package com.hisense.autotest.automation;

import org.apache.log4j.Logger;

import java.io.File;

public class ExecWithParas extends SmartAuto {

    private static Logger logger = Logger.getLogger(ExecWithParas.class);
    private boolean execOver = false;

    public ExecWithParas() {
        PgEIR.showEndMsg = true;
        execOver = false;
    }

    public void execScripts(String[] args) {
        if (!checkArgs(args)) {
            PgIRSimulator.shell.dispose();
            return;
        }
        setPgTexts(args);
        if (!importScript(args[2])) {
            PgIRSimulator.shell.dispose();
            return;
        }
        if (!connectSPs(args[0], args[1])) {
            PgIRSimulator.shell.dispose();
            return;
        }
        PgEIR.showEndMsg = false;
        // 切换到读取执行选项卡
        String tabType = "E";
        if (args.length > 4) {
            tabType = args[4];
        }
        if ("M".equals(tabType)) {
            PgIRSimulator.tabFolder.setSelection(0);
            PgMIR.setScript(args[2]);
            PgMIR.doMExecScript();
        } else {
            PgIRSimulator.tabFolder.setSelection(2);
            PgEIR.doEExecScript();
        }

//        PgIRSimulator.shell.dispose();
        execOver = true;
    }

    public boolean isExecOver() {
        return execOver;
    }

    /**
     * 检查输入参数，至少需要输入3个参数
     * 参数1：遥控器串口、参数2：电视串口、参数3：用例脚本所在文件夹
     */
    private boolean checkArgs(String[] args) {
        if (args == null || args.length < 3) {
            logger.error("至少需要输入3个参数（参数1：遥控器串口、参数2：电视串口、参数3：用例脚本所在文件夹、参数4：jar包所在文件夹）。");
            return false;
        }
        logger.debug("遥控器串口：" + args[0]);
        logger.debug("电视串口：" + args[1]);
        logger.debug("用例脚本所在文件夹：" + args[2]);
        return true;
    }

    /**
     * 设置页面中的参数值
     */
    private void setPgTexts(String[] args) {
        PgEIR.comboEIRCom.setText(args[0]);
        PgEIR.comboEDevCom.setText(args[1]);
        PgEIR.txtEScriptPath.setText(args[2]);
    }

    /**
     * 导入脚本
     */
    private boolean importScript(String dir) {
        if (dir == null || "".equals(dir)) {
            logger.error("没有输入脚本路径：" + dir);
            return false;
        }
        if (!new File(dir).exists()) {
            logger.error("选择的目录不存在。" + dir);
            return false;
        }
        PgEIR.readScriptFiles(dir);
        return true;
    }

    /**
     * 连接遥控器串口、电视串口
     */
    private boolean connectSPs(String irCOMName, String devCOMName) {
        spIR.close();
        spDev.close();
        connIRComSuccss = PgEIR.connectTool(spIR, PgEIR.comboEIRCom.getText(),
                PgEIR.comboEDevCom.getText(), PgEIR.lEIRComStatus, PgEIR.btnEIRConnCom,
                PgEIR.btnEIRDisConnCom, true);
        if (!connIRComSuccss) {
            logger.error("遥控器串口连接失败：" + irCOMName);
            return false;
        }
        connDevComSuccss = PgEIR.connectTool(spDev, PgEIR.comboEDevCom.getText(),
                PgEIR.comboEIRCom.getText(), PgEIR.lEDevComStatus, PgEIR.btnEDevConnCom,
                PgEIR.btnEDevDisConnCom, false);
        if (!connDevComSuccss) {
            logger.error("电视串口连接失败：" + devCOMName);
            return false;
        }
        if (connDevComSuccss) {
            spDev.setDevPortParameters();// 设置电视串口参数
        }
        return true;
    }

}
