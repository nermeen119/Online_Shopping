package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.user.OnItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataHolders>{

    List<CategoryModel> categoryModelss;
    private OnItemClick onItemClick;

    public CategoryAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.DataHolders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DataHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false));
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CategoryAdapter.DataHolders holder, int position) {
        holder.title.setText(categoryModelss.get(position).getTitle());
        int currentColor = Color.parseColor("#DBE9FA");
        holder.card.setCardBackgroundColor(currentColor);
        if(holder.title.getText().toString().equals("jewelery"))
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(holder.title.getText().toString());
                }
            });
        }
        if(holder.title.getText().toString().equals("electronics"))
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClickElectro(holder.title.getText().toString());
                }
            });
        }
        if(holder.title.getText().toString().equals("men's clothing"))
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClickMan(holder.title.getText().toString());
                }
            });
        }
        if(holder.title.getText().toString().equals("women's clothing"))
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClickWoman(holder.title.getText().toString());
                }
            });
        }
    }
    public void setCategoryModel(List<CategoryModel >categorymodelss)
    {
        this.categoryModelss=categorymodelss;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return categoryModelss.size();
    }


    class DataHolders extends RecyclerView.ViewHolder
    {
        TextView title;
        CardView card;
        public DataHolders(@NonNull @NotNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.text_string);
            card = itemView.findViewById(R.id.categoryList);
        }
    }
}
