package com.android.ClassVisitors;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import com.android.data.ClassHierarchy;

public class PreprocessClassVisitor extends ClassNode {
	public PreprocessClassVisitor() {
		super(Opcodes.ASM5);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		ClassHierarchy hierarchy = ClassHierarchy.getInstance();
		hierarchy.add(name, superName, 0);
		for (String s : interfaces) {
			hierarchy.add(name, s, 1);
		}
	}
}
