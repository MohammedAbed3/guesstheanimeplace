package com.tegogames.guesstheanimeplace;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class QuestionsAnswer extends AppCompatActivity {






    public static List<QuestionsList> questions(Context context) {
        final List<QuestionsList> questionsLists = new ArrayList<>();








        questionsLists.add(new QuestionsList(R.drawable.attacontitnen1,context.getString(R.string.attackontitan),context.getString(R.string.demonslayer),context.getString(R.string.bleach),context.getString(R.string.dragonball),context.getString(R.string.attackontitan),"","titan"));
        questionsLists.add(new QuestionsList(R.drawable.myheroacademia,context.getString(R.string.deathnote),context.getString(R.string.myheroacademia),context.getString(R.string.demonslayer),context.getString(R.string.fullmetalalchemisbrotherhood),context.getString(R.string.myheroacademia),"","iwant to be hero"));
        questionsLists.add(new QuestionsList(R.drawable.baclkclover2,context.getString(R.string.deathnote),context.getString(R.string.demonslayer),context.getString(R.string.baclkclover),context.getString(R.string.bleach),context.getString(R.string.baclkclover),"","wordl full of magec"));
        questionsLists.add(new QuestionsList(R.drawable.bleach,context.getString(R.string.dragonball),context.getString(R.string.onepiece),context.getString(R.string.bleach),context.getString(R.string.baclkclover),context.getString(R.string.bleach),"","sordt"));
        questionsLists.add(new QuestionsList(R.drawable.bluelock3,context.getString(R.string.bleach),context.getString(R.string.onepiece),context.getString(R.string.bluelock),context.getString(R.string.demonslayer),context.getString(R.string.bluelock),"","foot ball"));
        questionsLists.add(new QuestionsList(R.drawable.onepice,context.getString(R.string.hunterxhunter),context.getString(R.string.bleach),context.getString(R.string.onepiece),context.getString(R.string.onepunchman),context.getString(R.string.onepiece),"","مطاط"));


//        Collections.shuffle(questionsLists); // اخلط القائمة

        return  questionsLists;
    }








}


