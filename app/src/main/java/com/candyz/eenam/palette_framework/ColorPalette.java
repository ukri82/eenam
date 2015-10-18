package com.candyz.eenam.palette_framework;

import android.app.Fragment;
import android.app.FragmentManager;

import com.candyz.eenam.model.VideoQuery;

import com.candyz.eenam.video_list.VideoList;
import com.candyz.eenam.video_list.VideoListListener;

import java.util.HashMap;

/**
 * Created by u on 24.09.2015.
 */
public class ColorPalette extends Fragment
{
    protected int myBackgroundColor;
    protected VideoList myVideoList;
    protected String myName;
    protected int myIconResourceId;
    protected String myDescription;
    private String myQueryName;

    protected boolean myDislpayAlways = true;
    private boolean myCurrentlyDisplayed = false;

    protected HashMap<String, String> myParams = new HashMap<>();

    private VideoListListener myListener;

    public ColorPalette()
    {
        // Required empty public constructor
    }

    public int getBackgroundColor()
    {
        return myBackgroundColor;
    }

    public String getName()
    {
        return myName;
    }

    public int getIconResourceId()
    {
        return myIconResourceId;
    }

    public String getDescription()
    {
        return myDescription;
    }

    public void setDisplayAlways(boolean anAlways_in)
    {
        myDislpayAlways = anAlways_in;
    }

    public boolean shouldDisplayAlways()
    {
        return myDislpayAlways;
    }

    public boolean isShown()
    {
        return myCurrentlyDisplayed;
    }

    public void onHide()
    {
        myCurrentlyDisplayed = false;
    }
    public void onShow()
    {
        myCurrentlyDisplayed = true;
    }

    public void initializeSuper(int aVideoListId_in, String aQueryName_in)
    {
        myVideoList = (VideoList)getActivity().getFragmentManager().findFragmentById(aVideoListId_in);
        myQueryName = aQueryName_in;

        myVideoList.getView().setBackgroundColor(getActivity().getResources().getColor(myBackgroundColor));

        update();
    }

    public void initialize(VideoListListener aVideoListListener_in)
    {
        myListener = aVideoListListener_in;
    }

    public void update()
    {
        VideoQuery aQuery = new VideoQuery(myQueryName);
        if(myParams != null)
        {
            aQuery.setParams(myParams);
        }
        myVideoList.initialize(myListener, aQuery);
        onShow();
    }

    @Override
    public void onDestroyView()
    {
        /*try
        {
            FragmentManager fm = getFragmentManager();
            if (myVideoList != null)
            {
                fm.beginTransaction().remove(myVideoList).commit();
            }
            myCurrentlyDisplayed = false;
       }
        catch (IllegalStateException e)
        {

        }*/
        super.onDestroyView();
    }

}
