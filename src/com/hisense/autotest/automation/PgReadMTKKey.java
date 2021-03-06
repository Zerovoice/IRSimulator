
package com.hisense.autotest.automation;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;

public class PgReadMTKKey extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgReadMTKKey.class);
    public static boolean showEndMsg = true;

    protected Object result;
    protected Display display;
    protected static Shell shell;

    private static TabFolder tabFolder;
    private static TabFolder tabFolder_3;

    private static Frame fECPU;
    private static Frame fEMem;

    private static Group grpExecute;
    public static Group grpESetTool;
    private static Group grpEScript;
    private static Group grpEAction;
    private static Label mlblSerialPort;
    public static Combo comboEDevCom;
    public static Label lEDevComStatus;
    private static Button btnEDevComRefresh;
    public static Button btnEDevConnCom;
    public static Button btnEDevDisConnCom;
    private static Group grpEScriptFiles;
    public static Text txtEScriptPath;
    private static Table tblEScriptFiles;
    private static Text txtELoop;
    private static Table tblEScript;
    private static Button btnERead;
    private static Button chbEExecLoop;
    public static Button btnEExec;
    private static Button btnEStopExec;
    private static Button btn_receiver;

    private static String eSelFilePath = "";
    private static Text txtEUCInterval;
    private Label mlblS;
    private Label mlblTestCaseInterval;
    private TableColumn tblclmnNewColumn;
    private TableColumn mtblclmnVerification;
    private Button btnEEditVerPoint;
    private Button btnEDelSeled;
    private Button btnESaveScript;
    private TableColumn tblclmnNo_2;
    private TabItem mtabItem;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgReadMTKKey() {
        super();
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     * @wbp.parser.entryPoint
     */
    public Object open() {
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents() {
        display = Display.getDefault();

        shell = new Shell(display, SWT.CLOSE);
        shell.setSize(819, 688);
        shell.setText("\u7EA2\u5916\u9065\u63A7\u5668\u6A21\u62DF\u5DE5\u5177");
        shell.setLayout(null);

        tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 784, 642);

        TabItem tbtmExecute = new TabItem(tabFolder, SWT.NONE);
        tbtmExecute.setText("Read script mode");

        grpExecute = new Group(tabFolder, SWT.NONE);
        tbtmExecute.setControl(grpExecute);
        createEContents(shell, grpExecute);
    }

    /**
     * Create contents of the dialog.
     */
    public void createEContents(Shell shl, Group grpExecute) {
        shell = shl;
        isLinuxTV = true;// zxb mode
        // 脚本导入
        grpEScriptFiles = new Group(grpExecute, SWT.NONE);
        grpEScriptFiles.setText("Read script");
        grpEScriptFiles.setBounds(10, 89, 375, 339);

        txtEScriptPath = new Text(grpEScriptFiles, SWT.BORDER);
        txtEScriptPath.setBounds(10, 28, 246, 23);

        Button btnESelect = new Button(grpEScriptFiles, SWT.NONE);
        btnESelect.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                selectScripts();
            }
        });
        btnESelect.setBounds(262, 26, 49, 27);
        btnESelect.setText("Select");

        tblEScriptFiles = new Table(grpEScriptFiles, SWT.BORDER | SWT.FULL_SELECTION);
        tblEScriptFiles.setBounds(10, 57, 355, 272);
        tblEScriptFiles.setHeaderVisible(true);
        tblEScriptFiles.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int selIndex = tblEScriptFiles.getSelectionIndex();
                if (selIndex != -1) {
                    TableItem tblItem = tblEScriptFiles.getItem(selIndex);
                    readScriptFromFile(tblItem.getText(Resources.SCRIPTFILE_COL_PATH), true);
                }
            }
        });
        tblEScriptFiles.addListener(SWT.MenuDetect, new Listener() {

            @Override
            public void handleEvent(Event e) {
                // 右键菜单
                Table tblScriptFile = (Table) e.widget;
                Point pt = tblScriptFile.getDisplay().map(null, tblScriptFile, e.x, e.y);
                TableItem item = tblEScriptFiles.getItem(pt);
                showRightMenu(item);

            }
        });

        tblclmnNo_2 = new TableColumn(tblEScriptFiles, SWT.NONE);
        tblclmnNo_2.setWidth(35);
        tblclmnNo_2.setText("No.");

        TableColumn tblclmnName = new TableColumn(tblEScriptFiles, SWT.NONE);
        tblclmnName.setWidth(299);
        tblclmnName.setText("Name");

        btnERead = new Button(grpEScriptFiles, SWT.NONE);
        btnERead.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                readScriptFiles(txtEScriptPath.getText());
            }
        });
        btnERead.setText("Read");
        btnERead.setBounds(316, 26, 49, 27);
        // 工具设置
        grpESetTool = new Group(grpExecute, SWT.NONE);
        grpESetTool.setText("Settings");
        grpESetTool.setBounds(10, 20, 375, 63);

        mlblSerialPort = new Label(grpESetTool, SWT.NONE);
        mlblSerialPort.setText("Serial port");
        mlblSerialPort.setBounds(10, 25, 61, 17);

        comboEDevCom = new Combo(grpESetTool, SWT.NONE);
        comboEDevCom.setItems(comDev);
        comboEDevCom.setBounds(84, 22, 77, 25);
        comboEDevCom.select(0);
        comboEDevCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComDev(comboEDevCom.getText());
                if (connDevComSuccss && selDevSerialPort != null
                        && !selDevSerialPort.equals(comboEDevCom.getText())) {
                    disConnectCom(spDev, lEDevComStatus, btnEDevConnCom, btnEDevDisConnCom, false);
                    connDevComSuccss = false;
                }
            }
        });

        lEDevComStatus = new Label(grpESetTool, SWT.NONE);
        lEDevComStatus.setText("NotConnected");
        lEDevComStatus.setBounds(167, 25, 49, 17);

        btnEDevComRefresh = new Button(grpESetTool, SWT.NONE);
        btnEDevComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spDev);
            }
        });
        btnEDevComRefresh.setText("Refresh");
        btnEDevComRefresh.setBounds(228, 20, 41, 27);

        btnEDevConnCom = new Button(grpESetTool, SWT.NONE);
        btnEDevConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connDevComSuccss = connectTool(spDev, comboEDevCom.getText(), "", lEDevComStatus,
                        btnEDevConnCom, btnEDevDisConnCom, false);
                if (connDevComSuccss) {
                    spDev.setDevPortParameters();// 设置设备串口参数
                    startDTV();
                }
            }
        });
        btnEDevConnCom.setText("Connect");
        btnEDevConnCom.setBounds(276, 20, 41, 27);

        btnEDevDisConnCom = new Button(grpESetTool, SWT.NONE);
        btnEDevDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spDev, lEDevComStatus, btnEDevConnCom, btnEDevDisConnCom, false);
                connDevComSuccss = false;
            }
        });
        btnEDevDisConnCom.setText("DisC");
        btnEDevDisConnCom.setEnabled(false);
        btnEDevDisConnCom.setBounds(324, 20, 41, 27);
        // 执行操作
        grpEAction = new Group(grpExecute, SWT.NONE);
        grpEAction.setText("Run");
        grpEAction.setBounds(10, 434, 375, 73);

        btnEExec = new Button(grpEAction, SWT.NONE);
        btnEExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doEExecScript();
            }
        });
        btnEExec.setText("Run");
        btnEExec.setBounds(262, 38, 49, 27);

        btnEStopExec = new Button(grpEAction, SWT.NONE);
        btnEStopExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                stopExec(Resources.MODE_EXCUTE);
            }
        });
        btnEStopExec.setText("Stop");
        btnEStopExec.setEnabled(false);
        btnEStopExec.setBounds(316, 38, 49, 27);

        chbEExecLoop = new Button(grpEAction, SWT.CHECK);
        chbEExecLoop.setText("Cycle");
        chbEExecLoop.setBounds(10, 22, 69, 17);

        txtELoop = new Text(grpEAction, SWT.BORDER);
        txtELoop.setText("0");
        txtELoop.setBounds(116, 22, 47, 17);

        Label lblTimes = new Label(grpEAction, SWT.NONE);
        lblTimes.setText("Times");
        lblTimes.setBounds(169, 22, 33, 17);

        txtEUCInterval = new Text(grpEAction, SWT.BORDER);
        txtEUCInterval.setText("1");
        txtEUCInterval.setBounds(116, 48, 47, 17);
        txtEUCInterval.addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {
                Pattern pattern = Pattern.compile("[0-9]\\d*");
                Matcher matcher = pattern.matcher(e.text);
                if (e.text.length() == 0 || matcher.matches()) {
                    e.doit = true;
                } else {
                    e.doit = false;
                }
            }
        });

        mlblS = new Label(grpEAction, SWT.NONE);
        mlblS.setText("S");
        mlblS.setBounds(169, 48, 33, 17);

        mlblTestCaseInterval = new Label(grpEAction, SWT.NONE);
        mlblTestCaseInterval.setText("Testcase interval");
        mlblTestCaseInterval.setBounds(10, 48, 100, 17);

        btn_receiver = new Button(grpEAction, SWT.CHECK);
        btn_receiver.setBounds(262, 20, 80, 17);
        btn_receiver.setText("IR");

        tabFolder_3 = new TabFolder(grpExecute, SWT.NONE);
        tabFolder_3.setBounds(391, 20, 382, 552);

        TabItem tbtmEScript = new TabItem(tabFolder_3, SWT.NONE);
        tbtmEScript.setText("Steps");

        grpEScript = new Group(tabFolder_3, SWT.NONE);
        tbtmEScript.setControl(grpEScript);

        tblEScript = new Table(grpEScript, SWT.BORDER | SWT.FULL_SELECTION);
        tblEScript.setHeaderVisible(true);
        tblEScript.setBounds(10, 20, 355, 459);

        final TableEditor editorE = new TableEditor(tblEScript);
        tblEScript.addMouseListener(new MouseAdapter() {

            public void mouseDown(MouseEvent event) {
                Control old = editorE.getEditor();
                if (old != null)
                    old.dispose();

                Point pt = new Point(event.x, event.y);
                final TableItem item = tblEScript.getItem(pt);
                if (item == null) {
                    return;
                }
                if (3 == event.button) {
                    PgEditStepOrder editstep = new PgEditStepOrder(tblEScript, SWT.None);
                    editstep.editStep(tblEScript, pt);
                    return;
                }
                if (item.getBounds(Resources.SCRIPT_COL_INTERVAL).contains(pt)) {
                    // 编辑间隔时间列（第4列，index=3）
                    final int column = Resources.SCRIPT_COL_INTERVAL;
                    final Text text = new Text(tblEScript, SWT.NONE);
                    text.setForeground(item.getForeground());
                    text.setText(item.getText(column));
                    text.setForeground(item.getForeground());
                    text.selectAll();
                    text.setFocus();
                    editorE.minimumWidth = text.getBounds().width;
                    editorE.setEditor(text, item, column);

                    text.addModifyListener(new ModifyListener() {

                        public void modifyText(ModifyEvent event) {
                            String strVal = text.getText();
                            if (strVal == null || "".equals(strVal)) {
                                strVal = "1.0";
                            } else {
                                try {
                                    if (Float.parseFloat(strVal) > 0) {
                                        strVal = String.format("%.1f", Float.parseFloat(strVal));
                                    } else {
                                        strVal = "1.0";
                                    }
                                } catch (Exception e) {
                                    strVal = "1.0";
                                }
                            }
                            item.setText(column, strVal);
                        }
                    });
                } else if (item.getBounds(Resources.SCRIPT_COL_CONTENT).contains(pt)) {
                    // 编辑说明列（第3列，index=2）
                    final int column = Resources.SCRIPT_COL_CONTENT;
                    final Text text = new Text(tblEScript, SWT.NONE);
                    text.setForeground(item.getForeground());
                    text.setText(item.getText(column));
                    text.setForeground(item.getForeground());
                    text.selectAll();
                    text.setFocus();
                    editorE.minimumWidth = text.getBounds().width;
                    editorE.setEditor(text, item, column);

                    text.addModifyListener(new ModifyListener() {

                        public void modifyText(ModifyEvent event) {
                            item.setText(column, text.getText());
                        }
                    });
                }
            }
        });

        tblclmnNewColumn = new TableColumn(tblEScript, SWT.NONE);
        tblclmnNewColumn.setWidth(35);
        tblclmnNewColumn.setText("No.");

        TableColumn tblclmnKeyvalue = new TableColumn(tblEScript, SWT.NONE);
        tblclmnKeyvalue.setWidth(110);
        tblclmnKeyvalue.setText("KeyValue");

        TableColumn tblclmnInstructions = new TableColumn(tblEScript, SWT.NONE);
        tblclmnInstructions.setWidth(95);
        tblclmnInstructions.setText("Instructions");

        TableColumn tblclmnInterval = new TableColumn(tblEScript, SWT.NONE);
        tblclmnInterval.setWidth(45);
        tblclmnInterval.setText("Interval");

        mtblclmnVerification = new TableColumn(tblEScript, SWT.NONE);
        mtblclmnVerification.setWidth(48);
        mtblclmnVerification.setText("Verification");

        btnEEditVerPoint = new Button(grpEScript, SWT.NONE);
        btnEEditVerPoint.setText("Edit verif");
        btnEEditVerPoint.setBounds(10, 485, 72, 27);
        btnEEditVerPoint.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updAssertPoints(tblEScript);
            }
        });

        btnEDelSeled = new Button(grpEScript, SWT.NONE);
        btnEDelSeled.setText("Delete");
        btnEDelSeled.setBounds(221, 485, 72, 27);
        btnEDelSeled.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int selIndex = tblEScript.getSelectionIndex();
                if (selIndex != -1) {
                    tblEScript.remove(selIndex);
                    for (int i = 0; i < tblEScript.getItemCount(); i++) {
                        tblEScript.getItem(i).setText(0, String.valueOf(i + 1));
                    }
                    tblEScript.setSelection(selIndex);
                }
            }
        });

        btnESaveScript = new Button(grpEScript, SWT.NONE);
        btnESaveScript.setText("Save Script");
        btnESaveScript.setBounds(299, 485, 66, 27);
        btnESaveScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int scriptFileIndex = tblEScriptFiles.getSelectionIndex();
                if (scriptFileIndex != -1) {
                    if (tblEScript.getItemCount() < 1) {
                        showMsg(shell, "Non Steps, save failed!", SWT.ICON_INFORMATION);
                        return;
                    }
                    String savefile = tblEScriptFiles.getItem(scriptFileIndex)
                            .getText(Resources.SCRIPTFILE_COL_PATH);
                    if (savefile != null) {
                        saveScript(shell, tblEScript, savefile, Resources.MODE_EXCUTE);
                    }
                }
            }
        });

    }

    /**
     * 读取执行模式 执行按钮操作
     */
    public static void doEExecScript() {
        if (!toolCheck(null)) {
            return;
        }
        if (tblEScriptFiles.getItemCount() == 0) {
            showMsg(shell, "No scripts!", SWT.ICON_INFORMATION);
            return;
        }
        try {
            if ("".equals(txtEUCInterval.getText())) {
                txtEUCInterval.setText("1");
            }
            logger.debug("testRstTimePath = " + testRstTimePath);
            logger.debug("logFilePath = " + logFilePath);
            // 判断是否是红外接收器
//            if (btn_receiver.getSelection()) {
//                spIR.setIRReceiverPortParameters();
//            } else {
////                spIR.setDevPortParameters();
//                spIR.setIRReceiverPortParameters();
//            }
            execMTKReadScript(tblEScriptFiles, tblEScript, chbEExecLoop, txtELoop, "",
                    Integer.parseInt(txtEUCInterval.getText()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showMsg(shell, e.getMessage(), SWT.ICON_ERROR);
        }
    }

    /**
     * 执行前，工具设置信息验证
     */
    private static boolean toolCheck(Combo comboDevices) {
//        String deviceIp = comboDevices.getText();
//        if (connDevComSuccss) {
//            logger.debug("使用电视串口记录电视logcat信息。");
//        } else if (!"".equals(deviceIp)) {
//            logger.debug("使用adb logcat命令记录电视logcat信息。");
//        } else {
//            String strMsg = "未连接电视的串口并且没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能记录log信息\n2、不能进行log验证";
//            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
//            if (!gotoExec) {
//                return false;
//            }
//            logger.debug(strMsg);
//        }
//        if ("".equals(deviceIp) && !isLinuxTV) {
//            String strMsg = "没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能进行资源监控\n2、不能执行脚本STEP中的log验证\n3、不能执行脚本STEP中的sqlite验证\n4、不能执行脚本STEP中的截图验证";
////            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
////            if (!gotoExec) {
////                return false;
////            }
//            logger.debug(strMsg);
//        }
        testRstTimePath = testRstsPath + utils.getCurrTime(Resources.FORMAT_TIME_PATH)
                + File.separator;
        logFilePath = testRstTimePath + "logcat" + File.separator + "log_"
                + utils.getCurrTime(Resources.FORMAT_TIME_PATH) + ".log";
        screenshotPath = testRstTimePath + File.separator + "screenshot" + File.separator;
        return true;
    }

    /**
     * 连接工具
     */
    public static boolean connectTool(DSerialPort sp, String serialPort, String serialPortO,
            Label lComStatus, Button btnConnCom, Button btnDisConnCom, boolean isIR) {
        boolean connRst = true;
        try {
            if (serialPort == null || "".equals(serialPort)) {
                showMsg(shell, "Please select the serial port。", SWT.ICON_INFORMATION);
                connRst = false;
            } else if (serialPort.equals(serialPortO)
                    && ((isIR && connDevComSuccss) || (!isIR && connIRComSuccss))) {
                showMsg(shell, "Please select another serial port。", SWT.ICON_ERROR);
                connRst = false;
            } else if (!sp.selectPort(serialPort)) {
                showMsg(shell, "Connect failed。", SWT.ICON_ERROR);
                connRst = false;
            }
            if (connRst) {
                logger.debug("串口连接成功。" + serialPort);
                lComStatus.setText(Resources.TEXT_ON_CONN);
                btnConnCom.setEnabled(false);
                btnDisConnCom.setEnabled(true);
                if (isIR) {
                    selIRSerialPort = serialPort;
                    toolInfo.setComIRStatus(Resources.TEXT_ON_CONN);
                    toolInfo.setComIRConnEnabled(false);
                    toolInfo.setComIRDisConnEnabled(true);
                } else {
                    selDevSerialPort = serialPort;
                    toolInfo.setComDevStatus(Resources.TEXT_ON_CONN);
                    toolInfo.setComDevConnEnabled(false);
                    toolInfo.setComDevDisConnEnabled(true);
                }
            } else {
                logger.debug("串口连接失败。" + serialPort);
                sp.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showMsg(shell, "Connect failed!\n" + e.getMessage(), SWT.ICON_ERROR);
            connRst = false;
        }
        return connRst;
    }

    /*
     * 读取执行模式 -- 执行脚本时，页面控件的可用性状态
     */
    public static void setEExecStatus(boolean isExec) {
        logger.debug("设置页面控件可用性。" + isExec);
        if (isExec) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // 执行脚本时，[停止]按钮可用，其他控件不可用
                    grpESetTool.setEnabled(false);
                    grpEScriptFiles.setEnabled(false);
                    grpEScript.setEnabled(false);

                    chbEExecLoop.setEnabled(false);
                    txtELoop.setEnabled(false);
                    btnEExec.setEnabled(false);
                    btnEStopExec.setEnabled(true);

                    // 其他模式不可执行，不可更改工具设置信息
                    PgMIR.grpMSetTool.setEnabled(false);
                    PgMIR.btnMExec.setEnabled(false);
                    PgRIR.grpRSetTool.setEnabled(false);
                    PgRIR.btnRExec.setEnabled(false);
                }
            });
        } else {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // 停止执行时，[停止]按钮不可用，还原其他控件的状态
                    grpESetTool.setEnabled(true);
                    grpEScriptFiles.setEnabled(true);
                    grpEScript.setEnabled(true);

                    chbEExecLoop.setEnabled(true);
                    txtELoop.setEnabled(true);
                    btnEExec.setEnabled(true);
                    btnEStopExec.setEnabled(false);

                    // 其他模式控件状态还原
                    PgMIR.grpMSetTool.setEnabled(true);
                    PgMIR.btnMExec.setEnabled(true);
                    PgRIR.grpRSetTool.setEnabled(true);
                    PgRIR.btnRExec.setEnabled(true);

                    if (showEndMsg) {
                        showMsg(shell, "脚本执行结束。", SWT.ICON_INFORMATION);
                    } else {
                        System.exit(0);
                    }
                }
            });
        }
    }

    /*
     * 工具设置信息的连动
     */
    public void controlLinkage() {
        String strIRCom = toolInfo.getComIR();
        String strDevCom = toolInfo.getComDev();
        String strDevice = toolInfo.getDeviceIp();
//        lEIRComStatus.setText(toolInfo.getComIRStatus());
//        btnEIRConnCom.setEnabled(toolInfo.isComIRConnEnabled());
//        btnEIRDisConnCom.setEnabled(toolInfo.isComIRDisConnEnabled());
        lEDevComStatus.setText(toolInfo.getComDevStatus());
        btnEDevConnCom.setEnabled(toolInfo.isComDevConnEnabled());
        btnEDevDisConnCom.setEnabled(toolInfo.isComDevDisConnEnabled());
//        rdoEEncodeNEC.setSelection(toolInfo.isIrEncodeENCSel());
//        rdoEEncodeRC5.setSelection(toolInfo.isIrEncodeRC5Sel());
//        rdoEKeycode.setSelection(toolInfo.isKeycodeSel());
//        comboEIRCom.setText(strIRCom);
        comboEDevCom.setText(strDevCom);
//        comboEDevices.setText(strDevice);
        // 电视类型的联动
//        rdoRTVTypeAndroid.setSelection(!toolInfo.isLinuxTV());
//        rdoRTVTypeLinux.setSelection(toolInfo.isLinuxTV());
    }

    /*
     * 刷新串口
     */
    private void refreshCom(DSerialPort sp) {
        sp.initialDriver();
        String[] coms = sp.listPort();
        if (sp.getType() == Resources.TYPE_COM_IR) {
            comIR = coms;
            PgMIR.comboMIRCom.setItems(coms);
            PgRIR.comboRIRCom.setItems(coms);
//            comboEIRCom.setItems(coms);
//            comboEIRCom.select(0);
//            disConnectCom(spIR, lEIRComStatus, btnEIRConnCom, btnEIRDisConnCom, false);
        } else if (sp.getType() == Resources.TYPE_COM_DEV) {
            comDev = coms;
            PgMIR.comboMDevCom.setItems(coms);
            PgRIR.comboRDevCom.setItems(coms);
            comboEDevCom.setItems(coms);
            comboEDevCom.select(0);
            disConnectCom(spDev, lEDevComStatus, btnEDevConnCom, btnEDevDisConnCom, false);
        }
    }

    /*
     * 选择脚本
     */
    private void selectScripts() {
        DirectoryDialog ddSelect = new DirectoryDialog(shell);
        if (!"".equals(txtEScriptPath.getText())) {
            ddSelect.setFilterPath(txtEScriptPath.getText());
        }
        ddSelect.setText("Select script");
        String selfile = ddSelect.open();
        if (selfile != null) {
            txtEScriptPath.setText(selfile);
            readScriptFiles(selfile);
        }
    }

    /**
     * 导入脚本
     */
    public static void readScriptFiles(String dir) {
        tblEScriptFiles.removeAll();
        scriptRootPath = "";
        if (dir == null || "".equals(dir)) {
            showMsg(shell, "Select folder", SWT.ICON_ERROR);
            return;
        }
        if (!new File(dir).exists()) {
            showMsg(shell, "Folder not exist!" + dir, SWT.ICON_ERROR);
            return;
        }
        ArrayList<String> filePaths = utils.getScriptFiles(dir);
        int fileCnt = 0;
        for (String filePath : filePaths) {
            TableItem tableItem = new TableItem(tblEScriptFiles, SWT.NONE);
            tableItem.setText(new String[] { String.valueOf(++fileCnt), filePath });
        }
        scriptRootPath = new File(dir).getAbsolutePath() + File.separator;
    }

    // Kenneth 供后台执行方法调用
    public static void showMsg(String msg) {
        showMsg(shell, msg, SWT.ICON_ERROR);
    }

    /*
     * 读取csv文件，显示脚本内容
     */
    public static boolean readScriptFromFile(String filePath, boolean showMsg) {
        try {
            if (!new File(filePath).exists()) {
                String msg = "File not exist:" + filePath;
                logger.error(msg);
                if (showMsg) {
                    showMsg(shell, msg, SWT.ICON_ERROR);
                }
                return false;
            }
            eSelFilePath = filePath;
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    BufferedReader bf = null;
                    try {
                        tblEScript.removeAll();
                        bf = new BufferedReader(new InputStreamReader(
                                new FileInputStream(new File(eSelFilePath)), "utf-8"));
                        String strLine;
                        String[] scriptItemVal;
                        String[] splitedArray;
                        int itemCnt = 0;
                        while ((strLine = bf.readLine()) != null) {
                            if ("".equals(strLine)) {
                                continue;
                            }
                            splitedArray = strLine.split(",");
                            scriptItemVal = new String[splitedArray.length + 1];
                            scriptItemVal[0] = String.valueOf(++itemCnt);
                            for (int i = 1; i < scriptItemVal.length; i++) {
                                scriptItemVal[i] = splitedArray[i - 1];
                            }
                            TableItem tableItem = new TableItem(tblEScript, SWT.NONE);
                            tableItem.setText(scriptItemVal);
                        }
                        bf.close();
                    } catch (Exception e) {
                        try {
                            if (bf != null) {
                                bf.close();
                            }
                        } catch (Exception e1) {
                        }
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (showMsg) {
                showMsg(shell, e.getMessage(), SWT.ICON_ERROR);
            }
            return false;
        }
        return true;
    }

    /*
     * 编辑验证点
     */
    private void updAssertPoints(Table tblScript) {
        int selItemIndex = tblScript.getSelectionIndex();
        if (selItemIndex < 0) {
            showMsg(shell, "Please select a step!", SWT.ICON_INFORMATION);
            return;
        }
        TableItem selTblItem = tblScript.getItem(selItemIndex);
        Shell shlStep = new Shell(shell);
        PgVerify pgVerify = new PgVerify(shlStep, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
                selTblItem.getText(Resources.SCRIPT_COL_ASSERT));
        pgVerify.open();
        String assertPoints = pgVerify.getReturnValue();
        if (assertPoints == null || "".equals(assertPoints)) {
            selTblItem.setText(Resources.SCRIPT_COL_ASSERT, "");
            return;
        }
        selTblItem.setText(Resources.SCRIPT_COL_ASSERT, assertPoints);
    }

    /*
     * 用例一览右键菜单
     */
    private void showRightMenu(TableItem item) {
        if (item == null) {
            tblEScriptFiles.setMenu(null);
            return;
        }
        Menu menu = new Menu(tblEScriptFiles);
        tblEScriptFiles.setMenu(menu);
        MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
        menuItem.setText("Delete");
        menuItem.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                int selIndex = tblEScriptFiles.getSelectionIndex();
                if (selIndex == -1) {
                    return;
                }
                tblEScriptFiles.remove(selIndex);
                for (int i = 0; i < tblEScriptFiles.getItemCount(); i++) {
                    tblEScriptFiles.getItem(i).setText(0, String.valueOf(i + 1));
                }
                tblEScriptFiles.setSelection(selIndex);
            }
        });
    }

}
