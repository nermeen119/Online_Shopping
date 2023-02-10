package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.ui.LoginActivity;
import com.example.online_shopping.ui.addProduct;
import com.example.online_shopping.user.OnClickAllProduct;
import com.example.online_shopping.user.OnClickproduct;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.DataHolders>{
    private List<AllProductModel> productsmodel;
    private OnClickAllProduct onClickproduct;


    public AllProductAdapter(OnClickAllProduct onClickproduct) {
        this.onClickproduct = onClickproduct;
    }

    @NonNull
    @NotNull
    @Override
    public DataHolders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DataHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataHolders holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(productsmodel.get(position).getTitle());
        holder.price.setText("$ " + productsmodel.get(position).getPrice());
        holder.imageButton.setImageResource(productsmodel.get(position).getImage());

        if (LoginActivity.username.contains("admin") && LoginActivity.password.contains("123456")) {
            holder.addItem.setVisibility(View.GONE);
        } else {


            holder.addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (productsmodel.get(position).isFoundPro()) {
                        onClickproduct.onClickadditem(productsmodel.get(position));
                        holder.addItem.setImageResource(R.drawable.remove_cart);
                        productsmodel.get(position).setFoundPro(false);
                    } else if (!productsmodel.get(position).isFoundPro()) {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    CartDataBase.getInstance(view.getContext()).Deo().deleteProductWithName(productsmodel.get(position).getTitle());
                                }

                            }).start();
                            Toast.makeText(view.getContext(), "remove from basket", Toast.LENGTH_SHORT).show();
                            productsmodel.get(position).setFoundPro(true);
                            holder.addItem.setImageResource(R.drawable.icon_cart);
                        } catch (Exception e) {
                            Toast.makeText(view.getContext(), "error while delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
    public void setProduct(List<AllProductModel> products)
    {
        this.productsmodel=products;
        notifyDataSetChanged();
    }
    public void setFilteredList(ArrayList<AllProductModel> filteredList)
    {
        this.productsmodel=filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return productsmodel.size();
    }


    class DataHolders extends RecyclerView.ViewHolder
    {
        TextView title,price;
        ImageButton imageButton,addItem;
        public DataHolders(@NonNull @NotNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.productTitle);
            price=itemView.findViewById(R.id.productPrice);
            imageButton=itemView.findViewById(R.id.product_image);
            addItem=itemView.findViewById(R.id.Imageadditem);
        }
    }
}
