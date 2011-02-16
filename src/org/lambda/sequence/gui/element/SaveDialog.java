package org.lambda.sequence.gui.element;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SaveDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 6284843378579524377L;
	private JLabel text;
	private JTextField name;
	private JButton save;
	private KeyboardControl kc;
	private String fileName = "";
	
	public SaveDialog(KeyboardControl kc)
	{
		super();
		this.kc = kc;
		text = new JLabel("Please enter the complete file name:");
		add(text, BorderLayout.NORTH);
		name = new JTextField("file name");
		add(name, BorderLayout.CENTER);
		save = new JButton("Save");
		add(save, BorderLayout.SOUTH);
		save.addActionListener(this);
		
		setTitle("Save into file");
		setAlwaysOnTop(true);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == save)
		{
			System.out.println("SAVE BUTTON");
			this.fileName = this.name.getText();
			this.kc.save(this.fileName);
			this.dispose();
		}
	}
}
