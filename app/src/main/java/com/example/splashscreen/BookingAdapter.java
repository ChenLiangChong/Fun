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

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    ArrayList<String> hotelName;
    ArrayList<String> hotelDate;
    ArrayList<Integer> hotelImages;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowDate;
        ImageView rowImage;
        ImageButton btn_next;
        View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.HotelName);
            rowDate = itemView.findViewById(R.id.DateTime);
            rowImage = itemView.findViewById(R.id.hotelImageView);
            btn_next = itemView.findViewById(R.id.imageButton3);
            mView = itemView;
        }
    }

    public BookingAdapter(Context context, ArrayList<String> hotelName, ArrayList<String> hotelDate, ArrayList<Integer> hotelImages) {
        this.context = context;
        this.hotelName = hotelName;
        this.hotelDate = hotelDate;
        this.hotelImages = hotelImages;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_hotel,parent,false);
        BookingAdapter.ViewHolder viewHolder = new BookingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(hotelName.get(position));
        holder.rowDate.setText(hotelDate.get(position));
        holder.rowImage.setBackgroundResource(hotelImages.get(position));
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
        return hotelName.size();
    }
}
