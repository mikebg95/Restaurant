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



public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    // send callback to one of these
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // constructor for this class
    public CategoriesRequest(Context context) {
        this.context = context;
    }

    void getCategories(Callback activity) {
        // create volley queue, request json object and add to queue
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "https://resto.mprog.nl/categories",
                null,
                this,
                this);
        queue.add(jsonObjectRequest);

        // save callback activity in local variable
        this.activity = activity;

    }

    // when api request gone wrong
    @Override
    public void onErrorResponse(VolleyError error) {

        // print error to screen via toast
        activity.gotCategoriesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            // create empty arraylist to store categories in
            ArrayList<String> categories = new ArrayList<>();

            // get json object and add categories to arraylist
            JSONArray categoriesArray = response.getJSONArray("categories");
            for (int i = 0; i < categoriesArray.length(); i++) {
                categories.add(categoriesArray.getString(i));
            }

            // perform callback to CategoriesActivity
            activity.gotCategories(categories);
        }
        // exception gets handled here
        catch (JSONException e){

            // send error message through callback
            activity.gotCategoriesError(e.getMessage());
        }
    }
}
