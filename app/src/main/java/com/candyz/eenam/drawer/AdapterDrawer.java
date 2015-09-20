package com.candyz.eenam.drawer;

/**
 * Created by u on 11.06.2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.candyz.eenam.R;

import java.util.Collections;
import java.util.List;


/**
 * Created by Windows on 22-12-2014.
 */
public class AdapterDrawer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FilterItem> data= Collections.emptyList();
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;
    private LayoutInflater inflater;
    private Context context;
    public AdapterDrawer(Context context, List<FilterItem> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER){
            View view=inflater.inflate(R.layout.drawer_header, parent,false);
            HeaderHolder holder=new HeaderHolder(view);
            return holder;
        }
        else{
            View view=inflater.inflate(R.layout.item_drawer, parent,false);
            ItemHolder holder=new ItemHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderHolder ){
            HeaderHolder headerHolder = (HeaderHolder) holder;

        }
        else{
            ItemHolder itemHolder= (ItemHolder) holder;
            FilterItem current=data.get(position-1);
            itemHolder.title.setText(current.myName);
            itemHolder.icon.setImageResource(current.myResId);
            itemHolder.layout.setBackgroundColor(current.myBGColor);
        }

    }
    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public String getTag(int aPosition_in)
    {
        if(aPosition_in < 0)
            return "";


        return data.get(aPosition_in).getTag();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        RelativeLayout layout;
        public ItemHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
            layout = (RelativeLayout) itemView.findViewById(R.id.listLayout);
        }
    }
    class HeaderHolder extends RecyclerView.ViewHolder {

        View myView;

        public HeaderHolder(View itemView) {
            super(itemView);
            myView = itemView;

        }
    }
}

