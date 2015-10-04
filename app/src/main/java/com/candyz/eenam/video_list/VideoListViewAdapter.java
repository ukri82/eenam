package com.candyz.eenam.video_list;

import android.content.Context;
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
import com.candyz.eenam.misc.AnimationUtils;
import com.candyz.eenam.R;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 09.06.2015.
 */
public class VideoListViewAdapter extends RecyclerView.Adapter<VideoListViewAdapter.VideoItemViewHolder>
{
    private List<VideoItem> myVideoList = new ArrayList<>();

    private LayoutInflater myInflator;
    private Context myContext;

    private VideoItemsListener myVideoItemsListener;

    private VolleySingleton myVolleySingleton;
    private ImageLoader myImageLoader;

    private int myPreviousPosition = 0;

    boolean myScrollingExtendedList = false;

    int myInitialVideoListSize = 0;


    public VideoListViewAdapter(Context context, int anInitialVideoList_in, VideoItemsListener aVideoItemsListener_in)
    {
        this.myContext = context;
        this.myInflator = LayoutInflater.from(myContext);
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
        TextView mySourceView;
        TextView myDateView;
        ImageView myPhotoView;
        RippleView myRippleView;

        private Context myContext;
        public VideoItemsListener myListener;
        View myItemView;

        VideoItemViewHolder(View itemView, VideoItemsListener aListener_in, Context aContext_in)
        {
            super(itemView);
            myContext = aContext_in;
            myItemView = itemView;
            myListener = aListener_in;
            myCard = (CardView)itemView.findViewById(R.id.video_card);
            myTitleView = (TextView)itemView.findViewById(R.id.video_header);
            myDescView = (TextView)itemView.findViewById(R.id.video_details);

            myPhotoView = (ImageView)itemView.findViewById(R.id.video_photo);
            myRippleView = (RippleView)itemView.findViewById(R.id.ripple_view);

            Typeface myTypeface = Typeface.createFromAsset(myContext.getAssets(), "fonts/AnjaliOldLipi.ttf");
            myTitleView.setTypeface(myTypeface);

            myTitleView.setOnClickListener(this);
            myDescView.setOnClickListener(this);
            myPhotoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

            if(myListener != null)
            {
                myListener.onVideoItemSelected(myItemView);
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
        VideoItemViewHolder holder=new VideoItemViewHolder(view, myVideoItemsListener, myContext);
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
        return myVideoList.get(anItemIndex_in);//.getUTubeID();
    }


}
