package com.example.firebase.moiveguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("movie/now_playing")
    Call<Movielist> getmovielist(@Query("api_key")String apikey,
                                 @Query("language") String lang,
                                 @Query("page") int pageno);
    @GET("search/movie")
    Call<Searchmovie> getsearchmovie(@Query("api_key")String apikey,
                                     @Query("language") String lang,
                                     @Query("query") String moviename,
                                     @Query("page") int pageno1,
                                     @Query("include_adult") boolean include);
    @GET("movie/{id}/videos")
    Call<Movietrailer> getmovietrailer(@Path("id" ) int movieid,
            @Query("api_key") String apikey);
}
//https://api.themoviedb.org/3/search/movie?api_key=279a0ec28b79a9eddb605bb3fde3b5b6&language=en-US&query=the%20avengers&page=1&include_adult=false