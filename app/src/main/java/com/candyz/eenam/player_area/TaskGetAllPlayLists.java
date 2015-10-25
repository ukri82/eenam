package com.candyz.eenam.player_area;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.candyz.eenam.misc.VolleySingleton;
import com.candyz.eenam.model.Endpoints;
import com.candyz.eenam.model.PlayListItem;
import com.candyz.eenam.model.Requestor;
import com.candyz.eenam.model.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by u on 10.06.2015.
 */
public class TaskGetAllPlayLists extends AsyncTask<Void, Void, ArrayList<PlayListItem>>
{
    public interface GetAllPlayListsQueryListener
    {
        public void onPlayListsAvailable(ArrayList<PlayListItem> aPlayList_in);
    }


    private GetAllPlayListsQueryListener myListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String myUserId;

    public TaskGetAllPlayLists(GetAllPlayListsQueryListener aListener_in, String aUserId_in)
    {
        myUserId = aUserId_in;
        this.myListener = aListener_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<PlayListItem> doInBackground(Void... params)
    {
        JSONObject response = Requestor.request(requestQueue, Endpoints.getRequestUrlGetAllPlayLists(myUserId));
        return parse(response);
    }

    @Override
    protected void onPostExecute(ArrayList<PlayListItem> aList_in)
    {
        if (myListener != null)
        {
            myListener.onPlayListsAvailable(aList_in);
        }
    }

    public ArrayList<PlayListItem> parse(JSONObject response)
    {
        ArrayList<PlayListItem> listPlayLists = new ArrayList<>();
        if (response != null && response.length() > 0)
        {
            try
            {
                JSONArray arrayItems = response.getJSONArray("SongData");
                for (int i = 0; i < arrayItems.length(); i++)
                {

                    JSONObject currentItem = arrayItems.getJSONObject(i);

                    listPlayLists.add(new PlayListItem(Utils.get(currentItem, "id"),
                            Utils.get(currentItem, "pl_name"),
                            Utils.get(currentItem, "LastPlayedTime"),
                            Utils.get(currentItem, "LastPlayedSong"),
                            Utils.get(currentItem, "song_ids")
                    ));
                }

            }
            catch (JSONException e)
            {

            }
        }
        return listPlayLists;
    }

}