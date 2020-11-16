package com.susu.project_management;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class CreateProjectActivity extends AppCompatActivity {
    private static final String TAG = "CreateProjectActivity";
    Button btn_save;
    EditText etTitle;
    ImageButton calendar;
    TextView tvDeadline;
    EditText etDescription;
    EditText etUser;
    TextView project_page;
    Button btn_cancel;
    File tmpFile;

    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase database;
    String stEmail;
    File localFile;
    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;
    SharedPreferences sharedPreferences;

    ArrayList<Project> projectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_activity);
        database = FirebaseDatabase.getInstance();

        projectArrayList = new ArrayList<>();

        btn_save = (Button)findViewById(R.id.btn_save);
        etTitle = (EditText)findViewById(R.id.etTitle);
        calendar = (ImageButton)findViewById(R.id.calendar);
        tvDeadline = (TextView) findViewById(R.id.deadline);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etUser = (EditText) findViewById(R.id.add_friend);
        project_page = (TextView)findViewById(R.id.project_page);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
        stEmail = sharedPreferences.getString("email", "");
        Log.d(TAG, "email : " + stEmail);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener mDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Date Picker에서 선택한 날짜 Textview에 설정
                                tvDeadline.setText(String.format("%d/%d/%d", year, month+1, dayOfMonth));
                            }
                };
            }
        });


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Project project = dataSnapshot.getValue(Project.class);
                String commentKey = dataSnapshot.getKey();
                String stTitle = project.getTitle();
                String stDate = project.getDate();
                String stDescription = project.getDescription();
                Log.d(TAG, "stEmail: " + stTitle);
                Log.d(TAG, "stText: " + stDate);
                Log.d(TAG, "stDescription: " + stDescription);
                projectArrayList.add(project);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());
                String commentKey = snapshot.getKey();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved: " + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "postComments:onCancelled", error.toException());
                Toast.makeText(CreateProjectActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference databaseReference = database.getReference("project");
        databaseReference.addChildEventListener(childEventListener);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String stTitle = etTitle.getText().toString();
                String stDate = tvDeadline.getText().toString();
                String stDescription = etDescription.getText().toString();
                String stUser = etUser.getText().toString();

                Toast.makeText(CreateProjectActivity.this, "MSG : "+stTitle, Toast.LENGTH_SHORT).show();
                Toast.makeText(CreateProjectActivity.this, "MSG : "+stDate, Toast.LENGTH_SHORT).show();
                Toast.makeText(CreateProjectActivity.this, "MSG : "+stDescription, Toast.LENGTH_SHORT).show();
                Toast.makeText(CreateProjectActivity.this, "MSG : "+stUser, Toast.LENGTH_SHORT).show();

                DatabaseReference myRef = database.getReference("project").child(stEmail);

                Hashtable<String, String> numbers
                        = new Hashtable<String, String>();
                numbers.put("title", stTitle);
                numbers.put("date", stDate);
                numbers.put("description", stDescription);
                numbers.put("with", stUser);

                etTitle.setText("");
                etDescription.setText("");
                etUser.setText("");

                myRef.setValue(numbers);

                //Toast.makeText(this,"등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateProjectActivity.this, ProjectActivity.class);
                startActivity(i);
            }
        });
    }
}