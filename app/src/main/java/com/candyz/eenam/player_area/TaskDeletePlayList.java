package com.candyz.eenam.player_area;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Requestor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by u on 10.06.2015.
 */
public class TaskDeletePlayList extends AsyncTask<Void, Void, String>
{
    public interface PlayListDeletionListener
    {
        public void onPlayListDeleted(String aName_in);
    }


    private PlayListDeletionListener myListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String myPlayListName;
    String myUserId;

    public TaskDeletePlayList(PlayListDeletionListener aListener_in, String aUserId_in, String aPlayList_in)
    {
        myPlayListName = aPlayList_in;
        myUserId = aUserId_in;
        this.myListener = aListener_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected String doInBackground(Void... params)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlDeletePlayList(myUserId, myPlayListName));
        return myPlayListName;
    }

    @Override
    protected void onPostExecute(String aPlayListId_in)
    {
        if (myListener != null)
        {
            myListener.onPlayListDeleted(myPlayListName);
        }
    }


}