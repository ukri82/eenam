package com.candyz.eenam.videoList;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candyz.eenam.R;
import com.candyz.eenam.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.LandingAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoList extends Fragment implements TaskLoadVideos.VideoItemsLoadedListener, VideoItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView myVideoListView;
    private VideoListViewAdapter myVideoAdapter;

    private LandingAnimator myAnimator;

    private String YoutTubeKey = "AIzaSyBXf7ZTTBYL9c36PLaCxa6ssp2FOBDYZkY";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoList.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoList newInstance(String param1, String param2)
    {
        VideoList fragment = new VideoList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    VideoFragment myVideoFragment;
    public void setVideoFragment(VideoFragment aFragment_in)
    {
        myVideoFragment = aFragment_in;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
        public void onFragmentCreation(Fragment aFragment_in);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        if (mListener != null)
        {
            mListener.onFragmentCreation(this);
        }

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
                new TaskLoadVideos(aListener, myVideoAdapter.getItemCount(), 10).execute();
            }


        });



        new TaskLoadVideos(this, 0, 10).execute();

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
