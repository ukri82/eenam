package com.candyz.eenam.drawer;

import android.graphics.Color;

/**
 * Created by u on 11.06.2015.
 */
public class FilterItem
{
    public String myName;
    public int myResId;
    public int myBGColor;
    public String myDescription;

    FilterItem(String aName_in, int aResId_in, int aColor_in, String aDesciption_in)
    {
        myName = aName_in;
        myResId = aResId_in;
        myBGColor = aColor_in;
        myDescription = aDesciption_in;
    }

}
