package com.example.online_shopping.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.online_shopping.R;
import com.example.online_shopping.database.CartAdminDatabase;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.fragment.AccountFragment;
import com.example.online_shopping.fragment.CartAdminFragment;
import com.example.online_shopping.fragment.CartFragment;
import com.example.online_shopping.fragment.ExploreFragment;
import com.example.online_shopping.fragment.FeedbackAdminFragment;
import com.example.online_shopping.fragment.FeedbackFragment;
import com.example.online_shopping.fragment.HomeFragment;
import com.example.online_shopping.fragment.deleteFragment;
import com.example.online_shopping.fragment.favoriteFragment;
import com.example.online_shopping.model.CartAdminModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class button_navigate extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    LinearLayout home , profile , report,feedbackUser,feedbackAdmin,chart,search;
    FloatingActionButton cart;

    String userName;
    int length;
    String rate;
    String visit=null;
    String[] title=new String[1000];
    String[] time=new String[1000];
    int[] quntity=new int[1000];
    int[] image=new int[1000];
    double[] totalPrice=new double[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_navigate);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        bottomNavigationView=findViewById(R.id.nav_bottom);

        bottomNavigationView = findViewById(R.id.nav_bottom);
        userName = getIntent().getStringExtra("username");
        title= getIntent().getStringArrayExtra("itemTitle");
        time= getIntent().getStringArrayExtra("time");
        totalPrice=getIntent().getDoubleArrayExtra("totalPrice");
        quntity=getIntent().getIntArrayExtra("quntity");
        image=getIntent().getIntArrayExtra("image");
        length=getIntent().getIntExtra("length",length);
        rate=getIntent().getStringExtra("rate");
        visit=getIntent().getStringExtra("visit");

        System.out.println("len: "+length);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bottom_container, new HomeFragment(userName)).commit();

        if(visit!=null)
        {
            for(int a=0;a<length;a++)
            {
                CartAdminModel cartAdmin=new CartAdminModel(title[a],image[a],totalPrice[a],quntity[a],time[a],userName,rate);
                try{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CartAdminDatabase.getInstance(button_navigate.this).Deo().insert(cartAdmin);
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int x=0;x<length;x++)
            {
                String n=title[x];
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CartDataBase.getInstance(button_navigate.this).Deo().deleteProductWithName(n);
                    }
                }).start();
            }
        }

        visit=null;
        home= findViewById(R.id.homeBtn);
        profile= findViewById(R.id.profileUserbtn);
        report= findViewById(R.id.reportBtn);
        cart= findViewById(R.id.cartBtn);
        feedbackUser= findViewById(R.id.feedbackUserBtn);
        feedbackAdmin= findViewById(R.id.feedbackAdminbtn);
        chart= findViewById(R.id.chartAdminBtn);
        search= findViewById(R.id.searchUserBtn);

        Intent i = getIntent();
        String username = i.getStringExtra("username");



        if(LoginActivity.username.contains("admin")&& LoginActivity.password.contains("123456"))
        {
            feedbackAdmin.setVisibility(View.VISIBLE);
            chart.setVisibility(View.VISIBLE);
            report.setVisibility(View.VISIBLE);
            profile.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            feedbackUser.setVisibility(View.GONE);
            //   cart.setVisibility(View.GONE);/
            cart.setImageResource(R.drawable.icon_logout);
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(button_navigate.this,LoginActivity.class));
                    button_navigate.this.finish();
                }
            });
            //  cart.setImageDrawable(ContextCompat.getDrawable(button_navigate.this, R.drawable.icon_logout));

        }
        else
        {
            feedbackAdmin.setVisibility(View.GONE);
            chart.setVisibility(View.GONE);
            report.setVisibility(View.GONE);
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.bottom_container,new CartFragment(userName)).commit();
                }
            });
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.bottom_container, new HomeFragment(userName)).commit();

            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.bottom_container, new CartAdminFragment()).commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction3=getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.bottom_container,new AccountFragment(userName)).commit();

            }
        });

        feedbackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new FeedbackFragment(username)).commit();

            }
        });

        feedbackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new FeedbackAdminFragment()).commit();

            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(button_navigate.this,BarChart.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new ExploreFragment(userName)).commit();
            }
        });

    }



}