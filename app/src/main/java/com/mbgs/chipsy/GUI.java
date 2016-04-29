package com.mbgs.chipsy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

@SuppressWarnings("serial")


public class GUI extends SurfaceView{
	Paint paint = new Paint();
	private int chipMode;
	private int pixelSize;
	private int drawXSize;
	private int drawYSize;
	private int gfxXSize;
	private int gfxYSize;
	private int xOffset;
	private int yOffset;
	private SurfaceHolder surfaceHolder;
	private int UIZoom = 1;
	private MyThread myThread;
	private String name;
	private Canvas cv;
	boolean SetUpD;
	int Width;
	int Height;


	public GUI(Context context, int chipMode, int zoom, String romm) {
		super(context);
		setWillNotDraw(false);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//context.setOnTouchListener(this);

		this.name = romm;
		init();
		this.SetUpD = false;
		this.UIZoom = 1;//3;
		this.chipMode = chipMode;



		Chipsy.myChipsy8.chipRAM.load2(context, 0x200, name);


	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Width = this.getMeasuredWidth();
		Height = Width / 2;
		Log.d("Chipsy ", Width + " " + Height);
		setLayoutParams(new android.widget.RelativeLayout.LayoutParams(Width,Height));
		SetUpD = true;

	}

	public GUI(Context context,
						 AttributeSet attrs) {
		super(context, attrs);
		init();
		this.chipMode = chipMode;
		setWillNotDraw(false);
	}

	public GUI(Context context,
						 AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		this.chipMode = chipMode;
		setWillNotDraw(false);
	}

	private void init(){
		myThread = new MyThread(this);

		surfaceHolder = getHolder();

		surfaceHolder.addCallback(new SurfaceHolder.Callback(){

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				myThread.setRunning(true);
				myThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder,
									   int format, int width, int height) {
				// TODO Auto-generated method stub

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				myThread.setRunning(false);
				while (retry) {
					try {
						myThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}});
	}



	public void OnDraw(Canvas canvas) {
		//TODO FIX SHIT AT BOTTOM RIGHT
		SetupDrawingSurface(Chipsy.myChipsy8.mode);//TODO DD
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(0, 0, drawXSize * this.UIZoom, drawYSize * this.UIZoom, paint);
		paint.setColor(Color.WHITE);

		for (int i = 0; i < this.gfxXSize ; i++){
			for (int j = 0; j < this.gfxYSize ; j++){
				if (Chipsy.myChipsy8.chipGPU.getFlag()){
//test
					if (Chipsy.myChipsy8.chipGPU.getPixel(i,j)==1){
						canvas.drawRect(this.xOffset+this.drawXSize / this.gfxXSize * i * this.UIZoom,
								this.yOffset+this.drawYSize / this.gfxYSize * j * this.UIZoom,
								this.xOffset+pixelSize * this.UIZoom+this.drawXSize / this.gfxXSize * i * this.UIZoom,
								this.yOffset+pixelSize * this.UIZoom+this.drawYSize / this.gfxYSize * j * this.UIZoom,
								paint);
						//canvas.drawRect(this.drawXSize / this.gfxXSize * i, this.drawYSize / this.gfxYSize * j, 10,10, paint);
						//Log.w("Chipsy", "ram with something");

					}
					//Chipsy.myChipsy8.chipGPU.setFlag(false);
				}
			}
		}
	}


	public void SetupDrawingSurface(int chipMode){
		//if (SetUpD == false) {//TODO STUPID POINTLESS LOOPS


			this.gfxXSize = Chipsy.myChipsy8.chipGPU.getWidth();
			this.gfxYSize = Chipsy.myChipsy8.chipGPU.getHeight();

			if (chipMode == 0) {
				this.drawXSize = Width;//64*6
				this.drawYSize = Width/2;//32*6
				this.xOffset = ((Width/2)%64)/2;//19;
				this.yOffset = ((Height/2)%32)/2 ;//5;
				this.pixelSize = Width/64;

			//	Log.d("Choipy", this.drawXSize+" "+this.drawYSize+" "+ this.pixelSize);
			} else if (chipMode == 1) {
				this.drawXSize = 320;
				this.drawYSize = 320;
				this.pixelSize = 5;
			} else if (chipMode == 2) {
				//Log.d("Chipsy","Super");
				this.drawXSize = Width;//128
				this.drawYSize = Width/2;//64
				this.xOffset = ((Width/2)%128)/2;//19;
				this.yOffset = ((Height/2)%64)/2;//5;
				this.pixelSize = Width/128;;
				//this.UIZoom = 1;
			}
			//Log.d("CHIPSY", "current mode " + "is " + chipMode + " " + this.gfxXSize + " " + this.gfxYSize);

		//}
		}

}

