
package com.hisense.autotest.automation;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
import com.hisense.autotest.action.ExecReceiveTh;
import com.hisense.autotest.chart.RealTimeChartMCPU;
import com.hisense.autotest.chart.RealTimeChartMMem;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;
import com.hisense.autotest.util.GetIPOfTV;
import com.hisense.autotest.util.SPortRegQuery;

public class PgMIR extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgMIR.class);

    protected Object result;
    protected Display display;
    public static Shell shell;
    private String sequenceName = "";

    private static TabFolder tabFolder;

    private static Group grpMaunal;
    private static Group grpUserIR;
    private static Group grpMIR5651;
    private static Group grpMIRWhale; // Kenneth
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
    public static Combo comboMIRCom;
    private static Label lMIRComStatus;
    private static Button btnMIRConnCom;
    private static Button btnMIRDisConnCom;
    public static Combo comboMDevCom;
    private static Label lMDevComStatus;
    public static Combo comboMDevices;
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

    private Button btnNumSign;
    private Button btnAst;
    private Button btnZero;
    private Button btnNine;
    private Button btnEight;
    private Button btnSeven;
    private Button btnSix;
    private Button btnFive;
    private Button btnFour;
    private Button btnThree;
    private Button btnTwo;
    private Button btnOne;
    private Button btnReturn;
    private Button btnHome;
    private Button btnMenu;
    private Button btnSet;
    private Button btnVolAdd;
    private Button btnVolSub;
    private Button btnOk;
    private Button btnMute;
    private Button btnPower;
    private Button btn_saveSequence;
    private TableColumn tblclmn_name;
    private TabItem tbtm_custom;
    private TabFolder tabFolder_select;
    private static Button btn_receiver;
    public static Button btn_receiveRecord;
    private Button rdoMTVTypeAndroid;
    private Button rdoMTVTypeLinux;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgMIR() {
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
        tbtmManual.setText(Resources.OPTION_MANUAL);

        grpMaunal = new Group(tabFolder, SWT.NONE);
        tbtmManual.setControl(grpMaunal);
        createMContents(shell, grpMaunal);
    }

    /**
     * Create contents of the dialog.
     */
    public void createMContents(Shell shl, Group grpMaunal) {
        shell = shl;

        grpMSetTool = new Group(grpMaunal, SWT.NONE);
        grpMSetTool.setText("\u5DE5\u5177\u8BBE\u7F6E");
        grpMSetTool.setBounds(10, 20, 375, 188);

        comboMIRCom = new Combo(grpMSetTool, SWT.NONE);
        comboMIRCom.setBounds(84, 26, 77, 25);
        comboMIRCom.setItems(comIR);
        // 自动判断串口类型 kenneth
        String COMText = getComText(spIR, comIR);
        if (COMText != null && !"".equals(COMText)) {
            comboMIRCom.setText(COMText);
        } else {
            comboMIRCom.select(0);
        }
        comboMIRCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComIR(comboMIRCom.getText());
                if (connIRComSuccss && selIRSerialPort != null
                        && !selIRSerialPort.equals(comboMIRCom.getText())) {
                    disConnectCom(spIR, lMIRComStatus, btnMIRConnCom, btnMIRDisConnCom, true);
                    connIRComSuccss = false;
                }
            }
        });

        Label lblIr = new Label(grpMSetTool, SWT.NONE);
        lblIr.setBounds(10, 29, 61, 17);
        lblIr.setText("\u9065\u63A7\u5668\u4E32\u53E3");

        lMIRComStatus = new Label(grpMSetTool, SWT.NONE);
        lMIRComStatus.setBounds(167, 29, 49, 17);
        lMIRComStatus.setText("\u672A\u8FDE\u63A5");

        btnMIRConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMIRConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connIRComSuccss = connectTool(spIR, comboMIRCom.getText(), comboMDevCom.getText(),
                        lMIRComStatus, btnMIRConnCom, btnMIRDisConnCom, true);
            }
        });
        btnMIRConnCom.setBounds(276, 24, 41, 27);
        btnMIRConnCom.setText("\u8FDE\u63A5");

        Label label_2 = new Label(grpMSetTool, SWT.NONE);
        label_2.setText("\u9065\u63A7\u5668\u7F16\u7801");
        label_2.setBounds(10, 130, 73, 17);

        rdoMEncodeNEC = new Button(grpMSetTool, SWT.RADIO);
        rdoMEncodeNEC.setSelection(true);
        rdoMEncodeNEC.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMEncodeNEC.setBounds(84, 130, 61, 17);
        rdoMEncodeNEC.setText("NEC码");

        rdoMEncodeRC5 = new Button(grpMSetTool, SWT.RADIO);
        rdoMEncodeRC5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMEncodeRC5.setText("RC5码");
        rdoMEncodeRC5.setBounds(155, 130, 67, 17);

        rdoMKeycode = new Button(grpMSetTool, SWT.RADIO);
        rdoMKeycode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoMKeycode.setText("keycode");
        rdoMKeycode.setBounds(224, 130, 67, 17);

        btnMIRDisConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMIRDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spIR, lMIRComStatus, btnMIRConnCom, btnMIRDisConnCom, true);
                connIRComSuccss = false;
            }
        });
        btnMIRDisConnCom.setEnabled(false);
        btnMIRDisConnCom.setText("\u65AD\u5F00");
        btnMIRDisConnCom.setBounds(324, 24, 41, 27);

        Label lblip = new Label(grpMSetTool, SWT.NONE);
        lblip.setBounds(10, 95, 61, 17);
        lblip.setText("\u7535\u89C6IP");

        Button btnMIRComRefresh = new Button(grpMSetTool, SWT.NONE);
        btnMIRComRefresh.setText("\u5237\u65B0");
        btnMIRComRefresh.setBounds(228, 24, 41, 27);
        btnMIRComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spIR);
            }
        });

        Button btnMAdbDevRefresh = new Button(grpMSetTool, SWT.NONE);
        btnMAdbDevRefresh.setText("\u5237\u65B0");
        btnMAdbDevRefresh.setBounds(228, 90, 41, 27);
        btnMAdbDevRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        refreshDevice();
                    }
                });
            }
        });

        comboMDevices = new Combo(grpMSetTool, SWT.NONE);
        comboMDevices.setItems(adbOperation.getDevices());
        comboMDevices.setBounds(84, 92, 126, 25);
        comboMDevices.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setDeviceIp(comboMDevices.getText());
            }
        });

        Label label_13 = new Label(grpMSetTool, SWT.NONE);
        label_13.setText("\u7535\u89C6\u4E32\u53E3");
        label_13.setBounds(10, 62, 61, 17);

        comboMDevCom = new Combo(grpMSetTool, SWT.NONE);
        comboMDevCom.setItems(comDev);
        comboMDevCom.setBounds(84, 59, 77, 25);
        // 自动判断串口类型 kenneth
        COMText = getComText(spDev, comDev);
        if (COMText != null && !"".equals(COMText)) {
            comboMDevCom.setText(COMText);
        } else {
            comboMDevCom.select(0);
        }
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
        lMDevComStatus.setText("\u672A\u8FDE\u63A5");
        lMDevComStatus.setBounds(167, 62, 49, 17);

        btnMDevComRefresh = new Button(grpMSetTool, SWT.NONE);
        btnMDevComRefresh.setText("\u5237\u65B0");
        btnMDevComRefresh.setBounds(228, 57, 41, 27);
        btnMDevComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spDev);
            }
        });

        btnMDevConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMDevConnCom.setText("\u8FDE\u63A5");
        btnMDevConnCom.setBounds(276, 57, 41, 27);
        btnMDevConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connDevComSuccss = connectTool(spDev, comboMDevCom.getText(),
                        comboMIRCom.getText(), lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom,
                        false);
                if (connDevComSuccss) {
                    spDev.setDevPortParameters();// 设置设备串口参数
                }
            }
        });

        btnMDevDisConnCom = new Button(grpMSetTool, SWT.NONE);
        btnMDevDisConnCom.setText("\u65AD\u5F00");
        btnMDevDisConnCom.setEnabled(false);
        btnMDevDisConnCom.setBounds(324, 57, 41, 27);
        btnMDevDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spDev, lMDevComStatus, btnMDevConnCom, btnMDevDisConnCom, false);
                connDevComSuccss = false;
            }
        });
        // 脚本录制
        grpRecordScript = new Group(grpMaunal, SWT.NONE);
        grpRecordScript.setText("\u811A\u672C\u5F55\u5236");
        grpRecordScript.setBounds(10, 214, 375, 299);

        Button rdoActInt = new Button(grpRecordScript, SWT.RADIO);
        rdoActInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoActInt.setSelection(true);
        rdoActInt.setBounds(10, 48, 141, 17);
        rdoActInt.setText("\u8BB0\u5F55\u5B9E\u65F6\u6309\u952E\u65F6\u95F4\u95F4\u9694");

        rdoFixedInt = new Button(grpRecordScript, SWT.RADIO);
        rdoFixedInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoFixedInt.setText("\u8BB0\u5F55\u56FA\u5B9A\u6309\u952E\u95F4\u9694(\u79D2)");
        rdoFixedInt.setBounds(163, 48, 137, 17);

        txtFixedInt = new Text(grpRecordScript, SWT.BORDER);
        txtFixedInt.setText("2.0");
        txtFixedInt.setBounds(306, 48, 59, 17);
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
        btnDelSeled.setBounds(149, 266, 72, 27);
        btnDelSeled.setText("\u5220\u9664\u9009\u4E2D\u9879");

        Button btnMSaveScript = new Button(grpRecordScript, SWT.NONE);
        btnMSaveScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (btn_receiver.getSelection() && "停止录制".equals(btn_receiveRecord.getText())) {
                    showMsg(shell, "录制脚本还未停止，请先停止录制。", SWT.ICON_INFORMATION);
                    return;
                }
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
        btnMSaveScript.setBounds(299, 266, 66, 27);
        btnMSaveScript.setText("\u4FDD\u5B58\u811A\u672C");

        Button btnClearScript = new Button(grpRecordScript, SWT.NONE);
        btnClearScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                tblMScript.removeAll();
            }
        });
        btnClearScript.setBounds(227, 266, 66, 27);
        btnClearScript.setText("\u6E05\u7A7A\u811A\u672C");

        tblMScript = new Table(grpRecordScript, SWT.BORDER | SWT.FULL_SELECTION);
        tblMScript.setHeaderVisible(true);
        tblMScript.setBounds(10, 71, 355, 189);

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
        btnMEditVerPoint.setBounds(69, 266, 72, 27);
        btnMEditVerPoint.setText("\u7F16\u8F91\u9A8C\u8BC1\u70B9");

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
        btn_saveSequence.setBounds(10, 266, 59, 27);

        btn_receiver = new Button(grpRecordScript, SWT.CHECK);
        btn_receiver.setBounds(10, 22, 141, 17);
        btn_receiver.setText("使用红外接收器");
        btn_receiver.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (btn_receiver.getSelection()) {
                    btn_receiveRecord.setEnabled(true);
                } else {
                    btn_receiveRecord.setEnabled(false);
                }
            }
        });

        btn_receiveRecord = new Button(grpRecordScript, SWT.NONE);
        btn_receiveRecord.setEnabled(false);
        btn_receiveRecord.setBounds(163, 15, 80, 27);
        btn_receiveRecord.setText("开始录制");
        btn_receiveRecord.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                // 开始、停止录制
                startReceiver();
            }

        });

        grpExecScript = new Group(grpMaunal, SWT.NONE);
        grpExecScript.setText("\u811A\u672C\u6267\u884C");
        grpExecScript.setBounds(10, 525, 375, 84);
        // 脚本执行
        rdoMExecActInt = new Button(grpExecScript, SWT.RADIO);
        rdoMExecActInt
                .setText("\u4F7F\u7528\u811A\u672C\u8BB0\u5F55\u7684\u65F6\u95F4\u95F4\u9694");
        rdoMExecActInt.setSelection(true);
        rdoMExecActInt.setBounds(10, 23, 153, 17);

        rdoMExecFixedInt = new Button(grpExecScript, SWT.RADIO);
        rdoMExecFixedInt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

            }
        });
        rdoMExecFixedInt.setText("\u4F7F\u7528\u56FA\u5B9A\u95F4\u9694(\u79D2)");
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
        chbMExecLoop.setText("\u5FAA\u73AF\u6267\u884C");

        btnMExec = new Button(grpExecScript, SWT.NONE);
        btnMExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doMExecScript();
            }
        });
        btnMExec.setBounds(261, 46, 49, 27);
        btnMExec.setText("\u6267\u884C");

        btnMStopExec = new Button(grpExecScript, SWT.NONE);
        btnMStopExec.setEnabled(false);
        btnMStopExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                stopExec(Resources.MODE_MANUAL);
            }
        });
        btnMStopExec.setText("\u505C\u6B62");
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
        label_3.setText("\u6B21  \u5FAA\u73AF\u95F4\u9694(\u79D2)");

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
                                isLinuxTV = false;
                            }
                        } else {
                            logger.error("获取遥控器选项卡出错，请联系测试开发组！！！！！！！！！！！！！！");
                        }
                        logger.debug("isLinuxTV=" + isLinuxTV);
                    }
                });

        TabItem tbtmIR = new TabItem(tabFolder_select, SWT.NONE);
        tbtmIR.setText("\u7528\u6237\u9065\u63A7\u5668");

        grpUserIR = new Group(tabFolder_select, SWT.NONE);
        tbtmIR.setControl(grpUserIR);

        btnPower = new Button(grpUserIR, SWT.NONE);
        btnPower.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_POWER, btnPower.getText(), false);
            }
        });
        btnPower.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btnPower.setBounds(33, 36, 80, 27);
        btnPower.setText("电源");

        btnMute = new Button(grpUserIR, SWT.NONE);
        btnMute.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_MUTE, btnMute.getText(), false);
            }
        });
        btnMute.setBounds(263, 36, 80, 27);
        btnMute.setText("静音");

        Button btnUp = new Button(grpUserIR, SWT.ARROW | SWT.UP);
        btnUp.setText("上");
        btnUp.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_UP, "上", false);
            }
        });
        btnUp.setBounds(150, 146, 80, 27);

        Button btnLeft = new Button(grpUserIR, SWT.ARROW | SWT.LEFT);
        btnLeft.setText("左");
        btnLeft.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_LEFT, "左", false);
            }
        });
        btnLeft.setBounds(33, 190, 80, 27);

        Button btnDown = new Button(grpUserIR, SWT.ARROW | SWT.DOWN);
        btnDown.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_DOWN, "下", false);
            }
        });
        btnDown.setText("下");
        btnDown.setBounds(150, 233, 80, 27);

        Button btnRight = new Button(grpUserIR, SWT.ARROW | SWT.RIGHT);
        btnRight.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_RIGHT, "右", false);
            }
        });
        btnRight.setBounds(263, 190, 80, 27);
        btnRight.setText("右");

        btnOk = new Button(grpUserIR, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_OK, btnOk.getText(), false);
            }
        });
        btnOk.setBounds(150, 190, 80, 27);
        btnOk.setText("OK");

        btnVolSub = new Button(grpUserIR, SWT.NONE);
        btnVolSub.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_VOL_SUB, btnVolSub.getText(), false);
            }
        });
        btnVolSub.setBounds(33, 97, 80, 27);
        btnVolSub.setText("音量-");

        btnVolAdd = new Button(grpUserIR, SWT.NONE);
        btnVolAdd.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_VOL_ADD, btnVolAdd.getText(), false);
            }
        });
        btnVolAdd.setBounds(263, 97, 80, 27);
        btnVolAdd.setText("音量+");

        btnSet = new Button(grpUserIR, SWT.NONE);
        btnSet.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SET, btnSet.getText(), false);
            }
        });
        btnSet.setBounds(150, 66, 80, 27);
        btnSet.setText("设置");

        btnMenu = new Button(grpUserIR, SWT.NONE);
        btnMenu.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_MENU, btnMenu.getText(), false);
            }
        });
        btnMenu.setBounds(33, 276, 80, 27);
        btnMenu.setText("小聪");

        btnHome = new Button(grpUserIR, SWT.NONE);
        btnHome.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_HOME, btnHome.getText(), false);
            }
        });
        btnHome.setBounds(150, 276, 80, 27);
        btnHome.setText("主页");

        btnReturn = new Button(grpUserIR, SWT.NONE);
        btnReturn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_RETURN, btnReturn.getText(), false);
            }
        });
        btnReturn.setBounds(263, 276, 80, 27);
        btnReturn.setText("返回");

        btnOne = new Button(grpUserIR, SWT.NONE);
        btnOne.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ONE, btnOne.getText(), false);
            }
        });
        btnOne.setBounds(33, 341, 80, 27);
        btnOne.setText("1");

        btnTwo = new Button(grpUserIR, SWT.NONE);
        btnTwo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_TWO, btnTwo.getText(), false);
            }
        });
        btnTwo.setBounds(150, 341, 80, 27);
        btnTwo.setText("2");

        btnThree = new Button(grpUserIR, SWT.NONE);
        btnThree.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_THREE, btnThree.getText(), false);
            }
        });
        btnThree.setBounds(263, 341, 80, 27);
        btnThree.setText("3");

        btnFour = new Button(grpUserIR, SWT.NONE);
        btnFour.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FOUR, btnFour.getText(), false);
            }
        });
        btnFour.setBounds(33, 381, 80, 27);
        btnFour.setText("4");

        btnFive = new Button(grpUserIR, SWT.NONE);
        btnFive.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FIVE, btnFive.getText(), false);
            }
        });
        btnFive.setBounds(150, 381, 80, 27);
        btnFive.setText("5");

        btnSix = new Button(grpUserIR, SWT.NONE);
        btnSix.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SIX, btnSix.getText(), false);
            }
        });
        btnSix.setBounds(263, 381, 80, 27);
        btnSix.setText("6");

        btnSeven = new Button(grpUserIR, SWT.NONE);
        btnSeven.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SEVEN, btnSeven.getText(), false);
            }
        });
        btnSeven.setBounds(33, 423, 80, 27);
        btnSeven.setText("7");

        btnEight = new Button(grpUserIR, SWT.NONE);
        btnEight.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_EIGHT, btnEight.getText(), false);
            }
        });
        btnEight.setBounds(150, 423, 80, 27);
        btnEight.setText("8");

        btnNine = new Button(grpUserIR, SWT.NONE);
        btnNine.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_NINE, btnNine.getText(), false);
            }
        });
        btnNine.setBounds(263, 423, 80, 27);
        btnNine.setText("9");

        btnZero = new Button(grpUserIR, SWT.NONE);
        btnZero.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ZERO, btnZero.getText(), false);
            }
        });
        btnZero.setBounds(150, 463, 80, 27);
        btnZero.setText("0");

        btnAst = new Button(grpUserIR, SWT.NONE);
        btnAst.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_STAR, btnAst.getText(), false);
            }
        });
        btnAst.setBounds(33, 463, 80, 27);
        btnAst.setText("*");

        btnNumSign = new Button(grpUserIR, SWT.NONE);
        btnNumSign.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_POUND, btnNumSign.getText(), false);
            }
        });
        btnNumSign.setBounds(263, 463, 80, 27);
        btnNumSign.setText("#");

        tbtm_custom = new TabItem(tabFolder_select, SWT.V_SCROLL);
        tbtm_custom.setText("自定义遥控器");

        Composite composite = new Composite(tabFolder_select, SWT.NONE);
        tbtm_custom.setControl(composite);
        new PgDefinedButtons(shell, SWT.NONE, composite);

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
        btn_FacSave.setText("\u8282\u80FD");
        btn_FacSave.setBounds(101, 27, 80, 27);
        btn_FacSave.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_SAVE, btn_FacSave.getText(), true);
            }
        });

        btn_FacPattern = new Button(grpMFactory, SWT.NONE);
        btn_FacPattern.setText("\u5C4F\u68C0");
        btn_FacPattern.setBounds(194, 27, 80, 27);
        btn_FacPattern.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_PATTERN, btn_FacPattern.getText(), true);
            }
        });

        btn_FacAging = new Button(grpMFactory, SWT.NONE);
        btn_FacAging.setText("\u8001\u5316");
        btn_FacAging.setBounds(285, 27, 80, 27);
        btn_FacAging.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_AGING, btn_FacAging.getText(), true);
            }
        });

        final Button btnEditKeyCode = new Button(grpMSetTool, SWT.NONE);
        btnEditKeyCode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                pgEditKeyCode.open();

            }
        });
        btnEditKeyCode.setBounds(276, 90, 80, 27);
        btnEditKeyCode.setText("编辑键值");

        // 区分Android电视和linux电视， Kenneth （linux电视串口记录日志不需要logcat）
        Composite composite_TVType = new Composite(grpMSetTool, SWT.NONE);
        composite_TVType.setBounds(10, 153, 355, 25);

        Label TVType = new Label(composite_TVType, SWT.NONE);
        TVType.setBounds(5, 5, 55, 15);
        TVType.setText("电视类别");

        rdoMTVTypeAndroid = new Button(composite_TVType, SWT.RADIO);
        rdoMTVTypeAndroid.setText("Android TV");
        rdoMTVTypeAndroid.setSelection(true);
        rdoMTVTypeAndroid.setBounds(70, 5, 85, 17);
        rdoMTVTypeAndroid.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setTVType();
            }
        });

        rdoMTVTypeLinux = new Button(composite_TVType, SWT.RADIO);
        rdoMTVTypeLinux.setText("Linux TV");
        rdoMTVTypeLinux.setBounds(180, 5, 90, 17);
        rdoMTVTypeLinux.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setTVType();
            }
        });
        // 区分Android电视和linux电视 END

        btn_FacBalance = new Button(grpMFactory, SWT.NONE);
        btn_FacBalance.setText("\u5E73\u8861");
        btn_FacBalance.setBounds(10, 60, 80, 27);
        btn_FacBalance.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAC_BALANCE, btn_FacBalance.getText(), true);
            }
        });

        btn_FacAdc = new Button(grpMFactory, SWT.NONE);
        btn_FacAdc.setText("\u4F18\u5316");
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
        tbtmIR5651.setText("5651\u9065\u63A7\u5668");

        grpMIR5651 = new Group(tabFolder_select, SWT.NONE);
        tbtmIR5651.setControl(grpMIR5651);

        btn_power = new Button(grpMIR5651, SWT.NONE);
        btn_power.setBounds(10, 15, 80, 27);
        btn_power.setText("电源");
        btn_power.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btn_power.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_POWER, btn_power.getText(), false);
            }
        });

        btn_source = new Button(grpMIR5651, SWT.NONE);
        btn_source.setText("SOURCE");
        btn_source.setBounds(284, 15, 80, 27);
        btn_source.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SOURCE, btn_source.getText(), false);
            }
        });

        btn_one = new Button(grpMIR5651, SWT.NONE);
        btn_one.setText("1");
        btn_one.setBounds(10, 48, 80, 27);
        btn_one.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ONE, btn_one.getText(), false);
            }
        });

        btn_two = new Button(grpMIR5651, SWT.NONE);
        btn_two.setText("2");
        btn_two.setBounds(101, 48, 80, 27);
        btn_two.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_TWO, btn_two.getText(), false);
            }
        });

        btn_three = new Button(grpMIR5651, SWT.NONE);
        btn_three.setText("3");
        btn_three.setBounds(194, 48, 80, 27);
        btn_three.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_THREE, btn_three.getText(), false);
            }
        });

        btn_four = new Button(grpMIR5651, SWT.NONE);
        btn_four.setText("4");
        btn_four.setBounds(284, 48, 80, 27);
        btn_four.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FOUR, btn_four.getText(), false);
            }
        });

        btn_five = new Button(grpMIR5651, SWT.NONE);
        btn_five.setText("5");
        btn_five.setBounds(10, 78, 80, 27);
        btn_five.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FIVE, btn_five.getText(), false);
            }
        });

        btn_six = new Button(grpMIR5651, SWT.NONE);
        btn_six.setText("6");
        btn_six.setBounds(101, 78, 80, 27);
        btn_six.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SIX, btn_six.getText(), false);
            }
        });

        btn_seven = new Button(grpMIR5651, SWT.NONE);
        btn_seven.setText("7");
        btn_seven.setBounds(194, 78, 80, 27);
        btn_seven.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_SEVEN, btn_seven.getText(), false);
            }
        });

        btn_eight = new Button(grpMIR5651, SWT.NONE);
        btn_eight.setText("8");
        btn_eight.setBounds(284, 81, 80, 27);
        btn_eight.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_EIGHT, btn_eight.getText(), false);
            }
        });

        btn_nine = new Button(grpMIR5651, SWT.NONE);
        btn_nine.setText("9");
        btn_nine.setBounds(101, 111, 80, 27);
        btn_nine.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_NINE, btn_nine.getText(), false);
            }
        });

        btn_zero = new Button(grpMIR5651, SWT.NONE);
        btn_zero.setText("0");
        btn_zero.setBounds(194, 111, 80, 27);
        btn_zero.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_ZERO, btn_zero.getText(), false);
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
                recordKeyEnt(Resources.PROPKEY_VOL_ADD, btn_vol_add.getText(), false);
            }
        });

        btn_vol_sub = new Button(grpMIR5651, SWT.NONE);
        btn_vol_sub.setText("音量-");
        btn_vol_sub.setBounds(101, 151, 80, 27);
        btn_vol_sub.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_VOL_SUB, btn_vol_sub.getText(), false);
            }
        });

        btn_ch_add = new Button(grpMIR5651, SWT.NONE);
        btn_ch_add.setText("频道+");
        btn_ch_add.setBounds(194, 151, 80, 27);
        btn_ch_add.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_CH_ADD, btn_ch_add.getText(), false);
            }
        });

        btn_ch_sub = new Button(grpMIR5651, SWT.NONE);
        btn_ch_sub.setText("频道-");
        btn_ch_sub.setBounds(284, 151, 80, 27);
        btn_ch_sub.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_CH_SUB, btn_ch_sub.getText(), false);
            }
        });

        btn_media = new Button(grpMIR5651, SWT.NONE);
        btn_media.setText("MEDIA");
        btn_media.setBounds(10, 190, 80, 27);
        btn_media.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_MEDIA, btn_media.getText(), false);
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
                recordKeyEnt(Resources.PROPKEY_EPG, btn_epg.getText(), false);
            }
        });

        btn_mute = new Button(grpMIR5651, SWT.NONE);
        btn_mute.setText("MUTE");
        btn_mute.setBounds(10, 223, 80, 27);
        btn_mute.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_MUTE, btn_mute.getText(), false);
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
                recordKeyEnt(Resources.PROPKEY_INFO, btn_info.getText(), false);
            }
        });

        btn_menu = new Button(grpMIR5651, SWT.NONE);
        btn_menu.setText("MENU");
        btn_menu.setBounds(10, 256, 80, 27);
        btn_menu.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_MENU, btn_menu.getText(), false);
            }
        });

        btn_fav = new Button(grpMIR5651, SWT.NONE);
        btn_fav.setText("FAV");
        btn_fav.setBounds(284, 256, 80, 27);
        btn_fav.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_FAV, btn_fav.getText(), false);
            }
        });

        btn_return = new Button(grpMIR5651, SWT.NONE);
        btn_return.setText("RETURN");
        btn_return.setBounds(10, 322, 80, 27);
        btn_return.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_RETURN, btn_return.getText(), false);
            }
        });

        btn_exit = new Button(grpMIR5651, SWT.NONE);
        btn_exit.setText("EXIT");
        btn_exit.setBounds(284, 322, 80, 27);
        btn_exit.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_EXIT, btn_exit.getText(), false);
            }
        });

        btn_up = new Button(grpMIR5651, SWT.ARROW | SWT.UP);
        btn_up.setText("上");
        btn_up.setBounds(145, 256, 80, 27);
        btn_up.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_UP, "上", false);
            }
        });

        btn_down = new Button(grpMIR5651, SWT.ARROW | SWT.DOWN);
        btn_down.setText("下");
        btn_down.setBounds(145, 322, 80, 27);
        btn_down.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_DOWN, "下", false);
            }
        });

        btn_left = new Button(grpMIR5651, SWT.ARROW | SWT.LEFT);
        btn_left.setText("左");
        btn_left.setBounds(10, 289, 80, 27);
        btn_left.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_LEFT, "左", false);
            }
        });

        btn_right = new Button(grpMIR5651, SWT.ARROW | SWT.RIGHT);
        btn_right.setText("右");
        btn_right.setBounds(284, 289, 80, 27);
        btn_right.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_RIGHT, "右", false);
            }
        });

        btn_ok = new Button(grpMIR5651, SWT.NONE);
        btn_ok.setText("OK");
        btn_ok.setBounds(145, 289, 80, 27);
        btn_ok.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_OK, btn_ok.getText(), false);
            }
        });

        btn_hi_smart_at = new Button(grpMIR5651, SWT.NONE);
        btn_hi_smart_at.setText("HiSmart@");
        btn_hi_smart_at.setBounds(10, 355, 80, 27);
        btn_hi_smart_at.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_HI_SMART_AT, btn_hi_smart_at.getText(), false);
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
                recordKeyEnt(Resources.PROPKEY_RED_032C, btn_red_032c.getText(), false);
            }
        });

        btn_355c = new Button(grpMIR5651, SWT.NONE);
        btn_355c.setText("GREEN");
        btn_355c.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
        btn_355c.setBounds(170, 355, 55, 27);
        btn_355c.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_355C, btn_355c.getText(), false);
            }
        });

        btn_yellowc = new Button(grpMIR5651, SWT.NONE);
        btn_yellowc.setText("YELLOW");
        btn_yellowc.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
        btn_yellowc.setBounds(241, 355, 55, 27);
        btn_yellowc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_YELLOWC, btn_yellowc.getText(), false);
            }
        });

        btn_hexachrome_cyganc = new Button(grpMIR5651, SWT.NONE);
        btn_hexachrome_cyganc.setText("BLUE");
        btn_hexachrome_cyganc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        btn_hexachrome_cyganc.setBounds(309, 355, 55, 27);
        btn_hexachrome_cyganc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_HEXACHROME_CYGANC, btn_hexachrome_cyganc.getText(),
                        false);
            }
        });

        btn_play = new Button(grpMIR5651, SWT.NONE);
        btn_play.setText("播放");
        btn_play.setBounds(10, 388, 80, 27);
        btn_play.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_PLAY, btn_play.getText(), false);
            }
        });

        btn_pause = new Button(grpMIR5651, SWT.NONE);
        btn_pause.setText("暂停");
        btn_pause.setBounds(101, 388, 80, 27);
        btn_pause.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_PAUSE, btn_pause.getText(), false);
            }
        });

        btn_stop = new Button(grpMIR5651, SWT.NONE);
        btn_stop.setText("停止");
        btn_stop.setBounds(194, 388, 80, 27);
        btn_stop.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt(Resources.PROPKEY_STOP, btn_stop.getText(), false);
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
        // 新增选项卡，Whale TV遥控器Kenneth
        TabItem tbtmIRWhale = new TabItem(tabFolder_select, SWT.NONE);
        tbtmIRWhale.setText("Whale TV遥控器");

        grpMIRWhale = new Group(tabFolder_select, SWT.NONE);
        tbtmIRWhale.setControl(grpMIRWhale);
        // Power
        final Button btn_whale_power = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_power.setBounds(10, 15, 80, 27);
        btn_whale_power.setText("电源");
        btn_whale_power.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btn_whale_power.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_POWER, btn_whale_power.getText(), false);
            }
        });
        // HOME
        final Button btn_whale_home = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_home.setText("HOME");
        btn_whale_home.setBounds(147, 15, 80, 27);
        btn_whale_home.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_HOME, btn_whale_home.getText(), false);
            }
        });
        btn_whale_home.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        // MUTE
        final Button btn_whale_mute = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_mute.setText("MUTE");
        btn_whale_mute.setBounds(284, 15, 80, 27);
        btn_whale_mute.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_MUTE", btn_whale_mute.getText(), false);
            }
        });
        // 1
        final Button btn_whale_1 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_1.setText("1");
        btn_whale_1.setBounds(10, 48, 80, 27);
        btn_whale_1.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_ONE, btn_whale_1.getText(), false);
            }
        });
        // 2
        final Button btn_whale_2 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_2.setText("2");
        btn_whale_2.setBounds(101, 48, 80, 27);
        btn_whale_2.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_TWO, btn_whale_2.getText(), false);
            }
        });
        // 3
        final Button btn_whale_3 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_3.setText("3");
        btn_whale_3.setBounds(194, 48, 80, 27);
        btn_whale_3.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_THREE, btn_whale_3.getText(), false);
            }
        });
        // 4
        final Button btn_whale_4 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_4.setText("4");
        btn_whale_4.setBounds(284, 48, 80, 27);
        btn_whale_4.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_FOUR, btn_whale_4.getText(), false);
            }
        });
        // 5
        final Button btn_whale_5 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_5.setText("5");
        btn_whale_5.setBounds(10, 78, 80, 27);
        btn_whale_5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_FIVE, btn_whale_5.getText(), false);
            }
        });
        // 6
        final Button btn_whale_6 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_6.setText("6");
        btn_whale_6.setBounds(101, 78, 80, 27);
        btn_whale_6.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_SIX, btn_whale_6.getText(), false);
            }
        });
        // 7
        final Button btn_whale_7 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_7.setText("7");
        btn_whale_7.setBounds(194, 78, 80, 27);
        btn_whale_7.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_SEVEN, btn_whale_7.getText(), false);
            }
        });
        // 8
        final Button btn_whale_8 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_8.setText("8");
        btn_whale_8.setBounds(284, 81, 80, 27);
        btn_whale_8.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_EIGHT, btn_whale_8.getText(), false);
            }
        });
        // 9
        final Button btn_whale_9 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_9.setText("9");
        btn_whale_9.setBounds(101, 111, 80, 27);
        btn_whale_9.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_NINE, btn_whale_9.getText(), false);
            }
        });
        // 0
        final Button btn_whale_0 = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_0.setText("0");
        btn_whale_0.setBounds(194, 111, 80, 27);
        btn_whale_0.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_ZERO, btn_whale_0.getText(), false);
            }
        });
        // PRE-CH
        final Button btn_whale_prech = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_prech.setText("PRE-CH");
        btn_whale_prech.setBounds(10, 111, 80, 27);
        btn_whale_prech.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_PRE_CH, btn_whale_prech.getText(), false);
            }
        });
        // INFO
        final Button btn_whale_info = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_info.setText("INFO");
        btn_whale_info.setBounds(284, 114, 80, 27);
        btn_whale_info.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_INFO, btn_whale_info.getText(), false);
            }
        });
        // Volume +
        final Button btn_whale_volumeup = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_volumeup.setText("音量+");
        btn_whale_volumeup.setBounds(10, 151, 80, 27);
        btn_whale_volumeup.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_VOL_ADD, btn_whale_volumeup.getText(),
                        false);
            }
        });
        // volume -
        final Button btn_whale_volumedown = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_volumedown.setText("音量-");
        btn_whale_volumedown.setBounds(101, 151, 80, 27);
        btn_whale_volumedown.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_VOL_SUB, btn_whale_volumedown.getText(),
                        false);
            }
        });
        // channel +
        final Button btn_whale_chup = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_chup.setText("频道+");
        btn_whale_chup.setBounds(194, 151, 80, 27);
        btn_whale_chup.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_CH_UP", btn_whale_chup.getText(), false);
            }
        });
        // channel -
        final Button btn_whale_chdown = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_chdown.setText("频道-");
        btn_whale_chdown.setBounds(284, 151, 80, 27);
        btn_whale_chdown.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_CH_DOWN", btn_whale_chdown.getText(), false);
            }
        });
        // Media
        final Button btn_whale_media = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_media.setText("MEDIA");
        btn_whale_media.setBounds(10, 190, 80, 27);
        btn_whale_media.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_MEDIA, btn_whale_media.getText(), false);
            }
        });
        // 3D
        final Button btn_whale_3d = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_3d.setText("3D");
        btn_whale_3d.setBounds(147, 190, 80, 27);
        btn_whale_3d.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_3D", btn_whale_3d.getText(), false);
            }
        });
        // PIP
        final Button btn_whale_pip = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_pip.setText("PiP");
        btn_whale_pip.setBounds(284, 190, 80, 27);
        btn_whale_pip.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_PIP", btn_whale_pip.getText(), false);
            }
        });
        // EPG
        final Button btn_whale_epg = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_epg.setText("EPG");
        btn_whale_epg.setBounds(10, 223, 80, 27);
        btn_whale_epg.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_EPG", btn_whale_epg.getText(), false);
            }
        });
        // LIVE TV
        final Button btn_whale_livetv = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_livetv.setText("LIVETV");
        btn_whale_livetv.setBounds(147, 223, 80, 27);
        btn_whale_livetv.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_LIVETV", btn_whale_livetv.getText(), false);
            }
        });
        // CH.LIST
        final Button btn_whale_chlist = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_chlist.setText("CH.LIST");
        btn_whale_chlist.setBounds(284, 223, 80, 27);
        btn_whale_chlist.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_CH_LIST", btn_whale_chlist.getText(), false);
            }
        });

        // Menu
        final Button btn_whale_menu = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_menu.setText("MENU");
        btn_whale_menu.setBounds(10, 256, 80, 27);
        btn_whale_menu.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_MUTE, btn_whale_menu.getText(), false);
            }
        });
        // up
        final Button btn_whale_up = new Button(grpMIRWhale, SWT.ARROW | SWT.UP);
        btn_whale_up.setText("上");
        btn_whale_up.setBounds(147, 256, 80, 27);
        btn_whale_up.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_UP, "上", false);
            }
        });

        // Setting
        final Button btn_whale_setting = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_setting.setText("设置");
        btn_whale_setting.setBounds(284, 256, 80, 27);
        btn_whale_setting.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_SETTING", btn_whale_setting.getText(), false);
            }
        });

        // left
        final Button btn_whale_left = new Button(grpMIRWhale, SWT.ARROW | SWT.LEFT);
        btn_whale_left.setText("左");
        btn_whale_left.setBounds(10, 289, 80, 27);
        btn_whale_left.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_LEFT, "左", false);
            }
        });

        // OK
        final Button btn_whale_ok = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_ok.setText("OK");
        btn_whale_ok.setBounds(147, 289, 80, 27);
        btn_whale_ok.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_OK, btn_whale_ok.getText(), false);
            }
        });

        // right
        final Button btn_whale_right = new Button(grpMIRWhale, SWT.ARROW | SWT.RIGHT);
        btn_whale_right.setText("右");
        btn_whale_right.setBounds(284, 289, 80, 27);
        btn_whale_right.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_RIGHT, "右", false);
            }
        });
        // return
        btn_return = new Button(grpMIRWhale, SWT.NONE);
        btn_return.setText("RETURN");
        btn_return.setBounds(10, 322, 80, 27);
        btn_return.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_RETURN, btn_return.getText(), false);
            }
        });
        // down
        btn_down = new Button(grpMIRWhale, SWT.ARROW | SWT.DOWN);
        btn_down.setText("下");
        btn_down.setBounds(147, 322, 80, 27);
        btn_down.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_DOWN, "下", false);
            }
        });
        // exit
        btn_exit = new Button(grpMIRWhale, SWT.NONE);
        btn_exit.setText("EXIT");
        btn_exit.setBounds(284, 322, 80, 27);
        btn_exit.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_EXIT, btn_exit.getText(), false);
            }
        });

        // red
        final Button btn_whale_red = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_red.setText("RED");
        btn_whale_red.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        btn_whale_red.setBounds(10, 355, 80, 27);
        btn_whale_red.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_RED", btn_whale_red.getText(), false);
            }
        });
        // green
        final Button btn_whale_green = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_green.setText("GREEN");
        btn_whale_green.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
        btn_whale_green.setBounds(101, 355, 80, 27);
        btn_whale_green.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_GREEN", btn_whale_green.getText(), false);
            }
        });
        // yellow
        final Button btn_whale_yellow = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_yellow.setText("YELLOW");
        btn_whale_yellow.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
        btn_whale_yellow.setBounds(194, 355, 80, 27);
        btn_whale_yellow.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_YELLOW", btn_whale_yellow.getText(), false);
            }
        });
        // blue
        final Button btn_whale_blue = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_blue.setText("BLUE");
        btn_whale_blue.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        btn_whale_blue.setBounds(284, 355, 80, 27);
        btn_whale_blue.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_BLUE", btn_whale_blue.getText(), false);
            }
        });
        // 播放
        final Button btn_whale_play = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_play.setText("播放");
        btn_whale_play.setBounds(10, 388, 80, 27);
        btn_whale_play.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_PLAY, btn_whale_play.getText(), false);
            }
        });
        // 暂停
        final Button btn_whale_pause = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_pause.setText("暂停");
        btn_whale_pause.setBounds(101, 388, 80, 27);
        btn_whale_pause.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_PAUSE, btn_whale_pause.getText(), false);
            }
        });
        // 停止
        final Button btn_whale_stop = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_stop.setText("停止");
        btn_whale_stop.setBounds(194, 388, 80, 27);
        btn_whale_stop.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_STOP, btn_whale_stop.getText(), false);
            }
        });
        // PVR
        final Button btn_whale_pvr = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_pvr.setText("PVR");
        btn_whale_pvr.setBounds(284, 388, 80, 27);
        btn_whale_pvr.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_PVR, btn_whale_pvr.getText(), false);
            }
        });
        // 开始
        final Button btn_whale_start = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_start.setText("LAST");
        btn_whale_start.setBounds(10, 420, 80, 27);
        btn_whale_start.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_LAST", btn_whale_start.getText(), false);
            }
        });
        // 快退
        final Button btn_whale_FAST_BACKWARD = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_FAST_BACKWARD.setText("快退");
        btn_whale_FAST_BACKWARD.setBounds(101, 420, 80, 27);
        btn_whale_FAST_BACKWARD.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_FAST_BACKWARD", btn_whale_FAST_BACKWARD.getText(), false);
            }
        });
        // 快进
        final Button btn_whale_fast_forward = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_fast_forward.setText("快进");
        btn_whale_fast_forward.setBounds(194, 421, 80, 27);
        btn_whale_fast_forward.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_FAST_FORWARD,
                        btn_whale_fast_forward.getText(), false);
            }
        });

        // 末尾
        final Button btn_whale_ending = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_ending.setText("NEXT");
        btn_whale_ending.setBounds(284, 421, 80, 27);
        btn_whale_ending.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_NEXT", btn_whale_ending.getText(), false);
            }
        });
        // TEXT
        final Button btn_whale_text = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_text.setText("TEXT");
        btn_whale_text.setBounds(10, 453, 80, 27);
        btn_whale_text.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_TEXT, btn_whale_text.getText(), false);
            }
        });
        // STILL
        final Button btn_whale_still = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_still.setText("STILL");
        btn_whale_still.setBounds(101, 453, 80, 27);
        btn_whale_still.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_STILL, btn_whale_still.getText(), false);
            }
        });
        // SIZE
        final Button btn_whale_size = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_size.setText("SIZE");
        btn_whale_size.setBounds(194, 453, 80, 27);
        btn_whale_size.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_SIZE, btn_whale_size.getText(), false);
            }
        });
        // T.SHIFT
        final Button btn_whale_tshift = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_tshift.setText("T.SHIFT");
        btn_whale_tshift.setBounds(284, 453, 80, 27);
        btn_whale_tshift.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_T_SHIFT, btn_whale_tshift.getText(),
                        false);
            }
        });
        // P.MODE
        final Button btn_whale_pmode = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_pmode.setText("P.MODE");
        btn_whale_pmode.setBounds(10, 484, 80, 27);
        btn_whale_pmode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_P_MODE, btn_whale_pmode.getText(), false);
            }
        });
        // S.MODE
        final Button btn_whale_smode = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_smode.setText("S.MODE");
        btn_whale_smode.setBounds(101, 484, 80, 27);
        btn_whale_smode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_S_MODE, btn_whale_smode.getText(), false);
            }
        });
        // LANGUAGE
        final Button btn_whale_lang = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_lang.setText("LANGUAGE");
        btn_whale_lang.setBounds(194, 486, 80, 27);
        btn_whale_lang.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_LANGUAGE, btn_whale_lang.getText(), false);
            }
        });
        // SUBT.
        final Button btn_whale_subt = new Button(grpMIRWhale, SWT.NONE);
        btn_whale_subt.setText("SUBT.");
        btn_whale_subt.setBounds(284, 486, 80, 27);
        btn_whale_subt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                recordKeyEnt("WHALE_" + Resources.PROPKEY_SUBT, btn_whale_subt.getText(), false);
            }
        });
//        SX6 UHD北美项目机型取消，界面上取消遥控器界面
//        // 新增选项卡，SigmaSX6 UHD遥控器Kenneth //SX6 遥控器编码有些问题，暂时在界面去掉
//
//        TabItem tbtmIRSX6 = new TabItem(tabFolder_select, SWT.NONE);
//        tbtmIRSX6.setText("SX6 UHD遥控器");
//
//        grpMIRSX6 = new Group(tabFolder_select, SWT.NONE);
//        tbtmIRSX6.setControl(grpMIRSX6);
//        // Power
//        final Button btn_sx6_power = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_power.setBounds(10, 15, 80, 27);
//        btn_sx6_power.setText("电源");
//        btn_sx6_power.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
//        btn_sx6_power.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_POWER,
//                        btn_sx6_power.getText(), false);
//            }
//        });
//        // NETFLIX
//        final Button btn_sx6_netflix = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_netflix.setText("NETFLIX");
//        btn_sx6_netflix.setBounds(147, 15, 80, 27);
//        btn_sx6_netflix.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_NETFLIX", btn_sx6_netflix.getText(), false);
//            }
//        });
//        btn_sx6_netflix.setBackground(SWTResourceManager
//                .getColor(SWT.COLOR_RED));
//        // INPUT
//        final Button btn_sx6_input = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_input.setText("INPUTE");
//        btn_sx6_input.setBounds(284, 15, 80, 27);
//        btn_sx6_input.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_INPUT", btn_sx6_input.getText(), false);
//            }
//        });
//        // 1
//        final Button btn_sx6_1 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_1.setText("1");
//        btn_sx6_1.setBounds(10, 48, 80, 27);
//        btn_sx6_1.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_ONE,
//                        btn_sx6_1.getText(), false);
//            }
//        });
//        // 2
//        final Button btn_sx6_2 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_2.setText("2");
//        btn_sx6_2.setBounds(101, 48, 80, 27);
//        btn_sx6_2.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_TWO,
//                        btn_sx6_2.getText(), false);
//            }
//        });
//        // 3
//        final Button btn_sx6_3 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_3.setText("3");
//        btn_sx6_3.setBounds(194, 48, 80, 27);
//        btn_sx6_3.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_THREE,
//                        btn_sx6_3.getText(), false);
//            }
//        });
//        // 4
//        final Button btn_sx6_4 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_4.setText("4");
//        btn_sx6_4.setBounds(284, 48, 80, 27);
//        btn_sx6_4.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_FOUR,
//                        btn_sx6_4.getText(), false);
//            }
//        });
//        // 5
//        final Button btn_sx6_5 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_5.setText("5");
//        btn_sx6_5.setBounds(10, 78, 80, 27);
//        btn_sx6_5.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_FIVE,
//                        btn_sx6_5.getText(), false);
//            }
//        });
//        // 6
//        final Button btn_sx6_6 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_6.setText("6");
//        btn_sx6_6.setBounds(101, 78, 80, 27);
//        btn_sx6_6.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_SIX,
//                        btn_sx6_6.getText(), false);
//            }
//        });
//        // 7
//        final Button btn_sx6_7 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_7.setText("7");
//        btn_sx6_7.setBounds(194, 78, 80, 27);
//        btn_sx6_7.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_SEVEN,
//                        btn_sx6_7.getText(), false);
//            }
//        });
//        // 8
//        final Button btn_sx6_8 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_8.setText("8");
//        btn_sx6_8.setBounds(284, 81, 80, 27);
//        btn_sx6_8.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_EIGHT,
//                        btn_sx6_8.getText(), false);
//            }
//        });
//        // 9
//        final Button btn_sx6_9 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_9.setText("9");
//        btn_sx6_9.setBounds(101, 111, 80, 27);
//        btn_sx6_9.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_NINE,
//                        btn_sx6_9.getText(), false);
//            }
//        });
//        // 0
//        final Button btn_sx6_0 = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_0.setText("0");
//        btn_sx6_0.setBounds(194, 111, 80, 27);
//        btn_sx6_0.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_ZERO,
//                        btn_sx6_0.getText(), false);
//            }
//        });
//        // 横线
//        final Button btn_sx6_hline = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_hline.setText("一");
//        btn_sx6_hline.setBounds(10, 111, 80, 27);
//        btn_sx6_hline.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_HorizontalLine", btn_sx6_hline.getText(),
//                        false);
//            }
//        });
//        // LAST
//        final Button btn_sx6_last = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_last.setText("LAST");
//        btn_sx6_last.setBounds(284, 114, 80, 27);
//        btn_sx6_last.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_LAST", btn_sx6_last.getText(), false);
//            }
//        });
//        // Volume +
//        final Button btn_sx6_volumeup = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_volumeup.setText("音量+");
//        btn_sx6_volumeup.setBounds(10, 151, 80, 27);
//        btn_sx6_volumeup.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_VOL_ADD,
//                        btn_sx6_volumeup.getText(), false);
//            }
//        });
//        // volume -
//        final Button btn_sx6_volumedown = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_volumedown.setText("音量-");
//        btn_sx6_volumedown.setBounds(101, 151, 80, 27);
//        btn_sx6_volumedown.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_VOL_SUB,
//                        btn_sx6_volumedown.getText(), false);
//            }
//        });
//        // channel +
//        final Button btn_sx6_chup = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_chup.setText("频道+");
//        btn_sx6_chup.setBounds(194, 151, 80, 27);
//        btn_sx6_chup.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_CH_UP", btn_sx6_chup.getText(), false);
//            }
//        });
//        // channel -
//        final Button btn_sx6_chdown = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_chdown.setText("频道-");
//        btn_sx6_chdown.setBounds(284, 151, 80, 27);
//        btn_sx6_chdown.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_CH_DOWN", btn_sx6_chdown.getText(), false);
//            }
//        });
//        // MUTE
//        final Button btn_sx6_mute = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_mute.setText("MUTE");
//        btn_sx6_mute.setBounds(10, 190, 80, 27);
//        btn_sx6_mute.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_MUTE", btn_sx6_mute.getText(), false);
//            }
//        });
//        // MEDIA
//        final Button btn_sx6_MEDIA = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_MEDIA.setText("MEDIA");
//        btn_sx6_MEDIA.setBounds(147, 190, 80, 27);
//        btn_sx6_MEDIA.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_MEDIA", btn_sx6_MEDIA.getText(), false);
//            }
//        });
//        // TV
//        final Button btn_sx6_TV = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_TV.setText("TV");
//        btn_sx6_TV.setBounds(284, 190, 80, 27);
//        btn_sx6_TV.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_TV", btn_sx6_TV.getText(), false);
//            }
//        });
//        // VUDU
//        final Button btn_sx6_VUDU = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_VUDU.setText("VUDU");
//        btn_sx6_VUDU.setBounds(10, 223, 80, 27);
//        btn_sx6_VUDU.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_VUDU", btn_sx6_VUDU.getText(), false);
//            }
//        });
//        // AMAZON
//        final Button btn_sx6_amazon = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_amazon.setText("AMAZON");
//        btn_sx6_amazon.setBounds(147, 223, 80, 27);
//        btn_sx6_amazon.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_AMAZON", btn_sx6_amazon.getText(), false);
//            }
//        });
//        // YOUTUBE
//        final Button btn_sx6_YOUTUBE = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_YOUTUBE.setText("YOUTUBE");
//        btn_sx6_YOUTUBE.setBounds(284, 223, 80, 27);
//        btn_sx6_YOUTUBE.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_YOUTUBE", btn_sx6_YOUTUBE.getText(), false);
//            }
//        });
//
//        // Menu
//        final Button btn_sx6_menu = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_menu.setText("MENU");
//        btn_sx6_menu.setBounds(10, 256, 80, 27);
//        btn_sx6_menu.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_MENU", btn_sx6_menu.getText(), false);
//            }
//        });
//        // up
//        final Button btn_sx6_up = new Button(grpMIRSX6, SWT.ARROW | SWT.UP);
//        btn_sx6_up.setText("上");
//        btn_sx6_up.setBounds(147, 256, 80, 27);
//        btn_sx6_up.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_UP, "上", false);
//            }
//        });
//
//        // HOME
//        final Button btn_sx6_HOME = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_HOME.setText("HOME");
//        btn_sx6_HOME.setBounds(284, 256, 80, 27);
//        btn_sx6_HOME.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_HOME", btn_sx6_HOME.getText(), false);
//            }
//        });
//
//        // left
//        final Button btn_sx6_left = new Button(grpMIRSX6, SWT.ARROW | SWT.LEFT);
//        btn_sx6_left.setText("左");
//        btn_sx6_left.setBounds(10, 289, 80, 27);
//        btn_sx6_left.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_LEFT, "左", false);
//            }
//        });
//
//        // ENTER
//        final Button btn_sx6_ENTER = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_ENTER.setText("ENTER");
//        btn_sx6_ENTER.setBounds(147, 289, 80, 27);
//        btn_sx6_ENTER.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_ENTER", btn_sx6_ENTER.getText(), false);
//            }
//        });
//
//        // right
//        final Button btn_sx6_right = new Button(grpMIRSX6, SWT.ARROW
//                | SWT.RIGHT);
//        btn_sx6_right.setText("右");
//        btn_sx6_right.setBounds(284, 289, 80, 27);
//        btn_sx6_right.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_RIGHT, "右", false);
//            }
//        });
//        // return
//        btn_return = new Button(grpMIRSX6, SWT.NONE);
//        btn_return.setText("RETURN");
//        btn_return.setBounds(10, 322, 80, 27);
//        btn_return.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_RETURN,
//                        btn_return.getText(), false);
//            }
//        });
//        // down
//        btn_down = new Button(grpMIRSX6, SWT.ARROW | SWT.DOWN);
//        btn_down.setText("下");
//        btn_down.setBounds(147, 322, 80, 27);
//        btn_down.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_DOWN, "下", false);
//            }
//        });
//        // exit
//        btn_exit = new Button(grpMIRSX6, SWT.NONE);
//        btn_exit.setText("EXIT");
//        btn_exit.setBounds(284, 322, 80, 27);
//        btn_exit.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_EXIT,
//                        btn_exit.getText(), false);
//            }
//        });
//
//        // red
//        final Button btn_sx6_red = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_red.setText("RED");
//        btn_sx6_red.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
//        btn_sx6_red.setBounds(10, 355, 80, 27);
//        btn_sx6_red.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_RED", btn_sx6_red.getText(), false);
//            }
//        });
//        // green
//        final Button btn_sx6_green = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_green.setText("GREEN");
//        btn_sx6_green.setBackground(SWTResourceManager
//                .getColor(SWT.COLOR_GREEN));
//        btn_sx6_green.setBounds(101, 355, 80, 27);
//        btn_sx6_green.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_GREEN", btn_sx6_green.getText(), false);
//            }
//        });
//        // yellow
//        final Button btn_sx6_yellow = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_yellow.setText("YELLOW");
//        btn_sx6_yellow.setBackground(SWTResourceManager
//                .getColor(SWT.COLOR_YELLOW));
//        btn_sx6_yellow.setBounds(194, 355, 80, 27);
//        btn_sx6_yellow.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_YELLOW", btn_sx6_yellow.getText(), false);
//            }
//        });
//        // blue
//        final Button btn_sx6_blue = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_blue.setText("BLUE");
//        btn_sx6_blue.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
//        btn_sx6_blue.setBounds(284, 355, 80, 27);
//        btn_sx6_blue.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_BLUE", btn_sx6_blue.getText(), false);
//            }
//        });
//        // 快退
//        final Button btn_sx6_FAST_BACKWARD = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_FAST_BACKWARD.setText("快退");
//        btn_sx6_FAST_BACKWARD.setBounds(10, 388, 80, 27);
//        btn_sx6_FAST_BACKWARD.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_FAST_BACKWARD",
//                        btn_sx6_FAST_BACKWARD.getText(), false);
//            }
//        });
//        // 快进
//        final Button btn_sx6_fast_forward = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_fast_forward.setText("快进");
//        btn_sx6_fast_forward.setBounds(101, 388, 80, 27);
//        btn_sx6_fast_forward.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_FAST_FORWARD,
//                        btn_sx6_fast_forward.getText(), false);
//            }
//        });
//
//        // 开始
//        final Button btn_sx6_start = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_start.setText("开始");
//        btn_sx6_start.setBounds(194, 388, 80, 27);
//        btn_sx6_start.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_START,
//                        btn_sx6_start.getText(), false);
//            }
//        });
//
//        // 末尾
//        final Button btn_sx6_ending = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_ending.setText("末尾");
//        btn_sx6_ending.setBounds(284, 388, 80, 27);
//        btn_sx6_ending.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_ENDING,
//                        btn_sx6_ending.getText(), false);
//            }
//        });
//        // 播放
//        final Button btn_sx6_play = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_play.setText("播放");
//        btn_sx6_play.setBounds(10, 420, 80, 27);
//        btn_sx6_play.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_PLAY,
//                        btn_sx6_play.getText(), false);
//            }
//        });
//        // 暂停
//        final Button btn_sx6_pause = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_pause.setText("暂停");
//        btn_sx6_pause.setBounds(101, 420, 80, 27);
//        btn_sx6_pause.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_PAUSE,
//                        btn_sx6_pause.getText(), false);
//            }
//        });
//        // 停止
//        final Button btn_sx6_stop = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_stop.setText("停止");
//        btn_sx6_stop.setBounds(194, 420, 80, 27);
//        btn_sx6_stop.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_" + Resources.PROPKEY_STOP,
//                        btn_sx6_stop.getText(), false);
//            }
//        });
//        // ASPECT
//        final Button btn_sx6_pvr = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_pvr.setText("ASPECT");
//        btn_sx6_pvr.setBounds(284, 420, 80, 27);
//        btn_sx6_pvr.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_ASPECT", btn_sx6_pvr.getText(), false);
//            }
//        });
//        // PICTURE
//        final Button btn_sx6_picture = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_picture.setText("PICTURE");
//        btn_sx6_picture.setBounds(10, 453, 80, 27);
//        btn_sx6_picture.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_PICTURE", btn_sx6_picture.getText(), false);
//            }
//        });
//        // SOUND
//        final Button btn_sx6_sound = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_sound.setText("SOUND");
//        btn_sx6_sound.setBounds(101, 453, 80, 27);
//        btn_sx6_sound.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_SOUND", btn_sx6_sound.getText(), false);
//            }
//        });
//        // MTS/SAP
//        final Button btn_sx6_MTSSAP = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_MTSSAP.setText("MTS/SAP");
//        btn_sx6_MTSSAP.setBounds(194, 453, 80, 27);
//        btn_sx6_MTSSAP.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_MTS/SAP", btn_sx6_MTSSAP.getText(), false);
//            }
//        });
//        // CC
//        final Button btn_sx6_cc = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_cc.setText("CC");
//        btn_sx6_cc.setBounds(284, 453, 80, 27);
//        btn_sx6_cc.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_CC", btn_sx6_cc.getText(), false);
//            }
//        });
//        // SLEEP
//        final Button btn_sx6_sleep = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_sleep.setText("SLEEP");
//        btn_sx6_sleep.setBounds(10, 484, 80, 27);
//        btn_sx6_sleep.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_SLEEP", btn_sx6_sleep.getText(), false);
//            }
//        });
//        // DISPLAY
//        final Button btn_sx6_display = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_display.setText("DISPLAY");
//        btn_sx6_display.setBounds(101, 484, 80, 27);
//        btn_sx6_display.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_DISPLAY", btn_sx6_display.getText(), false);
//            }
//        });
//        // AV
//        final Button btn_sx6_av = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_av.setText("AV");
//        btn_sx6_av.setBounds(194, 486, 80, 27);
//        btn_sx6_av.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_AV", btn_sx6_av.getText(), false);
//            }
//        });
//        // HDMI
//        final Button btn_sx6_hdmi = new Button(grpMIRSX6, SWT.NONE);
//        btn_sx6_hdmi.setText("HDMI");
//        btn_sx6_hdmi.setBounds(284, 486, 80, 27);
//        btn_sx6_hdmi.addSelectionListener(new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                recordKeyEnt("SX6_HDMI", btn_sx6_hdmi.getText(), false);
//            }
//        });

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
     * 开始，停止红外接收器的录制
     */
    private void startReceiver() {
        // TODO Auto-generated method stub
        if ("开始录制".equals(btn_receiveRecord.getText())) {
            // 检查串口是否连接
            if (!connIRComSuccss) {
                String strMsg = "遥控器串口没有连接！";
                logger.error(strMsg);
                showMsg(shell, strMsg, SWT.ICON_INFORMATION);
                return;
            }
            // 检查是否是NEC编码
            if (!rdoMEncodeNEC.getSelection()) {
                showMsg(shell, "只支持NEC编码", SWT.ICON_INFORMATION);
                return;
            }
            // 修改串口参数
//            spIR.setIRReceiverPortParameters();
            spIR.startIRReceive();
            spIR.setStopIRReceiver(false);
            if (rdoFixedInt.getSelection()) {
                spIR.setIntervalTime(txtFixedInt.getText());
            } else {
                spIR.setIntervalTime("");
            }
            receiveControlPage(false);
            // 启动记录的线程
            ExecReceiveTh receiveTh = spIR.getReceiveTh();
            if (receiveTh == null) {
                receiveTh = new ExecReceiveTh(tblMScript, spIR);
                spIR.setReceiveTh(receiveTh);
                receiveTh.start();
            }
        } else if ("停止录制".equals(btn_receiveRecord.getText())) {
            spIR.setStopIRReceiver(true);
            receiveControlPage(true);
        }
    }

    /**
     * 开始录制、停止录制时的界面控制
     */
    private void receiveControlPage(boolean available) {
        if (!available) {
            btn_receiveRecord.setText("停止录制");
        } else {
            btn_receiveRecord.setText("开始录制");
        }
        btn_receiver.setEnabled(available);
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
        if (btn_receiver.getSelection() && "停止录制".equals(btn_receiveRecord.getText())) {
            showMsg(shell, "录制脚本还未停止，请先停止录制。", SWT.ICON_INFORMATION);
            return;
        }
        if (!toolCheck(comboMDevices)) {
            return;
        }
        if (tblMScript.getItemCount() == 0) {
            showMsg(shell, "脚本内容为空，请先录制脚本。", SWT.ICON_INFORMATION);
            return;
        }
        // 资源监控
        if (rtcMCPU == null) {
            fMCPU = SWT_AWT.new_Frame(compMCPU);
            rtcMCPU = new RealTimeChartMCPU(null, 600000D);
            fMCPU.add(rtcMCPU);
        }
        if (rtcMMem == null) {
            fMMem = SWT_AWT.new_Frame(compMMem);
            rtcMMem = new RealTimeChartMMem(null, 600000D);
            fMMem.add(rtcMMem);
        }
        try {
            if (rdoMExecFixedInt.getSelection()) {
                txtMExecFixedInt.setText(String.format("%.1f",
                        Float.parseFloat(txtMExecFixedInt.getText().trim())));
            }
            if ("".equals(txtMUCInterval.getText())) {
                txtMUCInterval.setText("1");
            }
            logger.debug("testRstTimePath = " + testRstTimePath);
            logger.debug("logFilePath = " + logFilePath);
            execMScript(tblMScript, chbMExecLoop, txtMLoop, rdoMExecFixedInt, txtMExecFixedInt,
                    comboMDevices.getText(), Integer.parseInt(txtMUCInterval.getText()));
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            showMsg(shell, e1.getMessage(), SWT.ICON_ERROR);
        }
    }

    /**
     * 执行前，工具设置信息验证
     */
    private static boolean toolCheck(Combo comboDevices) {

        for (TableItem ti : tblMScript.getItems()) {
            if (!ti.getText(Resources.SCRIPT_COL_KEY).contains("-")) {
                if ("".equals(comboDevices.getText())) {
                    if (isLinuxTV) {// Kenneth
                        if (!connDevComSuccss) {
                            String strMsg = "用例中有Whale KeyCode，请先连接电视串口。";
                            logger.error(strMsg);
                            showMsg(shell, strMsg, SWT.ICON_INFORMATION);
                            return false;
                        }
                    } else {
                        String strMsg = "用例中有keycode按键，请先选择电视IP。";
                        logger.error(strMsg);
                        showMsg(shell, strMsg, SWT.ICON_INFORMATION);
                        return false;
                    }
                }
            } else {
                if (!connIRComSuccss) {
                    String strMsg = "用例中有红外按键，请先连接遥控器串口。";
                    logger.error(strMsg);
                    showMsg(shell, strMsg, SWT.ICON_INFORMATION);
                    return false;
                }
            }
        }
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
        if ("".equals(deviceIp) && !isLinuxTV) {// whale等linux电视不验证
            String strMsg = "没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能进行资源监控\n2、不能执行脚本STEP中的log验证\n3、不能执行脚本STEP中的sqlite验证\n4、不能执行脚本STEP中的截图验证";
//            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
//            if (!gotoExec) {
//                return false;
//            }
            logger.debug(strMsg);
        }
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
            int encode = 0;
            if (rdoMEncodeNEC.getSelection()) {
                encode = Resources.ENCODE_NEC;
            } else if (rdoMEncodeRC5.getSelection()) {
                encode = Resources.ENCODE_RC5;
            } else if (rdoMKeycode.getSelection()) {
                encode = Resources.ENCODE_KEYCODE;
            }
            String strMsg = recordKeyEvent(tblMScript, comboMDevices.getText(), strFixedInt,
                    encode, propKey, name);
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
                    grpUserIR.setEnabled(false);
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
                    grpUserIR.setEnabled(true);
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
        toolInfo.setComIR(comboMIRCom.getText());
        toolInfo.setComIRConnEnabled(btnMIRConnCom.isEnabled());
        toolInfo.setComIRDisConnEnabled(btnMIRDisConnCom.isEnabled());
        toolInfo.setComIRStatus(lMDevComStatus.getText());
        toolInfo.setComDev(comboMDevCom.getText());
        toolInfo.setComDevConnEnabled(btnMDevConnCom.isEnabled());
        toolInfo.setComDevDisConnEnabled(btnMDevDisConnCom.isEnabled());
        toolInfo.setComDevStatus(lMIRComStatus.getText());
        toolInfo.setDeviceIp(comboMDevices.getText());
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
        lMIRComStatus.setText(toolInfo.getComIRStatus());
        btnMIRConnCom.setEnabled(toolInfo.isComIRConnEnabled());
        btnMIRDisConnCom.setEnabled(toolInfo.isComIRDisConnEnabled());
        lMDevComStatus.setText(toolInfo.getComDevStatus());
        btnMDevConnCom.setEnabled(toolInfo.isComDevConnEnabled());
        btnMDevDisConnCom.setEnabled(toolInfo.isComDevDisConnEnabled());
        rdoMEncodeNEC.setSelection(toolInfo.isIrEncodeENCSel());
        rdoMEncodeRC5.setSelection(toolInfo.isIrEncodeRC5Sel());
        rdoMKeycode.setSelection(toolInfo.isKeycodeSel());
        comboMIRCom.setText(strIRCom);
        comboMDevCom.setText(strDevCom);
        comboMDevices.setText(strDevice);
        // 电视类型的联动
        rdoMTVTypeAndroid.setSelection(!toolInfo.isLinuxTV());
        rdoMTVTypeLinux.setSelection(toolInfo.isLinuxTV());
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
            comIR = coms;
            comboMIRCom.setItems(coms);
            PgRIR.comboRIRCom.setItems(coms);
            PgEIR.comboEIRCom.setItems(coms);
            if (COMText != null && !"".equals(COMText)) {
                comboMIRCom.setText(COMText);
            } else {
                comboMIRCom.select(0);
            }
            disConnectCom(spIR, lMIRComStatus, btnMIRConnCom, btnMIRDisConnCom, false);
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
        comboMDevices.setItems(devices);
        PgRIR.comboRDevices.setItems(devices);
        PgEIR.comboEDevices.setItems(devices);
        if (devices.length > 0) {
            comboMDevices.select(1);
        } else {
            comboMDevices.select(0);
        }

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
     * 设置电视类型的选择状态
     */
    private void setTVType() {
        if (rdoMTVTypeAndroid.getSelection()) {
            isLinuxTV = false;
        } else if (rdoMTVTypeLinux.getSelection()) {
            isLinuxTV = true;
        }
        toolInfo.setLinuxTV(isLinuxTV);
        logger.debug("isLinuxTV=" + isLinuxTV);
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
            bf = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(scriptPath)), "utf-8"));
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
