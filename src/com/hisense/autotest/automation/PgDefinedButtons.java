package com.hisense.autotest.automation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.hisense.autotest.common.Resources;
import com.hisense.autotest.util.Utils;

public class PgDefinedButtons extends Composite {

    private static Logger logger = Logger.getLogger(PgDefinedButtons.class);
    private static String nameSuffix = "_自定义遥控器.properties";
    private static String nameDefineSuffix = "_自定义遥控器";
    private static Group grpCustom;
    private static Group group_new;

    private final int coordx_1 = 2;
    private final int coordx_2 = 87;
    private final int coordx_3 = 172;
    private final int coordx_4 = 257;
    private final int width = 80;
    private static boolean isSX6 = false;

    private ArrayList<String> normalKeys = new ArrayList<String>();
    private ArrayList<String> normalKeysName = new ArrayList<String>();
    private HashMap<String, String> normalNameMap = new HashMap<String, String>();
    private ArrayList<String> keys;
    private String outputString;
    private ArrayList<String[]> importList = new ArrayList<String[]>();
    private Utils utils = new Utils();

    private String importFilePath = ""; // 导入文件的路径
    private String fileName = "";
    private File newRemoterFile;

    private Button btn_edit;
    private Button btn_new;
    private Button btn_cancelNew;
    private Button btn_saveNew;
    private Button btn_editsave;
    private Button btn_editsaveas;
    private Button btn_editcancel;
    private Button btn_sx6;
    private Label lbl_No2;
    private Label lbl_No3;
    private Label lbl_No1;
    private Text txt_path;
    private Text txt_fileName;
    private Button btn_11;
    private Button btn_12;
    private Button btn_13;
    private Button btn_14;
    private Button btn_21;
    private Button btn_22;
    private Button btn_23;
    private Button btn_24;
    private Button btn_31;
    private Button btn_32;
    private Button btn_33;
    private Button btn_34;
    private Button btn_41;
    private Button btn_42;
    private Button btn_43;
    private Button btn_44;
    private Button btn_51;
    private Button btn_52;
    private Button btn_53;
    private Button btn_54;
    private Button btn_61;
    private Button btn_62;
    private Button btn_63;
    private Button btn_64;
    private Button btn_71;
    private Button btn_72;
    private Button btn_73;
    private Button btn_74;
    private Button btn_81;
    private Button btn_82;
    private Button btn_83;
    private Button btn_84;
    private Button btn_91;
    private Button btn_92;
    private Button btn_93;
    private Button btn_94;
    private Button btn_a1;
    private Button btn_a2;
    private Button btn_a3;
    private Button btn_a4;
    private Button btn_b1;
    private Button btn_b2;
    private Button btn_b3;
    private Button btn_b4;
    private Button btn_c1;
    private Button btn_c2;
    private Button btn_c3;
    private Button btn_c4;
    private Button btn_d1;
    private Button btn_d2;
    private Button btn_d3;
    private Button btn_d4;
    private Button btn_e1;
    private Button btn_e2;
    private Button btn_e3;
    private Button btn_e4;
    private Button btn_f1;
    private Button btn_f2;
    private Button btn_f3;
    private Button btn_f4;
    private Button btn_g1;
    private Button btn_g2;
    private Button btn_g3;
    private Button btn_g4;
    private Button btn_h1;
    private Button btn_h2;
    private Button btn_h3;
    private Button btn_h4;
    private Button btn_i1;
    private Button btn_i2;
    private Button btn_i3;
    private Button btn_i4;
    private Button btn_j1;
    private Button btn_j2;
    private Button btn_j3;
    private Button btn_j4;
    private Button btn_k1;
    private Button btn_k2;
    private Button btn_k3;
    private Button btn_k4;
    private Button btn_l1;
    private Button btn_l2;
    private Button btn_l3;
    private Button btn_l4;
    private Combo combo_11;
    private Combo combo_12;
    private Combo combo_13;
    private Combo combo_14;
    private Combo combo_21;
    private Combo combo_22;
    private Combo combo_23;
    private Combo combo_24;
    private Combo combo_31;
    private Combo combo_32;
    private Combo combo_33;
    private Combo combo_34;
    private Combo combo_41;
    private Combo combo_42;
    private Combo combo_43;
    private Combo combo_44;
    private Combo combo_51;
    private Combo combo_52;
    private Combo combo_53;
    private Combo combo_54;
    private Combo combo_61;
    private Combo combo_62;
    private Combo combo_63;
    private Combo combo_64;
    private Combo combo_71;
    private Combo combo_72;
    private Combo combo_73;
    private Combo combo_74;
    private Combo combo_81;
    private Combo combo_82;
    private Combo combo_83;
    private Combo combo_84;
    private Combo combo_91;
    private Combo combo_92;
    private Combo combo_93;
    private Combo combo_94;
    private Combo combo_a1;
    private Combo combo_a2;
    private Combo combo_a3;
    private Combo combo_a4;
    private Combo combo_b1;
    private Combo combo_b2;
    private Combo combo_b3;
    private Combo combo_b4;
    private Combo combo_c1;
    private Combo combo_c2;
    private Combo combo_c3;
    private Combo combo_c4;
    private Combo combo_d1;
    private Combo combo_d2;
    private Combo combo_d3;
    private Combo combo_d4;
    private Combo combo_e1;
    private Combo combo_e2;
    private Combo combo_e3;
    private Combo combo_e4;
    private Combo combo_f1;
    private Combo combo_f2;
    private Combo combo_f3;
    private Combo combo_f4;
    private Combo combo_g1;
    private Combo combo_g2;
    private Combo combo_g3;
    private Combo combo_g4;
    private Combo combo_h1;
    private Combo combo_h2;
    private Combo combo_h3;
    private Combo combo_h4;
    private Combo combo_i1;
    private Combo combo_i2;
    private Combo combo_i3;
    private Combo combo_i4;
    private Combo combo_j1;
    private Combo combo_j2;
    private Combo combo_j3;
    private Combo combo_j4;
    private Combo combo_k1;
    private Combo combo_k2;
    private Combo combo_k3;
    private Combo combo_k4;
    private Combo combo_l1;
    private Combo combo_l2;
    private Combo combo_l3;
    private Combo combo_l4;
    private Composite composite;
    private Composite composite_combos;
    private Composite composite_buttons;
    private ScrolledComposite scrolledComposite_combo;
    private ScrolledComposite scrolledComposite_btn;
    private Text text_saveAsName;

    public PgDefinedButtons(Composite parent, int style) {
        super(parent, style);
        this.composite = this;
        open();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public PgDefinedButtons(Composite parent, int style, Composite composite) {
        super(parent, style);
        this.composite = composite;
        open();
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void open() {

        if (!getKeys()) {
            logger.error("keyName.txt文件不存在！");
            return;
        }

        grpCustom = new Group(composite, SWT.NONE);
        grpCustom.setBounds(5, 0, 360, 50);

        scrolledComposite_combo = new ScrolledComposite(composite, SWT.BORDER
                | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite_combo.setBounds(5, 56, 360, 430);
        scrolledComposite_combo.setExpandHorizontal(true);
        scrolledComposite_combo.setExpandVertical(true);
        composite_combos = new Composite(scrolledComposite_combo, SWT.NONE);
        createCombos(composite_combos);
        scrolledComposite_combo.setContent(composite_combos);
        scrolledComposite_combo.setMinSize(composite_combos.computeSize(
                SWT.DEFAULT, SWT.DEFAULT));

        scrolledComposite_btn = new ScrolledComposite(composite, SWT.BORDER
                | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite_btn.setBounds(5, 56, 360, 463);
        scrolledComposite_btn.setExpandHorizontal(true);
        scrolledComposite_btn.setExpandVertical(true);
        composite_buttons = new Composite(scrolledComposite_btn, SWT.NONE);
        createButtons(composite_buttons);
        scrolledComposite_btn.setContent(composite_buttons);
        scrolledComposite_btn.setMinSize(composite_buttons.computeSize(
                SWT.DEFAULT, SWT.DEFAULT));

        text_saveAsName = new Text(composite, SWT.BORDER);
        text_saveAsName.setBounds(77, 490, 100, 23);
        text_saveAsName.setMessage("另存遥控器名称");

        btn_editsave = new Button(composite, SWT.NONE);
        btn_editsave.setBounds(221, 490, 62, 23);
        btn_editsave.setText("保存");
        btn_editsave.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 将编辑的内容保存到页面位置信息文件中
                saveEditedInfo();
            }

        });

        btn_editcancel = new Button(composite, SWT.NONE);
        btn_editcancel.setBounds(303, 490, 62, 23);
        btn_editcancel.setText("取消");
        btn_editcancel.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 恢复到刚导入时的界面，没有编辑过
                if (importList == null) {
                    return;
                }
                scrolledComposite_btn.setVisible(true);
                setEditPageVisible(false);
                showImportFile(importList);
                btn_edit.setEnabled(true);
            }

        });

        btn_editsaveas = new Button(composite, SWT.NONE);
        btn_editsaveas.setBounds(5, 490, 62, 23);
        btn_editsaveas.setText("另存为");
        btn_editsaveas.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                saveAs();
            }

        });

        createCustomePage(grpCustom);

        setEditPageVisible(false);
        scrolledComposite_btn.setVisible(false);

    }

    /**
     * 控制编辑页面是否可见
     * 
     * @param visible
     */
    private void setEditPageVisible(boolean visible) {
        scrolledComposite_combo.setVisible(visible);
        text_saveAsName.setVisible(visible);
        btn_editcancel.setVisible(visible);
        btn_editsave.setVisible(visible);
        btn_editsaveas.setVisible(visible);
    }

    /**
     * 保存编辑过的遥控器文件
     */
    private void saveEditedInfo() {
        String pagePath = Resources.pageInfoPath + File.separator
                + txt_path.getText() + nameDefineSuffix;
        File file = new File(pagePath);
        if (!savePosition(file)) {
            return;
        }
        scrolledComposite_btn.setVisible(true);
        // 将选中的combo显示为button
        comboToButton();
        btn_edit.setEnabled(true);
    }

    /**
     * 另存新配置的页面
     */
    private void saveAs() {
        String fileName = text_saveAsName.getText(); // 新遥控器名称
        if ("".equals(fileName)) {
            showMsg("遥控器名称不能为空", SWT.ICON_INFORMATION);
            return;
        }
        if(fileName.contains(" ") || fileName.contains("_")){
            showMsg("文件名不能含有空格或下划线！", SWT.ERROR);
            return;
        }
        // 判断另存的是否是离散码遥控器
        if (isSX6) {
            if (!fileName.contains("(离散码)")) {
                fileName = fileName + Resources.sx6Name;
            }
            if (!showAskMsg("导入的是离散码遥控器，确定另存为离散码遥控器？", SWT.ICON_QUESTION)) {
                return;
            }
        }
        try {
            File newpropertyFile = new File(Resources.propertyFilePath
                    + File.separator + fileName + nameSuffix);
            if (newpropertyFile.exists()) {
                if (!showAskMsg("遥控器名称已经存在，是否覆盖？", SWT.ICON_INFORMATION)) {
                    return;
                }
            }
            File oldpropertyFile = new File(Resources.propertyFilePath
                    + File.separator + txt_path.getText() + nameSuffix);
            File newPositionFile = new File(Resources.pageInfoPath
                    + File.separator + fileName + nameDefineSuffix);
            // 生成新的property文件
            saveNewFile(oldpropertyFile, newpropertyFile, fileName);
            // 保存新的位置文件
            if (!savePosition(newPositionFile)) {
                showMsg("遥控器保存失败！", SWT.ICON_INFORMATION);
                return;
            }
            scrolledComposite_btn.setVisible(true);
            // 将选中的combo显示为button
            comboToButton();
            btn_edit.setEnabled(true);
            txt_path.setText(fileName);
            showMsg(fileName + nameDefineSuffix + "遥控器保存成功！",
                    SWT.ICON_INFORMATION);
            inputEncodeMap();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showMsg("遥控器保存失败！", SWT.ICON_INFORMATION);
        }

    }

    /**
     * 另存文件时,将文件中间的名称替换成新文件的名称
     */
    private void saveNewFile(File oldfile, File newfile, String replaceName) {
        String content = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    new FileInputStream(oldfile), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line = null;
            int i = 1;
            String partContent1;
            String partContent2;
            String tmp;
            while ((line = bufferedReader.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                }
                if (i != 1) {
                    content += "\n";
                }
                if (line.startsWith(Resources.comment)) {
                    content += line;
                    i++;
                    continue;
                }
                int index = line.indexOf("_") + 1;
                partContent1 = line.substring(0, index);
                tmp = line.substring(index);
                partContent2 = tmp.substring(tmp.indexOf("_"));
                content += partContent1 + replaceName + partContent2;
                i++;
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(newfile), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 创建自定义遥控器的页面
     * 
     * @param grpCustom
     */
    private void createCustomePage(Group group) {
        txt_path = new Text(group, SWT.BORDER | SWT.READ_ONLY);
        txt_path.setBounds(142, 20, 142, 23);
        txt_path.setEnabled(false);

        btn_new = new Button(group, SWT.NONE);
        btn_new.setBounds(10, 20, 62, 23);
        btn_new.setText("新建");
        btn_new.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 打开新建页面
                showNewPage(composite);
            }
        });
        
        Button btn_import = new Button(group, SWT.NONE);
        btn_import.setText("导入");
        btn_import.setBounds(74, 20, 62, 23);
        btn_import.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                importFile();
            }

        });

        btn_edit = new Button(grpCustom, SWT.NONE);
        btn_edit.setText("编辑");
        btn_edit.setBounds(288, 20, 62, 23);
        btn_edit.setEnabled(false);
        btn_edit.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 编辑导入的文件
                editImportFile();
            }

        });

    }

    /**
     * 导入一个遥控器文件
     */
    private void importFile() {
        importFilePath = browseFile();
        if ("".equals(importFilePath)) {
            return;
        }
        isSX6 = false;
        File propertiesFile = new File(importFilePath);
        setEditPageVisible(false);
        scrolledComposite_btn.setVisible(true);
        btn_edit.setEnabled(true);
        setAllButtonVisible(false);
        String name = propertiesFile.getName().replace(nameSuffix, "");
        txt_path.setText(name);
        // 导入遥控器的properties文件
        if (importProperties(name) == null) {
            return;
        }
        inputEncodeMap();
        // 导入界面上的位置信息的文件
        String pagePath = Resources.pageInfoPath + File.separator + name;
        importList = importFile(pagePath);
        cleanButtonsColor();
        // 如果导入的是SX6遥控器，弹出提示
        if (isSX6) {
            grpCustom.setText("离散码遥控器");
        }else{
            grpCustom.setText("");
        }
        showImportFile(importList);
    }

    /**
     * 导入遥控器的properties文件，将properties文件作为下拉列表的内容
     */
    private ArrayList<String> importProperties(String name) {
        try {
            File file = new File(Resources.propertyFilePath + File.separator
                    + name + nameSuffix);
            InputStreamReader inputReader = new InputStreamReader(
                    new FileInputStream(file), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line = null;
            String content = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(" ")) {
                    showMsg(name + nameSuffix + "文件每行不能以空格开头！",
                            SWT.ICON_INFORMATION);
                    return null;
                }
                if (line.contains(Resources.sx6Name)) {
                    isSX6 = true;
                }
                content += line + "\n";
            }
            if ("".equals(content)) {
                showMsg(name + nameSuffix + "文件内容为空", SWT.ICON_INFORMATION);
                keys = null;
            }
            keys = PgNewRemotor.writeKeys(content);
        } catch (FileNotFoundException e) {
            showMsg(name + nameSuffix + "件不存在", SWT.ICON_INFORMATION);
            keys = null;
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            keys = null;
            logger.error(e.getMessage(), e);
        }
        return keys;
    }

    /**
     * 编辑导入的遥控器界面
     */
    private void editImportFile() {
        // 导入遥控器的properties文件
        keys = importProperties(txt_path.getText());
        if (keys == null) {
            return;
        }
        btn_edit.setEnabled(false);
        text_saveAsName.setText("");
        setCombosText("");
        setComboContent(keys);
        // 导入界面上的位置信息的文件
        String pagePath = Resources.pageInfoPath + File.separator
                + txt_path.getText();
        importList = importFile(pagePath);
        if (importList == null) {
            return;
        }
        showImportFile(importList);
        showCombo();
        setAllComboVisible(true);
        setEditPageVisible(true);
        scrolledComposite_btn.setVisible(false);

    }

    /**
     * 根据button的内容显示combo
     */
    private void showCombo() {
        if (btn_11.isVisible()) {
            combo_11.setText(btn_11.getText());
        }
        if (btn_12.isVisible()) {
            combo_12.setText(btn_12.getText());
        }
        if (btn_13.isVisible()) {
            combo_13.setText(btn_13.getText());
        }
        if (btn_14.isVisible()) {
            combo_14.setText(btn_14.getText());
        }
        if (btn_21.isVisible()) {
            combo_21.setText(btn_21.getText());
        }
        if (btn_22.isVisible()) {
            combo_22.setText(btn_22.getText());
        }
        if (btn_23.isVisible()) {
            combo_23.setText(btn_23.getText());
        }
        if (btn_24.isVisible()) {
            combo_24.setText(btn_24.getText());
        }
        if (btn_31.isVisible()) {
            combo_31.setText(btn_31.getText());
        }
        if (btn_32.isVisible()) {
            combo_32.setText(btn_32.getText());
        }
        if (btn_33.isVisible()) {
            combo_33.setText(btn_33.getText());
        }
        if (btn_34.isVisible()) {
            combo_34.setText(btn_34.getText());
        }
        if (btn_41.isVisible()) {
            combo_41.setText(btn_41.getText());
        }
        if (btn_42.isVisible()) {
            combo_42.setText(btn_42.getText());
        }
        if (btn_43.isVisible()) {
            combo_43.setText(btn_43.getText());
        }
        if (btn_44.isVisible()) {
            combo_44.setText(btn_44.getText());
        }
        if (btn_51.isVisible()) {
            combo_51.setText(btn_51.getText());
        }
        if (btn_52.isVisible()) {
            combo_52.setText(btn_52.getText());
        }
        if (btn_53.isVisible()) {
            combo_53.setText(btn_53.getText());
        }
        if (btn_54.isVisible()) {
            combo_54.setText(btn_54.getText());
        }
        if (btn_61.isVisible()) {
            combo_61.setText(btn_61.getText());
        }
        if (btn_62.isVisible()) {
            combo_62.setText(btn_62.getText());
        }
        if (btn_63.isVisible()) {
            combo_63.setText(btn_63.getText());
        }
        if (btn_64.isVisible()) {
            combo_64.setText(btn_64.getText());
        }
        if (btn_71.isVisible()) {
            combo_71.setText(btn_71.getText());
        }
        if (btn_72.isVisible()) {
            combo_72.setText(btn_72.getText());
        }
        if (btn_73.isVisible()) {
            combo_73.setText(btn_73.getText());
        }
        if (btn_74.isVisible()) {
            combo_74.setText(btn_74.getText());
        }
        if (btn_81.isVisible()) {
            combo_81.setText(btn_81.getText());
        }
        if (btn_82.isVisible()) {
            combo_82.setText(btn_82.getText());
        }
        if (btn_83.isVisible()) {
            combo_83.setText(btn_83.getText());
        }
        if (btn_84.isVisible()) {
            combo_84.setText(btn_84.getText());
        }
        if (btn_91.isVisible()) {
            combo_91.setText(btn_91.getText());
        }
        if (btn_92.isVisible()) {
            combo_92.setText(btn_92.getText());
        }
        if (btn_93.isVisible()) {
            combo_93.setText(btn_93.getText());
        }
        if (btn_94.isVisible()) {
            combo_94.setText(btn_94.getText());
        }
        if (btn_a1.isVisible()) {
            combo_a1.setText(btn_a1.getText());
        }
        if (btn_a2.isVisible()) {
            combo_a2.setText(btn_a2.getText());
        }
        if (btn_a3.isVisible()) {
            combo_a3.setText(btn_a3.getText());
        }
        if (btn_a4.isVisible()) {
            combo_a4.setText(btn_a4.getText());
        }
        if (btn_b1.isVisible()) {
            combo_b1.setText(btn_b1.getText());
        }
        if (btn_b2.isVisible()) {
            combo_b2.setText(btn_b2.getText());
        }
        if (btn_b3.isVisible()) {
            combo_b3.setText(btn_b3.getText());
        }
        if (btn_b4.isVisible()) {
            combo_b4.setText(btn_b4.getText());
        }
        if (btn_c1.isVisible()) {
            combo_c1.setText(btn_c1.getText());
        }
        if (btn_c2.isVisible()) {
            combo_c2.setText(btn_c2.getText());
        }
        if (btn_c3.isVisible()) {
            combo_c3.setText(btn_c3.getText());
        }
        if (btn_c4.isVisible()) {
            combo_c4.setText(btn_c4.getText());
        }
        if (btn_d1.isVisible()) {
            combo_d1.setText(btn_d1.getText());
        }
        if (btn_d2.isVisible()) {
            combo_d2.setText(btn_d2.getText());
        }
        if (btn_d3.isVisible()) {
            combo_d3.setText(btn_d3.getText());
        }
        if (btn_d4.isVisible()) {
            combo_d4.setText(btn_d4.getText());
        }
        if (btn_e1.isVisible()) {
            combo_e1.setText(btn_e1.getText());
        }
        if (btn_e2.isVisible()) {
            combo_e2.setText(btn_e2.getText());
        }
        if (btn_e3.isVisible()) {
            combo_e3.setText(btn_e3.getText());
        }
        if (btn_e4.isVisible()) {
            combo_e4.setText(btn_e4.getText());
        }
        if (btn_f1.isVisible()) {
            combo_f1.setText(btn_f1.getText());
        }
        if (btn_f2.isVisible()) {
            combo_f2.setText(btn_f2.getText());
        }
        if (btn_f3.isVisible()) {
            combo_f3.setText(btn_f3.getText());
        }
        if (btn_f4.isVisible()) {
            combo_f4.setText(btn_f4.getText());
        }
        if (btn_g1.isVisible()) {
            combo_g1.setText(btn_g1.getText());
        }
        if (btn_g2.isVisible()) {
            combo_g2.setText(btn_g2.getText());
        }
        if (btn_g3.isVisible()) {
            combo_g3.setText(btn_g3.getText());
        }
        if (btn_g4.isVisible()) {
            combo_g4.setText(btn_g4.getText());
        }
        if (btn_h1.isVisible()) {
            combo_h1.setText(btn_h1.getText());
        }
        if (btn_h2.isVisible()) {
            combo_h2.setText(btn_h2.getText());
        }
        if (btn_h3.isVisible()) {
            combo_h3.setText(btn_h3.getText());
        }
        if (btn_h4.isVisible()) {
            combo_h4.setText(btn_h4.getText());
        }
        if (btn_i1.isVisible()) {
            combo_i1.setText(btn_i1.getText());
        }
        if (btn_i2.isVisible()) {
            combo_i2.setText(btn_i2.getText());
        }
        if (btn_i3.isVisible()) {
            combo_i3.setText(btn_i3.getText());
        }
        if (btn_i4.isVisible()) {
            combo_i4.setText(btn_i4.getText());
        }
        if (btn_j1.isVisible()) {
            combo_j1.setText(btn_j1.getText());
        }
        if (btn_j2.isVisible()) {
            combo_j2.setText(btn_j2.getText());
        }
        if (btn_j3.isVisible()) {
            combo_j3.setText(btn_j3.getText());
        }
        if (btn_j4.isVisible()) {
            combo_j4.setText(btn_j4.getText());
        }
        if (btn_k1.isVisible()) {
            combo_k1.setText(btn_k1.getText());
        }
        if (btn_k2.isVisible()) {
            combo_k2.setText(btn_k2.getText());
        }
        if (btn_k3.isVisible()) {
            combo_k3.setText(btn_k3.getText());
        }
        if (btn_k4.isVisible()) {
            combo_k4.setText(btn_k4.getText());
        }
        if (btn_l1.isVisible()) {
            combo_l1.setText(btn_l1.getText());
        }
        if (btn_l2.isVisible()) {
            combo_l2.setText(btn_l2.getText());
        }
        if (btn_l3.isVisible()) {
            combo_l3.setText(btn_l3.getText());
        }
        if (btn_l4.isVisible()) {
            combo_l4.setText(btn_l4.getText());
        }
    }

    /**
     * 读取keyName.txt文件，获取所有的按键及按键名
     */
    private boolean getKeys() {
        String encoding = "UTF-8";
        File keyFile = new File(Resources.KeyNamePath);
        if (!keyFile.exists()) {
            return false;
        }
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            read = new InputStreamReader(new FileInputStream(keyFile), encoding);
            bufferedReader = new BufferedReader(read);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                int index = line.indexOf("=");
                normalKeys.add(line.substring(0, index));
                normalKeysName.add(line.substring(index + 1));
            }
            // 将按键名与实际的按键英文放在HashMap中
            for (int i = 0; i < normalKeys.size(); i++) {
                //normalMap.put(normalKeysName.get(i), normalKeys.get(i));
                normalNameMap.put(normalKeys.get(i), normalKeysName.get(i));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (read != null) {
                    read.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return true;
    }

    /**
     * 设置下拉列表中的内容
     */
    private void setComboContent(ArrayList<String> keys) {
        String[] name = new String[keys.size()];
        String key = "";
        for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i);
            if (normalNameMap.get(key) != null) {
                name[i] = normalNameMap.get(key);
            } else {
                normalNameMap.put(key, key);
                normalKeys.add(key);
                //normalMap.put(key, key);
                name[i] = key;
            }
        }
        setComboList(name);
        setComboData(keys);
    }

    /**
     * 浏览文件
     */
    protected String browseFile() {
        FileDialog fdBrowse = new FileDialog(PgMIR.shell, SWT.SELECTED);
        fdBrowse.setFilterPath(Resources.propertyFilePath);
        fdBrowse.setText("遥控器配置文件选择");
        fdBrowse.setFilterExtensions(new String[] { "*" + nameSuffix });
        fdBrowse.setFilterNames(new String[] { "Properties Files(*.properties)" });
        String ucFile = fdBrowse.open();
        if (ucFile == null) {
            return "";
        }
        String filename = (new File(ucFile)).getName();
        txt_path.setText(filename.substring(0, filename.indexOf(nameSuffix)));
        return ucFile;
    }

    /**
     * 导入文件，将文件的内容保存在ArrayList
     */
    private ArrayList<String[]> importFile(String path) {
        ArrayList<String[]> keysList = new ArrayList<String[]>();
        File importFile = new File(path + nameDefineSuffix);
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    importFile), "UTF-8");
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(" ")) {
                    showMsg(importFile.getName() + "文件每行不能以空格开头！",
                            SWT.ICON_INFORMATION);
                    return null;
                }
                keysList.add(line.split("  "));
            }
            read.close();
        } catch (FileNotFoundException e) {
            keysList = null;
            showMsg("没有对应的遥控器文件！", SWT.ICON_ERROR);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return keysList;
    }

    /**
     * 将导入文件生成的ArrayList显示在页面上
     */
    private void showImportFile(ArrayList<String[]> importList) {
        if (importList == null) {
            return;
        }
        for (int i = 0; i < importList.size(); i++) {
            String key = importList.get(i)[0];
            String keyName;
            if (normalNameMap.get(key) == null) {
                normalNameMap.put(key, key);
                normalKeys.add(key);
                //normalMap.put(key, key);
                keyName = key;
            } else {
                keyName = normalNameMap.get(key);
            }
            findButton(importList.get(i)[1], importList.get(i)[2], keyName,key,
                    true);
        }
    }

    /**
     * 找到一个button,设置它
     */
    private void findButton(String i, String j, String text,String data, boolean visible) {
        if ("1".equals(i)) {
            if ("1".equals(j)) {
                btn_11.setVisible(visible);
                btn_11.setText(text);
                btn_11.setData(data);
                setButtonColor(btn_11, visible);
            } else if ("2".equals(j)) {
                btn_12.setVisible(visible);
                btn_12.setText(text);
                btn_12.setData(data);
                setButtonColor(btn_12, visible);
            } else if ("3".equals(j)) {
                btn_13.setVisible(visible);
                btn_13.setText(text);
                btn_13.setData(data);
                setButtonColor(btn_13, visible);
            } else if ("4".equals(j)) {
                btn_14.setVisible(visible);
                btn_14.setText(text);
                btn_14.setData(data);
                setButtonColor(btn_14, visible);
            }
        } else if ("2".equals(i)) {
            if ("1".equals(j)) {
                btn_21.setVisible(visible);
                btn_21.setText(text);
                btn_21.setData(data);
                setButtonColor(btn_21, visible);
            } else if ("2".equals(j)) {
                btn_22.setVisible(visible);
                btn_22.setText(text);
                btn_22.setData(data);
                setButtonColor(btn_22, visible);
            } else if ("3".equals(j)) {
                btn_23.setVisible(visible);
                btn_23.setText(text);
                btn_23.setData(data);
                setButtonColor(btn_23, visible);
            } else if ("4".equals(j)) {
                btn_24.setVisible(visible);
                btn_24.setText(text);
                btn_24.setData(data);
                setButtonColor(btn_24, visible);
            }
        } else if ("3".equals(i)) {
            if ("1".equals(j)) {
                btn_31.setVisible(visible);
                btn_31.setText(text);
                btn_31.setData(data);
                setButtonColor(btn_31, visible);
            } else if ("2".equals(j)) {
                btn_32.setVisible(visible);
                btn_32.setText(text);
                btn_32.setData(data);
                setButtonColor(btn_32, visible);
            } else if ("3".equals(j)) {
                btn_33.setVisible(visible);
                btn_33.setText(text);
                btn_33.setData(data);
                setButtonColor(btn_33, visible);
            } else if ("4".equals(j)) {
                btn_34.setVisible(visible);
                btn_34.setText(text);
                btn_34.setData(data);
                setButtonColor(btn_34, visible);
            }
        } else if ("4".equals(i)) {
            if ("1".equals(j)) {
                btn_41.setVisible(visible);
                btn_41.setText(text);
                btn_41.setData(data);
                setButtonColor(btn_41, visible);
            } else if ("2".equals(j)) {
                btn_42.setVisible(visible);
                btn_42.setText(text);
                btn_42.setData(data);
                setButtonColor(btn_42, visible);
            } else if ("3".equals(j)) {
                btn_43.setVisible(visible);
                btn_43.setText(text);
                btn_43.setData(data);
                setButtonColor(btn_43, visible);
            } else if ("4".equals(j)) {
                btn_44.setVisible(visible);
                btn_44.setText(text);
                btn_44.setData(data);
                setButtonColor(btn_44, visible);
            }
        } else if ("5".equals(i)) {
            if ("1".equals(j)) {
                btn_51.setVisible(visible);
                btn_51.setText(text);
                btn_51.setData(data);
                setButtonColor(btn_51, visible);
            } else if ("2".equals(j)) {
                btn_52.setVisible(visible);
                btn_52.setText(text);
                btn_52.setData(data);
                setButtonColor(btn_52, visible);
            } else if ("3".equals(j)) {
                btn_53.setVisible(visible);
                btn_53.setText(text);
                btn_53.setData(data);
                setButtonColor(btn_53, visible);
            } else if ("4".equals(j)) {
                btn_54.setVisible(visible);
                btn_54.setText(text);
                btn_54.setData(data);
                setButtonColor(btn_54, visible);
            }
        } else if ("6".equals(i)) {
            if ("1".equals(j)) {
                btn_61.setVisible(visible);
                btn_61.setText(text);
                btn_61.setData(data);
                setButtonColor(btn_61, visible);
            } else if ("2".equals(j)) {
                btn_62.setVisible(visible);
                btn_62.setText(text);
                btn_62.setData(data);
                setButtonColor(btn_62, visible);
            } else if ("3".equals(j)) {
                btn_63.setVisible(visible);
                btn_63.setText(text);
                btn_63.setData(data);
                setButtonColor(btn_63, visible);
            } else if ("4".equals(j)) {
                btn_64.setVisible(visible);
                btn_64.setText(text);
                btn_64.setData(data);
                setButtonColor(btn_64, visible);
            }
        } else if ("7".equals(i)) {
            if ("1".equals(j)) {
                btn_71.setVisible(visible);
                btn_71.setText(text);
                btn_71.setData(data);
                setButtonColor(btn_71, visible);
            } else if ("2".equals(j)) {
                btn_72.setVisible(visible);
                btn_72.setText(text);
                btn_72.setData(data);
                setButtonColor(btn_72, visible);
            } else if ("3".equals(j)) {
                btn_73.setVisible(visible);
                btn_73.setText(text);
                btn_73.setData(data);
                setButtonColor(btn_73, visible);
            } else if ("4".equals(j)) {
                btn_74.setVisible(visible);
                btn_74.setText(text);
                btn_74.setData(data);
                setButtonColor(btn_74, visible);
            }
        } else if ("8".equals(i)) {
            if ("1".equals(j)) {
                btn_81.setVisible(visible);
                btn_81.setText(text);
                btn_81.setData(data);
                setButtonColor(btn_81, visible);
            } else if ("2".equals(j)) {
                btn_82.setVisible(visible);
                btn_82.setText(text);
                btn_82.setData(data);
                setButtonColor(btn_82, visible);
            } else if ("3".equals(j)) {
                btn_83.setVisible(visible);
                btn_83.setText(text);
                btn_83.setData(data);
                setButtonColor(btn_83, visible);
            } else if ("4".equals(j)) {
                btn_84.setVisible(visible);
                btn_84.setText(text);
                btn_84.setData(data);
                setButtonColor(btn_84, visible);
            }
        } else if ("9".equals(i)) {
            if ("1".equals(j)) {
                btn_91.setVisible(visible);
                btn_91.setText(text);
                btn_91.setData(data);
                setButtonColor(btn_91, visible);
            } else if ("2".equals(j)) {
                btn_92.setVisible(visible);
                btn_92.setText(text);
                btn_92.setData(data);
                setButtonColor(btn_92, visible);
            } else if ("3".equals(j)) {
                btn_93.setVisible(visible);
                btn_93.setText(text);
                btn_93.setData(data);
                setButtonColor(btn_93, visible);
            } else if ("4".equals(j)) {
                btn_94.setVisible(visible);
                btn_94.setText(text);
                btn_94.setData(data);
                setButtonColor(btn_94, visible);
            }
        } else if ("a".equals(i)) {
            if ("1".equals(j)) {
                btn_a1.setVisible(visible);
                btn_a1.setText(text);
                btn_a1.setData(data);
                setButtonColor(btn_a1, visible);
            } else if ("2".equals(j)) {
                btn_a2.setVisible(visible);
                btn_a2.setText(text);
                btn_a2.setData(data);
                setButtonColor(btn_a2, visible);
            } else if ("3".equals(j)) {
                btn_a3.setVisible(visible);
                btn_a3.setText(text);
                btn_a3.setData(data);
                setButtonColor(btn_a3, visible);
            } else if ("4".equals(j)) {
                btn_a4.setVisible(visible);
                btn_a4.setText(text);
                btn_a4.setData(data);
                setButtonColor(btn_a4, visible);
            }
        } else if ("b".equals(i)) {
            if ("1".equals(j)) {
                btn_b1.setVisible(visible);
                btn_b1.setText(text);
                btn_b1.setData(data);
                setButtonColor(btn_b1, visible);
            } else if ("2".equals(j)) {
                btn_b2.setVisible(visible);
                btn_b2.setText(text);
                btn_b2.setData(data);
                setButtonColor(btn_b2, visible);
            } else if ("3".equals(j)) {
                btn_b3.setVisible(visible);
                btn_b3.setText(text);
                btn_b3.setData(data);
                setButtonColor(btn_b3, visible);
            } else if ("4".equals(j)) {
                btn_b4.setVisible(visible);
                btn_b4.setText(text);
                btn_b4.setData(data);
                setButtonColor(btn_b4, visible);
            }
        } else if ("c".equals(i)) {
            if ("1".equals(j)) {
                btn_c1.setVisible(visible);
                btn_c1.setText(text);
                btn_c1.setData(data);
                setButtonColor(btn_c1, visible);
            } else if ("2".equals(j)) {
                btn_c2.setVisible(visible);
                btn_c2.setText(text);
                btn_c2.setData(data);
                setButtonColor(btn_c2, visible);
            } else if ("3".equals(j)) {
                btn_c3.setVisible(visible);
                btn_c3.setText(text);
                btn_c3.setData(data);
                setButtonColor(btn_c3, visible);
            } else if ("4".equals(j)) {
                btn_c4.setVisible(visible);
                btn_c4.setText(text);
                btn_c4.setData(data);
                setButtonColor(btn_c4, visible);
            }
        } else if ("d".equals(i)) {
            if ("1".equals(j)) {
                btn_d1.setVisible(visible);
                btn_d1.setText(text);
                btn_d1.setData(data);
                setButtonColor(btn_d1, visible);
            } else if ("2".equals(j)) {
                btn_d2.setVisible(visible);
                btn_d2.setText(text);
                btn_d2.setData(data);
                setButtonColor(btn_d2, visible);
            } else if ("3".equals(j)) {
                btn_d3.setVisible(visible);
                btn_d3.setText(text);
                btn_d3.setData(data);
                setButtonColor(btn_d3, visible);
            } else if ("4".equals(j)) {
                btn_d4.setVisible(visible);
                btn_d4.setText(text);
                btn_d4.setData(data);
                setButtonColor(btn_d4, visible);
            }
        } else if ("e".equals(i)) {
            if ("1".equals(j)) {
                btn_e1.setVisible(visible);
                btn_e1.setText(text);
                btn_e1.setData(data);
                setButtonColor(btn_e1, visible);
            } else if ("2".equals(j)) {
                btn_e2.setVisible(visible);
                btn_e2.setText(text);
                btn_e2.setData(data);
                setButtonColor(btn_e2, visible);
            } else if ("3".equals(j)) {
                btn_e3.setVisible(visible);
                btn_e3.setText(text);
                btn_e3.setData(data);
                setButtonColor(btn_e3, visible);
            } else if ("4".equals(j)) {
                btn_e4.setVisible(visible);
                btn_e4.setText(text);
                btn_e4.setData(data);
                setButtonColor(btn_e4, visible);
            }
        } else if ("f".equals(i)) {
            if ("1".equals(j)) {
                btn_f1.setVisible(visible);
                btn_f1.setText(text);
                btn_f1.setData(data);
                setButtonColor(btn_f1, visible);
            } else if ("2".equals(j)) {
                btn_f2.setVisible(visible);
                btn_f2.setText(text);
                btn_f2.setData(data);
                setButtonColor(btn_f2, visible);
            } else if ("3".equals(j)) {
                btn_f3.setVisible(visible);
                btn_f3.setText(text);
                btn_f3.setData(data);
                setButtonColor(btn_f3, visible);
            } else if ("4".equals(j)) {
                btn_f4.setVisible(visible);
                btn_f4.setText(text);
                btn_f4.setData(data);
                setButtonColor(btn_f4, visible);
            }
        } else if ("g".equals(i)) {
            if ("1".equals(j)) {
                btn_g1.setVisible(visible);
                btn_g1.setText(text);
                btn_g1.setData(data);
                setButtonColor(btn_g1, visible);
            } else if ("2".equals(j)) {
                btn_g2.setVisible(visible);
                btn_g2.setText(text);
                btn_g2.setData(data);
                setButtonColor(btn_g2, visible);
            } else if ("3".equals(j)) {
                btn_g3.setVisible(visible);
                btn_g3.setText(text);
                btn_g3.setData(data);
                setButtonColor(btn_g3, visible);
            } else if ("4".equals(j)) {
                btn_g4.setVisible(visible);
                btn_g4.setText(text);
                btn_g4.setData(data);
                setButtonColor(btn_g4, visible);
            }
        } else if ("h".equals(i)) {
            if ("1".equals(j)) {
                btn_h1.setVisible(visible);
                btn_h1.setText(text);
                btn_h1.setData(data);
                setButtonColor(btn_h1, visible);
            } else if ("2".equals(j)) {
                btn_h2.setVisible(visible);
                btn_h2.setText(text);
                btn_h2.setData(data);
                setButtonColor(btn_h2, visible);
            } else if ("3".equals(j)) {
                btn_h3.setVisible(visible);
                btn_h3.setText(text);
                btn_h3.setData(data);
                setButtonColor(btn_h3, visible);
            } else if ("4".equals(j)) {
                btn_h4.setVisible(visible);
                btn_h4.setText(text);
                btn_h4.setData(data);
                setButtonColor(btn_h4, visible);
            }
        } else if ("i".equals(i)) {
            if ("1".equals(j)) {
                btn_i1.setVisible(visible);
                btn_i1.setText(text);
                btn_i1.setData(data);
                setButtonColor(btn_i1, visible);
            } else if ("2".equals(j)) {
                btn_i2.setVisible(visible);
                btn_i2.setText(text);
                btn_i2.setData(data);
                setButtonColor(btn_i2, visible);
            } else if ("3".equals(j)) {
                btn_i3.setVisible(visible);
                btn_i3.setText(text);
                btn_i3.setData(data);
                setButtonColor(btn_i3, visible);
            } else if ("4".equals(j)) {
                btn_i4.setVisible(visible);
                btn_i4.setText(text);
                btn_i4.setData(data);
                setButtonColor(btn_i4, visible);
            }
        } else if ("j".equals(i)) {
            if ("1".equals(j)) {
                btn_j1.setVisible(visible);
                btn_j1.setText(text);
                btn_j1.setData(data);
                setButtonColor(btn_j1, visible);
            } else if ("2".equals(j)) {
                btn_j2.setVisible(visible);
                btn_j2.setText(text);
                btn_j2.setData(data);
                setButtonColor(btn_j2, visible);
            } else if ("3".equals(j)) {
                btn_j3.setVisible(visible);
                btn_j3.setText(text);
                btn_j3.setData(data);
                setButtonColor(btn_j3, visible);
            } else if ("4".equals(j)) {
                btn_j4.setVisible(visible);
                btn_j4.setText(text);
                btn_j4.setData(data);
                setButtonColor(btn_j4, visible);
            }
        } else if ("k".equals(i)) {
            if ("1".equals(j)) {
                btn_k1.setVisible(visible);
                btn_k1.setText(text);
                btn_k1.setData(data);
                setButtonColor(btn_k1, visible);
            } else if ("2".equals(j)) {
                btn_k2.setVisible(visible);
                btn_k2.setText(text);
                btn_k2.setData(data);
                setButtonColor(btn_k2, visible);
            } else if ("3".equals(j)) {
                btn_k3.setVisible(visible);
                btn_k3.setText(text);
                btn_k3.setData(data);
                setButtonColor(btn_k3, visible);
            } else if ("4".equals(j)) {
                btn_k4.setVisible(visible);
                btn_k4.setText(text);
                btn_k4.setData(data);
                setButtonColor(btn_k4, visible);
            }
        } else if ("l".equals(i)) {
            if ("1".equals(j)) {
                btn_l1.setVisible(visible);
                btn_l1.setText(text);
                btn_l1.setData(data);
                setButtonColor(btn_l1, visible);
            } else if ("2".equals(j)) {
                btn_l2.setVisible(visible);
                btn_l2.setText(text);
                btn_l2.setData(data);
                setButtonColor(btn_l2, visible);
            } else if ("3".equals(j)) {
                btn_l3.setVisible(visible);
                btn_l3.setText(text);
                btn_l3.setData(data);
                setButtonColor(btn_l3, visible);
            } else if ("4".equals(j)) {
                btn_l4.setVisible(visible);
                btn_l4.setText(text);
                btn_l4.setData(data);
                setButtonColor(btn_l4, visible);
            }
        }
    }

    /**
     * 输入保存文件的文件名
     */
    protected void saveFile() {

        final Shell shell_name = new Shell(PgMIR.shell);
        shell_name.setSize(330, 210);
        shell_name.setText("保存");
        shell_name.open();
        Label lbl_title = new Label(shell_name, SWT.NONE);
        lbl_title.setText("文件名称：");
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
                fileName = inputText.getText();
                if ("".equals(fileName)) {
                    showMsg("文件名称不能为空！", SWT.ICON_INFORMATION);
                    return;
                }
                File file = new File(Resources.propertyFilePath + "\\"
                        + fileName + ".txt");
                if (file.exists()) {
                    if (!showAskMsg("文件已存在，是否替换？", SWT.ICON_INFORMATION)) {
                        return;
                    }
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

    /**
     * 将界面上的combo隐藏，显示button
     */
    private void comboToButton() {
        setEditPageVisible(false);
        cleanButtonsColor();
        showButton(btn_11, combo_11);
        showButton(btn_12, combo_12);
        showButton(btn_13, combo_13);
        showButton(btn_14, combo_14);
        showButton(btn_21, combo_21);
        showButton(btn_22, combo_22);
        showButton(btn_23, combo_23);
        showButton(btn_24, combo_24);
        showButton(btn_31, combo_31);
        showButton(btn_32, combo_32);
        showButton(btn_33, combo_33);
        showButton(btn_34, combo_34);
        showButton(btn_41, combo_41);
        showButton(btn_42, combo_42);
        showButton(btn_43, combo_43);
        showButton(btn_44, combo_44);
        showButton(btn_51, combo_51);
        showButton(btn_52, combo_52);
        showButton(btn_53, combo_53);
        showButton(btn_54, combo_54);
        showButton(btn_61, combo_61);
        showButton(btn_62, combo_62);
        showButton(btn_63, combo_63);
        showButton(btn_64, combo_64);
        showButton(btn_71, combo_71);
        showButton(btn_72, combo_72);
        showButton(btn_73, combo_73);
        showButton(btn_74, combo_74);
        showButton(btn_81, combo_81);
        showButton(btn_82, combo_82);
        showButton(btn_83, combo_83);
        showButton(btn_84, combo_84);
        showButton(btn_91, combo_91);
        showButton(btn_92, combo_92);
        showButton(btn_93, combo_93);
        showButton(btn_94, combo_94);
        showButton(btn_a1, combo_a1);
        showButton(btn_a2, combo_a2);
        showButton(btn_a3, combo_a3);
        showButton(btn_a4, combo_a4);
        showButton(btn_b1, combo_b1);
        showButton(btn_b2, combo_b2);
        showButton(btn_b3, combo_b3);
        showButton(btn_b4, combo_b4);
        showButton(btn_c1, combo_c1);
        showButton(btn_c2, combo_c2);
        showButton(btn_c3, combo_c3);
        showButton(btn_c4, combo_c4);
        showButton(btn_d1, combo_d1);
        showButton(btn_d2, combo_d2);
        showButton(btn_d3, combo_d3);
        showButton(btn_d4, combo_d4);
        showButton(btn_e1, combo_e1);
        showButton(btn_e2, combo_e2);
        showButton(btn_e3, combo_e3);
        showButton(btn_e4, combo_e4);
        showButton(btn_f1, combo_f1);
        showButton(btn_f2, combo_f2);
        showButton(btn_f3, combo_f3);
        showButton(btn_f4, combo_f4);
        showButton(btn_g1, combo_g1);
        showButton(btn_g2, combo_g2);
        showButton(btn_g3, combo_g3);
        showButton(btn_g4, combo_g4);
        showButton(btn_h1, combo_h1);
        showButton(btn_h2, combo_h2);
        showButton(btn_h3, combo_h3);
        showButton(btn_h4, combo_h4);
        showButton(btn_i1, combo_i1);
        showButton(btn_i2, combo_i2);
        showButton(btn_i3, combo_i3);
        showButton(btn_i4, combo_i4);
        showButton(btn_j1, combo_j1);
        showButton(btn_j2, combo_j2);
        showButton(btn_j3, combo_j3);
        showButton(btn_j4, combo_j4);
        showButton(btn_k1, combo_k1);
        showButton(btn_k2, combo_k2);
        showButton(btn_k3, combo_k3);
        showButton(btn_k4, combo_k4);
        showButton(btn_l1, combo_l1);
        showButton(btn_l2, combo_l2);
        showButton(btn_l3, combo_l3);
        showButton(btn_l4, combo_l4);

    }

    /**
     * 显示button
     */
    private void showButton(Button button, Combo combo) {
        button.setVisible(false);
        if (!"".equals(combo.getText())) {
            button.setVisible(true);
            button.setText(combo.getText());
            button.setData(combo.getData("data"));
            setButtonColor(button, true);
        }
    }

    /**
     * 获取所有combo的内容，以及每个的位置信息,存放在字符串中
     */
    private boolean getAllComboDetail() {
        outputString = "";
        if (!getCombo(combo_11, String.valueOf(1), 1 )) {
            return false;
        }
        if (!getCombo(combo_12, String.valueOf(1), 2 )) {
            return false;
        }
        if (!getCombo(combo_13, String.valueOf(1), 3 )) {
            return false;
        }
        if (!getCombo(combo_14, String.valueOf(1), 4 )) {
            return false;
        }
        if (!getCombo(combo_21, String.valueOf(2), 1 )) {
            return false;
        }
        if (!getCombo(combo_22, String.valueOf(2), 2 )) {
            return false;
        }
        if (!getCombo(combo_23, String.valueOf(2), 3 )) {
            return false;
        }
        if (!getCombo(combo_24, String.valueOf(2), 4 )) {
            return false;
        }
        if (!getCombo(combo_31, String.valueOf(3), 1 )) {
            return false;
        }
        if (!getCombo(combo_32, String.valueOf(3), 2 )) {
            return false;
        }
        if (!getCombo(combo_33, String.valueOf(3), 3 )) {
            return false;
        }
        if (!getCombo(combo_34, String.valueOf(3), 4 )) {
            return false;
        }
        if (!getCombo(combo_41, String.valueOf(4), 1 )) {
            return false;
        }
        if (!getCombo(combo_42, String.valueOf(4), 2 )) {
            return false;
        }
        if (!getCombo(combo_43, String.valueOf(4), 3 )) {
            return false;
        }
        if (!getCombo(combo_44, String.valueOf(4), 4 )) {
            return false;
        }
        if (!getCombo(combo_51, String.valueOf(5), 1 )) {
            return false;
        }
        if (!getCombo(combo_52, String.valueOf(5), 2 )) {
            return false;
        }
        if (!getCombo(combo_53, String.valueOf(5), 3 )) {
            return false;
        }
        if (!getCombo(combo_54, String.valueOf(5), 4 )) {
            return false;
        }
        if (!getCombo(combo_61, String.valueOf(6), 1 )) {
            return false;
        }
        if (!getCombo(combo_62, String.valueOf(6), 2 )) {
            return false;
        }
        if (!getCombo(combo_63, String.valueOf(6), 3 )) {
            return false;
        }
        if (!getCombo(combo_64, String.valueOf(6), 4 )) {
            return false;
        }
        if (!getCombo(combo_71, String.valueOf(7), 1 )) {
            return false;
        }
        if (!getCombo(combo_72, String.valueOf(7), 2 )) {
            return false;
        }
        if (!getCombo(combo_73, String.valueOf(7), 3 )) {
            return false;
        }
        if (!getCombo(combo_74, String.valueOf(7), 4 )) {
            return false;
        }
        if (!getCombo(combo_81, String.valueOf(8), 1 )) {
            return false;
        }
        if (!getCombo(combo_82, String.valueOf(8), 2 )) {
            return false;
        }
        if (!getCombo(combo_83, String.valueOf(8), 3 )) {
            return false;
        }
        if (!getCombo(combo_84, String.valueOf(8), 4 )) {
            return false;
        }
        if (!getCombo(combo_91, String.valueOf(9), 1 )) {
            return false;
        }
        if (!getCombo(combo_92, String.valueOf(9), 2 )) {
            return false;
        }
        if (!getCombo(combo_93, String.valueOf(9), 3 )) {
            return false;
        }
        if (!getCombo(combo_94, String.valueOf(9), 4 )) {
            return false;
        }
        if (!getCombo(combo_a1, "a", 1 )) {
            return false;
        }
        if (!getCombo(combo_a2, "a", 2 )) {
            return false;
        }
        if (!getCombo(combo_a3, "a", 3 )) {
            return false;
        }
        if (!getCombo(combo_a4, "a", 4 )) {
            return false;
        }
        if (!getCombo(combo_b1, "b", 1 )) {
            return false;
        }
        if (!getCombo(combo_b2, "b", 2 )) {
            return false;
        }
        if (!getCombo(combo_b3, "b", 3 )) {
            return false;
        }
        if (!getCombo(combo_b4, "b", 4 )) {
            return false;
        }
        if (!getCombo(combo_c1, "c", 1 )) {
            return false;
        }
        if (!getCombo(combo_c2, "c", 2 )) {
            return false;
        }
        if (!getCombo(combo_c3, "c", 3 )) {
            return false;
        }
        if (!getCombo(combo_c4, "c", 4 )) {
            return false;
        }
        if (!getCombo(combo_d1, "d", 1 )) {
            return false;
        }
        if (!getCombo(combo_d2, "d", 2 )) {
            return false;
        }
        if (!getCombo(combo_d3, "d", 3 )) {
            return false;
        }
        if (!getCombo(combo_d4, "d", 4 )) {
            return false;
        }
        if (!getCombo(combo_e1, "e", 1 )) {
            return false;
        }
        if (!getCombo(combo_e2, "e", 2 )) {
            return false;
        }
        if (!getCombo(combo_e3, "e", 3 )) {
            return false;
        }
        if (!getCombo(combo_e4, "e", 4 )) {
            return false;
        }
        if (!getCombo(combo_f1, "f", 1 )) {
            return false;
        }
        if (!getCombo(combo_f2, "f", 2 )) {
            return false;
        }
        if (!getCombo(combo_f3, "f", 3 )) {
            return false;
        }
        if (!getCombo(combo_f4, "f", 4 )) {
            return false;
        }
        if (!getCombo(combo_g1, "g", 1 )) {
            return false;
        }
        if (!getCombo(combo_g2, "g", 2 )) {
            return false;
        }
        if (!getCombo(combo_g3, "g", 3 )) {
            return false;
        }
        if (!getCombo(combo_g4, "g", 4 )) {
            return false;
        }
        if (!getCombo(combo_h1, "h", 1 )) {
            return false;
        }
        if (!getCombo(combo_h2, "h", 2 )) {
            return false;
        }
        if (!getCombo(combo_h3, "h", 3 )) {
            return false;
        }
        if (!getCombo(combo_h4, "h", 4 )) {
            return false;
        }
        if (!getCombo(combo_i1, "i", 1 )) {
            return false;
        }
        if (!getCombo(combo_i2, "i", 2 )) {
            return false;
        }
        if (!getCombo(combo_i3, "i", 3 )) {
            return false;
        }
        if (!getCombo(combo_i4, "i", 4 )) {
            return false;
        }
        if (!getCombo(combo_j1, "j", 1 )) {
            return false;
        }
        if (!getCombo(combo_j2, "j", 2 )) {
            return false;
        }
        if (!getCombo(combo_j3, "j", 3 )) {
            return false;
        }
        if (!getCombo(combo_j4, "j", 4 )) {
            return false;
        }
        if (!getCombo(combo_k1, "k", 1 )) {
            return false;
        }
        if (!getCombo(combo_k2, "k", 2 )) {
            return false;
        }
        if (!getCombo(combo_k3, "k", 3 )) {
            return false;
        }
        if (!getCombo(combo_k4, "k", 4 )) {
            return false;
        }
        if (!getCombo(combo_l1, "l", 1 )) {
            return false;
        }
        if (!getCombo(combo_l2, "l", 2 )) {
            return false;
        }
        if (!getCombo(combo_l3, "l", 3 )) {
            return false;
        }
        if (!getCombo(combo_l4, "l", 4 )) {
            return false;
        }
        if ("".equals(outputString)) {
            showMsg("保存失败！ \n没有编辑遥控器按键位置", SWT.ICON_ERROR);
            return false;
        }
        return true;
    }

    /**
     * 获取单个combo的信息
     */
    private boolean getCombo(Combo combo, String i, int j) {
        String info = getComboInfo(combo);
        if (info == null) {
            showMsg("保存失败！ \n列表中没有【" + combo.getText() + "】", SWT.ICON_ERROR);
            return false;
        } else if (!"".equals(info)) {
            outputString += info + "  " + i + "  " + j + "\n";
        }
        return true;
    }

    /**
     * 获取每个combo的信息
     */
    @SuppressWarnings("unchecked")
    private String getComboInfo(Combo combo) {
        if ("".equals(combo.getText())) {
            return "";
        }
        boolean isexist = false;
        String line = "";
        String option = combo.getText();
        String[] options = combo.getItems();
        String str="";
        //检查combo中的选项在列表中存在
        for(int i=0;i<options.length;i++){
            str=options[i];
            if(str.equals(option)){
                isexist = true;
                combo.setData("data",((ArrayList<String>)combo.getData()).get(i));
                break;
            }
        }
        if (!isexist) {
            return null;
        }
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).contains((String)combo.getData("data"))) {
                line = keys.get(i);
                break;
            }
        }
        return line;
    }

    /**
     * 创建所有的按键
     * 
     * @param composite2
     */
    private void createButtons(Composite composite) {
        btn_11 = new Button(composite, SWT.NONE);
        btn_11.setBounds(coordx_1, 10, width, 25);
        btn_11.setText("");
        addButtonListener(btn_11);

        btn_12 = new Button(composite, SWT.NONE);
        btn_12.setText("");
        btn_12.setBounds(coordx_2, 10, width, 25);
        addButtonListener(btn_12);

        btn_13 = new Button(composite, SWT.NONE);
        btn_13.setText("");
        btn_13.setBounds(coordx_3, 10, 77, 25);
        addButtonListener(btn_13);

        btn_14 = new Button(composite, SWT.NONE);
        btn_14.setText("");
        btn_14.setBounds(coordx_4, 10, width, 25);
        addButtonListener(btn_14);

        btn_21 = new Button(composite, SWT.NONE);
        btn_21.setText("");
        btn_21.setBounds(coordx_1, 41, width, 25);
        addButtonListener(btn_21);

        btn_22 = new Button(composite, SWT.NONE);
        btn_22.setText("");
        btn_22.setBounds(coordx_2, 41, width, 25);
        addButtonListener(btn_22);

        btn_23 = new Button(composite, SWT.NONE);
        btn_23.setText("");
        btn_23.setBounds(coordx_3, 41, width, 25);
        addButtonListener(btn_23);

        btn_24 = new Button(composite, SWT.NONE);
        btn_24.setText("");
        btn_24.setBounds(coordx_4, 41, width, 25);
        addButtonListener(btn_24);

        btn_31 = new Button(composite, SWT.NONE);
        btn_31.setText("");
        btn_31.setBounds(coordx_1, 72, width, 25);
        addButtonListener(btn_31);

        btn_32 = new Button(composite, SWT.NONE);
        btn_32.setText("");
        btn_32.setBounds(coordx_2, 72, width, 25);
        addButtonListener(btn_32);

        btn_33 = new Button(composite, SWT.NONE);
        btn_33.setText("");
        btn_33.setBounds(coordx_3, 72, width, 25);
        addButtonListener(btn_33);

        btn_34 = new Button(composite, SWT.NONE);
        btn_34.setText("");
        btn_34.setBounds(coordx_4, 72, width, 25);
        addButtonListener(btn_34);

        btn_41 = new Button(composite, SWT.NONE);
        btn_41.setText("");
        btn_41.setBounds(coordx_1, 103, width, 25);
        addButtonListener(btn_41);

        btn_42 = new Button(composite, SWT.NONE);
        btn_42.setText("");
        btn_42.setBounds(coordx_2, 103, width, 25);
        addButtonListener(btn_42);

        btn_43 = new Button(composite, SWT.NONE);
        btn_43.setText("");
        btn_43.setBounds(coordx_3, 103, width, 25);
        addButtonListener(btn_43);

        btn_44 = new Button(composite, SWT.NONE);
        btn_44.setText("");
        btn_44.setBounds(coordx_4, 103, width, 25);
        addButtonListener(btn_44);

        btn_51 = new Button(composite, SWT.NONE);
        btn_51.setText("");
        btn_51.setBounds(coordx_1, 134, width, 25);
        addButtonListener(btn_51);

        btn_52 = new Button(composite, SWT.NONE);
        btn_52.setText("");
        btn_52.setBounds(coordx_2, 134, width, 25);
        addButtonListener(btn_52);

        btn_53 = new Button(composite, SWT.NONE);
        btn_53.setText("");
        btn_53.setBounds(coordx_3, 134, width, 25);
        addButtonListener(btn_53);

        btn_54 = new Button(composite, SWT.NONE);
        btn_54.setText("");
        btn_54.setBounds(coordx_4, 134, width, 25);
        addButtonListener(btn_54);

        btn_61 = new Button(composite, SWT.NONE);
        btn_61.setText("");
        btn_61.setBounds(2, 165, width, 25);
        addButtonListener(btn_61);

        btn_62 = new Button(composite, SWT.NONE);
        btn_62.setText("");
        btn_62.setBounds(87, 165, width, 25);
        addButtonListener(btn_62);

        btn_63 = new Button(composite, SWT.NONE);
        btn_63.setText("");
        btn_63.setBounds(172, 165, width, 25);
        addButtonListener(btn_63);

        btn_64 = new Button(composite, SWT.NONE);
        btn_64.setText("");
        btn_64.setBounds(257, 165, width, 25);
        addButtonListener(btn_64);

        btn_71 = new Button(composite, SWT.NONE);
        btn_71.setText("");
        btn_71.setBounds(coordx_1, 196, width, 25);
        addButtonListener(btn_71);

        btn_72 = new Button(composite, SWT.NONE);
        btn_72.setText("");
        btn_72.setBounds(coordx_2, 196, width, 25);
        addButtonListener(btn_72);

        btn_73 = new Button(composite, SWT.NONE);
        btn_73.setText("");
        btn_73.setBounds(coordx_3, 196, width, 25);
        addButtonListener(btn_73);

        btn_74 = new Button(composite, SWT.NONE);
        btn_74.setText("");
        btn_74.setBounds(coordx_4, 196, width, 25);
        addButtonListener(btn_74);

        btn_81 = new Button(composite, SWT.NONE);
        btn_81.setText("");
        btn_81.setBounds(coordx_1, 227, width, 25);
        addButtonListener(btn_81);

        btn_82 = new Button(composite, SWT.NONE);
        btn_82.setText("");
        btn_82.setBounds(coordx_2, 227, width, 25);
        addButtonListener(btn_82);

        btn_83 = new Button(composite, SWT.NONE);
        btn_83.setText("");
        btn_83.setBounds(coordx_3, 227, width, 25);
        addButtonListener(btn_83);

        btn_84 = new Button(composite, SWT.NONE);
        btn_84.setText("");
        btn_84.setBounds(coordx_4, 227, width, 25);
        addButtonListener(btn_84);

        btn_91 = new Button(composite, SWT.NONE);
        btn_91.setText("");
        btn_91.setBounds(coordx_1, 258, width, 25);
        addButtonListener(btn_91);

        btn_92 = new Button(composite, SWT.NONE);
        btn_92.setText("");
        btn_92.setBounds(coordx_2, 258, width, 25);
        addButtonListener(btn_92);

        btn_93 = new Button(composite, SWT.NONE);
        btn_93.setText("");
        btn_93.setBounds(coordx_3, 258, width, 25);
        addButtonListener(btn_93);

        btn_94 = new Button(composite, SWT.NONE);
        btn_94.setText("");
        btn_94.setBounds(coordx_4, 258, width, 25);
        addButtonListener(btn_94);

        btn_a1 = new Button(composite, SWT.NONE);
        btn_a1.setText("");
        btn_a1.setBounds(coordx_1, 289, width, 25);
        addButtonListener(btn_a1);

        btn_a2 = new Button(composite, SWT.NONE);
        btn_a2.setText("");
        btn_a2.setBounds(coordx_2, 289, width, 25);
        addButtonListener(btn_a2);

        btn_a3 = new Button(composite, SWT.NONE);
        btn_a3.setText("");
        btn_a3.setBounds(coordx_3, 289, width, 25);
        addButtonListener(btn_a3);

        btn_a4 = new Button(composite, SWT.NONE);
        btn_a4.setText("");
        btn_a4.setBounds(coordx_4, 289, width, 25);
        addButtonListener(btn_a4);

        btn_b1 = new Button(composite, SWT.NONE);
        btn_b1.setText("");
        btn_b1.setBounds(coordx_1, 320, width, 25);
        addButtonListener(btn_b1);

        btn_b2 = new Button(composite, SWT.NONE);
        btn_b2.setText("");
        btn_b2.setBounds(coordx_2, 320, width, 25);
        addButtonListener(btn_b2);

        btn_b3 = new Button(composite, SWT.NONE);
        btn_b3.setText("");
        btn_b3.setBounds(coordx_3, 320, width, 25);
        addButtonListener(btn_b3);

        btn_e2 = new Button(composite, SWT.NONE);
        btn_e2.setText("");
        btn_e2.setBounds(coordx_2, 413, width, 25);
        addButtonListener(btn_e2);

        btn_b4 = new Button(composite, SWT.NONE);
        btn_b4.setText("");
        btn_b4.setBounds(coordx_4, 320, width, 25);
        addButtonListener(btn_b4);

        btn_c1 = new Button(composite, SWT.NONE);
        btn_c1.setText("");
        btn_c1.setBounds(coordx_1, 351, width, 25);
        addButtonListener(btn_c1);

        btn_c2 = new Button(composite, SWT.NONE);
        btn_c2.setText("");
        btn_c2.setBounds(coordx_2, 351, width, 25);
        addButtonListener(btn_c2);

        btn_c3 = new Button(composite, SWT.NONE);
        btn_c3.setText("");
        btn_c3.setBounds(coordx_3, 351, width, 25);
        addButtonListener(btn_c3);

        btn_c4 = new Button(composite, SWT.NONE);
        btn_c4.setText("");
        btn_c4.setBounds(coordx_4, 351, width, 25);
        addButtonListener(btn_c4);

        btn_d1 = new Button(composite, SWT.NONE);
        btn_d1.setText("");
        btn_d1.setBounds(coordx_1, 382, width, 25);
        addButtonListener(btn_d1);

        btn_d2 = new Button(composite, SWT.NONE);
        btn_d2.setText("");
        btn_d2.setBounds(coordx_2, 382, width, 25);
        addButtonListener(btn_d2);

        btn_d3 = new Button(composite, SWT.NONE);
        btn_d3.setText("");
        btn_d3.setBounds(coordx_3, 382, width, 25);
        addButtonListener(btn_d3);

        btn_d4 = new Button(composite, SWT.NONE);
        btn_d4.setText("");
        btn_d4.setBounds(coordx_4, 382, width, 25);
        addButtonListener(btn_d4);

        btn_e1 = new Button(composite, SWT.NONE);
        btn_e1.setText("");
        btn_e1.setBounds(coordx_1, 413, width, 25);
        addButtonListener(btn_e1);

        btn_e3 = new Button(composite, SWT.NONE);
        btn_e3.setText("");
        btn_e3.setBounds(coordx_3, 413, width, 25);
        addButtonListener(btn_e3);

        btn_e4 = new Button(composite, SWT.NONE);
        btn_e4.setText("");
        btn_e4.setBounds(coordx_4, 413, width, 25);
        addButtonListener(btn_e4);

        btn_f1 = new Button(composite, SWT.NONE);
        btn_f1.setText("");
        btn_f1.setBounds(coordx_1, 444, width, 25);
        addButtonListener(btn_f1);

        btn_f2 = new Button(composite, SWT.NONE);
        btn_f2.setText("");
        btn_f2.setBounds(coordx_2, 444, width, 25);
        addButtonListener(btn_f2);

        btn_f3 = new Button(composite, SWT.NONE);
        btn_f3.setText("");
        btn_f3.setBounds(coordx_3, 444, width, 25);
        addButtonListener(btn_f3);

        btn_f4 = new Button(composite, SWT.NONE);
        btn_f4.setText("");
        btn_f4.setBounds(coordx_4, 444, width, 25);
        addButtonListener(btn_f4);

        btn_g1 = new Button(composite, SWT.NONE);
        btn_g1.setText("");
        btn_g1.setBounds(coordx_1, 475, width, 25);
        addButtonListener(btn_g1);

        btn_g2 = new Button(composite, SWT.NONE);
        btn_g2.setText("");
        btn_g2.setBounds(coordx_2, 475, width, 25);
        addButtonListener(btn_g2);

        btn_g3 = new Button(composite, SWT.NONE);
        btn_g3.setText("");
        btn_g3.setBounds(coordx_3, 475, width, 25);
        addButtonListener(btn_g3);

        btn_g4 = new Button(composite, SWT.NONE);
        btn_g4.setText("");
        btn_g4.setBounds(coordx_4, 475, width, 25);
        addButtonListener(btn_g4);

        btn_h1 = new Button(composite, SWT.NONE);
        btn_h1.setText("");
        btn_h1.setBounds(coordx_1, 506, width, 25);
        addButtonListener(btn_h1);

        btn_h2 = new Button(composite, SWT.NONE);
        btn_h2.setText("");
        btn_h2.setBounds(coordx_2, 506, width, 25);
        addButtonListener(btn_h2);

        btn_h3 = new Button(composite, SWT.NONE);
        btn_h3.setText("");
        btn_h3.setBounds(coordx_3, 506, width, 25);
        addButtonListener(btn_h3);

        btn_h4 = new Button(composite, SWT.NONE);
        btn_h4.setText("");
        btn_h4.setBounds(coordx_4, 506, width, 25);
        addButtonListener(btn_h4);

        btn_i1 = new Button(composite, SWT.NONE);
        btn_i1.setText("");
        btn_i1.setBounds(coordx_1, 537, width, 25);
        addButtonListener(btn_i1);

        btn_i2 = new Button(composite, SWT.NONE);
        btn_i2.setText("");
        btn_i2.setBounds(coordx_2, 537, width, 25);
        addButtonListener(btn_i2);

        btn_i3 = new Button(composite, SWT.NONE);
        btn_i3.setText("");
        btn_i3.setBounds(coordx_3, 537, width, 25);
        addButtonListener(btn_i3);

        btn_i4 = new Button(composite, SWT.NONE);
        btn_i4.setText("");
        btn_i4.setBounds(coordx_4, 537, width, 25);
        addButtonListener(btn_i4);

        btn_j1 = new Button(composite, SWT.NONE);
        btn_j1.setText("");
        btn_j1.setBounds(coordx_1, 568, width, 25);
        addButtonListener(btn_j1);

        btn_j2 = new Button(composite, SWT.NONE);
        btn_j2.setText("");
        btn_j2.setBounds(coordx_2, 568, width, 25);
        addButtonListener(btn_j2);

        btn_j3 = new Button(composite, SWT.NONE);
        btn_j3.setText("");
        btn_j3.setBounds(coordx_3, 568, width, 25);
        addButtonListener(btn_j3);

        btn_j4 = new Button(composite, SWT.NONE);
        btn_j4.setText("");
        btn_j4.setBounds(coordx_4, 568, width, 25);
        addButtonListener(btn_j4);

        btn_k1 = new Button(composite, SWT.NONE);
        btn_k1.setText("");
        btn_k1.setBounds(coordx_1, 599, width, 25);
        addButtonListener(btn_k1);

        btn_k2 = new Button(composite, SWT.NONE);
        btn_k2.setText("");
        btn_k2.setBounds(coordx_2, 599, width, 25);
        addButtonListener(btn_k2);

        btn_k3 = new Button(composite, SWT.NONE);
        btn_k3.setText("");
        btn_k3.setBounds(coordx_3, 599, width, 25);
        addButtonListener(btn_k3);

        btn_k4 = new Button(composite, SWT.NONE);
        btn_k4.setText("");
        btn_k4.setBounds(coordx_4, 599, width, 25);
        addButtonListener(btn_k4);

        btn_l1 = new Button(composite, SWT.NONE);
        btn_l1.setText("");
        btn_l1.setBounds(coordx_1, 630, width, 25);
        addButtonListener(btn_l1);

        btn_l2 = new Button(composite, SWT.NONE);
        btn_l2.setText("");
        btn_l2.setBounds(coordx_2, 630, width, 25);
        addButtonListener(btn_l2);

        btn_l3 = new Button(composite, SWT.NONE);
        btn_l3.setText("");
        btn_l3.setBounds(coordx_3, 630, width, 25);
        addButtonListener(btn_l3);

        btn_l4 = new Button(composite, SWT.NONE);
        btn_l4.setText("");
        btn_l4.setBounds(coordx_4, 630, width, 25);
        addButtonListener(btn_l4);

    }

    /**
     * 为Button添加监听
     */
    private void addButtonListener(final Button btn) {
        btn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                String text = (String) btn.getData();
                if (!keys.contains(text)) {
                    showMsg("配置文件中没有【" + btn.getText() + "】的定义", SWT.ICON_ERROR);
                    return;
                }
                String strFixedInt = "";
                if (PgMIR.rdoFixedInt.getSelection()) {
                    strFixedInt = PgMIR.txtFixedInt.getText().trim();
                    strFixedInt = String.format("%.1f",
                            Float.parseFloat(strFixedInt));
                    PgMIR.txtFixedInt.setText(strFixedInt);
                }
                String propKey = "";
                if (isSX6) {
                    String txt = txt_path.getText();
                    propKey = txt.substring(0, txt.indexOf("(")) + "_(_离散码_)_"
                            + text;
                } else {
                    propKey = txt_path.getText() + "_"
                            + text;
                }
                int encode = 0;
                if (PgMIR.rdoMEncodeNEC.getSelection()) {
                    encode = Resources.ENCODE_NEC;
                } else if (PgMIR.rdoMEncodeRC5.getSelection()) {
                    encode = Resources.ENCODE_RC5;
                } else if (PgMIR.rdoMKeycode.getSelection()) {
                    encode = Resources.ENCODE_KEYCODE;
                }
                String strMsg = SmartAuto.recordKeyEvent(PgMIR.tblMScript,
                        PgMIR.comboMDevices.getText(), strFixedInt, encode,
                        propKey, btn.getText());
                if (strMsg != null && !"".equals(strMsg)) {
                    showMsg(strMsg, SWT.ICON_ERROR);
                }
            }

        });
    }

    /**
     * 设置所有按钮可见性
     */
    private void setAllButtonVisible(boolean visible) {
        btn_11.setVisible(visible);
        btn_12.setVisible(visible);
        btn_13.setVisible(visible);
        btn_14.setVisible(visible);
        btn_21.setVisible(visible);
        btn_22.setVisible(visible);
        btn_23.setVisible(visible);
        btn_24.setVisible(visible);
        btn_31.setVisible(visible);
        btn_32.setVisible(visible);
        btn_33.setVisible(visible);
        btn_34.setVisible(visible);
        btn_41.setVisible(visible);
        btn_42.setVisible(visible);
        btn_43.setVisible(visible);
        btn_44.setVisible(visible);
        btn_51.setVisible(visible);
        btn_52.setVisible(visible);
        btn_53.setVisible(visible);
        btn_54.setVisible(visible);
        btn_61.setVisible(visible);
        btn_62.setVisible(visible);
        btn_63.setVisible(visible);
        btn_64.setVisible(visible);
        btn_71.setVisible(visible);
        btn_72.setVisible(visible);
        btn_73.setVisible(visible);
        btn_74.setVisible(visible);
        btn_81.setVisible(visible);
        btn_82.setVisible(visible);
        btn_83.setVisible(visible);
        btn_84.setVisible(visible);
        btn_91.setVisible(visible);
        btn_92.setVisible(visible);
        btn_93.setVisible(visible);
        btn_94.setVisible(visible);
        btn_a1.setVisible(visible);
        btn_a2.setVisible(visible);
        btn_a3.setVisible(visible);
        btn_a4.setVisible(visible);
        btn_b1.setVisible(visible);
        btn_b2.setVisible(visible);
        btn_b3.setVisible(visible);
        btn_b4.setVisible(visible);
        btn_c1.setVisible(visible);
        btn_c2.setVisible(visible);
        btn_c3.setVisible(visible);
        btn_c4.setVisible(visible);
        btn_d1.setVisible(visible);
        btn_d2.setVisible(visible);
        btn_d3.setVisible(visible);
        btn_d4.setVisible(visible);
        btn_e1.setVisible(visible);
        btn_e2.setVisible(visible);
        btn_e3.setVisible(visible);
        btn_e4.setVisible(visible);
        btn_f1.setVisible(visible);
        btn_f2.setVisible(visible);
        btn_f3.setVisible(visible);
        btn_f4.setVisible(visible);
        btn_g1.setVisible(visible);
        btn_g2.setVisible(visible);
        btn_g3.setVisible(visible);
        btn_g4.setVisible(visible);
        btn_h1.setVisible(visible);
        btn_h2.setVisible(visible);
        btn_h3.setVisible(visible);
        btn_h4.setVisible(visible);
        btn_i1.setVisible(visible);
        btn_i2.setVisible(visible);
        btn_i3.setVisible(visible);
        btn_i4.setVisible(visible);
        btn_j1.setVisible(visible);
        btn_j2.setVisible(visible);
        btn_j3.setVisible(visible);
        btn_j4.setVisible(visible);
        btn_k1.setVisible(visible);
        btn_k2.setVisible(visible);
        btn_k3.setVisible(visible);
        btn_k4.setVisible(visible);
        btn_l1.setVisible(visible);
        btn_l2.setVisible(visible);
        btn_l3.setVisible(visible);
        btn_l4.setVisible(visible);
    }

    /**
     * 清楚Button的底色
     */
    private void cleanButtonsColor() {
        btn_11.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_12.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_13.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_14.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_21.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_22.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_23.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_24.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_31.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_32.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_33.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_34.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_41.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_42.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_43.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_44.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_51.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_52.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_53.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_54.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_61.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_62.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_63.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_64.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_71.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_72.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_73.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_74.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_81.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_82.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_83.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_84.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_91.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_92.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_93.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_94.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_a1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_a2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_a3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_a4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_b1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_b2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_b3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_b4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_c1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_c2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_c3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_c4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_d1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_d2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_d3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_d4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_e1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_e2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_e3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_e4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_f1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_f2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_f3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_f4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_g1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_g2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_g3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_g4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_h1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_h2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_h3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_h4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_i1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_i2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_i3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_i4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_j1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_j2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_j3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_j4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_k1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_k2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_k3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_k4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_l1.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_l2.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_l3.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
        btn_l4.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_BACKGROUND));
    }

    /**
     * 如果button是红色、黄色、蓝色、绿色等，Button做标记
     */
    private void setButtonColor(Button but, boolean visible) {
        if (!visible) {
            return;
        }
        if ("电源".equals(but.getText()) || "RED".equals(but.getText())
                || "HiSmart@".equals(but.getText())) {
            but.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        } else if ("BLUE".equals(but.getText())) {
            but.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        } else if ("YELLOW".equals(but.getText())) {
            but.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
        } else if ("GREEN".equals(but.getText())) {
            but.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
        } else if ("上".equals(but.getText())) {
            but.setBackground(SWTResourceManager
                    .getColor(SWT.COLOR_TITLE_FOREGROUND));
        } else if ("下".equals(but.getText())) {
            but.setBackground(SWTResourceManager
                    .getColor(SWT.COLOR_TITLE_FOREGROUND));
        } else if ("左".equals(but.getText())) {
            but.setBackground(SWTResourceManager
                    .getColor(SWT.COLOR_TITLE_FOREGROUND));
        } else if ("右".equals(but.getText())) {
            but.setBackground(SWTResourceManager
                    .getColor(SWT.COLOR_TITLE_FOREGROUND));
        }
    }

    /**
     * 设置所有的combo可见性
     */
    private void setAllComboVisible(boolean visible) {
        combo_11.setVisible(visible);
        combo_12.setVisible(visible);
        combo_13.setVisible(visible);
        combo_14.setVisible(visible);
        combo_21.setVisible(visible);
        combo_22.setVisible(visible);
        combo_23.setVisible(visible);
        combo_24.setVisible(visible);
        combo_31.setVisible(visible);
        combo_32.setVisible(visible);
        combo_33.setVisible(visible);
        combo_34.setVisible(visible);
        combo_41.setVisible(visible);
        combo_42.setVisible(visible);
        combo_43.setVisible(visible);
        combo_44.setVisible(visible);
        combo_51.setVisible(visible);
        combo_52.setVisible(visible);
        combo_53.setVisible(visible);
        combo_54.setVisible(visible);
        combo_61.setVisible(visible);
        combo_62.setVisible(visible);
        combo_63.setVisible(visible);
        combo_64.setVisible(visible);
        combo_71.setVisible(visible);
        combo_72.setVisible(visible);
        combo_73.setVisible(visible);
        combo_74.setVisible(visible);
        combo_81.setVisible(visible);
        combo_82.setVisible(visible);
        combo_83.setVisible(visible);
        combo_84.setVisible(visible);
        combo_91.setVisible(visible);
        combo_92.setVisible(visible);
        combo_93.setVisible(visible);
        combo_94.setVisible(visible);
        combo_a1.setVisible(visible);
        combo_a2.setVisible(visible);
        combo_a3.setVisible(visible);
        combo_a4.setVisible(visible);
        combo_b1.setVisible(visible);
        combo_b2.setVisible(visible);
        combo_b3.setVisible(visible);
        combo_b4.setVisible(visible);
        combo_c1.setVisible(visible);
        combo_c2.setVisible(visible);
        combo_c3.setVisible(visible);
        combo_c4.setVisible(visible);
        combo_d1.setVisible(visible);
        combo_d2.setVisible(visible);
        combo_d3.setVisible(visible);
        combo_d4.setVisible(visible);
        combo_e1.setVisible(visible);
        combo_e2.setVisible(visible);
        combo_e3.setVisible(visible);
        combo_e4.setVisible(visible);
        combo_f1.setVisible(visible);
        combo_f2.setVisible(visible);
        combo_f3.setVisible(visible);
        combo_f4.setVisible(visible);
        combo_g1.setVisible(visible);
        combo_g2.setVisible(visible);
        combo_g3.setVisible(visible);
        combo_g4.setVisible(visible);
        combo_h1.setVisible(visible);
        combo_h2.setVisible(visible);
        combo_h3.setVisible(visible);
        combo_h4.setVisible(visible);
        combo_i1.setVisible(visible);
        combo_i2.setVisible(visible);
        combo_i3.setVisible(visible);
        combo_i4.setVisible(visible);
        combo_j1.setVisible(visible);
        combo_j2.setVisible(visible);
        combo_j3.setVisible(visible);
        combo_j4.setVisible(visible);
        combo_k1.setVisible(visible);
        combo_k2.setVisible(visible);
        combo_k3.setVisible(visible);
        combo_k4.setVisible(visible);
        combo_l1.setVisible(visible);
        combo_l2.setVisible(visible);
        combo_l3.setVisible(visible);
        combo_l4.setVisible(visible);
    }

    /**
     * 创建combo组件
     */
    private void createCombos(Composite composite) {
        combo_11 = new Combo(composite, SWT.NONE);
        combo_11.setBounds(coordx_1, 10, width, 25);
        addComboListener(combo_11);

        combo_12 = new Combo(composite, SWT.NONE);
        combo_12.setBounds(coordx_2, 10, width, 25);
        addComboListener(combo_12);
        
        combo_13 = new Combo(composite, SWT.NONE);
        combo_13.setBounds(coordx_3, 10, width, 25);
        addComboListener(combo_13);
        
        combo_14 = new Combo(composite, SWT.NONE);
        combo_14.setBounds(coordx_4, 10, width, 25);
        addComboListener(combo_14);

        combo_21 = new Combo(composite, SWT.NONE);
        combo_21.setBounds(coordx_1, 41, width, 25);
        addComboListener(combo_21);

        combo_22 = new Combo(composite, SWT.NONE);
        combo_22.setBounds(coordx_2, 41, width, 25);
        addComboListener(combo_22);

        combo_23 = new Combo(composite, SWT.NONE);
        combo_23.setBounds(coordx_3, 41, width, 25);
        addComboListener(combo_23);

        combo_24 = new Combo(composite, SWT.NONE);
        combo_24.setBounds(coordx_4, 41, width, 25);
        addComboListener(combo_24);

        combo_31 = new Combo(composite, SWT.NONE);
        combo_31.setBounds(coordx_1, 72, width, 25);
        addComboListener(combo_31);

        combo_32 = new Combo(composite, SWT.NONE);
        combo_32.setBounds(coordx_2, 72, width, 25);
        addComboListener(combo_32);

        combo_33 = new Combo(composite, SWT.NONE);
        combo_33.setBounds(coordx_3, 72, width, 25);
        addComboListener(combo_33);

        combo_34 = new Combo(composite, SWT.NONE);
        combo_34.setBounds(coordx_4, 72, width, 25);
        addComboListener(combo_34);

        combo_41 = new Combo(composite, SWT.NONE);
        combo_41.setBounds(coordx_1, 103, width, 25);
        addComboListener(combo_41);

        combo_42 = new Combo(composite, SWT.NONE);
        combo_42.setBounds(coordx_2, 103, width, 25);
        addComboListener(combo_42);

        combo_43 = new Combo(composite, SWT.NONE);
        combo_43.setBounds(coordx_3, 103, width, 25);
        addComboListener(combo_43);

        combo_44 = new Combo(composite, SWT.NONE);
        combo_44.setBounds(coordx_4, 103, width, 25);
        addComboListener(combo_44);

        combo_51 = new Combo(composite, SWT.NONE);
        combo_51.setBounds(coordx_1, 134, width, 25);
        addComboListener(combo_51);

        combo_52 = new Combo(composite, SWT.NONE);
        combo_52.setBounds(coordx_2, 134, width, 25);
        addComboListener(combo_52);

        combo_53 = new Combo(composite, SWT.NONE);
        combo_53.setBounds(coordx_3, 134, width, 25);
        addComboListener(combo_53);

        combo_54 = new Combo(composite, SWT.NONE);
        combo_54.setBounds(coordx_4, 134, width, 25);
        addComboListener(combo_54);

        combo_61 = new Combo(composite, SWT.NONE);
        combo_61.setBounds(2, 165, width, 25);
        addComboListener(combo_61);

        combo_62 = new Combo(composite, SWT.NONE);
        combo_62.setBounds(87, 165, 80, 25);
        addComboListener(combo_62);

        combo_63 = new Combo(composite, SWT.NONE);
        combo_63.setBounds(172, 165, width, 25);
        addComboListener(combo_63);

        combo_64 = new Combo(composite, SWT.NONE);
        combo_64.setBounds(257, 165, width, 25);
        addComboListener(combo_64);

        combo_71 = new Combo(composite, SWT.NONE);
        combo_71.setBounds(coordx_1, 196, width, 25);
        addComboListener(combo_71);

        combo_72 = new Combo(composite, SWT.NONE);
        combo_72.setBounds(coordx_2, 196, width, 25);
        addComboListener(combo_72);

        combo_73 = new Combo(composite, SWT.NONE);
        combo_73.setBounds(coordx_3, 196, width, 25);
        addComboListener(combo_73);

        combo_74 = new Combo(composite, SWT.NONE);
        combo_74.setBounds(coordx_4, 196, width, 25);
        addComboListener(combo_74);

        combo_81 = new Combo(composite, SWT.NONE);
        combo_81.setBounds(coordx_1, 227, width, 25);
        addComboListener(combo_81);

        combo_82 = new Combo(composite, SWT.NONE);
        combo_82.setBounds(coordx_2, 227, width, 25);
        addComboListener(combo_82);

        combo_83 = new Combo(composite, SWT.NONE);
        combo_83.setBounds(coordx_3, 227, width, 25);
        addComboListener(combo_83);

        combo_84 = new Combo(composite, SWT.NONE);
        combo_84.setBounds(coordx_4, 227, width, 25);
        addComboListener(combo_84);

        combo_91 = new Combo(composite, SWT.NONE);
        combo_91.setBounds(coordx_1, 258, width, 25);
        addComboListener(combo_91);

        combo_92 = new Combo(composite, SWT.NONE);
        combo_92.setBounds(coordx_2, 258, width, 25);
        addComboListener(combo_92);

        combo_93 = new Combo(composite, SWT.NONE);
        combo_93.setBounds(coordx_3, 258, width, 25);
        addComboListener(combo_93);

        combo_94 = new Combo(composite, SWT.NONE);
        combo_94.setBounds(coordx_4, 258, width, 25);
        addComboListener(combo_94);
        
        combo_a1 = new Combo(composite, SWT.NONE);
        combo_a1.setBounds(coordx_1, 289, width, 25);
        addComboListener(combo_a1);
        
        combo_a2 = new Combo(composite, SWT.NONE);
        combo_a2.setBounds(coordx_2, 289, width, 25);
        addComboListener(combo_a2);

        combo_a3 = new Combo(composite, SWT.NONE);
        combo_a3.setBounds(coordx_3, 289, width, 25);
        addComboListener(combo_a3);

        combo_a4 = new Combo(composite, SWT.NONE);
        combo_a4.setBounds(coordx_4, 289, width, 25);
        addComboListener(combo_a4);

        combo_b1 = new Combo(composite, SWT.NONE);
        combo_b1.setBounds(coordx_1, 320, width, 25);
        addComboListener(combo_b1);

        combo_b2 = new Combo(composite, SWT.NONE);
        combo_b2.setBounds(coordx_2, 320, width, 25);
        addComboListener(combo_b2);

        combo_b3 = new Combo(composite, SWT.NONE);
        combo_b3.setBounds(coordx_3, 320, width, 25);
        addComboListener(combo_b3);

        combo_b4 = new Combo(composite, SWT.NONE);
        combo_b4.setBounds(coordx_4, 320, width, 25);
        addComboListener(combo_b4);

        combo_c1 = new Combo(composite, SWT.NONE);
        combo_c1.setBounds(coordx_1, 351, width, 25);
        addComboListener(combo_c1);

        combo_c2 = new Combo(composite, SWT.NONE);
        combo_c2.setBounds(coordx_2, 351, width, 25);
        addComboListener(combo_c2);

        combo_c3 = new Combo(composite, SWT.NONE);
        combo_c3.setBounds(coordx_3, 351, width, 25);
        addComboListener(combo_c3);

        combo_c4 = new Combo(composite, SWT.NONE);
        combo_c4.setBounds(coordx_4, 351, width, 25);
        addComboListener(combo_c4);

        combo_d1 = new Combo(composite, SWT.NONE);
        combo_d1.setBounds(coordx_1, 382, width, 25);
        addComboListener(combo_d1);

        combo_d2 = new Combo(composite, SWT.NONE);
        combo_d2.setBounds(coordx_2, 382, width, 25);
        addComboListener(combo_d2);

        combo_d3 = new Combo(composite, SWT.NONE);
        combo_d3.setBounds(coordx_3, 382, width, 25);
        addComboListener(combo_d3);

        combo_d4 = new Combo(composite, SWT.NONE);
        combo_d4.setBounds(coordx_4, 382, width, 25);
        addComboListener(combo_d4);

        combo_e1 = new Combo(composite, SWT.NONE);
        combo_e1.setBounds(coordx_1, 413, width, 25);
        addComboListener(combo_e1);
        
        combo_e2 = new Combo(composite, SWT.NONE);
        combo_e2.setBounds(coordx_2, 413, width, 25);
        addComboListener(combo_e2);

        combo_e3 = new Combo(composite, SWT.NONE);
        combo_e3.setBounds(coordx_3, 413, width, 25);
        addComboListener(combo_e3);

        combo_e4 = new Combo(composite, SWT.NONE);
        combo_e4.setBounds(coordx_4, 413, width, 25);
        addComboListener(combo_e4);

        combo_f1 = new Combo(composite, SWT.NONE);
        combo_f1.setBounds(coordx_1, 444, width, 25);
        addComboListener(combo_f1);

        combo_f2 = new Combo(composite, SWT.NONE);
        combo_f2.setBounds(coordx_2, 444, width, 25);
        addComboListener(combo_f2);

        combo_f3 = new Combo(composite, SWT.NONE);
        combo_f3.setBounds(coordx_3, 444, width, 25);
        addComboListener(combo_f3);

        combo_f4 = new Combo(composite, SWT.NONE);
        combo_f4.setBounds(coordx_4, 444, width, 25);
        addComboListener(combo_f4);

        combo_g1 = new Combo(composite, SWT.NONE);
        combo_g1.setBounds(coordx_1, 475, width, 25);
        addComboListener(combo_g1);

        combo_g2 = new Combo(composite, SWT.NONE);
        combo_g2.setBounds(coordx_2, 475, width, 25);
        addComboListener(combo_g2);

        combo_g3 = new Combo(composite, SWT.NONE);
        combo_g3.setBounds(coordx_3, 475, width, 25);
        addComboListener(combo_g3);

        combo_g4 = new Combo(composite, SWT.NONE);
        combo_g4.setBounds(coordx_4, 475, width, 25);
        addComboListener(combo_g4);

        combo_h1 = new Combo(composite, SWT.NONE);
        combo_h1.setBounds(coordx_1, 506, width, 25);
        addComboListener(combo_h1);

        combo_h2 = new Combo(composite, SWT.NONE);
        combo_h2.setBounds(coordx_2, 506, width, 25);
        addComboListener(combo_h2);

        combo_h3 = new Combo(composite, SWT.NONE);
        combo_h3.setBounds(coordx_3, 506, width, 25);
        addComboListener(combo_h3);

        combo_h4 = new Combo(composite, SWT.NONE);
        combo_h4.setBounds(coordx_4, 506, width, 25);
        addComboListener(combo_h4);

        combo_i1 = new Combo(composite, SWT.NONE);
        combo_i1.setBounds(coordx_1, 537, width, 25);
        addComboListener(combo_i1);

        combo_i2 = new Combo(composite, SWT.NONE);
        combo_i2.setBounds(coordx_2, 537, width, 25);
        addComboListener(combo_i2);

        combo_i3 = new Combo(composite, SWT.NONE);
        combo_i3.setBounds(coordx_3, 537, width, 25);
        addComboListener(combo_i3);

        combo_i4 = new Combo(composite, SWT.NONE);
        combo_i4.setBounds(coordx_4, 537, width, 25);
        addComboListener(combo_i4);

        combo_j1 = new Combo(composite, SWT.NONE);
        combo_j1.setBounds(coordx_1, 568, width, 25);
        addComboListener(combo_j1);

        combo_j2 = new Combo(composite, SWT.NONE);
        combo_j2.setBounds(coordx_2, 568, width, 25);
        addComboListener(combo_j2);

        combo_j3 = new Combo(composite, SWT.NONE);
        combo_j3.setBounds(coordx_3, 568, width, 25);
        addComboListener(combo_j3);

        combo_j4 = new Combo(composite, SWT.NONE);
        combo_j4.setBounds(coordx_4, 568, width, 25);
        addComboListener(combo_j4);

        combo_k1 = new Combo(composite, SWT.NONE);
        combo_k1.setBounds(coordx_1, 599, width, 25);
        addComboListener(combo_k1);

        combo_k2 = new Combo(composite, SWT.NONE);
        combo_k2.setBounds(coordx_2, 599, width, 25);
        addComboListener(combo_k2);

        combo_k3 = new Combo(composite, SWT.NONE);
        combo_k3.setBounds(coordx_3, 599, width, 25);
        addComboListener(combo_k3);

        combo_k4 = new Combo(composite, SWT.NONE);
        combo_k4.setBounds(coordx_4, 599, width, 25);
        addComboListener(combo_k4);

        combo_l1 = new Combo(composite, SWT.NONE);
        combo_l1.setBounds(coordx_1, 630, width, 25);
        addComboListener(combo_l1);

        combo_l2 = new Combo(composite, SWT.NONE);
        combo_l2.setBounds(coordx_2, 630, width, 25);
        addComboListener(combo_l2);

        combo_l3 = new Combo(composite, SWT.NONE);
        combo_l3.setBounds(coordx_3, 630, width, 25);
        addComboListener(combo_l3);

        combo_l4 = new Combo(composite, SWT.NONE);
        combo_l4.setBounds(coordx_4, 630, width, 25);
        addComboListener(combo_l4);
    }

    /**
     * 为combo添加监听
     */
    private void addComboListener(final Combo combo){
        combo.addSelectionListener(new SelectionListener(){

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                for(int i=0;i<combo.getItemCount();i++){
                    if(combo.getText().equals(combo.getItem(i))){
                        @SuppressWarnings("unchecked")
                        String data = ((ArrayList<String>)combo.getData()).get(i);
                        combo.setData("data", data);
                    }
                }
            }
            
        });
    }
    /**
     * 列表中的内容
     * 
     * @param normalKeysName2
     */
    private void setComboList(String[] name) {
        if (name == null) {
            return;
        }
        combo_11.setItems(name);
        combo_12.setItems(name);
        combo_13.setItems(name);
        combo_14.setItems(name);
        combo_21.setItems(name);
        combo_22.setItems(name);
        combo_23.setItems(name);
        combo_24.setItems(name);
        combo_31.setItems(name);
        combo_32.setItems(name);
        combo_33.setItems(name);
        combo_34.setItems(name);
        combo_41.setItems(name);
        combo_42.setItems(name);
        combo_43.setItems(name);
        combo_44.setItems(name);
        combo_51.setItems(name);
        combo_52.setItems(name);
        combo_53.setItems(name);
        combo_54.setItems(name);
        combo_61.setItems(name);
        combo_62.setItems(name);
        combo_63.setItems(name);
        combo_64.setItems(name);
        combo_71.setItems(name);
        combo_72.setItems(name);
        combo_73.setItems(name);
        combo_74.setItems(name);
        combo_81.setItems(name);
        combo_82.setItems(name);
        combo_83.setItems(name);
        combo_84.setItems(name);
        combo_91.setItems(name);
        combo_92.setItems(name);
        combo_93.setItems(name);
        combo_94.setItems(name);
        combo_a1.setItems(name);
        combo_a2.setItems(name);
        combo_a3.setItems(name);
        combo_a4.setItems(name);
        combo_b1.setItems(name);
        combo_b2.setItems(name);
        combo_b3.setItems(name);
        combo_b4.setItems(name);
        combo_c1.setItems(name);
        combo_c2.setItems(name);
        combo_c3.setItems(name);
        combo_c4.setItems(name);
        combo_d1.setItems(name);
        combo_d2.setItems(name);
        combo_d3.setItems(name);
        combo_d4.setItems(name);
        combo_e1.setItems(name);
        combo_e2.setItems(name);
        combo_e3.setItems(name);
        combo_e4.setItems(name);
        combo_f1.setItems(name);
        combo_f2.setItems(name);
        combo_f3.setItems(name);
        combo_f4.setItems(name);
        combo_g1.setItems(name);
        combo_g2.setItems(name);
        combo_g3.setItems(name);
        combo_g4.setItems(name);
        combo_h1.setItems(name);
        combo_h2.setItems(name);
        combo_h3.setItems(name);
        combo_h4.setItems(name);
        combo_i1.setItems(name);
        combo_i2.setItems(name);
        combo_i3.setItems(name);
        combo_i4.setItems(name);
        combo_j1.setItems(name);
        combo_j2.setItems(name);
        combo_j3.setItems(name);
        combo_j4.setItems(name);
        combo_k1.setItems(name);
        combo_k2.setItems(name);
        combo_k3.setItems(name);
        combo_k4.setItems(name);
        combo_l1.setItems(name);
        combo_l2.setItems(name);
        combo_l3.setItems(name);
        combo_l4.setItems(name);
    }

    /**
     * 设置下拉列表中的隐藏信息
     */
    private void setComboData(ArrayList<String> keys){
        if(keys==null){
            return;
        }
        combo_11.setData(keys);
        combo_12.setData(keys);
        combo_13.setData(keys);
        combo_14.setData(keys);
        combo_21.setData(keys);
        combo_22.setData(keys);
        combo_23.setData(keys);
        combo_24.setData(keys);
        combo_31.setData(keys);
        combo_32.setData(keys);
        combo_33.setData(keys);
        combo_34.setData(keys);
        combo_41.setData(keys);
        combo_42.setData(keys);
        combo_43.setData(keys);
        combo_44.setData(keys);
        combo_51.setData(keys);
        combo_52.setData(keys);
        combo_53.setData(keys);
        combo_54.setData(keys);
        combo_61.setData(keys);
        combo_62.setData(keys);
        combo_63.setData(keys);
        combo_64.setData(keys);
        combo_71.setData(keys);
        combo_72.setData(keys);
        combo_73.setData(keys);
        combo_74.setData(keys);
        combo_81.setData(keys);
        combo_82.setData(keys);
        combo_83.setData(keys);
        combo_84.setData(keys);
        combo_91.setData(keys);
        combo_92.setData(keys);
        combo_93.setData(keys);
        combo_94.setData(keys);
        combo_a1.setData(keys);
        combo_a2.setData(keys);
        combo_a3.setData(keys);
        combo_a4.setData(keys);
        combo_b1.setData(keys);
        combo_b2.setData(keys);
        combo_b3.setData(keys);
        combo_b4.setData(keys);
        combo_c1.setData(keys);
        combo_c2.setData(keys);
        combo_c3.setData(keys);
        combo_c4.setData(keys);
        combo_d1.setData(keys);
        combo_d2.setData(keys);
        combo_d3.setData(keys);
        combo_d4.setData(keys);
        combo_e1.setData(keys);
        combo_e2.setData(keys);
        combo_e3.setData(keys);
        combo_e4.setData(keys);
        combo_f1.setData(keys);
        combo_f2.setData(keys);
        combo_f3.setData(keys);
        combo_f4.setData(keys);
        combo_g1.setData(keys);
        combo_g2.setData(keys);
        combo_g3.setData(keys);
        combo_g4.setData(keys);
        combo_h1.setData(keys);
        combo_h2.setData(keys);
        combo_h3.setData(keys);
        combo_h4.setData(keys);
        combo_i1.setData(keys);
        combo_i2.setData(keys);
        combo_i3.setData(keys);
        combo_i4.setData(keys);
        combo_j1.setData(keys);
        combo_j2.setData(keys);
        combo_j3.setData(keys);
        combo_j4.setData(keys);
        combo_k1.setData(keys);
        combo_k2.setData(keys);
        combo_k3.setData(keys);
        combo_k4.setData(keys);
        combo_l1.setData(keys);
        combo_l2.setData(keys);
        combo_l3.setData(keys);
        combo_l4.setData(keys);
    }
    /**
     * 设置每个下拉列表的内容
     * 
     * @param content
     */
    private void setCombosText(String content) {
        combo_11.setText(content);
        combo_12.setText(content);
        combo_13.setText(content);
        combo_14.setText(content);
        combo_21.setText(content);
        combo_22.setText(content);
        combo_23.setText(content);
        combo_24.setText(content);
        combo_31.setText(content);
        combo_32.setText(content);
        combo_33.setText(content);
        combo_34.setText(content);
        combo_41.setText(content);
        combo_42.setText(content);
        combo_43.setText(content);
        combo_44.setText(content);
        combo_51.setText(content);
        combo_52.setText(content);
        combo_53.setText(content);
        combo_54.setText(content);
        combo_61.setText(content);
        combo_62.setText(content);
        combo_63.setText(content);
        combo_64.setText(content);
        combo_71.setText(content);
        combo_72.setText(content);
        combo_73.setText(content);
        combo_74.setText(content);
        combo_81.setText(content);
        combo_82.setText(content);
        combo_83.setText(content);
        combo_84.setText(content);
        combo_91.setText(content);
        combo_92.setText(content);
        combo_93.setText(content);
        combo_94.setText(content);
        combo_a1.setText(content);
        combo_a2.setText(content);
        combo_a3.setText(content);
        combo_a4.setText(content);
        combo_b1.setText(content);
        combo_b2.setText(content);
        combo_b3.setText(content);
        combo_b4.setText(content);
        combo_c1.setText(content);
        combo_c2.setText(content);
        combo_c3.setText(content);
        combo_c4.setText(content);
        combo_d1.setText(content);
        combo_d2.setText(content);
        combo_d3.setText(content);
        combo_d4.setText(content);
        combo_e1.setText(content);
        combo_e2.setText(content);
        combo_e3.setText(content);
        combo_e4.setText(content);
        combo_f1.setText(content);
        combo_f2.setText(content);
        combo_f3.setText(content);
        combo_f4.setText(content);
        combo_g1.setText(content);
        combo_g2.setText(content);
        combo_g3.setText(content);
        combo_g4.setText(content);
        combo_h1.setText(content);
        combo_h2.setText(content);
        combo_h3.setText(content);
        combo_h4.setText(content);
        combo_i1.setText(content);
        combo_i2.setText(content);
        combo_i3.setText(content);
        combo_i4.setText(content);
        combo_j1.setText(content);
        combo_j2.setText(content);
        combo_j3.setText(content);
        combo_j4.setText(content);
        combo_k1.setText(content);
        combo_k2.setText(content);
        combo_k3.setText(content);
        combo_k4.setText(content);
        combo_l1.setText(content);
        combo_l2.setText(content);
        combo_l3.setText(content);
        combo_l4.setText(content);

    }

    /**
     * 显示新建的页面
     */
    private void showNewPage(Composite composite) {

        txt_path.setText("");
        btn_edit.setEnabled(false);
        
        grpCustom.setVisible(false);
        setEditPageVisible(false);
        scrolledComposite_btn.setVisible(false);

        group_new = new Group(composite, SWT.NONE);
        group_new.setBounds(5, 0, 360, 40);

        lbl_No1 = new Label(group_new, SWT.NONE);
        lbl_No1.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
        lbl_No1.setBounds(10, 12, 18, 17);
        lbl_No1.setText("1.");

        txt_fileName = new Text(group_new, SWT.BORDER);
        txt_fileName.setBounds(30, 12, 90, 23);
        txt_fileName.setMessage("输入遥控器名称");
        txt_fileName.setEnabled(true);
        
        btn_sx6 = new Button(group_new, SWT.CHECK);
        btn_sx6.setBounds(126, 12, 55, 23);
        btn_sx6.setText("离散码");
        btn_sx6.setEnabled(true);

        Button btn_create = new Button(group_new, SWT.NONE);
        btn_create.setBounds(186, 12, 59, 23);
        btn_create.setText("新建");
        btn_create.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 新建文件
                newRemotorFile();
            }

        });

        Button btn_return = new Button(group_new, SWT.NONE);
        btn_return.setBounds(252, 12, 59, 23);
        btn_return.setText("返回");
        btn_return.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                txt_path.setText("");
                hideNewPage(false);
            }

        });

        lbl_No2 = new Label(composite, SWT.NONE);
        lbl_No2.setVisible(false);
        lbl_No2.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
        lbl_No2.setBounds(15, 40, 106, 17);
        lbl_No2.setText("2.  编辑遥控器样式");

        lbl_No3 = new Label(composite, SWT.NONE);
        lbl_No3.setVisible(false);
        lbl_No3.setText("3.");
        lbl_No3.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
        lbl_No3.setBounds(15, 490, 18, 17);

        btn_saveNew = new Button(composite, SWT.NONE);
        btn_saveNew.setVisible(false);
        btn_saveNew.setBounds(39, 490, 70, 27);
        btn_saveNew.setText("保存");
        btn_saveNew.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                saveNewRemotor();
            }

        });

        btn_cancelNew = new Button(composite, SWT.NONE);
        btn_cancelNew.setText("取消");
        btn_cancelNew.setBounds(115, 490, 70, 27);
        btn_cancelNew.setVisible(false);
        btn_cancelNew.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // 判断是否取消编辑
                if (showAskMsg("确定取消？", SWT.ICON_QUESTION)) {
                    hideNewPage(false);
                    return;
                }
            }
        });
    }

    /**
     * 保存新建的遥控器
     */
    private void saveNewRemotor() {
        isSX6 = false;
        if (!createPropertiesFile(PgNewRemotor.getPropertyContent(),
                txt_fileName.getText())) {
            showMsg("保存失败！\n创建遥控器properties文件失败！", SWT.ICON_ERROR);
            return;
        }
        if (!saveRemotor(newRemoterFile)) {
            return;
        }
        if (btn_sx6.getSelection()) {
            isSX6 = true;
        }
        inputEncodeMap();
        btn_edit.setEnabled(true);
        // 保存后，回到原来的界面
        scrolledComposite_combo.setVisible(false);
        if (isSX6) {
            grpCustom.setText("离散码遥控器");
        }else{
            grpCustom.setText("");
        }
    }

    /**
     * 新建一个文件
     */
    private void newRemotorFile() {
        if (createNewFile(txt_fileName.getText())) {
            // 设置下拉列表的内容
            if ("".equals(PgNewRemotor.getPropertyContent())) {
                return;
            }
            keys = PgNewRemotor.writeKeys(PgNewRemotor.getPropertyContent());
            if (keys == null) {
                return;
            }
            setComboContent(keys);
            btn_cancelNew.setVisible(true);
            btn_saveNew.setVisible(true);
            lbl_No2.setVisible(true);
            lbl_No3.setVisible(true);
            btn_sx6.setEnabled(false);
            txt_fileName.setEnabled(false);
            scrolledComposite_combo.setVisible(true);
        }
    }

    /**
     * 不显示新建页面
     */
    private void hideNewPage(boolean visible) {
        grpCustom.setVisible(!visible);
        scrolledComposite_combo.setVisible(visible);
        btn_cancelNew.setVisible(visible);
        btn_saveNew.setVisible(visible);
        group_new.setVisible(visible);
        lbl_No2.setVisible(visible);
        lbl_No3.setVisible(visible);
    }

    /**
     * 新建一个遥控器配置文件
     */
    private boolean createNewFile(String name) {
        if ("".equals(name)) {
            showMsg("文件名不能为空！", SWT.ERROR);
            return false;
        }
        if(name.contains(" ") || name.contains("_")){
            showMsg("文件名不能含有空格或下划线！", SWT.ERROR);
            return false;
        }
        if (btn_sx6.getSelection()) {
            name = name + Resources.sx6Name;
        }
        newRemoterFile = new File(Resources.pageInfoPath + File.separator
                + name + nameDefineSuffix);
        if (newRemoterFile.exists()) {
            if (!showAskMsg("文件已存在，是否覆盖？", SWT.ICON_QUESTION)) {
                return false;
            }
        }
        PgMIR.shell.setEnabled(false);
        PgNewRemotor pgNewRemotor = new PgNewRemotor(name,btn_sx6.getSelection());
        pgNewRemotor.open();
        return PgNewRemotor.isNew();
    }

    /**
     * 将保存的文件生成一个遥控器
     */
    private boolean saveRemotor(File file) {
        if (file == null) {
            return false;
        }
        if (!savePosition(file)) {
            return false;
        }
        hideNewPage(false);
        scrolledComposite_btn.setVisible(true);
        // 将选中的combo显示为button
        comboToButton();
        if (btn_sx6.getSelection()) {
            txt_path.setText(txt_fileName.getText() + Resources.sx6Name);
        } else {
            txt_path.setText(txt_fileName.getText());
        }
        return true;
    }

    /**
     * 生成properties文件
     */
    private boolean createPropertiesFile(String text, String name) {
        try {
            if (btn_sx6.getSelection()) {
                name = name + Resources.sx6Name;
            }
            String propertyPath = Resources.propertyFilePath + File.separator
                    + name + nameSuffix;
            File propertyFile = new File(propertyPath);
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(propertyFile), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 将键值放入到htEncodeMap中
     */
    private void inputEncodeMap() {
        Properties prop = utils.getProperties(Resources.propertyFilePath
                + File.separator + txt_path.getText() + nameSuffix);
        if (isSX6) {
            // 如果是离散码，保存到Map之前，将XX(SX6)变为XX_(_SX6_)_
            Enumeration<Object> propKeys = prop.keys();
            Object[] propValues = prop.values().toArray();
            Properties newProp = new Properties();
            String propKey = "";
            Object propValue;
            int i = 0;
            while (propKeys.hasMoreElements()) {
                propKey = (String) propKeys.nextElement();
                propValue = propValues[i];
                propKey = propKey.substring(0, propKey.indexOf("("))
                        + "_(_离散码_)"
                        + propKey.substring(propKey.indexOf(")") + 1);
                newProp.put(propKey, propValue);
                i++;
            }
            SmartAuto.getAllOtherKeyMap(newProp);
        } else {
            SmartAuto.getAllOtherKeyMap(prop);
        }

    }

    /**
     * 将配置的按钮位置写在文件中
     */
    private boolean savePosition(File file) {
        try {
            // 将各选项的位置写入到文件中
            if (!getAllComboDetail()) {
                return false;
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(outputString);
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showMsg("保存失败！ \n编辑失败！", SWT.ICON_ERROR);
            return false;
        }
        return true;
    }

    /**
     * 显示提示信息
     */
    protected static void showMsg(String msg, int level) {
        MessageBox msgBox = new MessageBox(PgMIR.shell, SWT.OK | level
                | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        msgBox.open();
    }

    /**
     * 显示提示信息
     */
    protected boolean showAskMsg(String msg, int level) {
        MessageBox msgBox = new MessageBox(PgMIR.shell, SWT.OK | level
                | SWT.CANCEL | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        if (msgBox.open() == SWT.OK) {
            return true;
        } else {
            return false;
        }
    }
}
