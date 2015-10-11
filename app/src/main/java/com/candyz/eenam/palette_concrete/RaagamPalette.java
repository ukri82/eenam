package com.candyz.eenam.palette_concrete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.ColorPalette;
import com.candyz.eenam.palette_framework.DynamicPalette;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaagamPalette extends DynamicPalette
{
    public RaagamPalette()
    {
        super.myDescription = "Raagam";
        super.mySearchQuery = "RaagamId";
        super.myName = this.getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_raagam, "get_songs_of_raagam");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raagam_palette, container, false);
    }


}
