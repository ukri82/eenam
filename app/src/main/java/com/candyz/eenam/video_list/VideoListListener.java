package com.candyz.eenam.video_list;

import com.candyz.eenam.model.VideoItem;

/**
 * Created by u on 26.09.2015.
 */

public interface VideoListListener
{
    public void onVideoItemSelected(VideoItem aVideoItem_in);
    public void onPlayListSelected(VideoItem aVideoItem_in);
    public void onRaagamSelected(String aRaagamId_in, String aRaagamName_in);
    public void onMovieSelected(String aMovieId_in, String aMovieName_in);

    public void onHide();
    public void onShow();
}