package com.android.ClassVisitors;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class BasicMethodAdapter extends AdviceAdapter {
	
	protected String className;
	protected String name;
	protected String desc;
	protected String methodSigniture;
	protected String logFileName;
	protected String instrumentationType;
	
	protected BasicMethodAdapter(final int api, final MethodVisitor mv,
            String className, final int access, final String name, 
            final String desc, final String logFileName, final String instrumentationType) {
		super(api, mv, access, name, desc);
        this.className = className;
        this.name = name;
        this.desc = desc;
        methodSigniture = className + "." + name +  desc;
        this.logFileName = logFileName;
        this.instrumentationType = instrumentationType;
	}

	
	protected boolean isConnection(String owner, String name) {
		if ((owner.equals("java/net/URL") && name.equals("openConnection")) ||
			(owner.equals("java/net/URL") && name.equals("openStream")) ||
			//(owner.equals("org/apache/http/client/HttpClient") && name.equals("execute")) ||
			(owner.equals("org/apache/http/impl/client/AbstractHttpClient") && name.equals("execute")) ||
			(owner.equals("org/apache/http/impl/client/DefaultHttpClient") && name.equals("execute")) ||
//			               org/apache/http/impl/client/DefaultHttpClient
//			               org/apache/http/impl/client/AbstractHttpClient
			(owner.equals("android/net.http/AndroidHttpClient") && name.equals("execute")) ||
			(owner.equals("java/net/URL") && name.equals("openStream")) ||
			(owner.equals("android/webkit/WebView") && name.equals("loadUrl")) ||
			(owner.equals("android/webkit/WebView") && name.equals("loadData")) ||
			(owner.equals("java/net/DatagramSocket") && name.equals("send")) ||
			(owner.equals("OpenSSLSocketImpl$SSLOutputStream") && name.equals("write")) ||
			(owner.equals("java/net/Socket") && name.equals("connect")) ||
			(owner.equals("libcore/io/Posix") && name.equals("sendto")) ||
			(owner.equals("libcore/io/Posix") && name.equals("sendtoBytes")) ||
			(owner.equals("libcore/net/http/HttpsURLConnectionImpl") && name.equals("getInputStream")) ||
			(owner.equals("libcore/net/http/HttpURLConnectionImpl") && name.equals("getInputStream")) ||
			(name.equals("getOutputStream")) ||
			(name.equals("transact")) ||
			(name.equals("getInputStream")) 
) {
				return true;
			}
		else {
			return false;
		}
	}
	
}
