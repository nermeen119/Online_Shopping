package com.example.online_shopping.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_shopping.Adapter.CartAdminAdapter;
import com.example.online_shopping.Adapter.OrderAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartAdminDatabase;
import com.example.online_shopping.model.CartAdminModel;

import java.util.List;

public class OrderFragment extends Fragment {

    String userName;

    public OrderFragment(String name)
    {
        userName=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        RecyclerView recyclerView=v.findViewById(R.id.cart_admin);

        OrderAdapter orderAdapter=new OrderAdapter();
        CartAdminDatabase.getInstance(getActivity()).Deo().getAllProductsByName(userName).observe(getActivity(), new Observer<List<CartAdminModel>>() {
            @Override
            public void onChanged(List<CartAdminModel> cartAdminModels) {
                if(!cartAdminModels.isEmpty())
                {
                    orderAdapter.setList(cartAdminModels);
                    recyclerView.setAdapter(orderAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

            }
        });

        return v;
    }


}