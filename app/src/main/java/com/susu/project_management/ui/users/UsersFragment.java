package com.susu.project_management.ui.users;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.susu.project_management.Chat;
import com.susu.project_management.MyAdapter;
import com.susu.project_management.R;
import com.susu.project_management.User;
import com.susu.project_management.UserAdapter;

import java.util.ArrayList;

public class UsersFragment extends Fragment {
    private static final String TAG = "UsersFragment";
    private DashboardViewModel dashboardViewModel;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    UserAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<User> userArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        SharedPreferences sharedPref = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String stEmail = sharedPref.getString("email", "");

        userArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        String[] myDataset = {"test1", "test2", "test3"};
        mAdapter = new UserAdapter(userArrayList, stEmail);
        recyclerView.setAdapter(mAdapter);

        String stUid = user.getEmail();
        DatabaseReference ref = database.getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                    User user = dataSnapshot1.getValue(User.class);
                    Log.d(TAG, "user: "+ user.getEmail());
                    userArrayList.add(user);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}