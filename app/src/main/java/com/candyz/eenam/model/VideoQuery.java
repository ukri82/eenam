package com.candyz.eenam.model;

import java.util.HashMap;

/**
 * Created by u on 20.09.2015.
 */
public class VideoQuery
{
    public String myQuery = new String();

    private HashMap<String, String> myParams = new HashMap<>();

    public VideoQuery(String aQuery_in)
    {
        myQuery = aQuery_in;
    }

    void addParam(String key, String val)
    {
        myParams.put(key, val);
    }

    public void setParams(HashMap<String, String> aParams_in)
    {
        myParams = aParams_in;
    }

    public HashMap<String, String> getParams()
    {
        return myParams;
    }
}
