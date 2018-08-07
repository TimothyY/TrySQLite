package com.example.trysqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trysqlite.model.Song;

import java.util.ArrayList;

public class SongDAO {

    //use this funct. to add new record
    public void addSong(Context mCtx, Song song){

        MySQLiteHelper helper = new MySQLiteHelper(mCtx);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(helper.FIELD_MS_SONG_TITLE, song.title);
        cv.put(helper.FIELD_MS_SONG_ARTIST, song.artist);
        cv.put(helper.FIELD_MS_SONG_ALBUM, song.album);
        cv.put(helper.FIELD_MS_SONG_ART_URL, song.albumArtURL);

        db.insertWithOnConflict(
          helper.TABLE_MS_SONG,
          null, cv, SQLiteDatabase.CONFLICT_REPLACE
        );

        db.close();
    }

    //use this funct. to select record from db
    public ArrayList<Song> getSongs(Context mCtx){
        ArrayList<Song> songs = new ArrayList<>();

        MySQLiteHelper helper = new MySQLiteHelper(mCtx);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selectionString = null;
        String[] selectionArgs = null;

        Cursor resultCursor =
                db.query(
                        helper.TABLE_MS_SONG,
                        null,
                        selectionString,
                        selectionArgs,
                        null,
                        null,
                        null);
        while(resultCursor.moveToNext()){
//            String songTitle = resultCursor.getString(0);

            String songTitle =
                    resultCursor.getString(
                            resultCursor.getColumnIndex(helper.FIELD_MS_SONG_TITLE)
                    );
            String songArtist = resultCursor.getString(1);
            String songAlbum = resultCursor.getString(2);
            String songAlbumArt = resultCursor.getString(3);
            songs.add(new Song(songTitle,songAlbum,songArtist,songAlbumArt));
        }

        return songs;
    }
}
