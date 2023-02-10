package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.model.CartAdminModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.DataHolder> {

    List<CartAdminModel> list;

    @NonNull
    @NotNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.image.setImageResource(list.get(position).getItemImage());
        holder.productName.setText(list.get(position).getItemTitle());
        holder.totalPrice.setText("Total price: $"+list.get(position).getTotalPrice());
        holder.rate.setText("Rating: "+list.get(position).getRate());
        holder.quantity.setText("Quantity: "+list.get(position).getQuantity());
        holder.time.setText("Date: "+list.get(position).getDate());

    }


    public void setList(List<CartAdminModel>list)
    {
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView productName,totalPrice,time,quantity,rate;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageProduct);
            productName=itemView.findViewById(R.id.productNameTV);
            totalPrice=itemView.findViewById(R.id.totalPriceTE);
            time=itemView.findViewById(R.id.timeTE);
            quantity=itemView.findViewById(R.id.quantityTE);
            rate=itemView.findViewById(R.id.rateTV);

        }
    }
}
