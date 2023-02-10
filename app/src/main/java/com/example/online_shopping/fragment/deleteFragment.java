package com.example.online_shopping.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.database.categoryDataBase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.product;
import com.example.online_shopping.ui.UsernameActivity;
import com.example.online_shopping.ui.button_navigate;

import java.util.List;


public class deleteFragment extends Fragment {
    EditText txTitle;
    Button btDelete,btback;
    String title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_delete, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        txTitle=v.findViewById(R.id.editTitle);
        btDelete=v.findViewById(R.id.removeitem);
        btback=v.findViewById(R.id.back);
        title=btDelete.getText().toString();

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            categoryDataBase.Get_Instance(getActivity()).category_dao().deleteCategorytWithName(title);
                        }

                    }).start();
                    Toast.makeText(getActivity(), "category removed", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDataBase.Get_Instance(getActivity()).category_dao().getcategory().observe(getActivity(), new Observer<List<CategoryModel>>() {
                    @Override
                    public void onChanged(List<CategoryModel> categoryModels) {
                        if (!categoryModels.isEmpty()) {
                            HomeFragment.categoryAdapter.setCategoryModel(categoryModels);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                            HomeFragment.recyclerView.setHasFixedSize(true);
                            HomeFragment.recyclerView.setAdapter(HomeFragment.categoryAdapter);
                           HomeFragment.recyclerView.setLayoutManager(layoutManager2);
                        } else {
                            categoryModels.clear();

                        }
                    }
                });
                startActivity(new Intent(getActivity(),button_navigate.class));
            }
        });
        return v;
    }
}