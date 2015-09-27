package com.candyz.eenam.video_list;

import com.android.volley.RequestQueue;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.Parser;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.model.VideoQuery;

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
