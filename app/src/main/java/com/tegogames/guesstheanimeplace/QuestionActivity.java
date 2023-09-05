package com.tegogames.guesstheanimeplace;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;

public class QuestionActivity extends AppCompatActivity {
    private TextView questions;
    private ImageView question;
    private int lastQuestionPosition = -1; // تخزين آخر سؤال عرضته

    private AdView mAdView;
    AdRequest adRequest;
    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton nextBtn;
    private Timer quizTimer;
    private int totalTimerInMins = 1;
    private int sconds = 0;
    private InterstitialAd mInterstitialAd;

    private int currentQuestionPosition = 0;
    private List<QuestionsList> questionsLists;
    private String selectedOptionByUser = "";
    private int questionsDisplayed = 0;
    ImageView hint;
    int counter = 0;
    private RewardedAd rewardedAd;
    private final String TAG = "MainActivity";
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentQuestionPosition = preferences.getInt("lastQuestionPosition", 0);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        displayAd();
        loadRewardedAd();

        ImageView backBtn = findViewById(R.id.backBtn);
//        TextView timer  =findViewById(R.id.timer);


//        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        hint = findViewById(R.id.btn_hint);
        progressBar = findViewById(R.id.progressbar);


        nextBtn = findViewById(R.id.nextBtn);

        questionsLists = QuestionsAnswer.questions(this);
//        startTimer(timer);

        if (currentQuestionPosition>=0&&currentQuestionPosition<questionsLists.size()){
//           questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
            question.setImageResource(questionsLists.get(currentQuestionPosition).getImageQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
        }else {
            currentQuestionPosition=-1;
            showConfirmationDialog();

        }



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                quizTimer.cancel();
//                quizTimer.purge();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option1.getText().toString();
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);
                    revealAnswer();
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option2.getText().toString();
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);
                    revealAnswer();
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option3.getText().toString();
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);
                    revealAnswer();
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option4.getText().toString();
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);
                    revealAnswer();
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });
        
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintFunc();


            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {

                    String tost = getResources().getString(R.string.tost_choose);
                    Toast.makeText(QuestionActivity.this, tost, Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }

            }
        });







    }

    private void changeNextQuestion() {
        currentQuestionPosition++;
        questionsDisplayed++;
        counter = 0;
        Log.d("hhh",counter+"");

        if (questionsDisplayed >= 10) {
            showInterstitialAd();

            // إعادة ضبط العداد
            questionsDisplayed = 0;
        } else {
            // في حالة عدم عرض الإعلان الوسيط هذه المرة، يمكنك هنا تأخير عرضه
        }
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastQuestionPosition", currentQuestionPosition);
        editor.apply();

        if (currentQuestionPosition < questionsLists.size()) {
            selectedOptionByUser = "";
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

//            questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
            question.setImageResource(questionsLists.get(currentQuestionPosition).getImageQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());

            // تفعيل تأثير الانتقال للسؤال التالي
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

            nextBtn.startAnimation(slideIn);
            option1.startAnimation(slideIn);
            option2.startAnimation(slideIn);
            option3.startAnimation(slideIn);
            option4.startAnimation(slideIn);
            hint.startAnimation(slideIn);


            // تفعيل تأثير الانتقال للصورة
            question.startAnimation(slideIn);

        } else {
            // انتهاء الأسئلة، يمكنك هنا إضافة التحول لشاشة النتائج
            startActivity(new Intent(getApplicationContext(),QuizFinishedActivity.class));
        }
    }





    private int getCorrectAnswers() {

        int correctAnswer = 0;
        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            if (getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswer++;
            }
        }

        return correctAnswer;
    }

    private int getInCorrectAnswers() {

        int inCorrectAnswer = 0;
        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            if (!getUserSelectedAnswer.equals(getAnswer)) {
                inCorrectAnswer++;
            }
        }

        return inCorrectAnswer;
    }
    public void result(){
        SharedPreferences sharedPreferences = getSharedPreferences("quizFinished", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putInt("inCorrectAnswer", getInCorrectAnswers());
        editor1.putInt("correctAnswer", getCorrectAnswers());
        editor1.apply();

        Intent intent = new Intent(getApplicationContext(), QuizFinishedActivity.class);
        intent.putExtra("correct", getCorrectAnswers());
        intent.putExtra("incorrect", getInCorrectAnswers());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
//        quizTimer.cancel();
//        quizTimer.purge();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void revealAnswer() {
        final String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();
        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        } else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        } else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        } else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String mass = getString(R.string.re_playe);
        String yes = getString(R.string.yes);
        String no = getString(R.string.no);
        builder.setMessage(mass)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startNewGame();
                    }
                })
                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getApplicationContext(),QuizFinishedActivity.class));

                    }
                })
                .show();
    }

    private void startNewGame() {
        currentQuestionPosition =0;


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastQuestionPosition", currentQuestionPosition);
        editor.apply();

        if (currentQuestionPosition < questionsLists.size()) {
            selectedOptionByUser = "";
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

//            questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
            question.setImageResource(questionsLists.get(currentQuestionPosition).getImageQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());

            // تفعيل تأثير الانتقال للسؤال التالي
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

            nextBtn.startAnimation(slideIn);
            option1.startAnimation(slideIn);
            option2.startAnimation(slideIn);
            option3.startAnimation(slideIn);
            option4.startAnimation(slideIn);
            hint.startAnimation(slideIn);


            // تفعيل تأثير الانتقال للصورة
            question.startAnimation(slideIn);

        } else {
            // انتهاء الأسئلة، يمكنك هنا إضافة التحول لشاشة النتائج
            startActivity(new Intent(getApplicationContext(),QuizFinishedActivity.class));
        }
    }

    private void displayAd() {


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-5472884752867366/4796712943", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // تم تحميل الإعلان بنجاح
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // فشل تحميل الإعلان، يمكنك إضافة إجراءات تنفيذية إضافية هنا
                        Log.d("TAG", loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });


    }
    private void loadRewardedAd() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-5472884752867366/7553107919", adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        // تم تحميل الإعلان بنجاح
                        rewardedAd = ad;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // فشل تحميل الإعلان، يمكنك إضافة إجراءات تنفيذية إضافية هنا
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;

                    }
                });
    }

    private void showInterstitialAd() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(QuestionActivity.this);
                    // الأكشن بعد ظهور الإعلان
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // عندما يتم إغلاق الإعلان
//                            startActivity(new Intent(QuestionActivity.this, QuestionActivity.class));
//                             (QuestionActivity.this);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // في حالة حدوث خطأ أثناء عرض الإعلان بشكل كامل
                            Log.d("TAG", "Failed to show interstitial ad.");
//                            progressBar.setVisibility(View.GONE); // أخفي دائرة التحميل
//                            startActivity(new Intent(QuestionActivity.this, QuestionActivity.class));
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
//                    progressBar.setVisibility(View.GONE); // أخفي دائرة التحميل في حالة عدم توفر الإعلان
//                    startActivity(new Intent(QuestionActivity.this, QuestionActivity.class));
                    // لا تقم بإغلاق النشاط الحالي (ResultActivity)
                }
            }
        }, 3000); // تأخير لمدة 2 ثانية (2000 مللي ثانية)

    }


//    private void hintFunc(){
//
//
//        String h= questionsLists.get(currentQuestionPosition).getHint();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(h);
//        builder.setCancelable(false); // لجعل الحوار غير قابل للإغلاق بالنقر خارجه
//
//// إضافة زر للإغلاق
//        builder.setPositiveButton("إغلاق", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // هنا يمكنك إضافة أي شيء تريده عند النقر على زر الإغلاق
//                dialog.dismiss(); // لإغلاق مربع الحوار
//            }
//        });
//
//// إنشاء وعرض مربع الحوار
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }

    private void hintFunc() {

        // التحقق من وجود اتصال بالإنترنت
        if (isNetworkAvailable()) {
            // قم بتحميل وعرض الإعلان هنا
            progressBar.setVisibility(View.VISIBLE); // أظهر دائرة التحميل عند النقر على الزر

            // إضافة تأخير لمدة 2 ثانية قبل ظهور الإعلان
            final Handler handler = new Handler();

            if (counter <=0){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (rewardedAd != null) {
                            Activity activityContext = QuestionActivity.this;
                            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    Log.d(TAG, "The user earned the reward.");
                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();
                                    progressBar.setVisibility(View.GONE); // أظهر دائرة التحميل عند النقر على الزر

                                }


                            });
                            // الأكشن بعد ظهور الإعلان
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    showHintDalog();
                                    counter = 1;
                                    loadRewardedAd();
                                    progressBar.setVisibility(View.GONE); // أظهر دائرة التحميل عند النقر على الزر

                                }




                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // في حالة حدوث خطأ أثناء عرض الإعلان بشكل كامل
                                    Log.d("TAG", "Failed to show interstitial ad.");
//                                progressBar.setVisibility(View.GONE); // أخفي دائرة التحميل
                                    Toast.makeText(getApplicationContext(),"لا يوجد اتصال بالإنترنت",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE); // أظهر دائرة التحميل عند النقر على الزر


//                                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                                }
                            });
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                            Toast.makeText(getApplicationContext(),"لا يوجد اتصال بالإنترنت",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE); // أظهر دائرة التحميل عند النقر على الزر

//                        progressBar.setVisibility(View.GONE); // أخفي دائرة التحميل في حالة عدم توفر الإعلان
//                        startActivity(new Intent(ResultActivity.this, MainActivity.class));
                            // لا تقم بإغلاق النشاط الحالي (ResultActivity)
                        }
                    }
                }, 3000); // تأخير لمدة 2 ثانية (2000 مللي ثانية)

            }else {
                showHintDalog();
            }
        }

     else{
        // لا يوجد اتصال بالإنترنت، يمكنك تنفيذ إجراءات أخرى هنا (مثل عرض التلميح مباشرة)
        // أو عرض رسالة تفيد بعدم وجود اتصال بالإنترنت
        // على سبيل المثال:
        Toast.makeText(this,"لا يوجد اتصال بالإنترنت",Toast.LENGTH_SHORT).show();
        // أو عرض التلميح مباشرة
            progressBar.setVisibility(View.GONE); // أظهر دائرة التحميل عند النقر على الزر

            // builder.setMessage(h).show();
        }}







    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showHintDalog(){
        String h = questionsLists.get(currentQuestionPosition).getHint();

        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
        builder.setMessage(h);
        builder.setCancelable(false); // لجعل الحوار غير قابل للإغلاق بالنقر خارجه

// إضافة زر للإغلاق
        builder.setPositiveButton("إغلاق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // هنا يمكنك إضافة أي شيء تريده عند النقر على زر الإغلاق
                dialog.dismiss(); // لإغلاق مربع الحوار
            }
        });

// إنشاء وعرض مربع الحوار
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}