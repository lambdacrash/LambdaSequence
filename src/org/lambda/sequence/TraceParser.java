package org.lambda.sequence;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.lambda.sequence.gui.element.SequenceCanvas;

public class TraceParser
{
	private String file;
	private List<MethodCall> calls;
	
	public TraceParser(String file)
	{
		this.file = file;
		this.calls = Collections.synchronizedList(new LinkedList<MethodCall>());
	}

	public void compute(SequenceCanvas canvas)
	{
		try{
			FileInputStream fstream = new FileInputStream(this.file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   
			{
				// Get different informations about
				String calledClazz = this.getClassNameFromLine(strLine);
				String method = this.getMethodName(strLine);
				String shortName = this.shortName(method);
				String hash = this.getInstanceCode(strLine);
				String type = this.getReturnType(strLine);
				String callerClazz = this.getCallerClass(strLine);
				boolean constructor = this.isConstructor(strLine);
				boolean inC = this.inOrOut(strLine);
				int thread = this.getThreadId(strLine);
				String time = this.getTime(strLine);
				// Create a new method call !!! Oh yeahh
				MethodCall mc = new MethodCall(shortName, method, 
						calledClazz, type, hash, 
						thread, constructor, callerClazz, inC, time);
				
				this.calls.add(mc);
			}
			in.close();
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	public List<MethodCall> getCallList()
	{
		return this.calls;
	}
	
	private String getInstanceCode(String line)
	{
		String[] fields = line.split(" ");
		return fields[0];
	}
	
	private String getTime(String line)
	{
		String[] fields = line.split(" ");
		return fields[10];
	}
	
	private String getCallerClass(String line)
	{
		String clazz = "";
		String tmp = line.split(" ")[8];
		String [] packs = tmp.split("\\.");
		for(int i=0; i<(packs.length); i++)
		{
			if(packs[i].contains("("))
			{
				i = packs.length;
			}
			else
				clazz += packs[i]+".";
		}
		clazz = clazz.substring(0, clazz.length()-1);
		return clazz;
	}
	
	private boolean inOrOut(String line)
	{
		// return True if In, False if Out
		return line.split(" ")[1].contains("I");
	}
	
	private boolean isConstructor(String line)
	{
		return line.contains(" C ");
	}
	
	private String getReturnType(String line)
	{
		return line.split(" ")[4];
	}
	
	private String getMethodName(String line)
	{
		return line.split(" ")[2];
	}
	
	private String getClassNameFromLine(String line)
	{
		String[] fields;
		String clazz = "";
		boolean constructor = line.contains(" C ");
		fields = line.split(" ");
		String [] tmp = fields[2].split("\\.");
		for(int i=0; i<(tmp.length); i++)
		{
			if(tmp[i].contains("("))
			{
				if(constructor) clazz += tmp[i].substring(0, tmp[i].indexOf("("))+".";
				i = tmp.length;
			}
			else
				clazz += tmp[i]+".";
		}
		clazz = clazz.substring(0, clazz.length()-1);
		return clazz;
	}
	
	private int getThreadId(String line)
	{
		return Integer.parseInt(line.split(" ")[6]);
	}
	
	private String shortName(String name)
	{
		String[] packs = name.split("\\.");
		String result ="";
		boolean stuff = true;
		for(String s : packs)
		{
			if(s.contains("(")) stuff = false;
			if(!stuff) result += s+".";
		}
		return result.substring(0, result.length()-1);
	}
}
