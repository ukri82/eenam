package com.candyz.eenam.palette_concrete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.VideoFragment;
import com.candyz.eenam.palette_framework.ColorPalette;

/**
 * A simple {@link Fragment} subclass.
 */
public class MomsNostalgiaPalette extends ColorPalette
{


    public MomsNostalgiaPalette()
    {
        super.myIconResourceId = R.drawable.button_action_fg_mothers;
        super.myBackgroundColor = R.color.violet;
        super.myName = this.getClass().getSimpleName();
        super.myDescription = "Mom's Nostalgia";
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        VideoFragment aVideoFragment = (VideoFragment) getActivity().getFragmentManager().findFragmentById(R.id.video_fragment_container);
        super.initialize(R.id.fragment_video_list_moms_nostalgia, aVideoFragment, "get_moms_nostalgia_songs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moms_nostalgia_palette, container, false);
    }


}
