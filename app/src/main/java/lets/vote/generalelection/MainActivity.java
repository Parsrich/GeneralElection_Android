package lets.vote.generalelection;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(),
                    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new CandidateListFragment());
        viewPagerAdapter.addFragment(new MainFragment());
        viewPagerAdapter.addFragment(new SettingFragment());

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);

        ImageView ImageViewTab1= new ImageView(MainActivity.this);
        ImageViewTab1.setImageResource(R.drawable.ic_tab1_1);
        ImageViewTab1.setPadding(30,10,30,20);
        tabs.getTabAt(0).setCustomView(ImageViewTab1);

        ImageView ImageViewTab2= new ImageView(MainActivity.this);
        ImageViewTab2.setImageResource(R.drawable.ic_tab2_1);
        ImageViewTab2.setPadding(30,10,30,20);
        tabs.getTabAt(1).setCustomView(ImageViewTab2);

        ImageView ImageViewTab3= new ImageView(MainActivity.this);
        ImageViewTab3.setImageResource(R.drawable.ic_tab3_1);
        ImageViewTab3.setPadding(30,10,30,20);
        tabs.getTabAt(2).setCustomView(ImageViewTab3);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment){
            fragments.add(fragment);
        }


    }
}
