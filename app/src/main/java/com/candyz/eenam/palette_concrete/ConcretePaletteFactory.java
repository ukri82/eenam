package com.candyz.eenam.palette_concrete;

import com.candyz.eenam.palette_concrete.DaysSpecialPalette;
import com.candyz.eenam.palette_framework.ColorPalette;
import com.candyz.eenam.palette_framework.PaletteFactory;
import com.candyz.eenam.palette_framework.PaletteFrameWork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 24.09.2015.
 */
public class ConcretePaletteFactory implements PaletteFactory
{
    static List<ColorPalette> myPaletteList;

    public ConcretePaletteFactory()
    {
        PaletteFrameWork.setFactory(this);
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

            myPaletteList.add(new SearchPalette());
            myPaletteList.add(new RaagamPalette());
            myPaletteList.add(new MoviePalette());
        }
    }

    public List<ColorPalette> getPaletteList()
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
            if (aPalette.getName().equals(aName_in))
            {
                return aPalette;
            }
        }
        return null;
    }
}
