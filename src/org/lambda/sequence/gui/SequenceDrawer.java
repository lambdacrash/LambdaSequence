package org.lambda.sequence.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import org.lambda.sequence.MethodCall;
import org.lambda.sequence.gui.element.CallDrawable;
import org.lambda.sequence.gui.element.ClassDrawable;
import org.lambda.sequence.gui.element.SequenceCanvas;

public class SequenceDrawer
{
	private SequenceCanvas canvas;
	private int xOffset = 40;
	private int yOffset = 30;
	private int oldWidth = 10;
	private int oldY = 40;
	private int count = 0;

	private String calledClazz = "";
	private String longName = "";
	private String shortName = "";
	private String hash = "";
	private String type = "";
	private String callerClazz = "";
	private boolean constructor = false;
	private boolean inC = false;
	private int thread = 0;
	private String time = "";

	private HashMap<String, ClassDrawable> classDrawables;
	
	public SequenceDrawer(SequenceCanvas canvas)
	{
		this.canvas = canvas;
		this.classDrawables = new HashMap<String, ClassDrawable>();
	}
	
	public void draw(List<MethodCall> calls)
	{
		for(MethodCall call : calls)
		{
			// Collect the call informations
			calledClazz = call.getClazz();
			longName = call.getLongName();
			shortName = call.getShortName();
			hash = call.getHash();
			type = call.getType();
			callerClazz = call.getParent();
			constructor = call.isConstructor();
			inC = call.isIn();
			thread = call.getThread();
			time = call.getTime();
			////////////////////////////////////
			
			// Add instances of each class in order to count them
			if(this.classDrawables.containsKey(calledClazz))
				this.classDrawables.get(calledClazz).addInstance(hash);
			//if(this.classDrawables.containsKey(callerClazz))
			//	this.classDrawables.get(callerClazz).addInstance(hash);
			////////////////////////////////////////////////////////////

			this.drawClassBox(calledClazz);
			this.drawClassBox(callerClazz);
			
			if((!type.contains("void") && !inC) || !callerClazz.equals("null"))
				this.drawCall(calledClazz, callerClazz, thread, hash, type, inC, constructor);
			
		}
	}
	
	private void drawCall(String calledClazz, String callerClass, 
			int thread, String hash, String type, boolean in, boolean constructor)
	{
		System.out.println(calledClazz + "<-- "+callerClass);
		ClassDrawable called = this.classDrawables.get(calledClazz);
		ClassDrawable caller = this.classDrawables.get(callerClazz);
		int x = caller.xcenter;
		int dim = Math.abs(this.classDrawables.get(calledClazz).xcenter-x);
		CallDrawable c = null;
		if(inC)
		{
			c = new CallDrawable(
					Color.WHITE, 
					new Point(x, yOffset+oldY), 
					new Dimension(dim, 20), 
					shortName,
					caller,
					called, 
					in,
					constructor,
					hash,
					thread,
					type);
			canvas.addDrawable(c);
			canvas.validate();
			oldY = yOffset+oldY;
		}
		else if(!type.contains("void"))
		{
			c = new CallDrawable(
					Color.WHITE, 
					new Point(x, yOffset+oldY), 
					new Dimension(dim, 20), 
					shortName,
					called,
					caller, 
					in,
					constructor,
					hash,
					thread,
					type);
			canvas.addDrawable(c);
			canvas.validate();
			oldY = yOffset+oldY;
		}
	}
	
	private void drawClassBox(String shortName)
	{
		if(!this.classDrawables.containsKey(shortName) && !shortName.equals("null")) 
		{
			ClassDrawable g = new ClassDrawable(
					Color.decode("0xCCCCFF"), 
					new Point(count*xOffset+oldWidth, 10), 
					new Dimension(180, 15), 
					shortName);
			this.classDrawables.put(shortName, g);
			canvas.addDrawable(g);
			canvas.setFont(new Font("monaco", Font.BOLD, 10));
			FontMetrics metric = canvas.getGraphics().getFontMetrics();
			int width = metric.stringWidth(shortName);
			oldWidth = width+g.getRectangle().x;
			count = 1;
		}
	}
}
