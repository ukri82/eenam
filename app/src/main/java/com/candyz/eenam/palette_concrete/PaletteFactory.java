package com.candyz.eenam.palette_concrete;

import com.candyz.eenam.palette_concrete.DaysSpecialPalette;
import com.candyz.eenam.palette_framework.ColorPalette;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 24.09.2015.
 */
public class PaletteFactory
{
    static List<ColorPalette> myPaletteList;

    public PaletteFactory()
    {
        if(myPaletteList == null)
        {
            myPaletteList = new ArrayList<>();

            myPaletteList.add(new DaysSpecialPalette());
            myPaletteList.add(new MomsNostalgiaPalette());
            myPaletteList.add(new NostalgicHitsPalette());
            myPaletteList.add(new NostalgicEverGreenPalette());
            myPaletteList.add(new ClassicalPalette());
            myPaletteList.add(new AcceptedPalette());
            myPaletteList.add(new ContraversialPalette());
            myPaletteList.add(new NewGenPalette());

            SearchPalette aPalette = new SearchPalette();
            aPalette.setDisplayAlways(false);
            myPaletteList.add(aPalette);
        }
    }

    public static List<ColorPalette> getPaletteList()
    {
        return myPaletteList;
    }

    public String getDefaultPaletteName()
    {
        return myPaletteList.get(0).getName();
    }

    public ColorPalette getPalette(String aName_in)
    {
        for (ColorPalette aPalette : myPaletteList)
        {
            if (aPalette.getName() == aName_in)
            {
                return aPalette;
            }
        }
        return null;
    }
}
