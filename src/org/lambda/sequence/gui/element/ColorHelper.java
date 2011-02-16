package org.lambda.sequence.gui.element;

import java.awt.Color;

public class ColorHelper
{
	
	public static final Color[] colors = 
	{
		Color.decode("0x0000CC"),
		Color.decode("0x0033CC"),
		Color.decode("0x0066CC"),
		Color.decode("0x0099CC"),
		Color.decode("0x00CCCC"),
		Color.decode("0x00FFCC"),
		Color.decode("0x3300CC"),
		Color.decode("0x3333CC"),
		Color.decode("0x3366CC"),
		Color.decode("0x3399CC"),
		Color.decode("0x33CCCC"),
		Color.decode("0x33FFCC"),
		Color.decode("0x6600CC"),
		Color.decode("0x6633CC"),
		Color.decode("0x6666CC"),
		Color.decode("0x6699CC"),
		Color.decode("0x66CCCC"),
		Color.decode("0x66FFCC"),
		Color.decode("0x9900CC"),
		Color.decode("0x9933CC"),
		Color.decode("0x9966CC"),
		Color.decode("0x9999CC"),
		Color.decode("0x99CCCC"),
		Color.decode("0x99FFCC"),
		Color.decode("0xCC00CC"),
		Color.decode("0xCC33CC"),
		Color.decode("0xCC66CC"),
		Color.decode("0xCC99CC"),
		Color.decode("0xCCCCCC"),
		Color.decode("0xCCFFCC"),
		Color.decode("0xFF00CC"),
		Color.decode("0xFF33CC"),
		Color.decode("0xFF66CC"),
		Color.decode("0xFF99CC"),
		Color.decode("0xFFCCCC"),
		Color.decode("0xFFFFCC"),
		Color.decode("0x0000FF"),
		Color.decode("0x0033FF"),
		Color.decode("0x0066FF"),
		Color.decode("0x0099FF"),
		Color.decode("0x00CCFF"),
		Color.decode("0x00FFFF"),
		Color.decode("0x3300FF"),
		Color.decode("0x3333FF"),
		Color.decode("0x3366FF"),
		Color.decode("0x3399FF"),
		Color.decode("0x33CCFF"),
		Color.decode("0x33FFFF"),
		Color.decode("0x6600FF"),
		Color.decode("0x6633FF"),
		Color.decode("0x6666FF"),
		Color.decode("0x6699FF"),
		Color.decode("0x66CCFF"),
		Color.decode("0x66FFFF"),
		Color.decode("0x9900FF"),
		Color.decode("0x9933FF"),
		Color.decode("0x9966FF"),
		Color.decode("0x9999FF"),
		Color.decode("0x99CCFF"),
		Color.decode("0x99FFFF"),
		Color.decode("0xCC00FF"),
		Color.decode("0xCC33FF"),
		Color.decode("0xCC66FF"),
		Color.decode("0xCC99FF"),
		Color.decode("0xCCCCFF"),
		Color.decode("0xCCFFFF"),
		Color.decode("0xFF00FF"),
		Color.decode("0xFF33FF"),
		Color.decode("0xFF66FF"),
		Color.decode("0xFF99FF")
	};
	
	public static Color getColor(int index, int alpha)
	{
		Color c = colors[((index*3)%colors.length)];
		Color cc = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
		return cc;
	}
}
