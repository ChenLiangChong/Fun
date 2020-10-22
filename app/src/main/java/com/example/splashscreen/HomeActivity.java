package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setItemIconTintList(null);

        viewPager.addOnPageChangeListener(onPageChangeListener);

        setupViewPager(viewPager);
    }
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);

        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.home:
                            viewPager.setCurrentItem(0);
                            //fragment = new HomeFragment();
                            break;
                        case R.id.mbooking:
                            viewPager.setCurrentItem(1);
                            //fragment = new BookFragment();
                            break;
                        case R.id.coupon:
                            viewPager.setCurrentItem(2);
                            //fragment = new CouponFragment();
                            break;
                        case R.id.messenger:
                            viewPager.setCurrentItem(4);
                            //fragment = new MessengerFragment();
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return false;
                }
            };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new BookFragment());
        adapter.addFragment(new CouponFragment());
        adapter.addFragment(new MessengerFragment());
        viewPager.setAdapter(adapter);
    }
}