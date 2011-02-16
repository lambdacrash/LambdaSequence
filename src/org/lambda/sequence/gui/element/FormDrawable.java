package org.lambda.sequence.gui.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class FormDrawable implements IDrawable 
{
	protected Rectangle rect ;
	protected Color color;
	
	public FormDrawable(Color color, Point pos, Dimension dim)
	{
		this.color=color;
		this.rect = new Rectangle(pos,dim);
	}
	
	public abstract void draw(Graphics g) ;
	
	public Rectangle getRectangle()
	{
		return (Rectangle) rect.clone();
	}
	
}
