package com.example.stampsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Model> modelArrayList;

    private OnClickListener monClickListener;

    private OnLongClickListener monLongClickListener;
    public MyAdapter(Context context, ArrayList<Model> modelArrayList, OnClickListener onClickListener,OnLongClickListener onLongClickListener) {
        this.context = context;
        this.modelArrayList=modelArrayList;
        this.monClickListener=onClickListener;
        this.monLongClickListener=onLongClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemlay,parent,false);

        return new MyViewHolder(v,monClickListener,monLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.title.setText("Title: "+String.valueOf(modelArrayList.get(position).getStitle()));
        holder.link.setText(String.valueOf(modelArrayList.get(position).getSlink()));
        holder.stamp.setText("Timestamp: "+String.valueOf(modelArrayList.get(position).getSstamp()));
        String url=String.valueOf(modelArrayList.get(position).getSthumb());
        if(url.length()!=0)
        Glide.with(context).load(url).into(holder.imageView);
        else
            holder.imageView.setImageResource(R.drawable.ic_yticon);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title,link,stamp;
        ImageView imageView;
        CardView cardView;
        OnClickListener onClickListener;
        OnLongClickListener onLongClickListener;
        public MyViewHolder(@NonNull View itemView, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
            super(itemView);
            title=itemView.findViewById(R.id.titleitem);
            link=itemView.findViewById(R.id.linkitem);
            stamp=itemView.findViewById(R.id.stampitem);
            cardView=itemView.findViewById(R.id.cardView);
            imageView=itemView.findViewById(R.id.thumbnailitem);
            this.onClickListener=onClickListener;
            itemView.setOnClickListener(this);
           this.onLongClickListener=onLongClickListener;
           itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(getAdapterPosition());

        }


        @Override
        public boolean onLongClick(View view) {
            onLongClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
    public interface OnClickListener{
        void onItemClick(int position);

    }
    public interface OnLongClickListener{
        void onItemLongClick(int position);
    }
}
