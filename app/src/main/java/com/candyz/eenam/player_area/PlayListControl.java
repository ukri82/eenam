package com.candyz.eenam.player_area;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.candyz.eenam.R;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.PlayListItem;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.model.VideoQuery;
import com.candyz.eenam.model.VideoResultParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListControl extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    PlayListControlListener myListener;
    View myPrev;
    View myNext;
    View myPlay;
    TextView myCurrentSong;
    View mySave;
    Spinner myLoad;
    boolean myCurrentStatePlaying = false;
    String myIdentity;

    ArrayList<PlayListItem> myPlayLists = new ArrayList<>();


    public PlayListControl()
    {
        // Required empty public constructor
    }

    void initialize(PlayListControlListener aListener_in, String theIdentity_in)
    {
        myListener = aListener_in;
        myIdentity = theIdentity_in;

        loadPlayLists();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list_control, container, false);
    }

    public void setCurrentSong(String aSongTitle_in)
    {
        myCurrentSong.setText(aSongTitle_in);
    }

    private void loadPlayLists()
    {
        new TaskGetAllPlayLists(new TaskGetAllPlayLists.GetAllPlayListsQueryListener()
        {
            @Override
            public void onPlayListsAvailable(ArrayList<PlayListItem> aPlayList_in)
            {
                myPlayLists = aPlayList_in;
                Collections.sort(myPlayLists);
                ArrayAdapter<PlayListItem> adapter = new ArrayAdapter<PlayListItem>(myLoad.getContext(), android.R.layout.simple_spinner_item, myPlayLists);
                myLoad.setAdapter(adapter);

            }
        },myIdentity).execute();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        myPrev = view.findViewById(R.id.previous_video);
        myPlay = view.findViewById(R.id.pause_resume_video);
        myNext = view.findViewById(R.id.next_video);
        mySave = view.findViewById(R.id.save_play_list);
        myLoad = (Spinner)view.findViewById(R.id.load_play_list);
        myCurrentSong = (TextView)view.findViewById(R.id.marquee_text_view);
        Typeface myTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/AnjaliOldLipi.ttf");
        myCurrentSong.setTypeface(myTypeface);


        myPrev.setOnClickListener(this);
        myPlay.setOnClickListener(this);
        myNext.setOnClickListener(this);
        mySave.setOnClickListener(this);
        myLoad.setOnItemSelectedListener(this);
    }

    Animation myTickerAnimation;
    public  void createTickerAnimation()
    {
        if(myTickerAnimation == null)
        {
            Context context = myCurrentSong.getContext(); // gets the context of the view

            // measures the unconstrained size of the view
            // before it is drawn in the layout
            myCurrentSong.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            // takes the unconstrained width of the view
            float width = myCurrentSong.getMeasuredWidth();
            float height = myCurrentSong.getMeasuredHeight();

            // gets the screen width
            float screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();

            myCurrentSong.setLayoutParams(new RelativeLayout.LayoutParams((int) width, (int) height));

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
            myTickerAnimation = new TranslateAnimation(screenWidth, toXDelta, 0, 0);
            myTickerAnimation.setDuration(25000);
            myTickerAnimation.setRepeatMode(Animation.RESTART);
            myTickerAnimation.setRepeatCount(Animation.INFINITE);
        }
    }

    public void setTicker(String text)
    {
        if (text != "")
        {
            //createTickerAnimation();

            myCurrentSong.setText(text);

            //myCurrentSong.setAnimation(myTickerAnimation);
        }
    }


    @Override
    public void onClick(View v)
    {
        if (myListener == null)
            return;

        if (v == myPrev)
        {
            myListener.onPrevious();
        }
        if (v == myPlay)
        {
            if (myCurrentStatePlaying)
            {
                myListener.onPause();
                myCurrentStatePlaying = false;
                myPlay.setBackgroundResource(android.R.drawable.ic_media_play);
            } else
            {
                myListener.onResume();
                myCurrentStatePlaying = true;
                myPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
            }
        }
        if (v == myNext)
        {
            myListener.onNext();
        }

        if (v == mySave)
        {
            myListener.onSavePlayList();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        myListener.onPlayListSelected(myPlayLists.get(position).getId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
