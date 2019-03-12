package com.adzumi.moviesdb.services;

import com.adzumi.moviesdb.models.Popular;
import com.adzumi.moviesdb.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Instance {
    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String myAPIKey);
}
