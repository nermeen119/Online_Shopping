package com.example.online_shopping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;

import com.example.online_shopping.R;

public class RatingActivity extends AppCompatActivity {

    AppCompatButton rateNowBtn,laterBtn;
    RatingBar ratingBar;
    ImageView ratingImage;
    String name , visit;
    int length;
    String[] title=new String[1000];
    String[] time=new String[1000];
    int[] quntity=new int[1000];
    int[] image=new int[1000];
    double[] totalPrice=new double[1000];
    String rate="Not rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

         rateNowBtn=findViewById(R.id.rateNowBtn);
         laterBtn=findViewById(R.id.laterBtn);
         ratingBar= findViewById(R.id.ratingBar);
         ratingImage=findViewById(R.id.imoji);

        name = getIntent().getStringExtra("username");
        visit = getIntent().getStringExtra("visit");
        title= getIntent().getStringArrayExtra("itemTitle");
        time= getIntent().getStringArrayExtra("time");
        totalPrice=getIntent().getDoubleArrayExtra("totalPrice");
        quntity=getIntent().getIntArrayExtra("quntity");
        image=getIntent().getIntArrayExtra("image");
        length=getIntent().getIntExtra("length",length);


        rateNowBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(RatingActivity.this, button_navigate.class);
                 i.putExtra("username", name);
                 i.putExtra("itemTitle",title);
                 i.putExtra("time",time);
                 i.putExtra("quntity",quntity);
                 i.putExtra("image",image);
                 i.putExtra("totalPrice",totalPrice);
                 i.putExtra("length",length);
                 rate=String.valueOf(ratingBar.getRating());
                 i.putExtra("rate",rate);
                 i.putExtra("visit",visit);
                 startActivity(i);
             }
         });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RatingActivity.this, button_navigate.class);
                i.putExtra("username", name);
                i.putExtra("itemTitle",title);
                i.putExtra("time",time);
                i.putExtra("quntity",quntity);
                i.putExtra("image",image);
                i.putExtra("totalPrice",totalPrice);
                i.putExtra("length",length);
                rate=String.valueOf(ratingBar.getRating());
                i.putExtra("visit",visit);
                i.putExtra("rate","not rate");
                startActivity(i);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <= 1)
                {
                    ratingImage.setImageResource(R.drawable.icon_sad_imoji);
                }
                else if(rating <= 2)
                {
                    ratingImage.setImageResource(R.drawable.icon_sad_imoji);
                }
                else if(rating <= 3)
                {
                    ratingImage.setImageResource(R.drawable.icon_imoji_simi_happy);
                }
                else if(rating <= 4)
                {
                    ratingImage.setImageResource(R.drawable.icon_happy_imoji);
                }else if(rating <= 5)
                {
                    ratingImage.setImageResource(R.drawable.icon_happy_imoji);
                }

                //animation emoji
                animateImage(ratingImage);

            }
        });


    }


    private void animateImage(ImageView ratingImage)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);

    }

}