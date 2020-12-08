package com.susu.project_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    Button btnFinish;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnSend;
    EditText etText;
    String email, title;
    FirebaseDatabase database;
    ArrayList<Chat> chatArrayList;
    String datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();

        chatArrayList = new ArrayList<>();  //객체를 어댑터에 담을 어레이 리스트

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        title = intent.getExtras().getString("title");
        btnSend = (Button) findViewById(R.id.btnSend);

        etText = (EditText) findViewById(R.id.etText);

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(chatArrayList, this, email);
        Log.d(TAG, "onCreate: "+email);
        recyclerView.setAdapter(mAdapter);


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                chatArrayList.clear();
                if(!dataSnapshot.getKey().equals("date") && !dataSnapshot.getKey().equals("description")
                        && !dataSnapshot.getKey().equals("email")&& !dataSnapshot.getKey().equals("title")
                        && !dataSnapshot.getKey().equals("with")) {
                    String commentKey = dataSnapshot.getKey();
                    Log.d(TAG, "1:" + commentKey);
                    Chat chat = dataSnapshot.getValue(Chat.class);  //만들어둔 Chat 객체 데이터 담기
                    String stEmail = chat.getEmail();
                    String stText = chat.getText();
                    Log.d(TAG, "stEmail: " + stEmail);
                    Log.d(TAG, "onChildAdded: " + stText);
                    chatArrayList.add(chat);    //담은 데이터를 배열 리스트에 넣어 리사이클러뷰에 보낼 준비
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());

                chatArrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                Log.d(TAG, "onDataChange: "+snapshot.getValue().toString());
                if(!snapshot.getKey().equals("date") && !snapshot.getKey().equals("description")
                        && !snapshot.getKey().equals("email")&& !snapshot.getKey().equals("title")
                        && !snapshot.getKey().equals("with")) {
                    String commentKey = snapshot.getKey();
                    Log.d(TAG, "1:" + commentKey);
                    Chat chat = snapshot.getValue(Chat.class);  //만들어둔 Chat 객체 데이터 담기
                    String stEmail = chat.getEmail();
                    String stText = chat.getText();
                    Log.d(TAG, "stEmail: " + stEmail);
                    Log.d(TAG, "onChildAdded: " + stText);
                    chatArrayList.add(chat);    //담은 데이터를 배열 리스트에 넣어 리사이클러뷰에 보낼 준비
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference ref = database.getReference("project").child(title);
        ref.addChildEventListener(childEventListener);
        mAdapter = new MyAdapter(chatArrayList, this, email);
        recyclerView.setAdapter(mAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText = etText.getText().toString();
                Toast.makeText(ChatActivity.this, "MSG : "+stText, Toast.LENGTH_SHORT).show();


                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                datetime = dateformat.format(c.getTime());
                System.out.println(datetime);
                Log.d(TAG, "title: "+title);
                DatabaseReference myRef = database.getReference("project").child(title).child(datetime);

                SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                String stEmail = sharedPreferences.getString("email", "");
                Log.d(TAG, "stEmail: "+stEmail);
                Hashtable<String, String> numbers
                        = new Hashtable<String, String>();
                numbers.put("email", stEmail);
                numbers.put("text", stText);

                myRef.setValue(numbers);
                etText.setText("");
            }
        });
    }
}