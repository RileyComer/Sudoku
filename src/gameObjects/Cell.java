package gameObjects;

public class Cell {
	private int value;
	private int[] pencil;
	
	public Cell() {
		value=0;
		pencil=new int[9];
		for(int i=0; i<9; i++) {
			pencil[i]=i+1;
		}
	}
	
	public void setValue(int i) {
		value=i;
	}
	
	public void removePencil(int num) {
		int idx=-1;
		for(int i=0; i<pencil.length; i++) {
			if(pencil[i]==num) {
				idx=i;
			}
		}
		if(idx!=-1) {
			int[] temp=new int[pencil.length-1];
			for(int i=0; i<idx; i++) {
				temp[i]=pencil[i];
			}
			for(int i=idx; i<temp.length; i++) {
				temp[i]=pencil[i+1];
			}
			pencil=temp;
		}
	}
	
	public void addPencil(int num) {
		int[] temp=new int[pencil.length+1];
		for(int i=0; i<pencil.length; i++) {
			temp[i+1]=pencil[i];
		}
		temp[0]=num;
		pencil=temp;
	}
	
	public void resetPencil() {
		pencil=new int[9];
		for(int i=0; i<9; i++) {
			pencil[i]=i+1;
		}
	}
	
	public int[] getPencil() {
		return pencil;
	}
	
	public int getValue() {
		return value;
	}

}
