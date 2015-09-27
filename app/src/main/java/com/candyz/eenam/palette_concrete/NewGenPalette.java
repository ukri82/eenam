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
public class NewGenPalette extends ColorPalette
{


    public NewGenPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_new_gen;
        super.myBackgroundColor = R.color.red;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "New Gen";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_gen_palette, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_new_gen, "get_new_gen_songs");
    }
}
