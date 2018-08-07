package com.example.trysqlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trysqlite.R;
import com.example.trysqlite.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongRVAdapter extends RecyclerView.Adapter{

    Context mCtx;
    ArrayList<Song> songDataSet;

    //constructor
    public SongRVAdapter(Context ctx, ArrayList<Song> songs){
        mCtx = ctx;
        songDataSet = songs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).
                inflate(R.layout.song_card,parent,false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //mendapatkan data per posisi di layout dari arraylist
        final Song song = songDataSet.get(position);

        //memasang data ke view widget per posisi
        ((SongViewHolder)holder).llCardSong.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mCtx, song.title+" layout is long clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ((SongViewHolder)holder).tvTitle.setText(song.title);
        ((SongViewHolder)holder).tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, song.title+" is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ((SongViewHolder)holder).tvAlbum.setText(song.album);
        ((SongViewHolder)holder).tvTitle.setText(song.title);
        Picasso.get().load(song.albumArtURL).into(((SongViewHolder)holder).ivAlbumArt);

    }

    @Override
    public int getItemCount() {
        //mendapatkan jumlah total data yang masuk ke recyclerview
        return songDataSet.size();
    }


    //Ini kelas yang menampung layout song per baris
    public class SongViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llCardSong;
        TextView tvTitle, tvAlbum, tvArtist;
        ImageView ivAlbumArt;

        public SongViewHolder(View itemView) {
            super(itemView);
            llCardSong = itemView.findViewById(R.id.llCardSong);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            tvAlbum = itemView.findViewById(R.id.tvSongAlbum);
            tvArtist = itemView.findViewById(R.id.tvSongArtist);
            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
        }
    }

}
