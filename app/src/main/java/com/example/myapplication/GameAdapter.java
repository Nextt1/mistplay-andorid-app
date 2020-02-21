/**
 * @desc this is an adapter for recyclerView to populate the games data
 * @author Dhruvin Pipalia dhruvinhi@gmail.com
 */
package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<Game> mGames;

    public GameAdapter(List<Game> games) {
        mGames= games;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.game_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GameAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Game game = mGames.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        ImageView imageView = viewHolder.imageView;
        TextView infoTextView = viewHolder.infoTextView;

        textView.setText(game.getTitle());
        infoTextView.setText(game.getRating() + " | " + game.getSubgenre());
        new ImageLoadTask(game.getLogo(), imageView).execute();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGames.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView infoTextView;
        public ImageView imageView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.game_name);
            infoTextView = (TextView) itemView.findViewById(R.id.game_info);
            imageView = (ImageView) itemView.findViewById(R.id.game_logo);
        }
    }
}
