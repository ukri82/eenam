package com.candyz.eenam.video_list;

import android.view.View;

/**
 * Created by u on 10.06.2015.
 */
public interface VideoItemsListener
{
    public void onVideoItemSelected(View v);
    public void onPlayListSelected(View v);
    public void onRaagamSelected(String aRaagamId_in);
    public void onMovieSelected(String aMovieId_in);
}

