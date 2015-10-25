package com.candyz.eenam.palette_framework;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.candyz.eenam.R;
import com.candyz.eenam.video_list.VideoListListener;

/**
 * Created by u on 11.10.2015.
 */
public class PaletteStack
{
    String myCurrentPaletteName = "";
    VideoListListener myVideoListListener;

    AppCompatActivity myParentActivity;

    public PaletteStack(VideoListListener aVideoListListener_in, AppCompatActivity anActivity_in)
    {
        myVideoListListener = aVideoListListener_in;
        myParentActivity = anActivity_in;
        slide(PaletteFrameWork.getFactory().getDefaultPaletteName());
    }

    public void slideDynamic(String aPaletteName_in, String aParameter_in, String aParamDesc_in)
    {
        DynamicPalette aPalette = (DynamicPalette)PaletteFrameWork.getFactory().getPalette(aPaletteName_in);
        if(aPalette.isShown() == false)
        {
            aPalette.initialize(myVideoListListener);
            aPalette.setSearchParams(aParameter_in, aParamDesc_in, false);
            slide(aPalette);
        }
        else
        {
            aPalette.setSearchParams(aParameter_in, aParamDesc_in, true);
            myParentActivity.getSupportActionBar().setTitle("eenam" + " - " + aPalette.getDescription());
        }
    }

    public void slide(String aPaletteName_in)
    {
        ColorPalette aPalette = PaletteFrameWork.getFactory().getPalette(aPaletteName_in);

        aPalette.initialize(myVideoListListener);
        slide(aPalette);
    }

    private void slide(ColorPalette aPalette_in)
    {
        ColorPalette aCurrentPalette = PaletteFrameWork.getFactory().getPalette(myCurrentPaletteName);
        if(aCurrentPalette != null)
            aCurrentPalette.onHide();

        FragmentTransaction transaction = myParentActivity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.palette_holder, aPalette_in);
        transaction.commit();

        myParentActivity.getSupportActionBar().setTitle("eenam" + " - " + aPalette_in.getDescription());
        myCurrentPaletteName = aPalette_in.getName();
    }

}
