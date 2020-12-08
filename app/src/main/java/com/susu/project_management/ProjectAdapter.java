package com.susu.project_management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.susu.project_management.ui.home.HomeFragment;

import java.text.DateFormat;
import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private ArrayList<Project> arrayList;  //데이터 보관
    private Context context;    //어댑터에서 액티비티 액션 가져올 때 필요함. 선택한 액티비티에 대한 context 가져옴
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();    //데이터베이스 위치한 곳
    DatabaseReference mRef = databaseReference.child("project");    //project
    private static final String TAG = "ProjectAdapter";

    public ProjectAdapter(ArrayList<Project> mDataset, Context context) {
        this.arrayList = mDataset;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvEmail;
        public TextView tvTitle;
        public TextView tvDate;
        public TextView tvDescription;
        public TextView tvUser;
        public Button btnChat;
        public MyViewHolder(View v) {
            super(v);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvUser = v.findViewById(R.id.tvUser);
            btnChat = v.findViewById(R.id.btnChat);
        }
    }


    @Override
    public ProjectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item_view, parent, false);    //아이템의 뷰 객체 생성
        MyViewHolder vh = new MyViewHolder(v);  //각각의 아이템을 위한 뷰를 담고 있는 뷰 홀더 객체 반환
        return vh;
    }

    //홀더가 갖고 있는 뷰에 데이터 세팅
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvEmail.setText(arrayList.get(position).getEmail());
        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvDate.setText(arrayList.get(position).getDate());
        holder.tvDescription.setText(arrayList.get(position).getDescription());
        holder.tvUser.setText(arrayList.get(position).getUser());
//        String email = arrayList.get(position).getEmail();
        Intent intent = ((Activity) context).getIntent();
        String email = intent.getExtras().getString("email");
        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("email", email);
                i.putExtra("title", arrayList.get(position).getTitle());
                Log.d(TAG, "onClick: "+arrayList.get(position).getTitle());
                ((Activity)context).startActivity(i);
            }
        });

    }

    //arrayList가 null이 아니면 사이즈를, null이면 0을 반환하라는 뜻.
    @Override
    public int getItemCount() {
        return (null!= arrayList ? arrayList.size():0);
    }

//    @Override
//    public int getItemCount() {
//        return itmes.size();
//    }
    public void addItems(Project project){
        arrayList.add(project);
        notifyDataSetChanged();
    }
}
