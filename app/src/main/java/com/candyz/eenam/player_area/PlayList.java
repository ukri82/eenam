package com.candyz.eenam.player_area;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.candyz.eenam.OverTheTopLayer;
import com.candyz.eenam.R;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.VideoItem;
import com.mobeta.android.dslv.DragSortListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by u on 27.09.2015.
 */
public class PlayList implements VideoFragmentListener, PlayListListener, PlayListControlListener
{
    AppCompatActivity myParentActivity;
    VideoFragment myPlayer;
    PlayListViewAdapter myAdapter;
    PlayListView myPlayListView;

    private String myPlayListSaveName = "";
    private String myIdentity;

    private String myCurrentPlayListId;


    private PlayListControl myPlayListControl;

    public void create(AppCompatActivity parentActivity_in, VideoFragment aPlayer_in)
    {
        myParentActivity = parentActivity_in;
        myPlayer = aPlayer_in;
        myPlayer.attachVideoListener(this);

        myPlayListView = (PlayListView) myParentActivity.findViewById(R.id.play_list_view);
        myAdapter = new PlayListViewAdapter(myParentActivity, R.layout.play_list_item, this);
        myPlayListView.setAdapter(myAdapter);
        myPlayListView.setTransitionEffect(JazzyHelper.TWIRL);


        myPlayListView.setDropListener(new DragSortListView.DropListener()
        {
            @Override
            public void drop(int from, int to)
            {
                myAdapter.move(from, to);
            }
        });

        initializeIdentity();

        myPlayListControl = (PlayListControl) myParentActivity.getFragmentManager().findFragmentById(R.id.play_list_control_view);
        myPlayListControl.initialize(this, myIdentity);


        myPlayListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                changeVideo(myAdapter.getYoutubeId(position), "");
            }
        });

    }

    private void initializeIdentity()
    {
        String androidId = Settings.Secure.getString(myPlayListView.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32) | androidId.hashCode());
        myIdentity = deviceUuid.toString();
    }

    public void addVideoItem(VideoItem aVideoItem_in)
    {
        myAdapter.add(aVideoItem_in);
        triggerAnimation(myParentActivity, (ViewGroup) myParentActivity.findViewById(android.R.id.content));

        if(myAdapter.getNumVideos() == 1)
        {
            if(myPlayer != null)
            {
                changeVideo(aVideoItem_in.getUTubeID(), aVideoItem_in.getStart());
            }
        }
    }

    public void triggerAnimation(AppCompatActivity activity, ViewGroup ottParent)
    {

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.card_view_snap_shot);

        float aScalingFactor = ottParent.getWidth() / bitmap.getWidth();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 1), (int) (bitmap.getHeight() * 1), false);

        final OverTheTopLayer layer = new OverTheTopLayer();

        int startingPoints[] = new int[2];

        startingPoints[0] = 400;
        startingPoints[1] = 600;

        FrameLayout ottLayout = layer.with(activity)
                .scale(1)
                .attachTo(ottParent)
                .setBitmap(scaledBitmap, startingPoints)
                .create();

        ObjectAnimator animY = ObjectAnimator.ofFloat(layer.getImageView(), "translationY", -startingPoints[1]);
        ObjectAnimator rotX = ObjectAnimator.ofFloat(layer.getImageView(), "rotationX", 0f, 360f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(layer.getImageView(), "scaleY", 0.2f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(layer.getImageView(), "scaleX", 0.2f);
        scaleX.addListener(new AnimatorListenerAdapter()
        {
            public void onAnimationEnd(Animator animation)
            {
                layer.destroy();
            }
        });

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animY, rotX, scaleX, scaleY);
        animSetXY.setDuration(1000);
        animSetXY.start();
    }

    public void playVideoItem(VideoItem aVideoItem_in)
    {
        if(myPlayer != null)
        {
            changeVideo(aVideoItem_in.getUTubeID(), aVideoItem_in.getStart());
        }
    }

    @Override
    public void onVideoFinished(String aYoutubeId_in)
    {
        String aNextVideoId = myAdapter.getNext(aYoutubeId_in);
        if(myPlayer != null && aNextVideoId != null)
        {
            changeVideo(aNextVideoId, "");
        }
    }

    @Override
    public void onItemSelected(String aYoutubeId_in)
    {
        changeVideo(aYoutubeId_in, "");
    }

    private void changeVideo(String aYoutubeId_in, String aTitle_in)
    {
        myPlayer.setVideoId(aYoutubeId_in);
        if(aTitle_in == "")
        {
            aTitle_in = myAdapter.getYoutubeTitle(aYoutubeId_in);
        }
        myPlayListControl.setCurrentSong(aTitle_in);

        new TaskAddPlayRecord(myIdentity, myCurrentPlayListId, myAdapter.getSongId(aYoutubeId_in)).execute();
    }


    @Override
    public void onPrevious()
    {
        onItemSelected(myAdapter.getPrevious(myPlayer.getVideoId()));
    }

    @Override
    public void onNext()
    {
        onItemSelected(myAdapter.getNext(myPlayer.getVideoId()));
    }

    @Override
    public void onPause()
    {
        myPlayer.pauseVideo();
    }

    @Override
    public void onResume()
    {
        myPlayer.resumeVideo();
    }

    private void getPlayListNameFromUserAndSave()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(myPlayListView.getContext());
        builder.setTitle("Please enter name of playlist");

        final EditText input = new EditText(myPlayListView.getContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                myPlayListSaveName = input.getText().toString();
                if (myPlayListSaveName != "")
                {
                    new TaskSavePlayList(new TaskSavePlayList.PlayListCreationListener()
                    {
                        @Override
                        public void onPlayListCreated(String aName_in)
                        {
                            Toast.makeText(myPlayListView.getContext(), "Playlist " + aName_in + " saved", Toast.LENGTH_SHORT);
                        }
                    },myIdentity, myPlayListSaveName, myAdapter.getAllSongs()).execute();

                    myPlayListSaveName = "";
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onSavePlayList()
    {
        getPlayListNameFromUserAndSave();
    }

    @Override
    public void onPlayListSelected(String aPlayListId_in)
    {
        myCurrentPlayListId = aPlayListId_in;
        new TaskGetAllSongsOfPlayList(new TaskGetAllSongsOfPlayList.GetAllPlayListSongsQueryListener()
        {
            @Override
            public void onPlayListsAvailable(ArrayList<VideoItem> aPlayList_in)
            {
                myAdapter.set(aPlayList_in);
                changeVideo(myAdapter.getYoutubeId(0), "");
            }
        }, myCurrentPlayListId).execute();
    }

}
