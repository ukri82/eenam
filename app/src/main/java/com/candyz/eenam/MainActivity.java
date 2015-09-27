package com.candyz.eenam;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.palette_concrete.PaletteFactory;



public class MainActivity extends AppCompatActivity
{
   PaletteFactory myPaletteFactory = new PaletteFactory();

    UIDecorator myDecorator;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        VolleySingleton.getInstance(this);  //  Just initialize the singleton

        setContentView(R.layout.activity_main);

        myDecorator = new UIDecorator();
        myDecorator.create(this, myPaletteFactory);
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


}
