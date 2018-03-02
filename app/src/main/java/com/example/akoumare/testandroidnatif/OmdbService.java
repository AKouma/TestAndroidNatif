package com.example.akoumare.testandroidnatif;

/**
 * Created by akoumare on 02/03/2018.
 */
import java.util.Lisst;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.Call;

public interface OmdbService {

   // public static final String ENDPOINT = "http://www.omdbapi.com";

    @GET("/")
    Call<Films> searchFilms(@Query("s") String query);

}
