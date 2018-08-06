package com.example.android.gsonsample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.gsonsample.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movieDatabase;
    private Context context;

    public MovieAdapter(Context context, ArrayList<Movie> movieDatabase) {
        this.layoutInflater = LayoutInflater.from(context);
        this.movieDatabase = movieDatabase;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie currentMovie = movieDatabase.get(position);

            holder.title.setText(currentMovie.getTitle());
            holder.rate.setText(currentMovie.getVoteAverage() + "");
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + currentMovie.getPosterPath()).into(holder.imageView);

            //check if the description is defined by findViewById in MainActivity if not then it is defined in DetailActivity
        if (holder.description != null) {
            holder.description.setText(currentMovie.getOverview());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieIntent = new Intent(view.getContext(),DetailActivity.class);
                movieIntent.putExtra("Title" , currentMovie.getTitle());
                movieIntent.putExtra("Description",currentMovie.getOverview());
                movieIntent.putExtra("date",currentMovie.getReleaseDate());
                movieIntent.putExtra("image",currentMovie.getPosterPath());
                movieIntent.putExtra("rate", currentMovie.getVoteAverage());
                Log.i("rating", "onCreate: "+currentMovie.getVoteAverage());

                view.getContext().startActivity(movieIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDatabase == null ? 0 : movieDatabase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView rate;
        private ImageView imageView;
        private TextView description;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            imageView = itemView.findViewById(R.id.image);
            description = itemView.findViewById(R.id.description2);
            date = itemView.findViewById(R.id.release);

        }
    }
}
