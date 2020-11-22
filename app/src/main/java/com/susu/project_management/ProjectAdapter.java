package com.susu.project_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.susu.project_management.ui.home.HomeFragment;

import java.text.DateFormat;
import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private ArrayList<Project> mDataset = new ArrayList<>();    //데이터 보관
    Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ProjectAdapter(ArrayList<Project> mDataset) {
        this.mDataset = mDataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvEmail;
        public TextView tvTitle;
        public TextView tvDate;
        public TextView tvDescription;
        public TextView tvUser;
        public MyViewHolder(View v) {
            super(v);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDate = v.findViewById(R.id.tvDate);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvUser = v.findViewById(R.id.tvUser);
        }
    }


    @Override
    public ProjectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvEmail.setText(mDataset.get(position).getEmail());
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvDate.setText(mDataset.get(position).getDate());
        holder.tvDescription.setText(mDataset.get(position).getDescription());
        holder.tvUser.setText(mDataset.get(position).getUser());

    }

    @Override
    public int getItemCount() {
        return (null!= mDataset ? mDataset.size():0);
    }

//    @Override
//    public int getItemCount() {
//        return itmes.size();
//    }
    public void addItems(Project project){
        mDataset.add(project);
        notifyDataSetChanged();
    }
}
