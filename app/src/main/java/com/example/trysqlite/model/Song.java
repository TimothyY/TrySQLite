package com.example.trysqlite.model;

public class Song {

    public String title,album,artist,albumArtURL;

    //constructor
    public Song(String title, String album, String artist, String albumArtURL){
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.albumArtURL = albumArtURL;
    }

}
