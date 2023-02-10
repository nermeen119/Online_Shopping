package com.example.online_shopping.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.R;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.user.RemoveClick;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.DataHolder> {

    List<Cart> list;
    RemoveClick onItemClickremove;
    boolean found=false;
    int addresult;
    public static double  total;
    String quantity;
    public CartAdapter(RemoveClick onItemClickremove) {
        this.onItemClickremove = onItemClickremove;
    }

    @NonNull
    @NotNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataHolder holder, @SuppressLint("RecyclerView") int
            position) {
        holder.image.setImageResource(list.get(position).getItemImage());
        holder.productName.setText(list.get(position).getItemTitle());
        holder.price.setText("$"+list.get(position).getPrice());
        list.get(position).setQuantity(1);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickremove.onClickRemove(list.get(position).getId());
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 addresult=  Integer.parseInt(holder.txresults.getText().toString());
                addresult+=1;
                list.get(position).setQuantity(addresult);

                if(addresult<10)
                {
                    holder.txresults.setText(addresult+"");
                }
                else
                {
                    holder.txresults.setText(addresult+"");
                }
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int subresult=  Integer.parseInt(holder.txresults.getText().toString());
                if(subresult>0)

                    subresult-=1;
                list.get(position).setQuantity(subresult);
                if(subresult<10)
                {
                    holder.txresults.setText(subresult+"");
                    found=true;
                }
                else
                {
                    holder.txresults.setText(subresult+"");
                    found=true;

                }
                if(subresult==0&&found==false)
                {
                    holder.sub.setClickable(false);
                    found=false;
                }

            }
        });
        quantity=holder.txresults.getText().toString();
    }

    public void getTotalOrder(List<Cart>list)
    {
        double mul;
        double sum=0;

        for (Cart model:list)
        {
            System.out.println("model.getPrice(): "+model.getPrice());
            System.out.println("quantity: "+quantity);
            mul=(model.getPrice()*model.getQuantity());

            sum+=mul;
        }
        total=sum;
    }

    public void setList(List<Cart>list)
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
        TextView productName,price,txresults;
        ImageButton remove,add,sub;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageView2);
            productName=itemView.findViewById(R.id.textView3);
            price=itemView.findViewById(R.id.priceitem);
            remove=itemView.findViewById(R.id.button);
            add=itemView.findViewById(R.id.additems);
            sub=itemView.findViewById(R.id.subitems);
            txresults=itemView.findViewById(R.id.txres);
        }
    }
}
