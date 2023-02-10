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
import com.example.online_shopping.database.categoryDataBase;
import com.example.online_shopping.database.productDataBase;

public class DeleteCategoryActivity extends AppCompatActivity {

    Button delete, home;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        delete=findViewById(R.id.btremovepro);
        title=findViewById(R.id.deletecategorytitle);
        home=findViewById(R.id.backHome);

        productDataBase productDataBases= productDataBase.Get_Instance(getApplicationContext());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!title.getText().toString().isEmpty()) {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                categoryDataBase.Get_Instance(DeleteCategoryActivity.this).category_dao().deleteCategorytWithName(title.getText().toString());
                            }
                        }).start();
                        Toast.makeText(DeleteCategoryActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(DeleteCategoryActivity.this, button_navigate.class));
                }
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteCategoryActivity.this,button_navigate.class));

            }
        });

    }
}