/**
 * @desc this is the main activity class which will manage the home screen.
 * @author Dhruvin Pipalia dhruvinhi@gmail.com
 */
package com.example.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    ArrayList<Game> games;
    FetchData fetchData;
    Context context;
    int page = 1;
    String query;
    GameAdapter adapter;

    public void setGamesData(ArrayList<Game> games){
        this.games = games;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (EditText) findViewById(R.id.searchGameText);
        final RecyclerView rvGames = (RecyclerView) findViewById(R.id.game_list);

        games = new ArrayList<Game>();
        adapter = new GameAdapter(games);

        rvGames.setAdapter(adapter);

        rvGames.setLayoutManager(new LinearLayoutManager(this));

        rvGames.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;

                    fetchData.request(query, page, context, new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Game> g) {
                            if(g.size() == 0){
                                Toast.makeText(MainActivity.this, "No More Results", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Loading More Results", Toast.LENGTH_LONG).show();
                                games.addAll(g);
                                adapter.notifyItemInserted(games.size() - 1);
                            }
                        }
                    });


                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("BeforeTextChange", s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChanged", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChanged", s.toString());
                fetchData = new FetchData();
                page = 1;
                query = s.toString();
                fetchData.request(query, page, context, new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Game> g) {
                        games = g;
                        adapter = new GameAdapter(games);
                        rvGames.swapAdapter(adapter, false);

                    }
                });
            }
        });
    }
}
