package com.candyz.eenam.json;

import com.candyz.eenam.videoList.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class Parser {
    
    private static final String NA = "N.A";
    private static final String KEY_START = "start";
    private static final String KEY_START_ENGLISH = "start_english";
    private static final String KEY_UTUBE_TITLE = "youtube_title";
    private static final String KEY_UTUBE_URL = "youtube_url";
    private static final String KEY_UTUBE_LIKES = "youtube_likes";

    public static ArrayList<VideoItem> parseMoviesJSON(JSONObject response) {
        ArrayList<VideoItem> listVideo = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayVideoItems = response.getJSONArray("SongData");
                for (int i = 0; i < arrayVideoItems.length(); i++) {
                    //long id = -1;
                    String start = NA;
                    String start_english = NA;
                    String utube_title = NA;
                    String utube_url = NA;
                    String utube_likes = NA;


                    JSONObject currentVideoItem = arrayVideoItems.getJSONObject(i);


                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_START)) {
                        start = currentVideoItem.getJSONObject("song").getString(KEY_START);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_START_ENGLISH)) {
                        start_english = currentVideoItem.getJSONObject("song").getString(KEY_START_ENGLISH);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_UTUBE_TITLE)) {
                        utube_title = currentVideoItem.getJSONObject("song").getString(KEY_UTUBE_TITLE);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song"), KEY_UTUBE_URL)) {
                        utube_url = currentVideoItem.getJSONObject("song").getString(KEY_UTUBE_URL);
                    }

                    if (Utils.contains(currentVideoItem.getJSONObject("song_rating"), KEY_UTUBE_LIKES)) {
                        utube_likes = currentVideoItem.getJSONObject("song_rating").getString(KEY_UTUBE_LIKES);
                    }

                    VideoItem aVideoItem = new VideoItem(start, start_english, utube_title, utube_url, Integer.parseInt(utube_likes));

                    listVideo.add(aVideoItem);
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listVideo;
    }


}
