package com.example.multiapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
    Context c;
    String difficulty = "easy";
    public Parser(Context co) throws IOException {
        c = co;
    }
    public String LoadData(String inFile, String pDifficulty) {
        difficulty = "easy";
        String tContents = "";

        try {
            InputStream stream = c.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    public ArrayList<String> sortData(String input){
        ArrayList<String> output = new ArrayList<String>();
        input = input.substring(input.indexOf("["), input.lastIndexOf("]"));
        while(input.length() > 1){
            String temp;
            int start = input.indexOf("{");
            int end = input.indexOf("}");
            output.add(input.substring(start, end + 1));
            input = input.substring(end + 1);
        }
        return output;
    }

    public QuestionMultiple[] makeQuestions(ArrayList<String> input){
        QuestionMultiple[] output = new QuestionMultiple[50];
        ArrayList<QuestionMultiple> out = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++){
            String currentSelected = input.get(i);

            String question = currentSelected.substring(currentSelected.indexOf("Q") + 11);
            currentSelected = question;
            question = question.substring(0, question.indexOf('"' ));


            String possibleOptions = currentSelected.substring(currentSelected.indexOf("[") + 1, currentSelected.indexOf("]"));
            currentSelected = currentSelected.substring(currentSelected.indexOf("]") + 1);
            String[] options = possibleOptions.split(",");

            String Answers = currentSelected.substring(currentSelected.indexOf("[") + 1, currentSelected.indexOf("]"));
            String[] answers = Answers.split(",");
            ArrayList<Integer> temp = new ArrayList<>();
            for(int t = 0; t < answers.length; t++){
                if(answers[t] != ""){
                    temp.add( Integer.parseInt(answers[t]));}
            }
            Integer[] realAnswer = temp.toArray(new Integer[temp.size()]);
            int[] x = new int[realAnswer.length];
            for (int t = 0;t < realAnswer.length; t++ ){
                x[t] = realAnswer[t];
            }


            int picture = 0;


            counter++;


        }
        output = out.toArray(new QuestionMultiple[out.size()]);
        return output;
    }

}



