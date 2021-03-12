package com.example.multiapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;


public class ResultFragment extends Fragment {

    TextView score;
    Button backHomeBtn;
    TextView mTotal;
    MyDBHelper helper;
    TextView tvScores;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        score = rootView.findViewById(R.id.quizScoreTxt);
        backHomeBtn = rootView.findViewById(R.id.backHomeBtn);
        mTotal = rootView.findViewById(R.id.totalQ);
        tvScores = rootView.findViewById(R.id.tvScores);


        score.setText(String.valueOf(QuestionFragment.score));
        mTotal.setText("Out of " + QuestionFragment.bankSize );

        helper = new MyDBHelper(getContext());
        String s = helper.getAllScores(QuestionFragment.difficulty);
        String[] scores = s.split(",");
        s = "";
        for(String string : scores){s = s + string + System.lineSeparator();}
        tvScores.setText("TOP SCORES" + System.lineSeparator() + s);

        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionFragment.score = 0;
                QuestionFragment.count = 0;
                Fragment fragment = new UserFragment();
                replaceFragment(fragment);
            }
        });


        return rootView;
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
