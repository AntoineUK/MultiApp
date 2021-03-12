package com.example.multiapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private Button mToDifficulty;
    private EditText etName;
    public static String name = "";
    ListView highScores;
    MyDBHelper helper;
    ArrayList<String> scores = new ArrayList<String>();
    String[] scoreBits;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        highScores = rootView.findViewById(R.id.high_scores_list);

        name = "";
        helper = new MyDBHelper(getContext());
        String a = helper.getAllScores("easy") + helper.getAllScores("hard");
        String[] temp = a.split(",");
//        Toast.makeText(getActivity(), a,
//                Toast.LENGTH_SHORT).show();


        //a = "  ";
        for (String string : temp) {
            a = a + " " + string;
            if (!a.contains(" ")){
                scores.add(a);
            }
            if (scores.size() < 1) {
                scores.add("Scores");
            }
            if (a.contains(" ") && a.contains(":")) {
                scoreBits = a.split(" ");
                String lastOne = scoreBits[scoreBits.length - 1];
                scores.add("     " + lastOne);
            }
        }
        //tvScores.setText("TOP SCORES" + System.lineSeparator() + s);


        ArrayAdapter<String> highScoresAdapter = new ArrayAdapter<String>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, scores);
        highScores.setAdapter(highScoresAdapter);


        //get references to widgets
        mToDifficulty = (Button) rootView.findViewById(R.id.createAccountBtn);
        etName = (EditText) rootView.findViewById(R.id.etName);

        mToDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
                name = etName.getText().toString();
            }
        });

        return rootView;
    }


    public void replaceFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();

    }
}