package com.example.online_shopping.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.productAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.OnClickproduct;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class jeweleryActivity extends AppCompatActivity implements OnClickproduct {

    RecyclerView recjew;
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
        setContentView(R.layout.activity_jewelery);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        recjew=findViewById(R.id.jewlaryproduct);
        name=getIntent().getStringExtra("username");

        searchView=findViewById(R.id.searchJW);
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
        productsarr.add(new product("Infinity Ring",R.drawable.infinityring,2000.2));
        productsarr.add(new product("white Diamond",R.drawable.whitediamond,5000.22));
        productsarr.add(new product("white Gold",R.drawable.whitegold,3000.3));
        productsarr.add(new product("Infinity Heart",R.drawable.infintyheart,9000.29));
        productsarr.add(new product("Ring",R.drawable.ring,5000.23));
        productsarr.add(new product("pierced Owl",R.drawable.piercedowl,6900.29));

        productsarr=productAdapters.setProduct(productsarr);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(jeweleryActivity.this,2);
        recjew.setHasFixedSize(true);
        recjew.setAdapter(productAdapters);
        recjew.setLayoutManager(gridLayoutManager);
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
                    CartDataBase.getInstance(jeweleryActivity.this).Deo().insert(cart);
                }
            }).start();
            Toast.makeText(jeweleryActivity.this, "Added to basket", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(jeweleryActivity.this, "error while insert", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(jeweleryActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
        Intent i=new Intent(jeweleryActivity.this,searchScan.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    public void searchvoice(View view) {
        openVoice();
    }

}