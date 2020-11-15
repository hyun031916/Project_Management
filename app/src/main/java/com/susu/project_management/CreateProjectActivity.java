package com.susu.project_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Board> board1 = new ArrayList<Board>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_activity);

        recyclerView = (RecyclerView) findViewById(R.id.listview1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        BoardAdapter adapter = new BoardAdapter(this);

        recyclerView.setAdapter(adapter);

        //등록 레이아웃 생성
        board1.add(new Board("", "", "", "",1));

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("project").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Board board = snapshot.getValue(Board.class);
                board1.add(board);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}