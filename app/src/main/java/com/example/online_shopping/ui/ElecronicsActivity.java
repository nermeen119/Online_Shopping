package com.example.online_shopping.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.online_shopping.Adapter.productAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.fragment.ExploreFragment;
import com.example.online_shopping.fragment.HomeFragment;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.OnClickproduct;

import java.util.ArrayList;
import java.util.List;

public class ElecronicsActivity extends AppCompatActivity implements OnClickproduct {

    public static RecyclerView recElectro;
    public static productAdapter productAdapters;
    public static List<product>productsarr=new ArrayList<>();
    ArrayList<String> txResult=new ArrayList<>();
    String titles;
    int imagess;
    private SearchView searchView;
    double prices;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elecronics);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        recElectro=findViewById(R.id.electroproduct);
        searchView=findViewById(R.id.searchElectro);
        name=getIntent().getStringExtra("username");
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

        productAdapters=new productAdapter(this);
        productsarr.add(new product("Lenovo",R.drawable.lenovou,10000.2));
        productsarr.add(new product("Smart TV",R.drawable.tv,5000.22));
        productsarr.add(new product("Huawei",R.drawable.huawei,3000.3));
        productsarr.add(new product("DELL",R.drawable.dell,15000.23));
        productsarr.add(new product("Apple",R.drawable.appel,6900.29));

                productsarr=productAdapters.setProduct(productsarr);
                GridLayoutManager gridLayoutManager= new GridLayoutManager(ElecronicsActivity.this,2);
                recElectro.setHasFixedSize(true);
                recElectro.setAdapter(productAdapters);
                recElectro.setLayoutManager(gridLayoutManager);
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
                    CartDataBase.getInstance(ElecronicsActivity.this).Deo().insert(cart);
                }
            }).start();
            Toast.makeText(ElecronicsActivity.this, "Added to basket", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ElecronicsActivity.this, "error while insert", Toast.LENGTH_SHORT).show();
        }

    }

    public static void filterList(String text) {
        ArrayList<product> filteredList = new ArrayList<>();
        for (product model : productsarr) {
            if (model.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(model);
            }
        }
        if (filteredList.isEmpty()) {
          //  Toast.makeText(ElecronicsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            productAdapters.setFilteredList(filteredList);
        }
    }

    private void openVoice()
    {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode== RESULT_OK)
        {
           txResult= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            filterList(txResult.get(0));
        }
    }


    public void scan_qr(View view) {

        Intent i=new Intent(ElecronicsActivity.this,searchScan.class);
        i.putExtra("username", name);
        startActivity(i);
        //startActivity(new Intent(ElecronicsActivity.this,searchScan.class));
    }

    public void searchvoice(View view) {
        openVoice();
    }
}