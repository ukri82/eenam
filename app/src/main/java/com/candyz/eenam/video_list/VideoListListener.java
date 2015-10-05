package com.candyz.eenam.video_list;

import com.candyz.eenam.model.VideoItem;

/**
 * Created by u on 26.09.2015.
 */

public interface VideoListListener
{
    public void onVideoItemSelected(VideoItem aVideoItem_in);
    public void onPlayListSelected(VideoItem aVideoItem_in);

    public void onHide();
    public void onShow();
}