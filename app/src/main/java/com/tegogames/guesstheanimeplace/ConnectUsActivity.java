package com.tegogames.guesstheanimeplace;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tegogames.guesstheanimeplace.Language.AppCompat;


public class ConnectUsActivity extends AppCompat {


    EditText message;
    Button sent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_us);

        message = findViewById(R.id.feedbackEditText);
        sent = findViewById(R.id.submitButton);

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = message.getText().toString();
                if (content.isEmpty()){
                    String tost = getString(R.string.connet_us_tost);
                    Toast.makeText(ConnectUsActivity.this, tost+"", Toast.LENGTH_SHORT).show();
                }else {
                    sentEmail(content);
                }

            }
        });
    }

    public void sentEmail(String text){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"te" +
                "gogames3@gmail.com\n"});
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name)+"");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }

}