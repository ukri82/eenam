package com.candyz.eenam.video_list;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Requestor;

import org.json.JSONObject;

/**
 * Created by u on 10.06.2015.
 */
public class TaskAddSongRating extends AsyncTask<Void, Void, String>
{
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    int myRating;
    String myUserId;
    String mySongId;

    public TaskAddSongRating(String aUserId_in, String aSongId_in, int aRating_in)
    {
        myRating = aRating_in;
        mySongId = aSongId_in;
        myUserId = aUserId_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected String doInBackground(Void... params)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlAddSongRating(myUserId,mySongId, myRating));
        return "";
    }

}