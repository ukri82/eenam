package com.candyz.eenam.model;

import java.util.HashMap;

/**
 * Created by u on 20.09.2015.
 */
public class VideoQuery
{
    public String myQuery = new String();

    public HashMap<String, String> myParams = new HashMap<>();

    public VideoQuery(String aQuery_in)
    {
        myQuery = aQuery_in;
    }
}
