package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.widget.TextView;

import com.example.online_shopping.R;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        address = findViewById(R.id.address_set_tv);
        String address_str = getIntent().getStringExtra("address");
        address.setText(address_str);

    }
}