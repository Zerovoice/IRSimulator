package com.hisense.autotest.automation;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.hisense.autotest.common.Resources;

import org.eclipse.swt.widgets.Combo;

public class PgEditKeyCode extends SmartAuto {
	private static Logger logger = Logger.getLogger(PgIRSimulator.class);
	private static String nameSuffix = "_自定义遥控器.properties";
	private static String nameSuffix_prop = ".properties";

	private Object result;
	private Table tableSystem;
	private Table tableCustom;

	private Display display;
	private Shell shell;
	private ScrolledComposite scrolledTable;
	private Composite btComposite;
	private Button btnSave;
	private TableColumn tbcKeynameCustom;
	private Combo combo_CustomFileList;
	private Button btnRestore;

	private Properties customProperties = null;
	private HashMap<String, String> htChineseName = new HashMap<String, String>();
	private int colum_NEC = 1;
	private ArrayList<String> keynameColumn = new ArrayList<String>();
	private int colum_RC5 = 2;
	private int colum_KEYCODE = 3;

	public PgEditKeyCode() {
		super();
	}

	/**
	 * 创建键值编辑界面
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void creatPg() {
		display = Display.getDefault();
		shell = new Shell(display, SWT.CLOSE);
		shell.setText("键值编辑");
		shell.setSize(700, 560);
		shell.open();
		shell.addDisposeListener(new DisposeListener(){

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				PgMIR.shell.setEnabled(true);
			}
			
		});

		scrolledTable = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		scrolledTable.setBounds(0, 27, 694, 505);
		scrolledTable.setExpandHorizontal(true);
		scrolledTable.setExpandVertical(true);

		tableSystem = new Table(scrolledTable, SWT.BORDER | SWT.FULL_SELECTION);
		tableSystem.setOrientation(1);
		tableSystem.setLinesVisible(true);
		tableSystem.setHeaderVisible(true);

		tableCustom = new Table(scrolledTable, SWT.BORDER | SWT.FULL_SELECTION);
		tableCustom.setOrientation(1);
		tableCustom.setLinesVisible(true);
		tableCustom.setHeaderVisible(true);

		tbcKeynameCustom = new TableColumn(tableCustom, SWT.NONE);
		tbcKeynameCustom.setWidth(200);
		tbcKeynameCustom.setText("按键名称");

		btComposite = new Composite(shell, SWT.NONE);
		btComposite.setLayoutData(BorderLayout.NORTH);
		btComposite.setBounds(0, 0, 694, 27);

		//系统自带码 编辑界面的Editor
		final TableEditor editorSys = new TableEditor(tableSystem);
		editorSys.horizontalAlignment = SWT.LEFT;
		editorSys.grabHorizontal = true;
		//自定义按键码 编辑界面的Editor
		final TableEditor editorCus = new TableEditor(tableCustom);
		editorCus.horizontalAlignment = SWT.LEFT;
		editorCus.grabHorizontal = true;

		btnRestore = new Button(btComposite, SWT.NONE);
		btnRestore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				restoreALLKey();
			}

		});
		btnRestore.setBounds(531, 0, 80, 27);
		btnRestore.setText("恢复默认值");

		combo_CustomFileList = new Combo(btComposite, SWT.READ_ONLY);
		combo_CustomFileList.setVisible(false);
		combo_CustomFileList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				// 按照用户选择设置，自定义编辑界面显示内容。
				setCustomTable();
			}
		});
		combo_CustomFileList.setBounds(213, 2, 165, 25);
		// 初始化自定义按键编辑界面
		initCustomTable();
		loadChineseName();
		setCustomFileList();
		

		final Button btnCustomKey = new Button(btComposite, SWT.RADIO);
		btnCustomKey.setBounds(111, 5, 97, 17);
		btnCustomKey.setText("自定义按键");
		btnCustomKey.setSelection(false);

		btnCustomKey.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				combo_CustomFileList.setVisible(true);
				btnRestore.setVisible(false);
				scrolledTable.setContent(tableCustom);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		Button btnSystemKey = new Button(btComposite, SWT.RADIO);
		btnSystemKey.setBounds(10, 5, 97, 17);
		btnSystemKey.setText("系统按键");
		btnSystemKey.setSelection(true);

		btnSave = new Button(btComposite, SWT.NONE);
		btnSave.setBounds(614, 0, 80, 27);
		btnSave.setText("保存");

		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				if (btnCustomKey.getSelection()) {
					saveCustomProperties();
				} else {
					saveSystemProperties();
				}

			}
		});

		btnSystemKey.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				combo_CustomFileList.setVisible(false);
				btnRestore.setVisible(true);
				scrolledTable.setContent(tableSystem);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		TableColumn tbcKeyName = new TableColumn(tableSystem, SWT.NONE);
		tbcKeyName.setText("按键名称");
		tbcKeyName.setWidth(100);
		// 初始化系统自带键值编辑界面
		initTable();
		scrolledTable.setContent(tableSystem);

		tableCustom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}
				Point pt = new Point(event.x, event.y);
				final TableItem item = tableCustom.getItem(pt);
				if (item == null) {
					return;
				}
				for (int i = 0; i < tableCustom.getColumnCount(); i++) {
					final int column = i;
					if (!item.getBounds(column).contains(pt)
							|| item.getBounds(0).contains(pt)) {
						continue;
					}

					final Text text = new Text(tableCustom, SWT.NONE);
					text.setForeground(item.getForeground());
					text.setText(item.getText(column));
					text.setForeground(item.getForeground());
					text.selectAll();
					text.setFocus();
					editorCus.minimumWidth = text.getBounds().width;
					editorCus.setEditor(text, item, column);
					text.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							String strVal = text.getText();
							item.setText(column, strVal);
						}
					});
				}
			}
		});

		tableSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				Control old = editorSys.getEditor();
				if (old != null) {
					old.dispose();
				}
				old = editorCus.getEditor();
				if (old != null) {
					old.dispose();
				}

				Point pt = new Point(event.x, event.y);
				final TableItem item = tableSystem.getItem(pt);
				if (item == null) {
					return;
				}
				for (int i = 0; i < tableSystem.getColumnCount(); i++) {
					final int column = i;
					if (!item.getBounds(column).contains(pt)
							|| item.getBounds(0).contains(pt)) {
						continue;
					}

					final Text text = new Text(tableSystem, SWT.NONE);
					text.setForeground(item.getForeground());
					text.setText(item.getText(column));
					text.setForeground(item.getForeground());
					text.selectAll();
					text.setFocus();
					editorSys.minimumWidth = text.getBounds().width;
					editorSys.setEditor(text, item, column);
					text.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							String strVal = text.getText();
							item.setText(column, strVal);
						}
					});
				}
			}
		});

	}

	/**
	 * 初始化界面数据
	 */
	private void initTable() {
		for (int i = 0; i < column.size(); i++) {
			TableColumn tbcTmp = new TableColumn(tableSystem, SWT.NONE);
			tbcTmp.setWidth(110);
			tbcTmp.setText(column.get(i));
		}
		for (int i = 0; i < row.size(); i++) {
			TableItem tbiTmp = new TableItem(tableSystem, SWT.NONE);
			tbiTmp.setText(row.get(i));
		}
		load_Properties();

	}

	/**
	 * 从htEditcodeMap中加载所有键值数据
	 */
	private void load_Properties() {
		HashMap<String, String> htKeymapTmp = new HashMap<String, String>();
		getAllSystemKeymap(htKeymapTmp);
		String key = "";
		for (int i = 1; i < tableSystem.getColumnCount(); i++) {
			for (int j = 0; j < tableSystem.getItemCount(); j++) {
				TableItem tabTmp = tableSystem.getItem(j);
				key = tableSystem.getColumn(i).getText() + "_"
						+ tabTmp.getText();
				if (!htKeymapTmp.containsKey(key)) {
					tabTmp.setText(i, "");
					continue;
				}
				tabTmp.setText(i, htKeymapTmp.get(key));
			}
		}
	}

	/**
	 * 将界面内容保存，并覆盖原来的系统自带Properties文件，同时更新程序中的hashmap。
	 */
	private void saveSystemProperties() {
		if (!checkAllData(tableSystem)) {
			return;
		}
		String key = "";
		for (int i = 1; i < tableSystem.getColumnCount(); i++) {
			String filename = tableSystem.getColumn(i).getText()
					+ nameSuffix_prop;
			String filePath = Resources.propertyFilePath + File.separator
					+ filename;
			Properties propTmp = new Properties();
			for (int j = 0; j < tableSystem.getItemCount(); j++) {
				TableItem tabTmp = tableSystem.getItem(j);
				key = tableSystem.getColumn(i).getText() + "_"
						+ tabTmp.getText();
				if ("".equals(tabTmp.getText(i))) {
					continue;
				}
				propTmp.put(key, tabTmp.getText(i));
			}
			savePropertiesWithComment(filePath, propTmp);
		}
		//更新hashmap中所有系统自带码值
		for (int i = 0; i < column.size(); i++) {
			Properties otherProp = utils
					.getProperties(Resources.propertyFilePath + File.separator
							+ column.get(i) + nameSuffix_prop);
			getAllOtherKeyMap(otherProp);
		}
		showMsg(shell, "系统按键保存成功。", 0);
	}

	/**
	 * 保存当前正在编辑的自定义按键表格，同时更新hashmap
	 */
	private void saveCustomProperties() {
		if ("".equals(combo_CustomFileList.getText())
				|| combo_CustomFileList.getText() == null) {
			showMsg(shell, "请选择自定义遥控器文件", 0);
			return;
		}
		if (!checkAllData(tableCustom)) {
			return;
		}
		
		String filename = combo_CustomFileList.getText() + nameSuffix_prop;
		String filePath = Resources.propertyFilePath + File.separator
				+ filename;
		StringBuilder sb = new StringBuilder();
		FileOutputStream outSave = null;
		//获取StringBuilder类型的文件内容。
		sb = getKeysFromUI();
		String comment = getComment(filePath);
		//将Comment和键值内容（sb）保存到filepath所对应的文件中。
		try {
			outSave = new FileOutputStream(new File(filePath));
			byte[] b = comment.getBytes();
			outSave.write(b);
			b = sb.toString().getBytes();
			outSave.write(b);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				outSave.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Properties propTmp = utils.getProperties(filePath);
		getAllOtherKeyMap(propTmp);
		showMsg(shell, "自定义按键保存成功。", 0);
	}
	
	/**
	 * 将tableCustom上的所有内容转化为字符串内容
	 * @return
	 */
	private StringBuilder getKeysFromUI(){
		String key = "";
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < tableCustom.getColumnCount(); i++) {
			Iterator<String> itr = keynameColumn.iterator();
			int j = 0;
			while (itr.hasNext()) {
				TableItem tabTmp = tableCustom.getItem(j);
				key = tableCustom.getColumn(i).getText() + "_" + itr.next();
				if ("".equals(tabTmp.getText(i)) || tabTmp.getText(i) == null) {
					j++;
					continue;
				}
				sb.append(key + "=" + tabTmp.getText(i) + "\n");
				j++;
			}
		}
		return sb;
	}

	/**
	 * 获取相应文件的Comment信息返回comment字符串。
	 * @param filePath
	 * @return
	 */
	private String getComment(String filePath) {
		String tmp = "";
		String comments = "";
		File f = null;
		FileInputStream in = null;
		BufferedReader brin = null;
		try {
			f = new File(filePath);
			in = new FileInputStream(f);
			brin = new BufferedReader(new InputStreamReader(in));
			tmp = brin.readLine();
			//读取所有以"##"或"# "开头的行，保存在Comment中
			while (tmp.startsWith("##") || tmp.startsWith("# ")) {
				comments += tmp + "\n";
				tmp = brin.readLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
				brin.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return comments;
	}

	/**
	 * 在保留comment的情况下保留Properties文件
	 */
	private void savePropertiesWithComment(String filePath, Properties propTmp) {
		String comments = null;
		FileOutputStream out = null;
		File f = null;
		try {
			comments = getComment(filePath);
			//处理Comment字符串防止Properties.store方法影响文件结构。
			comments = comments.substring(1, comments.length() - 1);
			f = new File(filePath);
			out = new FileOutputStream(f);
			propTmp.store(out, comments);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查界面中是否包含不符合规则的字符串。
	 * 
	 * @return
	 */
	private boolean checkAllData(Table table) {
		for (int i = 1; i < table.getColumnCount(); i++) {
			for (int j = 0; j < table.getItemCount(); j++) {
				TableItem tabTmp = table.getItem(j);
				if ((!utils.isNumericAndLetter(tabTmp.getText(i)))||tabTmp.getText(i).length()>4) {
					showMsg(shell,
							"保存失败！\n"+"键值应为16进制且不能超过4位的字符序列：" + table.getColumn(i).getText()
									+ "_" + tabTmp.getText() + " 值为"
									+ tabTmp.getText(i),SWT.ICON_ERROR);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 创建界面。
	 * 
	 * @param parent
	 * @return
	 */
	public Object open() {
		try {
			creatPg();
			PgMIR.shell.setEnabled(false);
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return result;
	}

	/**
	 * 将所有键值恢复为默认值
	 */
	private void restoreALLKey() {
		if (!showSelMsg(shell, "是否要将全部键值恢复为默认值", 0)) {
			return;
		}
		String path_bac = Resources.propertyFilePathBac + File.separator;
		String path = Resources.propertyFilePath + File.separator;
		try {
			FileUtils.copyDirectory(new File(path_bac), new File(path));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		for (int i = 0; i < column.size(); i++) {
			Properties otherProp = utils
					.getProperties(Resources.propertyFilePath + File.separator
							+ column.get(i) + nameSuffix_prop);
			getAllOtherKeyMap(otherProp);
		}
		showMsg(shell, "系统按键恢复成功。", 0);
		load_Properties();
	}

	/**
	 * 读取后缀为“自定义遥控器.property”的文件。设置下拉列表内容
	 */
	private void setCustomFileList() {
		combo_CustomFileList.removeAll();
		String[] customFileList = null;
		File f = new File(Resources.propertyFilePath);
		customFileList = f.list();
		for (String str : customFileList) {
			if (str.endsWith(nameSuffix)) {
				combo_CustomFileList.add(str.substring(0, str.length()
						- nameSuffix_prop.length()));
			}
		}
		//如果文件列表不为空，则将文件列表第一项设置为下拉菜单的默认值。
		if (combo_CustomFileList.getItemCount() > 0) {
			combo_CustomFileList.setText(combo_CustomFileList.getItem(0));
			setCustomTable();
		}
		
	}

	/**
	 * 根据下拉列表combo_CustomFileList中选择的文件设置UI显示
	 */
	private void setCustomTable() {
		tableCustom.removeAll();
		String filename = combo_CustomFileList.getText() + nameSuffix_prop;
		String filenameHead = filename.substring(0, filename.length()
				- nameSuffix.length());
		//根据文件名“filenameHead”设置每一列的名称
		for (int i = 0; i < Resources.ENCODE_PREFIX.length; i++) {
			String str = Resources.ENCODE_PREFIX[i];
			TableColumn tbcTmp = tableCustom.getColumn(i + 1);
			tbcTmp.setText(str + filenameHead);
		}
		keynameColumn = getkeyNames(filename);
		// Collections.sort(keynameColumn);
		Iterator<String> itr = keynameColumn.iterator();
		while (itr.hasNext()) {
			String keyname = itr.next();
			TableItem tabTmp = new TableItem(tableCustom, SWT.NONE);
			if (htChineseName.get(keyname) != null) {
				tabTmp.setText(htChineseName.get(keyname));
			} else {
				tabTmp.setText(keyname);
			}
			keyname = filenameHead + "_" + keyname;
			//根据每一行的名称，设置每一列的值
			if (customProperties.getProperty("NEC_" + keyname) != null) {
				tabTmp.setText(colum_NEC,
						customProperties.getProperty("NEC_" + keyname));
			}
			if (customProperties.getProperty("RC5_" + keyname) != null) {
				tabTmp.setText(colum_RC5,
						customProperties.getProperty("RC5_" + keyname));
			}
			if (customProperties.getProperty("KEYCODE_" + keyname) != null) {
				tabTmp.setText(colum_KEYCODE,
						customProperties.getProperty("KEYCODE_" + keyname));
			}
		}

	}

	/**
	 * 初始化自定义键值表格
	 */
	private void initCustomTable() {
		for (String str : Resources.ENCODE_PREFIX) {
			TableColumn tbcTmp = new TableColumn(tableCustom, SWT.NONE);
			tbcTmp.setWidth(150);
			tbcTmp.setText(str.substring(0, str.length() - 1));
		}
	}

	/**
	 * 获取按键名称列的各个按键名，同时加载对应文件的Properties
	 */
	private ArrayList<String> getkeyNames(String filename) {
		Set<String> set = new HashSet<String>();
		ArrayList<String> customKeynames = new ArrayList<String>();
		int filenamelength = filename.substring(0,
				filename.length() - nameSuffix.length()).length();
		String customFilepath = Resources.propertyFilePath + File.separator
				+ filename;
		// 加载对应Properties文件
		customProperties = utils.getProperties(customFilepath);
		File f = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		try {
			f = new File(customFilepath);
			in = new InputStreamReader(new FileInputStream(f));
			br = new BufferedReader(in);
			String propKeyname = br.readLine();
			while (propKeyname != null) {
				//发现注释，跳过
				if (propKeyname.startsWith("#")) {
					propKeyname = br.readLine();
					continue;
				}
				//发现空行，跳过
				if ("".equals(propKeyname)) {
					propKeyname = br.readLine();
					continue;
				}
				propKeyname = propKeyname.substring(
						propKeyname.indexOf("_") + 1, propKeyname.indexOf("="));
				propKeyname = propKeyname.substring(filenamelength + 1);
				if (set.add(propKeyname)) {
					customKeynames.add(propKeyname);
				}
				propKeyname = br.readLine();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return customKeynames;
	}

	/**
	 * 加载自定义按键的中文名称。
	 */
	private boolean loadChineseName() {
		String encoding = "UTF-8";
		File keyFile = new File(Resources.KeyNamePath);
		if (!keyFile.exists()) {
			return false;
		}
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		htChineseName.clear();
		//加载remoteOption文件夹下的keyname文件，中英文键值对保存在htChinese中。
		try {
			read = new InputStreamReader(new FileInputStream(keyFile), encoding);
			bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				int index = line.indexOf("=");
				htChineseName.put(line.substring(0, index),
						line.substring(index + 1));
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
	 * 读取所有系统Properties文件,存储到htKeymapTmp中
	 */
	private void getAllSystemKeymap(HashMap<String, String> htKeymapTmp) {
		htKeymapTmp.clear();
		// column中存储了所有系统文件名称
		for (int i = 0; i < column.size(); i++) {
			Properties otherProp = utils
					.getProperties(Resources.propertyFilePath + File.separator
							+ column.get(i) + nameSuffix_prop);
			Enumeration<Object> propKeys = otherProp.keys();
			String propKey = "";
			while (propKeys.hasMoreElements()) {
				propKey = (String) propKeys.nextElement();
				htKeymapTmp.put(propKey, otherProp.getProperty(propKey));
			}
		}

	}
}
