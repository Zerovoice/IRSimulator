package com.hisense.autotest.common;

import java.io.File;

public class Resources {

    public final static String FORMAT_TIME_PATH = "yyyy-MM-dd_HH-mm-ss";
    public final static String FORMAT_TIME_SUFFIX = "_yyyyMMddHHmmss";

	// 选项卡标题
	public final static String OPTION_MANUAL = "录制回放模式";
	public final static String OPTION_RANDOM = "随机操作模式";
	public final static String OPTION_EXCUTE = "读取执行模式";
    public final static String OPTION_SCRIPT_TRANS = "脚本转换模式";

	// 遥控器按键Key
	public final static String PROPKEY_POWER = "POWER";
	public final static String PROPKEY_SOURCE = "SOURCE";
	public final static String PROPKEY_ONE = "ONE";
	public final static String PROPKEY_TWO = "TWO";
	public final static String PROPKEY_THREE = "THREE";
	public final static String PROPKEY_FOUR = "FOUR";
	public final static String PROPKEY_FIVE = "FIVE";
	public final static String PROPKEY_SIX = "SIX";
	public final static String PROPKEY_SEVEN = "SEVEN";
	public final static String PROPKEY_EIGHT = "EIGHT";
	public final static String PROPKEY_NINE = "NINE";
	public final static String PROPKEY_ZERO = "ZERO";
	public final static String PROPKEY_PRE_CH = "PRE_CH";
	public final static String PROPKEY_CH_LIST = "CH_LIST";
	public final static String PROPKEY_VOL_ADD = "VOL_ADD";
	public final static String PROPKEY_VOL_SUB = "VOL_SUB";
	public final static String PROPKEY_CH_ADD = "CH_ADD";
	public final static String PROPKEY_CH_SUB = "CH_SUB";
	public final static String PROPKEY_MEDIA = "MEDIA";
	public final static String PROPKEY_SET = "SET";
	public final static String PROPKEY_EPG = "EPG";
	public final static String PROPKEY_MUTE = "MUTE";
	public final static String PROPKEY_ECO = "ECO";
	public final static String PROPKEY_INFO = "INFO";
	public final static String PROPKEY_MENU = "MENU";
	public final static String PROPKEY_FAV = "FAV";
	public final static String PROPKEY_RETURN = "RETURN";
	public final static String PROPKEY_EXIT = "EXIT";
	public final static String PROPKEY_UP = "UP";
	public final static String PROPKEY_DOWN = "DOWN";
	public final static String PROPKEY_LEFT = "LEFT";
	public final static String PROPKEY_RIGHT = "RIGHT";
	public final static String PROPKEY_OK = "OK";
	public final static String PROPKEY_HI_SMART_AT = "HI_SMART_AT";
	public final static String PROPKEY_RED_032C = "RED_032C";
	public final static String PROPKEY_355C = "355C";
	public final static String PROPKEY_YELLOWC = "YELLOWC";
	public final static String PROPKEY_HEXACHROME_CYGANC = "HEXACHROME_CYGANC";
	public final static String PROPKEY_PLAY = "PLAY";
	public final static String PROPKEY_PAUSE = "PAUSE";
	public final static String PROPKEY_STOP = "STOP";
	public final static String PROPKEY_PVR = "PVR";
	public final static String PROPKEY_FALL_BACK = "FALL_BACK";
	public final static String PROPKEY_FAST_FORWARD = "FAST_FORWARD";
	public final static String PROPKEY_START = "START";
	public final static String PROPKEY_ENDING = "ENDING";
	public final static String PROPKEY_TEXT = "TEXT";
	public final static String PROPKEY_STILL = "STILL";
	public final static String PROPKEY_SIZE = "SIZE";
	public final static String PROPKEY_T_SHIFT = "T_SHIFT";
	public final static String PROPKEY_P_MODE = "P_MODE";
	public final static String PROPKEY_S_MODE = "S_MODE";
	public final static String PROPKEY_LANGUAGE = "LANGUAGE";
	public final static String PROPKEY_SUBT = "SUBT";
	// 遥控器按键Key
	public final static String PROPKEY_HOME = "HOME";
	public final static String PROPKEY_STAR = "STAR";
	public final static String PROPKEY_POUND= "POUND";
	// 遥控器按键Key 工厂遥控器
	public final static String PROPKEY_FAC_M = "FAC_M";
	public final static String PROPKEY_FAC_SAVE = "FAC_SAVE";
	public final static String PROPKEY_FAC_PATTERN = "FAC_PATTERN";
	public final static String PROPKEY_FAC_AGING = "FAC_AGING";
	public final static String PROPKEY_FAC_BALANCE = "FAC_BALANCE";
	public final static String PROPKEY_FAC_ADC = "FAC_ADC";
	public final static String PROPKEY_FAC_3D = "FAC_3D";
	public final static String PROPKEY_FAC_PC = "FAC_PC";
	public final static String PROPKEY_FAC_ATV = "FAC_ATV";
	public final static String PROPKEY_FAC_DTV = "FAC_DTV";
	public final static String PROPKEY_FAC_YPBPR="FAC_YPBPR";
	public final static String PROPKEY_FAC_VGA="FAC_VGA";
	public final static String PROPKEY_FAC_HDMI1="FAC_HDMI1";
	public final static String PROPKEY_FAC_HDMI2="FAC_HDMI2";
	public final static String PROPKEY_FAC_HDMI3="FAC_HDMI3";
	public final static String PROPKEY_FAC_HDMI4="FAC_HDMI4";
	public final static String PROPKEY_FAC_HDMI5="FAC_HDMI5";
	public final static String PROPKEY_FAC_AV1="FAC_AV1";
	public final static String PROPKEY_FAC_AV2="FAC_AV2";
	public final static String PROPKEY_FAC_AV3="FAC_AV3";

	// 遥控器编码
	public final static int ENCODE_NEC = 0;
    public final static int ENCODE_RC5 = 1;
    public final static int ENCODE_KEYCODE = 2;
    public final static String[] ENCODE_PREFIX = new String[] { "NEC_", "RC5_", "KEYCODE_" };

	// 串口区分
	public final static int TYPE_COM_IR = 0;
	public final static int TYPE_COM_DEV = 1;

	// 选项卡的模式区分
	public final static int MODE_MANUAL = 0;
	public final static int MODE_RANDOM = 1;
	public final static int MODE_EXCUTE = 2;

	// 脚本列
	public final static int SCRIPT_COL_INDEX = 0;
	public final static int SCRIPT_COL_KEY = 1;
	public final static int SCRIPT_COL_CONTENT = 2;
	public final static int SCRIPT_COL_INTERVAL = 3;
	public final static int SCRIPT_COL_ASSERT = 4;
	public final static int SCRIPT_COL_NAME = 5;
	public final static int SCRIPTFILE_COL_INDEX = 0;
	public final static int SCRIPTFILE_COL_PATH = 1;

    //测试执行结果 路径
    public final static String TEST_RST_PATH = "TestResults";
	//测试执行结果
    public final static int TEST_RST_PASS = 0;
    public final static int TEST_RST_FAIL = 1;
    public final static int TEST_RST_NOTRUN = 2;
	
	// 随机条件 按键执行区分
	public final static int SCR_LIMIT_TIME = 0;
	public final static int SCR_LIMIT_TIMES = 1;
	// 随机条件 按键范围区分
	public final static int KEY_RANGE_NN = 0;// 常用按键，电源键除外
	public final static int KEY_RANGE_NC = 1;// 常用按键，含电源键
	public final static int KEY_RANGE_AN = 2;// 全按键，电源键除外
	public final static int KEY_RANGE_AC = 3;// 全按键，含电源键

    // 文字
    public final static String TEXT_OFF_CONN = "未连接";
    public final static String TEXT_ON_CONN = "已连接";

    // 遥控器编码
    public final static String[] ENCODES = new String[] { "NEC码", "RC5码", "keycode" };
    // 待转换脚本列
    public final static int TRANS_COL_PATH = 0;
    
    // 遥控器系统码
    public final static String SYSCODE_NEC = "SYSCODE_NEC";
    public final static String SYSCODE_RC5 = "SYSCODE_RC5";
    public final static String SYSCODE_FAC = "SYSCODE_FAC";
    
    //按键名称
    public final static String NEC_NORMAL_PART_KEYS_NAME = "NEC_NORMAL_PART_KEYS_NAME";
    public final static String RC5_NORMAL_PART_KEYS_NAME = "RC5_NORMAL_PART_KEYS_NAME";
    public final static String KEYCODE_NORMAL_PART_KEYS_NAME = "KEYCODE_NORMAL_PART_KEYS_NAME";
    public final static String NEC_NORMAL_PART_KEYS = "NEC_NORMAL_PART_KEYS";
    public final static String RC5_NORMAL_PART_KEYS = "RC5_NORMAL_PART_KEYS";
    public final static String KEYCODE_NORMAL_PART_KEYS = "KEYCODE_NORMAL_PART_KEYS";
    
    public final static String KEYCODE ="keycode";
    public final static String NEC ="NEC" ;
    public final static String RC5 ="RC5";
    
    public final static String comment = "#";
    public final static String sx6Name="(离散码)";
    
    //文件路径
    public static String resourcesPath = "./config"+File.separator+"resources";
    public static String propertyFilePath = "./config" + File.separator+ "keymap_properties";
    public static String propertyFilePathBac = "./config" + File.separator+ "keymap_properties_backup";
    public static String pageInfoPath = "./config" + File.separator + "remoteOption";
    public static String KeyNamePath = pageInfoPath+File.separator + "keyName.txt";
    public static String customKeysPath = pageInfoPath+File.separator + "customKeys";
    public static String discretePath = pageInfoPath+File.separator + "离散码_customKeys";
    public static String sequencePath = resourcesPath+File.separator+"sequence.xml";
    
    //波特率
    public final static int serialPortBaud=115200;  //电视串口
    public final static int serialReceiveBaud=9600;  //红外接收小板波特率，红外小板波特率
	
}
