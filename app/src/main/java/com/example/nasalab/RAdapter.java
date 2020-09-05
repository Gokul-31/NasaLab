package com.example.nasalab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RAdapter extends RecyclerView.Adapter<RAdapter.RViewHolder> {

    ArrayList<POJO_.C1_Collection.C2_Item> items;
    Context context;
    OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public static class RViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView type;

        public RViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.card_img);
            type=itemView.findViewById(R.id.card_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public RAdapter(Context con,ArrayList<POJO_.C1_Collection.C2_Item> items1) {
        items = items1;
        context=con;
    }

    @NonNull
    @Override
    public RAdapter.RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_card, parent, false);
        RAdapter.RViewHolder tvh = new RAdapter.RViewHolder(v,mListener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RAdapter.RViewHolder holder, final int position) {

        if(items.get(position).getData().get(0).getMedia_type().equals("image")){
            holder.type.setText("Image");
        }
        else{
            holder.type.setText("Video");
        }

        String t= items.get(position).getLinks().get(0).getHref();

        Picasso.with(context)
                .load(t)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}