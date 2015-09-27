package com.candyz.eenam.model;

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
        for(String key : aQuery_in.myParams.keySet())
        {
            aQueryString += key + "=" + aQuery_in.myParams.get(key);
        }
        if(aQuery_in.myParams.size() > 0)
            aQueryString += "&";

        aQueryString += "Offset=" + aStart_in + "&Count=" + limit;
        return aQueryString;
    }
}
