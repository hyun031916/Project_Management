package com.susu.project_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZoneId;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btnLogin, btnRegister;
    EditText etId, etPwd, etCheckPwd;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase database;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        etId = (EditText)findViewById(R.id.etId);
        etPwd = (EditText)findViewById(R.id.etPwd);
        etCheckPwd = (EditText)findViewById(R.id.etCheckPwd);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stEmail = etId.getText().toString();
                String stPwd = etPwd.getText().toString();
                if(stEmail.isEmpty()){
                    Toast.makeText(MainActivity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stPwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(stEmail, stPwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String stUserEmail = user.getEmail();
                                    String stUserName = user.getDisplayName();
                                    Log.d(TAG, "stUserEmail : "+stUserEmail+", stUserName"+stUserName);

                                    SharedPreferences sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("email", stUserEmail);
                                    editor.commit();

                                    Intent i = new Intent(MainActivity.this, ProjectActivity.class);
                                    i.putExtra("email", stUserEmail);
                                    Log.d(TAG, "이게 이메일이다.: "+stUserEmail);
                                    startActivity(i);
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmail = etId.getText().toString();
                String stPwd = etPwd.getText().toString();
                String stCheckPwd = etCheckPwd.getText().toString();

                if(stEmail.isEmpty()){
                    Toast.makeText(MainActivity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stPwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stCheckPwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if(!stPwd.equals(stCheckPwd)){
                    Toast.makeText(MainActivity.this, "비밀번호가 틀렸습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(stEmail, stPwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    DatabaseReference myRef = database.getReference("users").child(user.getUid());

                                    Hashtable<String, String> numbers
                                            = new Hashtable<String, String>();
                                    numbers.put("email", user.getEmail());
                                    myRef.setValue(numbers);
                                    Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_LONG).cancel();

//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }
}