package com.example.online_shopping.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.newproductAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.categoryDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.fragment.HomeFragment;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.OnClickproduct;
import com.example.online_shopping.user.categoryDao;
import com.example.online_shopping.user.productDao;

import java.util.ArrayList;
import java.util.List;

public class addProduct extends AppCompatActivity implements OnClickproduct {
    ImageView image;
    Button add;
    EditText title,price,quantity;
    TextView addPhoto;
    String titles;
    public static String images,images2;
    Button edit,delete;
    public static Uri ur;
    int quantities;
    double prices;
    public static Bitmap bb;
    public static newproductAdapter newproductAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        productDataBase productDataBases= productDataBase.Get_Instance(getApplicationContext());
      //  final productDao productdaoss=productDataBases.productDao();
        image=findViewById(R.id.addimage);
        add=findViewById(R.id.addProductidn);
        edit=findViewById(R.id.bteditproduct);
        delete=findViewById(R.id.btremovepro);
        title=findViewById(R.id.addproductitleTe);
        price=findViewById(R.id.addproducpriceTE);
        quantity=findViewById(R.id.addproducquantity);
        addPhoto=findViewById(R.id.add_photo);
        newproductAdapters=new newproductAdapter(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titles=title.getText().toString();
                prices=Double.parseDouble(price.getText().toString());
                //quantities=Integer.parseInt(quantity.getText().toString());
                product product2=new product("mintGreen shirt",R.drawable.mintshirt,690.29);
                product product3=new product("white Gold",R.drawable.whitegold,3000.3);
                product product4=new product("Infinity Heart",R.drawable.infintyheart,9000.29);
                product product5=new product("Huawei",R.drawable.huawei,3000.3);
                product product6=new product("black jeans",R.drawable.blackjeanes,300.3);
                product product7=new product("Black shirt",R.drawable.blackshirts,200.2);
                // image.setImageURI(ur);
                images=String.valueOf(ur);
                //images2=String.valueOf(R.drawable.mintshirt);
                System.out.println("imdjbf "+images2);
                product product1=new product(titles,prices,images,quantities);
                //product product11=new product(titles,prices,images2,quantities);

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product1);
                            //          productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product11);
                        }
                    }).start();
                    Toast.makeText(addProduct.this,"inserted",Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("mintGreen shirt")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product2);
                            }
                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("white Gold")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product3);

                            }
                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("Infinity Heart")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product4);

                            }
                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("Huawei")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product5);

                            }
                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("black jeans")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product6);

                            }
                            if(productDataBase.Get_Instance(addProduct.this).productDao().returnProduct("Black shirt")==null){
                                productDataBase.Get_Instance(addProduct.this).productDao().insertProduct(product7);
                            }
                        }
                    }).start();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addProduct.this,EditProductActivity.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addProduct.this,DeleteProductActivity.class));
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);

              //  addPhoto(view);
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i,200);
            }
        });
    }

    //admin added product photo
//    public void addPhoto(View view) {
//        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(i,200);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK)
        {
            image.setImageBitmap((Bitmap)data.getExtras().get("data"));//masoala 3n 3rd el haga
        }
        else if (requestCode==200&&resultCode==RESULT_OK)
        {
            ur=data.getData();
            image.setImageURI(ur);
        }
    }

    @Override
    public void onClickadditem(product products) {

    }

    public void BackToHome(View view) {
        startActivity(new Intent(addProduct.this,button_navigate.class));

    }
}