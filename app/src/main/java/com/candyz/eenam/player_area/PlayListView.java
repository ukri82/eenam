package com.candyz.eenam.player_area;

import android.content.Context;
import android.util.AttributeSet;

import com.mobeta.android.dslv.DragSortListView;

/**
 * Created by u on 27.09.2015.
 */
public class PlayListView extends DragSortListView
{
    private final JazzyHelper mHelper;

    public PlayListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mHelper = init(context, null);
    }


    private JazzyHelper init(Context context, AttributeSet attrs)
    {
        JazzyHelper helper = new JazzyHelper(context, attrs);
        super.setOnScrollListener(helper);
        return helper;
    }

    @Override
    public final void setOnScrollListener(OnScrollListener l) {
        mHelper.setOnScrollListener(l);
    }

    public void setTransitionEffect(int transitionEffect) {
        mHelper.setTransitionEffect(transitionEffect);
    }

}
