package com.candyz.eenam.palette_framework;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.palette_framework.ColorPalette;


public class DynamicPalette extends ColorPalette
{
    protected String mySearchQuery;
    protected String mySearchValue;

    public DynamicPalette()
    {
        super.myBackgroundColor = R.color.white;
        super.myDescription = "Dynamic";
        super.myDislpayAlways = false;
    }

    public void setSearchParams(String aSearchParams_in, boolean anUpdate_in)
    {
        mySearchValue = aSearchParams_in;
        super.myParams.put(mySearchQuery, aSearchParams_in);
        if(anUpdate_in)
            super.update();
    }

    public void update(String anInputText_in)
    {
        setSearchParams(anInputText_in, true);
    }

}
