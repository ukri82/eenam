package com.candyz.eenam.player_area;

/**
 * Created by u on 14.10.2015.
 */
public interface PlayListControlListener
{
    public void onPrevious();
    public void onNext();
    public void onPause();
    public void onResume();

    public void onSavePlayList();
    public void onPlayListSelected(String aPlayListId_in);
}
