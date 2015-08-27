
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.GetIPOfTV;
import com.hisense.autotest.util.SPortRegQuery;

public class PgSendMTKKey extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgSendMTKKey.class);

    protected Object result;
    protected Display display;
    public static Shell shell;
    private String sequenceName = "";

    private static TabFolder tabFolder;

    private static Group grpMaunal;
    private static Group grpMIR5651;
//    private static Group grpMIRSX6; // Kenneth
    private static Group grpMFactory;
    public static Group grpMSetTool;
    private static Group grpRecordScript;
    private static Group grpExecScript;
    private PgEditKeyCode pgEditKeyCode = new PgEditKeyCode();

    public static Text txtFixedInt;
    private static Text txtMExecFixedInt;
    private static Text txtMLoop;
    public static Table tblMScript;
    public static Button rdoFixedInt;
    public static Button rdoMEncodeNEC;
    public static Button rdoMEncodeRC5;
    public static Button rdoMKeycode;
    public static Combo comboMDevCom;
    private static Label lMDevComStatus;
    private static Button btnMDevComRefresh;
    private static Button btnMDevConnCom;
    private static Button btnMDevDisConnCom;
    private static Button btnMStopExec;
    public static Button btnMExec;
    private static Button rdoMExecFixedInt;
    private static Button chbMExecLoop;
    private static Button rdoMExecActInt;
    private static Composite compMCPU;
    private static Composite compMMem;
    // 5651遥控器
    private static Button btn_power;
    private static Button btn_source;
    private static Button btn_one;
    private static Button btn_two;
    private static Button btn_three;
    private static Button btn_four;
    private static Button btn_five;
    private static Button btn_six;
    private static Button btn_seven;
    private static Button btn_eight;
    private static Button btn_nine;
    private static Button btn_zero;
    private static Button btn_pre_ch;
    private static Button btn_ch_list;
    private static Button btn_vol_add;
    private static Button btn_vol_sub;
    private static Button btn_ch_add;
    private static Button btn_ch_sub;
    private static Button btn_media;
    private static Button btn_set;
    private static Button btn_epg;
    private static Button btn_mute;
    private static Button btn_eco;
    private static Button btn_info;
    private static Button btn_menu;
    private static Button btn_fav;
    private static Button btn_return;
    private static Button btn_exit;
    private static Button btn_up;
    private static Button btn_down;
    private static Button btn_left;
    private static Button btn_right;
    private static Button btn_ok;
    private static Button btn_hi_smart_at;
    private static Button btn_red_032c;
    private static Button btn_355c;
    private static Button btn_yellowc;
    private static Button btn_hexachrome_cyganc;
    private static Button btn_play;
    private static Button btn_pause;
    private static Button btn_stop;
    private static Button btn_pvr;
    private static Button btn_fall_back;
    private static Button btn_fast_forward;
    private static Button btn_start;
    private static Button btn_ending;
    private static Button btn_text;
    private static Button btn_still;
    private static Button btn_size;
    private static Button btn_t_shift;
    private static Button btn_p_mode;
    private static Button btn_s_mode;
    private static Button btn_language;
    private static Button btn_subt;
    // 工厂遥控器
    private static Button btn_FacM;
    private static Button btn_FacAtv;
    private static Button btn_FacDtv;
    private static Button btn_FacSave;
    private static Button btn_FacPattern;
    private static Button btn_FacAging;
    private static Button btn_FacBalance;
    private static Button btn_FacAdc;
    private static Button btn_Fac3D;
    private static Button btn_FacPC;

    private static Frame fMCPU;
    private static Frame fMMem;

    private Button btnMEditVerPoint;
    private static String execErrMsg;
    private static Text txtMUCInterval;
    private TableColumn tblclmnNo;
    private TableColumn tblclmAssert;
    private TabItem tabItem;
    private Button btn_FacHdmi1;
    private Button btn_FacHdmi2;
    private Button btn_FacHdmi3;
    private Button btn_FacHdmi4;
    private Button btn_FacHdmi5;
    private Button btn_FacAv1;
    private Button btn_FacAv2;
    private Button btn_FacAv3;
    private Button btn_FacYPBPR;
    private Button btn_FacVga;
    private Button btn_saveSequence;
    private TableColumn tblclmn_name;
    private TabFolder tabFolder_select;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgSendMTKKey() {
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
        shell.setSize(791, 640);
        shell.setText("\u7EA2\u5916\u9065\u63A7\u5668\u6A21\u62DF\u5DE5\u5177");
        shell.setLayout(null);

        tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 784, 612);

        TabItem tbtmManual = new TabItem(tabFolder, SWT.NONE);
        tbtmManual.setText("zxb模式");

        grpMaunal = new Group(tabFolder, SWT.NONE);
        tbtmManual.setControl(grpMaunal);
        createMContents(shell, grpMaunal);
    }

    /**
     * Create contents of the dialog.
     */
    public void createMContents(Shell shl, Group grpMaunal) {
        shell = shl;
        isLinuxTV = true;// zxb mode
        grpMSetTool = new Group(grpMaunal, SWT.NONE);
        grpMSetTool.setText("工具设置");
        grpMSetTool.setBounds(10, 20, 375, 85);
        // 自动判断串口类型 kenneth
//        String COMText = getComText(spIR, comIR);
//        if (COMText != null && !"".equals(COMText)) {
//            comboMIRCom.setText(COMText);
//        } else {
//            comboMIRCom.select(0);
//        }

        Label label_2 = new Label(grpMSetTool, SWT.NONE);
        label_2.setText("遥控器编码");
        label_2.setBounds(10, 55, 73, 17);

        rdoMEncodeNEC = new Button(grpMSetTool, SWT.RADIO);
        rdoMEncodeNEC.setSelection(true);
        rdoMEncodeNEC.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMEncodeNEC.setBounds(84, 55, 61, 17);
        rdoMEncodeNEC.setText("NEC码");

        rdoMEncodeRC5 = new Button(grpMSetTool, SWT.RADIO);
        rdoMEncodeRC5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMEncodeRC5.setText("RC5码");
        rdoMEncodeRC5.setBounds(155, 55, 67, 17);

        rdoMKeycode = new Button(grpMSetTool, SWT.RADIO);
        rdoMKeycode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMKeycode.setText("keycode");
        rdoMKeycode.setBounds(224, 55, 67, 17);

        Label label_13 = new Label(grpMSetTool, SWT.NONE);
        label_13.setText("电视串口");
        label_13.setBounds(10, 26, 61, 17);

        comboMDevCom = new Combo(grpMSetTool, SWT.NONE);
        comboMDevCom.setItems(comDev);
        comboMDevCom.setBounds(84, 23, 77, 25);
        // 自动判断串口类型 kenneth
//        COMText = getComText(spDev, comDev);
//        if (COMText != null && !"".equals(COMText)) {
//            comboMDevCom.setText(COMText);
//        } else {
//            comboMDevCom.select(0);
//        }
        comboMDevCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComDev(comboMDevCom.getText());
                if (connDevComSuccss && selDevSerialPort != null
                        && !selDevSerialPort.equals(comboMDevCom.getText())) {
                    disConnectCom(spDev, lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom, false);
                    connDevComSuccss = false;
                }
            }
        });

        lMDevComStatus = new Label(grpMSetTool, SWT.NONE);
        lMDevComStatus.setText(Resources.TEXT_OFF_CONN);
        lMDevComStatus.setBounds(167, 26, 49, 17);

        btnMDevComRefresh = new Button(grpMSetTool, SWT.NONE);
        btnMDevComRefresh.setText("刷新");// 刷新
        btnMDevComRefresh.setBounds(228, 21, 41, 27);
        btnMDevComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spDev);
            }
        });

        btnMDevConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMDevConnCom.setText("连接");// 连接
        btnMDevConnCom.setBounds(276, 21, 41, 27);
        btnMDevConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connDevComSuccss = connectTool(spDev, comboMDevCom.getText(), "",
                        lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom, false);
                if (connDevComSuccss) {
                    spDev.setDevPortParameters();// 设置设备串口参数
                    startDTV();
                }
            }
        });

        btnMDevDisConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMDevDisConnCom.setText("断开");
        btnMDevDisConnCom.setEnabled(false);
        btnMDevDisConnCom.setBounds(324, 21, 41, 27);
        btnMDevDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spDev, lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom, false);
                connDevComSuccss = false;
            }
        });
        // 脚本录制
        grpRecordScript = new Group(grpMaunal, SWT.NONE);
        grpRecordScript.setText("脚本录制");// 脚本录制
        grpRecordScript.setBounds(10, 111, 375, 276);

        Button rdoActInt = new Button(grpRecordScript, SWT.RADIO);
        rdoActInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoActInt.setSelection(true);
        rdoActInt.setBounds(10, 20, 141, 17);
        rdoActInt.setText("记录实时按键时间间隔");

        rdoFixedInt = new Button(grpRecordScript, SWT.RADIO);
        rdoFixedInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoFixedInt.setText("记录固定按键间隔(秒)");
        rdoFixedInt.setBounds(157, 20, 137, 17);

        txtFixedInt = new Text(grpRecordScript, SWT.BORDER);
        txtFixedInt.setText("2.0");
        txtFixedInt.setBounds(300, 20, 59, 17);
        txtFixedInt.addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {
                // if (e.text.length() > 0) {
                // try {
                // Float.parseFloat(e.text);
                // e.doit = true;
                // } catch (Exception ep) {
                // e.doit = false;
                // }
                // }
            }
        });

        Button btnDelSeled = new Button(grpRecordScript, SWT.NONE);
        btnDelSeled.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int selIndex = tblMScript.getSelectionIndex();
                if (selIndex != -1) {
                    tblMScript.remove(selIndex);
                    for (int i = 0; i < tblMScript.getItemCount(); i++) {
                        tblMScript.getItem(i).setText(0, String.valueOf(i + 1));
                    }
                    tblMScript.setSelection(selIndex);
                }
            }
        });
        btnDelSeled.setBounds(149, 238, 72, 27);
        btnDelSeled.setText("删除选中项");

        Button btnMSaveScript = new Button(grpRecordScript, SWT.NONE);
        btnMSaveScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
//                if (btn_receiver.getSelection() && "停止录制".equals(btn_receiveRecord.getText())) {
//                    showMsg(shell, "录制脚本还未停止，请先停止录制。", SWT.ICON_INFORMATION);
//                    return;
//                }
                if (tblMScript.getItemCount() < 1) {
                    showMsg(shell, "脚本为空，请先输入脚本内容。", SWT.ICON_INFORMATION);
                    return;
                }
                FileDialog fdSave = new FileDialog(shell, SWT.SAVE);
                fdSave.setFilterPath(".");
                fdSave.setText("脚本保存");
                fdSave.setFilterExtensions(new String[] { "*.csv", "*.*" });
                fdSave.setFilterNames(new String[] { "CSV Files(*.csv)", "All Files(*.*)" });
                String savefile = fdSave.open();
                if (savefile != null) {
                    saveScript(shell, tblMScript, new File(savefile).getPath(),
                            Resources.MODE_MANUAL);
                }
            }
        });
        btnMSaveScript.setBounds(299, 238, 66, 27);
        btnMSaveScript.setText("保存脚本");

        Button btnClearScript = new Button(grpRecordScript, SWT.NONE);
        btnClearScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                tblMScript.removeAll();
            }
        });
        btnClearScript.setBounds(227, 238, 66, 27);
        btnClearScript.setText("清空脚本");

        tblMScript = new Table(grpRecordScript, SWT.BORDER | SWT.FULL_SELECTION);
        tblMScript.setHeaderVisible(true);
        tblMScript.setBounds(10, 43, 355, 189);

        // zxb mode
        grpExecScript = new Group(grpMaunal, SWT.NONE);
        grpExecScript.setBounds(10, 393, 375, 84);
        grpExecScript.setText("脚本执行");
        // 脚本执行
        rdoMExecActInt = new Button(grpExecScript, SWT.RADIO);
        rdoMExecActInt.setText("使用脚本记录的时间间隔");
        rdoMExecActInt.setSelection(true);
        rdoMExecActInt.setBounds(10, 23, 153, 17);

        rdoMExecFixedInt = new Button(grpExecScript, SWT.RADIO);
        rdoMExecFixedInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

            }
        });
        rdoMExecFixedInt.setText("使用固定间隔(秒)");
        rdoMExecFixedInt.setBounds(176, 23, 113, 17);

        txtMExecFixedInt = new Text(grpExecScript, SWT.BORDER);
        txtMExecFixedInt.setText("2.0");
        txtMExecFixedInt.setBounds(306, 23, 59, 17);
        txtMExecFixedInt.addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {
                // if (e.text.length() > 0) {
                // try {
                // Float.parseFloat(e.text);
                // e.doit = true;
                // } catch (Exception ep) {
                // e.doit = false;
                // }
                // }
            }
        });

        chbMExecLoop = new Button(grpExecScript, SWT.CHECK);
        chbMExecLoop.setBounds(10, 51, 69, 17);
        chbMExecLoop.setText("循环执行");

        btnMExec = new Button(grpExecScript, SWT.NONE);
        btnMExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doMExecScript();
            }
        });
        btnMExec.setBounds(261, 46, 49, 27);
        btnMExec.setText("执行");

        btnMStopExec = new Button(grpExecScript, SWT.NONE);
        btnMStopExec.setEnabled(false);
        btnMStopExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                stopExec(Resources.MODE_MANUAL);
            }
        });
        btnMStopExec.setText("停止");
        btnMStopExec.setBounds(316, 46, 49, 27);

        txtMLoop = new Text(grpExecScript, SWT.BORDER);
        txtMLoop.setText("0");
        txtMLoop.setBounds(80, 51, 37, 17);
        txtMLoop.addVerifyListener(new VerifyListener() {

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

        Label label_3 = new Label(grpExecScript, SWT.NONE);
        label_3.setBounds(120, 51, 88, 17);
        label_3.setText("次  循环间隔(秒)");

        txtMUCInterval = new Text(grpExecScript, SWT.BORDER);
        txtMUCInterval.setText("1");
        txtMUCInterval.setBounds(215, 51, 37, 17);
        txtMUCInterval.addVerifyListener(new VerifyListener() {

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

        final TableEditor editorM = new TableEditor(tblMScript);
        editorM.horizontalAlignment = SWT.LEFT;
        editorM.grabHorizontal = true;
        tblMScript.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDown(MouseEvent event) {
                Control old = editorM.getEditor();
                if (old != null)
                    old.dispose();

                Point pt = new Point(event.x, event.y);
                final TableItem item = tblMScript.getItem(pt);
                if (item == null) {
                    return;
                }
                if (3 == event.button) {
                    PgEditStepOrder editstep = new PgEditStepOrder(tblMScript, SWT.None);
                    editstep.editStep(tblMScript, pt);
                    return;
                }
                if (item.getBounds(Resources.SCRIPT_COL_INTERVAL).contains(pt)) {
                    // 编辑间隔时间列（第4列，index=3）
                    final int column = Resources.SCRIPT_COL_INTERVAL;
                    final Text text = new Text(tblMScript, SWT.NONE);
                    text.setForeground(item.getForeground());
                    text.setText(item.getText(column));
                    text.setForeground(item.getForeground());
                    text.selectAll();
                    text.setFocus();
                    editorM.minimumWidth = text.getBounds().width;
                    editorM.setEditor(text, item, column);

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
                    final Text text = new Text(tblMScript, SWT.NONE);
                    text.setForeground(item.getForeground());
                    text.setText(item.getText(column));
                    text.setForeground(item.getForeground());
                    text.selectAll();
                    text.setFocus();
                    editorM.minimumWidth = text.getBounds().width;
                    editorM.setEditor(text, item, column);

                    text.addModifyListener(new ModifyListener() {

                        public void modifyText(ModifyEvent event) {
                            item.setText(column, text.getText());
                        }
                    });
                }

            }

        });

        tblclmnNo = new TableColumn(tblMScript, SWT.NONE);
        tblclmnNo.setWidth(35);
        tblclmnNo.setText("No.");

        TableColumn tblclmKey = new TableColumn(tblMScript, SWT.NONE);
        tblclmKey.setWidth(110);
        tblclmKey.setText("\u952E\u503C");

        TableColumn tblclmName = new TableColumn(tblMScript, SWT.NONE);
        tblclmName.setWidth(95);
        tblclmName.setText("\u8BF4\u660E");

        TableColumn tblclmInterval = new TableColumn(tblMScript, SWT.NONE);
        tblclmInterval.setWidth(45);
        tblclmInterval.setText("\u95F4\u9694");

        tblclmAssert = new TableColumn(tblMScript, SWT.NONE);
        tblclmAssert.setWidth(48);
        tblclmAssert.setText("\u9A8C\u8BC1\u70B9");

        tblclmn_name = new TableColumn(tblMScript, SWT.NONE);
        tblclmn_name.setText("按键名");

        btnMEditVerPoint = new Button(grpRecordScript, SWT.NONE);
        btnMEditVerPoint.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updAssertPoints(tblMScript);
            }
        });
        btnMEditVerPoint.setBounds(69, 238, 72, 27);
        btnMEditVerPoint.setText("编辑验证点");

        btn_saveSequence = new Button(grpRecordScript, SWT.NONE);
        btn_saveSequence.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tblMScript.getItemCount() == 0) {
                    showMsg(shell, "表中没有操作序列！", SWT.NONE);
                    return;
                }
                inputSequenceName();
            }
        });
        btn_saveSequence.setText("保存序列");
        btn_saveSequence.setBounds(10, 238, 59, 27);

        tabFolder_select = new TabFolder(grpMaunal, SWT.NONE);
        tabFolder_select.setBounds(391, 20, 382, 552);
        // 对选项卡添加listener Kenneth
        tabFolder_select.addSelectionListener(new SelectionAdapter() {// 使用adapter

            @Override
            public void widgetSelected(final SelectionEvent e) {
                TabItem[] tis = tabFolder_select.getSelection();
                if (null != tis && tis.length == 1) {
                    if (tis[0].getText().equals("Whale TV遥控器")) {
                        isLinuxTV = true;
                    } else {
                        // isLinuxTV = false;
                    }
                    } else {
                    logger.error("获取遥控器选项卡出错，请联系测试开发组！！！！！！！！！！！！！！");
                    }
                logger.debug("isLinuxTV=" + isLinuxTV);
            }
        });

        tabItem = new TabItem(tabFolder_select, SWT.NONE);
        tabItem.setText("\u5DE5\u5382\u9065\u63A7\u5668");

        grpMFactory = new Group(tabFolder_select, SWT.NONE);
        tabItem.setControl(grpMFactory);

        btn_FacM = new Button(grpMFactory, SWT.NONE);
        btn_FacM.setText("M");
        btn_FacM.setBounds(10, 27, 80, 27);
        btn_FacM.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_M, btn_FacM.getText(), true);
            }
        });

        btn_FacSave = new Button(grpMFactory, SWT.NONE);
        btn_FacSave.setText("节能");
        btn_FacSave.setBounds(101, 27, 80, 27);
        btn_FacSave.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_SAVE, btn_FacSave.getText(), true);
            }
        });

        btn_FacPattern = new Button(grpMFactory, SWT.NONE);
        btn_FacPattern.setText("屏检");
        btn_FacPattern.setBounds(194, 27, 80, 27);
        btn_FacPattern.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_PATTERN, btn_FacPattern.getText(), true);
            }
        });

        btn_FacAging = new Button(grpMFactory, SWT.NONE);
        btn_FacAging.setText("老化");
        btn_FacAging.setBounds(285, 27, 80, 27);
        btn_FacAging.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_AGING, btn_FacAging.getText(), true);
            }
        });

//        final Button btnEditKeyCode = new Button(grpMSetTool, SWT.NONE);
//        btnEditKeyCode.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                pgEditKeyCode.open();
//
//            }
//        });
//        btnEditKeyCode.setBounds(276, 90, 80, 27);
//        btnEditKeyCode.setText("编辑键值");
        // 区分Android电视和linux电视 END

        btn_FacBalance = new Button(grpMFactory, SWT.NONE);
        btn_FacBalance.setText("平衡");
        btn_FacBalance.setBounds(10, 60, 80, 27);
        btn_FacBalance.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_BALANCE, btn_FacBalance.getText(), true);
            }
        });

        btn_FacAdc = new Button(grpMFactory, SWT.NONE);
        btn_FacAdc.setText("优化");
        btn_FacAdc.setBounds(100, 60, 80, 27);
        btn_FacAdc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_ADC, btn_FacAdc.getText(), true);
            }
        });

        btn_Fac3D = new Button(grpMFactory, SWT.NONE);
        btn_Fac3D.setText("3D");
        btn_Fac3D.setBounds(194, 60, 80, 27);
        btn_Fac3D.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_3D, btn_Fac3D.getText(), true);
            }
        });

        btn_FacPC = new Button(grpMFactory, SWT.NONE);
        btn_FacPC.setText("PC");
        btn_FacPC.setBounds(285, 60, 80, 27);
        btn_FacPC.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_PC, btn_FacPC.getText(), true);
            }
        });

        btn_FacAtv = new Button(grpMFactory, SWT.NONE);
        btn_FacAtv.setText("ATV");
        btn_FacAtv.setBounds(10, 93, 80, 27);
        btn_FacAtv.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_ATV, btn_FacAtv.getText(), true);
            }
        });

        btn_FacDtv = new Button(grpMFactory, SWT.NONE);
        btn_FacDtv.setText("DTV");
        btn_FacDtv.setBounds(101, 93, 80, 27);
        btn_FacDtv.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_DTV, btn_FacDtv.getText(), true);
            }
        });

        btn_FacYPBPR = new Button(grpMFactory, SWT.NONE);
        btn_FacYPBPR.setText("分量");
        btn_FacYPBPR.setBounds(194, 93, 80, 27);
        btn_FacYPBPR.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_YPBPR, btn_FacYPBPR.getText(), true);
            }
        });

        btn_FacVga = new Button(grpMFactory, SWT.NONE);
        btn_FacVga.setText("VGA");
        btn_FacVga.setBounds(285, 93, 80, 27);
        btn_FacVga.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_VGA, btn_FacVga.getText(), true);
            }
        });

        btn_FacHdmi1 = new Button(grpMFactory, SWT.NONE);
        btn_FacHdmi1.setText("HDMI1");
        btn_FacHdmi1.setBounds(10, 126, 80, 27);
        btn_FacHdmi1.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_HDMI1, btn_FacHdmi1.getText(), true);
            }
        });

        btn_FacHdmi2 = new Button(grpMFactory, SWT.NONE);
        btn_FacHdmi2.setText("HDMI2");
        btn_FacHdmi2.setBounds(101, 126, 80, 27);
        btn_FacHdmi2.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_HDMI2, btn_FacHdmi2.getText(), true);
            }
        });

        btn_FacHdmi3 = new Button(grpMFactory, SWT.NONE);
        btn_FacHdmi3.setText("HDMI3");
        btn_FacHdmi3.setBounds(194, 126, 80, 27);
        btn_FacHdmi3.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_HDMI3, btn_FacHdmi3.getText(), true);
            }
        });

        btn_FacHdmi4 = new Button(grpMFactory, SWT.NONE);
        btn_FacHdmi4.setText("HDMI4");
        btn_FacHdmi4.setBounds(285, 126, 80, 27);
        btn_FacHdmi4.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_HDMI4, btn_FacHdmi4.getText(), true);
            }
        });

        btn_FacHdmi5 = new Button(grpMFactory, SWT.NONE);
        btn_FacHdmi5.setText("HDMI5");
        btn_FacHdmi5.setBounds(285, 159, 80, 27);
        btn_FacHdmi5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_HDMI5, btn_FacHdmi5.getText(), true);
            }
        });

        btn_FacAv1 = new Button(grpMFactory, SWT.NONE);
        btn_FacAv1.setText("AV1");
        btn_FacAv1.setBounds(10, 159, 80, 27);
        btn_FacAv1.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_AV1, btn_FacAv1.getText(), true);
            }
        });

        btn_FacAv2 = new Button(grpMFactory, SWT.NONE);
        btn_FacAv2.setText("AV2");
        btn_FacAv2.setBounds(101, 159, 80, 27);
        btn_FacAv2.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_AV2, btn_FacAv2.getText(), true);
            }
        });

        btn_FacAv3 = new Button(grpMFactory, SWT.NONE);
        btn_FacAv3.setText("AV3");
        btn_FacAv3.setBounds(194, 159, 80, 27);
        btn_FacAv3.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_AV3, btn_FacAv3.getText(), true);
            }
        });
        // 5651遥控器Kenneth
        TabItem tbtmIR5651 = new TabItem(tabFolder_select, SWT.NONE);
        tbtmIR5651.setText("5657遥控器");

        grpMIR5651 = new Group(tabFolder_select, SWT.NONE);
        tbtmIR5651.setControl(grpMIR5651);

        btn_power = new Button(grpMIR5651, SWT.NONE);
        btn_power.setBounds(10, 15, 80, 27);
        btn_power.setText("电源");
        btn_power.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btn_power.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_POWER, btn_power.getText(), false);
            }
        });

        btn_source = new Button(grpMIR5651, SWT.NONE);
        btn_source.setText("SOURCE");
        btn_source.setBounds(284, 15, 80, 27);
        btn_source.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_INPUT_SRC, btn_source.getText(), false);
            }
        });

        btn_one = new Button(grpMIR5651, SWT.NONE);
        btn_one.setText("1");
        btn_one.setBounds(10, 48, 80, 27);
        btn_one.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_1, btn_one.getText(), false);
            }
        });

        btn_two = new Button(grpMIR5651, SWT.NONE);
        btn_two.setText("2");
        btn_two.setBounds(101, 48, 80, 27);
        btn_two.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_2, btn_two.getText(), false);
            }
        });

        btn_three = new Button(grpMIR5651, SWT.NONE);
        btn_three.setText("3");
        btn_three.setBounds(194, 48, 80, 27);
        btn_three.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_3, btn_three.getText(), false);
            }
        });

        btn_four = new Button(grpMIR5651, SWT.NONE);
        btn_four.setText("4");
        btn_four.setBounds(284, 48, 80, 27);
        btn_four.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_4, btn_four.getText(), false);
            }
        });

        btn_five = new Button(grpMIR5651, SWT.NONE);
        btn_five.setText("5");
        btn_five.setBounds(10, 78, 80, 27);
        btn_five.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_5, btn_five.getText(), false);
            }
        });

        btn_six = new Button(grpMIR5651, SWT.NONE);
        btn_six.setText("6");
        btn_six.setBounds(101, 78, 80, 27);
        btn_six.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_6, btn_six.getText(), false);
            }
        });

        btn_seven = new Button(grpMIR5651, SWT.NONE);
        btn_seven.setText("7");
        btn_seven.setBounds(194, 78, 80, 27);
        btn_seven.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_7, btn_seven.getText(), false);
            }
        });

        btn_eight = new Button(grpMIR5651, SWT.NONE);
        btn_eight.setText("8");
        btn_eight.setBounds(284, 81, 80, 27);
        btn_eight.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_8, btn_eight.getText(), false);
            }
        });

        btn_nine = new Button(grpMIR5651, SWT.NONE);
        btn_nine.setText("9");
        btn_nine.setBounds(101, 111, 80, 27);
        btn_nine.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_9, btn_nine.getText(), false);
            }
        });

        btn_zero = new Button(grpMIR5651, SWT.NONE);
        btn_zero.setText("0");
        btn_zero.setBounds(194, 111, 80, 27);
        btn_zero.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_DIGIT_0, btn_zero.getText(), false);
            }
        });

        btn_pre_ch = new Button(grpMIR5651, SWT.NONE);
        btn_pre_ch.setText("PRE-CH");
        btn_pre_ch.setBounds(10, 111, 80, 27);
        btn_pre_ch.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_PRE_CH, btn_pre_ch.getText(), false);
            }
        });

        btn_ch_list = new Button(grpMIR5651, SWT.NONE);
        btn_ch_list.setText("CH.LIST");
        btn_ch_list.setBounds(284, 114, 80, 27);
        btn_ch_list.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_CH_LIST, btn_ch_list.getText(), false);
            }
        });

        btn_vol_add = new Button(grpMIR5651, SWT.NONE);
        btn_vol_add.setText("音量+");
        btn_vol_add.setBounds(10, 151, 80, 27);
        btn_vol_add.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_VOL_UP, btn_vol_add.getText(), false);
            }
        });

        btn_vol_sub = new Button(grpMIR5651, SWT.NONE);
        btn_vol_sub.setText("音量-");
        btn_vol_sub.setBounds(101, 151, 80, 27);
        btn_vol_sub.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_VOL_DOWN, btn_vol_sub.getText(), false);
            }
        });

        btn_ch_add = new Button(grpMIR5651, SWT.NONE);
        btn_ch_add.setText("频道+");
        btn_ch_add.setBounds(194, 151, 80, 27);
        btn_ch_add.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_PRG_UP, btn_ch_add.getText(), false);
            }
        });

        btn_ch_sub = new Button(grpMIR5651, SWT.NONE);
        btn_ch_sub.setText("频道-");
        btn_ch_sub.setBounds(284, 151, 80, 27);
        btn_ch_sub.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_PRG_DOWN, btn_ch_sub.getText(), false);
            }
        });

        btn_media = new Button(grpMIR5651, SWT.NONE);
        btn_media.setText("MEDIA");
        btn_media.setBounds(10, 190, 80, 27);
        btn_media.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_MEDIA, btn_media.getText(), false);
            }
        });

        btn_set = new Button(grpMIR5651, SWT.NONE);
        btn_set.setText("设置");
        btn_set.setBounds(145, 190, 80, 27);
        btn_set.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SET, btn_set.getText(), false);
            }
        });

        btn_epg = new Button(grpMIR5651, SWT.NONE);
        btn_epg.setText("EPG");
        btn_epg.setBounds(284, 190, 80, 27);
        btn_epg.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_EPG, btn_epg.getText(), false);
            }
        });

        btn_mute = new Button(grpMIR5651, SWT.NONE);
        btn_mute.setText("MUTE");
        btn_mute.setBounds(10, 223, 80, 27);
        btn_mute.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_MUTE, btn_mute.getText(), false);
            }
        });

        btn_eco = new Button(grpMIR5651, SWT.NONE);
        btn_eco.setText("ECO");
        btn_eco.setBounds(145, 223, 80, 27);
        btn_eco.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ECO, btn_eco.getText(), false);
            }
        });

        btn_info = new Button(grpMIR5651, SWT.NONE);
        btn_info.setText("INFO");
        btn_info.setBounds(284, 223, 80, 27);
        btn_info.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_PRG_INFO, btn_info.getText(), false);
            }
        });

        btn_menu = new Button(grpMIR5651, SWT.NONE);
        btn_menu.setText("MENU");
        btn_menu.setBounds(10, 256, 80, 27);
        btn_menu.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_MENU, btn_menu.getText(), false);
            }
        });

        btn_fav = new Button(grpMIR5651, SWT.NONE);
        btn_fav.setText("FAV");
        btn_fav.setBounds(284, 256, 80, 27);
        btn_fav.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_FAV_CH, btn_fav.getText(), false);
            }
        });

        btn_return = new Button(grpMIR5651, SWT.NONE);
        btn_return.setText("RETURN");
        btn_return.setBounds(10, 322, 80, 27);
        btn_return.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_RETURN, btn_return.getText(), false);
            }
        });

        btn_exit = new Button(grpMIR5651, SWT.NONE);
        btn_exit.setText("EXIT");
        btn_exit.setBounds(284, 322, 80, 27);
        btn_exit.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_EXIT, btn_exit.getText(), false);
            }
        });

        btn_up = new Button(grpMIR5651, SWT.ARROW | SWT.UP);
        btn_up.setText("上");
        btn_up.setBounds(145, 256, 80, 27);
        btn_up.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_CURSOR_UP, "上", false);
            }
        });

        btn_down = new Button(grpMIR5651, SWT.ARROW | SWT.DOWN);
        btn_down.setText("下");
        btn_down.setBounds(145, 322, 80, 27);
        btn_down.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_CURSOR_DOWN, "下", false);
            }
        });

        btn_left = new Button(grpMIR5651, SWT.ARROW | SWT.LEFT);
        btn_left.setText("左");
        btn_left.setBounds(10, 289, 80, 27);
        btn_left.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_CURSOR_LEFT, "左", false);
            }
        });

        btn_right = new Button(grpMIR5651, SWT.ARROW | SWT.RIGHT);
        btn_right.setText("右");
        btn_right.setBounds(284, 289, 80, 27);
        btn_right.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_CURSOR_RIGHT, "右", false);
            }
        });

        btn_ok = new Button(grpMIR5651, SWT.NONE);
        btn_ok.setText("OK");
        btn_ok.setBounds(145, 289, 80, 27);
        btn_ok.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_SELECT, btn_ok.getText(), false);
            }
        });

        btn_hi_smart_at = new Button(grpMIR5651, SWT.NONE);
        btn_hi_smart_at.setText("Home");
        btn_hi_smart_at.setBounds(10, 355, 80, 27);
        btn_hi_smart_at.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_HOME, btn_hi_smart_at.getText(), false);
            }
        });
        btn_hi_smart_at.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));

        btn_red_032c = new Button(grpMIR5651, SWT.NONE);
        btn_red_032c.setText("RED");
        btn_red_032c.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btn_red_032c.setBounds(101, 355, 55, 27);
        btn_red_032c.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_RED, btn_red_032c.getText(), false);
            }
        });

        btn_355c = new Button(grpMIR5651, SWT.NONE);
        btn_355c.setText("GREEN");
        btn_355c.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
        btn_355c.setBounds(170, 355, 55, 27);
        btn_355c.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_GREEN, btn_355c.getText(), false);
            }
        });

        btn_yellowc = new Button(grpMIR5651, SWT.NONE);
        btn_yellowc.setText("YELLOW");
        btn_yellowc.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
        btn_yellowc.setBounds(241, 355, 55, 27);
        btn_yellowc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_YELLOW, btn_yellowc.getText(), false);
            }
        });

        btn_hexachrome_cyganc = new Button(grpMIR5651, SWT.NONE);
        btn_hexachrome_cyganc.setText("BLUE");
        btn_hexachrome_cyganc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        btn_hexachrome_cyganc.setBounds(309, 355, 55, 27);
        btn_hexachrome_cyganc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_BLUE, btn_hexachrome_cyganc.getText(), false);
            }
        });

        btn_play = new Button(grpMIR5651, SWT.NONE);
        btn_play.setText("播放");
        btn_play.setBounds(10, 388, 80, 27);
        btn_play.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_PLAY, btn_play.getText(), false);
            }
        });

        btn_pause = new Button(grpMIR5651, SWT.NONE);
        btn_pause.setText("暂停");
        btn_pause.setBounds(101, 388, 80, 27);
        btn_pause.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_PAUSE, btn_pause.getText(), false);
            }
        });

        btn_stop = new Button(grpMIR5651, SWT.NONE);
        btn_stop.setText("停止");
        btn_stop.setBounds(194, 388, 80, 27);
        btn_stop.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.MTK_BTN_STOP, btn_stop.getText(), false);
            }
        });

        btn_pvr = new Button(grpMIR5651, SWT.NONE);
        btn_pvr.setText("PVR");
        btn_pvr.setBounds(284, 388, 80, 27);
        btn_pvr.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_PVR, btn_pvr.getText(), false);
            }
        });

        btn_fall_back = new Button(grpMIR5651, SWT.NONE);
        btn_fall_back.setText("快退");
        btn_fall_back.setBounds(10, 420, 80, 27);
        btn_fall_back.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FALL_BACK, btn_fall_back.getText(), false);
            }
        });

        btn_fast_forward = new Button(grpMIR5651, SWT.NONE);
        btn_fast_forward.setText("快进");
        btn_fast_forward.setBounds(101, 421, 80, 27);
        btn_fast_forward.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAST_FORWARD, btn_fast_forward.getText(), false);
            }
        });

        btn_start = new Button(grpMIR5651, SWT.NONE);
        btn_start.setText("开始");
        btn_start.setBounds(194, 420, 80, 27);
        btn_start.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_START, btn_start.getText(), false);
            }
        });

        btn_ending = new Button(grpMIR5651, SWT.NONE);
        btn_ending.setText("末尾");
        btn_ending.setBounds(284, 421, 80, 27);
        btn_ending.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ENDING, btn_ending.getText(), false);
            }
        });

        btn_text = new Button(grpMIR5651, SWT.NONE);
        btn_text.setText("TEXT");
        btn_text.setBounds(10, 453, 80, 27);
        btn_text.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_TEXT, btn_text.getText(), false);
            }
        });

        btn_still = new Button(grpMIR5651, SWT.NONE);
        btn_still.setText("STILL");
        btn_still.setBounds(101, 453, 80, 27);
        btn_still.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_STILL, btn_still.getText(), false);
            }
        });

        btn_size = new Button(grpMIR5651, SWT.NONE);
        btn_size.setText("SIZE");
        btn_size.setBounds(194, 453, 80, 27);
        btn_size.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SIZE, btn_size.getText(), false);
            }
        });

        btn_t_shift = new Button(grpMIR5651, SWT.NONE);
        btn_t_shift.setText("T.SHIFT");
        btn_t_shift.setBounds(284, 453, 80, 27);
        btn_t_shift.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_T_SHIFT, btn_t_shift.getText(), false);
            }
        });

        btn_p_mode = new Button(grpMIR5651, SWT.NONE);
        btn_p_mode.setText("P.MODE");
        btn_p_mode.setBounds(10, 484, 80, 27);
        btn_p_mode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_P_MODE, btn_p_mode.getText(), false);
            }
        });

        btn_s_mode = new Button(grpMIR5651, SWT.NONE);
        btn_s_mode.setText("S.MODE");
        btn_s_mode.setBounds(101, 484, 80, 27);
        btn_s_mode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_S_MODE, btn_s_mode.getText(), false);
            }
        });

        btn_language = new Button(grpMIR5651, SWT.NONE);
        btn_language.setText("LANGUAGE");
        btn_language.setBounds(194, 486, 80, 27);
        btn_language.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_LANGUAGE, btn_language.getText(), false);
            }
        });

        btn_subt = new Button(grpMIR5651, SWT.NONE);
        btn_subt.setText("SUBT.");
        btn_subt.setBounds(284, 486, 80, 27);
        btn_subt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SUBT, btn_subt.getText(), false);
            }
        });

        // 资源监控Kenneth
        TabItem tbtmMResrc = new TabItem(tabFolder_select, SWT.NONE);
        tbtmMResrc.setText("\u8D44\u6E90\u76D1\u63A7");

        Group grpMResrc = new Group(tabFolder_select, SWT.NONE);
        tbtmMResrc.setControl(grpMResrc);

        compMCPU = new Composite(grpMResrc, SWT.NONE | SWT.EMBEDDED);
        compMCPU.setBounds(10, 28, 354, 214);

        compMMem = new Composite(grpMResrc, SWT.NONE | SWT.EMBEDDED);
        compMMem.setBounds(10, 280, 354, 214);

    }

    /**
     * 开始录制、停止录制时的界面控制
     */
    private void receiveControlPage(boolean available) {
//        if (!available) {
//            btn_receiveRecord.setText("停止录制");
//        } else {
//            btn_receiveRecord.setText("开始录制");
//        }
//        btn_receiver.setEnabled(available);
        // 可以点击界面上的遥控器按键
        tabFolder_select.setEnabled(available);
        // 其他TabItem点击后里面内容的控制
        PgIRSimulator.grpRandom.setEnabled(available);
        PgIRSimulator.grpExecute.setEnabled(available);
        PgIRSimulator.grpTrans.setEnabled(available);
        // 本页面上的内容控制
        grpMSetTool.setEnabled(available);
        grpExecScript.setEnabled(available);
    }

    /**
     * 编辑序列名称页面
     */
    protected void inputSequenceName() {
        sequenceName = "";
        final Shell shell_name = new Shell(display);
        shell_name.setSize(330, 210);
        shell_name.setText("输入序列名称");
        shell_name.open();
        Label lbl_title = new Label(shell_name, SWT.NONE);
        lbl_title.setText("序列名称：");
        lbl_title.setBounds(50, 50, 60, 18);
        final Text inputText = new Text(shell_name, SWT.NONE);
        inputText.setBounds(110, 50, 150, 18);
        inputText.setFocus();
        Button btn_ok = new Button(shell_name, SWT.NONE);
        btn_ok.setText("确定");
        btn_ok.setBounds(50, 100, 80, 25);
        Button btn_cancel = new Button(shell_name, SWT.NONE);
        btn_cancel.setBounds(170, 100, 80, 25);
        btn_cancel.setText("取消");
        btn_ok.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                sequenceName = inputText.getText();
                if ("".equals(sequenceName)) {
                    showMsg(shell_name, "序列名称不能为空！", SWT.ICON_INFORMATION);
                    return;
                }
                if (utils.addSequenceXML(tblMScript, sequenceName)) {
                    showMsg(shell_name, "保存序列成功！", SWT.ICON_INFORMATION);
                    PgRIR.selectedSequence.clear();
                    PgRIR.selectedKey.clear();
                    shell_name.dispose();
                } else {
                    showMsg(shell_name, "保存失败！", SWT.ICON_INFORMATION);
                    shell_name.dispose();
                }
            }

        });
        btn_cancel.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                shell_name.dispose();
            }

        });
    }

    // TODO==========================================

    /**
     * 录制回放模式 执行按钮操作
     */
    public static void doMExecScript() {
        if (!toolCheck(null)) {
            return;
        }
        if (tblMScript.getItemCount() == 0) {
            showMsg(shell, "脚本内容为空，请先录制脚本。", SWT.ICON_INFORMATION);
            return;
        }
        // 资源监控
//        if (rtcMCPU == null) {
//            fMCPU = SWT_AWT.new_Frame(compMCPU);
//            rtcMCPU = new RealTimeChartMCPU(null, 600000D);
//            fMCPU.add(rtcMCPU);
//        }
//        if (rtcMMem == null) {
//            fMMem = SWT_AWT.new_Frame(compMMem);
//            rtcMMem = new RealTimeChartMMem(null, 600000D);
//            fMMem.add(rtcMMem);
//        }
        try {
            if (rdoMExecFixedInt.getSelection()) {
                txtMExecFixedInt.setText(
                        String.format("%.1f", Float.parseFloat(txtMExecFixedInt.getText().trim())));
            }
            if ("".equals(txtMUCInterval.getText())) {
                txtMUCInterval.setText("1");
            }
            logger.debug("testRstTimePath = " + testRstTimePath);
            logger.debug("logFilePath = " + logFilePath);
            execMTKSendScript(tblMScript, chbMExecLoop, txtMLoop, rdoMExecFixedInt,
                    txtMExecFixedInt, "", Integer.parseInt(txtMUCInterval.getText()));
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            showMsg(shell, e1.getMessage(), SWT.ICON_ERROR);
        }
    }

    /**
     * 执行前，工具设置信息验证
     */
    private static boolean toolCheck(Combo comboDevices) {

//        for (TableItem ti : tblMScript.getItems()) {
//            if (!ti.getText(Resources.SCRIPT_COL_KEY).contains("-")) {
//                if ("".equals(comboDevices.getText())) {
//                    if (isLinuxTV) {// Kenneth
//                        if (!connDevComSuccss) {
//                            String strMsg = "用例中有Whale KeyCode，请先连接电视串口。";
//                            logger.error(strMsg);
//                            showMsg(shell, strMsg, SWT.ICON_INFORMATION);
//                            return false;
//                        }
//                    } else {
//                        String strMsg = "用例中有keycode按键，请先选择电视IP。";
//                        logger.error(strMsg);
//                        showMsg(shell, strMsg, SWT.ICON_INFORMATION);
//                        return false;
//                    }
//                }
//            } else {
//                if (!connIRComSuccss) {
//                    String strMsg = "用例中有红外按键，请先连接遥控器串口。";
//                    logger.error(strMsg);
//                    showMsg(shell, strMsg, SWT.ICON_INFORMATION);
//                    return false;
//                }
//            }
//        }
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
//        if ("".equals(deviceIp) && !isLinuxTV) {// whale等linux电视不验证
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
     * 记录按键操作
     */
    private void recordKeyEnt(String propKey, String name, boolean isFac) {
        String strFixedInt = "";
        try {
            // 固定时间间隔
            if (rdoFixedInt.getSelection()) {
                strFixedInt = txtFixedInt.getText().trim();
                strFixedInt = String.format("%.1f", Float.parseFloat(strFixedInt));
                txtFixedInt.setText(strFixedInt);
            }
            // zxb mode
            String strMsg = recordAndExcuKeyEvent(tblMScript, "", strFixedInt, 5, propKey, name);
            if (strMsg != null && !"".equals(strMsg)) {
                showMsg(shell, strMsg, SWT.ICON_ERROR);
            }
        } catch (Exception e) {
            showMsg(shell, e.getMessage(), SWT.ICON_ERROR);
        }
    }

    /**
     * 连接工具
     */
    private boolean connectTool(DSerialPort sp, String serialPort, String serialPortO,
            Label lComStatus, Button btnConnCom, Button btnDisConnCom, boolean isIR) {
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

    /**
     * 录制回放模式 -- 执行脚本时，页面控件的可用性状态
     */
    public static void setMExecStatus(boolean isExec, String errMsg) {
        logger.debug("设置页面控件可用性。");
        if (isExec) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // 执行脚本时，[停止]按钮可用，其他控件不可用
                    grpMSetTool.setEnabled(false);
                    grpRecordScript.setEnabled(false);
//                    grpUserIR.setEnabled(false);
                    grpMIR5651.setEnabled(false);
                    grpMFactory.setEnabled(false);

                    rdoMExecActInt.setEnabled(false);
                    rdoMExecFixedInt.setEnabled(false);
                    txtMExecFixedInt.setEnabled(false);
                    chbMExecLoop.setEnabled(false);
                    txtMLoop.setEnabled(false);
                    btnMExec.setEnabled(false);
                    btnMStopExec.setEnabled(true);

                    // 其他模式不可执行，不可更改工具设置信息
                    PgRIR.grpRSetTool.setEnabled(false);
                    PgRIR.btnRExec.setEnabled(false);
                    PgEIR.grpESetTool.setEnabled(false);
                    PgEIR.btnEExec.setEnabled(false);
                }
            });
        } else {
            execErrMsg = errMsg;
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // 停止执行时，[停止]按钮不可用，还原其他控件的状态
//                    grpUserIR.setEnabled(true);
                    grpMSetTool.setEnabled(true);
                    grpRecordScript.setEnabled(true);
                    grpMIR5651.setEnabled(true);
                    grpMFactory.setEnabled(true);

                    rdoMExecActInt.setEnabled(true);
                    rdoMExecFixedInt.setEnabled(true);
                    txtMExecFixedInt.setEnabled(true);
                    chbMExecLoop.setEnabled(true);
                    txtMLoop.setEnabled(true);
                    btnMExec.setEnabled(true);
                    btnMStopExec.setEnabled(false);

                    // 其他模式控件状态还原
                    PgRIR.grpRSetTool.setEnabled(true);
                    PgRIR.btnRExec.setEnabled(true);
                    PgEIR.grpESetTool.setEnabled(true);
                    PgEIR.btnEExec.setEnabled(true);

                    if (PgEIR.showEndMsg) {
                        if (execErrMsg == null || "".equals(execErrMsg)) {
                            showMsg(shell, "脚本执行结束。", SWT.ICON_INFORMATION);
                        } else {
                            showMsg(shell, "用例执行失败。错误信息：" + execErrMsg, SWT.ICON_ERROR);
                        }
                    } else {
                        System.exit(0);
                    }
                }
            });
        }
    }

    /**
     * 工具打开时，记录工具设置的信息
     */
    public void recordToolInfo() {
//        toolInfo.setComIR(comboMIRCom.getText());
//        toolInfo.setComIRConnEnabled(btnMIRConnCom.isEnabled());
//        toolInfo.setComIRDisConnEnabled(btnMIRDisConnCom.isEnabled());
        toolInfo.setComIRStatus(lMDevComStatus.getText());
        toolInfo.setComDev(comboMDevCom.getText());
        toolInfo.setComDevConnEnabled(btnMDevConnCom.isEnabled());
        toolInfo.setComDevDisConnEnabled(btnMDevDisConnCom.isEnabled());
//        toolInfo.setComDevStatus(lMIRComStatus.getText());
//        toolInfo.setDeviceIp(comboMDevices.getText());
        toolInfo.setIrEncodeENCSel(rdoMEncodeNEC.getSelection());
        toolInfo.setIrEncodeRC5Sel(rdoMEncodeRC5.getSelection());
    }

    /**
     * 工具设置信息的连动
     */
    public void controlLinkage() {
        String strIRCom = toolInfo.getComIR();
        String strDevCom = toolInfo.getComDev();
        String strDevice = toolInfo.getDeviceIp();
//        lMIRComStatus.setText(toolInfo.getComIRStatus());
//        btnMIRConnCom.setEnabled(toolInfo.isComIRConnEnabled());
//        btnMIRDisConnCom.setEnabled(toolInfo.isComIRDisConnEnabled());
        lMDevComStatus.setText(toolInfo.getComDevStatus());
        btnMDevConnCom.setEnabled(toolInfo.isComDevConnEnabled());
        btnMDevDisConnCom.setEnabled(toolInfo.isComDevDisConnEnabled());
        rdoMEncodeNEC.setSelection(toolInfo.isIrEncodeENCSel());
        rdoMEncodeRC5.setSelection(toolInfo.isIrEncodeRC5Sel());
        rdoMKeycode.setSelection(toolInfo.isKeycodeSel());
//        comboMIRCom.setText(strIRCom);
        comboMDevCom.setText(strDevCom);
//        comboMDevices.setText(strDevice);
        // 电视类型的联动
//        rdoMTVTypeAndroid.setSelection(!toolInfo.isLinuxTV());
//        rdoMTVTypeLinux.setSelection(toolInfo.isLinuxTV());
    }

    /**
     * 刷新串口
     */
    private void refreshCom(DSerialPort sp) {
        sp.initialDriver();
        String[] coms = sp.listPort();
        // 自动判断串口类型 kenneth
        String COMText = getComText(sp, coms);
        if (sp.getType() == Resources.TYPE_COM_IR) {
//            comIR = coms;
//            comboMIRCom.setItems(coms);
//            PgRIR.comboRIRCom.setItems(coms);
//            PgEIR.comboEIRCom.setItems(coms);
//            if (COMText != null && !"".equals(COMText)) {
//                comboMIRCom.setText(COMText);
//            } else {
//                comboMIRCom.select(0);
//            }
//            disConnectCom(spIR, lMIRComStatus, btnMIRConnCom, btnMIRDisConnCom, false);
        } else if (sp.getType() == Resources.TYPE_COM_DEV) {
            comDev = coms;
            comboMDevCom.setItems(coms);
            PgRIR.comboRDevCom.setItems(coms);
            PgEIR.comboEDevCom.setItems(coms);
            if (COMText != null && !"".equals(COMText)) {
                comboMDevCom.setText(COMText);
            } else {
                comboMDevCom.select(0);
            }
            disConnectCom(spDev, lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom, false);
        }
    }

    /**
     * 刷新设备
     */
    private void refreshDevice() {
        // 在电视串口连接成功的时候尝试自动获取IP并连接
        logger.debug("电视串口是否连接成功:" + connDevComSuccss);
        if (connDevComSuccss && !isLinuxTV) {
            GetIPOfTV getIP = new GetIPOfTV();
            String ipOfTV = getIP.get();
            logger.debug("尝试自动获取ip。只适用Android系统,获取的IP为= " + ipOfTV);
            logger.debug("尝试自动获取ip。只适用Android系统,是否wifi网络= " + getIP.isWifiNetwork());
            if (!"".equals(ipOfTV)) {
                // wifi情况下，可能不在同一个局域网，adb连接会失败
                if (!getIP.isWifiNetwork()) {
                    adbOperation.connDev(ipOfTV);
                }
            }

        }
        String[] devices = adbOperation.getDevices();
//        comboMDevices.setItems(devices);
//        PgRIR.comboRDevices.setItems(devices);
//        PgEIR.comboEDevices.setItems(devices);
//        if (devices.length > 0) {
//            comboMDevices.select(1);
//        } else {
//            comboMDevices.select(0);
//        }

    }

    /**
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

    /**
     * 设置编码的选择状态
     */
    private void setEncodeStatus() {
        toolInfo.setIrEncodeENCSel(rdoMEncodeNEC.getSelection());
        toolInfo.setIrEncodeRC5Sel(rdoMEncodeRC5.getSelection());
        toolInfo.setKeycodeSel(rdoMKeycode.getSelection());
    }

    /*
     * 自动获取串口，电视串口，遥控器串口、继电器串口（目前，遥控器串口、继电器串口无法分辨出来） KEN
     */
    private String getComText(DSerialPort sp, String[] coms) {
        String COMText = "";
        String COMType = "";
        HashMap<String, String> spMap = SPortRegQuery.get();
        if (sp.getType() == Resources.TYPE_COM_IR) {
            for (String tmpCom : coms) {
                COMType = spMap.get(tmpCom);
                if (COMType != null && COMType.contains(SPortRegQuery.COMType_IR)) {
                    COMText = tmpCom;
                    break;
                }
            }
        }
        if (sp.getType() == Resources.TYPE_COM_DEV) {
            for (String tmpCom : coms) {
                COMType = spMap.get(tmpCom);
                if (COMType != null && COMType.contains(SPortRegQuery.COMType_TV)) {
                    COMText = tmpCom;
                    break;
                }
            }
        }
        return COMText;
    }

    /**
     * 显示测试脚本
     * 
     * @param scriptPath
     */
    public static void setScript(String scriptPath) {
        BufferedReader bf = null;
        try {
            tblMScript.removeAll();
            bf = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(scriptPath)), "utf-8"));
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
                TableItem tableItem = new TableItem(tblMScript, SWT.NONE);
                tableItem.setText(scriptItemVal);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception e1) {
            }
        }
    }

}
