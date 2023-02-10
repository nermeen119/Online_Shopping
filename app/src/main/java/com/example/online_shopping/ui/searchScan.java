package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class searchScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    String name;
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_scan);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        name=getIntent().getStringExtra("username");
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

                Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        goToLink(rawResult.getText());
        onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    private void goToLink(String link)
    {
       // Uri uri=Uri.parse(link);
       // System.out.println("link" + (link));
        if (link.equals("https://qrco.de/bdalxL")) {

            womenActivity.filterList("red dress");
            womenActivity.productsarr.clear();
            flag=false;

        }
        if (link.equals("https://qrco.de/bdanIi")) {

            menActivity.filterList("Solid Formal");
            menActivity.productsarr.clear();
            flag=false;
        }

        if (link.equals("https://qrco.de/bdanbq")) {

            jeweleryActivity.filterList("pierced Owl");
            jeweleryActivity.productsarr.clear();
            flag=false;
        }

        if (link.equals("https://qrco.de/bdanOv")) {
            ElecronicsActivity.filterList("Huawei");
            ElecronicsActivity.productsarr.clear();
            flag=false;
        }
        if (flag==true )
        {
            Toast.makeText(searchScan.this, "Not Found product here", Toast.LENGTH_LONG).show();
           // flag=false;
            womenActivity.productsarr.clear();
            ElecronicsActivity.productsarr.clear();
            jeweleryActivity.productsarr.clear();
            menActivity.productsarr.clear();

        }
        womenActivity.productsarr.clear();
        ElecronicsActivity.productsarr.clear();
        jeweleryActivity.productsarr.clear();
        menActivity.productsarr.clear();
//        else {
//            Toast.makeText(searchScan.this, "Not Found product here", Toast.LENGTH_LONG).show();
//        }
//        Intent i=new Intent(Intent.ACTION_VIEW,uri);
//        i.putExtra("username", name);
//        startActivity(i);
    }

}