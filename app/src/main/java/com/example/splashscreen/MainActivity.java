package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public class PageView extends RelativeLayout {
        public PageView(Context context) {
            super(context);
        }
    }

    public class PageList1 extends PageView{

        public PageList1(Context context) {
            super(context);

            View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText("pagelist1");
            addView(view);
        }
    }
    public class PageList2 extends PageView{

        public PageList2(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText("pagelist2");
            addView(view);
        }
    }
    public class PageList3 extends PageView{

        public PageList3(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText("pagelist3");
            addView(view);
        }
    }
    public class PageList4 extends PageView{

        public PageList4(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText("pagelist4");
            addView(view);
        }
    }
    public class PageList5 extends PageView{

        public PageList5(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText("pagelist5dfjiagrhoug\nerhagoerhgreogheogehgoerw");
            addView(view);
        }
    }

    ViewPager viewPager;
    DotsIndicator dotsIndicator;
    List<PageView> pageList;
    Button SignUp,login;
    EditText AuthCode;
    ConstraintLayout qrButton;
    TextView Alarm,textView;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView6);
        String Welcome = "<b>FUN</b>SERVICE";
        textView.setText((Html.fromHtml(Welcome)));

        SignUp = findViewById(R.id.register);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        qrButton = findViewById(R.id.QR_button);
        qrButton.setOnClickListener(this);
        AuthCode = findViewById(R.id.AuthCode);
        Alarm = findViewById(R.id.Alarm);

        pageList = new ArrayList<>();
        pageList.add(new PageList1(MainActivity.this));
        pageList.add(new PageList2(MainActivity.this));
        pageList.add(new PageList3(MainActivity.this));
        pageList.add(new PageList4(MainActivity.this));
        pageList.add(new PageList5(MainActivity.this));
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PageAdapter());
        dotsIndicator = findViewById(R.id.dot);
        dotsIndicator.setViewPager(viewPager);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanner code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                AuthCheck(result.getContents());
                //AuthCheck(result.getContents());
            }else {
                if(data!=null) {
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("qr_scan_result");
                    //将扫描出的信息显示出来
                    AuthCheck(scanResult);
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void AuthCheck(String AuthCode){
        if (AuthCode.equals("000000")){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }else{
            Alarm.setText("AuthCode error! Please check and try again, or you can use QR code.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }
    }

    private class PageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object==view;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position){
            container.addView(pageList.get(position));
            return pageList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container,int position, @NonNull Object object){
            container.removeView((View)object);
        }
    }
}