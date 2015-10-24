package com.candyz.eenam.palette_concrete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.ColorPalette;


public class DaysSpecialPalette extends ColorPalette
{

    public DaysSpecialPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_filter_cross;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "Today's special";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_days_special, "get_songs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days_special_palette, container, false);
    }


}
