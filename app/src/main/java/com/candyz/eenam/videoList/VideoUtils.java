package com.candyz.eenam.videoList;

import com.android.volley.RequestQueue;
import com.candyz.eenam.json.Endpoints;
import com.candyz.eenam.json.Parser;
import com.candyz.eenam.json.Requestor;
import com.candyz.eenam.json.VideoQuery;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by u on 10.06.2015.
 */
public class VideoUtils
{
    public static ArrayList<VideoItem> loadVideoItems(RequestQueue requestQueue, VideoQuery aQuery_in, int aStart_in, int limit)
    {
        JSONObject response = Requestor.requestVideosJSON(requestQueue, Endpoints.getRequestUrlNextVideoChunk(aQuery_in, aStart_in, limit));
        return Parser.parseVideosJSON(response);
    }
}
