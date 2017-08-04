package com.android.ClassVisitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.android.data.ClassHierarchy;
import com.android.data.Constants;
import com.android.data.Method;
import com.android.data.Statement;
import com.android.instrument.AsmUtils;

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
			if ((owner.equals("android/location/LocationManager") && name.equals("requestLocationUpdates"))) {
				System.out.println(Constants.SOURCE_MARKER + owner + "." + name + desc +
				" from " + methodSigniture);
//				AsmUtils.addPrintoutStatement(mv, logFileName, instrumentationType, 
//						"CHECK CONNECT from " + methodSigniture + " via " + owner + "." + name, 2);
//				AsmUtils.addPrintStackTrace(mv);
				//mv.visitInsn(ICONST_0);
			}
		}
    };
	

    
}
