package com.example.online_shopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.Adapter.CartAdminAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.CartAdminDatabase;
import com.example.online_shopping.model.CartAdminModel;

import java.util.List;

public class CartAdminFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart_admin, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        RecyclerView recyclerView=v.findViewById(R.id.cart_admin);

        EditText date=v.findViewById(R.id.dateET);
        Button search=v.findViewById(R.id.searchBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "pleace enter date", Toast.LENGTH_LONG).show();
                }
                else
                {
                    CartAdminAdapter cartAdminAdapter=new CartAdminAdapter();
                    CartAdminDatabase.getInstance(getActivity()).Deo().getAllProductsByDate(date.getText().toString()).observe(getActivity(), new Observer<List<CartAdminModel>>() {
                        @Override
                        public void onChanged(List<CartAdminModel> cartAdminModels) {
                            if(!cartAdminModels.isEmpty())
                            {
                                cartAdminAdapter.setList(cartAdminModels);
                                recyclerView.setAdapter(cartAdminAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            }else
                            {
                                Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


        return v;
    }

}