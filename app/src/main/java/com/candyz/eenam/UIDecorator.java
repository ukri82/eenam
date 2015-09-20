package com.candyz.eenam;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.candyz.eenam.drawer.DrawerEntries;
import com.candyz.eenam.drawer.FilterItem;
import com.candyz.eenam.drawer.FragmentDrawer;
import com.candyz.eenam.json.Utils;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by u on 20.09.2015.
 */
public class UIDecorator implements FragmentDrawer.DrawerEventsListener, View.OnClickListener
{
    private FloatingActionButton myFAB;
    private FloatingActionMenu myFABMenu;

    private Toolbar myToolbar;

    AppCompatActivity myParentActivity;


    private FragmentDrawer myDrawerFragment;

    public void create(AppCompatActivity parentActivity_in)
    {
        myParentActivity = parentActivity_in;


        setUpActionBar();

        setupDrawer();

        setupFAB();
    }

    private void setupDrawer()
    {
        final DrawerLayout aDrawerLayout = (DrawerLayout) ((ViewGroup) myParentActivity.findViewById(android.R.id.content)).getChildAt(0); //  Top layout should be DrawerLayout

        Fragment aFragment = FragmentDrawer.newInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            aDrawerLayout.setId(Utils.generateViewId());
        }
        else
        {
            aDrawerLayout.setId(View.generateViewId());
        }


        myDrawerFragment = (FragmentDrawer)aFragment;
        myParentActivity.getSupportFragmentManager().beginTransaction().add(aDrawerLayout.getId(), aFragment).commit();

        myDrawerFragment.initialize(aFragment.getId(), aDrawerLayout, myToolbar, this);
    }

    private void toggleTranslateFAB(float slideOffset)
    {
        if (myFABMenu != null)
        {
            if (myFABMenu.isOpen()) {
                myFABMenu.close(true);
            }
            if(myFAB != null)
            {
                myFAB.setTranslationX(slideOffset * 200);
            }
        }
    }

    private void setUpActionBar()
    {
        LayoutInflater inflator = LayoutInflater.from(myParentActivity);
        View v = inflator.inflate(R.layout.app_bar, null);
        myToolbar = (Toolbar)v.findViewById(R.id.app_bar_generic);
        final DrawerLayout aDrawerrLayout = (DrawerLayout) ((ViewGroup) myParentActivity.findViewById(android.R.id.content)).getChildAt(0); //  Top layout should be DrawerLayout
        final LinearLayout aLinearLayout = (LinearLayout) ((ViewGroup) aDrawerrLayout.getChildAt(0));   //  Its child should be linear layout.


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        myToolbar.setLayoutParams(lp);

        aLinearLayout.addView(myToolbar, 0);

        myParentActivity.setSupportActionBar(myToolbar);
        myParentActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        myParentActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        myParentActivity.getSupportActionBar().setTitle(R.string.app_name);
    }

    private void setupFAB()
    {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(myParentActivity);
        iconFAB.setImageResource(R.drawable.button_action_fg_filter);


        //set the appropriate background for the main floating action button along with its icon
        myFAB = new FloatingActionButton.Builder(myParentActivity)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.button_action_bg_red)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(myParentActivity);
        itemBuilder.setBackgroundDrawable(myParentActivity.getResources().getDrawable(R.drawable.button_action_bg_red));

        FloatingActionMenu.Builder aBuilder = new FloatingActionMenu.Builder(myParentActivity);

        List<FilterItem> aFilterList = DrawerEntries.getFilterList(myParentActivity);
        for (FilterItem aFilter : aFilterList)
        {
            ImageView iconView = new ImageView(myParentActivity);
            iconView.setImageResource(aFilter.myResId);

            SubActionButton buttonFilter = itemBuilder.setContentView(iconView).build();
            buttonFilter.setTag(aFilter.getTag());
            buttonFilter.setOnClickListener(this);

            aBuilder.addSubActionView(buttonFilter);
        }
        aBuilder.attachTo(myFAB);
        aBuilder.setRadius(myParentActivity.getResources().getDimensionPixelSize(R.dimen.action_menu_radius_more));
        myFABMenu = aBuilder.build();

    }
    @Override
    public void onSlide(float slideOffset)
    {
        toggleTranslateFAB(slideOffset);
    }

    @Override
    public void onClick(String aTag_in)
    {
        if (!aTag_in.equals(""))
        {
            List<String> aTokens = Arrays.asList(aTag_in.split("#"));

            Class<?> c = null;
            try
            {
                c = Class.forName(getClass().getPackage().getName() + "." + aTokens.get(0) );

            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            Intent aIntent = new Intent(myParentActivity, c);
            aIntent.putExtra("BackGroundColor", aTokens.get(1));
            myParentActivity.startActivity(aIntent);
        }
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getTag().toString());
    }

}
