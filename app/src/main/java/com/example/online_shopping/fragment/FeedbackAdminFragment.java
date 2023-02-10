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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shopping.Adapter.FeedbackAdminAdapter;
import com.example.online_shopping.R;
import com.example.online_shopping.database.FeedbackDatabase;
import com.example.online_shopping.model.FeedbackModel;
import com.example.online_shopping.user.RemoveClick;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdminFragment extends Fragment implements RemoveClick {

    List<FeedbackModel> feedbackModels;


    FeedbackModel f=new FeedbackModel();

    FeedbackAdminAdapter feedbackAdminAdapter=new FeedbackAdminAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feedback_admin, container, false);
        ViewCompat.setLayoutDirection(getActivity().getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        //SearchView searchView=v.findViewById(R.id.searchUser);
        RecyclerView recyclerView=v.findViewById(R.id.feedback_admin);

        EditText user=v.findViewById(R.id.feedET);
        Button search=v.findViewById(R.id.searchBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "pleace enter user name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    FeedbackAdminAdapter feedbackAdapter=new FeedbackAdminAdapter();
                    FeedbackDatabase.getInstance(getActivity()).Deo().getAllFeedbackByUserName(user.getText().toString()).observe(getActivity(), new Observer<List<FeedbackModel>>() {
                        @Override
                        public void onChanged(List<FeedbackModel> feedbacks) {
                            if(!feedbacks.isEmpty())
                            {
                                feedbackAdapter.setList(feedbacks);
                                recyclerView.setAdapter(feedbackAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });

        /*searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });*/

        return v;
    }


    private void filterList(String userName) {
        ArrayList<FeedbackModel> filteredList = new ArrayList<>();

        for (FeedbackModel model : feedbackModels) {
            if (model.getUserName().toLowerCase().contains(userName.toLowerCase())) {
                filteredList.add(model);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            feedbackAdminAdapter.setFilteredList(filteredList);


        }
    }

    @Override
    public void onClickRemove(Integer click) {

    }
}