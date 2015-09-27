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
public class ContraversialPalette extends ColorPalette
{


    public ContraversialPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_contra;
        super.myBackgroundColor = R.color.orange;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "Widely Discussed";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_contraversial, "get_contraversial_songs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contraversial_palette, container, false);
    }


}
