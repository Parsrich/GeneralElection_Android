package lets.vote.generalelection;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    SwipeDisabledViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Fragment mainFragment = MainFragment.getInstance();
        viewPagerAdapter.addFragment(new GuideFragment());
        viewPagerAdapter.addFragment(mainFragment);
        viewPagerAdapter.addFragment(new PartyListFragment());

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);

        ImageView imageViewTab1= new ImageView(MainActivity.this);
        imageViewTab1.setImageResource(R.drawable.ic_tab1_1);
        imageViewTab1.setPadding(30,10,30,20);
        tabs.getTabAt(0).setCustomView(imageViewTab1);

        ImageView imageViewTab2= new ImageView(MainActivity.this);
        imageViewTab2.setImageResource(R.drawable.ic_tab2_1);
        imageViewTab2.setPadding(30,10,30,20);
        tabs.getTabAt(1).setCustomView(imageViewTab2);

        ImageView imageViewTab3= new ImageView(MainActivity.this);
        imageViewTab3.setImageResource(R.drawable.ic_tab3_1);
        imageViewTab3.setPadding(30,10,30,20);
        tabs.getTabAt(2).setCustomView(imageViewTab3);

        tabs.getTabAt(1).select();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("test", "onTabReselected: ");
                if(tab.getPosition() == 1) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

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
