package com.adzumi.moviesdb.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.adzumi.moviesdb.Constants;
import com.adzumi.moviesdb.R;
import com.adzumi.moviesdb.adapters.PopularMoviesAdapter;
import com.adzumi.moviesdb.models.Popular;
import com.adzumi.moviesdb.models.Result;
import com.adzumi.moviesdb.services.API_Instance;
import com.adzumi.moviesdb.services.RetrofitClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar) Toolbar myToolbar;
    private RecyclerView recyclerView;
    private PopularMoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.splash_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getPopularMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Keyword ...");

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Log.e("onQueryTextChange", "called");
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Intent intent = new Intent(HomeActivity.this, EventResultsActivity.class);
//                intent.putExtra("query", query);
//                startActivity(intent);
//                return false;
//            }
//        });
        return true;
    }

    public void getPopularMovies() {
        API_Instance service = RetrofitClient.getClient().create(API_Instance.class);
        Call<Popular> call = service.getPopularMovies(Constants.MOVIE_DB_API);
        Log.v("MY URL", String.valueOf(call.request().url()));

        call.enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {
                List<Result> movies = response.body().getResults();
                getCurrentPopularMovies(movies);
//                for (Event e : events) {
//                    Log.d(TAG, "Event " + e.getDescription().getText());
//                }
//              Gson gson = new Gson();
//              String eventsJson = gson.toJson(events);
//              Log.v(TAG, "MY JSON RESPONSE: " + eventsJson);
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCurrentPopularMovies(List<Result> movies) {
        recyclerView = findViewById(R.id.popularRecyclerView);
        adapter = new PopularMoviesAdapter(this,movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
