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
public class NostalgicEverGreenPalette extends ColorPalette
{


    public NostalgicEverGreenPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_nostalgic;
        //super.myBackgroundColor = R.color.blue;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "Nostalgic Ever Green";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nostalgic_ever_green_palette, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_nostalgic_evergreen, "get_nostalgic_ever_green_songs");
    }
}
