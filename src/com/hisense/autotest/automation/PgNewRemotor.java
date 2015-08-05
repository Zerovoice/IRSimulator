package com.hisense.autotest.automation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import com.hisense.autotest.common.Resources;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class PgNewRemotor extends SmartAuto {

    private static Logger logger = Logger.getLogger(PgNewRemotor.class);
    public static Shell shell;
    protected Object result;
    protected Display display;
    private Composite composite;
    private Text text;
    private Button btn_save;
    private Button btn_cancel;
    private String name = "";
    private boolean isSX6;
    private static boolean isNew = false;

    private static String propertyContent = "";

    public PgNewRemotor(String name,boolean isSX6) {
        super();
        this.isSX6=isSX6;
        this.name = name;
    }

    /**
     * @wbp.parser.entryPoint
     */
    public Object open() {
        String content = readFile(isSX6);
        createContent(content);
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
     * 创建界面
     */
    private void createContent(String content) {
        display = Display.getDefault();

        shell = new Shell(display, SWT.CLOSE);
        shell.setSize(642, 548);
        shell.setText("新建一个遥控器的配置");
        shell.setLayout(null);
        shell.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent arg0) {
                PgMIR.shell.setEnabled(true);
            }
        });

        composite = new Composite(shell, SWT.NONE);
        composite.setBounds(39, 50, 560, 416);

        text = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
        text.setBounds(0, 0, 560, 416);
        text.setText(content);

        btn_save = new Button(shell, SWT.NONE);
        btn_save.setBounds(427, 472, 80, 27);
        btn_save.setText("确定");
        btn_save.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                propertyContent = "";
                // 判断每个码值是否有重复按键
                if (containDuplicate()) {
                    return;
                } else {
                    propertyContent = text.getText();
                    isNew = true;
                    shell.close();
                }
            }

        });

        btn_cancel = new Button(shell, SWT.NONE);
        btn_cancel.setText("取消");
        btn_cancel.setBounds(519, 472, 80, 27);

        Label lbl_title = new Label(shell, SWT.NONE);
        lbl_title.setForeground(SWTResourceManager
                .getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
        lbl_title.setBounds(39, 10, 494, 34);
        lbl_title
                .setText("\u66F4\u6539\u7801\u503C\uFF1A\u4F8B\uFF0CNEC_xx_XX=0D\u6539\u4E3ANEC_xx_XX=09\uFF1B\r\n\u6DFB\u52A0\u6309\u952E\uFF1A\u4F8B\uFF0C\u5BF9\u5E94\u4F4D\u7F6E\u6DFB\u52A0  NEC_xx_XX=\u7801\u503C\u3002");
        btn_cancel.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                isNew = false;
                shell.dispose();
            }

        });
    }

    /**
     * 判断每个码值是否有重复的按键名
     * 
     * @return
     */
    private boolean containDuplicate() {
        ArrayList<String> NEC_keys = new ArrayList<String>();
        ArrayList<String> RC5_keys = new ArrayList<String>();
        ArrayList<String> KEYCODE_keys = new ArrayList<String>();
        String[] keysContent = text.getText().split("\n");
        String keystart = "";
        String keyend = "";
        for (String str : keysContent) {
            if (str.startsWith(Resources.comment)) {
                continue;
            }
            keystart = str.substring(str.indexOf("_") + 1);
            keyend = keystart.substring(keystart.indexOf("_") + 1,
                    keystart.indexOf("="));
            if (str.startsWith(Resources.NEC)) {
                if (NEC_keys.contains(keyend)) {
                    showMsg("NEC按键" + keyend + "对应码值定义重复！",
                            SWT.ICON_INFORMATION);
                    return true;
                } else {
                    NEC_keys.add(keyend);
                }
            } else if (str.startsWith(Resources.RC5)) {
                if (RC5_keys.contains(keyend)) {
                    showMsg("RC5按键" + keyend + "对应码值定义重复！",
                            SWT.ICON_INFORMATION);
                    return true;
                } else {
                    RC5_keys.add(keyend);
                }
            } else if (str.startsWith(Resources.KEYCODE)) {
                if (KEYCODE_keys.contains(keyend)) {
                    showMsg("keycode按键" + keyend + "对应码值定义重复！",
                            SWT.ICON_INFORMATION);
                    return true;
                } else {
                    KEYCODE_keys.add(keyend);
                }
            }
        }
        return false;
    }

    /**
     * 读取文件，获取键名和键值
     */
    private String readFile(boolean isSX6) {
        String content = "";
        try {
            File file=null;
            if(isSX6){
                file = new File(Resources.discretePath);
            }else{
             file = new File(Resources.customKeysPath);
            }
            InputStreamReader inputReader = new InputStreamReader(
                    new FileInputStream(file), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line = null;
            int i = 1;
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
                content += line.substring(0, index) + name + "_"
                        + line.substring(index);
                i++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return content;
    }

    /**
     * 生成下拉列表的选项内容
     */
    public static ArrayList<String> writeKeys(String outputString) {
        ArrayList<String> keys = new ArrayList<String>();
        String[] keysContent = outputString.split("\n");
        String key = "";
        int index;
        String partkey = "";
        for (String str : keysContent) {
            if (str.startsWith(Resources.comment)) {
                continue;
            }
            // 截取按键名,第二个_后面到=号之间的字符串
            index = str.indexOf("_") + 1;
            partkey = str.substring(index);
            key = partkey.substring(partkey.indexOf("_") + 1,
                    partkey.indexOf("="));
            keys.add(key);
        }
        Set<String> set = new HashSet<String>();
        ArrayList<String> newList = new ArrayList<String>();
        for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
            String element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        keys.clear();
        keys.addAll(newList);
        return keys;
    }

    /**
     * 返回是否保存
     */
    public static boolean isNew() {
        return isNew;
    }

    /**
     * 获取property文件的内容
     * 
     * @return
     */
    public static String getPropertyContent() {
        return propertyContent;
    }

    /**
     * 显示提示信息
     */
    private static void showMsg(String msg, int level) {
        MessageBox msgBox = new MessageBox(shell, SWT.OK | level | SWT.CENTER);
        msgBox.setText("提示信息");
        msgBox.setMessage(msg);
        msgBox.open();
    }
}
