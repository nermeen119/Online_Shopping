package com.example.online_shopping.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.OnClickproduct;

import java.util.ArrayList;
import java.util.List;

public class womenActivity extends AppCompatActivity implements OnClickproduct {

    RecyclerView recwoman;
    public static productAdapter productAdapters;
    public static List<product> productsarr=new ArrayList<>();
    ArrayList<String> txResult=new ArrayList<>();
    String titles;
    int imagess;
    private SearchView searchView;
    double prices;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        recwoman=findViewById(R.id.Womanproduct);
        name=getIntent().getStringExtra("username");
        searchView=findViewById(R.id.searchWomen);
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
        productsarr.add(new product("yellow shirt",R.drawable.shirt,200.2));
        productsarr.add(new product("red dress",R.drawable.dress,500.22));
        productsarr.add(new product("mintGreen shirt",R.drawable.mintshirt,690.29));
        productsarr.add(new product("black shirt",R.drawable.blackshirt,300.3));
        productsarr.add(new product("skirt",R.drawable.skirt,900.29));
        productsarr.add(new product("blue jeans",R.drawable.bluejenes,500.23));
        productsarr.add(new product("BabyBlue shirt",R.drawable.blueshirt,600.29));


        productsarr=productAdapters.setProduct(productsarr);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(womenActivity.this,2);
        recwoman.setHasFixedSize(true);
        recwoman.setAdapter(productAdapters);
        recwoman.setLayoutManager(gridLayoutManager);
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
                    CartDataBase.getInstance(womenActivity.this).Deo().insert(cart);
                }
            }).start();
            Toast.makeText(womenActivity.this, "Added to basket", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(womenActivity.this, "error while insert", Toast.LENGTH_SHORT).show();
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
          //  Toast.makeText(this., "No Data Found", Toast.LENGTH_SHORT).show();
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

    public void scan_qr(View view)
    {
        Intent i=new Intent(womenActivity.this,searchScan.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    public void searchvoice(View view) {
        openVoice();
    }

}