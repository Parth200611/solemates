package com.example.solemates.comman;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.solemates.R;

public class Networkchangelistner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Networkdetails.isconnectedtointernet(context)){
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            View layout_dilog = LayoutInflater.from(context).inflate(R.layout.check_internet_connection,null);
            ad.setView(layout_dilog);

            AppCompatButton btnretry = layout_dilog.findViewById(R.id.btnretry);

            AlertDialog alertDialog = ad.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

            btnretry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }

        else {

        }
    }
}
