package com.android.data;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class ClassHierarchy {
	private static ClassHierarchy instance = null;
	private Map<String, Ancestors> hierarchy;
	
	
	private ClassHierarchy() {
		hierarchy = new TreeMap<String, Ancestors>();
	}
	
	public static ClassHierarchy getInstance() {
		if (instance == null) {
			instance = new ClassHierarchy();
		}
		return instance;
	}
	
	public void reset() {
		hierarchy = new TreeMap<String, Ancestors>();
	}
	
	/* flag==0 --> super class
	 * flag==1 --> interface
	 */
	public void add(String className, String superName, int flag) {
		Ancestors a; 
		if (hierarchy.containsKey(className)) {
			a = hierarchy.get(className);
		}
		else {
			a = new Ancestors();
			hierarchy.put(className, a);
		}
		if (flag == 0) {
			a.superClass = superName;
		}
		else {
			a.interfaces.add(superName);
		}
	}
	
	
	public boolean isAncestors(String className, String superName) {
		if (className.equals(superName)) {
			return true;
		}
		
		//we don't know anything about this class or it has no ancestors
		if (!hierarchy.containsKey(className) || (hierarchy.get(className).superClass == null && hierarchy.get(className).interfaces.isEmpty())) {
			return false;
		}
		
		if (hierarchy.get(className).superClass.equals(superName)) {
			return true;
		}
		
		for (String s: hierarchy.get(className).interfaces) {
			if (s.equals(superName)) {
				return true;
			}
		}
		
		if (isAncestors(hierarchy.get(className).superClass, superName)) {
			return true;
		}
		
		for (String s: hierarchy.get(className).interfaces) {
			if (isAncestors(s, superName)) {
				return true;
			}
		}
		
		return false;
	}
	
	private class Ancestors {
		public String superClass;
		public HashSet<String> interfaces = new HashSet<String>();
	}

	

}
