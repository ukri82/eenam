package com.candyz.eenam.video_list;

import android.view.View;

/**
 * Created by u on 10.06.2015.
 */
public interface VideoItemsListener
{
    public void onVideoItemSelected(View v);
    public void onPlayListSelected(View v);
    public void onRaagamSelected(String aRaagamNameEnglish_in, String aRaagamName_in);
    public void onMovieSelected(String aMovieId_in, String aMovieName_in);
    public void onVideoRated(View v, int aRating_in);
}

