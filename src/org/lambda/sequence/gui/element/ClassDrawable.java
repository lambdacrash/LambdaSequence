package org.lambda.sequence.gui.element;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


public class ClassDrawable extends FormDrawable
{
	public String name;
	public int numInstances = 0;
	public int w,h,xcenter, mw, mh;
	private ArrayList<String> instances;
	
	public ClassDrawable(Color color, Point pos, Dimension dim, String name) 
	{
		super(color, pos, dim);
		this.name = name;
		this.instances = new ArrayList<String>();
	}
	
	public void addInstance(String hash)
	{
		if(!this.instances.contains(hash))
			this.instances.add(hash);
		this.numInstances = this.instances.size();
	}

	public void draw(Graphics g) 
	{
		Color c = g.getColor();
		g.setFont(new Font("monaco", Font.BOLD, 10));
		FontMetrics metric = g.getFontMetrics();
		int width = metric.stringWidth(this.name);
		int height = metric.getHeight();
		super.rect.setBounds(rect.x,rect.y,width+20,height*3);
		g.setColor(color);
		g.fillRoundRect(rect.x,rect.y,width+20,height*3, 11, 11);
		this.w = width+20;
		this.xcenter = rect.x + w/2;
		g.fillRect(xcenter-4, rect.y, 8, mh+10000);
		g.setColor(Color.black);
		this.h = height*3;
		g.drawString(this.name, rect.x+10, rect.y+20);
		g.drawString("#: "+this.numInstances, rect.x+10, rect.y+30);
		g.setColor(c);
	}

	@Override
	public int getDisplayX()
	{
		return this.rect.x + this.w;
	}

	@Override
	public int getDisplayY()
	{
		return this.rect.y + this.h;
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
