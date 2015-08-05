
package com.hisense.autotest.automation;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class PgVerify extends Dialog {

    private static Logger logger = Logger.getLogger(PgVerify.class);

    private final String[] assertTypes = new String[] { "log", "sqlite", "screenshot" };
    private static String returnValue = "";
    private String defaultAssert = "";

    protected Object result;
    protected static Shell shell;
    private Text txtLog;
    private Text txtDBPath;
    private Text txtSelectSql;
    private Text txtExpectVal;
    private Combo comboAssertType;
    private Composite compLog;
    private Composite compSqlite;
    private Composite compScreenshot;
    private Table tblAssert;
    private Button btnUpdate;
    private Button chkExist;
    private Button chkEqual;
    private Text txtScreenName;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public PgVerify(Shell parent, int style, String defaultAssert) {
        super(parent, style);
        this.defaultAssert = defaultAssert;
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     */
    public Object open() {
        createContents();
        addDefaultAssert();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
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
        shell = new Shell(getParent(), getStyle());
        shell.setSize(614, 405);
        shell.setText("\u9A8C\u8BC1\u70B9\u7F16\u8F91");

        comboAssertType = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
        comboAssertType.setItems(assertTypes);
        comboAssertType.setBounds(24, 47, 92, 25);
        comboAssertType.select(0);
        comboAssertType.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent arg0) {
                modifyType();
            }
        });

        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBounds(24, 20, 61, 17);
        lblNewLabel.setText("\u9A8C\u8BC1\u7C7B\u578B");

        compSqlite = new Composite(shell, SWT.NONE);
        compSqlite.setBounds(129, 381, 474, 110);

        Label label_1 = new Label(compSqlite, SWT.NONE);
        label_1.setBounds(10, 13, 61, 17);
        label_1.setText("\u6570\u636E\u5E93\u8DEF\u5F84");

        txtDBPath = new Text(compSqlite, SWT.BORDER);
        txtDBPath.setBounds(88, 10, 368, 23);

        Label lblsql = new Label(compSqlite, SWT.NONE);
        lblsql.setBounds(10, 46, 71, 17);
        lblsql.setText("SQL\u67E5\u8BE2\u8BED\u53E5");

        txtSelectSql = new Text(compSqlite, SWT.BORDER);
        txtSelectSql.setBounds(88, 43, 368, 23);

        Label label_3 = new Label(compSqlite, SWT.NONE);
        label_3.setBounds(10, 81, 61, 17);
        label_3.setText("\u671F\u671B\u503C");

        txtExpectVal = new Text(compSqlite, SWT.BORDER);
        txtExpectVal.setBounds(88, 78, 141, 23);

        Label label_5 = new Label(compSqlite, SWT.NONE);
        label_5.setText("\u671F\u671B\u7ED3\u679C");
        label_5.setBounds(260, 81, 53, 17);

        chkEqual = new Button(compSqlite, SWT.CHECK);
        chkEqual.setSelection(true);
        chkEqual.setText("\u76F8\u540C");
        chkEqual.setBounds(319, 81, 98, 17);

        compLog = new Composite(shell, SWT.NONE);
        compLog.setBounds(129, 37, 474, 110);

        Label label = new Label(compLog, SWT.NONE);
        label.setBounds(10, 13, 47, 17);
        label.setText("\u5173\u952E\u5B57");

        txtLog = new Text(compLog, SWT.BORDER | SWT.WRAP);
        txtLog.setBounds(69, 10, 387, 51);

        Label label_4 = new Label(compLog, SWT.NONE);
        label_4.setText("\u671F\u671B\u7ED3\u679C");
        label_4.setBounds(10, 72, 53, 17);

        chkExist = new Button(compLog, SWT.CHECK);
        chkExist.setSelection(true);
        chkExist.setBounds(69, 72, 98, 17);
        chkExist.setText("\u5B58\u5728");

        Button btnAdd = new Button(shell, SWT.NONE);
        btnAdd.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                addAssertPoint();
            }
        });
        btnAdd.setBounds(505, 153, 80, 27);
        btnAdd.setText("\u6DFB\u52A0");

        btnUpdate = new Button(shell, SWT.NONE);
        btnUpdate.setEnabled(false);
        btnUpdate.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                uptAssertPoint();
            }
        });
        btnUpdate.setText("\u66F4\u65B0");
        btnUpdate.setBounds(418, 153, 80, 27);

        tblAssert = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        tblAssert.setBounds(24, 186, 561, 132);
        tblAssert.setHeaderVisible(true);
        tblAssert.setLinesVisible(true);
        tblAssert.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doTblAssertSeled();
            }
        });

        TableColumn tblclmnNo = new TableColumn(tblAssert, SWT.NONE);
        tblclmnNo.setWidth(35);
        tblclmnNo.setText("No.");

        TableColumn tableColumn = new TableColumn(tblAssert, SWT.NONE);
        tableColumn.setWidth(85);
        tableColumn.setText("\u7C7B\u578B");

        TableColumn tableColumn_1 = new TableColumn(tblAssert, SWT.NONE);
        tableColumn_1.setWidth(225);
        tableColumn_1.setText("\u53C2\u65701");

        TableColumn tableColumn_2 = new TableColumn(tblAssert, SWT.NONE);
        tableColumn_2.setWidth(65);
        tableColumn_2.setText("\u53C2\u65702");

        TableColumn tableColumn_3 = new TableColumn(tblAssert, SWT.NONE);
        tableColumn_3.setWidth(65);
        tableColumn_3.setText("\u53C2\u65703");

        TableColumn tableColumn_4 = new TableColumn(tblAssert, SWT.NONE);
        tableColumn_4.setWidth(65);
        tableColumn_4.setText("\u53C2\u65704");

        Button btnDelete = new Button(shell, SWT.NONE);
        btnDelete.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                delSelAssetPoint();
            }
        });
        btnDelete.setText("\u5220\u9664");
        btnDelete.setBounds(24, 324, 80, 27);

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });
        btnCancel.setText("Cancel");
        btnCancel.setBounds(505, 324, 80, 27);

        Label label_2 = new Label(shell, SWT.NONE);
        label_2.setText("\u9A8C\u8BC1\u5185\u5BB9");
        label_2.setBounds(140, 20, 61, 17);

        Button btnOk = new Button(shell, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                setReturnValue(recordAssertPoints());
                shell.close();
            }
        });
        btnOk.setText("OK");
        btnOk.setBounds(418, 324, 80, 27);

        compScreenshot = new Composite(shell, SWT.NONE);
        compScreenshot.setBounds(129, 505, 474, 110);

        Label label_6 = new Label(compScreenshot, SWT.NONE);
        label_6.setText("\u622A\u56FE\u540D\u79F0");
        label_6.setBounds(10, 13, 53, 17);

        txtScreenName = new Text(compScreenshot, SWT.BORDER | SWT.WRAP);
        txtScreenName.setBounds(69, 10, 387, 51);
    }

    // TODO----------------------------------------------------------------------------

    /*
     * 验证点类型变更操作
     */
    private void modifyType() {
        if (comboAssertType.getSelectionIndex() == 0) {
            // log
            compSqlite.setVisible(false);
            compLog.setVisible(true);
            compScreenshot.setVisible(false);
            compLog.setBounds(129, 37, 474, 110);
            txtLog.setText("");
            chkExist.setSelection(true);
        } else if (comboAssertType.getSelectionIndex() == 1) {
            // sqlite
            compLog.setVisible(false);
            compSqlite.setVisible(true);
            compScreenshot.setVisible(false);
            compSqlite.setBounds(129, 37, 474, 110);
            txtDBPath.setText("");
            txtSelectSql.setText("");
            txtExpectVal.setText("");
            chkEqual.setSelection(true);
        } else if (comboAssertType.getSelectionIndex() == 2) {
            // screenshot
            compScreenshot.setVisible(true);
            compLog.setVisible(false);
            compSqlite.setVisible(false);
            compScreenshot.setBounds(129, 37, 474, 110);
            txtScreenName.setText("");
        }
        btnUpdate.setEnabled(false);
    }

    /*
     * 选择已添加的验证点
     */
    private void doTblAssertSeled() {
        int selIndex = tblAssert.getSelectionIndex();
        if (selIndex != -1) {
            TableItem tblItem = tblAssert.getItem(selIndex);
            if (assertTypes[0].equals(tblItem.getText(1))) {
                // 选择的验证点是log验证
                comboAssertType.select(0);
                txtLog.setText(tblItem.getText(2));
                if ("true".equalsIgnoreCase(tblItem.getText(3))) {
                    // 期望结果为 ：存在
                    chkExist.setSelection(true);
                } else {
                    // 期望结果为 ：不存在
                    chkExist.setSelection(false);
                }
            } else if (assertTypes[1].equals(tblItem.getText(1))) {
                // 选择的验证点是sqlite验证
                comboAssertType.select(1);
                txtDBPath.setText(tblItem.getText(2));
                txtSelectSql.setText(tblItem.getText(3));
                txtExpectVal.setText(tblItem.getText(4));
                if ("true".equalsIgnoreCase(tblItem.getText(5))) {
                    // 期望值相同
                    chkEqual.setSelection(true);
                } else {
                    // 期望值不相同
                    chkEqual.setSelection(false);
                }
            } else if (assertTypes[2].equals(tblItem.getText(1))) {
                // 选择的验证点是screenshot验证
                comboAssertType.select(2);
                txtScreenName.setText(tblItem.getText(2));
            }
            btnUpdate.setEnabled(true);
        }
    }

    /*
     * 添加验证点
     */
    private void addAssertPoint() {
        if (checkAssertPoint()) {
            if (comboAssertType.getSelectionIndex() == 0) {
                // log验证
                int itemCnt = tblAssert.getItemCount();
                TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                tableItem.setText(new String[] { String.valueOf(itemCnt + 1),
                        comboAssertType.getText(), txtLog.getText(),
                        String.valueOf(chkExist.getSelection()) });
                tblAssert.setTopIndex(itemCnt + 1);
                // 数据还原
                txtLog.setText("");
                chkExist.setSelection(true);
            } else if (comboAssertType.getSelectionIndex() == 1) {
                // sqlite验证
                int itemCnt = tblAssert.getItemCount();
                TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                tableItem.setText(new String[] { String.valueOf(itemCnt + 1),
                        comboAssertType.getText(), txtDBPath.getText(), txtSelectSql.getText(),
                        txtExpectVal.getText(), String.valueOf(chkEqual.getSelection()) });
                tblAssert.setTopIndex(itemCnt + 1);
                // 数据还原
                txtDBPath.setText("");
                txtSelectSql.setText("");
                txtExpectVal.setText("");
            } else if (comboAssertType.getSelectionIndex() == 2) {
                // screenshot验证
                int itemCnt = tblAssert.getItemCount();
                TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                tableItem.setText(new String[] { String.valueOf(itemCnt + 1),
                        comboAssertType.getText(), txtScreenName.getText() });
                tblAssert.setTopIndex(itemCnt + 1);
                // 数据还原
                txtScreenName.setText("");
            }
        }
    }

    /*
     * 更新验证点
     */
    private void uptAssertPoint() {
        int selIndex = tblAssert.getSelectionIndex();
        if (selIndex < 0) {
            return;
        }
        if (checkAssertPoint()) {
            TableItem tableItem = tblAssert.getItem(selIndex);
            if (comboAssertType.getSelectionIndex() == 0) {
                // log验证
                tableItem.setText(new String[] { tableItem.getText(0), comboAssertType.getText(),
                        txtLog.getText(), String.valueOf(chkExist.getSelection()) });
                tblAssert.setTopIndex(selIndex);
                // 数据还原
                txtLog.setText("");
                chkExist.setSelection(true);
            } else if (comboAssertType.getSelectionIndex() == 1) {
                // sqlite验证
                tableItem.setText(new String[] { tableItem.getText(0), comboAssertType.getText(),
                        txtDBPath.getText(), txtSelectSql.getText(), txtExpectVal.getText(),
                        String.valueOf(chkEqual.getSelection()) });
                tblAssert.setTopIndex(selIndex);
                // 数据还原
                txtDBPath.setText("");
                txtSelectSql.setText("");
                txtExpectVal.setText("");
            } else if (comboAssertType.getSelectionIndex() == 2) {
                // screenshot验证
                tableItem.setText(new String[] { tableItem.getText(0), comboAssertType.getText(),
                        txtScreenName.getText() });
                tblAssert.setTopIndex(selIndex);
                // 数据还原
                txtScreenName.setText("");
            }
        }
    }

    /*
     * 验证输入内容
     */
    private boolean checkAssertPoint() {
        boolean checkRst = true;
        if (comboAssertType.getSelectionIndex() == 0) {
            // log验证
            if ("".equals(txtLog.getText())) {
                showMsg("[关键字]不能为空。", SWT.ERROR);
                checkRst = false;
            }
        } else if (comboAssertType.getSelectionIndex() == 1) {
            // sqlite验证
            if ("".equals(txtDBPath.getText())) {
                showMsg("[数据库路径]不能为空。", SWT.ERROR);
                checkRst = false;
            } else if ("".equals(txtSelectSql.getText())) {
                showMsg("[SQL查询语句]不能为空。", SWT.ERROR);
                checkRst = false;
            } else if ("".equals(txtExpectVal.getText())) {
                showMsg("[期望值]不能为空。", SWT.ERROR);
                checkRst = false;
            }
        } else if (comboAssertType.getSelectionIndex() == 2) {
            // screenshot验证
//			if ("".equals(txtScreenName.getText())) {
//				showMsg("[截图名称]不能为空。", SWT.ERROR);
//				checkRst = false;
//			}
        }
        return checkRst;
    }

    /*
     * 删除验证点
     */
    private void delSelAssetPoint() {
        int selIndex = tblAssert.getSelectionIndex();
        if (selIndex < 0) {
            return;
        }
        tblAssert.remove(selIndex);
        for (int i = 0; i < tblAssert.getItemCount(); i++) {
            tblAssert.getItem(i).setText(0, String.valueOf(i + 1));
        }
        tblAssert.select(selIndex);
        btnUpdate.setEnabled(false);
    }

    /*
     * 记录验证点
     */
    private String recordAssertPoints() {
        StringBuffer assertPoints = new StringBuffer();
        int itemCnt = tblAssert.getItemCount();
        if (itemCnt > 0) {
            assertPoints.append("<root>");
            TableItem tblItem;
            for (int i = 0; i < itemCnt; i++) {
                tblItem = tblAssert.getItem(i);
                if (assertTypes[0].equals(tblItem.getText(1))) {
                    // log验证
                    assertPoints.append("<assertlog isexist='");
                    assertPoints.append(tblItem.getText(3));
                    assertPoints.append("'>");
                    assertPoints.append(tblItem.getText(2));
                    assertPoints.append("</assertlog>");
                } else if (assertTypes[1].equals(tblItem.getText(1))) {
                    // sqlite验证
                    assertPoints.append("<assertsqlite isequal='");
                    assertPoints.append(tblItem.getText(5));
                    assertPoints.append("'>");
                    assertPoints.append("<dbpath>");
                    assertPoints.append(tblItem.getText(2));
                    assertPoints.append("</dbpath>");
                    assertPoints.append("<selectsql>");
                    assertPoints.append(tblItem.getText(3));
                    assertPoints.append("</selectsql>");
                    assertPoints.append("<expectval>");
                    assertPoints.append(tblItem.getText(4));
                    assertPoints.append("</expectval>");
                    assertPoints.append("</assertsqlite>");
                } else if (assertTypes[2].equals(tblItem.getText(1))) {
                    // screenshot验证
                    assertPoints.append("<screenshot>");
                    assertPoints.append("<name>");
                    assertPoints.append(tblItem.getText(2));
                    assertPoints.append("</name>");
                    assertPoints.append("</screenshot>");
                }
            }
            assertPoints.append("</root>");
        }
        return assertPoints.toString();
    }

    /*
     * 窗口打开时，添加默认验证点
     */
    @SuppressWarnings("unchecked")
    private void addDefaultAssert() {
        if (defaultAssert == null || "".equals(defaultAssert)) {
            return;
        }
        Element elmAssert;
        try {
            int itemCnt = 0;
            elmAssert = DocumentHelper.parseText(defaultAssert).getRootElement();
            // log验证
            List<Element> assElms = elmAssert.elements("assertlog");
            if (assElms != null && assElms.size() > 0) {
                for (Element assElm : assElms) {
                    TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                    tableItem.setText(new String[] { String.valueOf(++itemCnt), assertTypes[0],
                            assElm.getText(), assElm.attributeValue("isexist") });
                }
            }
            // sqlite验证
            assElms = elmAssert.elements("assertsqlite");
            if (assElms != null && assElms.size() > 0) {
                String isEqual = "";
                for (Element assElm : assElms) {
                    TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                    if (assElm.attribute("isequal") != null) {
                        isEqual = assElm.attributeValue("isequal");
                    } else if (assElm.element("expectval").attribute("isequal") != null) {
                        isEqual = assElm.element("expectval").attributeValue("isequal");
                    } else {
                        isEqual = "true";
                    }
                    tableItem.setText(new String[] { String.valueOf(++itemCnt), assertTypes[1],
                            assElm.elementText("dbpath"), assElm.elementText("selectsql"),
                            assElm.elementText("expectval"), isEqual });
                }
            }
            // screenshot验证
            assElms = elmAssert.elements("screenshot");
            if (assElms != null && assElms.size() > 0) {
                for (Element assElm : assElms) {
                    TableItem tableItem = new TableItem(tblAssert, SWT.NONE);
                    tableItem.setText(new String[] { String.valueOf(++itemCnt), assertTypes[2],
                            assElm.elementText("name") });
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * 显示提示信息
     */
    private static void showMsg(String msg, int level) {
        MessageBox msgBox = new MessageBox(shell, SWT.OK | level | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        msgBox.open();
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        PgVerify.returnValue = returnValue;
    }
}
