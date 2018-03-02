package com.example.akoumare.testandroidnatif;

/**
 * Created by akoumare on 02/03/2018.
 */
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbService {

   // public static final String ENDPOINT = "http://www.omdbapi.com";

    @GET("/")
    Call<Films> searchFilms(@Query("apikey") String apiKey,@Query("s") String query);

}
