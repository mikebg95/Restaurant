package com.example.mike.restaurant;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuItemsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    // initialize local variables
    private Context context;
    private Callback activity;

    // api request performs callback to one of these
    public interface Callback {
        void gotMenuItems(ArrayList<MenuItem> menuItems);
        void gotMenuItemsError(String message);
    }

    // constructor for this class
    public MenuItemsRequest(Context context) {
        this.context = context;
    }

    void getMenuItems(Callback activity, String category) {

        // initialize queue, request json object and add to queue
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu?category=" + category, null, this, this);
        queue.add(jsonObjectRequest);

        // save activity to local variable
        this.activity = activity;
    }

    // when api request not succeeded
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotMenuItemsError(error.getMessage());
    }

    // when api request succeeded
    @Override
    public void onResponse(JSONObject response) {
        try {

            // creae arraylist to store menu items
            ArrayList<MenuItem> menuItems = new ArrayList<>();

            // get json array
            JSONArray itemsArray = response.getJSONArray("items");

            // for each menu item in json array
            for (int i = 0; i < itemsArray.length(); i++) {
                // save menu item as variable
                JSONObject item = itemsArray.getJSONObject(i);

                // get all needed characteristics from menu item
                String name = item.getString("name");
                String description = item.getString("description");
                String imageUrl = item.getString("image_url");
                double price = item.getDouble("price");
                String category = item.getString("category");

                // create new MenuItem and add to array of items
                menuItems.add(new MenuItem(name, description, imageUrl, price, category));
            }

            // send array of menu items back to activity
            activity.gotMenuItems(menuItems);

        }
        catch (JSONException e) {
            // send error message back to activity
            activity.gotMenuItemsError(e.getMessage());
            e.printStackTrace();
        }

    }
}
