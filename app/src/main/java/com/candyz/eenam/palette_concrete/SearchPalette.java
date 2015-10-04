package com.candyz.eenam.palette_concrete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.ColorPalette;
import com.candyz.eenam.video_list.VideoListListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPalette extends ColorPalette
{
    public static String PaletteName;

    public SearchPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_accepted;
        super.myBackgroundColor = R.color.white;
        PaletteName = super.myName = this.getClass().getSimpleName();
        super.myDescription = "Search";
    }

    public void setSearchQuery(String anInputText_in, boolean anUpdate_in)
    {
        super.myParams.put("Input", anInputText_in);
        if(anUpdate_in)
            super.update();
    }

    public void update(String anInputText_in)
    {
        setSearchQuery(anInputText_in, true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_search, "get_search_results");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_palette, container, false);
    }


}
