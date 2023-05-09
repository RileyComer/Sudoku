package main;

import java.awt.*;

import javax.swing.*;

import displays.*;

public class Display extends JPanel{
	
	Toolkit t=Toolkit.getDefaultToolkit();
	private Gui gui;
	private String[] mode;
	
	private MenuDisplay menu;
	private Mode1Display mode1Display;
	private Mode2Display mode2Display;
	
	//game stuff
	
	public Display(Gui gui, String[] mode, Gameframe gameframe){
		this.gui=gui;
		this.mode=mode;
		menu=new MenuDisplay(gui);
		mode1Display=new Mode1Display(gui, gameframe);
		mode2Display=new Mode2Display(gui);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mode[0].equals("Menu")) {
			this.setBackground(new Color(80,80,80));
			menu.render(g);
			
		}else if(mode[0].equals("Mode1")) {
			this.setBackground(new Color(20,20,20));
			mode1Display.render(g);
		}else if(mode[0].equals("Mode2")) {
			this.setBackground(new Color(20,20,20));
			mode2Display.render(g);
		}
	}
	
	public void redraw() {
		this.repaint();
	}
}