package com.example.splashscreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    Button btnFacebook,btnGoogle;

    private EditText inputEmail,inputPassword,inputUsername,inputBirth,inputGender;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);

        Spannable ss = new SpannableString("Sign up with\nFacebook");
        Spannable ss2 = new SpannableString("Sign up with\nGoogle");

        Typeface font = Typeface.create("sans-serif-thin",Typeface.NORMAL);
        Typeface font2 = Typeface.create("sans-serif",Typeface.BOLD);

        ss.setSpan(new RelativeSizeSpan(1.2f), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(1.4f), 13, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new TypefaceSpan(font),0,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new TypefaceSpan(font2),13,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnFacebook.setText(ss);

        ss2.setSpan(new RelativeSizeSpan(1.2f), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new RelativeSizeSpan(1.4f), 13, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new TypefaceSpan(font),0,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new TypefaceSpan(font2),13,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnGoogle.setText(ss2);



    }
}