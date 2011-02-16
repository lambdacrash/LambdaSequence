package org.lambda.sequence.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.lambda.sequence.TraceParser;
import org.lambda.sequence.gui.element.KeyboardControl;
import org.lambda.sequence.gui.element.SequenceCanvas;
import org.w3c.dom.*;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 3292448411227539052L;
	private SequenceCanvas canvas;
	private String callTraceFile;
	private TraceParser parser;
	private JScrollPane scrollPane;
	private SequenceDrawer drawer;

	public MainFrame(String callTraceFile) 
	{
		this.callTraceFile = callTraceFile;
		this.parser = new TraceParser(this.callTraceFile);
		this.setTitle("LambdaSequence");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.canvas = new SequenceCanvas();
		this.canvas.setPreferredSize(new Dimension(10000, 10000));
		this.scrollPane = new JScrollPane(canvas);
		this.scrollPane.setPreferredSize(new Dimension(800, 600));
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.parser.compute(this.canvas);
		this.addKeyListener(new KeyboardControl(this.canvas));
		this.drawer = new SequenceDrawer(this.canvas);
		
		this.draw();
		this.pack();
		
		System.out.println("SVG STARTS");
		// Get a DOMImplementation.
        DOMImplementation domImpl =
            GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // Ask the test to render into the SVG Graphics2D implementation.
        System.out.println("SVG PAINT");
        this.canvas.paint(svgGenerator);
        System.out.println("SVG PAINT DONE");

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        try
        {
        	Writer out = new OutputStreamWriter(System.out, "UTF-8");
        	svgGenerator.stream(out, useCSS);
        }catch(Exception e)
        {e.printStackTrace();}

		System.out.println("SVG OK");
	}
	
	public void draw()
	{
		this.drawer.draw(this.parser.getCallList());
	}
	
	public static void main(String[] args) 
	{
		String file = "/Users/brice/1.lbds";
		if(args.length > 0)
		{
			file = args[0];
		}
		System.out.println(file);
		new MainFrame(file);
	}
}
