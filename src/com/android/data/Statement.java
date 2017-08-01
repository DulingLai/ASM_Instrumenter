package com.android.data;


public class Statement {
	
	private String ownerMethodSigniture;
	private String statementSigniture;
	private int type; //0 - source; 1 - sink;
	private boolean called = false;

	public Statement(String ownerMethodSigniture, String statementSigniture, int type) {
		this.ownerMethodSigniture = ownerMethodSigniture;
		this.statementSigniture = statementSigniture;
		this.type = type;
	}
	public String getOwnerMethodSigniture() {
		return ownerMethodSigniture;
	}
	public String getStatementSigniture() {
		return statementSigniture;
	}

	public boolean getCalled() {		
		return called;
	}
	public void setCalled(boolean value) {
		this.called = value;
	}
	public int getType() {		
		return type;
	}
	public String getSigniture() {
		return getOwnerMethodSigniture() + ": " + getStatementSigniture();
	}
	
	
	
	

}
