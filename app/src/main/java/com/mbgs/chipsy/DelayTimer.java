package com.mbgs.chipsy;

import java.util.Timer;
import java.util.TimerTask;


	public class DelayTimer {
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
						if (Chipsy.myChipsy8.DT.getState()){
							if (Chipsy.myChipsy8.DT.getTimer()>0){
								Chipsy.myChipsy8.DT.setTimer((char) (Chipsy.myChipsy8.DT.getTimer()-1));
							}
						}else{
							Chipsy.myChipsy8.DT.isTicking=false;
							Chipsy.myChipsy8.DT.myTimer.cancel();
						}
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


