package com.candyz.eenam.video_list;


import android.content.Context;
import android.app.Fragment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.candyz.eenam.R;
import com.candyz.eenam.player_area.TaskAddPlayRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarRater extends RelativeLayout implements View.OnClickListener
{

    LayoutInflater mInflater;
    TextView myStarView;
    double myRating = 0;
    StarRaterListener myListener;

    public StarRater(Context context)
    {
        super(context);
        init(context);
    }
    public StarRater(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }
    public StarRater(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public void init(Context context)
    {
        mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.fragment_rating, this, true);
        myStarView = (TextView) v.findViewById(R.id.rating_icon);
        myStarView.setOnClickListener(this);
    }

    public void setListener(StarRaterListener aListener_in)
    {
        myListener = aListener_in;
    }

    public void setRating(double aRating_in)
    {
        myRating = aRating_in;
        if(myStarView != null && myRating > 0)
        {
            myStarView.setText(myRating + "");
        }
    }

    void animateStars(int i, final ImageView imageView, final boolean anAdd_in)
    {
        int aCurrentPosX = imageView.getLeft();
        int aCurrentPosY = imageView.getTop();
        int aStartX = myStarView.getLeft();
        TranslateAnimation anim;
        if(anAdd_in)
        {
            anim = new TranslateAnimation(aStartX - aCurrentPosX, 0, aCurrentPosY, aCurrentPosY);
        }
        else
        {
            anim = new TranslateAnimation(0, aStartX - aCurrentPosX, aCurrentPosY, aCurrentPosY);
        }
        anim.setDuration((myNumStars - i) * 100);
        anim.setFillAfter(true);
        final StarRater theFragment = this;
        anim.setAnimationListener(new TranslateAnimation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                if(anAdd_in)
                    imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                if(!anAdd_in)
                {
                    imageView.setVisibility(View.VISIBLE);
                    ((ViewManager) imageView.getParent()).removeView(imageView);
                    myStars.remove(imageView);
                    if(myStars.size() == 0)
                    {
                        int theWidthInDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, myStarWidth, getResources().getDisplayMetrics());
                        RelativeLayout.LayoutParams thisViewParams = (RelativeLayout.LayoutParams) getLayoutParams();
                        thisViewParams.width = thisViewParams.width - myNumStars * theWidthInDip;
                        theFragment.setLayoutParams(thisViewParams);
                    }
                }
            }
        });

        imageView.startAnimation(anim);
    }

    List<ImageView> myStars = new ArrayList<>();

    @Override
    public void onClick(View v)
    {
        if(myStars.size() == 0)
        {
            addStars(v);
        }
        else
        {
            removeStars(v);
        }
    }

    final int myStarWidth = 30;
    final int myStarHeight = 30;
    final int myNumStars = 5;

    private void removeStars(View v)
    {
        for(int i = 0; i < myStars.size(); i++)
        {
            final int aViewIndex = i;
            final Handler aHandler = new Handler();
            final ImageView imageView = myStars.get(aViewIndex);
            aHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    animateStars(aViewIndex, imageView, false);
                }
            }, 1);
        }
    }

    private void addStars(View v)
    {
        int theWidthInDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, myStarWidth, getResources().getDisplayMetrics());
        int theHeightInDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, myStarHeight, getResources().getDisplayMetrics());
        int aPrevView = -1;

        RelativeLayout.LayoutParams thisViewParams = (RelativeLayout.LayoutParams) getLayoutParams();
        thisViewParams.width = thisViewParams.width + myNumStars * theWidthInDip;
        this.setLayoutParams(thisViewParams);

        for(int i = 0; i < myNumStars; i++)
        {
            final ImageView imageView = new ImageView(getContext());
            if(i < myRating)
                imageView.setImageResource(R.drawable.ic_star);
            else
                imageView.setImageResource(R.drawable.ic_star_outline);

            imageView.setId((i + 1) * 1000);

            RelativeLayout.LayoutParams aParams = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            aParams.width = theWidthInDip;
            aParams.height = theHeightInDip;

            imageView.setTag(5000 + i);


            if(aPrevView!=-1)
                    aParams.addRule(RelativeLayout.RIGHT_OF, aPrevView);

            imageView.setVisibility(View.GONE);
            this.addView(imageView, aParams);
            myStars.add(imageView);

            final int aRating = i + 1;
            imageView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    for (int j = 0; j < myStars.size(); j++)
                    {
                        final ImageView imageView = myStars.get(j);
                        if (j < aRating)
                            imageView.setImageResource(R.drawable.ic_star);
                        else
                            imageView.setImageResource(R.drawable.ic_star_outline);
                    }
                    setRating(aRating);
                    if (myListener != null)
                    {
                        myListener.onRated(aRating);
                    }
                }
            });

            final int aViewIndex = i;
            final Handler aHandler = new Handler();
            aHandler.postDelayed(new

            Runnable()
            {
                public void run ()
                {
                    animateStars(aViewIndex, imageView, true);
                }
            }

            ,1);

            aPrevView=imageView.getId();

        }
    }

}
