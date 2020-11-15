package com.susu.project_management;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    private ArrayList<Project> projectArrayList;
    private int count = -1;
    private ProjectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        RecyclerView mRecylerView = (RecyclerView) findViewById(R.id.project_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLinearLayoutManager);

        projectArrayList = new ArrayList<>();

        mAdapter = new ProjectAdapter(projectArrayList);
        mRecylerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecylerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecylerView.addItemDecoration(dividerItemDecoration);
        Button btnInsert = (Button)findViewById(R.id.btnInsertProject);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Project data = new Project(count+"", "날짜 : ", "설명 : ");
                projectArrayList.add(data);
                mAdapter.notifyDataSetChanged();

                Intent i = new Intent(ProjectActivity.this, CreateProjectActivity.class);
                startActivity(i);
            }
        });
    }
}