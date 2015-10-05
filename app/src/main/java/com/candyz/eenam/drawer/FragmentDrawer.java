package com.candyz.eenam.drawer;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.candyz.eenam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment
{

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private View myContainer;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private RecyclerView myRecyclerDrawer;
    private AdapterDrawer myAdapter;

    //private int myFragmentId;
    private Toolbar myToolBar;

    public interface DrawerEventsListener
    {
        void onClick(String aTag_in);
        void onSlide(float slideOffset);
    }

    DrawerEventsListener myDrawerEventsListener;

    public FragmentDrawer()
    {
        // Required empty public constructor
    }

    public void initialize(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar, DrawerEventsListener aListener_in)
    {
        myToolBar = toolbar;
        //myFragmentId = fragmentId;
        //myDrawerLayout = drawerLayout;
        myDrawerEventsListener = aListener_in;
    }

    private void resize()
    {
        Resources resources = getResources();
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, resources.getDisplayMetrics());

        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams( (int)width , LinearLayout.LayoutParams.MATCH_PARENT);

        lp.gravity= Gravity.START;

        getView().setLayoutParams(lp);
    }

    //public void setUp()
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar, DrawerEventsListener aListener_in)
    {
        myContainer = getActivity().findViewById(fragmentId);
        myDrawerLayout = drawerLayout;
        myToolBar = toolbar;
        myDrawerEventsListener = aListener_in;

        myDrawerToggle = new ActionBarDrawerToggle(getActivity(), myDrawerLayout, myToolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    UBApplication.saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(myDrawerEventsListener != null)
                    myDrawerEventsListener.onSlide(slideOffset);
                if(Build.VERSION.SDK_INT >= 11)
                {
                    myToolBar.setAlpha(1 - slideOffset / 2);
                }
            }
        };

        myDrawerLayout.setDrawerListener(myDrawerToggle);
        myDrawerLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                myDrawerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState)
                {
                    myDrawerLayout.openDrawer(myContainer);
                }
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = UBApplication.readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, false);
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //setUp();

        myRecyclerDrawer = (RecyclerView) view.findViewById(R.id.drawerList);
        myAdapter = new AdapterDrawer(getActivity(), DrawerEntries.getFilterList(getActivity()));
        myRecyclerDrawer.setAdapter(myAdapter);
        myRecyclerDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerDrawer.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecyclerDrawer, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                myDrawerLayout.closeDrawer(GravityCompat.START);
                if(myDrawerEventsListener != null)
                    myDrawerEventsListener.onClick(myAdapter.getTag(position - 1));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public static Fragment newInstance()
    {
        FragmentDrawer f = new FragmentDrawer();

        return f;
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b)
        {

        }
    }
}
