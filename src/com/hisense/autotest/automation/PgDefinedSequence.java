package com.hisense.autotest.automation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import com.hisense.autotest.bean.SequenceInfo;
import com.hisense.autotest.common.Resources;
import com.hisense.autotest.util.Utils;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;

public class PgDefinedSequence extends Dialog {

    private static Logger logger = Logger.getLogger(PgDefinedSequence.class);
    protected Object result;
    private Utils util = new Utils();
    protected Shell shell;
    private Tree tree_Sequence;
    private TreeColumn trclmn_check;
    private Button btn_ok;
    private Button btn_cancel;
    private Button btn_del;
    private Group group_sequence;
    private Group group_key;
    private int COL_Name = 0;
    private File xmlFile;
    private Tree tree_key;
    public static ArrayList<SequenceInfo[]> sequenceInfoList = new ArrayList<SequenceInfo[]>();
    public static ArrayList<SequenceInfo[]> selectedSequenceList = new ArrayList<SequenceInfo[]>();
    public static ArrayList<String> keysOptions = new ArrayList<String>();
    private static ArrayList<String> selectedKeyList = new ArrayList<String>();
    private int num = -1;
    private ArrayList<String[]> sequenceOptions = new ArrayList<String[]>(); 
    private ArrayList<String[]> intervalOptions = new ArrayList<String[]>(); 
    private HashMap<String, String> nameKeyMap = new HashMap<String, String>();
    private String[] name = { "主页", "数字1", "数字2", "数字3", "数字4", "数字5", "数字6",
            "数字7", "数字8", "数字9", "数字0", "音量+", "音量-", "设置", "静音", "MENU", "返回",
            "上", "下", "左", "右", "OK", "#", "*" };
    private String[] key = { "HOME", "ONE", "TWO", "THREE", "FOUR", "FIVE",
            "SIX", "SEVEN", "EIGHT", "NINE", "ZERO", "VOL_ADD", "VOL_SUB",
            "SET", "MUTE", "MENU", "RETURN", "UP", "DOWN", "LEFT", "RIGHT",
            "OK", "POUND", "STAR" };

    public PgDefinedSequence(Shell parent, int style) {
        super(parent, style);
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     */
    public Object open() {
        xmlFile = new File(Resources.sequencePath);
        if (!xmlFile.exists()) {
            logger.debug("sequence.xml文件不存在");
            return result;
        }
        initiaHashMap(); // 向HashMap填充数据
        createContents();
        shell.open();
        shell.layout();
        shell.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent arg0) {
                PgIRSimulator.shell.setEnabled(true);
            }

        });
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * 创建控件内容
     */
    private void createContents() {

        shell = new Shell(this.getParent(), this.getStyle());
        shell.setSize(454, 573);
        shell.setText("更改应用名称");

        group_sequence = new Group(shell, SWT.NONE);
        group_sequence.setText("随机序列选择");
        group_sequence.setBounds(10, 10, 423, 223);

        tree_Sequence = new Tree(group_sequence, SWT.BORDER | SWT.CHECK
                | SWT.MULTI);
        tree_Sequence.setLinesVisible(true);
        tree_Sequence.setBounds(10, 28, 403, 164);
        // 设置全选监听
        tree_Sequence.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (event.detail == SWT.CHECK) {
                    TreeItem item = (TreeItem) event.item;
                    boolean state = item.getChecked();
                    checkChildren(item, state);
                    checkParents(item, state);
                }
            }
        });
        // 设置菜单监听
        tree_Sequence.addListener(SWT.MenuDetect, new Listener() {
            @Override
            public void handleEvent(Event e) {
                Tree tree = (Tree) e.widget;
                Point pt = tree.getDisplay().map(null, tree, e.x, e.y);
                TreeItem item = tree_Sequence.getItem(pt);
                showRightMenu(item);
            }

        });

        trclmn_check = new TreeColumn(tree_Sequence, SWT.BORDER);
        trclmn_check.setWidth(380);

        btn_del = new Button(group_sequence, SWT.NONE);
        btn_del.setBounds(353, 198, 60, 20);
        btn_del.setText("\u5220\u9664\u5E8F\u5217");
        btn_del.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // 删除选中的序列
                deleteSelected();
            }

        });

        group_key = new Group(shell, SWT.NONE);
        group_key.setText("随机按键选择");
        group_key.setBounds(10, 239, 423, 262);

        tree_key = new Tree(group_key, SWT.BORDER | SWT.CHECK
                | SWT.FULL_SELECTION);
        tree_key.setLinesVisible(true);
        tree_key.setBounds(10, 25, 403, 243);
        // 设置全选监听
        tree_key.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (event.detail == SWT.CHECK) {
                    TreeItem item = (TreeItem) event.item;
                    boolean state = item.getChecked();
                    checkChildren(item, state);
                    checkParents(item, state);
                }
            }
        });

     // 将按键填充到表中
        fillKeyTable();
        // 将序列填充到表中
        fillSequenceTree();
        // 将上次打勾的选打勾
        selectLastChecked();

        btn_ok = new Button(shell, SWT.NONE);
        btn_ok.setBounds(264, 507, 80, 27);
        btn_ok.setText("确认");
        btn_ok.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                PgRIR.selectedSequence.clear();
                PgRIR.selectedKey.clear();
                selectedSequenceList.clear();
                selectedKeyList.clear();
                // 选出被选中的序列和按键
                addSelectedOption();
                // 获得序列的键值序列，按键的键值对
                getSequencevalues();
                getKeyvalues();
                shell.close();
            }

        });

        btn_cancel = new Button(shell, SWT.NONE);
        btn_cancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });
        btn_cancel.setBounds(353, 507, 80, 27);
        btn_cancel.setText("取消");
    }

    /**
     * 将上次打勾的选项选上
     */
    private void selectLastChecked() {
        // 选上序列
        for (int i = 0; i < PgRIR.selectedSequence.size(); i++) {
            tree_Sequence.getItem(0).getItem(PgRIR.selectedSequence.get(i))
                    .setChecked(true);
        }
        // 选上按键
        for (int i = 0; i < PgRIR.selectedKey.size(); i++) {
            tree_key.getItem(0).getItem(PgRIR.selectedKey.get(i))
                    .setChecked(true);
        }
    }

    /**
     * 将序列、按键中被勾选的选项选出
     */
    protected void addSelectedOption() {
        // 将选中的序列添加到队列中
        for (int i = 0; i < tree_Sequence.getItem(0).getItemCount(); i++) {
            TreeItem item = tree_Sequence.getItem(0).getItem(i);
            if (item.getChecked()) {
                selectedSequenceList.add(sequenceInfoList.get(i));
                // 保存选中的按键都有哪些，下次打开页面时，直接勾选上
                PgRIR.selectedSequence.add(i);
            }
        }
        // 将选中的键值对应的序号添加的序列中
        TreeItem item;
        for (int i = 0; i < tree_key.getItem(0).getItemCount(); i++) {
            item = tree_key.getItem(0).getItem(i);
            if (item.getChecked()) {
                selectedKeyList.add(item.getText());
                // 保存选中的按键都有哪些，下次打开页面时，直接勾选上
                PgRIR.selectedKey.add(i);
            }
        }
    }

    /**
     * 获得序列的键值序列
     */
    protected void getSequencevalues() {
        sequenceOptions.clear();
        // 获取序列里的键值序列
        for (SequenceInfo[] info : selectedSequenceList) {
            String[] value = new String[info.length];
            String[] time = new String[info.length];
            for (int i = 0; i < info.length; i++) {
                value[i] = info[i].getValue();
                if ("".equals(info[i].getInterval())) {
                    time[i] = "0";
                } else {
                    time[i] = info[i].getInterval();
                }
            }
            sequenceOptions.add(value);
            intervalOptions.add(time);
        }
    }

    /**
     * 获得按键对应编码，例如：KEYCODE_POWER
     */
    private void getKeyvalues() {
        keysOptions.clear();
        // 获取按键名
        for (int i = 0; i < selectedKeyList.size(); i++) {
            keysOptions.add(nameKeyMap.get(selectedKeyList.get(i)));
        }
    }

    /**
     * 将按键内容填充到表中
     */
    private void fillKeyTable() {
        TreeItem rootItem = new TreeItem(tree_key, SWT.CHECK);
        rootItem.setText("可选按键");
        for (int i = 0; i < nameKeyMap.size(); i++) {
            TreeItem treeItem = new TreeItem(rootItem, SWT.CHECK);
            treeItem.setText(name[i]);
        }
        tree_key.setSelection(rootItem.getItem(0));
    }

    /**
     * 将序列从XML文件中解析出来，并填充到tree中
     */
    private void fillSequenceTree() {
        try {
            sequenceInfoList.clear();
            Element newStep = null;
            String name = "";
            Document Doc = new SAXReader().read(xmlFile);
            Element rootElm = Doc.getRootElement();
            TreeItem rootItem = new TreeItem(tree_Sequence, SWT.CHECK);
            rootItem.setText(COL_Name, "可选序列");
            for (Iterator<?> j = rootElm.elementIterator(); j.hasNext();) { // 遍历所有STEPS节点
                newStep = (Element) j.next();
                name = newStep.attributeValue("name");
                TreeItem item = new TreeItem(rootItem, SWT.CHECK);
                item.setText(COL_Name, name);
                // 遍历每个STEPS节点下面的STEP节点，将每个步骤都放到Sequence对象中
                @SuppressWarnings("unchecked")
                List<Element> elements = newStep.elements();
                SequenceInfo[] sequenceInfos = new SequenceInfo[elements.size()];
                Element stepElm = null;
                for (int n = 0; n < elements.size(); n++) {
                    stepElm = elements.get(n);
                    sequenceInfos[n] = new SequenceInfo();
                    sequenceInfos[n].setIndex(stepElm.attributeValue("index"));
                    sequenceInfos[n].setNote(stepElm.attributeValue("note"));
                    sequenceInfos[n].setValue(stepElm.attributeValue("value"));
                    sequenceInfos[n].setInterval(stepElm
                            .attributeValue("interval"));

                }
                sequenceInfoList.add(sequenceInfos);
            }
            if (rootItem.getItemCount() != 0) {
                tree_Sequence.setSelection(rootItem.getItem(0));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
    }

    /**
     *删除选中的序列
     */
    private void deleteSelected() {
        TreeItem[] items = tree_Sequence.getItem(0).getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getChecked()) {
                // 删除配置文件sequence.xml中的此序列
                deleteIteminXML(items[i]);
                items[i].dispose();
            }
        }
    }

    /**
     * 删除配置文件中的此序列
     */
    private void deleteIteminXML(TreeItem item) {
        try {
            Element seqElm = null;
            String name = item.getText();
            Document Doc;
            Doc = new SAXReader().read(xmlFile);
            Element rootElm = Doc.getRootElement();
            for (Iterator<?> j = rootElm.elementIterator(); j.hasNext();) {
                seqElm = (Element) j.next();
                // 找到此序列的节点删除此序列
                if (name.equals(seqElm.attributeValue("name"))) {
                    rootElm.remove(seqElm);
                    break;
                }
            }
            // 生成文件
            util.writeDocument(Doc, xmlFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
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

    /**
     * 全选功能
     * 
     * @param msg
     */
    private void checkChildren(TreeItem item, boolean state) {
        item.setChecked(state);
        TreeItem[] items = item.getItems();
        for (TreeItem treeItem : items) {
            checkChildren(treeItem, state);
        }
    }

    /**
     * 全选功能
     * 
     * @param msg
     */
    private void checkParents(TreeItem item, boolean state) {
        item.setChecked(state);
        TreeItem parent = item.getParentItem();
        if (parent != null) {
            parent.setChecked(state);
            checkParents(parent, state);
        }
    }

    /**
     * 右键显示序列详细内容
     * 
     * @param item
     */
    private void showRightMenu(TreeItem item) {
        // 如果未选中item ，不显示菜单
        if (item == null) {
            return;
        }
        final String itemName = item.getText();
        TreeItem rootItem = tree_Sequence.getItem(0);
        // 选中的是根节点，不显示菜单
        if (itemName.equals(rootItem.getText())) {
            return;
        }
        for (int i = 0; i < rootItem.getItemCount(); i++) {
            if (itemName.equals(rootItem.getItem(i).getText())) {
                num = i;
                break;
            }
        }
        Menu menu = new Menu(tree_Sequence);
        tree_Sequence.setMenu(menu);
        MenuItem menuDetail = new MenuItem(menu, SWT.PUSH);
        menuDetail.setText("显示序列详细内容");
        menuDetail.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                showDetail(itemName, num);
            }

        });
    }

    /**
     *填充名称-键值的HashMap
     */
    private void initiaHashMap() {
        for (int i = 0; i < name.length; i++) {
            nameKeyMap.put(name[i], key[i]);
        }
    }

    /**
     * 获取按键的所有名称和键值
     * 
     */
    public static String[] getKey() {
        String[] key = { "", "" };
        for (int i = 0; i < selectedKeyList.size(); i++) {
            key[0] += selectedKeyList.get(i) + ";";
            key[1] += keysOptions.get(i) + ";";
        }
        return key;
    }

    /**
     * 获取序列的所有键值名和时间间隔
     */
    public static ArrayList<SequenceInfo[]> getSequence() {
        return selectedSequenceList;
    }

    /**
     * 创建显示详细信息的页面
     * 
     * @param currSeq
     * @param num
     */
    protected void showDetail(String currSeq, int num) {

        if (num == -1) {
            return;
        }
        shell.setEnabled(false);
        final Shell shell_name = new Shell(shell, SWT.CENTER | SWT.CLOSE);
        shell_name.setSize(340, 280);
        shell_name.setText("序列详细内容");
        shell_name.open();
        shell_name.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent arg0) {
                shell.setEnabled(true);
            }
        });
        Label title = new Label(shell_name, SWT.NONE);
        title.setBounds(20, 20, 300, 20);
        title.setText(currSeq + "序列的详细步骤");
        Table table = new Table(shell_name, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(20, 40, 300, 190);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn tblclmn_No = new TableColumn(table, SWT.NONE);
        tblclmn_No.setWidth(35);
        tblclmn_No.setText("No.");

        TableColumn tblclmn_keyvalue = new TableColumn(table, SWT.NONE);
        tblclmn_keyvalue.setWidth(115);
        tblclmn_keyvalue.setText("按键名");

        TableColumn tblclmn_note = new TableColumn(table, SWT.NONE);
        tblclmn_note.setWidth(75);
        tblclmn_note.setText("说明");

        TableColumn tblclmn_interval = new TableColumn(table, SWT.NONE);
        tblclmn_interval.setWidth(51);
        tblclmn_interval.setText("间隔");

        for (SequenceInfo info : sequenceInfoList.get(num)) {
            TableItem item = new TableItem(table, SWT.BORDER);
            item.setText(0, info.getIndex());
            item.setText(1, info.getValue());
            item.setText(2, info.getNote());
            item.setText(3, info.getInterval());
        }

    }
}
