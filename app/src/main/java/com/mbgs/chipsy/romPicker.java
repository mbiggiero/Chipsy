package com.mbgs.chipsy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.mbgs.chipsy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class romPicker extends AppCompatActivity {
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";

    ExpandableListAdapter expandableListAdapter;

    /*expandable list*/
    ExpandableListView expandableListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rom_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Choose rom file:");

        String[] categories={

                "Chip-8 Demos",
                "Chip-8 Games",
                "Chip-8 Hires",
                "Chip-8 Programs",
                "SuperChip-8 Demos",
                "SuperChip-8 Games",
                "SuperChip-8 Test Programs"

        };
        String[] myStringArray={
                "Maze [David Winter, 199x].ch8",
                "Maze (alt) [David Winter, 199x].ch8",
                "Particle Demo [zeroZshadow, 2008].ch8",
                "Sierpinski [Sergey Naydenov, 2010].ch8",
                "Stars [Sergey Naydenov, 2010].ch8",
                "Trip8 Demo (2008) [Revival Studios].ch8",
                "Zero Demo [zeroZshadow, 2007].ch8",

                "15 Puzzle [Roger Ivie].ch8",
                "15 Puzzle [Roger Ivie] (alt).ch8",
                "Addition Problems [Paul C. Moews].ch8",
                "Airplane.ch8",
                "Animal Race [Brian Astle].ch8",
                "Astro Dodge [Revival Studios, 2008].ch8",
                "Biorhythm [Jef Winsor].ch8",
                "Blinky [Hans Christian Egeberg, 1991].ch8",
                "Blinky [Hans Christian Egeberg, 1991] (alt).ch8",
                "Blitz [David Winter].ch8",
                "Bowling [Gooitzen van der Wal].ch8",
                "Breakout (Brix hack) [David Winter, 1997].ch8",
                "Breakout [Carmelo Cortez, 1979].ch8",
                "Brick (Brix hack, 1990).ch8",
                "Brix [Andreas Gustafsson, 1990].ch8",
                "Cave.ch8",
                "Coin Flipping [Carmelo Cortez, 1978].ch8",
                "Connect 4 [David Winter].ch8",
                "Craps [Camerlo Cortez, 1978].ch8",
                "Deflection [John Fort].ch8",
                "Figures.ch8",
                "Filter.ch8",
                "Guess [David Winter].ch8",
                "Guess [David Winter] (alt).ch8",
                "Hidden [David Winter, 1996].ch8",
                "Hi-Lo [Jef Winsor, 1978].ch8",
                "Kaleidoscope [Joseph Weisbecker, 1978].ch8",
                "Landing.ch8",
                "Lunar Lander (Udo Pernisz, 1979).ch8",
                "Mastermind FourRow (Robert Lindley, 1978).ch8",
                "Merlin [David Winter].ch8",
                "minimal.ch8",
                "Missile [David Winter].ch8",
                "Most Dangerous Game [Peter Maruhnic].ch8",
                "Nim [Carmelo Cortez, 1978].ch8",
                "Paddles.ch8",
                "Pong (1 player).ch8",
                "Pong (alt).ch8",
                "Pong [Paul Vervalin, 1990].ch8",
                "Pong 2 (Pong hack) [David Winter, 1997].ch8",
                "Programmable Spacefighters [Jef Winsor].ch8",
                "Puzzle.ch8",
                "Reversi [Philip Baltzer].ch8",
                "Rocket [Joseph Weisbecker, 1978].ch8",
                "Rocket Launch [Jonas Lindstedt].ch8",
                "Rocket Launcher.ch8",
                "Rush Hour [Hap, 2006].ch8",
                "Rush Hour [Hap, 2006] (alt).ch8",
                "Russian Roulette [Carmelo Cortez, 1978].ch8",
                "Sequence Shoot [Joyce Weisbecker].ch8",
                "Shooting Stars [Philip Baltzer, 1978].ch8",
                "Slide [Joyce Weisbecker].ch8",
                "Soccer.ch8",
                "Space Flight.ch8",
                "Space Intercept [Joseph Weisbecker, 1978].ch8",
                "Space Invaders [David Winter].ch8",
                "Spooky Spot [Joseph Weisbecker, 1978].ch8",
                "Squash [David Winter].ch8",
                "Submarine [Carmelo Cortez, 1978].ch8",
                "Sum Fun [Joyce Weisbecker].ch8",
                "Syzygy [Roy Trevino, 1990].ch8",
                "Tank.ch8",
                "Tapeworm [JDR, 1999].ch8",
                "Tetris [Fran Dachille, 1991].ch8",
                "Tic-Tac-Toe [David Winter].ch8",
                "Timebomb.ch8",
                "Tron.ch8",
                "UFO [Lutz V, 1992].ch8",
                "Vers [JMN, 1991].ch8",
                "Vertical Brix [Paul Robson, 1996].ch8",
                "Wall [David Winter].ch8",
                "Wipe Off [Joseph Weisbecker].ch8",
                "Worm V4 [RB-Revival Studios, 2007].ch8",
                "X-Mirror.ch8",
                "ZeroPong [zeroZshadow, 2007].ch8",

                "Astro Dodge Hires [Revival Studios, 2008].ch8",
                "Hires Maze [David Winter, 199x].ch8",
                "Hires Particle Demo [zeroZshadow, 2008].ch8",
                "Hires Sierpinski [Sergey Naydenov, 2010].ch8",
                "Hires Stars [Sergey Naydenov, 2010].ch8",
                "Hires Test [Tom Swan, 1979].ch8",
                "Hires Worm V4 [RB-Revival Studios, 2007].ch8",
                "Trip8 Hires Demo (2008) [Revival Studios].ch8",

                "BMP Viewer - Hello (C8 example) [Hap, 2005].ch8",
                "Chip8 emulator Logo [Garstyciuks].ch8",
                "Chip8 Picture.ch8",
                "Clock Program [Bill Fisher, 1981].ch8",
                "Delay Timer Test [Matthew Mikolay, 2010].ch8",
                "Division Test [Sergey Naydenov, 2010].ch8",
                "Fishie [Hap, 2005].ch8",
                "Framed MK1 [GV Samways, 1980].ch8",
                "Framed MK2 [GV Samways, 1980].ch8",
                "IBM Logo.ch8",
                "Jumping X and O [Harry Kleinberg, 1977].ch8",
                "Keypad Test [Hap, 2006].ch8",
                "Life [GV Samways, 1980].ch8",
                "Minimal game [Revival Studios, 2007].ch8",
                "Random Number Test [Matthew Mikolay, 2010].ch8",
                "SQRT Test [Sergey Naydenov, 2010].ch8",

                "Bounce [Les Harris].ch8",
                "Car Race Demo [Erik Bryntse, 1991].ch8",
                "Climax Slideshow - Part 1 [Revival Studios, 2008].ch8",
                "Climax Slideshow - Part 2 [Revival Studios, 2008].ch8",
                "Robot.ch8",
                "SCSerpinski [Sergey Naydenov, 2010].ch8",
                "SCStars  [Sergey Naydenov, 2010].ch8",
                "Super Particle Demo [zeroZshadow, 2008].ch8",
                "SuperMaze [David Winter, 199x].ch8",
                "SuperTrip8 Demo (2008) [Revival Studios].ch8",
                "Worms demo.ch8",

                "Alien [Jonas Lindstedt, 1993].ch8",
                "Ant - In Search of Coke [Erin S. Catto].ch8",
                "Blinky [Hans Christian Egeberg, 1991].sc8",
                "Car [Klaus von Sengbusch, 1994].ch8",
                "Field! [Al Roland, 1993].ch8",
                "Field! [Al Roland, 1993] (alt).ch8",
                "H. Piper [Paul Raines, 1991].ch8",
                "Joust [Erin S. Catto, 1993].ch8",
                "Laser.ch8",
                "Loopz (with difficulty select) [Hap, 2006].ch8",
                "Loopz [Andreas Daumann].ch8",
                "Magic Square [David Winter, 1997].ch8",
                "Matches.ch8",
                "Mines! - The minehunter [David Winter, 1997].ch8",
                "Single Dragon (Bomber Section) [David Nurser, 1993].ch8",
                "Single Dragon (Stages 1-2) [David Nurser, 1993].ch8",
                "Sokoban [Hap, 2006].ch8",
                "Sokoban [Hap, 2006] (alt).ch8",
                "Spacefight 2091 [Carsten Soerensen, 1992].ch8",
                "Super Astro Dodge [Revival Studios, 2008].ch8",
                "SuperWorm V3 [RB, 1992].ch8",
                "SuperWorm V4 [RB-Revival Studios, 2007].ch8",
                "U-Boat [Michael Kemper, 1994].ch8",

                "BMP Viewer - Flip-8 logo [Newsdee, 2006].ch8",
                "BMP Viewer - Kyori (SC example) [Hap, 2005].ch8",
                "BMP Viewer - Let's Chip-8! [Koppepan, 2005].ch8",
                "BMP Viewer (16x16 tiles) (MAME) [IQ_132].ch8",
                "BMP Viewer (Google) [IQ_132].ch8",
                "Emutest [Hap, 2006].ch8",
                "Font Test [Newsdee, 2006].ch8",
                "Hex Mixt.ch8",
                "Line Demo.ch8",
                "SC Test.ch8",
                "SCHIP Test [iq_132].ch8",
                "Scroll Test (modified) [Garstyciuks].ch8",
                "Scroll Test.ch8",
                "SuperChip Test.ch8",
                "Test128.ch8"

        };

        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < 7; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, categories[i]);
            //curGroupMap.put(IS_EVEN, "This group is odd"+i);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();

            int games=7;
            if (i==1) games =75;
            else if (i==2) games = 8;
            else if (i==3) games = 16;
            else if (i==4) games = 11;
            else if(i==5) games =23;//7 75 23
            else if (i==6) games = 15;

            for (int j = 0; j < games; j++) {
                if (i==0){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j]);
                }else if (i==1){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+7]);
                }else if (i==2){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+82]);
                }else if (i==3){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+90]);
                }else if (i==4){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+106]);
                }else if (i==5){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+117]);
                }else if (i==6){
                    Map<String, String> curChildMap = new HashMap<String, String>();
                    children.add(curChildMap);
                    // curChildMap.put(NAME, "Child " + j);
                    curChildMap.put(IS_EVEN, myStringArray[j+140]);
                }
            }
            childData.add(children);
        }

// Set up our adapter
        expandableListAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
       // setListAdapter(mAdapter);

        expandableListView = (ExpandableListView)this.findViewById(R.id.listView);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               HashMap<String,String> map  = (HashMap<String,String>)expandableListAdapter.getChild(groupPosition,childPosition);

               String value = map.get(IS_EVEN);
                Log.d("Chipsy", " " + value);

                if (value!=null){
                Intent intent = new Intent(romPicker.this, Chipsy.class);
                intent.putExtra("romName", value);
                startActivity(intent);}
                return true;
            }



        });





    }



}
