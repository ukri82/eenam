package com.candyz.eenam.palette_concrete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.ColorPalette;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedPalette extends ColorPalette
{


    public AcceptedPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_accepted;
        //super.myBackgroundColor = R.color.yellow;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "Widely Accepted";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_accepted, "get_accepted_songs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_palette, container, false);
    }


}
