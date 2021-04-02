package com.example.multiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;


public class ResultFragment extends Fragment {

    TextView score;
    Button backHomeBtn;
    TextView mTotal;
    TextView playerView;
    MyDBHelper helper;
    TextView tvScores;
    EditText editText;
    String value;
    String playerName = "";
    String playerScore = "";

    FirebaseDatabase database;
    DatabaseReference scoreResult;
    DatabaseReference playerRef;
            //= scoreResult.child("players").getRef();;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        score = rootView.findViewById(R.id.quizScoreTxt);
        backHomeBtn = rootView.findViewById(R.id.backHomeBtn);
        mTotal = rootView.findViewById(R.id.totalQ);
        tvScores = rootView.findViewById(R.id.tvScores);
        playerView = rootView.findViewById(R.id.playerName);

        score.setText(String.valueOf(QuestionFragment.score));
        mTotal.setText("Out of " + QuestionFragment.bankSize );
        playerScore = String.valueOf(QuestionFragment.score);
        helper = new MyDBHelper(getContext());
        String s = helper.getAllScores(QuestionFragment.difficulty);
        String[] scores = s.split(",");
        s = "";
        for(String string : scores){s = s + string + System.lineSeparator();}
        tvScores.setText("TOP SCORES" + System.lineSeparator() + s);

        database = FirebaseDatabase.getInstance();
        scoreResult = database.getReference("score/"+playerName);


        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoi du score
                value = "Player Name :" + playerName + "Score : " + playerScore ;
                scoreResult.setValue(value);
                QuestionFragment.score = 0;
                QuestionFragment.count = 0;
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        playerRef = database.getReference("players");
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playerName = snapshot.getValue().toString();
                playerView.setText(playerName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
