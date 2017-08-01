package com.android.data;


public class Method {
	
	private String signiture;
	private boolean called = false;

	public Method(String signiture) {
		this.signiture = signiture;
	}
	public String getSigniture() {
		return signiture;
	}
	public boolean getCalled() {		
		return called;
	}
	public void setCalled(boolean value) {
		this.called = value;
	}
	
	
	

}
