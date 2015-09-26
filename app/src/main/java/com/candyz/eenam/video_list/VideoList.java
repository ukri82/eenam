package com.candyz.eenam.video_list;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.VideoFragment;
import com.candyz.eenam.json.VideoQuery;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.LandingAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * Use the {@link VideoList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoList extends Fragment implements TaskLoadVideos.VideoItemsLoadedListener, VideoItemSelectedListener
{

    RecyclerView myVideoListView;
    private VideoListViewAdapter myVideoAdapter;

    private LandingAnimator myAnimator;

    private String YoutTubeKey = "AIzaSyBXf7ZTTBYL9c36PLaCxa6ssp2FOBDYZkY";

    VideoFragment myVideoFragment;

    VideoQuery myQuery;

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


    public void initialize(VideoFragment aFragment_in, VideoQuery aQuery_in)
    {
        myVideoFragment = aFragment_in;
        myQuery = aQuery_in;

        new TaskLoadVideos(this, myQuery, 0, 10).execute();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        myVideoListView = (RecyclerView)view.findViewById(R.id.video_list);

        List<VideoItem> aVideoList = new ArrayList<>();
        myVideoAdapter = new VideoListViewAdapter(getActivity(), aVideoList, this);
        myVideoListView.setAdapter(myVideoAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        myVideoListView.setLayoutManager(llm);



        final TaskLoadVideos.VideoItemsLoadedListener aListener = this;

        myVideoListView.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm)
        {
            @Override
            public void onLoadMore(int current_page)
            {
                new TaskLoadVideos(aListener, myQuery, myVideoAdapter.getItemCount(), 10).execute();
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
    public void onVideoItemSelected(View v)
    {
        int itemPosition = myVideoListView.getChildPosition(v);

        final String aUTubeId = myVideoAdapter.getVideoID(itemPosition);
        try
        {
            myVideoFragment.setVideoId(aUTubeId);

        }
        catch (Exception ex)
        {
            Log.e("Youtube Exception:", ex.toString());
        }

    }

    @Override
    public void onVideoItemsLoaded(ArrayList<VideoItem> listVideos)
    {
        myVideoAdapter.appendVideoList(listVideos);
    }
}