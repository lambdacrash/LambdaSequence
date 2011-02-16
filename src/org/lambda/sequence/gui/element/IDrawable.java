package org.lambda.sequence.gui.element;
import java.awt.Graphics;
import java.awt.Rectangle;

public interface IDrawable 
{
	public int getDisplayX();
	public int getDisplayY();
	public void setDisplayX(int x);
	public void setDisplayY(int y);
	public void draw(Graphics g);
	public Rectangle getRectangle();
}
