package displays;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gameObjects.Cell;
import main.Gameframe;
import main.Gui;

public class Mode1Display{
	
	private Gui gui;
	private Gameframe gameframe;
	private int size,startX,startY,frameW,frameH, border;
	private boolean showPencil;
	
	//game stuff
	
	public Mode1Display(Gui gui, Gameframe gameframe){
		this.gui=gui;
		this.gameframe=gameframe;
		frameW=9;
		frameH=9;
		showPencil=true;
		border=2;
		
		
	}
	
	public void render(Graphics g) {
		if((gui.getWidth()/frameW)<gui.getHeight()/frameH) {
			size=gui.getWidth()/frameW;
		}else {
			size=gui.getHeight()/frameH;
		}
		startX=(int)((gui.getWidth()/2.0)-((size*frameW)/2.0));
		startY=(int)((gui.getHeight()/2.0)-((size*frameH)/2.0));
		
		int[][] mask=gameframe.getMask();
		for(int y=0; y< frameH; y++) {
			for(int x=0; x<frameW;x++) {
				if(mask[x][y]==0) {
					g.setColor(new Color((int) (255),(int) (255),(int) (255)));
				}else if(mask[x][y]==1) {
					g.setColor(new Color((int) (0),(int) (255),(int) (0)));
				}else if(mask[x][y]==2) {
					g.setColor(new Color((int) (255),(int) (0),(int) (0)));
				}
				g.fillRect(startX+size*x+border, startY+size*((frameH-1)-y)+border, size-border, size-border);
			}
		}
		g.setColor(new Color((int) (0),(int) (0),(int) (0)));
		g.fillRect(startX+size*3-border, startY+size*((frameH-1)-8), border*2, size*9);
		g.fillRect(startX+size*6-border, startY+size*((frameH-1)-8), border*2, size*9);
		
		g.fillRect(startX+size*0, startY+size*((frameH-1)-2)-border, size*9, border*2);
		g.fillRect(startX+size*0, startY+size*((frameH-1)-5)-border, size*9, border*2);
		
		Cell[][] cells=gameframe.getGrid();
		Cell[][] pen=gameframe.getPen();
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells[x].length; y++) {
				int value=cells[x][y].getValue();
				if(value!=0) {
					g.setColor(new Color(0, 0, 0));
					Font font=new Font("arial", Font.BOLD, 50);
					g.setFont(font);
					g.drawString(""+value, startX+size*x+(int)(size*0.35), startY+size*((frameH-1)-(y-1))-(int)(size*0.35));
				}else if(cells[x][y].getValue()!=pen[x][y].getValue() && pen[x][y].getValue()!=0) {
					g.setColor(new Color(0, 0, 255));
					Font font=new Font("arial", Font.PLAIN, 50);
					g.setFont(font);
					g.drawString(""+pen[x][y].getValue(), startX+size*x+(int)(size*0.35), startY+size*((frameH-1)-(y-1))-(int)(size*0.35));
				}else if(showPencil){
					int[] pencil=cells[x][y].getPencil();
					for(int i=0; i<pencil.length; i++) {
						value=pencil[i];
						int gx=(value-1)%3;
						int gy=(value-1)/3;
						
						Font font=new Font("arial", Font.BOLD, 20);
						g.setFont(font);
						g.setColor(new Color(175, 175, 175));
						g.drawString(""+value, startX+size*x+(int)(size*0.2)+(int)(size*0.3)*gx, startY+size*((frameH-1)-(y-1))-(int)(size*0.2)-(int)(size*0.3)*gy);
					}
				}
			}
		}
	}
}