package org.lambda.sequence.gui.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class CallDrawable extends FormDrawable
{
	public int x1, y1, x2, mw, mh;
	public String name;
	public ClassDrawable caller, called;
	public boolean in, constructor;
	public String hash;
	public int thread;
	public String type;
	
	public CallDrawable(Color color, Point pos, Dimension dim, String name, ClassDrawable caller, 
			ClassDrawable called, boolean in, boolean constructor, String hash, int thread, String type)
	{
		super(color, pos, dim);
		x1 = pos.x;
		x2 = pos.x + dim.width;
		y1 = pos.y;
		this.name = name;
		this.caller = caller;
		this.called = called;
		this.in = in;
		this.constructor = constructor;
		this.hash = hash;
		this.thread = thread;
		this.type = type;
	}


	@Override
	public void draw(Graphics g)
	{
		x1 = caller.xcenter;
		x2 = called.xcenter;
		// Display thread line
		g.setFont(new Font("monaco", Font.BOLD, 10));
		Color th = ColorHelper.getColor(thread, 30);
		g.setColor(th);
		g.fillRect(0, y1-12, mw+2000, 26);
		th = ColorHelper.getColor(thread, 255);
		g.setColor(th);
		g.drawString("Th id: "+thread, 4, y1+4);
		
		if(in)
		{
			g.setColor(Color.black);
			if(x1>x2)
			{
				g.drawLine(x2, y1, x2+6, y1-4);
				g.drawLine(x2, y1, x2+6, y1+4);
			}
			if(x1<x2)
			{
				g.drawLine(x2, y1, x2-6, y1-4);
				g.drawLine(x2, y1, x2-6, y1+4);
			}
			if(x1 == x2)
			{
				g.drawLine(x2, y1, x2-6, y1-4);
				g.drawLine(x2, y1, x2-6, y1+4);
				g.drawLine(x2, y1, x2+6, y1-4);
				g.drawLine(x2, y1, x2+6, y1+4);
			}
			g.drawLine(x1, y1, x2, y1);
			g.setColor(Color.BLUE);
			if(constructor)
				g.setColor(Color.red);
			g.drawString((this.name), Math.min(x1, x2)+10, y1+10);
			g.setColor(Color.green);
			g.drawString(this.hash+" - "+type, Math.min(x1, x2)+10, y1-2);
			g.setColor(Color.black);
			//g.drawLine(Math.min(x1, x2), y1-10, Math.min(x1, x2), y1+10);
		}
		else
		{
			g.setColor(Color.gray);
			if(x1>x2)
			{
				g.drawLine(x2, y1, x2+6, y1-4);
				g.drawLine(x2, y1, x2+6, y1+4);
			}
			if(x1<x2)
			{
				g.drawLine(x2, y1, x2-6, y1-4);
				g.drawLine(x2, y1, x2-6, y1+4);
			}
			if(x1 == x2)
			{
				g.drawLine(x2, y1, x2-6, y1-4);
				g.drawLine(x2, y1, x2-6, y1+4);
				g.drawLine(x2, y1, x2+6, y1-4);
				g.drawLine(x2, y1, x2+6, y1+4);
			}
			g.drawLine(x1, y1, x2, y1);
			if(constructor)
				g.setColor(Color.red);
			g.setFont(new Font("monaco", Font.BOLD, 10));
			g.drawString((this.name), Math.min(x1, x2)+10, y1+10);
			g.drawString(this.hash+" - "+type , Math.min(x1, x2)+10, y1-2);
			g.setColor(Color.gray);
			//g.drawLine(Math.min(x1, x2), y1-10, Math.min(x1, x2), y1+10);
		}
		g.setColor(Color.black);
	}

	@Override
	public int getDisplayX()
	{
		return this.rect.x + Math.max(x1,x2);
	}

	@Override
	public int getDisplayY()
	{
		return this.rect.y + 30;
	}


	@Override
	public void setDisplayX(int x)
	{
		this.mw = x;
	}


	@Override
	public void setDisplayY(int y)
	{
		this.mh = y;
	}
}
