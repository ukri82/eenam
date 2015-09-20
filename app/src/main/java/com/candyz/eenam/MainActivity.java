package com.candyz.eenam;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.os.Handler;

import com.candyz.eenam.drawer.DrawerEntries;
import com.candyz.eenam.drawer.FilterItem;
import com.candyz.eenam.drawer.FragmentDrawer;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.videoList.VideoList;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements VideoList.OnFragmentInteractionListener
{

    //private Toolbar myToolbar;

    private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 2;

    private ImageView splash;

    private VideoList myVideoList;
    VideoFragment myVideoFragment;

    //private FloatingActionButton myFAB;
   // private FloatingActionMenu myFABMenu;


    //private FragmentDrawer myDrawerFragment;

    UIDecorator myDecorator;

    //handler for splash screen
    private Handler splashHandler = new Handler() {
        /* (non-Javadoc)
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //remove SplashScreen from view
                    splash.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        VolleySingleton.getInstance(this);  //  Just initialize the singleton

        setContentView(R.layout.activity_main);


        /*splash = (ImageView) findViewById(R.id.splashscreen);
        if(splash != null)
        {
            Message msg = new Message();
            msg.what = STOPSPLASH;
            splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        }*/

        myDecorator = new UIDecorator();
        myDecorator.create(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_menu_item).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onFragmentCreation(Fragment aFragment_in)
    {
        myVideoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        myVideoList = (VideoList)aFragment_in;
        myVideoList.setVideoFragment(myVideoFragment);
    }



}
