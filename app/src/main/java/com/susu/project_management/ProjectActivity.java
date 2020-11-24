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
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        
        recyclerView = (RecyclerView)findViewById(R.id.project_recycler_view);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        projectArrayList = new ArrayList<>();   //Proejct 객체를 어댑터에 담을 어레이 리스트

        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");
        database = FirebaseDatabase.getInstance();  //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("project");   //DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터 받아오는 곳
                projectArrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                Log.d(TAG, "onDataChange: "+snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    //반복문으로 데이터 list 추출
//                    String email = snapshot.child("email").getValue(String.class);
//                    String title = snapshot.child("title").getValue(String.class);
//                    String date = snapshot.child("date").getValue(String.class);
//                    String description = snapshot.child("description").getValue(String.class);
//                    String user = snapshot.child("with").getValue(String.class);
//
//                    Log.d(TAG, "onDataChange: "+email+title+date+description+user);
                    if(dataSnapshot.child("email").getValue(String.class).equals(email)) {
                        String key = dataSnapshot.getKey();
                        Project project = dataSnapshot.getValue(Project.class); //만들어둔 Project 객체 데이터 담기
                        Log.d(TAG, "onDataChange: " + key);
                        projectArrayList.add(project);  //담은 데이터를 배열 리스트에 넣어 리사이클러뷰에 보낼 준비
                    }
                }
                mAdapter.notifyDataSetChanged(); //리스트 저장 및 새로고침 시 반영됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mAdapter = new ProjectAdapter(projectArrayList, this);
        recyclerView.setAdapter(mAdapter);
        Button btnInsert = (Button)findViewById(R.id.btnInsertProject);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             count++;
////                Project data = new Project("이메일", count+"k", "날짜 : ", "설명 : ", "dd");
////                projectArrayList.add(data);
////                mAdapter.notifyDataSetChanged();   count++;
//                Project data = new Project("이메일", count+"k", "날짜 : ", "설명 : ", "dd");
//                projectArrayList.add(data);
//                mAdapter.notifyDataSetChanged();

                Intent i = new Intent(ProjectActivity.this, CreateProjectActivity.class);
                startActivity(i);
            }
        });
    }
}