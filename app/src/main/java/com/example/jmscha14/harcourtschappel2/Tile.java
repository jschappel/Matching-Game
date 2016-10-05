package com.example.jmscha14.harcourtschappel2;

import android.media.Image;
import android.view.View;

/**
 * Created by jmscha14 on 10/3/2016.
 */

public class Tile {
    // 0 means that the tile has not been matched yet
    // 1 means that the tail is no longer playable
    private int matched =0;
    private View mView;
    public final int aimage;


    private final ImageFrag mGame;

    public Tile(ImageFrag game, int image) {
        this.mGame = game;
        this.aimage = image;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }


    // check if the tile can be pressed
    public boolean isPlayable(Tile t){
        if(t.matched == 1)
            return false;
        return true;
    }

    //check if the two tiles are the same
    public boolean matched(Tile t) {
        return true;
    }


}
