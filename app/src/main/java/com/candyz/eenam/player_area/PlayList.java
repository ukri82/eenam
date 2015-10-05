package com.candyz.eenam.player_area;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.candyz.eenam.R;
import com.candyz.eenam.model.VideoItem;
import com.mobeta.android.dslv.DragSortListView;


/**
 * Created by u on 27.09.2015.
 */
public class PlayList implements VideoFragmentListener, PlayListListener
{
    AppCompatActivity myParentActivity;
    VideoFragment myPlayer;
    PlayListViewAdapter myAdapter;
    TextView myPlayerAreaTicker;
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
        myAdapter = new PlayListViewAdapter(myParentActivity, R.layout.play_list_item, this);
        myPlayListView.setAdapter(myAdapter);
        myPlayListView.setTransitionEffect(JazzyHelper.TWIRL);
        myPlayerAreaTicker = (TextView) parentActivity_in.findViewById(R.id.player_area_ticker);
        Typeface myTypeface = Typeface.createFromAsset(myParentActivity.getAssets(), "fonts/AnjaliOldLipi.ttf");
        myPlayerAreaTicker.setTypeface(myTypeface);
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
                Log.i("", "inonItemClick" );
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
                changeVideo(aVideoItem_in.getUTubeID());
            }
        }
    }

    public void playVideoItem(VideoItem aVideoItem_in)
    {
        if(myPlayer != null)
        {
            changeVideo(aVideoItem_in.getUTubeID());
        }
    }

    @Override
    public void onVideoFinished(String aYoutubeId_in)
    {
        String aNextVideoId = myAdapter.getNext(aYoutubeId_in);
        if(myPlayer != null)
        {
            changeVideo(aNextVideoId);
        }
    }

    @Override
    public void onItemSelected(String aYoutubeId_in)
    {
        changeVideo(aYoutubeId_in);
    }

    private void changeVideo(String aYoutubeId_in)
    {
        myPlayer.setVideoId(aYoutubeId_in);
        setTicker(myAdapter.getYoutubeTitle(aYoutubeId_in));
    }

    public  void setTicker(String text)
    {
        if (text != "")
        {
            myPlayerAreaTicker.setText(text);

            Context context = myPlayerAreaTicker.getContext(); // gets the context of the view

            // measures the unconstrained size of the view
            // before it is drawn in the layout
            myPlayerAreaTicker.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            // takes the unconstrained width of the view
            float width = myPlayerAreaTicker.getMeasuredWidth();
            float height = myPlayerAreaTicker.getMeasuredHeight();

            // gets the screen width
            float screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();

            myPlayerAreaTicker.setLayoutParams(new LinearLayout.LayoutParams((int) width, (int) height, 1f));

            // performs the calculation
            float toXDelta = width - (screenWidth - 0);

            // sets toXDelta to -300 if the text width is smaller that the
            // screen size
            if (toXDelta < 0)
            {
                toXDelta = 0 - screenWidth;// -300;
            } else
            {
                toXDelta = 0 - screenWidth - toXDelta;// -300 - toXDelta;
            }
            // Animation parameters
            Animation mAnimation = new TranslateAnimation(screenWidth, toXDelta, 0, 0);
            mAnimation.setDuration(15000);
            mAnimation.setRepeatMode(Animation.RESTART);
            mAnimation.setRepeatCount(Animation.INFINITE);
            myPlayerAreaTicker.setAnimation(mAnimation);
        }
    }
}
