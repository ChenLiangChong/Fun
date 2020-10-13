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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    Button btnFacebook,btnGoogle,btnSignUp;
    CallbackManager mCallbackManager;
    GoogleSignInClient mGoogleSignInClient;

    private EditText inputEmail,inputPassword,inputUsername,inputBirth,inputGender;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    FirebaseUser user;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(SignUpActivity.this);

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
                CheckValid(inputEmail.getText().toString(),inputPassword.getText().toString(),inputUsername.getText().toString());
            }
        });

        //Google SignUp
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Facebook SignUp
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebook = findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookLogin();
            }
        });
    }

    //Facebook SignUp

    private void FacebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SignUpActivity.this,"SignUp Successfully",Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this,"SignUp Successfully",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    //Google SignUP
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }else mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this,"Sign Up Successfully",Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
            startActivity(intent);
        }
    }

    //Sign Up with Email
    private void CheckValid(String email, String password, final String username) {
//        String email = inputEmail.getText().toString();
//        String password = inputPassword.getText().toString();
//        String username = inputUsername.getText().toString();

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
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this,"Set username successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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