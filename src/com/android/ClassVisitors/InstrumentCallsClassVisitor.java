package com.android.ClassVisitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.android.data.ClassHierarchy;
import com.android.data.Constants;
import com.android.data.Method;
import com.android.data.Statement;

public class InstrumentCallsClassVisitor extends BasicClassVisitor {
    
	public InstrumentCallsClassVisitor(ClassVisitor classvisitor, String instrumentationType) {
		super(classvisitor, instrumentationType);
	}

	@Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature,
            final String[] exceptions) {
        
		MethodVisitor mv = classWriterVisitor.visitMethod(access, name, desc, signature,
                exceptions);

        if (mv == null
                || (access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) > 0) {
            return mv;
        }

        return new InstrumentMethodAdapter(Opcodes.ASM5, mv, this.name, access, name, desc);
    }
	

	
	private class InstrumentMethodAdapter extends BasicMethodAdapter {
		
		protected InstrumentMethodAdapter(final int api, final MethodVisitor mv,
	            String className, final int access, final String name, final String desc) {
			super(api, mv, className, access, name, desc, Constants.INST_DEV_LOG_FILE, InstrumentCallsClassVisitor.this.instrumentationType);
		}
		
		@Override
        protected void onMethodEnter() {
		  Method m = new Method(methodSigniture);
		  dm.addMethod(m);
//		  System.out.println("Entering " + methodSigniture);
		  // Duling - enable here to addPrintOutStatement
		  // com.android.instrument.AsmUtils.addPrintoutStatement(mv, Constants.INST_DEV_LOG_FILE, instrumentationType, Constants.LOG_MARKER + methodSigniture, 2);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		
//			if (isConnection(owner, name)) { 
//				AsmUtils.addPrintoutStatement(mv, logFileName, instrumentationType, 
//						"CONNECT from " + methodSigniture + " via " + owner + "." + name, 2);
//				AsmUtils.addPrintStackTrace(mv);
//			}
//			
//			if ((owner.equals("android/net/NetworkInfo") && name.equals("isConnected"))) {
//				AsmUtils.addPrintoutStatement(mv, logFileName, instrumentationType, 
//						"CHECK CONNECT from " + methodSigniture + " via " + owner + "." + name, 2);
//				AsmUtils.addPrintStackTrace(mv);
//				//mv.visitInsn(ICONST_0);
//			}
			
			
			//sources
			if (
					(ClassHierarchy.getInstance().isAncestors(owner, "org/apache/http/client/HttpClient") && name.equals("execute")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "org/apache/http/impl/CloseableHttpClient") && name.equals("execute")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "org/apache/http/impl/client/AbstractHttpClient") && name.equals("execute")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "org/apache/http/impl/client/DefaultHttpClient") && name.equals("execute")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "android/net/http/AndroidHttpClient") && name.equals("execute")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "javax/net/ssl/HttpsURLConnection") && name.equals("connect")) ||
					
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/URL") && name.equals("openConnection")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/URL") && name.equals("openStream")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/URLConnection") && name.equals("connect")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/HttpURLConnection") && name.equals("connect")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/HttpsURLConnection") && name.equals("connect")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/JarURLConnection") && name.equals("connect")) ||
					
					(ClassHierarchy.getInstance().isAncestors(owner, "java/net/Socket") && name.equals("getOutputStream")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "javax/net/ssl/SSLSocket") && name.equals("getOutputStream")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "org/apache/harmony/xnet/provider/jsse/OpenSSLSocketImpl") && name.equals("getOutputStream")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "libcore/io/Posix") && name.equals("<init>")) ||
					
					
					//(ClassHierarchy.getInstance().isAncestors(owner, "android/content/Context") && name.equals("startService")) ||
//					(ClassHierarchy.getInstance().isAncestors(owner, "android/content/Context") && name.equals("bindService")) ||
					(ClassHierarchy.getInstance().isAncestors(owner, "android/os/IBinder") && name.equals("transact")) 
					//(owner.equals("android/os/Parcel") && name.equals("obtain")) ||
					//(owner.equals("android/os/Parcel") && name.equals("obtain")) 
					//(owner.equals("android/os/Parcel") && name.equals("obtain")) 
			 ){
//						com.android.instrument.AsmUtils.addPrintoutStatement(mv, logFileName, instrumentationType, 
//						Constants.SOURCE_MARKER + owner + "." + name + desc +
//						" from " + methodSigniture, 2);
//						System.out.println(Constants.SOURCE_MARKER + owner + "." + name + desc +
//								" from " + methodSigniture);
//						com.android.instrument.AsmUtils.addPrintStackTrace(mv);
						Statement statement = new Statement(methodSigniture, owner + "." + name + desc, 0);
						dm.addStatement(statement);
			}
			
			//sinks
			if (					
					(owner.equals("android/view/ViewGroup") && name.equals("addView")) ||
					(owner.equals("android/view/ViewGroup") && name.equals("addFocusables")) ||
					(owner.equals("android/view/ViewGroup") && name.equals("addTouchables")) ||
			        (owner.equals("android/view/ViewGroup") && name.equals("addChildrenForAccessibility")) ||
				    (owner.equals("android/widget/TextSwitcher") && name.equals("addView")) ||
					(owner.equals("android/widget/ViewSwitcher") && name.equals("addView")) ||
			        (owner.equals("android/view/WindowManagerImpl") && name.equals("addView")) ||
			        (owner.equals("android/view/WindowManagerImpl$CompatModeWrapper") && name.equals("addView")) ||
				
			        (ClassHierarchy.getInstance().isAncestors(owner, "android/view/ViewManager") && name.equals("addView")) ||
				    (ClassHierarchy.getInstance().isAncestors(owner, "android/view/ViewManager") && name.equals("updateViewLayout")) ||
				    
				    (owner.equals("android/app/Dialog") && name.equals("setContentView")) ||
					(owner.equals("android/support/v7/app/ActionBarActivityDelegate") && name.equals("setContentView")) ||
					(owner.equals("android/support/v7/app/ActionBarActivityDelegateBase") && name.equals("setContentView")) ||
					(owner.equals("android/support/v7/app/ActionBarActivityDelegateICS") && name.equals("setContentView")) ||

					(owner.equals("android/webkit/WebView") && name.equals("loadData")) ||
					(owner.equals("android/webkit/WebView") && name.equals("loadDataWithBaseURL")) ||
					(owner.equals("android/webkit/WebView") && name.equals("loadUrl")) ||
					
					(owner.equals("android/view/View") && name.equals("onLayout")) ||
					(owner.equals("android/view/View") && name.equals("layout"))  ||
					(owner.equals("android/view/View") && name.equals("onDraw"))  ||
					(owner.equals("android/view/View") && name.equals("onAttachedToWindow"))  ||

					(owner.equals("android/widget/ImageView") && name.equals("onLayout")) ||
					(owner.equals("android/widget/ImageView") && name.equals("layout"))  ||
					(owner.equals("android/widget/ImageView") && name.equals("onDraw"))  ||
					(owner.equals("android/widget/ImageView") && name.equals("onAttachedToWindow"))  ||

					(owner.equals("android/inputmethodservice/KeyboardView") && name.equals("onLayout")) ||
					(owner.equals("android/inputmethodservice/KeyboardView") && name.equals("layout"))  ||
					(owner.equals("android/inputmethodservice/KeyboardView") && name.equals("onDraw"))  ||
					(owner.equals("android/inputmethodservice/KeyboardView") && name.equals("onAttachedToWindow"))  ||

					(owner.equals("android/widget/AnalogClock") && name.equals("onLayout")) ||
					(owner.equals("android/widget/AnalogClock") && name.equals("layout"))  ||
					(owner.equals("android/widget/AnalogClock") &&name.equals("onDraw"))  ||
					(owner.equals("android/widget/AnalogClock") && name.equals("onAttachedToWindow"))  ||

					(owner.equals("android/widget/TextView") && name.equals("onLayout")) ||
					(owner.equals("android/widget/TextView") && name.equals("layout"))  ||
					(owner.equals("android/widget/TextView") && name.equals("onDraw"))  ||
					(owner.equals("android/widget/TextView") && name.equals("onAttachedToWindow"))  ||
					(owner.equals("android/widget/TextView") && name.equals("append"))  ||
					(owner.equals("android/widget/TextView") && name.equals("setText"))
			) {
//						com.android.instrument.AsmUtils.addPrintoutStatement(mv, logFileName, instrumentationType, 
//								Constants.SINK_MARKER + owner + "." + name + desc +
//								" from " + methodSigniture, 2);
//						com.android.instrument.AsmUtils.addPrintStackTrace(mv);
						Statement statement = new Statement(methodSigniture, owner + "." + name + desc, 1);
						dm.addStatement(statement);
			}
		}
    };
	

    
}
