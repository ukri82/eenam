package com.candyz.eenam.player_area;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.candyz.eenam.R;
import com.candyz.eenam.model.VideoItem;

import java.util.ArrayList;
import java.util.Arrays;
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

        holder.text.setBackgroundColor(res.getColor(getBackgroundColorRes(position, itemLayoutRes)));
        holder.text.setText(myItems.get(position).getStart());
        holder.text.setTag(myItems.get(position).getUTubeID());

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
        return myItems.get(aCurrentIndex).getUTubeID();
    }

    public String getYoutubeId(int anIndex_in)
    {
        return myItems.get(anIndex_in).getUTubeID();
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

    static class ViewHolder  implements View.OnClickListener
    {
        final TextView text;
        PlayListListener myListener;

        ViewHolder(View view, Context aContext_in, PlayListListener aListener_in)
        {
            text = (TextView) view.findViewById(R.id.text);
            Typeface myTypeface = Typeface.createFromAsset(aContext_in.getAssets(), "fonts/AnjaliOldLipi.ttf");
            text.setTypeface(myTypeface);
            text.setOnClickListener(this);
            myListener = aListener_in;
        }

        @Override
        public void onClick(View v)
        {
            Log.i("", "inonItemClick");
            if(myListener != null)
            {
                myListener.onItemSelected((String)v.getTag());
            }
        }

    }




}