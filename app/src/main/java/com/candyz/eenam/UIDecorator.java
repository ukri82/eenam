package com.candyz.eenam;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.candyz.eenam.drawer.DrawerEntries;
import com.candyz.eenam.drawer.FilterItem;
import com.candyz.eenam.drawer.FragmentDrawer;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.palette_framework.PaletteStack;
import com.candyz.eenam.player_area.PlayList;
import com.candyz.eenam.player_area.PlayListView;
import com.candyz.eenam.player_area.VideoFragment;
import com.candyz.eenam.video_list.VideoListListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.util.List;

/**
 * Created by u on 20.09.2015.
 */
public class UIDecorator implements FragmentDrawer.DrawerEventsListener, View.OnClickListener, VideoListListener
{
    private FloatingActionButton myFAB;
    private FloatingActionMenu myFABMenu;

    private Toolbar myToolbar;

    AppCompatActivity myParentActivity;

    PaletteStack myPaletteStack;

    SlidingUpPanelLayout myPlayerPanel;

    VideoFragment myPlayer;

    View myDummyView;

    PlayList myPlayList;

    private float myAnchorHeight = 0.4f;

    FragmentDrawer myDrawerFragment;


    public void create(AppCompatActivity parentActivity_in)
    {
        myParentActivity = parentActivity_in;

        myPlayer = (VideoFragment) parentActivity_in.getFragmentManager().findFragmentById(R.id.video_fragment_container);

        setUpActionBar();

        setupDrawer();

        setupFAB();

        positionPlayer();

        myPlayList = new PlayList();
        myPlayList.create(myParentActivity, myPlayer);

        myPaletteStack = new PaletteStack(this, myParentActivity);
    }


    private void positionPlayer()
    {
        myPlayerPanel = (SlidingUpPanelLayout) myParentActivity.findViewById(R.id.palyer_sliding_panel);
        myPlayerPanel.setAnchorPoint(myAnchorHeight);
        myPlayerPanel.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        myDummyView = myParentActivity.findViewById(R.id.dummy_place_holder_behind_anchored_slidepanel);
        myPlayerPanel.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener()
        {
            @Override
            public void onPanelSlide(View panel, float slideOffset)
            {
                resizeScrollView(slideOffset);
            }

            @Override
            public void onPanelExpanded(View panel)
            {
                resizeScrollView(1.0f);
            }

            @Override
            public void onPanelCollapsed(View panel)
            {
                resizeScrollView(0.0f);
            }

            @Override
            public void onPanelAnchored(View panel)
            {
                resizeScrollView(myAnchorHeight);
            }

            @Override
            public void onPanelHidden(View view)
            {

            }

            private void resizeScrollView(float slideOffset)
            {
                if (slideOffset < myAnchorHeight)
                {
                    final int scrollViewHeight = (int) (myPlayerPanel.getLayoutParams().height * slideOffset);
                    myDummyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, scrollViewHeight));
                }
            }
        });
    }

    private void setupDrawer()
    {
        myDrawerFragment = (FragmentDrawer)myParentActivity.getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_id);
        myDrawerFragment.setUp(R.id.fragment_navigation_drawer_id, (DrawerLayout) myParentActivity.findViewById(R.id.drawer_layout), myToolbar, this);
    }

    private void toggleTranslateFAB(float slideOffset)
    {
        if (myFABMenu != null)
        {
            if (myFABMenu.isOpen()) {
                myFABMenu.close(true);
            }
            if(myFAB != null)
            {
                myFAB.setTranslationX(slideOffset * 200);
            }
        }
    }

    private void setUpActionBar()
    {

        myToolbar = (Toolbar) myParentActivity.findViewById(R.id.app_bar);

        myParentActivity.setSupportActionBar(myToolbar);
        myParentActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        myParentActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        myParentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFAB()
    {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(myParentActivity);
        iconFAB.setImageResource(R.drawable.button_action_fg_filter);


        //set the appropriate background for the main floating action button along with its icon
        myFAB = new FloatingActionButton.Builder(myParentActivity)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.button_action_bg_red)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(myParentActivity);
        itemBuilder.setBackgroundDrawable(myParentActivity.getResources().getDrawable(R.drawable.button_action_bg_red));

        FloatingActionMenu.Builder aBuilder = new FloatingActionMenu.Builder(myParentActivity);

        List<FilterItem> aFilterList = DrawerEntries.getFilterList(myParentActivity);
        for (FilterItem aFilter : aFilterList)
        {
            ImageView iconView = new ImageView(myParentActivity);
            iconView.setImageResource(aFilter.myResId);

            SubActionButton buttonFilter = itemBuilder.setContentView(iconView).build();
            buttonFilter.setTag(aFilter.myName);
            buttonFilter.setOnClickListener(this);

            aBuilder.addSubActionView(buttonFilter);
        }
        aBuilder.attachTo(myFAB);
        aBuilder.setRadius(myParentActivity.getResources().getDimensionPixelSize(R.dimen.action_menu_radius_more));
        myFABMenu = aBuilder.build();

    }

    public void displaySearch(String aSearchQuery_in)
    {
        myPaletteStack.slideDynamic("SearchPalette", aSearchQuery_in, aSearchQuery_in);
    }

    @Override
    public void onSlide(float slideOffset)
    {
        toggleTranslateFAB(slideOffset);
    }

    @Override
    public void onClick(String aTag_in)
    {
        myPaletteStack.slide(aTag_in);
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getTag().toString());
    }

    @Override
    public void onVideoItemSelected(VideoItem aVideoItem_in)
    {
        if(myPlayList != null)
        {
            myPlayList.playVideoItem(aVideoItem_in);
        }
    }



    @Override
    public void onPlayListSelected(VideoItem aVideoItem_in)
    {
        if(myPlayList != null)
        {
            myPlayList.addVideoItem(aVideoItem_in);
        }
    }

    @Override
    public void onRaagamSelected(String aRaagamId_in, String aRaagamName_in)
    {
        myPaletteStack.slideDynamic("RaagamPalette", aRaagamId_in, aRaagamName_in);
    }

    @Override
    public void onMovieSelected(String aMovieId_in, String aMovieName_in)
    {
        myPaletteStack.slideDynamic("MoviePalette", aMovieId_in, aMovieName_in);
    }

    @Override
    public void onHide()
    {
        myPlayerPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onShow()
    {
        myPlayerPanel.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
    }



}
