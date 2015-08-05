
package com.hisense.autotest.automation;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.hisense.autotest.action.ExecTScriptTh;
import com.hisense.autotest.common.Resources;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.TableColumn;

public class PgTrans extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgTrans.class);

    private String lastScriptFolder = "";

    protected Object result;
    protected Display display;
    protected static Shell shell;

    private Table tblTScriptPaths;
    private Text txtTTransPath;
    private Combo comboTSrcEncode;
    private Combo comboTDesEncode;
    private List listTTransList;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgTrans() {
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

        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(0, 0, 784, 612);

        TabItem tbtmTrans = new TabItem(tabFolder, SWT.NONE);
        tbtmTrans.setText(Resources.OPTION_SCRIPT_TRANS);

        Group grpTrans = new Group(tabFolder, SWT.NONE);
        tbtmTrans.setControl(grpTrans);
        createTransContents(shell, grpTrans);

    }

    /**
     * Create contents of the dialog.
     */
    public void createTransContents(Shell shl, Group grpTrans) {
        shell = shl;

        Label label_0 = new Label(grpTrans, SWT.NONE);
        label_0.setBounds(10, 23, 76, 17);
        label_0.setText("\u5F85\u8F6C\u6362\u811A\u672C");

        Label label_2 = new Label(grpTrans, SWT.NONE);
        label_2.setText("\u9065\u63A7\u5668\u7F16\u7801");
        label_2.setBounds(10, 159, 76, 17);

        Label label_1 = new Label(grpTrans, SWT.NONE);
        label_1.setText("\u8F6C\u6362\u4E3A");
        label_1.setBounds(198, 159, 49, 17);

        Label label = new Label(grpTrans, SWT.NONE);
        label.setText("\u4FDD\u5B58\u8DEF\u5F84");
        label.setBounds(10, 194, 76, 17);

        comboTSrcEncode = new Combo(grpTrans, SWT.READ_ONLY);
        comboTSrcEncode.setItems(Resources.ENCODES);
        comboTSrcEncode.setBounds(92, 156, 86, 25);
        comboTSrcEncode.select(0);

        comboTDesEncode = new Combo(grpTrans, SWT.READ_ONLY);
        comboTDesEncode.setItems(Resources.ENCODES);
        comboTDesEncode.setBounds(253, 156, 86, 25);
        comboTDesEncode.select(0);

        tblTScriptPaths = new Table(grpTrans, SWT.BORDER | SWT.FULL_SELECTION);
        tblTScriptPaths.setBounds(92, 20, 592, 130);

        TableColumn tblclmnNewColumn = new TableColumn(tblTScriptPaths, SWT.NONE);
        tblclmnNewColumn.setWidth(570);

        txtTTransPath = new Text(grpTrans, SWT.BORDER);
        txtTTransPath.setBounds(92, 191, 506, 23);
        txtTTransPath.setText(new File("").getAbsolutePath());

        Label label1 = new Label(grpTrans, SWT.NONE);
        label1.setText("注：转换时，会在”保存路径“中新建带有时间戳的文件夹，转换后的脚本保存到新建文件夹下。");
        label1.setBounds(92, 223, 555, 17);

        Button btnTSelFile = new Button(grpTrans, SWT.NONE);
        btnTSelFile.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                selScriptFiles();
            }
        });
        btnTSelFile.setBounds(690, 20, 76, 27);
        btnTSelFile.setText("\u9009\u62E9\u6587\u4EF6");

        Button btnTSelFolder = new Button(grpTrans, SWT.NONE);
        btnTSelFolder.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                selScriptFolder();
            }
        });
        btnTSelFolder.setText("\u9009\u62E9\u6587\u4EF6\u5939");
        btnTSelFolder.setBounds(690, 50, 76, 27);

        Button btnTDelSel = new Button(grpTrans, SWT.NONE);
        btnTDelSel.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                delSel();
            }
        });
        btnTDelSel.setText("\u5220\u9664\u9009\u4E2D\u9879");
        btnTDelSel.setBounds(690, 93, 76, 27);

        Button btnTClear = new Button(grpTrans, SWT.NONE);
        btnTClear.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                clearSel();
            }
        });
        btnTClear.setText("\u6E05\u7A7A");
        btnTClear.setBounds(690, 123, 76, 27);

        Button btnTSavePath = new Button(grpTrans, SWT.NONE);
        btnTSavePath.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                selScriptSavePath();
            }
        });
        btnTSavePath.setText("\u6D4F\u89C8");
        btnTSavePath.setBounds(604, 189, 55, 27);

        Button btnTTrans = new Button(grpTrans, SWT.NONE);
        btnTTrans.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                transEncode();
            }
        });
        btnTTrans.setBounds(350, 258, 80, 27);
        btnTTrans.setText("\u8F6C\u6362");

        listTTransList = new List(grpTrans, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        listTTransList.setBounds(10, 315, 756, 257);

        Label label2 = new Label(grpTrans, SWT.NONE);
        label2.setText("\u8F6C\u6362\u7ED3\u679C\uFF1A");
        label2.setBounds(10, 292, 126, 17);

    }

    // TODO ===================================================

    /*
     * 脚本中遥控器按键的编码转换
     */
    private void transEncode() {
        listTTransList.removeAll();
        if (tblTScriptPaths.getItemCount() == 0) {
            showMsg(shell, "请先选择待转换脚本。", SWT.ICON_ERROR);
            return;
        }
        int srcEncodeIndex = comboTSrcEncode.getSelectionIndex();
        int desEncodeIndex = comboTDesEncode.getSelectionIndex();
        if (srcEncodeIndex == desEncodeIndex) {
            showMsg(shell, "转换前与转换后的遥控器编码不能相同，请确认。", SWT.ICON_ERROR);
            return;
        }
        TableItem[] scriptPaths = tblTScriptPaths.getItems();
        if (scriptPaths == null) {
            return;
        }
        ArrayList<String> scriptList = new ArrayList<String>();
        for (TableItem scriptPath : scriptPaths) {
            scriptList.add(scriptPath.getText(Resources.TRANS_COL_PATH));
        }
        logger.debug("遥控器编码由  " + comboTSrcEncode.getText() + "  转换为  " + comboTDesEncode.getText());
        String transType = comboTSrcEncode.getText() + "_" + comboTDesEncode.getText();
        execTTh = new ExecTScriptTh(transType, listTTransList, scriptList, txtTTransPath.getText(),
                srcEncodeIndex, desEncodeIndex, htEncodeMap);
        execTTh.start();
    }

    /*
     * 选择脚本文件
     */
    private void selScriptFiles() {
        FileDialog ddSelect = new FileDialog(shell, SWT.MULTI);
        ddSelect.setFilterPath(".");
        ddSelect.setFilterExtensions(new String[] { "*.csv" });
        ddSelect.setText("脚本选择");
        ddSelect.open();
        String[] selFileNames = ddSelect.getFileNames();
        String selFilePath = ddSelect.getFilterPath();
        if (selFilePath != null && selFileNames != null) {
            for (String fileName : selFileNames) {
                TableItem tableItem = new TableItem(tblTScriptPaths, SWT.NONE);
                tableItem.setText(new String[] { selFilePath + File.separator + fileName });
            }
        }
    }

    /*
     * 选择脚本文件夹
     */
    private void selScriptFolder() {
        DirectoryDialog ddSelect = new DirectoryDialog(shell);
        if (!"".equals(lastScriptFolder)) {
            ddSelect.setFilterPath(lastScriptFolder);
        }
        ddSelect.setText("脚本选择");
        String selFolder = ddSelect.open();
        if (selFolder != null) {
            lastScriptFolder = selFolder;
            TableItem tableItem = new TableItem(tblTScriptPaths, SWT.NONE);
            tableItem.setText(new String[] { selFolder });
        }
    }

    /*
     * 删除选中项
     */
    private void delSel() {
        int selIndex = tblTScriptPaths.getSelectionIndex();
        if (selIndex == -1) {
            return;
        }
        tblTScriptPaths.remove(selIndex);
        tblTScriptPaths.setSelection(selIndex);
    }

    /*
     * 清空选中项
     */
    private void clearSel() {
        tblTScriptPaths.removeAll();
    }

    /*
     * 选择存放路径
     */
    private void selScriptSavePath() {
        DirectoryDialog ddSelect = new DirectoryDialog(shell);
        if (!"".equals(txtTTransPath.getText())) {
            ddSelect.setFilterPath(txtTTransPath.getText());
        }
        ddSelect.setText("选择存放目录");
        String selFolder = ddSelect.open();
        if (selFolder != null) {
            txtTTransPath.setText(selFolder);
        }
    }

}
