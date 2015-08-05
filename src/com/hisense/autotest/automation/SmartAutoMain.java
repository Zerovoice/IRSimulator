
package com.hisense.autotest.automation;

import java.io.File;

import com.hisense.autotest.common.Resources;

public class SmartAutoMain extends SmartAuto {

    public static void main(String[] args) {
        String propPath = "./config/keymap_properties";
        String settingPath = "./config";
        if (args != null && args.length > 3) {
            testRstsPath = rootPath + Resources.TEST_RST_PATH + File.separator
                    + Resources.TEST_RST_PATH + "_遥控器" + File.separator;

            rootPath = new File(args[3]).getAbsolutePath() + File.separator;
            propPath = rootPath + "config/keymap_properties";
            settingPath = rootPath + "config";
            
            
            Resources.resourcesPath = settingPath+File.separator+"resources";
            Resources.propertyFilePath = settingPath + File.separator+ "keymap_properties";
            Resources.propertyFilePathBac = settingPath + File.separator+ "keymap_properties_backup";
            Resources.pageInfoPath = settingPath + File.separator + "remoteOption";
            Resources.KeyNamePath = Resources.pageInfoPath+File.separator + "keyName.txt";
            Resources.customKeysPath = Resources.pageInfoPath+File.separator + "customKeys";
            Resources.discretePath = Resources.pageInfoPath+File.separator + "离散码_customKeys";
            Resources.sequencePath = Resources.resourcesPath+File.separator+"sequence.xml";
        }
        //加载系统码
        properties = utils.getProperties(propPath + File.separator + "keymap.properties");
        getSysMapInfo();
        //加载自定义按键的行和列
        String keytypeFilepath = propPath + File.separator + "keytypes";
 		setColumnAndRow(keytypeFilepath);
        //加载setting.xml配置文件。
        settings = utils.readXMLConfigs(settingPath + File.separator + "Settings.xml");
        //加载所有按键
        getKeyMapInfo(propPath);
        getSettingsInfo();
        PgIRSimulator dlg = new PgIRSimulator();
        dlg.open(args);
      
    }

}
