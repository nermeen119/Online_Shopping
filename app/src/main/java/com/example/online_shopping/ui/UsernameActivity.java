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

public class UsernameActivity extends AppCompatActivity {

    EditText uname_txt,job;
    Button reset_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        uname_txt = (EditText)findViewById(R.id.userName);
        job = (EditText)findViewById(R.id.job);
        reset_btn = (Button)findViewById(R.id.btnChange);

        reset_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username=uname_txt.getText().toString();
                String jobStr=job.getText().toString();

                if(username.isEmpty())
                {
                    Toast.makeText(UsernameActivity.this, "Username is empty", Toast.LENGTH_LONG).show();
                }
                else if(jobStr.isEmpty())
                {
                    Toast.makeText(UsernameActivity.this, "Job is empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //perform Query
                    UserDatabase userDatabase=UserDatabase.getUsersDatabase(getApplicationContext());

                    final UserDao userDao=userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            UserEntity userEntity = userDao.CheckUsername(username,jobStr);
                            if(userEntity==null)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UsernameActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();


                                    }
                                });
                            }
                            else{
                                Intent i = new Intent(UsernameActivity.this, ResetPasswordActivity.class);
                                i.putExtra("username", uname_txt.getText().toString());
                                i.putExtra("job", job.getText().toString());
                                startActivity(i);
                            }

                        }
                    }).start();


                }

            }
        });


    }
}
