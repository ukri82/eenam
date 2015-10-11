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
public class MoviePalette extends DynamicPalette
{
    public MoviePalette()
    {
        super.myDescription = "movie";
        super.mySearchQuery = "MovieId";
        super.myName = this.getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.initializeSuper(R.id.fragment_video_list_movie, "get_songs_of_movie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_palette, container, false);
    }


}
