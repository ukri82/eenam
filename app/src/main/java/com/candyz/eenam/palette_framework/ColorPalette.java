package com.candyz.eenam.palette_framework;

import android.app.Fragment;
import android.app.FragmentManager;

import com.candyz.eenam.VideoFragment;
import com.candyz.eenam.json.VideoQuery;
import com.candyz.eenam.video_list.VideoList;

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


    public void initialize(int aVideoListId_in, VideoFragment aFragment_in, String aQueryName_in)
    {
        myVideoList = (VideoList)getActivity().getFragmentManager().findFragmentById(aVideoListId_in);
        myVideoList.initialize(aFragment_in, new VideoQuery(aQueryName_in));
        myVideoList.getView().setBackgroundColor(getActivity().getResources().getColor(myBackgroundColor));
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
