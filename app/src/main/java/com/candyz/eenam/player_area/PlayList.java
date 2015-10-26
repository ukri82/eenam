package com.candyz.eenam.player_area;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.candyz.eenam.OverTheTopLayer;
import com.candyz.eenam.R;
import com.candyz.eenam.model.DeviceIdentity;
import com.candyz.eenam.model.VideoItem;
import com.mobeta.android.dslv.DragSortListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by u on 27.09.2015.
 */
public class PlayList implements VideoFragmentListener, PlayListAdapterListener, PlayListControlListener
{
    AppCompatActivity myParentActivity;
    VideoFragment myPlayer;
    PlayListViewAdapter myAdapter;
    PlayListView myPlayListView;

    private String myPlayListSaveName = "";

    private String myCurrentPlayListId;
    private String myCurrentPlayListName;

    private PlayListListener myListener;


    private PlayListControl myPlayListControl;

    public void create(AppCompatActivity parentActivity_in, VideoFragment aPlayer_in, PlayListListener aListener_in)
    {
        myListener = aListener_in;
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

        myPlayListControl = (PlayListControl) myParentActivity.getFragmentManager().findFragmentById(R.id.play_list_control_view);
        myPlayListControl.initialize(this, DeviceIdentity.get());


        myPlayListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                changeVideo(myAdapter.getYoutubeId(position), "", "");
            }
        });

    }

    public void addVideoItems(List<VideoItem> aVideoList_in)
    {
        for(VideoItem anItem : aVideoList_in)
        {
            addVideoItem(anItem);
        }
    }

    public void addVideoItem(VideoItem aVideoItem_in)
    {
        myAdapter.add(aVideoItem_in);
        triggerAnimation(myParentActivity, (ViewGroup) myParentActivity.findViewById(android.R.id.content));

        if(myAdapter.getNumVideos() == 1)
        {
            if(myPlayer != null)
            {
                changeVideo(aVideoItem_in.getUTubeID(), aVideoItem_in.getStart(), aVideoItem_in.getId());
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
            changeVideo(aVideoItem_in.getUTubeID(), aVideoItem_in.getStart(), aVideoItem_in.getId());
        }
    }

    @Override
    public void onVideoFinished(String aYoutubeId_in)
    {
        String aNextVideoId = myAdapter.getNext(aYoutubeId_in);
        if(myPlayer != null && aNextVideoId != null)
        {
            changeVideo(aNextVideoId, "", "");
        }
    }

    public int getHeight()
    {
        return myPlayListView.getHeight();
    }

    @Override
    public void onItemSelected(String aYoutubeId_in)
    {
        changeVideo(aYoutubeId_in, "", "");
    }

    @Override
    public void onItemDeleted(String aYoutubeId_in)
    {
        myAdapter.remove(aYoutubeId_in);
    }

    private void changeVideo(String aYoutubeId_in, String aTitle_in, String aVideoId_in)
    {
        myPlayer.setVideoId(aYoutubeId_in);
        if(aTitle_in == "")
        {
            aTitle_in = myAdapter.getYoutubeTitle(aYoutubeId_in);
        }
        if(aVideoId_in == "")
        {
            aVideoId_in =  myAdapter.getSongId(aYoutubeId_in);
        }
        myPlayListControl.setCurrentSong(aTitle_in);

        new TaskAddPlayRecord(DeviceIdentity.get(), myCurrentPlayListId, aVideoId_in).execute();
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
                try
                {
                    myPlayListSaveName = URLEncoder.encode(input.getText().toString(), "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                if (myPlayListSaveName != "")
                {
                    new TaskSavePlayList(new TaskSavePlayList.PlayListCreationListener()
                    {
                        @Override
                        public void onPlayListCreated(String aName_in)
                        {
                            Toast.makeText(myParentActivity, "Playlist " + aName_in + " saved", Toast.LENGTH_SHORT);
                        }
                    },DeviceIdentity.get(), myPlayListSaveName, myAdapter.getAllSongs()).execute();

                    myPlayListSaveName = "";
                    myPlayListControl.loadPlayLists();
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
    public void onDeletePlayList()
    {
        new TaskDeletePlayList(new TaskDeletePlayList.PlayListDeletionListener()
        {
            @Override
            public void onPlayListDeleted(String aName_in)
            {
                Toast.makeText(myParentActivity, "Playlist " + aName_in + " deleted", Toast.LENGTH_SHORT);
                myPlayer.pauseVideo();
            }
        },DeviceIdentity.get(), myCurrentPlayListName).execute();
    }

    @Override
    public void clearPlayList()
    {
        myAdapter.removeAll();
        myPlayer.pauseVideo();
    }

    @Override
    public void addAllToPlayList()
    {
        if(myListener != null)
            myListener.onAddAll();
    }

    @Override
    public void onPlayListSelected(String aPlayListId_in, String aPlayListName_in)
    {
        myCurrentPlayListId = aPlayListId_in;
        myCurrentPlayListName = aPlayListName_in;
        new TaskGetAllSongsOfPlayList(new TaskGetAllSongsOfPlayList.GetAllPlayListSongsQueryListener()
        {
            @Override
            public void onPlayListsAvailable(ArrayList<VideoItem> aPlayList_in)
            {
                myAdapter.set(aPlayList_in);
                changeVideo(myAdapter.getYoutubeId(0), "", "");
            }
        }, myCurrentPlayListId).execute();
    }

}
