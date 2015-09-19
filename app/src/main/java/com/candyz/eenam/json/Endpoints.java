package com.candyz.eenam.json;

/**
 * Created by Windows on 02-03-2015.
 */
public class Endpoints {
    public static String getRequestUrlNextVideoChunk(int aStart_in, int limit) {

        return "http://80.240.142.76/get_songs?" + "Offset=" + aStart_in + "&Count=" + limit;
    }
}
