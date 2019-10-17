package com.example.firebase.moiveguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frontmoviedetails extends YouTubeBaseActivity implements Serializable {
    public static final String imagebaseurl="https://image.tmdb.org/t/p/w500";
    TextView moviename,moviesummary,movierealisedate,movierating;
    ImageView movieimage;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    int movieposition;
    int movieid;
    String movietrailerkey;
    Button playtrailerbtn;
    List<Newresults> newresults=new ArrayList<>();
    public Api apiinterface;
    WebView videoWeb;
    public ArrayList<Result> detailedmovielist = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontmoviedetails);
        moviename=findViewById(R.id.moviename);
        moviesummary=findViewById(R.id.moviesummary);
        movierealisedate=findViewById(R.id.releasingdate);
        movierating=findViewById(R.id.movierating);
        movieimage =findViewById(R.id.frontmovieimage);
        playtrailerbtn=findViewById(R.id.btnplaytrailer);
        youTubePlayerView=findViewById(R.id.youtubevideoview);
        Intent intent=getIntent();
        movieid =intent.getIntExtra("movieid",0);
        movieposition=intent.getIntExtra("movieposition",0);
        Bundle bundle = intent.getExtras();
         detailedmovielist= (ArrayList) bundle.getParcelableArrayList("detailedmoivelist");
         moviename.setText(detailedmovielist.get(movieposition).getTitle());
            moviesummary.setText(detailedmovielist.get(movieposition).getOverview());
            String voteavg=String.valueOf(detailedmovielist.get(movieposition).getVoteAverage());
        movierating.setText(voteavg);

        movierealisedate.setText(detailedmovielist.get(movieposition).getReleaseDate());
        String imagefullpath=imagebaseurl + detailedmovielist.get(movieposition).getPosterPath();
        Glide.with(getApplicationContext())
                .load(imagefullpath)
                .fitCenter()
                .into(movieimage);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            apiinterface=retrofit.create(Api.class);
        Call<Movietrailer> call=apiinterface.getmovietrailer(movieid,"279a0ec28b79a9eddb605bb3fde3b5b6");
        call.enqueue(new Callback<Movietrailer>() {
            @Override
            public void onResponse(Call<Movietrailer> call, Response<Movietrailer> response) {
                if(response.isSuccessful())
                {
                    Movietrailer movietrailer=response.body();
                    newresults=movietrailer.getResults();
                    movietrailerkey=newresults.get(0).getKey();

                }
            }

            @Override
            public void onFailure(Call<Movietrailer> call, Throwable t) {

            }
        });

        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(movietrailerkey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        playtrailerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               /* String fullmovietrailerpath="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed"+movietrailerkey+"\" frameborder=\"0\" allowfullscreen></iframe>";
//
//                videoWeb = (WebView)findViewById(R.id.videoWebView);
//
//                videoWeb.getSettings().setJavaScriptEnabled(true);
//                videoWeb.setWebChromeClient(new WebChromeClient() {
//
//                } );
//                videoWeb.loadData( fullmovietrailerpath, "text/html" , "utf-8" );*/
                youTubePlayerView.initialize("AIzaSyAmDqihqCkRuQgAm9QNNrIJAjDWdZjZ21o",onInitializedListener);

            }
        });

    }
}
