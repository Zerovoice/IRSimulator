
package com.hisense.autotest.automation;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import java.awt.Frame;

import com.hisense.autotest.common.Resources;

public class PgIRSimulator extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgIRSimulator.class);

    protected Object result;
    protected Display display;
    public static Shell shell;

    private PgMIR pgMIR = new PgMIR();
    private PgRIR pgRIR = new PgRIR();
    private PgEIR pgEIR = new PgEIR();
    private PgTrans pgTrans = new PgTrans();
    private PgSendMTKKey pgMTKRecord = new PgSendMTKKey();
    private PgReadMTKKey pgMTKRead = new PgReadMTKKey();
    public static Group grpRandom;
    public static Group grpMaunal;
    public static Group grpExecute;
    public static Group grpTrans;

    public static TabFolder tabFolder;

    protected static Frame fMCPU;
    protected static Frame fMMem;
    protected static Frame fRCPU;
    protected static Frame fRMem;
    protected static Frame fECPU;
    protected static Frame fEMem;
    
    private Composite composite;
    private Label label;
    private Text txtLogcatInterval;

    protected static boolean initPage = false;
    protected static String eSelFilePath = "";
    protected static String execErrMsg;
    public static int logcatInterval = 20;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgIRSimulator() {
        super();
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     * @wbp.parser.entryPoint
     */
    public Object open(String[] args) {
        createContents();
        recordToolInfo();
        logger.debug("红外遥控器模拟工具>>>>>>>>>>>>>>>>>>>>>");
        shell.open();
        shell.layout();

        // 非页面形式执行，使用命令按照指定参数执行
        if (args != null && args.length > 0) {
            ExecWithParas execMethod = new ExecWithParas();
            execMethod.execScripts(args);
        }
        // 在页面中，手动执行
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
        comIR = spIR.listPort();
        comDev = spDev.listPort();

        shell = new Shell(display, SWT.CLOSE|SWT.MIN);
        shell.setSize(791, 670);
        shell.setText("\u7EA2\u5916\u9065\u63A7\u5668\u6A21\u62DF\u5DE5\u5177");
        shell.setLayout(null);
        shell.addShellListener(new ShellListener() {

            @Override
            public void shellActivated(ShellEvent arg0) {
            }

            @Override
            public void shellClosed(ShellEvent arg0) {
                if (spIR != null) {
                    spIR.close();
                }
                if (spDev != null) {
                    spDev.close();
                }
                System.exit(0);
            }

            @Override
            public void shellDeactivated(ShellEvent arg0) {
            }

            @Override
            public void shellDeiconified(ShellEvent arg0) {
            }

            @Override
            public void shellIconified(ShellEvent arg0) {
            }
        });
        
        composite = new Composite(shell, SWT.NONE);
        composite.setLocation(525, 0);
        composite.setSize(257, 22);
        
        label = new Label(composite, SWT.NONE);
        label.setText("发送logcat命令时间间隔(秒)");
        label.setAlignment(SWT.RIGHT);
        label.setBounds(10, 3, 195, 17);
        
        txtLogcatInterval = new Text(composite, SWT.BORDER);
        txtLogcatInterval.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent arg0) {
                setLogcatInterval();
            }
        });
        txtLogcatInterval.setText("20");
        txtLogcatInterval.setBounds(211, 2, 40, 20);

        tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 784, 642);
        tabFolder.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                controlLinkage(tabFolder.getSelectionIndex());
            }
        });

        TabItem tbtmManual = new TabItem(tabFolder, SWT.NONE);
        tbtmManual.setText(Resources.OPTION_MANUAL);

        grpMaunal = new Group(tabFolder, SWT.NONE);
        tbtmManual.setControl(grpMaunal);
        pgMIR.createMContents(shell, grpMaunal);

        TabItem tbtmRandom = new TabItem(tabFolder, SWT.NONE);
        tbtmRandom.setText(Resources.OPTION_RANDOM);

        grpRandom = new Group(tabFolder, SWT.NONE);
        tbtmRandom.setControl(grpRandom);
        pgRIR.createRContents(shell, grpRandom);

        TabItem tbtmExecute = new TabItem(tabFolder, SWT.NONE);
        tbtmExecute.setText(Resources.OPTION_EXCUTE);

        grpExecute = new Group(tabFolder, SWT.NONE);
        tbtmExecute.setControl(grpExecute);
        pgEIR.createEContents(shell, grpExecute);

        TabItem tbtmTrans = new TabItem(tabFolder, SWT.NONE);
        tbtmTrans.setText(Resources.OPTION_SCRIPT_TRANS);

        grpTrans = new Group(tabFolder, SWT.NONE);
        tbtmTrans.setControl(grpTrans);
        pgTrans.createTransContents(shell, grpTrans);
        // zxb mode
        TabItem tbtmtkrecord = new TabItem(tabFolder, SWT.NONE);
        tbtmtkrecord.setText("MTKRecord");

        Group grpMTKRecord = new Group(tabFolder, SWT.NONE);
        tbtmtkrecord.setControl(grpMTKRecord);
        pgMTKRecord.createMContents(shell, grpMTKRecord);

        TabItem tbtmtkread = new TabItem(tabFolder, SWT.NONE);
        tbtmtkread.setText("MTKRead");

        Group grpMTKRead = new Group(tabFolder, SWT.NONE);
        tbtmtkread.setControl(grpMTKRead);
        pgMTKRead.createEContents(shell, grpMTKRead);

    }

    /**
     * 工具打开时，记录工具设置的信息
     */
    private void recordToolInfo() {
        pgMIR.recordToolInfo();
        initPage = true;
    }

    /**
     * 工具设置信息的连动
     */
    private void controlLinkage(int tabItemIndex) {
        if (!initPage) {
            return;
        }
        if (tabItemIndex == 0) {
            pgMIR.controlLinkage();
        } else if (tabItemIndex == 1) {
            pgRIR.controlLinkage();
        } else if (tabItemIndex == 2) {
            pgEIR.controlLinkage();
        } else if (tabItemIndex == 4) {
            pgMTKRecord.controlLinkage();
        } else if (tabItemIndex == 5) {
            pgMTKRead.controlLinkage();
        }
    }
    
    /**
     * 设置发送logcat命令时间间隔
     */
    private void setLogcatInterval() {
        try {
            String strTmp = txtLogcatInterval.getText();
            if (strTmp == null || "".equals(strTmp)) {
                logcatInterval = 20;
            } else {
                logcatInterval = Integer.parseInt(strTmp);
            }
        } catch (Exception e) {
            txtLogcatInterval.setText(String.valueOf(logcatInterval));
        }
    }

}
