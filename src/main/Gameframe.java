package main;

import gameObjects.Cell;
import gameObjects.Filler;
import gameObjects.PuzzleMaker;
import gameObjects.Solver;

public class Gameframe {
	private Cell[][] grid;
	private Cell[][] solution;
	private Cell[][] pen;
	private int[][] mask;
	private int maskCount;
	private Filler filler;
	private PuzzleMaker maker;
	private Solver solver;
	
	private boolean doneFill, haveSolution, puzzleDone, doneSolving;
	private int frame;
	
	public Gameframe() {
		grid=new Cell[9][9];
		for(int x=0; x<grid.length; x++) {
			for(int y=0; y<grid[x].length; y++) {
				grid[x][y]=new Cell();
			}
		}
		solution=new Cell[9][9];
		for(int x=0; x<solution.length; x++) {
			for(int y=0; y<solution[x].length; y++) {
				solution[x][y]=new Cell();
			}
		}
		pen=new Cell[9][9];
		for(int x=0; x<pen.length; x++) {
			for(int y=0; y<pen[x].length; y++) {
				pen[x][y]=new Cell();
			}
		}
		mask=new int[9][9];
		maskCount=0;
		haveSolution=false;
		filler=new Filler();
		maker=new PuzzleMaker();
		solver=new Solver();
		frame=0;
		doneFill=false;
		puzzleDone=false;
		doneSolving=false;
	}
	 
	public void update() {
		frame++;
		
		if(frame==30) {
			frame=0;
			if(!doneFill) {
				doneFill=filler.step();
				grid=copy(filler.getCells());
			}else if(!haveSolution){
				solution=copy(grid);
				haveSolution=true;
			}else if(maskCount!=81){
				int x=(int) (Math.random()*9);
				int y=(int) (Math.random()*9);
				while(mask[x][y]!=0) {
					x=(int) (Math.random()*9);
					y=(int) (Math.random()*9);
				}
				grid[x][y]=new Cell();
				Cell[][] temp=copy(grid);
				temp[x][y]=new Cell();
				if(maker.isSolvable(temp, solution)) {
					mask[x][y]=1;
				}else {
					grid[x][y].setValue(solution[x][y].getValue());
					mask[x][y]=2;
				}
				maskCount++;
			}else if(!puzzleDone){
				mask=new int[9][9];
				puzzleDone=true;
				solver.setCells(copy(grid));
			}else if(!doneSolving){
				doneSolving=solver.step();
				pen=copy(solver.getCells());
			}else {
				restart();
			}
		}
	 }
	 
	 public Cell[][] getGrid() {
		 Cell[][] out=copy(grid);
		 rePencil(out);
		 return out;
	 }
	 
	 public int[][] getMask(){
		 return mask;
	 }
	 
	 public Cell[][] copy(Cell[][] source){
		 Cell[][] cells=new Cell[9][9];
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells[x].length; y++) {
				cells[x][y]=new Cell();
				cells[x][y].setValue(source[x][y].getValue());
			}
		}
		return cells;
	 }

	public Cell[][] getPen() {
		rePencil(pen);
		return pen;
	}
	
	public void rePencil(Cell[][] cells) {
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells[x].length; y++) {
				if(cells[x][y].getValue()==0) {
					cells[x][y].resetPencil();
				}
			}
		}
		for(int bx=0; bx<cells.length; bx++) {
			for(int by=0; by<cells[bx].length; by++) {
				if(cells[bx][by].getValue()!=0) {
					updatePencil(cells, bx, by);
				}
			}
		}
	}
	
	public void updatePencil(Cell[][] cells, int x, int y) {
		int num=cells[x][y].getValue();
		for(int i=0; i<cells.length; i++) {
			if(cells[i][y].getValue()==0) {
				cells[i][y].removePencil(num);
			}
		}
		for(int i=0; i<cells[0].length; i++) {
			if(cells[x][i].getValue()==0) {
				cells[x][i].removePencil(num);
			}
		}
		for(int ix=x/3*3; ix<(x/3*3)+3; ix++) {
			for(int iy=y/3*3; iy<(y/3*3)+3; iy++) {
				if(cells[ix][iy].getValue()==0) {
					cells[ix][iy].removePencil(num);
				}
			}
		}
	}
	
	private void restart() {
		grid=new Cell[9][9];
		for(int x=0; x<grid.length; x++) {
			for(int y=0; y<grid[x].length; y++) {
				grid[x][y]=new Cell();
			}
		}
		solution=new Cell[9][9];
		for(int x=0; x<solution.length; x++) {
			for(int y=0; y<solution[x].length; y++) {
				solution[x][y]=new Cell();
			}
		}
		pen=new Cell[9][9];
		for(int x=0; x<pen.length; x++) {
			for(int y=0; y<pen[x].length; y++) {
				pen[x][y]=new Cell();
			}
		}
		mask=new int[9][9];
		maskCount=0;
		haveSolution=false;
		filler=new Filler();
		maker=new PuzzleMaker();
		solver=new Solver();
		frame=0;
		doneFill=false;
		puzzleDone=false;
		doneSolving=false;
	}
}
