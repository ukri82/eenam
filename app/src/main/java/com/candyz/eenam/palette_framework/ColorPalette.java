package com.candyz.eenam.palette_framework;

import android.app.Fragment;
import android.app.FragmentManager;

import com.candyz.eenam.model.VideoQuery;

import com.candyz.eenam.video_list.VideoList;
import com.candyz.eenam.video_list.VideoListListener;

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


    public void initializeSuper(int aVideoListId_in, String aQueryName_in)
    {
        myVideoList = (VideoList)getActivity().getFragmentManager().findFragmentById(aVideoListId_in);
        myVideoList.initialize(myListener, new VideoQuery(aQueryName_in));
        myVideoList.getView().setBackgroundColor(getActivity().getResources().getColor(myBackgroundColor));

    }

    public void initialize(VideoListListener aVideoListListener_in)
    {
        myListener = aVideoListListener_in;
    }

    @Override
    public void onDestroyView()
    {
        try
        {
            FragmentManager fm = getFragmentManager();
            if (myVideoList != null)
            {
                fm.beginTransaction().remove(myVideoList).commit();
            }
       }
        catch (IllegalStateException e)
        {

        }
        super.onDestroyView();
    }

}
