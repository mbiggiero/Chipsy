package com.mbgs.chipsy;

public class Input {
	private boolean keys[];

	public Input(){
		this.keys=new boolean[16];
	}

	public void resetInput(){
		for (int i = 0; i < 16; i++){
			this.keys[i]=false;
		}
	}

	public void setKey(int a,boolean b){
		this.keys[a]=b;
	}
	public boolean getKey(char a){
		return this.keys[a];
	}
}