package com.example.online_shopping.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.online_shopping.Adapter.AllProductAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.AllProductDatabase;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.user.OnClickAllProduct;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment implements OnClickAllProduct {

    RecyclerView recyclerView;
    public static AllProductAdapter allproductAdapters;
    List<AllProductModel> productsarr=new ArrayList<>();

    String titles;
    int imagess;
    SearchView searchView;
    double prices;

    String name;

    public ExploreFragment(String name) {

        this.name = name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_explore, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        recyclerView = v.findViewById(R.id.search_rec);
        searchView=v.findViewById(R.id.search);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        productsarr.clear();
        allproductAdapters=new AllProductAdapter(this);
        productsarr.add(new AllProductModel("Lenovo",R.drawable.lenovou,10000.2, 30));
        productsarr.add(new AllProductModel("Smart TV",R.drawable.tv,5000.22, 30));
        productsarr.add(new AllProductModel("Infinity Ring",R.drawable.infinityring,2000.2, 30));
        productsarr.add(new AllProductModel("white Diamond",R.drawable.whitediamond,5000.22, 25));
        productsarr.add(new AllProductModel("white Gold",R.drawable.whitegold,3000.3, 10));
        productsarr.add(new AllProductModel("black jeans",R.drawable.blackjeanes,300.3, 15));
        productsarr.add(new AllProductModel("Black shirt",R.drawable.blackshirts,200.2, 40));
        productsarr.add(new AllProductModel("Infinity Heart",R.drawable.infintyheart,9000.29, 20));
        productsarr.add(new AllProductModel("Solid Formal",R.drawable.solidformal,690.29, 10));
        productsarr.add(new AllProductModel("Mens Cotton",R.drawable.menscotton,600.29, 30));
        productsarr.add(new AllProductModel("yellow shirt",R.drawable.shirt,200.2, 35));
        productsarr.add(new AllProductModel("red dress",R.drawable.dress,500.22, 40));
        productsarr.add(new AllProductModel("mintGreen shirt",R.drawable.mintshirt,690.29, 20));
        productsarr.add(new AllProductModel("black shirt",R.drawable.blackshirt,300.3, 15));
        productsarr.add(new AllProductModel("skirt",R.drawable.skirt,900.29, 25));
        productsarr.add(new AllProductModel("blue jeans",R.drawable.bluejenes,500.23, 20));
        productsarr.add(new AllProductModel("BabyBlue shirt",R.drawable.blueshirt,600.29, 35));
        productsarr.add(new AllProductModel("Mens Casual",R.drawable.menscasual,900.29, 50));
        productsarr.add(new AllProductModel("white shirt",R.drawable.whiteshirt,700.23, 25));
        productsarr.add(new AllProductModel("Solid Red",R.drawable.solidred,500.23, 15));
        productsarr.add(new AllProductModel("Ring",R.drawable.ring,5000.23, 40));
        productsarr.add(new AllProductModel("pierced Owl",R.drawable.piercedowl,6900.29, 10));
        productsarr.add(new AllProductModel("Huawei",R.drawable.huawei,3000.3, 35));
        productsarr.add(new AllProductModel("DELL",R.drawable.dell,15000.23, 35));
        productsarr.add(new AllProductModel("Apple",R.drawable.appel,6900.29, 40));

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AllProductDatabase.Get_Instance(getActivity()).allProductDao().insertProduct(productsarr);
                }

            }).start();
           // Toast.makeText(getActivity(), "Inserted !", Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            e.printStackTrace();
        }

        AllProductDatabase.Get_Instance(getActivity()).allProductDao().getAllProducts().observe(getActivity(), new Observer<List<AllProductModel>>() {
            @Override
            public void onChanged(List<AllProductModel> allProductModels) {
                if(!allProductModels.isEmpty()){
                    allproductAdapters.setProduct(allProductModels);
                    GridLayoutManager gridLayoutManager= new GridLayoutManager(getActivity(),2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(allproductAdapters);
                    recyclerView.setLayoutManager(gridLayoutManager);

                }
            }
        });

        return v;
    }


    @Override
    public void onClickadditem(AllProductModel products) {

        titles=products.getTitle();
        imagess=products.getImage();
        prices=products.getPrice();

        Cart cart=new Cart(titles,imagess,prices,name);
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CartDataBase.getInstance(getActivity()).Deo().insert(cart);
                }
            }).start();
            Toast.makeText(getActivity(), "Added to basket", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error while insert", Toast.LENGTH_LONG).show();
        }

    }
    private void filterList(String text) {
        ArrayList<AllProductModel> filteredList = new ArrayList<>();
        for (AllProductModel model : productsarr) {
            if (model.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(model);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            allproductAdapters.setFilteredList(filteredList);
        }
    }


}