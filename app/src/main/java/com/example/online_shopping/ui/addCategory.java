package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.CategoryAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.UserDatabase;
import com.example.online_shopping.database.categoryDataBase;
import com.example.online_shopping.fragment.HomeFragment;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.UserEntity;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.OnItemClick;
import com.example.online_shopping.user.UserDao;
import com.example.online_shopping.user.categoryDao;
import com.example.online_shopping.user.onClickCategory;

import java.util.ArrayList;
import java.util.List;

public class addCategory extends AppCompatActivity {
    EditText addcategory,editTitle;
    Button add,delete,edit;
    String titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        categoryDataBase categoryDataBases= categoryDataBase.Get_Instance(getApplicationContext());
        final categoryDao categorydao=categoryDataBases.category_dao();
        addcategory = findViewById(R.id.addcatgName);
        //editTitle = findViewById(R.id.edittitles);
        add = findViewById(R.id.addCategoryToList);
        delete=findViewById(R.id.removecatg);
        edit=findViewById(R.id.btedits);
        String  categ1="electronics";
        String  categ2="jewelery";
        String  categ3="men's clothing";
        String  categ4="women's clothing";

        titles = addcategory.getText().toString();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titles=addcategory.getText().toString();
                CategoryModel categoryModel2=new CategoryModel(categ1);
                CategoryModel categoryModel3=new CategoryModel(categ2);
                CategoryModel categoryModel4=new CategoryModel(categ3);
                CategoryModel categoryModel5=new CategoryModel(categ4);
                CategoryModel CategoryModel1=new CategoryModel(titles);

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if(categoryDataBase.Get_Instance(addCategory.this).category_dao().Checktitle(categ1)==null){
                                categoryDataBase.Get_Instance(addCategory.this).category_dao().insert_category(categoryModel2);

                            } if(categoryDataBase.Get_Instance(addCategory.this).category_dao().Checktitle(categ2)==null){
                                categoryDataBase.Get_Instance(addCategory.this).category_dao().insert_category(categoryModel3);

                            } if(categoryDataBase.Get_Instance(addCategory.this).category_dao().Checktitle(categ3)==null){
                                categoryDataBase.Get_Instance(addCategory.this).category_dao().insert_category(categoryModel4);

                            } if(categoryDataBase.Get_Instance(addCategory.this).category_dao().Checktitle(categ4)==null){
                                categoryDataBase.Get_Instance(addCategory.this).category_dao().insert_category(categoryModel5);
                            }
                        }
                    }).start();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            categoryDataBase.Get_Instance(addCategory.this).category_dao().insert_category(CategoryModel1);
                        }
                    }).start();
                    Toast.makeText(addCategory.this, "inserted", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addCategory.this, DeleteCategoryActivity.class));


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addcategory.getText().toString().isEmpty()) {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CategoryModel categoryModelss = categorydao.Checktitle(addcategory.getText().toString());
                                if (categoryModelss != null) {

                                    Intent i=new Intent(addCategory.this, EditCategoryActivity.class);
                                    i.putExtra("name",addcategory.getText().toString());
                                    startActivity(i);

                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    public void getCategory(View view) {
        startActivity(new Intent(addCategory.this,button_navigate.class));
    }
}