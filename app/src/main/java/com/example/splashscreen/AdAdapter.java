package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {

    Context context;
    ArrayList<String> adDescriptionList;
    ArrayList<Integer> adImages;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        ImageView rowImage;
        ImageButton btn_next;
        View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.adDescription);
            rowImage = itemView.findViewById(R.id.adImageView);
            btn_next = itemView.findViewById(R.id.imageButton3);
            mView = itemView;
        }
    }

    public AdAdapter(Context context,ArrayList<String> adDescriptionList,ArrayList<Integer> adImages){
        this.context = context;
        this.adDescriptionList = adDescriptionList;
        this.adImages = adImages;
    }

    @NonNull
    @Override
    public AdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_ad,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(adDescriptionList.get(position));
        holder.rowImage.setBackgroundResource(adImages.get(position));
        holder.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(),AdDetail.class);
                holder.mView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return adDescriptionList.size();
    }
}
