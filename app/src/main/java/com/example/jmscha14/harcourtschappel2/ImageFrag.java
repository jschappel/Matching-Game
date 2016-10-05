package com.example.jmscha14.harcourtschappel2;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;



/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFrag extends Fragment {

    int number =1;
    int score = 0;

    static private int idList[] = {R.id.i0, R.id.i1, R.id.i2,
            R.id.i3, R.id.i4, R.id.i5, R.id.i6, R.id.i7,
            R.id.i8,R.id.i9, R.id.i10, R.id.i11,
            R.id.i12, R.id.i13, R.id.i14, R.id.i15};

    //Arraylists
    ArrayList<Drawable> compareList = new ArrayList<>();
    ArrayList<ImageButton> IB = new ArrayList<>();
    ArrayList<Drawable> picList1 = new ArrayList<>();;
    ArrayList<Drawable> newList = new ArrayList<>();

    //sounds
    private SoundPool soundPool = null;
    private int clickSound;
    private int wrongSound;

    Handler h;

    int clickCount =0;

    Drawable puck;

    private void shuffle() {

        //get the pictures from the resource folder into an array list

        Resources r = getResources();
        for (int i =1;i<17;i++){
            int id = r.getIdentifier("hoc" + i, "drawable", getActivity().getPackageName());
            Drawable dr = getResources().getDrawable(id,null);
            picList1.add(dr);
        }

        int id = r.getIdentifier("puck", "drawable", getActivity().getPackageName());
        puck = getResources().getDrawable(id,null);

        //shuffle the arraylist
        Collections.shuffle(picList1);
        //put the first 8 items into finalPicList and and remove them for the previous
        for(int i = 0; i<8;i++){
            Drawable item = picList1.get(i);
            newList.add(item);
            newList.add(item);
            picList1.remove(i);
        }
        Collections.shuffle(newList);
    }




    private Boolean compare(ArrayList<Drawable> draw) {
        Drawable.ConstantState stateA = draw.get(0).getConstantState();
        Drawable.ConstantState stateB = draw.get(1).getConstantState();
        if(stateA.equals(stateB))
            return true;
        else{
            return false;
        }
    }


    // found on the android developer website
//https://developer.android.com/training/animation/cardflip.html#views
    public void flip(View view){
        Animator leftIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
        Animator leftOut = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_out);
        Animator rightIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_in);
        Animator rightOut = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_out);
        leftIn.setTarget(view);
        leftOut.setTarget(view);
        rightIn.setTarget(view);
        rightIn.setTarget(view);
        leftIn.start();
        leftOut.start();
        rightIn.start();
        leftOut.start();
    }


    public void startGame(View view){
        shuffle();

        h = new Handler();

        Resources res = getResources();
        final TextView couunter = (TextView) getActivity().findViewById(res.getIdentifier("number_of_clicks", "id",getActivity().getPackageName()));

        //creat the sounds
        this.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        this.clickSound = this.soundPool.load(this.getActivity(), R.raw.correct, 1);
        this.wrongSound = this.soundPool.load(this.getActivity(), R.raw.wrong1, 1);


        for( int i= 0; i<16; i++) {

            final int temp = i;
            final ImageButton ib = (ImageButton) view.findViewById(idList[i]);
            final int in = 1;

            //  final int number = 1;
            ib.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {

                    //see if the restart button was pressed
                    clickCount++;
                    ((MainActivity)getActivity()).countUpdate(clickCount);

                    flip(view);
                    ib.setBackground(newList.get(temp));

                    //make the button not clickable
                    ib.setClickable(false);

                    //add the button to the compare list
                    //add the id to the id list
                    compareList.add(newList.get(temp));
                    IB.add(ib);

                    if(compareList.size() > 1){
                        Drawable d1 = compareList.get(0);
                        Drawable d2 = compareList.get(1);
                        compareList.clear();
                        compareList.add(d1);
                        compareList.add(d2);

                    }

                    if (number%2 == 0) {
                        //add thinking icon
                        ((MainActivity)getActivity()).startThinking();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (compare(compareList) == true) {
                                    soundPool.play(clickSound,1f,1f,1,0,1f);

                                    //set the views to stay face up
                                    ImageButton ib1 = IB.get(1);
                                    ib1.setBackground(compareList.get(1));
                                    ImageButton ib2 = IB.get(0);
                                    ib2.setBackground(compareList.get(0));

                                    //empty the list
                                    compareList.clear();
                                    IB.clear();

                                    //add one to the score
                                    score++;

                                    //see if your cool enough to win the game
                                    if(score == 8){
                                        Toast.makeText(getActivity(),"Congratulations, hit restart to play again!",Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    soundPool.play(wrongSound,1f,1f,1,0,1f);

                                    //set the images back to hockeypucks

                                    flip(view);

                                    ImageButton ib1 = IB.get(1);
                                    ib1.setBackground(puck);
                                    ib1.setClickable(true);

                                    ImageButton ib2 = IB.get(0);
                                    ib2.setBackground(puck);
                                    ib2.setClickable(true);

                                    //empty the list
                                    compareList.clear();
                                    IB.clear();

                                }
                                ((MainActivity) getActivity()).stopThinking();
                            }
                        }, 1000);
                    }
                    number++;
                }
            });//on click listener
        }
    }//startGame



    public void restartGame() {
        //mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
        // ...
        startGame(getView());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_image, container, false);
       final View root = inflater.inflate(R.layout.fragment_image, container, false);

        startGame(root);
        return root;
    }
}
