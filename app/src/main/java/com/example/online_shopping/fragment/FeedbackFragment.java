package com.example.online_shopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.Adapter.FeedbackAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.FeedbackDatabase;
import com.example.online_shopping.model.FeedbackModel;
import com.example.online_shopping.user.RemoveClick;

import java.util.List;

public class FeedbackFragment extends Fragment implements RemoveClick {

    String userName;

    public FeedbackFragment(String name)
    {
        userName=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);

        RecyclerView recyclerView=v.findViewById(R.id.feedback_recy);
        EditText feed=v.findViewById(R.id.feedET);
        Button addFeedback=v.findViewById(R.id.addFeedbackBtn);

        addFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feed.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Add Feedback is empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    FeedbackModel feedbackModel=new FeedbackModel(feed.getText().toString(),userName);
                    try{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FeedbackDatabase.getInstance(getActivity()).Deo().insert(feedbackModel);
                            }
                        }).start();
                        Toast.makeText(getActivity().getApplicationContext(), "Added to Feedback", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "error while add feedback", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        FeedbackAdapter feedbackAdapter=new FeedbackAdapter(this);
        FeedbackDatabase.getInstance(getActivity()).Deo().getAllFeedbackByUserName(userName).observe(getActivity(), new Observer<List<FeedbackModel>>() {
            @Override
            public void onChanged(List<FeedbackModel> feedbacks) {
                if(!feedbacks.isEmpty())
                {
                    feedbackAdapter.setList(feedbacks);
                    recyclerView.setAdapter(feedbackAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

            }
        });
        return v;
    }

        @Override
    public void onClickRemove(Integer click)
        {
            try{
                new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        FeedbackDatabase.getInstance(getActivity()).Deo().deleteFeedback(click);
                    }
                }).start();
                Toast.makeText(getActivity().getApplicationContext(), "feedback deleted", Toast.LENGTH_LONG).show();
                FragmentTransaction fragmentTransaction1=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.bottom_container,new FeedbackFragment(userName)).commit();
            }catch (Exception e)
            {
                Toast.makeText(getActivity().getApplicationContext(), "error while delete", Toast.LENGTH_LONG).show();
            }
        }
}