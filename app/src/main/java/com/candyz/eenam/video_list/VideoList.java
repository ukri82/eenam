package com.candyz.eenam.video_list;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.model.DeviceIdentity;
import com.candyz.eenam.model.VideoItem;
import com.candyz.eenam.model.VideoQuery;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.LandingAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * Use the {@link VideoList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoList extends Fragment implements TaskLoadVideos.VideoItemsLoadedListener, VideoItemsListener
{

    RecyclerView myVideoListView;
    private VideoListViewAdapter myVideoAdapter;

    private LandingAnimator myAnimator;

    private String YoutTubeKey = "AIzaSyBXf7ZTTBYL9c36PLaCxa6ssp2FOBDYZkY";

    VideoListListener myListener;

    VideoQuery myQuery;

    LinearLayoutManager myLayoutManager;

    public static VideoList newInstance()
    {
        VideoList fragment = new VideoList();
        return fragment;
    }

    public VideoList()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


    public void initialize(VideoListListener aListener_in, VideoQuery aQuery_in)
    {
        myListener = aListener_in;
        myQuery = aQuery_in;

        myVideoAdapter.clear();

        new TaskLoadVideos(this, myQuery, 0, 10).execute();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        myVideoListView = (RecyclerView)view.findViewById(R.id.video_list);

        myVideoAdapter = new VideoListViewAdapter(getActivity(), 10, this);
        myVideoListView.setAdapter(myVideoAdapter);

        myLayoutManager = new LinearLayoutManager(getActivity());
        myVideoListView.setLayoutManager(myLayoutManager);



        final TaskLoadVideos.VideoItemsLoadedListener aListener = this;

        myVideoListView.setOnScrollListener(new EndlessRecyclerOnScrollListener(myLayoutManager)
        {
            @Override
            public void onLoadMore(int current_page)
            {
                new TaskLoadVideos(aListener, myQuery, myVideoAdapter.getItemCount(), 10).execute();
            }


            public void onHide()
            {
                myListener.onHide();
            }
            public void onShow()
            {
                myListener.onShow();
            }
        });



        //new TaskLoadVideos(this, myQuery, 0, 10).execute();

        //enableItemAnimation();
    }

    private void enableItemAnimation()
    {
        if(Build.VERSION.SDK_INT >= 11)
        {
            myAnimator = new LandingAnimator();
            myAnimator.setAddDuration(1000);
            myAnimator.setRemoveDuration(1000);
            myVideoListView.setItemAnimator(myAnimator);


        }
    }

    @Override
    public void onVideoRated(View v, int aRating_in)
    {
        int itemPosition = myVideoListView.getChildPosition(v);
        VideoItem anItem = myVideoAdapter.getVideoItem(itemPosition);
        new TaskAddSongRating(DeviceIdentity.get(), anItem.getId(), aRating_in).execute();
    }

    @Override
    public void onVideoItemSelected(View v)
    {
        if(myListener != null)
        {
            int itemPosition = myVideoListView.getChildPosition(v);

            myListener.onVideoItemSelected(myVideoAdapter.getVideoItem(itemPosition));
        }

    }

    @Override
    public void onPlayListSelected(View v)
    {
        if(myListener != null)
        {
            int itemPosition = myVideoListView.getChildPosition(v);

            myListener.onPlayListSelected(myVideoAdapter.getVideoItem(itemPosition));
        }
    }

    @Override
    public void onRaagamSelected(String aRaagamNameEnglish_in, String aRaagamName_in)
    {
        if(myListener != null)
        {
            myListener.onRaagamSelected(aRaagamNameEnglish_in, aRaagamName_in);
        }
    }

    @Override
    public void onMovieSelected(String aMovieId_in, String aMovieName_in)
    {
        if(myListener != null)
        {
            myListener.onMovieSelected(aMovieId_in, aMovieName_in);
        }
    }

    @Override
    public void onVideoItemsLoaded(ArrayList<VideoItem> listVideos)
    {
        myVideoAdapter.appendVideoList(listVideos);
    }
}
