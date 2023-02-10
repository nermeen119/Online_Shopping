package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.database.UserDatabase;
import com.example.online_shopping.model.UserEntity;
import com.example.online_shopping.user.UserDao;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private DatePickerDialog picker;

    EditText username,password,confirmpass,job,calenderET,genderET;
    String[] list_gender={"Male","Female"};
    Button sinUp;
    String gender="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        username =findViewById(R.id.userName);
        password=findViewById(R.id.password);
        confirmpass=findViewById(R.id.confirmPass);
        job=findViewById(R.id.job);
        calenderET=findViewById(R.id.calender);
        genderET=findViewById(R.id.gender);
        sinUp=findViewById(R.id.btnSinUp);
        //radioGroup=findViewById(R.id.radioGroup);

        calenderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);

                // DatePicker
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calenderET.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                },year, month, day);
                picker.show();
            }
        });

        genderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mb=new AlertDialog.Builder(RegisterActivity.this);
                mb.setTitle("Select gender");
                mb.setIcon(R.drawable.icon_gender);
                mb.setSingleChoiceItems(list_gender, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        genderET.setText(list_gender[which]);
                        gender=list_gender[which];
                        dialog.dismiss();
                    }
                });
                mb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog mDialog=mb.create();
                mDialog.show();

            }
        });


        sinUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // int radioId=radioGroup.getCheckedRadioButtonId();

                UserDatabase userDatabase= UserDatabase.getUsersDatabase(getApplicationContext());
                UserEntity userEntity=new UserEntity();

                userEntity.setUsername(username.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setJob(job.getText().toString());
                userEntity.setCalendar(calenderET.getText().toString());
                userEntity.setGender(genderET.getText().toString());


                if(validDataInput(userEntity, confirmpass.getText().toString()))
                {
                    //Do insert
                    final UserDao userDaoo=userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Register User
                            userDaoo.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                                }
                            });
                        }
                    }).start();
                }

            }
        });

    }

    private Boolean validDataInput(UserEntity userEntity,String confirm)
    {
        if(userEntity.getUsername().isEmpty())
        {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        else if(userEntity.getPassword().isEmpty())
        {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(confirm.isEmpty())
        {
            Toast.makeText(this, "Confirm Password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userEntity.getJob().isEmpty())
        {
            Toast.makeText(this, "Job is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userEntity.getCalendar().isEmpty())
        {
            Toast.makeText(this, "Select Birthdate is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        else if(userEntity.getPassword().length()<6)
        {
            Toast.makeText(this, "Password must be greater than 6 ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!userEntity.getPassword().matches(confirm))
        {
            Toast.makeText(this, "Confirm password not equal password ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userEntity.getGender().isEmpty())
        {
            Toast.makeText(this, "please select gender ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}