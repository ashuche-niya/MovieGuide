package com.example.firebase.moiveguide;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public Api apiintrerface;
    Movielist largemovielist;
    ArrayList<Result> movielist=new ArrayList<>();
    ArrayList<Result> searchmovielist=new ArrayList<>();
    List<moviemodel> mainmovielist=new ArrayList<>();

    RecyclerView recyclerView;
    movielistadapter movielistadapter;
    String searchformovie;
    public static final String imagebaseurl="https://image.tmdb.org/t/p/w500";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
       Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiintrerface=retrofit.create(Api.class);
        Call<Movielist> call =apiintrerface.getmovielist("279a0ec28b79a9eddb605bb3fde3b5b6","en-US",1);
        call.enqueue(new Callback<Movielist>() {
            @Override
            public void onResponse(Call<Movielist> call, Response<Movielist> response) {
                if(response.isSuccessful())
                {
                   largemovielist=response.body();
                   movielist= (ArrayList<Result>) largemovielist.getResults();
                    if(movielist.size()!=0) {
                        for (int i = 0; i < movielist.size(); i++) {
                            String fullimagepath = imagebaseurl + movielist.get(i).getPosterPath();
                            mainmovielist.add(new moviemodel(fullimagepath));
                        }
                    }
                    movielistadapter = new movielistadapter(mainmovielist, getApplicationContext());
                    recyclerView.setAdapter(movielistadapter);
                    movielistadapter.setOnItemClickListener(new movielistadapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(getApplicationContext(), frontmoviedetails.class);
                            intent.putExtra("movieposition", position).putExtra("detailedmoivelist", movielist).putExtra("movieid",movielist.get(position).getId());
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Movielist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"moive can't looad",Toast.LENGTH_SHORT).show();
            }
        });
       //searchmovie();
    }
    /*public void searchmovie()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiintrerface=retrofit.create(Api.class);
        Call<Searchmovie> call =apiintrerface.getsearchmovie("279a0ec28b79a9eddb605bb3fde3b5b6","en-US","the avengers",1,false);
        call.enqueue(new Callback<Searchmovie>() {
            @Override
            public void onResponse(Call<Searchmovie> call, Response<Searchmovie> response) {
                if(response.isSuccessful())
                {
                    Searchmovie searchmovie=response.body();
                    searchmovielist= (ArrayList<Result>) searchmovie.getResults();
                    Intent intent = new Intent(getApplicationContext(), frontmoviedetails.class);
                    intent.putExtra("movieposition", 1).putExtra("detailedmoivelist", searchmovielist);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Searchmovie> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"no movie",Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.menusearch);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                //apiintrerface=retrofit.create(Api.class);
                Call<Searchmovie> call =apiintrerface.getsearchmovie("279a0ec28b79a9eddb605bb3fde3b5b6","en-US",query,1,false);
                call.enqueue(new Callback<Searchmovie>() {
                    @Override
                    public void onResponse(Call<Searchmovie> call, Response<Searchmovie> response) {
                        if(response.isSuccessful())
                        {
                            Searchmovie searchmovie=response.body();
                            searchmovielist= (ArrayList<Result>) searchmovie.getResults();
                            Intent intent = new Intent(getApplicationContext(), frontmoviedetails.class);
                            intent.putExtra("movieposition", 0).putExtra("detailedmoivelist", searchmovielist).putExtra("movieid",searchmovielist.get(0).getId());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Searchmovie> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"no movie",Toast.LENGTH_SHORT).show();

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
//https://api.themoviedb.org/3/search/movie?api_key=279a0ec28b79a9eddb605bb3fde3b5b6&language=en-US&query=the%20avengers&page=1&include_adult=false