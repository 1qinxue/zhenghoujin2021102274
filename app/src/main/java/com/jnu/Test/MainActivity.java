package com.jnu.Test;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    public String []tabName={"图书","新闻","地图","时钟","游戏"};
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager2= findViewById(R.id.viewPaGet2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.setOffscreenPageLimit(5);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabName[(position)])).attach();

    }

    public class FragmentAdapter extends FragmentStateAdapter {
        private final int size_tab = tabName.length;
        public FragmentAdapter(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
            super(supportFragmentManager,lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new BookFragment();
                case 1:
                    return new WebFragment();
                case 2:
                    return new BaiduMapFragment();
                case 3:
                    return new ClockviewFragment();
                case 4:
                    return new GameFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return size_tab;
        }
    }
}