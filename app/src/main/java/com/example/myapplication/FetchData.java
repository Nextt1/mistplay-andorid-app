/**
 * @desc this class is used to make a network request to server and will return a ArrayList of Game Objects
 * @author Dhruvin Pipalia dhruvinhi@gmail.com
 */
package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FetchData {

    public void request(String query, int page, final Context context, final VolleyCallback callback) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            final String request_url = "http://18.191.233.166:3000/search?orderBy=rating&orderType=desc&page=" + page;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("query", query);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, request_url, new Response.Listener<String>() {
                ArrayList<Game> games = new ArrayList<Game>();

                @Override
                public void onResponse(String response) {
                    try {
                        Log.i("URL", request_url);
                        JSONObject obj = new JSONObject(response);
                        String name = "data";
                        JSONObject data = obj.getJSONObject(name);

                        JSONArray results = data.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject row = results.getJSONObject(i);
                            games.add(new Game("", row.getString("imgURL"), row.getString("subgenre"), row.getString("title"), "", (float) row.getDouble("rating"), 0));
                        }

                        callback.onSuccess(games);
                    } catch (JSONException e) {
                        Log.i("LOG_VOLLEY", e.getMessage());
                        e.printStackTrace();
                    }
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

