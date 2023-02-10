package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.model.FeedbackModel;
import com.example.online_shopping.user.RemoveClick;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.DataHolder> {

    List<FeedbackModel> list;
    RemoveClick onItemClickremove;

    public FeedbackAdapter(RemoveClick onItemClickremove) {
        this.onItemClickremove = onItemClickremove;
    }


    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_design,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.feedback.setText(list.get(position).getComment());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickremove.onClickRemove(list.get(position).getId());
            }
        });

    }

    public void setList(List<FeedbackModel>list)
    {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder{

        TextView feedback;
        ImageButton remove;
        public DataHolder(@NonNull View itemView) {
            super(itemView);

            remove=itemView.findViewById(R.id.button);
            feedback=itemView.findViewById(R.id.textFeedback);
        }
    }

    public void setFilteredList(ArrayList<FeedbackModel> filteredList)
    {
        this.list=filteredList;
        notifyDataSetChanged();
    }
}
