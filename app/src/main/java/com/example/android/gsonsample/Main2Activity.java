package com.example.android.gsonsample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.gsonsample.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycle_view2);
        progressBar = findViewById(R.id.progressbar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(0, null, this).forceLoad(); // this means belong to LoaderCallback
            progressBar.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("page",1);


    }

    private boolean isConnected()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
           return connected = true;
        }
        else
            return connected = false;
    }
    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        // int page = bundle.getInt()
        return new MovieAsyncTask(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movies) {
        if (movies.isEmpty())
        {

        }else {
            MovieAdapter movieAdapter = new MovieAdapter(Main2Activity.this, (ArrayList<Movie>) movies);
            recyclerView.setAdapter(movieAdapter);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }

}
