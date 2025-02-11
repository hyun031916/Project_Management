package com.susu.project_management;

import android.app.DatePickerDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ItemViewHolder> {
    Context context;
    List<Board> data = new ArrayList<>();

    public BoardAdapter(Context context) {
        this.context = context;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @NonNull
    @Override
    public BoardAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 전 단계에서 만들었던
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        Button btn_save;
        EditText etTitle;
        ImageButton calendar;
        TextView tvDeadline;
        EditText etDescription;
        EditText etUser;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //https://dev-imaec.tistory.com/27
            btn_save = (Button)itemView.findViewById(R.id.btn_save);
            etTitle = (EditText)itemView.findViewById(R.id.etTitle);
            calendar = (ImageButton)itemView.findViewById(R.id.calendar);
            tvDeadline = (TextView) itemView.findViewById(R.id.deadline);
            etDescription = (EditText) itemView.findViewById(R.id.etDescription);
            etUser = (EditText) itemView.findViewById(R.id.add_friend);

            //날짜 출력 텍스트 뷰에 오늘 날짜 설정.
            TextView tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            Calendar cal = Calendar.getInstance();
            tvDate.setText(Calendar.YEAR+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE));

            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //date picker가 처음 떴을 때 오늘 날짜 보이도록 설정하기
                    Calendar cal = Calendar.getInstance();
                    new DatePickerDialog(context,
                            mDateSetListener,
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE)).show();
                }

                DatePickerDialog.OnDateSetListener mDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Date Picker에서 선택한 날짜 Textview에 설정
                                tvDeadline.setText(String.format("%d/%d/%d", year, month+1, dayOfMonth));
                            }
                        };
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    Board board = new Board(etTitle.getText().toString(),
                            tvDeadline.toString(), etDescription.toString(), etUser.toString(), 1);
                    databaseReference.child("project").push().setValue(board);
                    etTitle.setText("");
                    etDescription.setText("");
                    etUser.setText("");

                    Toast.makeText(context, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void onBind(Board board){
            etTitle.setText(board.getTitle());
            tvDeadline.setText(board.getDate());
            etDescription.setText(board.getDescription());
            etUser.setText(board.getUser());
        }
    }
}

