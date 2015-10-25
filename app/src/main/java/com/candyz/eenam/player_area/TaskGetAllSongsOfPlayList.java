package com.candyz.eenam.player_area;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.model.VideoResultParser;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by u on 10.06.2015.
 */
public class TaskGetAllSongsOfPlayList extends AsyncTask<Void, Void, ArrayList<VideoItem>>
{
    public interface GetAllPlayListSongsQueryListener
    {
        public void onPlayListsAvailable(ArrayList<VideoItem> aPlayList_in);
    }

    private GetAllPlayListSongsQueryListener myListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String myPlayListId;

    public TaskGetAllSongsOfPlayList(GetAllPlayListSongsQueryListener aListener_in, String aPlayListId_in)
    {
        myPlayListId = aPlayListId_in;
        this.myListener = aListener_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<VideoItem> doInBackground(Void... params)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlGetAllSongsOfPlayList(myPlayListId));
        return VideoResultParser.parse(response);
    }

    @Override
    protected void onPostExecute(ArrayList<VideoItem> aList_in)
    {
        if (myListener != null)
        {
            myListener.onPlayListsAvailable(aList_in);
        }
    }


}