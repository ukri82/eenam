package com.candyz.eenam.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class VideoResultParser
{

    public static ArrayList<VideoItem> parse(JSONObject response)
    {
        ArrayList<VideoItem> listVideo = new ArrayList<>();
        if (response != null && response.length() > 0)
        {
            try
            {
                JSONArray arrayVideoItems = response.getJSONArray("SongData");
                for (int i = 0; i < arrayVideoItems.length(); i++)
                {
                    JSONObject currentVideoItem = arrayVideoItems.getJSONObject(i);

                    try
                    {
                        listVideo.add(new VideoItem(Utils.get(currentVideoItem, "song_id"),
                                Utils.get(currentVideoItem, "start"),
                                Utils.get(currentVideoItem, "start_english"),
                                Utils.get(currentVideoItem, "youtube_title"),
                                Utils.get(currentVideoItem, "youtube_url"),
                                Double.parseDouble(Utils.get(currentVideoItem, "user_rating")),
                                Utils.get(currentVideoItem, "movie"),
                                Utils.get(currentVideoItem, "movie_id"),
                                Utils.get(currentVideoItem, "raagam"),
                                URLEncoder.encode(Utils.get(currentVideoItem, "raagam_english"), "UTF-8"),
                                Utils.get(currentVideoItem, "raagam_id")));
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return listVideo;
    }


}
