package com.tegogames.guesstheanimeplace.Language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    private Context context;
    private static final String PREF_LANG = "LANG";
    SharedPreferences sharedPreferences;
    private static final String DEFAULT_LANG = "ar";

    public LanguageManager(Context context) {
        this.context = context;
        sharedPreferences =context.getSharedPreferences(PREF_LANG,Context.MODE_PRIVATE);
    }

    public void updateResource(String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        setLang(code);
    }

    public String getLang() {
        return sharedPreferences.getString(PREF_LANG, DEFAULT_LANG);
    }

    public void setLang(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LANG, code);
        editor.apply();
    }
}
