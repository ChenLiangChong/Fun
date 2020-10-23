package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView textUsername,textEmail;
    Button btnLogout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    View v;
    RecyclerView recyclerView;
    RecyclerView.Adapter AdAdapter;
    RecyclerView.LayoutManager ad_LayoutManager;

    ArrayList<String> adDescriptionList = new ArrayList<String> (Arrays.asList("hehehehe","hahahhahahah","kerokerokero"));

    ArrayList<Integer> adImages = new ArrayList<Integer>(Arrays.asList(R.drawable.img_ad_1,R.drawable.img_ad_2,R.drawable.img_ad_1));

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initializati11on parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textUsername = (TextView)view.findViewById(R.id.textUsername);
        textEmail = (TextView)view.findViewById(R.id.textEmail);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);
        mAuth= FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        ad_LayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(ad_LayoutManager);
        AdAdapter = new AdAdapter(view.getContext(),adDescriptionList,adImages);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(AdAdapter);

        if (mUser!=null){
            textEmail.setText(mUser.getEmail());
            textUsername.setText(mUser.getDisplayName());
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adDescriptionList.remove(viewHolder.getAdapterPosition());
            adImages.remove(viewHolder.getAdapterPosition());
            AdAdapter.notifyDataSetChanged();
        }
    };
}