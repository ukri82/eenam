package com.candyz.eenam.player_area;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.candyz.eenam.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class VideoFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener
{

    private YouTubePlayer player;
    private String myVideoId;

    WebView myWebView;
    VideoFragmentListener myVideoListener;


    public static VideoFragment newInstance()
    {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            initialize("AIzaSyCPr3jFeOIvjURMtVdclayyx2COd_pzlBg", this);
        }*/
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            initializeWebView();
        }

    }

    public void attachVideoListener(VideoFragmentListener aListener_in)
    {
        myVideoListener = aListener_in;
    }

    private void initializeWebView()
    {
        myWebView = (WebView) getActivity().findViewById(R.id.youtube_webview);

        if(myWebView == null)
        {
            Log.e("The webview is null", " ");
            return;
        }

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.addJavascriptInterface(this, "VideoFragment");
        myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.setBackgroundColor(Color.BLACK);

    }

    @Override
    public void onDestroy()
    {
        if (player != null)
        {
            player.release();
        }
        else if(myWebView != null)
        {
            //myWebView.destroy();
            myWebView.onPause();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId)
    {


        if (videoId != null && !videoId.equals(this.myVideoId))
        {
            this.myVideoId = videoId;

            if (player != null)
            {
                player.loadVideo(videoId);
                player.play();
            }
            else
            {
                loadVideo();
            }

        }
    }

    @JavascriptInterface
    public String getVideoId()
    {
        Log.i("", "in getVideoId");
        return myVideoId;
    }

    public boolean isVideoLoaded()
    {
        return !myVideoId.isEmpty();
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void videoLoaded()
    {
        Log.i("", "in videoLoaded");
        emulateClick(myWebView);    //  Workaround to start autoplay
    }

    @JavascriptInterface
    public void videoEnded(int anErroCode_in)   //  0 = no error
    {
        Log.i("", "in videoEnded");
        myVideoId = "";
        int aDelay = 20;
        if(anErroCode_in != 0)
        {
            aDelay = 2000;
        }
        if(myVideoListener != null)
        {
            myWebView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    myVideoListener.onVideoFinished(myVideoId);
                }
            }, aDelay);

        }
    }

    public void pause()
    {
        if (player != null)
        {
            player.pause();
        }
        else if(myWebView != null)
        {
            //myWebView.onPause();
        }
    }

    public void resume()
    {
        if(myWebView != null)
        {
            myWebView.onResume();
        }
    }

    public void pauseVideo()
    {
        /*if(myWebView != null)
        {
            myWebView.onPause();
        }*/
        myWebView.loadUrl("javascript:pauseVideo()");
    }
    public void resumeVideo()
    {
        /*if(myWebView != null)
        {
            myWebView.onResume();
        }*/
        myWebView.loadUrl("javascript:playVideo()");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored)
    {
        this.player = player;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        Log.e("Initialize error : ", " " +  youTubeInitializationResult);
        Toast.makeText(this.getActivity().getApplicationContext(), "Initialize error : " +  youTubeInitializationResult, Toast.LENGTH_LONG).show();
    }


    private void emulateClick(final WebView webview)
    {
        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = webview.getLeft() + webview.getWidth()/2; //in the middle of the webview
        float y = webview.getTop() + webview.getHeight()/2;

        final MotionEvent motionEvent = MotionEvent.obtain( downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0 );
        final MotionEvent motionEvent2 = MotionEvent.obtain( downTime + delta + 1, downTime + delta * 2, MotionEvent.ACTION_UP, x, y, 0 );

        Runnable tapdown = new Runnable()
        {
            @Override
            public void run()
            {
                if (webview != null)
                {
                    webview.dispatchTouchEvent(motionEvent);
                }
            }
        };

        Runnable tapup = new Runnable()
        {
            @Override
            public void run()
            {
                if (webview != null)
                {
                    webview.dispatchTouchEvent(motionEvent2);
                }
            }
        };

        int toWait = 0;
        int delay = 2000;
        webview.postDelayed(tapdown, delay);
        delay += 100;
        webview.postDelayed(tapup, delay);
    }

    public void loadVideo()
    {
        myWebView.loadUrl("file:///android_asset/html/youtubeplayer.html");
    }

}