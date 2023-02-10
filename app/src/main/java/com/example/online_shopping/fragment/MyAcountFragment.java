package com.example.online_shopping.fragment;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.online_shopping.R;
import com.example.online_shopping.database.UserDatabase;
import com.example.online_shopping.model.UserEntity;
import com.example.online_shopping.user.UserDao;


public class MyAcountFragment extends Fragment
{

    String userName;
    TextView userTE, job,gender,birthdate;
    UserEntity user;

    public MyAcountFragment(String name) {
        this.userName=name;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_acount, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        userTE = v.findViewById(R.id.userName);
        job = v.findViewById(R.id.job);
        gender = v.findViewById(R.id.gender);
        birthdate = v.findViewById(R.id.calender);

        UserDatabase userDatabase = UserDatabase.getUsersDatabase(getActivity());
        final UserDao userDao = userDatabase.userDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity = userDao.getAccountByName(userName);

                userTE.setText("Name: " + userEntity.getUsername());
                job.setText("Job: " + userEntity.getJob());
                gender.setText("gender: " + userEntity.getGender());
                birthdate.setText("Birthdate: " + userEntity.getCalendar());
            }
        }).start();

        return v;


    }
}