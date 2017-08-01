package com.android.instrument;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import com.android.ClassVisitors.InstrumentCallsClassVisitor;
import com.android.ClassVisitors.PreprocessClassVisitor;
import com.android.data.ClassHierarchy;
import com.android.data.Constants;
import com.android.data.DataManager;

public class Instrumenter {
	
	private static final String CLASS_FILE_REGEX = "[^\\s]+\\.class$";
	private static final String INSTRUMENT = "instrument";
	private static final String PREPROCESS = "preprocess";
		
	public static DataManager dm = new DataManager();
	
	public static void main(String[] args) {
		
		
		Utils.runBatchFile(Constants.SCRIPTS_DIR + "asmPrepare.bat " + Constants.APP_NAME);
		
		long start = System.currentTimeMillis();
			
		String instrumentationType = INSTRUMENT;
		
		//build class hierarchy
		
		ClassHierarchy.getInstance().reset();
		loopOverFiles(PREPROCESS);
		
		loopOverFiles(instrumentationType);

		dm.dumpMethodsToFile(Constants.METHOD_DATA_FILE); 
		dm.dumpStatementsToFile(Constants.STATEMENT_DATA_FILE);

		long end = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
		System.out.println("Execution time is " + formatter.format(end - start) + " m:ss:SSS");
		System.out.println(instrumentationType + " execution time is " + (end - start) + " ms");
		
		Utils.runBatchFile(Constants.SCRIPTS_DIR + "asmPack.bat " + Constants.APP_NAME + " " + instrumentationType);
//	    Utils.runBatchFile(Constants.SCRIPTS_DIR + "deploy " + Constants.APP_NAME + " " + instrumentationType);
	}

	private static void loopOverFiles(String instrumentationType) {
		File inputClassFolder = new File(Constants.DECOMPILED_INPUT_DIR + Constants.APP_NAME);
		File instrumentedClassFolder = new File(Constants.ASM_DECOMPILED_OUTPUT_DIR + Constants.APP_NAME + "_" + instrumentationType);
			
		Collection<File> classFiles = FileUtils.listFiles(inputClassFolder, new RegexFileFilter(CLASS_FILE_REGEX), DirectoryFileFilter.DIRECTORY);

		try {
			for (File classFile : classFiles) {
							
				byte[] inBytes = FileUtils.readFileToByteArray(classFile);
				
				final ClassReader classReader = new ClassReader(inBytes);
	            final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

	            ClassVisitor cv = null;
	            
	            if (INSTRUMENT.equals(instrumentationType)) {
	            	cv = new InstrumentCallsClassVisitor(classWriter, instrumentationType);	            	
	            }
	            else if (PREPROCESS.equals(instrumentationType)) {
	            	cv = new com.android.ClassVisitors.PreprocessClassVisitor(); 
	            }
	           
	            classReader.accept(cv, 0);
	            
	            if (!PREPROCESS.equals(instrumentationType)) {
	            	final byte[] outBytes = classWriter.toByteArray();
		            
					File instrumentedClassFile = new File(classFile.getPath().replace(inputClassFolder.getPath(), instrumentedClassFolder.getPath()));
					FileUtils.writeByteArrayToFile(instrumentedClassFile, outBytes);
	            }	            
			} 
			
//			if (!PREPROCESS.equals(instrumentationType)) {
//				FileUtils.copyFileToDirectory(new File(Constants.BASE_DIR + "XXX_Utils.class"), new File(instrumentedClassFolder.getPath() + "\\dulingl\\"));
//				
//			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}