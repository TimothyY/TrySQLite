package com.example.trysqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String name = "dbMusic";
    public static final int version = 1;
    public static final SQLiteDatabase.CursorFactory factory = null;

    public static String TABLE_MS_SONG = "msSong";
    public static String FIELD_MS_SONG_TITLE = "songTitle";
    public static String FIELD_MS_SONG_ARTIST = "songArtist";
    public static String FIELD_MS_SONG_ALBUM = "songAlbum";
    public static String FIELD_MS_SONG_ART_URL = "songArtURL";

    public static String TABLE_MS_USER = "msUser";
    public static String FIELD_MS_USER_EMAIL = "email";
    public static String FIELD_MS_USER_PASSWORD = "password";

    public MySQLiteHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qCreateMsSong =
                "CREATE TABLE IF NOT EXISTS `"+ TABLE_MS_SONG +"` (\n" +
                "\t`"+FIELD_MS_SONG_TITLE+"`\tTEXT,\n" +
                "\t`"+FIELD_MS_SONG_ARTIST+"`\tTEXT,\n" +
                "\t`"+FIELD_MS_SONG_ALBUM+"`\tTEXT,\n" +
                "\t`"+FIELD_MS_SONG_ART_URL+"`\tTEXT\n" +
                ");";
        db.execSQL(qCreateMsSong);

        String qCreateMsUser =
                "CREATE TABLE IF NOT EXISTS `"+ TABLE_MS_USER +"` (\n" +
                        "\t`"+FIELD_MS_USER_EMAIL+"`\tTEXT NOT NULL PRIMARY KEY UNIQUE,\n" +
                        "\t`"+FIELD_MS_USER_PASSWORD+"`\tTEXT\n" +
                        ");";
        db.execSQL(qCreateMsUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
