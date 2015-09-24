package com.candyz.eenam.video_list;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by u on 09.06.2015.
 */
public class VideoItem
{

    String myStart;
    String myStartEnglish;
    String myUYubeTitle;
    String myUTubeURL;
    String myUTubeId;
    String myUTubeThumbImageURL;

    double myRating;

    public VideoItem(String aStart_in, String aStartEnglish_in, String aUTubeTitle_in, String aUTubeURL_in, double aRating_in)
    {
        myStart = aStart_in;
        myStartEnglish = aStartEnglish_in;
        myUYubeTitle = aUTubeTitle_in;
        myUTubeURL = aUTubeURL_in;
        myRating = aRating_in;

        myUTubeId = extractYoutubeId(myUTubeURL);
        myUTubeThumbImageURL = "http://img.youtube.com/vi/" + myUTubeId + "/1.jpg";
    }

    public String getStart()
    {
        return myStart;
    }

    public String getUTubeURL()
    {
        return myUTubeURL;
    }

    public String getUTubeID()
    {
        return myUTubeId;
    }

    public String getUTubeThumbImageURL()
    {
        return myUTubeThumbImageURL;
    }

    public String getStartEnglish()
    {
        return myStartEnglish;
    }

    public String getUTubeTitle()
    {
        return myUYubeTitle;
    }

    public double getRating()
    {
        return myRating;
    }

    private static String extractYoutubeId(String url)
    {
       String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find())
        {
            return matcher.group();
        }

        return "";
    }
}
