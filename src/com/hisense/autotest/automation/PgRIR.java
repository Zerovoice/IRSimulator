
package com.hisense.autotest.automation;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.hisense.autotest.bean.SequenceInfo;
import com.hisense.autotest.chart.RealTimeChartRCPU;
import com.hisense.autotest.chart.RealTimeChartRMem;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.serialport.DSerialPort;

import org.eclipse.swt.widgets.Composite;

public class PgRIR extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgIRSimulator.class);

    protected Object result;
    protected Display display;
    public static Shell shell;

    private static TabFolder tabFolder;
    private static Group grpRScript;
    private static Group grpRRandCond;
    public static Group grpRSetTool;
    private static Group grpRAction;
    private static Group grpRResrc;
    private static TabFolder tabFolder_2;

    private static Text txtRMinInt;
    private static Text txtRMaxInt;
    private static Text txtRActTime;
    private static Text txtRPressCnt;
    private static Table tblRScript;
    private static Text txtRLoop;
    public static Combo comboRIRCom;
    private static Label lRIRComStatus;
    private static Button btnRIRConnCom;
    private static Button btnRIRDisConnCom;
    public static Combo comboRDevCom;
    private static Label lRDevComStatus;
    private static Button btnRDevConnCom;
    private static Button btnRDevDisConnCom;
    public static Combo comboRDevices;
    public static Button rdoREncodeNEC;
    public static Button rdoREncodeRC5;
    private static Button rdoRTVTypeAndroid;
//    private static Button rdoRTVTypeWhale;
    private static Button rdoRTVTypeLinux;
    public static Button rdoRKeycode;
    private static Button rdoRPressCnt;
    private static Combo comboRTimeUnit;
    private static Button rdoRNKeyNP;
    private static Button rdoRNKeyCP;
    public static Button rdoSequence;
    private static Button chbRExecLoop;
    private static Button btnRStopExec;
    public static Button btnRExec;
    private static Button btnRSaveScript;
    private static Button rdoRRandNumT;
    private static Button rdoRRandNumF;
    public static Button btn_setSequence;
    private static Text txtRRandNumT;
    private static Text txtRRandNumF;
    private static Composite compRCPU;
    private static Composite compRMem;

    private static Frame fRCPU;
    private static Frame fRMem;

    private static String execErrMsg;
    private Label label_17;
    private Text txtRUCInterval;
    private TableColumn tblclmnNo_1;
    public static Group grpRandom;
    
    private String randKeys;
    private String randKeysName;
    public static ArrayList<Integer> selectedSequence = new ArrayList<Integer>(); //PgDefinedSequence也页面打钩的序列
    public static ArrayList<Integer> selectedKey = new ArrayList<Integer>(); //PgDefinedSequence也页面打钩的按键

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgRIR() {
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
        shell.setSize(791, 670);
        shell.setText("\u7EA2\u5916\u9065\u63A7\u5668\u6A21\u62DF\u5DE5\u5177");
        shell.setLayout(null);

        tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 784, 670);

        TabItem tbtmRandom = new TabItem(tabFolder, SWT.NONE);
        tbtmRandom.setText(Resources.OPTION_RANDOM);

        grpRandom = new Group(tabFolder, SWT.NONE);
        
        tbtmRandom.setControl(grpRandom);
        createRContents(shell, grpRandom);
       
    }


    /**
     * Create contents of the dialog.
     */
    public void createRContents(Shell shl, Group grpRandom) {
        shell = shl;
        //grpRandom.setEnabled(isRecording);
         //工具设置
        grpRSetTool = new Group(grpRandom, SWT.NONE);
        grpRSetTool.setText("\u5DE5\u5177\u8BBE\u7F6E");
        grpRSetTool.setBounds(10, 20, 375, 188);

        comboRIRCom = new Combo(grpRSetTool, SWT.NONE);
        comboRIRCom.setItems(comIR);
        comboRIRCom.setBounds(84, 26, 77, 25);
        comboRIRCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComIR(comboRIRCom.getText());
                if (connIRComSuccss && selIRSerialPort != null
                        && !selIRSerialPort.equals(comboRIRCom.getText())) {
                    disConnectCom(spIR, lRIRComStatus, btnRIRConnCom, btnRIRDisConnCom, true);
                    connIRComSuccss = false;
                }
            }
        });

        Label label_1 = new Label(grpRSetTool, SWT.NONE);
        label_1.setText("\u9065\u63A7\u5668\u4E32\u53E3");
        label_1.setBounds(10, 29, 61, 17);

        lRIRComStatus = new Label(grpRSetTool, SWT.NONE);
        lRIRComStatus.setText("\u672A\u8FDE\u63A5");
        lRIRComStatus.setBounds(167, 29, 49, 17);

        btnRIRConnCom = new Button(grpRSetTool, SWT.NONE);
        btnRIRConnCom.setText("\u8FDE\u63A5");
        btnRIRConnCom.setBounds(276, 24, 41, 27);
        btnRIRConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connIRComSuccss = connectTool(spIR, comboRIRCom.getText(), comboRDevCom.getText(),
                        lRIRComStatus, btnRIRConnCom, btnRIRDisConnCom, true);
            }
        });

        Label label_5 = new Label(grpRSetTool, SWT.NONE);
        label_5.setText("\u9065\u63A7\u5668\u7F16\u7801");
        label_5.setBounds(10, 130, 67, 17);

        rdoREncodeNEC = new Button(grpRSetTool, SWT.RADIO);
        rdoREncodeNEC.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoREncodeNEC.setText("NEC\u7801");
        rdoREncodeNEC.setSelection(true);
        rdoREncodeNEC.setBounds(84, 130, 61, 17);

        rdoREncodeRC5 = new Button(grpRSetTool, SWT.RADIO);
        rdoREncodeRC5.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoREncodeRC5.setText("RC5\u7801");
        rdoREncodeRC5.setBounds(155, 130, 67, 17);

        rdoRKeycode = new Button(grpRSetTool, SWT.RADIO);
        rdoRKeycode.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setEncodeStatus();
            }
        });
        rdoRKeycode.setText("keycode");
        rdoRKeycode.setBounds(224, 130, 67, 17);
        //区分Android电视和linux电视， Kenneth
        Composite composite_TVType = new Composite(grpRSetTool, SWT.NONE);
        composite_TVType.setBounds(10, 153, 355, 25);
//        composite_TVType.setBackground( Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));
        Label TVType = new Label(composite_TVType, SWT.NONE);
        TVType.setText("电视类别");
        TVType.setBounds(10, 5, 67, 17);
        
        //android TV
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
      //Whale TV--》linux TV
        rdoRTVTypeLinux = new Button(composite_TVType, SWT.RADIO);
        rdoRTVTypeLinux.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	setTVType();
            }
        });
        rdoRTVTypeLinux.setText("Linux TV");
        rdoRTVTypeLinux.setBounds(170, 5, 90, 17);


        btnRIRDisConnCom = new Button(grpRSetTool, SWT.NONE);
        btnRIRDisConnCom.setText("\u65AD\u5F00");
        btnRIRDisConnCom.setEnabled(false);
        btnRIRDisConnCom.setBounds(324, 24, 41, 27);
        btnRIRDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spIR, lRIRComStatus, btnRIRConnCom, btnRIRDisConnCom, true);
                connIRComSuccss = false;
            }
        });

        Label lblip_1 = new Label(grpRSetTool, SWT.NONE);
        lblip_1.setText("\u7535\u89C6IP");
        lblip_1.setBounds(10, 95, 61, 17);

        Button btnRIRComRefresh = new Button(grpRSetTool, SWT.NONE);
        btnRIRComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spIR);
            }
        });
        btnRIRComRefresh.setText("\u5237\u65B0");
        btnRIRComRefresh.setBounds(228, 24, 41, 27);

        Button btnRAdbDevRefresh = new Button(grpRSetTool, SWT.NONE);
        btnRAdbDevRefresh.setText("\u5237\u65B0");
        btnRAdbDevRefresh.setBounds(228, 90, 41, 27);
        btnRAdbDevRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshDevice();
            }
        });

        comboRDevices = new Combo(grpRSetTool, SWT.NONE);
        comboRDevices.setBounds(84, 92, 126, 25);
        comboRDevices.setItems(adbOperation.getDevices());
        comboRDevices.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setDeviceIp(comboRDevices.getText());
            }
        });

        Label label = new Label(grpRSetTool, SWT.NONE);
        label.setText("\u7535\u89C6\u4E32\u53E3");
        label.setBounds(10, 62, 61, 17);

        comboRDevCom = new Combo(grpRSetTool, SWT.NONE);
        comboRDevCom.setItems(comDev);
        comboRDevCom.setBounds(84, 59, 77, 25);
        comboRDevCom.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                toolInfo.setComDev(comboRDevCom.getText());
                if (connDevComSuccss && selDevSerialPort != null
                        && !selDevSerialPort.equals(comboRDevCom.getText())) {
                    disConnectCom(spDev, lRDevComStatus, btnRDevConnCom, btnRDevDisConnCom, false);
                    connDevComSuccss = false;
                }
            }
        });

        lRDevComStatus = new Label(grpRSetTool, SWT.NONE);
        lRDevComStatus.setText("\u672A\u8FDE\u63A5");
        lRDevComStatus.setBounds(167, 62, 49, 17);

        Button btnRDevComRefresh = new Button(grpRSetTool, SWT.NONE);
        btnRDevComRefresh.setText("\u5237\u65B0");
        btnRDevComRefresh.setBounds(228, 57, 41, 27);
        btnRDevComRefresh.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshCom(spDev);
            }
        });

        btnRDevConnCom = new Button(grpRSetTool, SWT.NONE);
        btnRDevConnCom.setText("\u8FDE\u63A5");
        btnRDevConnCom.setBounds(276, 57, 41, 27);
        btnRDevConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                connDevComSuccss = connectTool(spDev, comboRDevCom.getText(),
                        comboRIRCom.getText(), lRDevComStatus, btnRDevConnCom, btnRDevDisConnCom,
                        false);
                if (connDevComSuccss) {
                    spDev.setDevPortParameters();// 设置设备串口参数
                }
            }
        });

        btnRDevDisConnCom = new Button(grpRSetTool, SWT.NONE);
        btnRDevDisConnCom.setText("\u65AD\u5F00");
        btnRDevDisConnCom.setEnabled(false);
        btnRDevDisConnCom.setBounds(324, 57, 41, 27);
        btnRDevDisConnCom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                disConnectCom(spDev, lRDevComStatus, btnRDevConnCom, btnRDevDisConnCom, false);
                connDevComSuccss = false;
            }
        });
        //随机条件设置
        grpRRandCond = new Group(grpRandom, SWT.NONE);
        grpRRandCond.setText("\u968F\u673A\u6761\u4EF6\u8BBE\u7F6E");
        grpRRandCond.setBounds(10, 214, 375, 309);

        Label label_4 = new Label(grpRRandCond, SWT.NONE);
        label_4.setBounds(10, 27, 112, 17);
        label_4.setText("\u6309\u952E\u95F4\u9694\u65F6\u95F4\u8303\u56F4");

        txtRMinInt = new Text(grpRRandCond, SWT.BORDER);
        txtRMinInt.setText("1.0");
        txtRMinInt.setBounds(126, 24, 52, 23);
        txtRMinInt.addVerifyListener(new VerifyListener() {

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

        txtRMaxInt = new Text(grpRRandCond, SWT.BORDER);
        txtRMaxInt.setText("2.0");
        txtRMaxInt.setBounds(212, 24, 52, 23);
        txtRMaxInt.addVerifyListener(new VerifyListener() {

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

        Label label_7 = new Label(grpRRandCond, SWT.CENTER);
        label_7.setBounds(184, 27, 24, 17);
        label_7.setText("~");

        Label label_8 = new Label(grpRRandCond, SWT.NONE);
        label_8.setBounds(10, 62, 94, 17);
        label_8.setText("\u6309\u952E\u6267\u884C\u8BBE\u7F6E");

        Label label_9 = new Label(grpRRandCond, SWT.NONE);
        label_9.setBounds(10, 123, 94, 17);
        label_9.setText("\u968F\u673A\u6309\u952E\u9009\u62E9");

        Label label_11 = new Label(grpRRandCond, SWT.NONE);
        label_11.setText("\u79D2");
        label_11.setBounds(270, 27, 38, 17);

        Composite composite = new Composite(grpRRandCond, SWT.NONE);
        composite.setBounds(115, 53, 235, 62);

        Button rdoRActTime = new Button(composite, SWT.RADIO);
        rdoRActTime.setBounds(10, 8, 79, 17);
        rdoRActTime.setSelection(true);
        rdoRActTime.setText("\u64CD\u4F5C\u65F6\u957F");

        rdoRPressCnt = new Button(composite, SWT.RADIO);
        rdoRPressCnt.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rdoRPressCnt.setBounds(10, 38, 79, 17);
        rdoRPressCnt.setText("\u6309\u952E\u6B21\u6570");

        txtRActTime = new Text(composite, SWT.BORDER);
        txtRActTime.setBounds(97, 5, 52, 23);
        txtRActTime.setText("30");
        txtRActTime.addVerifyListener(new VerifyListener() {

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

        txtRPressCnt = new Text(composite, SWT.BORDER);
        txtRPressCnt.setBounds(97, 35, 52, 23);
        txtRPressCnt.setText("1000");
        txtRPressCnt.addVerifyListener(new VerifyListener() {

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

        comboRTimeUnit = new Combo(composite, SWT.NONE);
        comboRTimeUnit.setBounds(156, 5, 51, 25);
        comboRTimeUnit.setItems(new String[] { "\u79D2", "\u5206\u949F", "\u5C0F\u65F6" });
        comboRTimeUnit.select(1);

        Composite composite_SelRandom = new Composite(grpRRandCond, SWT.NONE);
        composite_SelRandom.setBounds(115, 117, 235, 100);

        rdoRNKeyNP = new Button(composite_SelRandom, SWT.RADIO);
        rdoRNKeyNP.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btn_setSequence.setEnabled(false);
                selectedSequence.clear();
                selectedKey.clear();
            }
        });
        rdoRNKeyNP.setBounds(10, 5, 165, 17);
        rdoRNKeyNP.setSelection(true);
        rdoRNKeyNP.setText("\u5E38\u7528\u6309\u952E(\u7535\u6E90\u952E\u9664\u5916)");

        rdoRNKeyCP = new Button(composite_SelRandom, SWT.RADIO);
        rdoRNKeyCP.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btn_setSequence.setEnabled(false);
                selectedSequence.clear();
                selectedKey.clear();
            }
        });
        rdoRNKeyCP.setBounds(10, 40, 165, 17);
        rdoRNKeyCP.setText("\u5E38\u7528\u6309\u952E(\u542B\u7535\u6E90\u952E)");
        
        rdoSequence = new Button(composite_SelRandom, SWT.RADIO);
        rdoSequence.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btn_setSequence.setEnabled(true);//选择自定义序列后，才能点击配置选项按钮
            }
        });
        rdoSequence.setBounds(10, 70, 113, 17);
        rdoSequence.setText("自定义序列按键");
        
        btn_setSequence = new Button(composite_SelRandom, SWT.NONE);
        btn_setSequence.setEnabled(false);
        btn_setSequence.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                PgIRSimulator.shell.setEnabled(false);
                //保存当前的遥控器类型、将table中的步骤保存成一个序列，添加到配置文件中
                PgDefinedSequence pgDefinedSequence = new PgDefinedSequence(shell,SWT.TITLE | SWT.CLOSE
                        | SWT.CENTER);
                pgDefinedSequence.open();
            }
        });
        btn_setSequence.setBounds(129, 68, 80, 20);
        btn_setSequence.setText("配置选项");

        Composite composite_SetRandom = new Composite(grpRRandCond, SWT.NONE);
        composite_SetRandom.setBounds(115, 223, 250, 78);

        rdoRRandNumT = new Button(composite_SetRandom, SWT.RADIO);
        rdoRRandNumT.setText("\u5B8C\u5168\u968F\u673A");
        rdoRRandNumT.setSelection(true);
        rdoRRandNumT.setBounds(10, 8, 69, 17);

        rdoRRandNumF = new Button(composite_SetRandom, SWT.RADIO);
        rdoRRandNumF.setText("\u4F2A\u968F\u673A");
        rdoRRandNumF.setBounds(10, 38, 79, 17);

        Button btnRGetRandNum = new Button(composite_SetRandom, SWT.NONE);
        btnRGetRandNum.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setRRandNum();
            }
        });
        btnRGetRandNum.setBounds(179, 33, 47, 27);
        btnRGetRandNum.setText("\u83B7\u53D6");

        txtRRandNumT = new Text(composite_SetRandom, SWT.BORDER);
        txtRRandNumT.setBounds(97, 5, 69, 23);
        txtRRandNumT.setEnabled(false);

        txtRRandNumF = new Text(composite_SetRandom, SWT.BORDER);
        txtRRandNumF.setBounds(97, 35, 69, 23);
        txtRRandNumF.addVerifyListener(new VerifyListener() {

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

        Label label_12 = new Label(grpRRandCond, SWT.NONE);
        label_12.setText("\u968F\u673A\u6570\u8BBE\u7F6E");
        label_12.setBounds(10, 223, 94, 17);
         //执行操作
        grpRAction = new Group(grpRandom, SWT.NONE);
        grpRAction.setText("\u6267\u884C\u64CD\u4F5C");
        grpRAction.setBounds(10, 530, 375, 73);

        btnRExec = new Button(grpRAction, SWT.NONE);
        btnRExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doRExecScript();
            }
        });
        btnRExec.setBounds(190, 38, 47, 27);
        btnRExec.setText("\u6267\u884C");

        btnRSaveScript = new Button(grpRAction, SWT.NONE);
        btnRSaveScript.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tblRScript.getItemCount() < 1) {
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
                    saveScript(shell, tblRScript, new File(savefile).getPath(),
                            Resources.MODE_RANDOM);
                }
            }
        });
        btnRSaveScript.setEnabled(false);
        btnRSaveScript.setBounds(296, 38, 69, 27);
        btnRSaveScript.setText("\u4FDD\u5B58\u811A\u672C");

        btnRStopExec = new Button(grpRAction, SWT.NONE);
        btnRStopExec.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                stopExec(Resources.MODE_RANDOM);
            }
        });
        btnRStopExec.setEnabled(false);
        btnRStopExec.setBounds(243, 38, 47, 27);
        btnRStopExec.setText("\u505C\u6B62");

        chbRExecLoop = new Button(grpRAction, SWT.CHECK);
        chbRExecLoop.setText("\u5FAA\u73AF\u6267\u884C");
        chbRExecLoop.setBounds(10, 22, 69, 17);

        txtRLoop = new Text(grpRAction, SWT.BORDER);
        txtRLoop.setText("0");
        txtRLoop.setBounds(108, 22, 47, 17);
        txtRLoop.addVerifyListener(new VerifyListener() {

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

        Label label_10 = new Label(grpRAction, SWT.NONE);
        label_10.setText("\u6B21");
        label_10.setBounds(161, 22, 33, 17);

        label_17 = new Label(grpRAction, SWT.NONE);
        label_17.setText("\u5FAA\u73AF\u95F4\u9694(\u79D2)");
        label_17.setBounds(25, 48, 74, 17);

        txtRUCInterval = new Text(grpRAction, SWT.BORDER);
        txtRUCInterval.setText("1");
        txtRUCInterval.setBounds(108, 48, 47, 17);
        txtRUCInterval.addVerifyListener(new VerifyListener() {

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

        tabFolder_2 = new TabFolder(grpRandom, SWT.NONE);
        tabFolder_2.setBounds(391, 20, 382, 582);
        //脚本内容选项卡
        TabItem tbtmRScript = new TabItem(tabFolder_2, SWT.NONE);
        tbtmRScript.setText("\u811A\u672C\u5185\u5BB9");

        grpRScript = new Group(tabFolder_2, SWT.NONE);
        tbtmRScript.setControl(grpRScript);

        tblRScript = new Table(grpRScript, SWT.BORDER | SWT.FULL_SELECTION);
        tblRScript.setHeaderVisible(true);
        tblRScript.setBounds(10, 20, 355, 492);

        final TableEditor editorR = new TableEditor(tblRScript);
        editorR.horizontalAlignment = SWT.LEFT;
        editorR.grabHorizontal = true;
        tblRScript.addMouseListener(new MouseAdapter() {

            public void mouseDown(MouseEvent event) {
                Control old = editorR.getEditor();
                if (old != null)
                    old.dispose();

                Point pt = new Point(event.x, event.y);
                final TableItem item = tblRScript.getItem(pt);
                if (item == null) {
                    return;
                }
                if (item.getBounds(Resources.SCRIPT_COL_CONTENT).contains(pt)) {
                    // 编辑说明列（第3列，index=2）
                    final int column = Resources.SCRIPT_COL_CONTENT;
                    final Text text = new Text(tblRScript, SWT.NONE);
                    text.setForeground(item.getForeground());
                    text.setText(item.getText(column));
                    text.setForeground(item.getForeground());
                    text.selectAll();
                    text.setFocus();
                    editorR.minimumWidth = text.getBounds().width;
                    editorR.setEditor(text, item, column);

                    text.addModifyListener(new ModifyListener() {

                        public void modifyText(ModifyEvent event) {
                            item.setText(column, text.getText());
                        }
                    });
                }
            }
        });

        tblclmnNo_1 = new TableColumn(tblRScript, SWT.NONE);
        tblclmnNo_1.setWidth(35);
        tblclmnNo_1.setText("No.");

        TableColumn tblclmRKey = new TableColumn(tblRScript, SWT.NONE);
        tblclmRKey.setWidth(110);
        tblclmRKey.setText("\u952E\u503C");

        TableColumn tblclmRName = new TableColumn(tblRScript, SWT.NONE);
        tblclmRName.setWidth(130);
        tblclmRName.setText("\u8BF4\u660E");

        TableColumn tblclmRInterval = new TableColumn(tblRScript, SWT.NONE);
        tblclmRInterval.setWidth(59);
        tblclmRInterval.setText("\u95F4\u9694(\u79D2)");

        TabItem tbtmRResrc = new TabItem(tabFolder_2, SWT.NONE);
        tbtmRResrc.setText("\u8D44\u6E90\u76D1\u63A7");

        grpRResrc = new Group(tabFolder_2, SWT.NONE);
        tbtmRResrc.setControl(grpRResrc);

        compRCPU = new Composite(grpRResrc, SWT.NONE | SWT.EMBEDDED);
        compRCPU.setBounds(10, 28, 354, 214);

        compRMem = new Composite(grpRResrc, SWT.NONE | SWT.EMBEDDED);
        compRMem.setBounds(10, 280, 354, 214);
        
    }

    /**
     * 随机操作模式 执行按钮操作
     */
    private void doRExecScript() {
        tblRScript.removeAll();
        if (!toolCheck(comboRDevices)) {
            return;
        }
        // 设置随机条件
        if (!setRandCond()) {
            return;
        }
        //选择自定义随机序列后，判断有没有选择序列或按键
        if (rdoSequence.getSelection() && selectedSequence.size()==0 && selectedKey.size()==0) {
            String strMsg = "选择的自定义随机序列按键，沒有配置选项";
            logger.debug(strMsg);
            showMsg(shell, strMsg, SWT.ICON_INFORMATION);
            return ;
        }
        //资源监控
        if (rtcRCPU == null) {
            fRCPU = SWT_AWT.new_Frame(compRCPU);
            rtcRCPU = new RealTimeChartRCPU(null, 600000D);
            fRCPU.add(rtcRCPU);
        }
        if (rtcRMem == null) {
            fRMem = SWT_AWT.new_Frame(compRMem);
            rtcRMem = new RealTimeChartRMem(null, 600000D);
            fRMem.add(rtcRMem);
        }
        if ("".equals(txtRUCInterval.getText())) {
            txtRUCInterval.setText("1");
        }
        logger.debug("testRstTimePath = " + testRstTimePath);
        logger.debug("logFilePath = " + logFilePath);
        int encode = 0;
        if (rdoREncodeRC5.getSelection()) {
            encode = Resources.ENCODE_RC5;
        } else if (rdoRKeycode.getSelection()) {
            encode = Resources.ENCODE_KEYCODE;
        } else {
            encode = Resources.ENCODE_NEC;
        }
        execRScript(tblRScript, encode, chbRExecLoop, txtRLoop, comboRDevices.getText(),
                Integer.parseInt(txtRUCInterval.getText()));
    }

    /**
     * 执行前，工具设置信息验证
     */
    private boolean toolCheck(Combo comboDevices) {
        if (!connIRComSuccss && (rdoREncodeNEC.getSelection() || rdoREncodeRC5.getSelection())) {
            String strMsg = "执行前，请连接遥控器串口。";
            logger.debug(strMsg);
            showMsg(shell, strMsg, SWT.ICON_INFORMATION);
            return false;
        }
        //选择自定义随机序列后，判断有没有选择序列或按键
        if (rdoSequence.getSelection() && selectedSequence.size()==0 && selectedKey.size()==0) {
            String strMsg = "选择的自定义随机序列按键，沒有配置选项";
            logger.debug(strMsg);
            showMsg(shell, strMsg, SWT.ICON_INFORMATION);
            return false;
        }
		if ("".equals(comboDevices.getText()) && rdoRKeycode.getSelection()) {
			if (isLinuxTV) {
				if (connDevComSuccss) {//Kenneth
				} else {
					String strMsg = "Whale电视选择KeyCode时，执行前，请选择电视串口。";
					logger.debug(strMsg);
					showMsg(shell, strMsg, SWT.ICON_INFORMATION);
					return false;
				}
			} else {
				String strMsg = "执行前，请选择电视IP。";
				logger.debug(strMsg);
				showMsg(shell, strMsg, SWT.ICON_INFORMATION);
				return false;
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
        if ("".equals(deviceIp)&&!isLinuxTV) {
            String strMsg = "没有选择电视IP，不能执行如下操作。是否继续执行？\n1、不能进行资源监控\n2、不能执行脚本STEP中的log验证\n3、不能执行脚本STEP中的sqlite验证\n4、不能执行脚本STEP中的截图验证";
            boolean gotoExec = showSelMsg(shell, strMsg, SWT.ICON_INFORMATION);
            if (!gotoExec) {
                return false;
            }
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
     * 随机操作模式 -- 执行脚本时，页面控件的可用性状态
     */
    public static void setRExecStatus(boolean isExec, String errMsg) {
        logger.debug("设置页面控件可用性。");
        if (isExec) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    // 执行脚本时，[停止]按钮可用，其他控件不可用
                    grpRScript.setEnabled(false);
                    grpRRandCond.setEnabled(false);
                    grpRSetTool.setEnabled(false);

                    chbRExecLoop.setEnabled(false);
                    txtRLoop.setEnabled(false);
                    btnRExec.setEnabled(false);
                    btnRSaveScript.setEnabled(false);
                    btnRStopExec.setEnabled(true);

                    // 其他模式不可执行，不可更改工具设置信息
                    PgMIR.grpMSetTool.setEnabled(false);
                    PgMIR.btnMExec.setEnabled(false);
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
                    grpRScript.setEnabled(true);
                    grpRRandCond.setEnabled(true);
                    grpRSetTool.setEnabled(true);

                    chbRExecLoop.setEnabled(true);
                    txtRLoop.setEnabled(true);
                    btnRExec.setEnabled(true);
                    btnRSaveScript.setEnabled(true);
                    btnRStopExec.setEnabled(false);

                    // 其他模式控件状态还原
                    PgMIR.grpMSetTool.setEnabled(true);
                    PgMIR.btnMExec.setEnabled(true);
                    PgEIR.grpESetTool.setEnabled(true);
                    PgEIR.btnEExec.setEnabled(true);

                    if (execErrMsg == null || "".equals(execErrMsg)) {
                        showMsg(shell, "脚本执行结束。", SWT.ICON_INFORMATION);
                    } else {
                        showMsg(shell, "用例执行失败。错误信息：" + execErrMsg, SWT.ICON_ERROR);
                    }
                }
            });
        }
    }

    /**
     * 工具设置信息的连动
     */
    public void controlLinkage() {
        String strIRCom = toolInfo.getComIR();
        String strDevCom = toolInfo.getComDev();
        String strDevice = toolInfo.getDeviceIp();
        lRIRComStatus.setText(toolInfo.getComIRStatus());
        btnRIRConnCom.setEnabled(toolInfo.isComIRConnEnabled());
        btnRIRDisConnCom.setEnabled(toolInfo.isComIRDisConnEnabled());
        lRDevComStatus.setText(toolInfo.getComDevStatus());
        btnRDevConnCom.setEnabled(toolInfo.isComDevConnEnabled());
        btnRDevDisConnCom.setEnabled(toolInfo.isComDevDisConnEnabled());
        rdoREncodeNEC.setSelection(toolInfo.isIrEncodeENCSel());
        rdoREncodeRC5.setSelection(toolInfo.isIrEncodeRC5Sel());
        rdoRKeycode.setSelection(toolInfo.isKeycodeSel());
        comboRIRCom.setText(strIRCom);
        comboRDevCom.setText(strDevCom);
        comboRDevices.setText(strDevice);
        //电视类型的联动
        rdoRTVTypeAndroid.setSelection(!toolInfo.isLinuxTV());
        rdoRTVTypeLinux.setSelection(toolInfo.isLinuxTV());
    }

    /**
     * 刷新串口
     */
    private void refreshCom(DSerialPort sp) {
        sp.initialDriver();
        String[] coms = sp.listPort();
        if (sp.getType() == Resources.TYPE_COM_IR) {
            comIR = coms;
            PgMIR.comboMIRCom.setItems(coms);
            comboRIRCom.setItems(coms);
            PgEIR.comboEIRCom.setItems(coms);
            comboRIRCom.select(0);
            disConnectCom(spIR, lRIRComStatus, btnRIRConnCom, btnRIRDisConnCom, false);
        } else if (sp.getType() == Resources.TYPE_COM_DEV) {
            comDev = coms;
            PgMIR.comboMDevCom.setItems(coms);
            comboRDevCom.setItems(coms);
            PgEIR.comboEDevCom.setItems(coms);
            comboRDevCom.select(0);
            disConnectCom(spDev, lRDevComStatus, btnRDevConnCom, btnRDevDisConnCom, false);
        }
    }

    /**
     * 刷新设备
     */
    private void refreshDevice() {
        String[] devices = adbOperation.getDevices();
        PgMIR.comboMDevices.setItems(devices);
        comboRDevices.setItems(devices);
        PgEIR.comboEDevices.setItems(devices);
        comboRDevices.select(0);
    }

    /**
     * 设置随机条件
     */
    private boolean setRandCond() {
        try {
            String minInt = String.format("%.1f", Float.parseFloat(txtRMinInt.getText()));
            String maxInt = String.format("%.1f", Float.parseFloat(txtRMaxInt.getText()));
            txtRMinInt.setText(minInt);
            txtRMaxInt.setText(maxInt);
            if (Float.parseFloat(minInt) > Float.parseFloat(maxInt)) {
                String strMsg = "按键间隔时间范围 最大值不能小于最小值。";
                logger.error(strMsg);
                showMsg(shell, strMsg, SWT.ICON_ERROR);
                return false;
            }
            randCondInfo.setMinInterval(Float.parseFloat(minInt));
            logger.debug("时间间隔（最小）为 " + minInt);
            randCondInfo.setMaxInterval(Float.parseFloat(maxInt));
            logger.debug("时间间隔（最大）为 " + maxInt);
            if (rdoRPressCnt.getSelection()) {
                randCondInfo.setScriptLimit(Resources.SCR_LIMIT_TIMES);
                String strTimes = txtRPressCnt.getText();
                if (strTimes == null || "".equals(strTimes)) {
                    randCondInfo.setStepCnt(1000);
                } else {
                    randCondInfo.setStepCnt(Integer.parseInt(txtRPressCnt.getText()));
                }
                randCondInfo.setMaxTime(0);
                logger.debug("按键操作执行限制为 按键次数 " + randCondInfo.getStepCnt());
            } else {
                randCondInfo.setScriptLimit(Resources.SCR_LIMIT_TIME);
                randCondInfo.setStepCnt(0);
                int selIndex = comboRTimeUnit.getSelectionIndex();
                if (selIndex == 0) {
                    randCondInfo.setMaxTime(Integer.parseInt(txtRActTime.getText()));
                } else if (selIndex == 1) {
                    randCondInfo.setMaxTime(Integer.parseInt(txtRActTime.getText()) * 60);
                } else {
                    randCondInfo.setMaxTime(Integer.parseInt(txtRActTime.getText()) * 3600);
                }
                logger.debug("按键操作执行限制为 执行时长 " + randCondInfo.getMaxTime() + "秒");
            }
            String randPropKey = "";
            if (rdoREncodeNEC.getSelection()) {
                randPropKey = Resources.ENCODE_PREFIX[Resources.ENCODE_NEC];
            } else if (rdoREncodeRC5.getSelection()) {
                randPropKey = Resources.ENCODE_PREFIX[Resources.ENCODE_RC5];
            } else if (rdoRKeycode.getSelection()) {
                randPropKey = Resources.ENCODE_PREFIX[Resources.ENCODE_KEYCODE];
            } else {
                String strMsg = "请选择遥控器编码。";
                logger.error(strMsg);
                showMsg(shell, strMsg, SWT.ICON_ERROR);
                return false;
            }
            logger.debug("遥控器编码选择的是= "+randPropKey);
            //whale电视的随机模式 Kenneth 20140822
            if(isLinuxTV){
            	randPropKey="WHL_";
            }
//            //SX6电视的随机模式 Kenneth 20140910
//            if(isSX6SendIRCode){
//            	randPropKey="SX6_";
//            }
            //获取随机按键范围
            if(!getRandomKeys(randPropKey)){
                return false;
            }
            //设置随机数
            if (rdoRRandNumT.getSelection()) {
                int rndNum = new Random().nextInt(1000000);
                randCondInfo.setRandNum(rndNum);
                txtRRandNumT.setText(String.valueOf(rndNum));
            } else {
                String rndNum = txtRRandNumF.getText();
                if (rndNum == null || "".equals(rndNum)) {
                    setRRandNum();
                }
                randCondInfo.setRandNum(Integer.parseInt(txtRRandNumF.getText()));
            }
            logger.debug("随机数：" + randCondInfo.getRandNum());
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showMsg(shell, e.getMessage(), SWT.ICON_ERROR);
            return false;
        }
    }
    
    /**
     * 获取随机按键的范围
     */
    private boolean getRandomKeys(String randPropKey) {
        randKeys ="" ;
        randKeysName ="";
        if (rdoRNKeyNP.getSelection()) {
            randPropKey += "NORMAL_PART_KEYS";
            logger.debug("按键选择 " + rdoRNKeyNP.getText());
            if(!getKeyRange(randPropKey)){
                return false ;
            }
        } else if (rdoRNKeyCP.getSelection()) {
            randPropKey += "NORMAL_ALL_KEYS";
            logger.debug("按键选择 " + rdoRNKeyCP.getText());
            if(!getKeyRange(randPropKey)){
                return false ;
            }
        } else if (rdoSequence.getSelection()){
            logger.debug("按键选择 " + rdoSequence.getText());
            //获取随机序列的键值和时间间隔
            ArrayList<SequenceInfo[]> sequenceList ;
            sequenceList = PgDefinedSequence.getSequence();
            randCondInfo.setRangeSequence(sequenceList);
            //获取按键的键值序列
            randKeysName += PgDefinedSequence.getKey()[0];
            randKeys += PgDefinedSequence.getKey()[1];
        }
        randCondInfo.setRangeKeys(randKeys.split(";"));
        randCondInfo.setRangeKeysName(randKeysName.split(";"));
        return true ;
    }
    
    /**
     * 获取范围值
     */
    private boolean getKeyRange(String randPropKey){
        randKeys = properties.getProperty(randPropKey);
        randKeysName = properties.getProperty(randPropKey + "_NAME");
        logger.debug("随机按键定义的范围，key= "+randKeys);
        logger.debug("随机按键定义的范围，name= "+randKeysName);
        if (randKeys == null || "".equals(randKeys) || randKeysName == null
                || "".equals(randKeysName)) {
            String strMsg = "配置文件中，没有设定相应的内容。" + randPropKey + "、" + randPropKey + "_NAME";
            logger.error(strMsg);
            showMsg(shell, strMsg, SWT.ICON_ERROR);
            return false;
        }
        return true ;
    }

    /**
     * 随机操作模式，设置伪随机数
     */
    private void setRRandNum() {
        int rndNumF = new Random().nextInt(1000000);
        txtRRandNumF.setText(String.valueOf(rndNumF));
    }
    
	/**
	 * 设置电视类型的选择状态
	 */
	private void setTVType() {
		if (rdoRTVTypeAndroid.getSelection()) {
			isLinuxTV=false;
		}if (rdoRTVTypeLinux.getSelection()) {
			isLinuxTV=true;
		}
		toolInfo.setLinuxTV(isLinuxTV);
//		toolInfo.setSX6SendIRCode(isSX6SendIRCode);
		logger.debug("isLinuxTV=" + isLinuxTV);
//		logger.debug("isSX6SendIRCode=" + isSX6SendIRCode);

	}
    /**
     * 设置编码的选择状态
     */
    private void setEncodeStatus() {
        toolInfo.setIrEncodeENCSel(rdoREncodeNEC.getSelection());
        toolInfo.setIrEncodeRC5Sel(rdoREncodeRC5.getSelection());
        toolInfo.setKeycodeSel(rdoRKeycode.getSelection());
    }
    
}
