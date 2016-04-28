package com.mbgs.chipsy;


import java.util.Timer;
import java.util.TimerTask;


public class SoundTimer {
	private Timer myTimer;
	private char timer;
	private boolean isTicking;
	public void reset() {
		this.timer = 0;
	}
	public char getTimer() {
		return this.timer;
	}
	public void setTimer(char a) {
		this.timer=a;
	}
	
	public boolean getState(){
		return this.isTicking;
	}
	public void setState(boolean a){
		this.isTicking=a;
	}
	
	public void runTimer(){
		this.isTicking=true;

		/*myTimer = new Timer();

		myTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				synchronized (this) {

				}
			}
		}, 0, 1000 / 60);*/
	}
	
	public void pauseTimer(){
		this.isTicking=false;
	}
	public void tick(){
		this.timer=this.timer--;
	}
}
