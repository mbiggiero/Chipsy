package com.mbgs.chipsy;

public class Graphics {
	private int width;
	private int height;
	private int gfx[][];
	private int mode;
	private int palette[];
	private int spriteBlendMode;
	
	private boolean drawFlag;
	private char currentSpriteHeight;
	private char currentSpriteWidth;
	
	public Graphics(int chipMode){
		this.mode = chipMode;
		Initialize(this.mode);
	}

	public void Initialize(int a) {
		if (a==0){
			this.width = 64;
			this.height = 32;
		}else
		if (a==1){
			this.width = 64;
			this.height = 64;
		}else
		if (a==2){
			this.width = 128;
			this.height = 64;
		}
		
		gfx = new int [this.width][this.height];

	}
	
	void setPixel(int x, int y, int a){
		this.gfx[x][y]=a;
	}
	
	int getPixel(int x, int y){
		return this.gfx[x][y];
	}
	
	protected boolean getFlag(){
		return this.drawFlag;
	}
	
	void setFlag(boolean b){
		this.drawFlag=b;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

	public void ClearScreen() {
		for (int i = 0; i < this.getWidth(); i++){
			for (int j = 0; j < this.getHeight(); j++){
				this.setPixel(i, j,0);
				this.setFlag(true);
			}
		}
		
	}

	public void Scroll(int a) {
		//-2 left
		//-1 right
		//0...15 down
		if (a == -2){
			for (int i = 0; i < this.width; i++){
				for (int j = 0; j < this.height; j++){
					if (i+4>=this.width){
						this.gfx[i][j]=0;
					} else {
						this.gfx[i][j]=this.gfx[i+4][j];
					}
				}
			}
		}else if (a==-1){
			for (int i = this.width-1; i >= 0; i--){
				for (int j = this.height-1; j >= 0; j--){
					if (i-4<0){
						this.gfx[i][j]=0;
					} else {
						this.gfx[i][j]=this.gfx[i-4][j];
					}
				}
				
			}
		}else{
			for (int i = 0; i < this.width; i++){
				for (int j = this.height-1; j >= 0; j--){
					if (j<a){
						this.gfx[i][j]=0;
					}else{
						this.gfx[i][j]=this.gfx[i][j-a];
					}
				}
			}
		}
		
		this.setFlag(true);
		
	}

	public void ScrollUp(char a) {
		for (int i = 0; i < this.width; i++){
			for (int j = 0; j < this.height; j++){
				if (j+a>this.height){
					this.gfx[i][j]=0;
				}else{
					this.gfx[i][j]=this.gfx[i][j+a];
				}
			}
		}
		this.setFlag(true);
		
	}

	public void setSpriteHeight(char nn) {
		this.currentSpriteHeight = nn;
		
	}

	public void setSpriteWidth(char nn) {
		this.currentSpriteWidth = nn;
		
	}

	public char getSpriteHeight() {
		return this.currentSpriteHeight;
	}
	public char getSpriteWidth() {
		return this.currentSpriteWidth;
	}


}
