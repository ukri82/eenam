package com.candyz.eenam.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class PlayListResultParser
{
    
    private static final String NA = "N.A";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "pl_name";
    private static final String KEY_LAST_PLAYED_TIME = "LastPlayedTime";
    private static final String KEY_SONGS = "song_ids";
    private static final String KEY_LAST_PLAYED_SONG = "LastPlayedSong";

    public static ArrayList<PlayListItem> parse(JSONObject response)
    {
        ArrayList<PlayListItem> listPlayLists = new ArrayList<>();
        if (response != null && response.length() > 0)
        {
            try
            {
                JSONArray arrayItems = response.getJSONArray("SongData");
                for (int i = 0; i < arrayItems.length(); i++)
                {
                    String id = NA;
                    String name = NA;
                    String lastPlayedTime = NA;
                    String songsIds = NA;
                    String lastPlayedSong = NA;

                    JSONObject currentItem = arrayItems.getJSONObject(i);


                    if (Utils.contains(currentItem, KEY_ID))
                    {
                        id = currentItem.getString(KEY_ID);
                    }

                    if (Utils.contains(currentItem, KEY_NAME))
                    {
                        name = currentItem.getString(KEY_NAME);
                    }

                    if (Utils.contains(currentItem, KEY_LAST_PLAYED_TIME))
                    {
                        lastPlayedTime = currentItem.getString(KEY_LAST_PLAYED_TIME);
                    }

                    if (Utils.contains(currentItem, KEY_SONGS))
                    {
                        songsIds = currentItem.getString(KEY_SONGS);
                    }

                    if (Utils.contains(currentItem, KEY_LAST_PLAYED_SONG))
                    {
                        lastPlayedSong = currentItem.getString(KEY_LAST_PLAYED_SONG);
                    }

                    PlayListItem anItem = new PlayListItem(id, name, lastPlayedTime, lastPlayedSong, songsIds);

                    listPlayLists.add(anItem);
                }

            }
            catch (JSONException e)
            {

            }
        }
        return listPlayLists;
    }


}
