package com.example.stampsaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity implements MyAdapter.OnClickListener,MyAdapter.OnLongClickListener {
    RecyclerView rv;
ArrayList<Model> modelArrayList;
DBmain DB;
SQLiteDatabase db;
Dialog dialog,dialog2;
TextView rand;
MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        rv=findViewById(R.id.rv);
        DB=new DBmain(this);
        rand=findViewById(R.id.rand);
        dialog=new Dialog(DisplayData.this);
        dialog.setContentView(R.layout.longpressdialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog2=new Dialog(DisplayData.this);
        dialog2.setContentView(R.layout.deletedialog);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
       modelArrayList=new ArrayList<>();
        if(modelArrayList.isEmpty()){
            rand.setVisibility(View.VISIBLE);}
        myAdapter=new MyAdapter(this, modelArrayList,this,this);
        rv.setAdapter(myAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        displaydata();
    }

    private void displaydata() {
        Cursor cursor=DB.getdata();
        if(cursor.getCount()==0){
            rand.setVisibility(View.VISIBLE);
            Toast.makeText(DisplayData.this, "Wow, so empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                modelArrayList.add(new Model(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)));
                rand.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(DisplayData.this,String.valueOf(modelArrayList.get(position).getSlink()),Toast.LENGTH_SHORT).show();
        String web= String.valueOf(modelArrayList.get(position).getSlink());
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(web));
        startActivity(i);
    }

    @Override
    public void onItemLongClick(int position) {
       dialog.show();
        FloatingActionButton yes,no;
        yes=dialog.findViewById(R.id.yesbt);
        no=dialog.findViewById(R.id.nobt);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.delete(String.valueOf(modelArrayList.get(position).getStitle()));
                modelArrayList.remove(position);
                myAdapter.notifyItemRemoved(position);
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onRestart() {
        Intent i=getIntent();
        String action=i.getAction();
        String type = i.getType();
        if(Intent.ACTION_SEND.equals(action)&&type!=null){
            if("text/plain".equals(type)){
                startActivity(new Intent(DisplayData.this,MainActivity.class));
                finish();
            }
        }
        super.onRestart();
    }

    @Override
    protected void onPostResume() {
        Intent i=getIntent();
        String action=i.getAction();
        String type = i.getType();
        if(Intent.ACTION_SEND.equals(action)&&type!=null){
            if("text/plain".equals(type)){
                startActivity(new Intent(DisplayData.this,MainActivity.class));
                finish();
            }
        }
        super.onPostResume();
    }
}