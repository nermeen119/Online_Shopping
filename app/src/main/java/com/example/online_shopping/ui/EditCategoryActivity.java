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
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.user.categoryDao;

public class EditCategoryActivity extends AppCompatActivity {

    EditText newTitle;
    Button edit, home;

    String name;

    boolean f=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        categoryDataBase categoryDataBases= categoryDataBase.Get_Instance(getApplicationContext());
        final categoryDao categorydao=categoryDataBases.category_dao();

name=getIntent().getStringExtra("name");
        edit = findViewById(R.id.bteditcategorybt);
        newTitle = findViewById(R.id.newedittitle);
        home=findViewById(R.id.backHome);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!newTitle.getText().toString().isEmpty()) {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CategoryModel categoryModelss = categorydao.Checktitle(name);
                                 if(categoryModelss!=null)
                                {
                                    categoryModelss.setTitle(newTitle.getText().toString());
                                    categorydao.editCategory(categoryModelss);
                                    startActivity(new Intent(EditCategoryActivity.this,button_navigate.class));
                                }
                            }
                        }).start();
                        Toast.makeText(EditCategoryActivity.this, "Updated", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditCategoryActivity.this,button_navigate.class));

            }
        });

    }
}