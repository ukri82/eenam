package com.candyz.eenam.player_area;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;

import com.candyz.eenam.R;
import com.candyz.eenam.model.VideoItem;
import com.mobeta.android.dslv.DragSortListView;


/**
 * Created by u on 27.09.2015.
 */
public class PlayList implements VideoFragmentListener
{
    AppCompatActivity myParentActivity;
    VideoFragment myPlayer;
    PlayListViewAdapter myAdapter;
    PlayListView myPlayListView;

    public void create(AppCompatActivity parentActivity_in, VideoFragment aPlayer_in)
    {
        myParentActivity = parentActivity_in;
        myPlayer = aPlayer_in;
        myPlayer.attachVideoListener(this);

        /*JazzyListView aPlayList = (JazzyListView) myParentActivity.findViewById(R.id.play_list_view);
        aPlayList.setTransitionEffect(new TwirlEffect());
        aPlayList.setAdapter(new PlayListViewAdapter(myParentActivity, R.layout.play_list_item));*/

        myPlayListView = (PlayListView) myParentActivity.findViewById(R.id.play_list_view);
        myAdapter = new PlayListViewAdapter(myParentActivity, R.layout.play_list_item);
        myPlayListView.setAdapter(myAdapter);
        myPlayListView.setTransitionEffect(JazzyHelper.TWIRL);
        //aPlayList.setTransitionEffect(JazzyHelper.WAVE);

        myPlayListView.setDropListener(new DragSortListView.DropListener()
        {
            @Override
            public void drop(int from, int to)
            {
                myAdapter.move(from, to);
            }
        });


        myPlayListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                myPlayer.setVideoId(myAdapter.getYoutubeId(position));
            }
        });
    }

    public void addVideoItem(VideoItem aVideoItem_in)
    {
        myAdapter.add(aVideoItem_in);
        if(myAdapter.getNumVideos() == 1)
        {
            if(myPlayer != null)
            {
                myPlayer.setVideoId(aVideoItem_in.getUTubeID());
            }
        }
    }

    @Override
    public void onVideoFinished(String aYoutubeId_in)
    {
        String aNextVideoId = myAdapter.getNext(aYoutubeId_in);
        if(myPlayer != null)
        {
            myPlayer.setVideoId(aNextVideoId);
        }
    }

}
