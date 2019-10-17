package com.example.firebase.moiveguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class movielistadapter extends RecyclerView.Adapter<movielistadapter.movielistviewholder> {
    List<moviemodel> movielist;
    Context context;
    OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }
    public movielistadapter(List<moviemodel> movielist, Context context) {
        this.movielist = movielist;
        this.context = context;
    }

    @NonNull
    @Override
    public movielistadapter.movielistviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gridviewbasiclayout, viewGroup, false);
        return new movielistviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movielistadapter.movielistviewholder movielistviewholder, final int i) {
        Glide.with(context)
                .load(movielist.get(i).getImagepath())
                .centerCrop()
                .into(movielistviewholder.movieimage);



movielistviewholder.movieimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                {
                    onItemClickListener.onItemClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return movielist.size();
    }

    public class movielistviewholder extends RecyclerView.ViewHolder {
        //TextView moviename;
        ImageView movieimage;

        public movielistviewholder(@NonNull View itemView) {
            super(itemView);
            movieimage = itemView.findViewById(R.id.movieimage);
        }
    }
}
