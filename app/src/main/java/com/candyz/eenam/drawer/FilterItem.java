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
    public String myClassName;

    FilterItem(String aName_in, int aResId_in, int aColor_in, String aClassName_in)
    {
        myName = aName_in;
        myResId = aResId_in;
        myBGColor = aColor_in;
        myClassName = aClassName_in;
    }

    public String getTag()
    {
        //return myClassName + "#" + Long.toString(myBGColor & 0xFFFFFFFFL);
        return myClassName + "#" + Integer.toString(myBGColor);
    }
}
