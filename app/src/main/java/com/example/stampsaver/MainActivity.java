package com.example.stampsaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
Dialog dialog;
    EditText ettitle, etstamp;
    FloatingActionButton  cancelbt,savebt;
    TextView txlink;
    DBmain DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB=new DBmain(this);
        dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_lay);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.setCancelable(false);

        txlink=dialog.findViewById(R.id.link);
        ettitle = dialog.findViewById(R.id.title);
        etstamp = dialog.findViewById(R.id.stamp);
        savebt = dialog.findViewById(R.id.savebt);
        cancelbt=dialog.findViewById(R.id.cancelbt);
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ettitle.getText().toString().isEmpty()){
                    ettitle.setError("Title can't be blank");
                }
                else if(etstamp.getText().toString().isEmpty()){
                    etstamp.setError("Time stamp can't be blank");
                }
                else if(etstamp.getText().toString().length()>8||etstamp.getText().toString().length()<5||etstamp.getText().toString().length()==7){
                    etstamp.setError("Invalid time stamp");
                }
                else{
                    String title=ettitle.getText().toString();
                    String stamp=etstamp.getText().toString();
                    String lnk="https://m.youtube.com/watch";
                    String thumb="https://img.youtube.com/vi/";
                    if(etstamp.getText().toString().length()==5){
                        lnk=lnk+"?t="+etstamp.getText().toString().substring(0,2)+"m"+etstamp.getText().toString().substring(3)+"s&v="+txlink.getText().toString().substring(23)+"&feature=youtu.be";
                        thumb=thumb+txlink.getText().toString().substring(23)+"/0.jpg";
                    }
                    else{
                        lnk=lnk+"?t="+etstamp.getText().toString().substring(0,2)+"h"+etstamp.getText().toString().substring(3,5)+"m"+etstamp.getText().toString().substring(6)+"s&v="+txlink.getText().toString().substring(23)+"&feature=youtu.be";
                        thumb=thumb+txlink.getText().toString().substring(23)+"/0.jpg";
                    }

                    Boolean checkinsertdata = DB.insertuserdata(title,lnk,stamp,thumb);
                    if(checkinsertdata==true){
                        Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,DisplayData.class));
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Something wrong, try again", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        cancelbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DisplayData.class));
                finish();
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }

}


    private void handleSendText(Intent intent) {
        dialog.show();
        txlink.setText("Link: "+intent.getStringExtra(Intent.EXTRA_TEXT));
    }

    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }
        super.onRestart();
    }
}