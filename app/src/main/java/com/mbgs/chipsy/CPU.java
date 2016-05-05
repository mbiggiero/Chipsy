package com.mbgs.chipsy;

import android.util.Log;

import java.util.Random;

public class CPU {
	private char[] V;
	private short pc;
	private int I;
	private char opcode;
	private char pOpcode;
	private char op1;
	private char op2;
	private char op3;
	private char op4;
	private char nn;
	private char sp;
	private short stack[];
	private char nnn;
	private short ppc;
	private short pppc;
	private char ppOpcode;
	private boolean isRunning;
	private String pAssembly;
	private String assembly;	
	private String ppAssembly;
	private Random rand;
	protected Boolean fx1eFlag;
	private String romName;
	protected Boolean legacyMode;
	protected int currentHash;
	public double chipSpeed = 1;
	protected String currentTitle;
	private char[] FR;
	

	public CPU(){
		V = new char[16];
		FR = new char[8];
		this.legacyMode=false;
		this.fx1eFlag = true;
		this.Reset();
	}

	public void setRomName(String r){
		this.romName=r;
	}

	public String getRomName(){
		return this.romName;
	}
	
	public void setHash(int hash){
		this.currentHash=hash;
		Log.d("HASH", " is "+this.currentHash);
	}
	
	public int getHash(){
		return this.currentHash;
	}
	
	void Reset() {
		this.currentHash=0;
		this.romName = "Chipsy";
		WipeRegisters();	
		this.stack = new short[16];
		this.I = 0;		
		this.pc = 0x200;
		this.ppc = 0;
		this.pppc = 0;
		this.assembly = "Initialized";
		this.pAssembly = "Not Initialized";
		this.ppAssembly = "Not Initialized";
		this.opcode=0;
		this.pOpcode=0;
		this.ppOpcode = 0;
		this.sp=0;
		
		for (int i=0; i<16;i++){
			this.stack[i]=0;
		}		
	}
	
	
	protected void AutoDetectSettings() {

		switch (this.currentHash){
			case 1042316148:
				this.romName = "Animal Race";
				this.legacyMode = true;
			break;
			case -1539047181:
				this.romName = "Blinky (SuperChip8)";
				this.chipSpeed = 1.5;
				Chipsy.drawView.SetupDrawingSurface(2);
				break;
			case -1504861104:
				this.romName = "Blinky";
				break;
			case 845163141:
				this.romName = "Space Invaders";
				this.legacyMode = false;
			break;
			case -613156860:
				this.romName = "Fishie";
			break;
			case -307149892:
				this.romName = "15 Puzzle";
				break;
			case -613156864:
				this.romName = "Car (SuperChip8)";
				this.chipSpeed = 1.5;
				Chipsy.drawView.SetupDrawingSurface(2);
				break;
			case 908515207:
				this.romName = "Keypad Test";
				break;
			default: {this.romName = "Chipsy";Chipsy.drawView.SetupDrawingSurface(0);}
		}
		Log.d("NAME", " is " + this.romName);

		DetectMode();
	}

	public void Cycle(int j){
		this.isRunning=true;
		while(j>0){
			this.opcode=this.Fetch();
			//Log.v("CHIPSY", "DECODING " + String.format("%x",(short)this.opcode));
			this.Decode();
			//TODO only if debugging
		
			//TODO move to Timers later
			//at the end	
			j-=1;		
		}
	}
	
	private void DetectMode() {
		 if ((this.pc==0x200) && (Chipsy.myChipsy8.chipRAM.getOpcode(0x200)==0x1260)) {
			/* Chipsy.chipsyWindow.ResizeWindow(1,Chipsy.chipsyWindow.currentZoom);
			 Chipsy.myChipsy8.chipGPU.Initialize(1);
			 Chipsy.myChipsy8.mode = 1;
			this.pc=0x2C0;*/}else if (Chipsy.myChipsy8.mode==2){
				//Chipsy.chipsyWindow.ResizeWindow(2,Chipsy.chipsyWindow.currentZoom);
			}else{
				//Chipsy.chipsyWindow.ResizeWindow(0,Chipsy.chipsyWindow.currentZoom);
				 Chipsy.myChipsy8.chipGPU.Initialize(0);
				 Chipsy.myChipsy8.mode = 0;//TODO fix while changing speed
			}
		  // Make the interperter jump to address 0x2c0
		  
		 
	}

	private void WipeRegisters() {
		for (int i = 0; i<16; i++){
			V[i]=0;
		}
			for (int i = 0; i<8; i++){
				FR[i]=0;
		
		}
	}

	public char Fetch(){
		this.ppOpcode= this.pOpcode;
		this.pOpcode = this.opcode; 
		this.ppAssembly = this.pAssembly;
		this.pAssembly = this.assembly;	
		char opc = Chipsy.myChipsy8.chipRAM.getOpcode(this.pc);
		op1 = (char) ((opc&0xF000)>>12);
		op2 = (char) ((opc&0x0F00)>>8);
		op3 = (char) ((opc&0x00F0)>>4);
		op4 = (char) ((opc&0x000F));
		nn =  (char)(opc&0x00FF);
		nnn = (char)(opc&0x0FFF);
		return opc;
	}
	
	public void Decode(){
		//convert opcode to assembly
			switch (op1){
			case 0:
				switch(op2){
				case 0:
					switch(op3){
					case 0:
						if (op4==0){
							this.assembly="End of the ROM";
							Chipsy.myChipsy8.isRunning=false;
						}
					break;
					case 0xB:
						this.assembly="Scrolling up by "+op4+" pixels";
						Chipsy.myChipsy8.chipGPU.ScrollUp(op4);
					break;
					case 0xC:
						this.assembly="Scrolling down by "+op4+" pixels";
						Chipsy.myChipsy8.chipGPU.Scroll(op4);
					break;
					case 0xE:
						if(op4==0){
							this.assembly="Clear Screen";
							Chipsy.myChipsy8.chipGPU.ClearScreen();
						}else if (op4 == 0xE){
							this.assembly="Return from subroutine";
							if (this.sp!=0){
								this.sp--;
								this.pc=this.stack[this.sp];}
						}
					break;
					case 0xF:
						switch(op4){
							case 0xA:
								this.assembly="Setting Legacy Mode to true";
								this.legacyMode=true;
							break;
							case 0xB:
								this.assembly="Scroll screen 4 pixels to the right";
								Chipsy.myChipsy8.chipGPU.Scroll(-1);
							break;
							case 0xC:
								this.assembly="Scroll screen 4 pixels to the left";
								Chipsy.myChipsy8.chipGPU.Scroll(-2);
							break;
							case 0xD:
								this.assembly="Exit";
								Chipsy.myChipsy8.isRunning=false;
							break;
							case 0xE:
								if ( Chipsy.myChipsy8.mode != 0){
									this.assembly="Switch to Chip-8 Mode";
									//Chipsy.chipsyWindow.ResizeWindow(0,Chipsy.chipsyWindow.currentZoom);
									 Chipsy.myChipsy8.chipGPU.Initialize(0);
									 Chipsy.myChipsy8.mode = 0;
								 }
							break;
							case 0xF:
								if ( Chipsy.myChipsy8.mode != 2){
									this.assembly="Switch to SuperChip-8 Mode";
									//	Chipsy.chipsyWindow.ResizeWindow(2,Chipsy.chipsyWindow.currentZoom);
										 Chipsy.myChipsy8.chipGPU.Initialize(2);
										 Chipsy.myChipsy8.mode = 2;
									}
							break;
						}
					break;
					}
				break;
				case 2:
					this.assembly="Clear Screen";
					Chipsy.myChipsy8.chipGPU.ClearScreen();
					break;
				}
				//default: this.assembly="Machine Code: Unimplemented";this.pc-=2;Chipsy.myChipsy8.isRunning=false;;
				
				break;
			case 0x1://DONE
				this.assembly="Jump at "+(String.format("%x", (short)nnn)).toUpperCase();				
				this.pc= (short) nnn;
				this.pppc = this.ppc;
				this.ppc = this.pc;
				this.pc-=2;
				if ((this.pOpcode==this.ppOpcode)&&(this.opcode==this.pOpcode)&&(op1==1)){
					Chipsy.myChipsy8.isRunning=false;

				}
				break;
			case 0x2://DONE
				this.assembly="Call subroutine at "+(String.format("%x", (short)nnn)).toUpperCase();
				this.stack[this.sp]=this.pc;
				this.sp++;
				this.pc=(short)nnn;
				this.pppc = this.ppc;
				this.ppc = this.pc;
				this.pc-=2;
				break;
			case 0x3://DONE
				this.assembly="Skip instruction if V["+(short)op2+"]'s "+(short)this.V[op2]+" == "+(short)nn;
				if (this.V[op2]==nn){
					this.pc+=2;
				}
				break;
			case 0x4://DONE
				this.assembly="Skip instruction if V["+(short)op2+"]'s "+(short)this.V[op2]+" != "+(short)nn;
				if (this.V[op2]!=nn){
					this.pc+=2;
				}
				break;
			case 0x5://DONE
				this.assembly="Skip instruction if V["+(short)op2+"]'s "+(short)this.V[op2]+" == "+(short)this.V[op3];
				if (this.V[op2]==this.V[op3]){
					this.pc+=2;
				}
				break;
			case 0x6://DONE
				this.V[op2]=nn;
				this.assembly="Load "+(short) nn+" in V["+(short)op2+"]";
				break;
			case 0x7://DONE
				this.assembly="Add "+(short)nn+" to "+(short)this.V[op2]+" in V["+(short)op2+"]";
					this.V[op2]=(char) ((this.V[op2]+nn)&0xFF);
				break;
			case 0x8:
				switch(op4){
				case 0x0://DONE
					this.V[op2]=this.V[op3];
					this.assembly="Load V["+(short)op3+"]'s "+(short)this.V[op2]+" in V["+(short)op2+"]";
					break;
				case 0x1://DONE
					this.assembly="Set V["+(short)op2+"] to V["+(short)op2+"] OR V["+(short)op3+"]";
					this.V[op2]|=this.V[op3];
					break;
				case 0x2://DONE
					this.assembly="Set V["+(short)op2+"] to V["+(short)op2+"] AND V["+(short)op3+"]";
					this.V[op2]&=this.V[op3];
					break;
				case 0x3://DONE
					this.assembly="Set V["+(short)op2+"] to V["+(short)op2+"] XOR V["+(short)op3+"]";
					this.V[op2]^=this.V[op3];
					break;
				case 0x4://DONE
					this.assembly="Add V["+(short)op3+"]'s "+(short)this.V[op3]+" to V["+(short)op2+"]'s "+(short)this.V[op2];
					short temp = (short) ((this.V[op2]+this.V[op3])>>8);
					this.V[op2]=(char) ((this.V[op2]+this.V[op3])&0xFF);
					this.V[0xF]=(char) temp;
					break;
				case 0x5://DONE
					this.assembly="Set V["+(short)op2+"] to "+(short)this.V[op2]+" - V["+(short)op3+"]'s "+(short)this.V[op3];
					short temp2 = (short) this.V[op2];
					this.V[op2] = (char) ( (this.V[op2]-this.V[op3])&0xFF);
					if (this.V[op3]>temp2){
						this.V[0xF]=0;
					}else{
						this.V[0xF]=1;
					}
					break;
				case 0x6://DONE				
					if (legacyMode){
						this.assembly="Shift V["+(short)op3+"]'s "+(short)this.V[op2]+" right by 1. Set V[F] to y";
						char temp6 = (char) (this.V[op3]&0x1);
						this.V[op2]= (char) ((this.V[op3]>>1)&0xFF);
						this.V[0xF]=temp6;
					}else{
						this.assembly="Shift V["+(short)op2+"]'s "+(short)this.V[op2]+" right by 1. Set V[F] to y";
						char temp6 = (char) (this.V[op2]&0x1);
						this.V[op2]= (char) ((this.V[op2]>>1)&0xFF);
						this.V[0xF]=temp6;
					}
					break;
				case 0x7://DONE
					this.assembly="Set V["+(short)op2+"] to V["+(short)op3+"]'s "+(short)this.V[op3]+" - V["+(short)op2+"]'s "+(short)this.V[op2];
					short temp3 = (short) this.V[op2];
					this.V[op2] =  (char) ((this.V[op3]-this.V[op2])&0xFF);
					if (temp3>this.V[op3]){
						this.V[0xF]=0;
					}else{
						this.V[0xF]=1;
					}
					break;
				case 0xE://DONE
					if (legacyMode){
						this.assembly="Shift V["+(short)op3+"]'s "+(short)this.V[op2]+" left by 1. Set V[F] to y1";
						char temp6 = (char) ((this.V[op3]&0x80)>>7);
						this.V[op2]=(char) ((this.V[op3]<<0x1)&0xFF);
						this.V[0xF]=temp6;						
					}else{
						this.assembly="Shift V["+(short)op2+"]'s "+(short)this.V[op2]+" left by 1. Set V[F] to y1";
						char temp6 = (char) ((this.V[op2]&0x80)>>7);
						this.V[op2]= (char) ((this.V[op2]<<0x1)&0xFF);
						this.V[0xF]=temp6;
					}
					break;					
				}
				break;
				
			case 0x9://DONE
				this.assembly="Skip instruction if V["+(short)op2+"]'s "+(short)this.V[op2]+" != "+(short)this.V[op3];
				if (this.V[op2]!=this.V[op3]){
					this.pppc = this.ppc;
					this.ppc = this.pc;
					this.pc+=2;
				}
				break;
				
			case 0xA://DONE
				this.assembly="Load "+(String.format("%x", (short)nnn)).toUpperCase()+" in I";	
				this.I = (short) nnn;
				break;
				
			case 0xB://DONE
				this.assembly="Jump at V[0]'s "+(short)this.V[0]+" + "+(String.format("%x", (short)nn)).toUpperCase();				
				this.pc= (short) (nn+this.V[0]);
				this.pppc = this.ppc;
				this.ppc = this.pc;
				this.pc-=2;
				break;
			case 0xC://DONE
				rand = new Random();
				byte temp = (byte) ((rand.nextInt(256)));
				this.assembly="Set V["+(short)op2+"] to a random byte & "+(short)nn;				
			    this.V[op2] = (char) (nn&(temp&0xFF));
				break;
			case 0xD://DONE except warping
				if(this.op4==0){
					//Log.d("Chipsy", "dxy0");
					char x = this.V[op2];
					char y = this.V[op3];
					short pixel;
					this.V[0xF] = 0;
					int offset=0x8000;
					int test = 2;
					int test2= 16;
					if (Chipsy.myChipsy8.mode==0){
						offset=0x80;
						test=1;
						test2 = 8;
					}else{
						offset=0x8000;
						test=2;
						test2=16;
					}
					
					  for (char yline = 0; yline < 16; yline++)
					  {
					    pixel = Chipsy.myChipsy8.chipRAM.get2Byte(this.I+yline*test);
						  if (Chipsy.myChipsy8.mode==0){
							  pixel = (short)Chipsy.myChipsy8.chipRAM.getByte(this.I+yline);
						  }else{
							  pixel = Chipsy.myChipsy8.chipRAM.get2Byte(this.I+yline*test);
						  }

					    for(char xline = 0; xline < test2; xline++)
					    {
					      if((pixel & (offset >> xline)) != 0){
					    	if ((x+xline>=0)&&(x+xline<Chipsy.myChipsy8.chipGPU.getWidth())&&(y+yline>=0)&&(y+yline<Chipsy.myChipsy8.chipGPU.getHeight()))	{	      
					        if(Chipsy.myChipsy8.chipGPU.getPixel((xline+x), (yline+y)) == 1){
					        	this.V[0xF] = 1;  
					          }  
					        Chipsy.myChipsy8.chipGPU.setPixel((xline+x), (y+yline),1^Chipsy.myChipsy8.chipGPU.getPixel((xline+x),(yline+y)));
							Chipsy.myChipsy8.chipGPU.setFlag(true);}
					      }
					    }
					  }		

						this.assembly="Draw sprite at V["+(short)op2+"]'s "+(short)this.V[op2]+", V["+(short)op3+"]'s "+(short)this.V[op3]+" with height of 16. Set V[F] to "+(byte)this.V[0xF];
				}
				else{
					char x = this.V[op2];
					char y = this.V[op3];
					char height = op4;
					char pixel;
					this.V[0xF] = 0;
					
					  for (char yline = 0; yline < height; yline++)
					  {
					    pixel = Chipsy.myChipsy8.chipRAM.getByte(this.I+yline);
					    for(char xline = 0; xline < 8; xline++)
					    {
					      if((pixel & (0x80 >> xline)) != 0){				    	  
					    	if ((x+xline>=0)&&(x+xline<Chipsy.myChipsy8.chipGPU.getWidth())&&(y+yline>=0)&&(y+yline<Chipsy.myChipsy8.chipGPU.getHeight()))	{	      
					        if(Chipsy.myChipsy8.chipGPU.getPixel((xline+x), (yline+y)) == 1){
					        	this.V[0xF] = 1;  
					          }  
					        Chipsy.myChipsy8.chipGPU.setPixel((xline+x), (y+yline),1^Chipsy.myChipsy8.chipGPU.getPixel((xline+x),(yline+y)));
							Chipsy.myChipsy8.chipGPU.setFlag(true);}
					      }
					    }
					  }		

						this.assembly="Draw sprite at V["+(short)op2+"]'s "+(short)this.V[op2]+", V["+(short)op3+"]'s "+(short)this.V[op3]+" with height of "+(short)op4+". Set V[F] to "+(byte)this.V[0xF];
						
				}
				break;
			case 0xE:
				switch(nn){
				case 0x9E://DONE
					this.assembly="Skip instruction if the key at V["+(short)op2+"] is pressed. Key is pressed == "+Chipsy.myChipsy8.chipKeys.getKey(op2);
					 if(Chipsy.myChipsy8.chipKeys.getKey(this.V[op2])==true){
			                this.pc += 2;
			                }
					break;
				case 0xA1://DONE
					this.assembly="Skip instruction if the key at V["+(short)op2+"] is not pressed. Key is pressed == "+Chipsy.myChipsy8.chipKeys.getKey(op2);
					 if(Chipsy.myChipsy8.chipKeys.getKey(this.V[op2])==false){
			                this.pc += 2;
			                }
					
					break;
					default:
				}
				break;
			case 0xF:
				switch(nn){
					case 0x07://DONE
						this.assembly="Set V["+(byte)op2+"]'s "+(short)this.V[op2]+" to DT";
						this.V[op2]=Chipsy.myChipsy8.DT.getTimer();
					break;
					case 0x0A:
						this.assembly="Wait for keypress and store it in V["+(byte)op2+"]";
						boolean keyPress = false;
				        for(char i = 0; i < 16; ++i){
				                if(Chipsy.myChipsy8.chipKeys.getKey(i) == true){
				                        this.V[op2] =  i;
				                        //Chipsy.myChipsy8.chipKeys.setKey(i,false);   //this line is important, or the input becomes bugged
				                        keyPress = true;
				                        break;
				                }
				        }
				        if(!keyPress){
				                this.pc -= 2;       
				        }
					break;
					case 0x15://DONE
						this.assembly="Set DT to V["+(byte)op2+"]'s "+(short)this.V[op2];
						Chipsy.myChipsy8.DT.setTimer(this.V[op2]);
					break;
					case 0x18://DONE
						this.assembly="Set ST to V["+(byte)op2+"]'s "+(short)this.V[op2];
						Chipsy.myChipsy8.ST.setTimer(this.V[op2]);
					break;
					case 0x1E://DONE
						this.assembly="Set I to "+(String.format("%x", this.I)).toUpperCase()+" + "+(short)this.V[op2];
						int temp8 = this.I;
						this.I=(short) ((this.I+this.V[op2])&0xFFF);
						if ((this.fx1eFlag)&&((temp8+this.V[op2])>0xFFF)){
							this.V[0xF]=1;
							this.assembly="Set I to "+(String.format("%x", this.I)).toUpperCase()+" + "+(short)this.V[op2]+". V[F] is set to 1";
						}
					break;
					case 0x29://DONE
						this.assembly="Set I to the location of the 5 bitcharacter "+(String.format("%x", (byte)this.V[op2])).toUpperCase();
						this.I=(short) (0x50+this.V[op2]*5);
					break;
					case 0x30://DONE
						this.assembly="Set I to the location of the 10bit character "+(String.format("%x", (byte)this.V[op2])).toUpperCase();
						this.I=(short) (0x100+this.V[op2]*10);
					break;
					case 0x33://DONE
						this.assembly="Store BCD of V["+(byte)op2+"]'s "+(short)this.V[op2]+" starting at I";
						Chipsy.myChipsy8.chipRAM.setByte(I,(char) (this.V[op2]/100));//     = V[(opcode & 0x0F00) >> 8] / 100;
						Chipsy.myChipsy8.chipRAM.setByte(I+1,(char) ((char) (this.V[op2]/10)%10));// (V[(opcode & 0x0F00) >> 8] / 10) % 10;
						Chipsy.myChipsy8.chipRAM.setByte(I+2,(char) ((char) (this.V[op2]%100)%10));// (V[(opcode & 0x0F00) >> 8] % 100) % 10;
					break;
					case 0x55://DONE
						this.assembly="Copy V[0]-V["+(byte)op2+"] to memory starting at I";
							for (int i=0;i<=op2;i++){
								Chipsy.myChipsy8.chipRAM.setByte(this.I+i,this.V[i]);
							}
							if (this.legacyMode) {
								this.I=(short) (this.I+op2+1);
								this.assembly="Copy V[0]-V["+(byte)op2+"] to memory starting at I. I is set to I + 1 +"+(byte)op2;
							}						
					break;
					case 0x65://DONE
						this.assembly="Copy memory starting at I to V[0]-V["+(byte)op2+"]";
							for (int i=0;i<=op2;i++){
								this.V[i]=Chipsy.myChipsy8.chipRAM.getByte(this.I+i);
							}
							if (this.legacyMode) {
								this.I=(short) (this.I+op2+1);
								this.assembly="Copy memory starting at I to V[0]-V["+(byte)op2+"]. I is set to I + 1 +"+(byte)op2;
							}
					break;
					case 0x75://DONE
						//TODO Remove later
						this.assembly="Copy V[0]-V["+(byte)op2+"] to FR[0]-FR["+(byte)op2+"]";
							for (int i=0;i<=op2;i++){
								this.FR[i]=this.V[i];
							}
					break;
					case 0x85://DONE
						//TODO Remove later
						this.assembly="Copy FR[0]-FR["+(byte)op2+"] to V[0]-V["+(byte)op2+"]";
							for (int i=0;i<=op2;i++){
								this.V[i]=this.FR[i];
							}
					break;					
				}
			break;
		}

		this.pppc = this.ppc;
		this.ppc = this.pc;
		this.pc +=2;	
	}

	public char getV(int j) {
		return this.V[j];
	}

	public short getPC() {
		return (short) (this.pc&0xFFFF);//TODO mega chip
	}
	
	public short getPPC() {
		return (short) (this.ppc&0xFFFF);
	}
	
	public short getI(int j) {
		return (short) ((this.I+j)&0xFFFF);
	}
	
	public String getAssembly(){
		return this.assembly;
	}
	
	public String getPAssembly(){
		return this.pAssembly;
	}

	public String getPPAssembly(){
		return this.ppAssembly;
	}
	
	public char getOpcode() {
		return this.opcode;
	}

	public char getPOpcode() {
		return this.pOpcode;
	}
	
	public char getPPOpcode() {
		return this.ppOpcode;
	}

	
}

