package com.hisense.autotest.automation;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
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
import org.eclipse.swt.widgets.Composite;
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

import com.hisense.autotest.chart.RealTimeChartECPU;
import com.hisense.autotest.chart.RealTimeChartEMem;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;

public class PgEIR extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgIRSimulator.class);
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
    public static Combo comboEIRCom;
    private static Label label_14;
    public static Label lEIRComStatus;
    public static Button btnEIRConnCom;
    private static Label label_16;
    private static Button rdoEEncodeNEC;
    private static Button rdoEEncodeRC5;
    private static Button rdoRTVTypeAndroid;// Kenneth
    private static Button rdoRTVTypeLinux;
//    private static Button rdoRTVTypeSX6;
    private static Button rdoEKeycode;
    public static Button btnEIRDisConnCom;
    private static Label lblip_2;
    private static Button btnEIRComRefresh;
    private static Button btnEAdbDevRefresh;
    public static Combo comboEDevices;
    private static Label label_18;
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
    private static Composite compECPU;
    private static Composite compEMem;
    private static Button btn_receiver;

    private static String eSelFilePath = "";
    private TabItem tbtmEResrc;
    private static Text txtEUCInterval;
    private Label label_6;
    private Label label_15;
    private TableColumn tblclmnNewColumn;
    private TableColumn tableColumn_4;
    private Button btnEEditVerPoint;
    private Button btnEDelSeled;
    private Button btnESaveScript;
    private TableColumn tblclmnNo_2;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgEIR() {
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
        tbtmExecute.setText(Resources.OPTION_EXCUTE);

        grpExecute = new Group(tabFolder, SWT.NONE);
        tbtmExecute.setControl(grpExecute);
        createEContents(shell, grpExecute);
    }

    /**
     * Create contents of the dialog.
     */
    public void createEContents(Shell shl, Group grpExecute) {
        shell = shl;
        // 脚本导入
        grpEScriptFiles = new Group(grpExecute, SWT.NONE);
        grpEScriptFiles.setText("\u811A\u672C\u5BFC\u5165");
        grpEScriptFiles.setBounds(10, 184, 375, 339);

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
        btnESelect.setText("\u9009\u62E9");

        tblEScriptFiles = new Table(grpEScriptFiles, SWT.BORDER
                | SWT.FULL_SELECTION);
        tblEScriptFiles.setBounds(10, 57, 355, 272);
        tblEScriptFiles.setHeaderVisible(true);
        tblEScriptFiles.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int selIndex = tblEScriptFiles.getSelectionIndex();
                if (selIndex != -1) {
                    TableItem tblItem = tblEScriptFiles.getItem(selIndex);
                    readScriptFromFile(
                            tblItem.getText(Resources.SCRIPTFILE_COL_PATH),
                            true);
                }
            }
        });
        tblEScriptFiles.addListener(SWT.MenuDetect, new Listener() {

            @Override
            public void handleEvent(Event e) {
                // 右键菜单
                Table tblScriptFile = (Table) e.widget;
                Point pt = tblScriptFile.getDisplay().map(null, tblScriptFile,
                        e.x, e.y);
                TableItem item = tblEScriptFiles.getItem(pt);
                showRightMenu(item);

            }
        });

        tblclmnNo_2 = new TableColumn(tblEScriptFiles, SWT.NONE);
        tblclmnNo_2.setWidth(35);
        tblclmnNo_2.setText("No.");

        TableColumn tableColumn = new TableColumn(tblEScriptFiles, SWT.NONE);
        tableColumn.setWidth(299);
        tableColumn.setText("\u540D\u79F0");

        btnERead = new Button(grpEScriptFiles, SWT.NONE);
        btnERead.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                readScriptFiles(txtEScriptPath.getText());
            }
        });
        btnERead.setText("\u8BFB\u53D6");
        btnERead.setBounds(316, 26, 49, 27);
        // 工具设置
        grpESetTool = new Group(grpExecute, SWT.NONE);
        grpESetTool.setText("\u5DE5\u5177\u8BBE\u7F6E");
        grpESetTool.setBounds(10, 20, 375, 158);

        comboEIRCom = new Combo(grpESetTool, SWT.NONE);
        comboEIRCom.setItems(comIR);
        comboEIRCom.setBounds(84, 26, 77, 25);
        comboEIRCom.select(0);
        comboEIRCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComIR(comboEIRCom.getText());
                if (connIRComSuccss && selIRSerialPort != null
                        && !selIRSerialPort.equals(comboEIRCom.getText())) {
                    disConnectCom(spIR, lEIRComStatus, btnEIRConnCom,
                            btnEIRDisConnCom, true);
                    connIRComSuccss = false;
                }
            }
        });

        label_14 = new Label(grpESetTool, SWT.NONE);
        label_14.setText("\u9065\u63A7\u5668\u4E32\u53E3");
        label_14.setBounds(10, 29, 61, 17);

        lEIRComStatus = new Label(grpESetTool, SWT.NONE);
        lEIRComStatus.setText("\u672A\u8FDE\u63A5");
        lEIRComStatus.setBounds(167, 29, 49, 17);

        btnEIRConnCom = new Button(grpESetTool, SWT.NONE);
        btnEIRConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connIRComSuccss = connectTool(spIR, comboEIRCom.getText(),
                        comboEDevCom.getText(), lEIRComStatus, btnEIRConnCom,
                        btnEIRDisConnCom, true);
            }
        });
        btnEIRConnCom.setText("\u8FDE\u63A5");
        btnEIRConnCom.setBounds(276, 24, 41, 27);

        // 区分Android电视和linux电视， Kenneth
        Composite composite_TVType = new Composite(grpESetTool, SWT.NONE);
        composite_TVType.setBounds(10, 130, 400, 25);
        // composite_TVType.setBackground(
        // Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));
        Label TVType = new Label(composite_TVType, SWT.NONE);
        TVType.setText("电视类别");
        TVType.setBounds(10, 5, 67, 17);
        // android tv
        rdoRTVTypeAndroid = new Button(composite_TVType, SWT.RADIO);
        rdoRTVTypeAndroid.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setTVType();
            }
        });
        rdoRTVTypeAndroid.setText("Android TV");
        rdoRTVTypeAndroid.setSelection(true);
        rdoRTVTypeAndroid.setBounds(80, 5, 85, 17);
        // whale tv
        rdoRTVTypeLinux = new Button(composite_TVType, SWT.RADIO);
        rdoRTVTypeLinux.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setTVType();
            }
        });
        rdoRTVTypeLinux.setText("Linux TV");
        rdoRTVTypeLinux.setBounds(170, 5, 90, 17);
//        // SX6 TV
//        rdoRTVTypeSX6 = new Button(composite_TVType, SWT.RADIO);
//        rdoRTVTypeSX6.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                setTVType();
//            }
//        });
//        rdoRTVTypeSX6.setText("SX6 UHD TV");
//        rdoRTVTypeSX6.setBounds(260, 5, 90, 17);

        label_16 = new Label(grpESetTool, SWT.NONE);
        label_16.setText("\u9065\u63A7\u5668\u7F16\u7801");
        label_16.setBounds(10, 160, 73, 17);

        rdoEEncodeNEC = new Button(grpESetTool, SWT.RADIO);
        rdoEEncodeNEC.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoEEncodeNEC.setText("NEC\u7801");
        rdoEEncodeNEC.setSelection(true);
        rdoEEncodeNEC.setBounds(84, 160, 61, 17);

        rdoEEncodeRC5 = new Button(grpESetTool, SWT.RADIO);
        rdoEEncodeRC5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoEEncodeRC5.setText("RC5\u7801");
        rdoEEncodeRC5.setBounds(155, 160, 67, 17);

        rdoEKeycode = new Button(grpESetTool, SWT.RADIO);
        rdoEKeycode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoEKeycode.setText("keycode");
        rdoEKeycode.setBounds(224, 160, 67, 17);
        btnEIRDisConnCom = new Button(grpESetTool, SWT.NONE);
        btnEIRDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spIR, lEIRComStatus, btnEIRConnCom,
                        btnEIRDisConnCom, true);
                connIRComSuccss = false;
            }
        });
        btnEIRDisConnCom.setText("\u65AD\u5F00");
        btnEIRDisConnCom.setEnabled(false);
        btnEIRDisConnCom.setBounds(324, 24, 41, 27);

        lblip_2 = new Label(grpESetTool, SWT.NONE);
        lblip_2.setText("\u7535\u89C6IP");
        lblip_2.setBounds(10, 95, 61, 17);

        btnEIRComRefresh = new Button(grpESetTool, SWT.NONE);
        btnEIRComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spIR);
            }
        });
        btnEIRComRefresh.setText("\u5237\u65B0");
        btnEIRComRefresh.setBounds(228, 24, 41, 27);

        btnEAdbDevRefresh = new Button(grpESetTool, SWT.NONE);
        btnEAdbDevRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshDevice();
            }
        });
        btnEAdbDevRefresh.setText("\u5237\u65B0");
        btnEAdbDevRefresh.setBounds(228, 90, 41, 27);

        comboEDevices = new Combo(grpESetTool, SWT.NONE);
        comboEDevices.setItems(adbOperation.getDevices());
        comboEDevices.setBounds(84, 92, 126, 25);
        comboEDevices.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setDeviceIp(comboEDevices.getText());
            }
        });

        label_18 = new Label(grpESetTool, SWT.NONE);
        label_18.setText("\u7535\u89C6\u4E32\u53E3");
        label_18.setBounds(10, 62, 61, 17);

        comboEDevCom = new Combo(grpESetTool, SWT.NONE);
        comboEDevCom.setItems(comDev);
        comboEDevCom.setBounds(84, 59, 77, 25);
        comboEDevCom.select(0);
        comboEDevCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComDev(comboEDevCom.getText());
                if (connDevComSuccss && selDevSerialPort != null
                        && !selDevSerialPort.equals(comboEDevCom.getText())) {
                    disConnectCom(spDev, lEDevComStatus, btnEDevConnCom,
                            btnEDevDisConnCom, false);
                    connDevComSuccss = false;
                }
            }
        });

        lEDevComStatus = new Label(grpESetTool, SWT.NONE);
        lEDevComStatus.setText("\u672A\u8FDE\u63A5");
        lEDevComStatus.setBounds(167, 62, 49, 17);

        btnEDevComRefresh = new Button(grpESetTool, SWT.NONE);
        btnEDevComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spDev);
            }
        });
        btnEDevComRefresh.setText("\u5237\u65B0");
        btnEDevComRefresh.setBounds(228, 57, 41, 27);

        btnEDevConnCom = new Button(grpESetTool, SWT.NONE);
        btnEDevConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connDevComSuccss = connectTool(spDev, comboEDevCom.getText(),
                        comboEIRCom.getText(), lEDevComStatus, btnEDevConnCom,
                        btnEDevDisConnCom, false);
                if (connDevComSuccss) {
                    spDev.setDevPortParameters();// 设置设备串口参数
                }
            }
        });
        btnEDevConnCom.setText("\u8FDE\u63A5");
        btnEDevConnCom.setBounds(276, 57, 41, 27);

        btnEDevDisConnCom = new Button(grpESetTool, SWT.NONE);
        btnEDevDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spDev, lEDevComStatus, btnEDevConnCom,
                        btnEDevDisConnCom, false);
                connDevComSuccss = false;
            }
        });
        btnEDevDisConnCom.setText("\u65AD\u5F00");
        btnEDevDisConnCom.setEnabled(false);
        btnEDevDisConnCom.setBounds(324, 57, 41, 27);
        // 执行操作
        grpEAction = new Group(grpExecute, SWT.NONE);
        grpEAction.setText("\u6267\u884C\u64CD\u4F5C");
        grpEAction.setBounds(10, 529, 375, 73);

        btnEExec = new Button(grpEAction, SWT.NONE);
        btnEExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doEExecScript();
            }
        });
        btnEExec.setText("\u6267\u884C");
        btnEExec.setBounds(262, 38, 49, 27);

        btnEStopExec = new Button(grpEAction, SWT.NONE);
        btnEStopExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                stopExec(Resources.MODE_EXCUTE);
            }
        });
        btnEStopExec.setText("\u505C\u6B62");
        btnEStopExec.setEnabled(false);
        btnEStopExec.setBounds(316, 38, 49, 27);

        chbEExecLoop = new Button(grpEAction, SWT.CHECK);
        chbEExecLoop.setText("\u5FAA\u73AF\u6267\u884C");
        chbEExecLoop.setBounds(10, 22, 69, 17);

        txtELoop = new Text(grpEAction, SWT.BORDER);
        txtELoop.setText("0");
        txtELoop.setBounds(116, 22, 47, 17);

        Label label_20 = new Label(grpEAction, SWT.NONE);
        label_20.setText("\u6B21");
        label_20.setBounds(169, 22, 33, 17);

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

        label_6 = new Label(grpEAction, SWT.NONE);
        label_6.setText("\u79D2");
        label_6.setBounds(169, 48, 33, 17);

        label_15 = new Label(grpEAction, SWT.NONE);
        label_15.setText("\u7528\u4F8B\u95F4\u7684\u95F4\u9694\u65F6\u95F4");
        label_15.setBounds(10, 48, 100, 17);

        btn_receiver = new Button(grpEAction, SWT.CHECK);
        btn_receiver.setBounds(262, 20, 80, 17);
        btn_receiver.setText("红外接收器");

        tabFolder_3 = new TabFolder(grpExecute, SWT.NONE);
        tabFolder_3.setBounds(391, 20, 382, 552);

        TabItem tbtmEScript = new TabItem(tabFolder_3, SWT.NONE);
        tbtmEScript.setText("\u811A\u672C\u5185\u5BB9");

        grpEScript = new Group(tabFolder_3, SWT.NONE);
        tbtmEScript.setControl(grpEScript);

        tblEScript = new Table(grpEScript, SWT.BORDER | SWT.FULL_SELECTION);
        tblEScript.setHeaderVisible(true);
        tblEScript.setBounds(10, 20, 355, 459);

        final TableEditor editorE = new TableEditor(tblEScript);
        editorE.horizontalAlignment = SWT.LEFT;
        editorE.grabHorizontal = true;
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
                    PgEditStepOrder editstep = new PgEditStepOrder(tblEScript,
                            SWT.None);
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
                                        strVal = String.format("%.1f",
                                                Float.parseFloat(strVal));
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
                } else if (item.getBounds(Resources.SCRIPT_COL_CONTENT)
                        .contains(pt)) {
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

        TableColumn tableColumn_1 = new TableColumn(tblEScript, SWT.NONE);
        tableColumn_1.setWidth(110);
        tableColumn_1.setText("\u952E\u503C");

        TableColumn tableColumn_2 = new TableColumn(tblEScript, SWT.NONE);
        tableColumn_2.setWidth(95);
        tableColumn_2.setText("\u8BF4\u660E");

        TableColumn tableColumn_3 = new TableColumn(tblEScript, SWT.NONE);
        tableColumn_3.setWidth(45);
        tableColumn_3.setText("\u95F4\u9694");

        tableColumn_4 = new TableColumn(tblEScript, SWT.NONE);
        tableColumn_4.setWidth(48);
        tableColumn_4.setText("\u9A8C\u8BC1\u70B9");

        btnEEditVerPoint = new Button(grpEScript, SWT.NONE);
        btnEEditVerPoint.setText("\u7F16\u8F91\u9A8C\u8BC1\u70B9");
        btnEEditVerPoint.setBounds(10, 485, 72, 27);
        btnEEditVerPoint.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updAssertPoints(tblEScript);
            }
        });

        btnEDelSeled = new Button(grpEScript, SWT.NONE);
        btnEDelSeled.setText("\u5220\u9664\u9009\u4E2D\u9879");
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
        btnESaveScript.setText("\u4FDD\u5B58\u811A\u672C");
        btnESaveScript.setBounds(299, 485, 66, 27);
        btnESaveScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int scriptFileIndex = tblEScriptFiles.getSelectionIndex();
                if (scriptFileIndex != -1) {
                    if (tblEScript.getItemCount() < 1) {
                        showMsg(shell, "脚本为空，保存失败。", SWT.ICON_INFORMATION);
                        return;
                    }
                    String savefile = tblEScriptFiles.getItem(scriptFileIndex)
                            .getText(Resources.SCRIPTFILE_COL_PATH);
                    if (savefile != null) {
                        saveScript(shell, tblEScript, savefile,
                                Resources.MODE_EXCUTE);
                    }
                }
            }
        });

        tbtmEResrc = new TabItem(tabFolder_3, SWT.NONE);
        tbtmEResrc.setText("\u8D44\u6E90\u76D1\u63A7");

        Group grpEResrc = new Group(tabFolder_3, SWT.NONE);
        tbtmEResrc.setControl(grpEResrc);

        compECPU = new Composite(grpEResrc, SWT.EMBEDDED);
        compECPU.setBounds(10, 28, 354, 214);

        compEMem = new Composite(grpEResrc, SWT.EMBEDDED);
        compEMem.setBounds(10, 280, 354, 214);
        
    }

    /**
     * 读取执行模式 执行按钮操作
     */
    public static void doEExecScript() {
        if (!toolCheck(comboEDevices)) {
            return;
        }
        if (tblEScriptFiles.getItemCount() == 0) {
            showMsg(shell, "没有可执行的脚本。", SWT.ICON_INFORMATION);
            return;
        }
        // 资源监控
        if (rtcECPU == null) {
            fECPU = SWT_AWT.new_Frame(compECPU);
            rtcECPU = new RealTimeChartECPU(null, 600000D);
            fECPU.add(rtcECPU);
        }
        if (rtcEMem == null) {
            fEMem = SWT_AWT.new_Frame(compEMem);
            rtcEMem = new RealTimeChartEMem(null, 600000D);
            fEMem.add(rtcEMem);
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
            execEScript(tblEScriptFiles, tblEScript, chbEExecLoop, txtELoop,
                    comboEDevices.getText(),
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
        String deviceIp = comboDevices.getText();
        if (connDevComSuccss) {
            logger.debug("使用电视串口记录电视logcat信息。");
        } else if (!"".equals(deviceIp)) {
            logger.debug("使用adb logcat命令记录电视logcat信息。");
        } else {
            String strMsg = "未连接电视的串口并且没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能记录log信息\n2、不能进行log验证";
            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
            if (!gotoExec) {
                return false;
            }
            logger.debug(strMsg);
        }
        if ("".equals(deviceIp)&&!isLinuxTV) {
            String strMsg = "没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能进行资源监控\n2、不能执行脚本STEP中的log验证\n3、不能执行脚本STEP中的sqlite验证\n4、不能执行脚本STEP中的截图验证";
//            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
//            if (!gotoExec) {
//                return false;
//            }
            logger.debug(strMsg);
        }
        testRstTimePath = testRstsPath
                + utils.getCurrTime(Resources.FORMAT_TIME_PATH)
                + File.separator;
        logFilePath = testRstTimePath + "logcat" + File.separator + "log_"
                + utils.getCurrTime(Resources.FORMAT_TIME_PATH) + ".log";
        screenshotPath = testRstTimePath + File.separator + "screenshot"
                + File.separator;
        return true;
    }

    /**
     * 连接工具
     */
    public static boolean connectTool(DSerialPort sp, String serialPort,
            String serialPortO, Label lComStatus, Button btnConnCom,
            Button btnDisConnCom, boolean isIR) {
        boolean connRst = true;
        try {
            if (serialPort == null || "".equals(serialPort)) {
                showMsg(shell, "请选择串口。", SWT.ICON_INFORMATION);
                connRst = false;
            } else if (serialPort.equals(serialPortO)
                    && ((isIR && connDevComSuccss) || (!isIR && connIRComSuccss))) {
                showMsg(shell, "遥控器串口与设备串口重复，请选择其他串口。", SWT.ICON_ERROR);
                connRst = false;
            } else if (!sp.selectPort(serialPort)) {
                showMsg(shell, "串口连接失败。", SWT.ICON_ERROR);
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
            showMsg(shell, "串口连接失败。\n" + e.getMessage(), SWT.ICON_ERROR);
            connRst = false;
        }
        return connRst;
    }

    /*
     * 读取执行模式 -- 执行脚本时，页面控件的可用性状态
     */
    public static void setEExecStatus(boolean isExec) {
        logger.debug("设置页面控件可用性。");
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
        lEIRComStatus.setText(toolInfo.getComIRStatus());
        btnEIRConnCom.setEnabled(toolInfo.isComIRConnEnabled());
        btnEIRDisConnCom.setEnabled(toolInfo.isComIRDisConnEnabled());
        lEDevComStatus.setText(toolInfo.getComDevStatus());
        btnEDevConnCom.setEnabled(toolInfo.isComDevConnEnabled());
        btnEDevDisConnCom.setEnabled(toolInfo.isComDevDisConnEnabled());
        rdoEEncodeNEC.setSelection(toolInfo.isIrEncodeENCSel());
        rdoEEncodeRC5.setSelection(toolInfo.isIrEncodeRC5Sel());
        rdoEKeycode.setSelection(toolInfo.isKeycodeSel());
        comboEIRCom.setText(strIRCom);
        comboEDevCom.setText(strDevCom);
        comboEDevices.setText(strDevice);
        // 电视类型的联动
        rdoRTVTypeAndroid.setSelection(!toolInfo.isLinuxTV());
        rdoRTVTypeLinux.setSelection(toolInfo.isLinuxTV());
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
            comboEIRCom.setItems(coms);
            comboEIRCom.select(0);
            disConnectCom(spIR, lEIRComStatus, btnEIRConnCom, btnEIRDisConnCom,
                    false);
        } else if (sp.getType() == Resources.TYPE_COM_DEV) {
            comDev = coms;
            PgMIR.comboMDevCom.setItems(coms);
            PgRIR.comboRDevCom.setItems(coms);
            comboEDevCom.setItems(coms);
            comboEDevCom.select(0);
            disConnectCom(spDev, lEDevComStatus, btnEDevConnCom,
                    btnEDevDisConnCom, false);
        }
    }

    /*
     * 刷新设备
     */
    private void refreshDevice() {
        String[] devices = adbOperation.getDevices();
        PgMIR.comboMDevices.setItems(devices);
        PgRIR.comboRDevices.setItems(devices);
        comboEDevices.setItems(devices);
        comboEDevices.select(0);
    }

    /*
     * 选择脚本
     */
    private void selectScripts() {
        DirectoryDialog ddSelect = new DirectoryDialog(shell);
        if (!"".equals(txtEScriptPath.getText())) {
            ddSelect.setFilterPath(txtEScriptPath.getText());
        }
        ddSelect.setText("脚本选择");
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
            showMsg(shell, "请选择脚本目录。", SWT.ICON_ERROR);
            return;
        }
        if (!new File(dir).exists()) {
            showMsg(shell, "选择的目录不存在。" + dir, SWT.ICON_ERROR);
            return;
        }
        ArrayList<String> filePaths = utils.getScriptFiles(dir);
        int fileCnt = 0;
        for (String filePath : filePaths) {
            TableItem tableItem = new TableItem(tblEScriptFiles, SWT.NONE);
            tableItem.setText(new String[] { String.valueOf(++fileCnt),
                    filePath });
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
                String msg = "文件不存在。" + filePath;
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
                        bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(
                                eSelFilePath)), "utf-8"));
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
                            for(int i=1;i<scriptItemVal.length;i++){
                                scriptItemVal[i] = splitedArray[i-1];
                            }
                            TableItem tableItem = new TableItem(tblEScript,
                                    SWT.NONE);
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
            showMsg(shell, "请先选择脚本中的Step操作。", SWT.ICON_INFORMATION);
            return;
        }
        TableItem selTblItem = tblScript.getItem(selItemIndex);
        Shell shlStep = new Shell(shell);
        PgVerify pgVerify = new PgVerify(shlStep, SWT.DIALOG_TRIM
                | SWT.APPLICATION_MODAL,
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
     * 设置编码的选择状态
     */
    private void setEncodeStatus() {
        toolInfo.setIrEncodeENCSel(rdoEEncodeNEC.getSelection());
        toolInfo.setIrEncodeRC5Sel(rdoEEncodeRC5.getSelection());
        toolInfo.setKeycodeSel(rdoEKeycode.getSelection());
    }

    /*
     * 设置电视类型的选择状态
     */
    private void setTVType() {
        if (rdoRTVTypeAndroid.getSelection()) {
            isLinuxTV = false;
        } else if (rdoRTVTypeLinux.getSelection()) {
        	isLinuxTV = true;
        }
        toolInfo.setLinuxTV(isLinuxTV);
        logger.debug("isLinuxTV=" + isLinuxTV);
        
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
        menuItem.setText("删除");
        menuItem.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                int selIndex = tblEScriptFiles.getSelectionIndex();
                if (selIndex == -1) {
                    return;
                }
                tblEScriptFiles.remove(selIndex);
                for (int i = 0; i < tblEScriptFiles.getItemCount(); i++) {
                    tblEScriptFiles.getItem(i)
                            .setText(0, String.valueOf(i + 1));
                }
                tblEScriptFiles.setSelection(selIndex);
            }
        });
    }
    
}
