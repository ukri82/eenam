package com.candyz.eenam.model;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class Endpoints
{
    static String myServerIp = "80.240.142.76";
    public static String getRequestUrlNextVideoChunk(VideoQuery aQuery_in, int aStart_in, int limit)
    {
        //get_songs
        String aQueryString = "http://" + myServerIp + "/" + aQuery_in.myQuery + "?";
        for(String key : aQuery_in.getParams().keySet())
        {
            aQueryString += key + "=" + aQuery_in.getParams().get(key);
        }
        if(aQuery_in.getParams().size() > 0)
            aQueryString += "&";

        aQueryString += "Offset=" + aStart_in + "&Count=" + limit;
        return aQueryString;
    }

    public static String getRequestUrlSavePlayList(String aUserId_in, String aPlayListName_in, ArrayList<String> aPlayListItems_in)
    {
        String aQueryString = "http://" + myServerIp + "/create_play_list?";
        String aSongList = "";
        for(String aSongId : aPlayListItems_in)
        {
            aSongList += aSongId + ",";
        }

        aQueryString += "UserId=" + aUserId_in + "&PlayListName=" + aPlayListName_in + "&Songs=" + aSongList;
        return aQueryString;
    }

    public static String getRequestUrlGetAllPlayLists(String aUserId_in)
    {
        String aQueryString = "http://" + myServerIp + "/get_all_play_lists?";
        aQueryString += "UserId=" + aUserId_in;
        return aQueryString;
    }

    public static String getRequestUrlGetAllSongsOfPlayList(String aPlayListId_in)
    {
        String aQueryString = "http://" + myServerIp + "/get_all_songs_of_list?";
        aQueryString += "PlayListId=" + aPlayListId_in;
        return aQueryString;
    }

    public static String getRequestUrlCreatePlayRecord(String aPlayListId_in, String aUserId_in, String aSongId_in)
    {
        String aQueryString = "http://" + myServerIp + "/add_play_record?";
        aQueryString += "PlayListId=" + aPlayListId_in + "&UserId=" + aUserId_in + "&SongId=" + aSongId_in;
        return aQueryString;
    }
}
