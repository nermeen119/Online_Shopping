package com.example.online_shopping.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.fragment.HomeFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetAddressActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;

    EditText address_etxt, city_etxt, country_etxt;
    Button getLocation_btn, continue_btn;

    String name, visit=null;
    int length;
    String[] title=new String[1000];
    String[] time=new String[1000];
    int[] quntity=new int[1000];
    int[] image=new int[1000];
    double[] totalPrice=new double[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_address);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        name=getIntent().getStringExtra("username");
        title= getIntent().getStringArrayExtra("itemTitle");
        time= getIntent().getStringArrayExtra("time");
        totalPrice=getIntent().getDoubleArrayExtra("totalPrice");
        quntity=getIntent().getIntArrayExtra("quntity");
        image=getIntent().getIntArrayExtra("image");
        length=Integer.parseInt(getIntent().getStringExtra("length"));


        final Dialog dialog = new Dialog(GetAddressActivity.this);
        address_etxt = findViewById(R.id.address_etxt);
        address_etxt.setEnabled(false);

        city_etxt = findViewById(R.id.city_etxt);
        city_etxt.setEnabled(false);

        country_etxt = findViewById(R.id.country_etxt);
        country_etxt.setEnabled(false);

        getLocation_btn = findViewById(R.id.getlocation_btn);
        continue_btn = findViewById(R.id.contiune_btn);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.get_map);
                Button GoToHome = dialog.findViewById(R.id.gohome);
                TextView location=dialog.findViewById(R.id.text_location);
                location.setText(address_etxt.getText().toString() + ", "
                        + country_etxt.getText().toString() + ", " + city_etxt.getText()
                        .toString());

                GoToHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(GetAddressActivity.this, "Transaction Complete", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(GetAddressActivity.this, RatingActivity.class);
                        i.putExtra("username", name);
                        i.putExtra("itemTitle",title);
                        i.putExtra("time",time);
                        i.putExtra("quntity",quntity);
                        i.putExtra("image",image);
                        i.putExtra("totalPrice",totalPrice);
                        i.putExtra("length",length);
                        visit="visit";
                        i.putExtra("visit",visit);
                        startActivity(i);

                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });
    }

    private void getLastLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(GetAddressActivity.this, Locale.getDefault());
                                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    address_etxt.setText(addressList.get(0).getAddressLine(0));
                                    city_etxt.setText(addressList.get(0).getLocality());
                                    country_etxt.setText(addressList.get(0).getCountryName());
                                    address_etxt.setEnabled(true);
                                    country_etxt.setEnabled(true);
                                    city_etxt.setEnabled(true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(GetAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(GetAddressActivity.this, "Please provide the required permission to access location", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}