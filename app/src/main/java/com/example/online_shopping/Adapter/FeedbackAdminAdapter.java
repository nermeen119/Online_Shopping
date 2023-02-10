package com.example.online_shopping.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.model.FeedbackModel;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdminAdapter extends RecyclerView.Adapter<FeedbackAdminAdapter.DataHolder> {

    List<FeedbackModel> list;

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_design_admin,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.feedback.setText(list.get(position).getComment());

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
        public DataHolder(@NonNull View itemView) {
            super(itemView);

            feedback=itemView.findViewById(R.id.textFeedback);
        }
    }

    public void setFilteredList(ArrayList<FeedbackModel> filteredList)
    {
        this.list=filteredList;
        notifyDataSetChanged();
    }
}


