package com.example.trysqlite;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class GeneralUtility {

    public boolean isOnline(Context mCtx) {
        ConnectivityManager conMgr =
                (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        boolean status=false;
        if(netInfo==null){
            status = false;
        }else{
            status = true;
        }
        if (status == false){
            new AlertDialog.Builder(mCtx)
                    .setTitle("Warning")
                    .setMessage("Tidak ada internet")
                    .setPositiveButton("OK", null).show();
        }
        return status;
    }
}
