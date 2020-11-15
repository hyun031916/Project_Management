package com.susu.project_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.susu.project_management.ui.users.UsersFragment;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<User> mDataset;
    String stMyEmail = "";
    UsersFragment context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvUser;
        public ImageView ivUser;
        public MyViewHolder(View v) {
            super(v);
            tvUser = (TextView)v.findViewById(R.id.tvUser);
            ivUser = (ImageView)v.findViewById(R.id.ivUser);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(ArrayList<User> mDataset, String stEmail, UsersFragment context) {
        this.mDataset = mDataset;
        this.stMyEmail = stEmail;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvUser.setText(mDataset.get(position).getEmail());
        Picasso.get()
                .load(mDataset.get(position).getPhoto())
                .fit()
                .centerInside()
                .into(holder.ivUser);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
