package com.candyz.eenam.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by u on 09.06.2015.
 */
public class VideoItem
{

    String myId;
    String myStart;
    String myStartEnglish;
    String myUYubeTitle;
    String myUTubeURL;
    String myUTubeId;
    String myUTubeThumbImageURL;

    String myMovieName;
    String myMovieId;
    String myRaagamName;
    String myRaagamNameEnglish;
    String myRaagamId;

    double myRating;

    public VideoItem(String anId_in, String aStart_in, String aStartEnglish_in,
                     String aUTubeTitle_in, String aUTubeURL_in, double aRating_in,
                     String aMovieName_in, String aMovieID_in,
                     String aRaagamName_in, String aRaagamNameEnglish_in, String aRaagamId_in)
    {
        myId = anId_in;
        myStart = aStart_in;
        myStartEnglish = aStartEnglish_in;
        myUYubeTitle = aUTubeTitle_in;
        myUTubeURL = aUTubeURL_in;
        myRating = aRating_in;
        myMovieName = aMovieName_in;
        myMovieId = aMovieID_in;
        myRaagamId = aRaagamId_in;
        myRaagamName = aRaagamName_in;
        myRaagamNameEnglish = aRaagamNameEnglish_in;

        myUTubeId = extractYoutubeId(myUTubeURL);
        myUTubeThumbImageURL = "http://img.youtube.com/vi/" + myUTubeId + "/1.jpg";
    }

    public String getStart()
    {
        return myStart  + "...";
    }

    public String getId()
    {
        return myId;
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

    public String getMovieName()
    {
        return myMovieName;
    }

    public String getMovieId()
    {
        return myMovieId;
    }

    public String getRaagamName()
    {
        return myRaagamName;
    }

    public String getRaagamNameEnglish()
    {
        return myRaagamNameEnglish;
    }

    public String getRaagamId()
    {
        return myRaagamId;
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
