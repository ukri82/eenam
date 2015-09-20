package com.candyz.eenam;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.videoList.VideoList;

public class NewGenActivity extends AppCompatActivity  implements VideoList.OnFragmentInteractionListener
{

    private VideoList myVideoList;
    VideoFragment myVideoFragment;
    int myBackgroundColor;

    UIDecorator myDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        VolleySingleton.getInstance(this);
        Intent intent = getIntent();
        myBackgroundColor = Integer.parseInt(intent.getStringExtra("BackGroundColor"));

        setContentView(R.layout.activity_new_gen);

        myDecorator = new UIDecorator();
        myDecorator.create(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_gen, menu);
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
        myVideoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container_new_gen);
        myVideoList = (VideoList)aFragment_in;
        myVideoList.setVideoFragment(myVideoFragment);
        myVideoList.getView().setBackgroundColor(myBackgroundColor);
    }
}
