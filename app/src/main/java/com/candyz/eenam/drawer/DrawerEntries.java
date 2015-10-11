package com.candyz.eenam.drawer;

import android.app.Activity;

import com.candyz.eenam.palette_framework.ColorPalette;
import com.candyz.eenam.palette_concrete.ConcretePaletteFactory;
import com.candyz.eenam.palette_framework.PaletteFrameWork;

import java.util.List;

/**
 * Created by u on 11.06.2015.
 */
public class DrawerEntries
{

    public static List<FilterItem> getFilterList(Activity anActivity_in)
    {
        List<FilterItem> aFilterList = new java.util.ArrayList<>();

        for (ColorPalette aPalette : PaletteFrameWork.getFactory().getPaletteList())
        {
            if(aPalette.shouldDisplayAlways())
            {
                aFilterList.add(new FilterItem(aPalette.getName(), aPalette.getIconResourceId(), aPalette.getBackgroundColor(), aPalette.getDescription()));
            }
        }
        return aFilterList;
    }

}
