package com.android.instrument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.android.data.Constants;

public class AsmUtils {
	
	/* flag == 0 --> write to log only,
	 * flag == 1 --> write to data file only
	 * flag == 2 --> write to both
	 */
	public static void addPrintoutStatement(MethodVisitor mv, String fileName, String tag, String msg, int flag) {
		addPrintoutStatementInternal(mv, fileName, tag, Constants.APP_NAME + " ** " + msg, flag);
	}
	
	public static void addPrintoutStatementInternal(MethodVisitor mv, String fileName, String tag, String msg, int flag) {
		//mv.visitVarInsn(Opcodes.ALOAD, 0);
		
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "julia/XXX_Utils", "getInstance", "()Ljulia/XXX_Utils;", false);
		
		mv.visitLdcInsn(fileName);
		mv.visitLdcInsn(tag);
		mv.visitLdcInsn(msg);
		if (flag == 0) {
			mv.visitInsn(Opcodes.ICONST_0);
		}
		else if (flag == 1) {
			mv.visitInsn(Opcodes.ICONST_1);
		}
		else {
			mv.visitInsn(Opcodes.ICONST_2);
		}
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "julia/XXX_Utils", "XXX_julia_log", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", false);
//		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "julia/XXX_Utils", "XXX_julia_log", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", false);
	}
	
	public static void addExcludedPopupForView(MethodVisitor mv) {
		// adds: android.widget.Toast.makeText(getApplicationContext(), "Excluded", Toast.LENGTH_LONG).show();
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		addToast(mv);
	}
	
	//FIXME walmart search menu 
	//I/filter  ( 1546): FILTERED_CALL android/support/v7/app/ActionBarActivityDelegateICS$WindowCallbackWrapper.onMenuItemSelected(ILandroid/view/MenuItem;)Z
	public static void addExcludedPopupForMenu(MethodVisitor mv) {
		// adds: android.widget.Toast.makeText(item.getActionView().getContext().getApplicationContext(), "Excluded", android.widget.Toast.LENGTH_LONG).show();
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "android/view/MenuItem", "getActionView", "()Landroid/view/View;", true);
		addToast(mv);
	}

	public static void addToast(MethodVisitor mv) {
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/view/View", "getContext", "()Landroid/content/Context;", false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/content/Context", "getApplicationContext", "()Landroid/content/Context;", false);
		mv.visitLdcInsn(Constants.EXCLUDED);
		mv.visitInsn(Opcodes.ICONST_1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);
	}
	
	public static void addCallToSuper(MethodVisitor mv, final String name, final String desc, final String superClass) {
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		
		//calculate the number of parameters
		Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
		Matcher matcher = pattern.matcher(desc);
		int counter = 0;
		while(matcher.find()) {
			counter++;
		}
		
		for (int i = 1; i <= counter; i++) {
			mv.visitVarInsn(Opcodes.ALOAD, i);
		}
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, superClass, name, desc, false);
	}
	
	public static void addPrintStackTrace(MethodVisitor mv) {
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "dumpStack", "()V", false);
	}
	
	public static void addReturnStatement(MethodVisitor mv, final String returnType) {
		
		if ("V".equals(returnType)) {
			mv.visitInsn(Opcodes.RETURN);
		}
		else if ("I".equals(returnType) || "Z".equals(returnType) || "B".equals(returnType) || 
				 "C".equals(returnType) || "S".equals(returnType)) {
			mv.visitInsn(Opcodes.ICONST_0);
			mv.visitInsn(Opcodes.IRETURN);
		}
		else if ("L".equals(returnType) || "J".equals(returnType)) {
			mv.visitInsn(Opcodes.LCONST_0);
			mv.visitInsn(Opcodes.LRETURN);
		}
		else if ("F".equals(returnType)) {
			mv.visitInsn(Opcodes.FCONST_0);
			mv.visitInsn(Opcodes.FRETURN);
		}
		else if ("D".equals(returnType)) {
			mv.visitInsn(Opcodes.DCONST_0);
			mv.visitInsn(Opcodes.DRETURN);
		}
		else {
			mv.visitInsn(Opcodes.ACONST_NULL);
			mv.visitInsn(Opcodes.ARETURN);
		}
	}
	
	public static void addThrow(MethodVisitor mv, final String exception, final String msg) {
		mv.visitTypeInsn(Opcodes.NEW, exception);
		mv.visitInsn(Opcodes.DUP);
		mv.visitLdcInsn(msg);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, exception, "<init>", "(Ljava/lang/String;)V", false);
		mv.visitInsn(Opcodes.ATHROW);
	}

}
