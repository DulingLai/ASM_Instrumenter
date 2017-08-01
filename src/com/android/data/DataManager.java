package com.android.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DataManager {
	
	//method sig to method
	private HashMap<String, Method> methods = new  HashMap<String, Method>();
	
	//statement sig to statement
	private HashMap<String, Statement> statements = new HashMap<String, Statement>();
	
	//calling method to list of statements
	private static HashMap<String, List<String>> statementsToBlock = new HashMap<String, List<String>>();

	//-----------------------------------------------------------------------------------------------
	// executed methods and source/sink statements 
	//-----------------------------------------------------------------------------------------------
	
	public Method getMethod(String key) {
		return methods.get(key);
	}

	public void addMethod(Method method) {
		methods.put(method.getSigniture(), method);
	}
	
	public Statement getStatement(String ownerMethodSigniture, String statementSigniture) {
		return statements.get(ownerMethodSigniture + ": " + statementSigniture);
	}

	public void addStatement(Statement statement) {
		statements.put(statement.getOwnerMethodSigniture() + ": " + statement.getStatementSigniture(), statement);
	}
	

	public void dumpMethodsToFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if (!file.exists()){
				file.createNewFile();
			} 
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (String m : methods.keySet()) {
				output.write(m + "|" + (methods.get(m).getCalled()?"1":"0") + System.lineSeparator());
			}
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readMethodsFromFile(String filename) {	
		methods = new  HashMap<String, Method>();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));	       
	        String line = br.readLine();

	        String lineData[];
	        while (line != null) {	          
	        	lineData = line.split("[|]");
	            if (lineData.length != 2) {
	            	System.err.println("Bad line in input: " + line);
	            	line = br.readLine();
	            	continue;
	            }	            
	            Method method = new Method(lineData[0]);
	            method.setCalled(lineData[1].equals("1")?true:false);
	            addMethod(method);
	            line = br.readLine();
	        }
	        
	        br.close();
	    } 
	    catch (IOException e) {
			e.printStackTrace();
		}	    	 
	}
	
	public void dumpStatementsToFile(String filename) {
		try {
			File file = new File(filename);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (Statement s : statements.values()) {
				output.write(s.getType() + " " + s.getSigniture() + "|" + (s.getCalled()?"1":"0") + System.lineSeparator());
			}
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void readStatementsFromFile(String filename) {	
		statements = new  HashMap<String, Statement>();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));	       
	        String line = br.readLine();

	        String lineData[];
	        String statementData[];
	        
	        while (line != null) {	          
	        	lineData = line.split("[|]");
	            if (lineData.length != 2) {
	            	System.err.println("Bad line in input: " + line);
	            	line = br.readLine();
	            	continue;
	            }	            
	            statementData = lineData[0].split(" ");
	            try {
	            	Statement statement = new Statement(statementData[1].substring(0, statementData[1].length()-1), statementData[2], Integer.parseInt(statementData[0]));
	            	statement.setCalled(lineData[1].equals("1")?true:false);
	            	addStatement(statement);
	            }
	            catch (Exception ex) {
	            	System.err.println("Bad line in input: " + line);
	            }
	            
	            line = br.readLine();
	        }
	        
	        br.close();
	    } 
	    catch (IOException e) {
			e.printStackTrace();
		}	    	 
	}


	public void processExecutionLog(String filename) {

		
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));	       
	        String line = br.readLine();

	        String lineData[];
	        String methodSigniture;
	        String statementData[];
	        
	        while (line != null) {	          
	            //methodCall
	        	lineData = line.split(Constants.LOG_MARKER);
	            if (lineData.length == 2) {
	            	methodSigniture = lineData[1];	         
	            	Method method = getMethod(methodSigniture);
	            	if (method == null) {
	            		System.err.println("Method executed but not in the data structure: " + methodSigniture);
	            	}
	            	else {
	            		method.setCalled(true);
	            	}
	            	line = br.readLine();
	            	continue;
	            }
	            
	            //source
	            lineData = line.split(Constants.SOURCE_MARKER);
	           
	           if (lineData.length == 2) {
	        	   statementData = lineData[1].split(" from ");
	        	   if (statementData.length == 2) {
	        		   Statement statement = getStatement(statementData[1], statementData[0]);
	        		   if (statement == null) {
	        			   System.err.println("Statement executed but not in the data structure: " + statementData[0] + "." + statementData[1]);
	        		   }
	        		   else {
	        			   statement.setCalled(true);
	        			   if (statement.getType() != 0) {
	        				   System.err.println("Sink statement executed as source: " + statementData[0] + "." + statementData[1]);
	        			   }
	        		   }
	        		   line = br.readLine();
	        		   continue;
	        	   }        	   
	           }
	           
	        	//sink
	           lineData = line.split(Constants.SINK_MARKER);
	           if (lineData.length == 2) {
	        	   statementData = lineData[1].split(" from ");
	        	   if (statementData.length == 2) {
	        		   Statement statement = getStatement(statementData[1], statementData[0]);
	        		   if (statement == null) {
	        			   System.err.println("Statement executed but not in the data structure: " + statementData[0] + "." + statementData[1]);
	        		   }
	        		   else {
	        			   statement.setCalled(true);
	        			   if (statement.getType() != 1) {
	        				   System.err.println("Source statement executed as sink: " + statementData[0] + "." + statementData[1]);
	        			   }
	        		   }
	        		   line = br.readLine();
	        		   continue;
	        	   }
	           }
	            
	           //wrong line
	           System.err.println("Bad execution log entry: " + line);
	           line = br.readLine();
	        }
	        
	        br.close();
	    } 
	    catch (IOException e) {
			e.printStackTrace();
		}	    	 
		
	}
	
	//-----------------------------------------------------------------------------------------------
	//for flowdroid
	//-----------------------------------------------------------------------------------------------
	
	public void dumpSourceSinkEntryPoints(String sourcesAndSinksFile, String entrypointsFile) {
//		org.objectweb.asm.Type type;
		
		HashSet<String> sources = new HashSet<String>();
		HashSet<String> sinks = new HashSet<String>();
		HashSet<String> entrypoints = new HashSet<String>();
		
		for (Statement s : statements.values()) {
			if (!s.getCalled()) continue;
			
			entrypoints.add(s.getOwnerMethodSigniture());
			if (s.getType() == 0) {
				sources.add(s.getStatementSigniture());
			}
			else {
				sinks.add(s.getStatementSigniture());
			}
		}
		
		try {
			File file = new File(sourcesAndSinksFile);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (String s : sources) {
				//type = org.objectweb.asm.Type.getMethodType(m);
				output.write("<" + s + "> -> _SOURCE_" + System.lineSeparator());
			}
			for (String s : sinks) {
				output.write("<" + s + "> -> _SINK_" + System.lineSeparator());
			}
			output.close();
			
			file = new File(entrypointsFile);
			output = new BufferedWriter(new FileWriter(file));
			for (String m : entrypoints) {
				output.write("<" + m + ">" + System.lineSeparator());
			}
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//-----------------------------------------------------------------------------------------------
	//for blocking statements
	//-----------------------------------------------------------------------------------------------
	
	public void dumpStatementsToBlockToFile(String filename) {
		
		HashSet<String> called = new HashSet<String>();
		HashSet<String> notCalled = new HashSet<String>();
		
		String signiture;
		
		for (Statement s : statements.values()) {
			//for sources only
			if (s.getType() == 0) {
				if (s.getStatementSigniture().contains("android/os/IBinder.transact(") //|| 
						//s.getOwnerMethodSigniture().contains("com/google/android/gms") ||
						//s.getOwnerMethodSigniture().contains("com/android/vending/billing")
					) {
					continue;
				}
				signiture = s.getStatementSigniture() + " from " + s.getOwnerMethodSigniture();
				if (s.getCalled()) {
					called.add(signiture);
				}
				else {
					notCalled.add(signiture);
				}
			}
		}
				
		
		try {
			File file = new File(filename);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			
			for (String s : notCalled) {
				output.write(s + System.lineSeparator());
			}
			output.write("%------------" + System.lineSeparator());
			
			for (String s : called) {
				output.write("% " + s + System.lineSeparator());
			}
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO add statement count for identical calls within the same method
	
	public void readStatementsToBlockFromFile(String filename) {	
		
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));	       
	        String line = br.readLine();

	        while (line != null) {	
	        	if (line.startsWith("%")) {
	        		line = br.readLine();
	        		continue;
	        	}
	            String methodData[] = line.split(" from ");
	            if (methodData.length != 2) {
	            	System.err.println("Bad line in input: " + line);
	            	line = br.readLine();
	            	continue;
	            }	     
	            addBlockedStatement(methodData[0], methodData[1]);
	            line = br.readLine();
	        }
	        
	        br.close();
	    } 
	    catch (IOException e) {
			e.printStackTrace();
		}	    	 
	}	
	
	private void addBlockedStatement(String s, String from) {
		if (statementsToBlock.containsKey(from)) {
			List<String> statements = statementsToBlock.get(from);
			boolean found = false;
			for (String st : statements) {
				if (st.equals(s)) {
					found = true;
					break;
				}
			}
			if (!found) {
				statementsToBlock.get(from).add(s);
			}
        }
        else {
        	ArrayList<String> statements = new ArrayList<String>();
        	statements.add(s);
        	statementsToBlock.put(from, statements);
        }
	}

	public HashMap<String, List<String>> getStatementsToBlock() {
		// TODO Auto-generated method stub
		return statementsToBlock;
	}
	

}
