package com.example.online_shopping.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.CategoryAdapter;
import com.example.online_shopping.Adapter.newproductAdapter;
import com.example.online_shopping.Adapter.productAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.categoryDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.product;
import com.example.online_shopping.ui.ElecronicsActivity;
import com.example.online_shopping.ui.LoginActivity;
import com.example.online_shopping.ui.addCategory;
import com.example.online_shopping.ui.addProduct;
import com.example.online_shopping.ui.button_navigate;
import com.example.online_shopping.ui.jeweleryActivity;
import com.example.online_shopping.ui.menActivity;
import com.example.online_shopping.ui.searchScan;
import com.example.online_shopping.ui.womenActivity;
import com.example.online_shopping.user.OnClickproduct;
import com.example.online_shopping.user.OnItemClick;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClick,OnClickproduct {

   public static RecyclerView recyclerView,recyclerView2;
   public static CategoryAdapter categoryAdapter;
   public static productAdapter productAdapter;
    TextView tx8,tx9;
    newproductAdapter newproductAdapters;
    ImageButton btAddcategory,btAddproduct;
    public static List<CategoryModel> catg=new ArrayList<>();
    String categ1,categ2,categ3,categ4;
    List<product>productsarr=new ArrayList<>();

    String titles;
    int imagess;
    private SearchView searchView;
    double prices;
    String name;

    public HomeFragment(String name) {

        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_home, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        categoryAdapter=new CategoryAdapter(this);
        productAdapter=new productAdapter(this);
        newproductAdapters =new newproductAdapter(this);
        recyclerView=(RecyclerView)v.findViewById(R.id.reclistcategory);
        recyclerView2=v.findViewById(R.id.recItem);
        btAddcategory=v.findViewById(R.id.addCategory);
        btAddproduct=v.findViewById(R.id.addpro);
        tx8=v.findViewById(R.id.textView8);
        tx9=v.findViewById(R.id.textView9);
        productsarr.add(new product("mintGreen shirt",R.drawable.mintshirt,690.29));
        productsarr.add(new product("white Gold",R.drawable.whitegold,3000.3));
        productsarr.add(new product("Infinity Heart",R.drawable.infintyheart,9000.29));
        productsarr.add(new product("Huawei",R.drawable.huawei,3000.3));
        productsarr.add(new product("black jeans",R.drawable.blackjeanes,300.3));
        productsarr.add(new product("Black shirt",R.drawable.blackshirts,200.2));

        categ1="electronics";
        categ2="jewelery";
        categ3="men's clothing";
        categ4="women's clothing";
        catg.clear();
        CategoryModel categoryModel2=new CategoryModel(categ1);
        CategoryModel categoryModel3=new CategoryModel(categ2);
        CategoryModel categoryModel4=new CategoryModel(categ3);
        CategoryModel categoryModel5=new CategoryModel(categ4);
        catg.add(categoryModel2);
        catg.add(categoryModel3);
        catg.add(categoryModel4);
        catg.add(categoryModel5);

        if(LoginActivity.username.contains("admin")&&LoginActivity.password.contains("123456"))
        {
           btAddcategory.setVisibility(View.VISIBLE);
           btAddproduct.setVisibility(View.VISIBLE);
           tx8.setVisibility(View.VISIBLE);
           tx9.setVisibility(View.VISIBLE);

        }
        else
        {
            btAddcategory.setVisibility(View.GONE);
            btAddproduct.setVisibility(View.GONE);
            tx8.setText("Category");
            tx8.setVisibility(View.VISIBLE);
            tx9.setText("New Product");
            tx9.setVisibility(View.VISIBLE);
        }

        btAddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), addProduct.class));
            }
        });
        btAddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), addCategory.class));
            }
        });
        categoryAdapter.setCategoryModel(catg);
        RecyclerView.LayoutManager layoutManager2= new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(layoutManager2);

// admin add new category

        categoryDataBase.Get_Instance(getActivity()).category_dao().getcategory().observe(getActivity(), new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                if (!categoryModels.isEmpty()) {
                    categoryAdapter.setCategoryModel(categoryModels);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(HomeFragment.categoryAdapter);
                    recyclerView.setLayoutManager(layoutManager2);
                } else {
                    categoryModels.clear();

                }
            }
        });

        productAdapter.setProduct(productsarr);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(productAdapter);

        productDataBase.Get_Instance(getActivity()).productDao().getAllProducts().observe(getActivity(), new Observer<List<product>>() {
            @Override
            public void onChanged(List<product> products) {
                if(!products.isEmpty())
                {
                    productAdapter.setProduct(products);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setLayoutManager(layoutManager);
                    recyclerView2.setAdapter(productAdapter);

                }
                else
                {
                    products.clear();
                }
            }
        });
        return v;
    }

    @Override
    public void onClick(String categoryAdapter) {
        Intent i = new Intent(getActivity(), jeweleryActivity.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    @Override
    public void onClickElectro(String categoryAdapter) {
        Intent i = new Intent(getActivity(), ElecronicsActivity.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    @Override
    public void onClickMan(String categoryAdapter) {
        Intent i = new Intent(getActivity(), menActivity.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    @Override
    public void onClickWoman(String categoryAdapter) {
        Intent i = new Intent(getActivity(), womenActivity.class);
        i.putExtra("username", name);
        startActivity(i);
    }


    @Override
    public void onClickadditem(product products) {

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
            Toast.makeText(getActivity(), "Added to basket", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error while insert", Toast.LENGTH_SHORT).show();
        }


    }
}