package org.lambda.sequence.gui.element;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.lambda.sequence.gui.ScreenImage;

public class KeyboardControl implements KeyListener
{
	private SequenceCanvas canvas;
	
	public KeyboardControl(SequenceCanvas c)
	{
		this.canvas = c;
		c.getParent();
	}
	
	public void save(String file)
	{
		System.out.println(file);
		BufferedImage bf = ScreenImage.createImage( canvas );
		try
		{
			ScreenImage.writeImage(bf, file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 's')
		{
			new SaveDialog(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

}
