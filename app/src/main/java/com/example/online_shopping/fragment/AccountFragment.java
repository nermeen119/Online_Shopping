package com.example.online_shopping.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Adapter.CartAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.AllProductDatabase;
import com.example.online_shopping.database.CartAdminDatabase;
import com.example.online_shopping.database.CartDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.CartAdminModel;
import com.example.online_shopping.ui.GetAddressActivity;
import com.example.online_shopping.ui.LoginActivity;
import com.example.online_shopping.ui.button_navigate;
import com.example.online_shopping.user.AllProductDao;
import com.example.online_shopping.user.productDao;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;


public class AccountFragment extends Fragment {

    Button logout;
    TextView userNameTV;
    LinearLayout order,account, delivaryAddress;

    String userName;
    int length;
    String[] title;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME="PrefsFile";

    public AccountFragment(String userName) {
        this.userName = userName;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_account, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        logout=v.findViewById(R.id.btlogout);
        userNameTV=v.findViewById(R.id.nameusers);
        order=v.findViewById(R.id.orderLin);
        account=v.findViewById(R.id.account);
        delivaryAddress=v.findViewById(R.id.deliveryAddress);

        userNameTV.setText("User Name: "+userName);

        title=new String[1000];
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartDataBase.getInstance(getActivity()).Deo().getAllProductsByUserName(userName).observe(getActivity(), new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> carts) {
                        if (!carts.isEmpty()) {
                            for (int i = 0; i < carts.size(); i++) {

                                title[i] = (carts.get(i).getItemTitle());
                            }
                            for (int x = 0; x < carts.size(); x++) {
                                String n = title[x];
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CartDataBase.getInstance(getActivity()).Deo().deleteProductWithName(n);
                                    }
                                }).start();
                            }
                        }
                    }
                });

                /*SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();*/
                Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new OrderFragment(userName)).commit();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new MyAcountFragment(userName)).commit();
            }
        });

        return v;
    }
}