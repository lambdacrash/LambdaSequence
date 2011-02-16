package org.lambda.sequence.gui.element;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class SequenceCanvas extends JPanel
{
	private static final long serialVersionUID = 7986283314165275498L;
	private List<IDrawable> drawables;
	private int w=0, h=0;
	
	public SequenceCanvas()
	{
		this.drawables = new LinkedList<IDrawable>();
		this.setLayout(null);
	}
	
	@Override
	public void paint(Graphics g) 
	{
		Dimension dim = getSize();
		g.clearRect(0,0,dim.width,dim.height);
		this.paintHelper(g);
	}
	
	private synchronized void paintHelper(Graphics g)
	{
		for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) 
		{
			IDrawable d = (IDrawable) iter.next();
			if(w <d.getDisplayX())
				w = d.getDisplayX();
			if(h <d.getDisplayY())
				h = d.getDisplayY();
			d.draw(g);	
		}
		for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) 
		{
			IDrawable d = (IDrawable) iter.next();
			d.setDisplayX(w+100);
			d.setDisplayY(h);
		}
		setPreferredSize(new Dimension(w, h));
		revalidate();
	}

	public synchronized void addDrawable(IDrawable d) 
	{
		drawables.add(d);
		revalidate();
	}

	public synchronized void removeDrawable(IDrawable d) 
	{
		drawables.remove(d);
	}

	public synchronized void clear() 
	{
		drawables.clear();
	}
}
