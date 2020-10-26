package com.example.splashscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView title;
    ImageButton setting;

    RecyclerView recyclerView;
    RecyclerView.Adapter bookingAdapter;
    RecyclerView.LayoutManager ad_LayoutManager;

    ArrayList<String> HotelName = new ArrayList<String> (Arrays.asList("FUNHotel","Sultania Hotel","Sultania Hotel"));
    ArrayList<String> HotelDate = new ArrayList<String> (Arrays.asList("25 NOV WED - 28 NOV SAT","28 NOV WED - 30 NOV SAT","Date Expired"));
    ArrayList<Integer> HotelImages = new ArrayList<Integer>(Arrays.asList(R.drawable.img_my_booking_1,R.drawable.img_my_booking_2,R.drawable.img_my_booking_2));

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        title = view.findViewById(R.id.txtTitle);
        title.setText("My Booking");

        setting = view.findViewById(R.id.Setting);
        setting.setBackgroundResource(R.drawable.guest_ic_add);

        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        ad_LayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(ad_LayoutManager);
        bookingAdapter = new BookingAdapter(view.getContext(),HotelName,HotelDate,HotelImages);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(bookingAdapter);

        return view;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            HotelName.remove(viewHolder.getAdapterPosition());
            HotelDate.remove(viewHolder.getAdapterPosition());
            HotelImages.remove(viewHolder.getAdapterPosition());
            bookingAdapter.notifyDataSetChanged();
        }
    };
}