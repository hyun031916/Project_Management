package com.susu.project_management.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.susu.project_management.CProject;
import com.susu.project_management.Project;
import com.susu.project_management.ProjectAdapter;
import com.susu.project_management.R;
import com.susu.project_management.User;
import com.susu.project_management.UserAdapter;
import com.susu.project_management.ui.users.DashboardViewModel;

import java.util.ArrayList;
import java.util.Dictionary;

public class HomeFragment extends Fragment {

    private DashboardViewModel homeViewModel;
    private static final String TAG = "HomeFragment";
    private ArrayList<Project> projectArrayList;
    private int count = -1;
    private RecyclerView mRecyclerView;
    private ProjectAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String stEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.project_recycler_view);

        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(root.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        projectArrayList = new ArrayList<>();

        mAdapter = new ProjectAdapter(projectArrayList, getContext());
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        projectArrayList = new ArrayList<>();
        //유저 이메일 받아오기
        Bundle extra = getArguments();
        if(extra != null)
            stEmail = extra.getString("email");

        Button btnInsert = (Button)root.findViewById(R.id.btnInsertProject);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Project data = new Project(stEmail.toString(), count+"", "날짜 : ", "설명 : ", "유저");
                projectArrayList.add(data);
                mAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }
}