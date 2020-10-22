package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button btnFacebook,btnGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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