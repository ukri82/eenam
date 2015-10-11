package com.candyz.eenam;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.palette_concrete.PaletteFactory;


public class MainActivity extends AppCompatActivity
{
    PaletteFactory myPaletteFactory = new PaletteFactory();

    UIDecorator myDecorator;

    private Menu myOptionsMenu;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        VolleySingleton.getInstance(this);  //  Just initialize the singleton

        setContentView(R.layout.activity_main);

        myDecorator = new UIDecorator();
        myDecorator.create(this, myPaletteFactory);

    }

    SearchView mySearchView;
    @Override
    public void onBackPressed()
    {
        MainActivity.this.finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        myOptionsMenu = menu;

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mySearchView =
                (SearchView) menu.findItem(R.id.search_menu_item).getActionView();
        mySearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        final AppCompatActivity aParentActivity = this;


        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Log.d("", "Search query is : " + query);
                if(query != "")
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mySearchView.getWindowToken(), 0);
                    mySearchView.clearFocus();

                    myDecorator.displaySearch(query);

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {

                SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

                final SearchView searchView = (SearchView) menu.findItem(R.id.search_menu_item).getActionView();

                searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

                searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default



                //search.setSuggestionsAdapter(new SearchAutoCorrectAdapter(aParentActivity, query).getAdapater());

                return true;

            }

        });

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
