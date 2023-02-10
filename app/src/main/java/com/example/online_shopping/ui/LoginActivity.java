package com.example.online_shopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.R;
import com.example.online_shopping.database.UserDatabase;
import com.example.online_shopping.model.UserEntity;
import com.example.online_shopping.user.UserDao;

public class LoginActivity extends AppCompatActivity {

    TextView forgot_pass;
    EditText usernameET,passwordET;
    CheckBox checkBoxRemeberMe;
    Button login;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME="PrefsFile";
    public static String username;
    public static String  password;
     String adminName;
     String  adminPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
        //admin login
         adminName="admin";
         adminPassword="123456";
        sharedPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        forgot_pass = (TextView) findViewById(R.id.forgetPass);
        usernameET=findViewById(R.id.userName);
        passwordET=findViewById(R.id.password);
        checkBoxRemeberMe=findViewById(R.id.checkRememberMe);
        login=findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                if (username.contains(adminName) && password.contains(adminPassword)) {
                    startActivity(new Intent(LoginActivity.this, button_navigate.class));
                } else {
                    if (username.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Username is empty", Toast.LENGTH_LONG).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_LONG).show();
                    } else {
                        //perform Query

                        UserDatabase userDatabase = UserDatabase.getUsersDatabase(getApplicationContext());
                        final UserDao userDao = userDatabase.userDao();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //Register User
                                UserEntity userEntity = userDao.login(username, password);
                                if (userEntity == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    if (checkBoxRemeberMe.isChecked()) {
                                        Boolean aBoolean = checkBoxRemeberMe.isChecked();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("pref_name", username);
                                        editor.putString("pref_pass", password);
                                        editor.putBoolean("pref_check", aBoolean);
                                        editor.apply();

                                    } else {
                                        sharedPreferences.edit().clear().apply();
                                    }
                                    Intent i = new Intent(LoginActivity.this, button_navigate.class);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                }
                            }
                        }).start();
                    }

                }
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UsernameActivity.class));
            }
        });

        getPreferencesData();

    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(
                PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found.");
            usernameET.setText(u.toString());
        }
        if (sp.contains("pref_pass")) {
            String p = sp.getString("pref_pass", "not found.");
            passwordET.setText(p.toString());
        }
        if (sp.contains("pref_check")) {
            Boolean b = sp.getBoolean("pref_check", false);
            checkBoxRemeberMe.setChecked(b);
        }
    }

    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}