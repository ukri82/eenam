package com.candyz.eenam.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class VideoResultParser
{
    
    private static final String NA = "N.A";
    private static final String KEY_START = "start";
    private static final String KEY_START_ENGLISH = "start_english";
    private static final String KEY_UTUBE_TITLE = "youtube_title";
    private static final String KEY_UTUBE_URL = "youtube_url";
    private static final String KEY_UTUBE_LIKES = "youtube_likes";

    public static ArrayList<VideoItem> parseVideosJSON(JSONObject response)
    {
        ArrayList<VideoItem> listVideo = new ArrayList<>();
        if (response != null && response.length() > 0)
        {
            try
            {
                JSONArray arrayVideoItems = response.getJSONArray("SongData");
                for (int i = 0; i < arrayVideoItems.length(); i++)
                {
                    //long id = -1;
                    String start = NA;
                    String start_english = NA;
                    String utube_title = NA;
                    String utube_url = NA;
                    String utube_likes = NA;
                    String movie_name = NA;
                    String movie_id = NA;
                    String raagam_name = NA;
                    String raagam_id = NA;


                    JSONObject currentVideoItem = arrayVideoItems.getJSONObject(i);


                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_START))
                    {
                        start = currentVideoItem.getJSONObject("song").getString(KEY_START);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_START_ENGLISH))
                    {
                        start_english = currentVideoItem.getJSONObject("song").getString(KEY_START_ENGLISH);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_UTUBE_TITLE))
                    {
                        utube_title = currentVideoItem.getJSONObject("song").getString(KEY_UTUBE_TITLE);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_UTUBE_URL))
                    {
                        utube_url = currentVideoItem.getJSONObject("song").getString(KEY_UTUBE_URL);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song_rating"), KEY_UTUBE_LIKES))
                    {
                        utube_likes = currentVideoItem.getJSONObject("song_rating").getString(KEY_UTUBE_LIKES);
                    }

                    if (Utils.contains(currentVideoItem, "movie"))
                    {
                        if (Utils.contains(currentVideoItem.getJSONObject("movie"), "name"))
                        {
                            movie_name = currentVideoItem.getJSONObject("movie").getString("name");
                        }

                        if (Utils.contains(currentVideoItem.getJSONObject("movie"), "id"))
                        {
                            movie_id = currentVideoItem.getJSONObject("movie").getString("id");
                        }
                    }

                    if (Utils.contains(currentVideoItem, "raagam"))
                    {
                        if (Utils.contains(currentVideoItem.getJSONObject("raagam"), "name"))
                        {
                            raagam_name = currentVideoItem.getJSONObject("raagam").getString("name");
                        }

                        if (Utils.contains(currentVideoItem.getJSONObject("raagam"), "id"))
                        {
                            raagam_id = currentVideoItem.getJSONObject("raagam").getString("id");
                        }
                    }

                    VideoItem aVideoItem = new VideoItem(start, start_english, utube_title, utube_url, Integer.parseInt(utube_likes), movie_name, movie_id, raagam_name, raagam_id);

                    listVideo.add(aVideoItem);
                }

            } catch (JSONException e)
            {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listVideo;
    }


}
