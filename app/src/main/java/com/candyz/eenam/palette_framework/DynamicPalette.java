package com.candyz.eenam.palette_framework;

import com.candyz.eenam.R;


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

    public void setSearchParams(String aSearchParams_in, String aSearchDescription_in, boolean anUpdate_in)
    {
        mySearchValue = aSearchParams_in;
        int anIndex = super.myDescription.indexOf(" - ");
        if(anIndex >= 0)
            super.myDescription = super.myDescription.substring(0, anIndex);

        super.myDescription = super.myDescription + " - " + aSearchDescription_in;
        super.myParams.put(mySearchQuery, aSearchParams_in);
        if(anUpdate_in)
            super.update();
    }

    public void update(String aSearchParams_in, String aSearchDescription_in)
    {
        setSearchParams(aSearchParams_in, aSearchDescription_in, true);
    }

}
