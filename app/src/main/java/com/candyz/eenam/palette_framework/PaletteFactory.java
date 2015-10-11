package com.candyz.eenam.palette_framework;

import java.util.List;

/**
 * Created by u on 11.10.2015.
 */
public interface PaletteFactory
{
    public ColorPalette getPalette(String aName_in);
    public String getDefaultPaletteName();
    public List<ColorPalette> getPaletteList();
}
