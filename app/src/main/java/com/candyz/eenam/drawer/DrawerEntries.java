package com.candyz.eenam.drawer;

import android.app.Activity;
import android.graphics.Color;

import com.candyz.eenam.R;
import com.candyz.eenam.drawer.FilterItem;

import java.util.List;

/**
 * Created by u on 11.06.2015.
 */
public class DrawerEntries
{
    public static final String NO_FILTER = "All";
    public static final String FILTER_NEW_GEN = "New Gen";
    public static final String FILTER_ACCEPTED = "Widely Accepted";
    public static final String FILTER_CLASSICAL = "Classical Based";
    public static final String FILTER_NOSTALGIC = "Nostalgic Evergreen";
    public static final String FILTER_NOSTALGIC_NUMBERS = "Nostalgic Hits";
    public static final String FILTER_CONTRA = "Widely Discussed";
    public static final String FILTER_MOTHERS = "Mom's Nostalgia";

    public static List<FilterItem> getFilterList(Activity anActivity_in)
    {
        List<FilterItem> aFilterList = new java.util.ArrayList<>();

        aFilterList.add(new FilterItem(FILTER_MOTHERS, R.drawable.button_action_fg_mothers, anActivity_in.getResources().getColor(R.color.violet), "MOTHERS_NOSTALGIA"));
        aFilterList.add(new FilterItem(FILTER_NOSTALGIC_NUMBERS, R.drawable.button_action_fg_nostalgic_numbers, anActivity_in.getResources().getColor(R.color.indgio), "NOSTALGIC_NUMBERS"));
        aFilterList.add(new FilterItem(FILTER_NOSTALGIC, R.drawable.button_action_fg_nostalgic, anActivity_in.getResources().getColor(R.color.blue), "NOSTALGIC"));
        aFilterList.add(new FilterItem(FILTER_CLASSICAL, R.drawable.button_action_fg_classical, anActivity_in.getResources().getColor(R.color.green), "CLASSICAL"));
        aFilterList.add(new FilterItem(FILTER_ACCEPTED, R.drawable.button_action_fg_accepted, anActivity_in.getResources().getColor(R.color.yellow), "ACCEPTED"));
        aFilterList.add(new FilterItem(FILTER_CONTRA, R.drawable.button_action_fg_contra, anActivity_in.getResources().getColor(R.color.orange), "CONTRA"));
        aFilterList.add(new FilterItem(FILTER_NEW_GEN, R.drawable.button_action_fg_new_gen, anActivity_in.getResources().getColor(R.color.red), "NewGenActivity"));

        aFilterList.add(new FilterItem(NO_FILTER, R.drawable.button_action_fg_filter_cross, anActivity_in.getResources().getColor(R.color.white), "NO_FILTER"));

        return aFilterList;
    }

}
