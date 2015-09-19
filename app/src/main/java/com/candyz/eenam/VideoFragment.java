package com.candyz.eenam;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class VideoFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener
{

    private YouTubePlayer player;
    private String videoId;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize("AIzaSyCPr3jFeOIvjURMtVdclayyx2COd_pzlBg", this);
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (player != null) {
                player.loadVideo(videoId);
                player.play();
            }
        }
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        this.player = player;
        //player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        //player.setOnFullscreenListener((MainActivity) getActivity());
        /*if (!restored && videoId != null) {
            player.cueVideo(videoId);
        }*/
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        Log.e("Initialize error : ", " " +  youTubeInitializationResult);
        Toast.makeText(this.getActivity().getApplicationContext(), "Initialize error : " +  youTubeInitializationResult, Toast.LENGTH_LONG).show();
    }

}