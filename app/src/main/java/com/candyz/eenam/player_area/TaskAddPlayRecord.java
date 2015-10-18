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
public class TaskAddPlayRecord extends AsyncTask<Void, Void, String>
{
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String myPlayListId;
    String myUserId;
    String mySongId;

    public TaskAddPlayRecord(String aUserId_in, String aPlayListId_in, String aSongId_in)
    {
        myPlayListId = aPlayListId_in;
        mySongId = aSongId_in;
        myUserId = aUserId_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected String doInBackground(Void... params)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlCreatePlayRecord(myPlayListId, myUserId,mySongId));
        return "";
    }

}