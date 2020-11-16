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
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    private ArrayList<Project> projectArrayList;
    private int count = -1;
    private ProjectAdapter mAdapter;
    TextView project_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        RecyclerView mRecylerView = (RecyclerView) findViewById(R.id.project_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLinearLayoutManager);
        project_page = (TextView)findViewById(R.id.project_page);

//        //유저 이메일 받아오기
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("userId");
//        project_page.setText(name+"님의 프로젝트 생성 페이지");
//        projectArrayList = new ArrayList<>();

        mAdapter = new ProjectAdapter(projectArrayList);
        mRecylerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecylerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecylerView.addItemDecoration(dividerItemDecoration);
        Button btnInsert = (Button)findViewById(R.id.btnInsertProject);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;
//                Project data = new Project(count+"", "날짜 : ", "설명 : ");
//                projectArrayList.add(data);
//                mAdapter.notifyDataSetChanged();

                Intent i = new Intent(ProjectActivity.this, CreateProjectActivity.class);
                startActivity(i);
            }
        });
    }
}