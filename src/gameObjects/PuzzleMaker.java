package gameObjects;

public class PuzzleMaker {
	private int[][] history;
	private Cell[][] cells;
	private int cellsLeft;
	
	public PuzzleMaker() {
		history=new int[0][2];
		cells=new Cell[9][9];
		cellsLeft=81;
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells[x].length; y++) {
				cells[x][y]=new Cell();
			}
		}
	}
	
	public boolean step() {
		if(cellsLeft==0) {
			//Finished
			return true;
		}else {
			int low=getLowest();
			int x=low%9;
			int y=low/9;
			if(cells[x][y].getPencil().length>0) {
				cells[x][y].setValue(cells[x][y].getPencil()[0]);
				int[][] temp=new int[history.length+1][2];
				for(int ix=0; ix<history.length; ix++) {
					for(int iy=0; iy<history[ix].length; iy++) {
						temp[ix][iy]=history[ix][iy];
					}
				}
				temp[temp.length-1][0]=x;
				temp[temp.length-1][1]=y;
				history=temp;
				updatePencil(x, y);
				cellsLeft--;
			}else {
				if(!backtrack()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isSolvable(Cell[][] cells, Cell[][] solution) {
		reset();
		this.cells=cells;
		rePencil();
		cellsLeft=checkEmpty();
		if(cellsLeft==0) {
			return true;
		}
		boolean out=false;
		boolean done=false;
		while(!done) {
			done=step();
		}
		if(isDone()) {
			if(areEqual(cells, solution)) {
				out=true;
				if(backtrack()) {
					done=false;
					while(!done) {
						done=step();
					}
					if(isDone()) {
						return false;
					}else {
						return true;
					}
				}else {
					return true;
				}
			}else {
				return false;
			}
		}
		return out;
	}
	
	public boolean areEqual(Cell[][] c1, Cell[][] c2) {
		boolean out=true;
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells.length; y++) {
				if(c1[x][y].getValue()!=c2[x][y].getValue()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isDone() {
		boolean out=true;
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells.length; y++) {
				if(cells[x][y].getValue()==0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int checkEmpty() {
		int out=0;
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells.length; y++) {
				if(cells[x][y].getValue()==0) {
					out++;
				}
			}
		}
		return out;
	}
	
	private int getIndexOf(int value, int[] pencil) {
		int out=0;
		for(int i=0; i<pencil.length; i++) {
			if(pencil[i]==value) {
				return i;
			}
		}
		return out;
	}

	public void updatePencil(int x, int y) {
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
	
	public void rePencil() {
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
					updatePencil(bx, by);
				}
			}
		}
	}
	
	public boolean backtrack() {
		boolean out=true;
		if(history.length==0) {
			return false;
		}
		int x=history[history.length-1][0];
		int y=history[history.length-1][1];
		int[][] temp=new int[history.length-1][2];
		for(int ix=0; ix<temp.length; ix++) {
			for(int iy=0; iy<temp[ix].length; iy++) {
				temp[ix][iy]=history[ix][iy];
			}
		}
		history=temp;
		int value=cells[x][y].getValue();
		int i=getIndexOf(value, cells[x][y].getPencil());
		cells[x][y].setValue(0);
		rePencil();
		if(i<cells[x][y].getPencil().length-1) {
			temp=new int[history.length+1][2];
			cells[x][y].setValue(cells[x][y].getPencil()[i+1]);
			for(int ix=0; ix<history.length; ix++) {
				for(int iy=0; iy<history[ix].length; iy++) {
					temp[ix][iy]=history[ix][iy];
				}
			}
			temp[temp.length-1][0]=x;
			temp[temp.length-1][1]=y;
			history=temp;
			updatePencil(x, y);
		}else {
			cellsLeft++;
			out=backtrack();
		}
		return out;
	}
	
	public void reset() {
		history=new int[0][2];
		cells=new Cell[9][9];
		cellsLeft=81;
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells[x].length; y++) {
				cells[x][y]=new Cell();
			}
		}
	}
	
	private int getLowest() {
		int out=0;//0-80
		int lowest=9;
		int[] list=new int[0];
		for(int x=0; x<cells.length; x++) {
			for(int y=0; y<cells.length; y++) {
				if(cells[x][y].getPencil().length<lowest && cells[x][y].getValue()==0) {
					list=new int[1];
					list[0]=x+(y*9);
					lowest=cells[x][y].getPencil().length;
				}else if(cells[x][y].getPencil().length==lowest && cells[x][y].getValue()==0) {
					int[] temp=new int[list.length+1];
					for(int i=0; i<list.length; i++) {
						temp[i]=list[i];
					}
					temp[temp.length-1]=x+y*9;
					list=temp;
				}
			}
		}
		out=list[(int)(Math.random()*list.length)];
		return out;
	}
	
	public Cell[][] getCells(){
		return cells;
	}

}
