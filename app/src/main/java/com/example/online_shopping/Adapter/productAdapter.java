package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.product;
import com.example.online_shopping.ui.ElecronicsActivity;
import com.example.online_shopping.ui.LoginActivity;
import com.example.online_shopping.ui.addProduct;
import com.example.online_shopping.user.OnClickproduct;
import com.google.android.material.internal.ContextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class productAdapter extends RecyclerView.Adapter<productAdapter.DataHolders>{
    private List<product> productsmodel;
    private OnClickproduct onClickproduct;
    public productAdapter(OnClickproduct onClickproduct) {
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

        String image = String.valueOf(addProduct.ur);
        holder.title.setText(productsmodel.get(position).getTitle());
        holder.price.setText("$ " + productsmodel.get(position).getPrice());
       // System.out.println("cjkdnvccccc + " + productsmodel.get(position).getImageproduct());
        holder.imageButton.setImageResource(productsmodel.get(position).getImage());
        if (String.valueOf(productsmodel.get(position).getImageproduct()).contains(image)) {
            holder.imageButton.setImageURI(addProduct.ur);
        } else {
            holder.imageButton.setImageResource(productsmodel.get(position).getImage());
        }
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
    public List<product> setProduct(List<product> products)
    {
        this.productsmodel=products;
        notifyDataSetChanged();
        return products;
    }
    public void setFilteredList(ArrayList<product> filteredList)
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
