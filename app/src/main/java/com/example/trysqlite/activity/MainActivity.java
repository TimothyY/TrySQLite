package com.example.trysqlite.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trysqlite.GeneralUtility;
import com.example.trysqlite.R;
import com.example.trysqlite.adapter.SongRVAdapter;
import com.example.trysqlite.database.SongDAO;
import com.example.trysqlite.model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Song> songDataSet;

    RecyclerView rvSongs;
    RecyclerView.LayoutManager rvLayoutManager;
    SongRVAdapter songRVAdapter;

    //untuk volley
    RequestQueue queue;
    String url;

    Context mCtx;

    SongDAO songDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCtx = this;
        songDAO = new SongDAO();

        //isi data set untuk recyclerview dengan data dummy
        songDataSet = generateDummySongs();

        //kenali recyclerview dari xml
        rvSongs = findViewById(R.id.rvSongs);

        //memasang layout manajer ke recycler view
        rvLayoutManager = new LinearLayoutManager(this);
        rvSongs.setLayoutManager(rvLayoutManager);

        //pakai data dummy dulu
        //buat objek adapter dan hubungkan dengan activity dan dataset
        songRVAdapter = new SongRVAdapter(mCtx,songDataSet);
        //pasang adapter ke recycler view
        rvSongs.setAdapter(songRVAdapter);

        //untuk volley
        queue = Volley.newRequestQueue(this);
        url ="https://itunes.apple.com/search?term=chrisye";

        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray resultsSongs=null;
                        try {
                            resultsSongs= response.getJSONArray("results");
                            songDataSet = parseJSONArrayToSongs(resultsSongs);

                            //buat objek adapter dan hubungkan dengan activity dan dataset
                            songRVAdapter = new SongRVAdapter(mCtx,songDataSet);
                            //pasang adapter ke recycler view
                            rvSongs.setAdapter(songRVAdapter);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley",error.getLocalizedMessage());
                    }
                }

        );

        //jalankan request web service
        queue.add(myRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GeneralUtility util = new GeneralUtility();

        if(util.isOnline(mCtx)==false){
            songDataSet = songDAO.getSongs(mCtx);
            songRVAdapter = new SongRVAdapter(mCtx,songDataSet);
            rvSongs.setAdapter(songRVAdapter);
        }
    }

    public ArrayList<Song> generateDummySongs(){
        //siapkan kontainer data dummy
        ArrayList<Song> dummySongs = new ArrayList<>();

        //proses buat data dummy
        Song song01 = new Song("judul01","album01","artist01","albumArt01");
        dummySongs.add(song01);
        Song song02 = new Song("judul02","album01","artist01","albumArt01");
        dummySongs.add(song02);

        dummySongs.add(new Song("judul03","album02","artist01","albumArt02"));

        dummySongs.add(new Song("judul04","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul05","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul06","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul07","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul08","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul09","album02","artist01","albumArt02"));
        dummySongs.add(new Song("judul10","album02","artist01","albumArt02"));

        //kembalikan data dummy ke pemanggil
        return dummySongs;
    }

    //mengconvert json array yang di download ke kelas Song yang sudah dibuat
    public ArrayList<Song> parseJSONArrayToSongs(JSONArray results){
        ArrayList<Song> songsArrList = new ArrayList<>();
        Song newSong = null;
        for(int i=0;i<results.length();i++){
            //parse per JSON Object menjadi Song
            try {
                newSong = parseJSONObjToSong(results.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //tambahkan song tersebut ke database
            songDAO.addSong(mCtx,newSong);

            //tambahkan song tersebut ke arraylist
            songsArrList.add(newSong);
        }

        return songsArrList;
    }

    //parse JSONObject menjadi Song
    public Song parseJSONObjToSong(JSONObject resultItem){
        Song newSong = null;
        String judul = null, album = null, artist = null, albumArtURL = null;
        try {
            judul = resultItem.getString("trackName");
            album = resultItem.getString("collectionName");
            artist = resultItem.getString("artistName");
            albumArtURL = resultItem.getString("artworkUrl100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        newSong = new Song(judul,album,artist,albumArtURL);
        return newSong;
    }
}