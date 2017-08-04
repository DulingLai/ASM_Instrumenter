package com.android.data;

public class Constants {

	
//top 20
	
	//tests
//	public static String APP_NAME = "com.passportparking.mobile.toronto";
//	public static String APP_NAME = "com.waze";
	public static String APP_NAME = "com.kartatech.karta.gps";
	
	
	//public static String APP_NAME = "com.g6677.android.princesshs";
	//public static String APP_NAME = "com.google.android.apps.translate";

	//public static String APP_NAME = "com.example.jj";
	//public static String APP_NAME = "com.example.testreflectionapp";
	//public static String APP_NAME = "com.cleanmaster.mguard";
	//public static String APP_NAME = "com.facebook.katana";
	//public static String APP_NAME = "com.appershopper.ios7lockscreen";
	
	//public static String APP_NAME = "com.netthreads.android.noiz2";
	
	public static String SCRIPTS_DIR = "C:\\Users\\zackl\\Desktop\\ResearchTool\\ASM\\ASM_Instrumenter\\scripts\\";
	
	public static String SOOT_ANDROID_PLATFORM = "C:\\Users\\zackl\\Desktop\\FlowDroid_Soot\\libs\\AndroidPlatform\\";
	//public static String SOOT_CALL_GRAPH_DIR = BASE_DIR + "callgraphs/";
	//public static String SOOT_INPUT_DIR = BASE_DIR + "apks/orig/";
	//public static String SOOT_OUTPUT_DIR = BASE_DIR + "apks/sootOutput/";
	
	public static String BASE_DIR = "C:\\Users\\zackl\\Desktop\\ResearchTool\\ASM\\ASM_Instrumenter\\";

	public static String DEX2JAR_PATH = "C:\\Users\\zackl\\Desktop\\ResearchTool\\dex2jar-2.1\\";
	public static String INPUT_DIR = BASE_DIR + "testApk\\";
	public static String DECOMPILED_INPUT_DIR = BASE_DIR + "testApk\\decompiled\\orig\\";
	public static String ASM_DECOMPILED_OUTPUT_DIR = BASE_DIR + "testApk\\decompiled\\asmOutput\\";
	public static String ASM_OUTPUT_DIR = BASE_DIR + "testApk\\asmOutput\\";
	
	public static String METHOD_DATA_FILE = BASE_DIR + "logs\\" + APP_NAME + ".methods.log";
	public static String STATEMENT_DATA_FILE = BASE_DIR + "logs\\" + APP_NAME + ".statements.log";
	
	public static String EXECUTED_LOG_FILE = BASE_DIR + "logs\\" + APP_NAME + "_instrument_dev.log";
	
	public static final String INST_DEV_LOG_FILE = BASE_DIR + "dev_logs\\" + APP_NAME + "\\instrument.log";
	public static final String FILTER_DEV_LOG_FILE = BASE_DIR + "dev_logs\\" + APP_NAME + "\\filter.log";
	
	public static String LOG_MARKER = "INST_CALL ";
	public static String FILTERED_MARKER = "FILTERED_CALL ";
	public static String SOURCE_MARKER = "_SOURCE_ ";
	public static String SINK_MARKER = "_SINK_ ";
	public static String EXCLUDED = "Excluded";
}
