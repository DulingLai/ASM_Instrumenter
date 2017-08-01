package com.android.ClassVisitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import com.android.data.DataManager;
import com.android.instrument.Instrumenter;

public class BasicClassVisitor extends ClassNode {

	protected ClassVisitor classWriterVisitor;
	protected com.android.data.DataManager dm = com.android.instrument.Instrumenter.dm;
	protected String instrumentationType;

	public BasicClassVisitor(ClassVisitor classvisitor, String instrumentationType) {
		super(Opcodes.ASM5);
		classWriterVisitor = classvisitor;
		this.instrumentationType = instrumentationType;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		// TODO Auto-generated method stub
		super.visit(version, access, name, signature, superName, interfaces);
	}
	

	
/*	
	private void addPrintingMethod() {
//		 add the logging method
//		   * this inserts the following code:
//		   * 
//		   * public void XXX_julia_log(String fileName, String tag, String msg) {
//				android.util.Log.i(tag, msg);
//				try {
//            		java.io.BufferedWriter output = new java.io.BufferedWriter(new java.io.FileWriter( new java.io.File(fileName), true));
//            		int pid = Integer.parseInt(new java.io.File("/proc/self").getCanonicalFile().getName());
//            		output.write(tag + " ( " + pid + " ) " + msg);
//            		output.close();
//          		}
//          		catch (java.io.IOException e) {
//	        		android.util.Log.i(tag , "exception " + e.getMessage());
//            		e.printStackTrace(); 
//          		}
//			}
		  
		  
		  MethodVisitor mv = classWriterVisitor.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "XXX_julia_log", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", null, null);
		  mv.visitCode();
		  Label l0 = new Label();
		  Label l1 = new Label();
		  Label l2 = new Label();
		  mv.visitTryCatchBlock(l0, l1, l2, "java/io/IOException");
		  Label l3 = new Label();
		  mv.visitLabel(l3);
		  
		  mv.visitVarInsn(Opcodes.ALOAD, 1);
		  mv.visitVarInsn(Opcodes.ALOAD, 2);
		  mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
		  mv.visitInsn(Opcodes.POP);
		  mv.visitLabel(l0);
		  
		  mv.visitTypeInsn(Opcodes.NEW, "java/io/BufferedWriter");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitTypeInsn(Opcodes.NEW, "java/io/FileWriter");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitTypeInsn(Opcodes.NEW, "java/io/File");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitVarInsn(Opcodes.ALOAD, 0);
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V", false);
		  mv.visitInsn(Opcodes.ICONST_1);
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/io/FileWriter", "<init>", "(Ljava/io/File;Z)V", false);
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/io/BufferedWriter", "<init>", "(Ljava/io/Writer;)V", false);
		  mv.visitVarInsn(Opcodes.ASTORE, 3);
		  Label l4 = new Label();
		  mv.visitLabel(l4);
		  
		  mv.visitTypeInsn(Opcodes.NEW, "java/io/File");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitLdcInsn("/proc/self");
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/File", "getCanonicalFile", "()Ljava/io/File;", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/File", "getName", "()Ljava/lang/String;", false);
		  mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I", false);
		  mv.visitVarInsn(Opcodes.ISTORE, 4);
		  Label l5 = new Label();
		  mv.visitLabel(l5);
		  
		  mv.visitVarInsn(Opcodes.ALOAD, 3);
		  mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitVarInsn(Opcodes.ALOAD, 1);
		  mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
		  mv.visitLdcInsn(" ( ");
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		  mv.visitVarInsn(Opcodes.ILOAD, 4);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
		  mv.visitLdcInsn(" ) ");
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		  mv.visitVarInsn(Opcodes.ALOAD, 2);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/BufferedWriter", "write", "(Ljava/lang/String;)V", false);
		  Label l6 = new Label();
		  mv.visitLabel(l6);
		  
		  mv.visitVarInsn(Opcodes.ALOAD, 3);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/BufferedWriter", "close", "()V", false);
		  mv.visitLabel(l1);
		
		  Label l7 = new Label();
		  mv.visitJumpInsn(Opcodes.GOTO, l7);
		  mv.visitLabel(l2);
		  
		  mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/io/IOException"});
		  mv.visitVarInsn(Opcodes.ASTORE, 3);
		  Label l8 = new Label();
		  mv.visitLabel(l8);
		 
		  mv.visitVarInsn(Opcodes.ALOAD, 1);
		  mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
		  mv.visitInsn(Opcodes.DUP);
		  mv.visitLdcInsn("exception ");
		  mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
		  mv.visitVarInsn(Opcodes.ALOAD, 3);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/IOException", "getMessage", "()Ljava/lang/String;", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
		  mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
		  mv.visitInsn(Opcodes.POP);
		  Label l9 = new Label();
		  mv.visitLabel(l9);
	
		  mv.visitVarInsn(Opcodes.ALOAD, 3);
		  mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/IOException", "printStackTrace", "()V", false);
		  mv.visitLabel(l7);
	
		  mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		  mv.visitInsn(Opcodes.RETURN);
		  Label l10 = new Label();
		  mv.visitLabel(l10);
		  mv.visitLocalVariable("fileName", "Ljava/lang/String;", null, l3, l10, 0);
		  mv.visitLocalVariable("tag", "Ljava/lang/String;", null, l3, l10, 1);
		  mv.visitLocalVariable("msg", "Ljava/lang/String;", null, l3, l10, 2);
		  mv.visitLocalVariable("output", "Ljava/io/BufferedWriter;", null, l4, l1, 3);
		  mv.visitLocalVariable("pid", "I", null, l5, l1, 4);
		  mv.visitLocalVariable("e", "Ljava/io/IOException;", null, l8, l7, 3);
		  mv.visitMaxs(7, 5);
		  mv.visitEnd();
	}
*/	
	  //@Override
	  public void visitEnd() {
	     //addPrintingMethod();
		  accept(classWriterVisitor);

		  
		  
		  //	      for (Object methodNodeObject : this.methods) {
//	          final MethodNode methodNode = (MethodNode) methodNodeObject;
//	          final String methodname = methodNode.name;
	//    
//	          boolean isAbstract = ( methodNode.access & Opcodes.ACC_ABSTRACT) != 0;
//	          boolean isNative = (methodNode.access & Opcodes.ACC_NATIVE) != 0;
//	          
//	          if (isAbstract || isNative) {
//	          	continue;
//	          }
//	          
//	         AbstractInsnNode firstInst = methodNode.instructions.getFirst();
	//
//	          // Insert the three opcodes that will output the
//	          // name of the routine
	//
//	          // ***
//	          // insert GETSTATIC
//	          final FieldInsnNode fieldnode = new FieldInsnNode(org.objectweb.asm.Opcodes.GETSTATIC,
//	                                                           "java/lang/System", // field class
//	                                                           "out",              // field name
//	                                                           "Ljava/io/PrintStream;"); // field type
//	          methodNode.instructions.insertBefore(firstInst, fieldnode);
	//
//	          // ***
//	          // insert ldc "Am in method xxx"
//	          final String str = "AM IN METHOD:" + this.name + "." + methodname;
//	          final LdcInsnNode ldcnode = new LdcInsnNode(str);
//	          methodNode.instructions.insertBefore(firstInst, ldcnode);
	//
//	          // ***
//	          // insert invokevirtual
//	          final AbstractInsnNode newInsnNode = new MethodInsnNode(org.objectweb.asm.Opcodes.INVOKEVIRTUAL,
//	                                                                 "java/io/PrintStream", // class name
//	                                                                 "println",             // method name
//	                                                                 "(Ljava/lang/String;)V", false); // desc
//	          methodNode.instructions.insertBefore(firstInst, newInsnNode);
//	      }
	//
	// 
//	      // Without this, get
//	      // "UnsupportedClassVersionError: Test : Unsupoorted majorminor version 0.0"
//	      // Where Test was the application class
//	      accept(classvisitor);
	  }


	  

}
