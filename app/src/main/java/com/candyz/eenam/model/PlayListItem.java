package com.candyz.eenam.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by u on 18.10.2015.
 */
public class PlayListItem  implements Comparable<PlayListItem>
{
    private String myId;
    private String myName;
    private Date myDateTime;
    private String myLastSongId;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<String> mySongIds;

    PlayListItem(String anId_in, String aName_in, String aDateTime_in, String aLastSongId_in, String aSongIdList_in)
    {
        myId = anId_in;
        myName = aName_in;
        myLastSongId = aLastSongId_in;
        try
        {
            myDateTime = format.parse(aDateTime_in);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public String getId()
    {
        return myId;
    }

    public String getName()
    {
        return myName;
    }

    public String getLastSongId()
    {
        return myLastSongId;
    }

    public Date getDateTime()
    {
        return myDateTime;
    }

    @Override
    public String toString()
    {
        return  myName;
    }

    @Override
    public int compareTo(PlayListItem o)
    {
        return this.myDateTime.after(o.myDateTime)? -1 : 1;
    }
}
