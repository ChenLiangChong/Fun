package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    Button btnFacebook,btnGoogle,btnSignUp;

    private EditText inputEmail,inputPassword,inputUsername,inputBirth,inputGender;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    FirebaseUser user;

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

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputUsername = findViewById(R.id.inputUsername);
        inputBirth = findViewById(R.id.inputBirth);
        inputGender = findViewById(R.id.inputGender);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(SignUpActivity.this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValid();
            }
        });
    }

    private void CheckValid() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        final String username = inputUsername.getText().toString();

        if(email.isEmpty()||!email.contains("@")){
            showError(inputEmail,"Invalid Email");
        }
        else if(password.isEmpty()||password.length()<7){
            showError(inputPassword,"Invalid Password");
        }
        else if(username.isEmpty()){
            showError(inputUsername,"please enter username");
        }
        else{
            mLoadingBar.setTitle("Sign Up");
            mLoadingBar.setMessage("Please wait while check");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this,"Successfully Sign Up",Toast.LENGTH_SHORT).show();
                        user = mAuth.getCurrentUser();

                        assert user != null;
                        user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName("username").build());

                        Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}