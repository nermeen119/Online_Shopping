package com.example.online_shopping.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.CartAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.AllProductDatabase;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.product;
import com.example.online_shopping.ui.GetAddressActivity;
import com.example.online_shopping.ui.button_navigate;
import com.example.online_shopping.user.AllProductDao;
import com.example.online_shopping.user.RemoveClick;
import com.example.online_shopping.user.productDao;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;


public class CartFragment extends Fragment implements RemoveClick {
    AlertDialog.Builder builder;
    Button btSubmit;
    String userName;
    String[] title=new String[1000];
    String[] time=new String[1000];
    int[] quntity=new int[1000];
    int[] image=new int[1000];
    String length;
    double[] totalPrice=new double[1000];


    public CartFragment(String name)
    {
        userName=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cart, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        btSubmit=v.findViewById(R.id.submitOrder);
        builder = new AlertDialog.Builder(getActivity());
        final Dialog dialog = new Dialog(getActivity());
        builder.setMessage("Do you want to delete ?");
        builder.setTitle("Delete product from cart !");
        builder.setCancelable(false);
        RecyclerView recyclerView=v.findViewById(R.id.Cart_Recy);
        CartAdapter cartAdapter=new CartAdapter(this);
        CartDataBase.getInstance(getActivity()).Deo().getAllProductsByUserName(userName).observe(getActivity(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if(!carts.isEmpty())
                {
                    cartAdapter.setList(carts);
                    recyclerView.setAdapter(cartAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                else
                {
                    dialog.setContentView(R.layout.custom_item);
                    Button GoToHome = dialog.findViewById(R.id.gohome);
                    GoToHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction2.replace(R.id.bottom_container,new HomeFragment(userName)).commit();
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }

                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        cartAdapter.getTotalOrder(carts);
                        AllProductDatabase proDatabase= AllProductDatabase.Get_Instance(getContext());
                        final AllProductDao prodDao=proDatabase.allProductDao();

                        productDataBase proDatabase1= productDataBase.Get_Instance(getContext());
                        final productDao prodDao1 = proDatabase1.productDao();

                        Calendar calDate=Calendar.getInstance();
                        String dateOrder=DateFormat.getDateInstance().format(calDate.getTime());

                        length= String.valueOf(carts.size());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<carts.size();i++) {

                                    title[i]=(carts.get(i).getItemTitle());
                                    quntity[i]=(carts.get(i).getQuantity());
                                    totalPrice[i]=(carts.get(i).getPrice()*carts.get(i).getQuantity());
                                    image[i]=(carts.get(i).getItemImage());
                                    time[i]=dateOrder;
                                    AllProductModel prod = prodDao.returnProduct(carts.get(i).getItemTitle());
                                    prod.setPurchaseCount(carts.get(i).getQuantity() + prod.getPurchaseCount());
                                    AllProductDatabase.Get_Instance(getActivity()).allProductDao().UpdatePurchaseCount(prod);
                                    prodDao.UpdatePurchaseCount(prod);


                                }
                            }
                        }).start();


                        dialog.setContentView(R.layout.custom_dialog);
                        TextView backtoHome = dialog.findViewById(R.id.backhome);
                        Button Track_order = dialog.findViewById(R.id.trackorder);
                        TextView total_order=dialog.findViewById(R.id.totalorder);

                        backtoHome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getActivity(), button_navigate.class);
                                i.putExtra("username", userName);
                                startActivity(i);
                                dialog.cancel();

                            }
                        });


                        Track_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(getActivity(), GetAddressActivity.class);
                                i.putExtra("username", userName);
                                i.putExtra("itemTitle",title);
                                i.putExtra("time",time);
                                i.putExtra("quntity",quntity);
                                i.putExtra("image",image);
                                i.putExtra("totalPrice",totalPrice);
                                i.putExtra("length",length);

                                startActivity(i);

                            }
                        });
                        total_order.setText(CartAdapter.total+"");
                        dialog.show();
                    }
                });
            }
        });


        return v;
    }


    @Override
    public void onClickRemove(Integer click)
    {

        try{
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CartDataBase.getInstance(getActivity()).Deo().deleteProductByUserName(click,userName);
                    }
                }).start();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "error while delete", Toast.LENGTH_SHORT).show();
        }
    }
}