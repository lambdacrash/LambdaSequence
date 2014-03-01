package org.lambda.sequence.util;

import java.util.Calendar;

public class CallerInfo extends SecurityManager
{
	private static CallerInfo caller = new CallerInfo();
	
	public static long getCallDate()
	{
		return Calendar.getInstance().getTimeInMillis();
	}
	
	public static String getCallerClassName()
	{
		Class<?>[] context = caller.getClassContext();
		if(context.length>2)
			return context[2].getName();
		return null;
	}
	
	
	public static String getCallerName()
	{
		String r = "";
		StackTraceElement[] s = new Throwable().getStackTrace();
		//System.out.println("------------------------");
		//for(StackTraceElement e : s)
		//	System.out.println("--> "+e);
		if(s.length<3)
			r += s[s.length-1];
		else
			r += s[2];
		return r;
	}
}
