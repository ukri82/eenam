package com.candyz.eenam.player_area;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.candyz.eenam.R;
import com.candyz.eenam.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

class PlayListViewAdapter extends ArrayAdapter<VideoItem>
{

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayoutRes;
    private Context myContext;

    static List<VideoItem> myItems = new ArrayList<>();

    PlayListListener myListener;

    public PlayListViewAdapter(Context context, int itemLayoutRes, PlayListListener aListener_in)
    {
        super(context, itemLayoutRes, R.id.play_list_card, myItems);
        myContext = context;
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayoutRes = itemLayoutRes;
        myListener = aListener_in;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = inflater.inflate(itemLayoutRes, null);
            holder = new ViewHolder(convertView, myContext, myListener);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.myVideoTitle.setBackgroundColor(res.getColor(getBackgroundColorRes(position, itemLayoutRes)));
        holder.myVideoTitle.setText(myItems.get(position).getStart());
        holder.myVideoTitle.setTag(myItems.get(position).getUTubeID());
        holder.myDeleteButton.setTag(myItems.get(position).getUTubeID());

        Animation animation = AnimationUtils.loadAnimation(myContext, R.anim.bottom_up);
        convertView.startAnimation(animation);

        return convertView;
    }
    int getBackgroundColorRes(int position, int itemLayoutRes)
    {
        if (itemLayoutRes == this.itemLayoutRes) {
            return position % 2 == 0 ? R.color.even : R.color.odd;
        } else {
            return (position % 4 == 0 || position % 4 == 3) ? R.color.even : R.color.odd;
        }
    }

    public void move(int from, int to)
    {
        VideoItem anItem = myItems.get(from);
        myItems.remove(from);
        myItems.add(to, anItem);
        notifyDataSetChanged();
    }

    public void remove(String aYoutubeId_in)
    {
        int anItemIndex = getIndexOf(aYoutubeId_in);
        myItems.remove(anItemIndex);
        notifyDataSetChanged();
    }

    private int getIndexOf(String aYoutubeId_in)
    {
        int anItemIndex = -1;
        for(int i = 0; i < myItems.size(); i++)
        {
            if(myItems.get(i).getUTubeID() == aYoutubeId_in)
            {
                anItemIndex = i;
                break;
            }
        }
        return anItemIndex;
    }

    public int getNumVideos()
    {
        return myItems.size();
    }

    public void add(VideoItem aVideoItem_in)
    {
        if(getIndexOf(aVideoItem_in.getUTubeID()) == -1)
        {
            myItems.add(aVideoItem_in);
            notifyDataSetChanged();
        }
    }

    public void set(ArrayList<VideoItem> aList_in)
    {
        myItems.clear();
        myItems.addAll(aList_in);
        notifyDataSetChanged();
    }


    public String getNext(String aYoutubeId_in)
    {
        int aCurrentIndex = getIndexOf(aYoutubeId_in);
        if(aCurrentIndex < myItems.size() - 1)
        {
            aCurrentIndex++;
        }
        else
        {
            aCurrentIndex = 0;
        }

        return getYoutubeId(aCurrentIndex);
    }

    public String getPrevious(String aYoutubeId_in)
    {
        int aCurrentIndex = getIndexOf(aYoutubeId_in);
        if(aCurrentIndex > 0)
        {
            aCurrentIndex--;
        }
        else
        {
            aCurrentIndex = myItems.size() - 1;
        }

        return getYoutubeId(aCurrentIndex);
    }

    public String getYoutubeId(int anIndex_in)
    {
        if(anIndex_in < 0 || anIndex_in >= myItems.size())
            return null;

        return myItems.get(anIndex_in).getUTubeID();
    }

    public String getSongId(String aYoutubeId_in)
    {
        int aCurrentIndex = getIndexOf(aYoutubeId_in);

        if(aCurrentIndex < 0 || aCurrentIndex >= myItems.size())
            return "";

        return myItems.get(aCurrentIndex).getId();
    }

    public String getYoutubeTitle(String aYoutubeId_in)
    {
        int anIndex = getIndexOf(aYoutubeId_in);
        if(anIndex != -1)
        {
            return myItems.get(anIndex).getStart();
        }
        return "";
    }

    public ArrayList<String> getAllSongs()
    {
        ArrayList<String> aList = new ArrayList<>();
        for(VideoItem anItem : myItems)
        {
            aList.add(anItem.getId());
        }
        return aList;
    }

    static class ViewHolder  implements View.OnClickListener
    {
        final TextView myVideoTitle;
        PlayListListener myListener;
        ImageView myDeleteButton;


        ViewHolder(View view, Context aContext_in, PlayListListener aListener_in)
        {
            myVideoTitle = (TextView) view.findViewById(R.id.video_title);
            myDeleteButton = (ImageView) view.findViewById(R.id.delete_button);
            Typeface myTypeface = Typeface.createFromAsset(aContext_in.getAssets(), "fonts/AnjaliOldLipi.ttf");
            myVideoTitle.setTypeface(myTypeface);
            myVideoTitle.setOnClickListener(this);
            myDeleteButton.setOnClickListener(this);
            myListener = aListener_in;
        }

        @Override
        public void onClick(View v)
        {
            if(myListener != null)
            {
                String aUTubeTitle = (String) v.getTag();

                if(myVideoTitle == v)
                {
                    myListener.onItemSelected(aUTubeTitle);
                }
                if(myDeleteButton == v)
                {
                    myListener.onItemDeleted(aUTubeTitle);
                }
            }
        }

    }




}