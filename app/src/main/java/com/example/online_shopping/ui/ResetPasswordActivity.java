package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.database.UserDatabase;
import com.example.online_shopping.model.UserEntity;
import com.example.online_shopping.user.UserDao;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText newpass1, newpass2;
    Button resetpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        newpass1 = (EditText)findViewById(R.id.password);
        newpass2 = (EditText)findViewById(R.id.confirmPass);
        resetpass = (Button) findViewById(R.id.btnConfirm);

        Intent i = getIntent();
        String username = i.getStringExtra("username");
        String jobStr = i.getStringExtra("job");

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = newpass1.getText().toString();
                String s2 = newpass2.getText().toString();

                if(s1.isEmpty())
                {
                    Toast.makeText(ResetPasswordActivity.this, "Password is empty", Toast.LENGTH_LONG).show();
                }
                else if(s2.isEmpty())
                {
                    Toast.makeText(ResetPasswordActivity.this, "Confirm Password is empty", Toast.LENGTH_LONG).show();
                }
                else if(s1.length()<6)
                {
                    Toast.makeText(ResetPasswordActivity.this, "Password must be greater than 6 ", Toast.LENGTH_LONG).show();

                }
                else if(s1.equals(s2))
                {
                    UserDatabase userDatabase= UserDatabase.getUsersDatabase(getApplicationContext());
                    final UserDao userDao=userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // update old pass
                            UserEntity userEntity = userDao.CheckUsername(username, jobStr);
                            userEntity.setPassword(s1);
                            userDao.UpdatePassword(userEntity);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Password successfully updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));

                                }
                            });

                        }
                    }).start();
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, "Confirm password not equal password ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}