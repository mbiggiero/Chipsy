package com.mbgs.chipsy;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class Chipsy extends AppCompatActivity {

    public static GUI drawView;
    public static SuperMegaChipsy8 myChipsy8;
    private static int chipMode;
    public static AssetManager test;
    public static java.lang.String getRomNameMain;
    private TextureView mTextureView;
    private GLSurfaceView.Renderer mRenderer;
    private SeekBar sb;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonE;
    private Button buttonF;
    public static int GUIWidth;
    public static int GUIHeight;
    public String romNameMain;
    private TextView titleTxt;
    public static  RelativeLayout layout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        chipMode=0;
        myChipsy8 = new SuperMegaChipsy8(chipMode);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            romNameMain = extras.getString("romName");
        }
        drawView = new GUI(this,0,1,romNameMain);
    drawView.setWillNotDraw(false);

        setContentView(R.layout.activity_main);
        addListenerOnButton();
        //setContentView(drawView);


        myChipsy8.Run();
        titleTxt = (TextView) findViewById(R.id.titleText);
        titleTxt.setText(Chipsy.myChipsy8.chipCPU.getRomName());

    }



    @Override
    public void onBackPressed(){
        finish();
    }

    public void addListenerOnButton(){

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);
        buttonE = (Button) findViewById(R.id.buttonE);
        buttonF = (Button) findViewById(R.id.buttonF);


        button0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(0, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(0, false);
                }
                return false;
            }

        });

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(1, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(1, false);
                }
                return false;
            }

        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(2, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(2, false);
                }
                return false;
            }

        });

        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(3, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(3, false);
                }
                return false;
            }

        });

        button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(4, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(4, false);
                }
                return false;
            }

        });

        button5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(5, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(5, false);
                }
                return false;
            }

        });

        button6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(6, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(6, false);
                }
                return false;
            }

        });

        button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(7, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(7, false);
                }
                return false;
            }

        });

        button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(8, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(8, false);
                }
                return false;
            }

        });

        button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(9, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(9, false);
                }
                return false;
            }

        });

        buttonA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(10, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(10, false);
                }
                return false;
            }

        });

        buttonB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(11, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(11, false);
                }
                return false;
            }

        });

        buttonC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(12, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(12, false);
                }
                return false;
            }

        });

        buttonD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(13, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(13, false);
                }
                return false;
            }

        });

        buttonE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(14, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(14, false);
                }
                return false;
            }

        });


        buttonF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myChipsy8.chipKeys.setKey(15, true);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    myChipsy8.chipKeys.setKey(15, false);
                }
                return false;
            }

        });

        sb = (SeekBar)(findViewById(R.id.seekBar));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                Chipsy.myChipsy8.setCPUSpeed(progress+5);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }



    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


}
