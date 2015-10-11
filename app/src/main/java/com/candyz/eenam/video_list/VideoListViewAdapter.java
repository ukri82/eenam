package com.candyz.eenam.video_list;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.candyz.eenam.drawer.DrawerEntries;
import com.candyz.eenam.drawer.FilterItem;
import com.candyz.eenam.misc.AnimationUtils;
import com.candyz.eenam.R;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.VideoItem;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 09.06.2015.
 */
public class VideoListViewAdapter extends RecyclerView.Adapter<VideoListViewAdapter.VideoItemViewHolder>
{
    private List<VideoItem> myVideoList = new ArrayList<>();

    private LayoutInflater myInflator;
    private Activity myActivity;

    private VideoItemsListener myVideoItemsListener;

    private VolleySingleton myVolleySingleton;
    private ImageLoader myImageLoader;

    private int myPreviousPosition = 0;

    boolean myScrollingExtendedList = false;

    int myInitialVideoListSize = 0;


    public VideoListViewAdapter(Activity anActivity_in, int anInitialVideoList_in, VideoItemsListener aVideoItemsListener_in)
    {
        this.myActivity = anActivity_in;
        this.myInflator = LayoutInflater.from(myActivity);
        this.myInitialVideoListSize = anInitialVideoList_in;

        myVideoItemsListener = aVideoItemsListener_in;

        myVolleySingleton = VolleySingleton.getInstance(null);
        myImageLoader = myVolleySingleton.getImageLoader();

    }

    void clear()
    {
        myVideoList.clear();
        notifyDataSetChanged();
    }



    public static class VideoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView myCard;
        TextView myTitleView;
        TextView myDescView;
        TextView myMovieView;
        TextView myRaagamView;
        ImageView myPhotoView;
        RippleView myRippleView;
        ImageView myPlayListView;
        StarRater myRater;

        private Activity myActivity;
        public VideoItemsListener myListener;
        View myItemView;

        VideoItemViewHolder(View itemView, VideoItemsListener aListener_in, Activity anActivity_in)
        {
            super(itemView);
            myActivity = anActivity_in;
            myItemView = itemView;
            myListener = aListener_in;
            myCard = (CardView)itemView.findViewById(R.id.video_card);
            myTitleView = (TextView)itemView.findViewById(R.id.video_header);
            myDescView = (TextView)itemView.findViewById(R.id.video_details);
            myMovieView = (TextView)itemView.findViewById(R.id.video_movie_name);
            myRaagamView = (TextView)itemView.findViewById(R.id.video_raagam_name);

            myPhotoView = (ImageView)itemView.findViewById(R.id.video_photo);
            myRippleView = (RippleView)itemView.findViewById(R.id.ripple_view);
            myPlayListView = (ImageView)itemView.findViewById(R.id.add_to_play_list_icon);
            myRater = (StarRater)itemView.findViewById(R.id.five_star_rater);

            Typeface myTypeface = Typeface.createFromAsset(myActivity.getAssets(), "fonts/AnjaliOldLipi.ttf");
            myTitleView.setTypeface(myTypeface);

            myTitleView.setOnClickListener(this);
            myDescView.setOnClickListener(this);
            myPhotoView.setOnClickListener(this);
            myPlayListView.setOnClickListener(this);
            myMovieView.setOnClickListener(this);
            myRaagamView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(myListener != null)
            {
                if(v == myPlayListView)
                {
                    myListener.onPlayListSelected(myItemView);
                }
                else if(v == myRaagamView)
                {
                    myListener.onRaagamSelected((String)myRaagamView.getTag());
                }
                else if(v == myMovieView)
                {
                    myListener.onMovieSelected((String) myMovieView.getTag());
                }
                else
                {
                    myListener.onVideoItemSelected(myItemView);
                }
            }
        }

    }


    private void loadImages(String urlThumbnail, final VideoItemViewHolder holder)
    {
        if (!urlThumbnail.equals("N.A") && !urlThumbnail.equals(""))
        {
            myImageLoader.get(urlThumbnail, new ImageLoader.ImageListener()
            {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                {
                    holder.myPhotoView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error)
                {

                }
            });
        }
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = this.myInflator.inflate(R.layout.video_card, parent, false);
        VideoItemViewHolder holder=new VideoItemViewHolder(view, myVideoItemsListener, myActivity);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder holder, int position)
    {
        if(position >= myVideoList.size())
        {
            return;
        }

        VideoItem current = myVideoList.get(position);
        holder.myTitleView.setText(current.getStart());
        holder.myDescView.setText(current.getUTubeTitle());
        holder.myMovieView.setText(current.getMovieName());
        holder.myMovieView.setTag(current.getMovieId());
        if(current.getRaagamName() != "N.A")
        {
            holder.myRaagamView.setText(current.getRaagamName());
            holder.myRaagamView.setTag(current.getRaagamId());
            holder.myRaagamView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.myRaagamView.setVisibility(View.INVISIBLE);
        }

        String urlThumnail = current.getUTubeThumbImageURL();
        loadImages(urlThumnail, holder);

        if (position > myPreviousPosition)
        {
            if(Build.VERSION.SDK_INT >= 11)
            {
                AnimationUtils.animateSunblind(holder, true);
            }
        }
        else
        {
            if(Build.VERSION.SDK_INT >= 11)
            {
                AnimationUtils.animateSunblind(holder, false);
            }
        }
        myPreviousPosition = position;
     }

    @Override
    public int getItemCount()
    {
        return myVideoList.size();
    }


    public void appendVideoList(List<VideoItem> data)
    {
        int aNumItems = myVideoList.size();
        myVideoList.addAll(data);

        if(myVideoList.size() > myInitialVideoListSize)
        {
            myScrollingExtendedList = true;
        }

        notifyItemRangeInserted(aNumItems, data.size());
    }

    public VideoItem getVideoItem(int anItemIndex_in)
    {
        return myVideoList.get(anItemIndex_in);
    }


}
