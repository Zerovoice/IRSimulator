package com.hisense.autotest.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import com.hisense.autotest.automation.PgMIR;
import com.hisense.autotest.automation.SmartAuto;
import com.hisense.autotest.serialport.DSerialPort;

public class ExecReceiveTh extends Thread {

    private DSerialPort spIR;
    private Table tblScript;
    private boolean isRun = false;

    public ExecReceiveTh(Table tblScript, DSerialPort spIR) {
        this.tblScript = tblScript;
        this.spIR = spIR;
    }

    public void run() {
        int currPostion = 1;
        isRun = true;
        while (isRun) {
            if (spIR.getReceivedIRs().size() >= currPostion
                    && spIR.getReceivedIRs().get(currPostion - 1) != null) {
                final int currIndex = currPostion - 1;
                Display.getDefault().syncExec(new Runnable() {
                    @Override
                    public void run() {
                        // 记录按键，添加到table中
                        String strMsg = SmartAuto.receivedKeyEvent(tblScript,
                                spIR.getReceivedIRs().get(currIndex)[1], spIR
                                        .getReceivedIRs().get(currIndex)[0]);
                        if (strMsg != null && !"".equals(strMsg)) {
                            showMsg(PgMIR.shell, strMsg, SWT.ICON_ERROR);
                        }
                    }
                });
                currPostion++;
            }
        }
    }

    public boolean isRun() {
        return isRun;
    }

    public void stopRun() {
        this.isRun = false;
    }

    /**
     * 显示提示信息
     */
    protected static void showMsg(Shell shell, String msg, int level) {
        MessageBox msgBox = new MessageBox(shell, SWT.OK | level | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        msgBox.open();
    }

}
