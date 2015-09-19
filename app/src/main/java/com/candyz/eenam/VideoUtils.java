package com.candyz.eenam;

import com.android.volley.RequestQueue;
import com.candyz.eenam.json.Endpoints;
import com.candyz.eenam.json.Parser;
import com.candyz.eenam.json.Requestor;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by u on 10.06.2015.
 */
public class VideoUtils
{
    public static ArrayList<VideoItem> loadVideoItems(RequestQueue requestQueue, int aStart_in, int limit) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlNextVideoChunk(aStart_in, limit));
        return Parser.parseMoviesJSON(response);
    }
}
