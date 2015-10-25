package com.candyz.eenam.video_list;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.Utils;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.model.VideoQuery;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.VideoResultParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by u on 10.06.2015.
 */
public class TaskLoadVideos extends AsyncTask<Void, Void, ArrayList<VideoItem>>
{
    public interface VideoItemsLoadedListener
    {
        public void onVideoItemsLoaded(ArrayList<VideoItem> listVideo);
    }


    private VideoItemsLoadedListener myListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private int myStart = 0;
    private int myCount = 10;
    VideoQuery myQuery;


    public TaskLoadVideos(VideoItemsLoadedListener aListener_in, VideoQuery aQuery_in, int aStart_in, int limit)
    {
        myQuery = aQuery_in;
        myStart = aStart_in;
        myCount = limit;
        this.myListener = aListener_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<VideoItem> doInBackground(Void... params)
    {

        ArrayList<VideoItem> listVideo = null;
        if(!myQuery.myQuery.isEmpty())
        {
            listVideo = load(requestQueue, myQuery, myStart, myCount);
        }
        return listVideo;
    }

    @Override
    protected void onPostExecute(ArrayList<VideoItem> listVideo)
    {
        if (myListener != null)
        {
            myListener.onVideoItemsLoaded(listVideo);
        }
    }

    public static ArrayList<VideoItem> load(RequestQueue requestQueue, VideoQuery aQuery_in, int aStart_in, int limit)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlNextVideoChunk(aQuery_in, aStart_in, limit));
        return VideoResultParser.parse(response);
    }

}