package com.candyz.eenam.palette_concrete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.DynamicPalette;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPalette extends DynamicPalette
{
    public SearchPalette()
    {
        super.myDescription = "found";
        super.mySearchQuery = "Input";
        super.myName = this.getClass().getSimpleName();
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
