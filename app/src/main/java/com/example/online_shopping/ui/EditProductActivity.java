package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.productDao;

public class EditProductActivity extends AppCompatActivity {

    Button edit, home;
    EditText oldTitle, newPrice, newQuantity;
    boolean f=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
        edit=findViewById(R.id.bteditproductb);
        oldTitle=findViewById(R.id.addOldproductitle);
        newPrice=findViewById(R.id.addNewproducprice);
        newQuantity=findViewById(R.id.addNewproducquantity);
        home=findViewById(R.id.backHomes);

        productDataBase productDataBases= productDataBase.Get_Instance(getApplicationContext());
        final productDao productdaoss=productDataBases.productDao();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product products;
                if (oldTitle.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProductActivity.this, "Old Title is empty", Toast.LENGTH_LONG).show();

                }
                else if (newQuantity.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProductActivity.this, "Quantity is empty", Toast.LENGTH_LONG).show();

                }
                else if (newPrice.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProductActivity.this, "Price is empty", Toast.LENGTH_LONG).show();

                }
               else if(!newPrice.getText().toString().isEmpty()&&!newQuantity.getText().toString().isEmpty()&&!oldTitle.getText().toString().isEmpty()) {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                product products=productdaoss.returnProduct(oldTitle.getText().toString());
                                products.setPrice(Double.parseDouble(newPrice.getText().toString()));
                                products.setQuntity(Integer.parseInt(newQuantity.getText().toString()));
                                productdaoss.editProduct(products);
                            }
                        }).start();
                        Toast.makeText(EditProductActivity.this, "Updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//                if(f==false)
//                {
//
//                    oldTitle.setText("");
//                    newPrice.setText("");
//                    newQuantity.setText("");
//                    f=true;
//                    Toast.makeText(EditProductActivity.this, "Old Title Not Found", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    f=false;
//                    Toast.makeText(EditProductActivity.this, "Updated", Toast.LENGTH_LONG).show();
//
//                }


            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProductActivity.this,button_navigate.class));

            }
        });
    }
}