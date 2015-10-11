package com.candyz.eenam.palette_framework;

/**
 * Created by u on 11.10.2015.
 */
public class PaletteFrameWork
{
    private static PaletteFactory myFactory;

    public static PaletteFactory getFactory()
    {
        return myFactory;
    }

    public static void setFactory(PaletteFactory theFactory_in)
    {
        myFactory = theFactory_in;
    }
}
