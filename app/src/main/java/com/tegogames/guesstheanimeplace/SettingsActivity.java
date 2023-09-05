package com.tegogames.guesstheanimeplace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tegogames.guesstheanimeplace.Language.LanguageManager;

public class SettingsActivity extends AppCompatActivity {


    private int selectedLanguageIndex = 0; // المؤشر الافتراضي للغة العربية

    LanguageManager languageManager;
    String lang;
    LanguageManager manager;
    ImageView back;

    Button updataLang,about,connect_us, shareButton,rateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageManager = new LanguageManager(this);
        lang= languageManager.getLang();
        manager = new LanguageManager(this);

        updataLang = findViewById(R.id.setting_tv_lang);
        about = findViewById(R.id.btn_about);
        connect_us = findViewById(R.id.btn_connect_us);
        rateButton = findViewById(R.id.btn_rate_app);
        back = findViewById(R.id.setting_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        updataLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            }
        });
        connect_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ConnectUsActivity.class));

            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        shareButton = findViewById(R.id.btn_share_app);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
    }

    private void showConfirmationDialog() {
        String lang_select = getString(R.string.lang_select);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(lang_select);

        // تضمين تخطيط القائمة من ملف التصميم dialog_language_selection.xml
        View view = getLayoutInflater().inflate(R.layout.dialog_language_selection, null);
        builder.setView(view);

        // الحصول على المكونات المطلوبة من التخطيط
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButtonArabic = view.findViewById(R.id.radioButtonArabic);
        RadioButton radioButtonEnglish = view.findViewById(R.id.radioButtonEnglish);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int selectedButtonId = preferences.getInt("SelectedButtonId", R.id.radioButtonArabic);
        radioGroup.check(selectedButtonId);

        String confirm = getString(R.string.confirm);
        String cancel = getString(R.string.cancel);

        // الإجراء عند الضغط على زر "ذهاب"
        builder.setPositiveButton(confirm, (dialog, which) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("SelectedButtonId", selectedId);
            editor.apply();
            if (selectedId == R.id.radioButtonArabic) {
                updataLanguage("ar");

            } else if (selectedId == R.id.radioButtonEnglish) {
                updataLanguage("en");
            }

            recreate();
        });

        // زر الإغلاق
        builder.setNegativeButton(cancel, (dialog, which) -> {
            dialog.dismiss();
            recreate();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My App"); // عنوان المشاركة

        // رابط التطبيق
        String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();

        // نص المشاركة مع رابط التطبيق
        String invite = getString(R.string.invite);
        String shareMessage = invite+"\n\n" + appLink;

        intent.putExtra(Intent.EXTRA_TEXT, shareMessage); // نص المشاركة
        startActivity(Intent.createChooser(intent, "Share via")); // افتح واجهة المشاركة واختر التطبيق الذي ترغب في المشاركة من خلاله
    }
    public void updataLanguage(String lang) {
        if (lang.equals("ar")) {
            manager.updateResource("ar");
            recreate();


        } else if(lang.equals("en")) {
            manager.updateResource("en");
            recreate();

        }
    }


    private void rateApp() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            // إذا كان لم يتم العثور على متجر التطبيقات (مثل Google Play)، قم بفتح متصفح الويب لصفحة تطبيقك على متجر التطبيقات
            Uri webUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent webRateIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webRateIntent);
        }
    }
}