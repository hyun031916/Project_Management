package com.susu.project_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.logging.LogWrapper;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";
    private RecyclerView recyclerView;
    private ProjectAdapter mAdapter;
    private ArrayList<Project> projectArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private int count = -1;
    private DatabaseReference mDatabase;
    TextView project_page;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        database = FirebaseDatabase.getInstance();
        
        recyclerView = (RecyclerView)findViewById(R.id.project_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        projectArrayList = new ArrayList<>();

        mAdapter = new ProjectAdapter(projectArrayList);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Button btnInsert = (Button)findViewById(R.id.btnInsertProject);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Project data = new Project("이메일", count+"k", "날짜 : ", "설명 : ", "dd");
                projectArrayList.add(data);
                mAdapter.notifyDataSetChanged();

//                Intent i = new Intent(ProjectActivity.this, CreateProjectActivity.class);
//                startActivity(i);
            }
        });
//
////        database.getReference("project").child(stTitle);
//        Log.d(TAG, "onCreate: "+ stTitle);
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Project project = snapshot.getValue(Project.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "onCancelled: ", error.toException());
//            }
//        };
//        mDatabase.addValueEventListener(postListener);
//        Project project = new Project();
//        DatabaseReference ref = database.getReference("project").child(stTitle);
    }
}