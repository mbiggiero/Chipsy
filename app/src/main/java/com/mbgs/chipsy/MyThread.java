package com.mbgs.chipsy;

/**
 * Created by Mark on 10/04/2016.
 */
import android.graphics.Canvas;
import android.util.Log;

public class MyThread extends Thread {

    GUI myView;
    private boolean running = false;

    public MyThread(GUI view) {
        myView = view;
        myView.setWillNotDraw(false);
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        Log.v("THREAD","STARTED");
        while(running){

            Canvas canvas = myView.getHolder().lockCanvas();

            if(canvas != null){
                synchronized (myView.getHolder()) {
                    myView.OnDraw(canvas);

                    if (Chipsy.myChipsy8.DT.getState()){
                        if (Chipsy.myChipsy8.DT.getTimer()>0){
                            Chipsy.myChipsy8.DT.setTimer((char) (Chipsy.myChipsy8.DT.getTimer()-1));
                        }
                    }else{
                        Chipsy.myChipsy8.DT.setState(false);
                        Chipsy.myChipsy8.DT.reset();
                    }

                    if (Chipsy.myChipsy8.ST.getState()){
                        if (Chipsy.myChipsy8.ST.getState()){
                            if (Chipsy.myChipsy8.ST.getTimer()>0){
                                // System.out.println("beep");
                                if (Chipsy.myChipsy8.isSoundEnabled==true){
							/* try {
								    File yourFile = new File("beep.wav");
								    AudioInputStream stream;
								    AudioFormat format;
								    DataLine.Info info;
								    Clip clip;

								    stream = AudioSystem.getAudioInputStream(yourFile);
								    format = stream.getFormat();
								    info = new DataLine.Info(Clip.class, format);
								    clip = (Clip) AudioSystem.getLine(info);
								    clip.open(stream);
								    clip.start();
								}
								catch (Exception e) {
								    //whatevers
								}*/
                                }

                                Chipsy.myChipsy8.ST.setTimer((char) (Chipsy.myChipsy8.ST.getTimer()-1));
                            }
                        }else{
                            Chipsy.myChipsy8.ST.setState(false);
                            Chipsy.myChipsy8.ST.reset();
                        }
                    }

                }
                myView.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
                sleep(1000 / 60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Log.v("THREAD", " "+e.toString());
                e.printStackTrace();
            }

        }
    }

}
