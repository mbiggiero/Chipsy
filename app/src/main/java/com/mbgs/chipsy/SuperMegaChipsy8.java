package com.mbgs.chipsy;

/**
 * Main Chip-8 Class
 * @author Mark Biggiero
 * @version 09/11/2015
 * */

import java.io.File;
import java.util.concurrent.Executors;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SuperMegaChipsy8 {
	protected RAM chipRAM;	
	protected CPU chipCPU;
	protected SoundTimer ST;
	protected DelayTimer DT;
	protected Graphics chipGPU;
	protected Input chipKeys;
	protected int CPUSpeed;
	protected Timer timer;
	protected int mode;
	protected int previousMode;
	public boolean hasRom;
	public boolean isRunning;
	protected boolean isSoundEnabled;

	File file;
	
	public SuperMegaChipsy8(int chipMode){
		this.mode=chipMode;
		this.isSoundEnabled=true;
		this.CPUSpeed=20;
		this.initialize();

		file = new File("file:///android_asset/Space Invaders [David Winter].ch8");
	}

	public int getCPUSpeed(){
		return this.CPUSpeed;
	}

	public void setCPUSpeed(int a){
		this.CPUSpeed = a;
	}

	public void initialize(){
		Executors.newSingleThreadScheduledExecutor();



		this.isRunning = false;
		chipRAM = new RAM(0x200);
		ST = new SoundTimer();
		DT = new DelayTimer();

		chipGPU = new Graphics(this.mode);
		chipCPU = new CPU();

		chipKeys = new Input();
		chipGPU.ClearScreen();
		chipRAM.wipeRAM();
		chipRAM.loadFont();			
		ST.reset();
		DT.reset();
		//this.reset();
	}

	public void reset(){
		//this.isRunning = false;
		//Chipsy.myChipsy8.chipCPU.AutoDetectSettings();
		chipGPU.ClearScreen();
		chipRAM.softReset();
		chipRAM.loadFont();
		chipKeys.resetInput();
		//chipRAM.loadAtRAM(0x200,file);
		chipCPU.Reset();
		ST.reset();
		DT.reset();


//		Chipsy.drawView.repaint();
	}
		
	public void Run() {
		Chipsy.drawView.SetupDrawingSurface(Chipsy.myChipsy8.mode);
		Chipsy.myChipsy8.chipCPU.AutoDetectSettings();
		Log.v("Chipsy", "RUNNING");
		Chipsy.myChipsy8.isRunning=true;
		this.ST.runTimer();
		this.DT.runTimer();

		if (timer!=null){
			timer.cancel();
			}
		timer = new Timer();

		timer.scheduleAtFixedRate( new TimerTask() {
			public void run() {
				synchronized(this){
					if (Chipsy.myChipsy8.isRunning){
						Chipsy.myChipsy8.chipCPU.Cycle(CPUSpeed); //	5*60=300
						Chipsy.myChipsy8.chipGPU.setFlag(true);		//	50*60=3000
					}else{
						Chipsy.myChipsy8.ST.setState(false);
						Chipsy.myChipsy8.DT.setState(false);
						timer.cancel();
					}

				}
			}
		}, 0, 1000/60);

	}
	public void close() {
		chipGPU.ClearScreen();
		chipRAM.softReset();
		chipRAM.wipeRAM();
		chipRAM.loadFont();	//TODO maybe rmeove later	
		chipCPU.Reset();
		ST.reset();
		DT.reset();
		chipKeys.resetInput();
		//Chipsy.chipsyWindow.setTitle(chipCPU.currentTitle);


		
	}
		
}
