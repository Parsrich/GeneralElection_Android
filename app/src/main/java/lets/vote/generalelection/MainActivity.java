package lets.vote.generalelection;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainActivity extends AppCompatActivity {
    public SwipeDisabledViewPager viewPager;

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(GuideFragment.getInstance());
        viewPagerAdapter.addFragment(MainFragment.getInstance());
        viewPagerAdapter.addFragment(DefaultSettingFragment.getInstance());

        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);

        final ImageView imageViewTab1= new ImageView(MainActivity.this);
        imageViewTab1.setImageResource(R.drawable.ic_tab1);
        imageViewTab1.setPadding(30,10,30,20);
        tabs.getTabAt(0).setCustomView(imageViewTab1);

        final ImageView imageViewTab2= new ImageView(MainActivity.this);
        imageViewTab2.setImageResource(R.drawable.ic_tab2_1);
        imageViewTab2.setPadding(30,10,30,20);
        tabs.getTabAt(1).setCustomView(imageViewTab2);

        final ImageView imageViewTab3= new ImageView(MainActivity.this);
        imageViewTab3.setImageResource(R.drawable.ic_tab3);
        imageViewTab3.setPadding(30,10,30,20);
        tabs.getTabAt(2).setCustomView(imageViewTab3);

        tabs.getTabAt(1).select();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);

                if(tab.getPosition()==0){
                    imageViewTab1.setImageResource(R.drawable.ic_tab1_1);
                    imageViewTab2.setImageResource(R.drawable.ic_tab2);
                    imageViewTab3.setImageResource(R.drawable.ic_tab3);
                }else if(tab.getPosition()==1){

                    imageViewTab1.setImageResource(R.drawable.ic_tab1);
                    imageViewTab2.setImageResource(R.drawable.ic_tab2_1);
                    imageViewTab3.setImageResource(R.drawable.ic_tab3);

                }else if(tab.getPosition()==2){

                    imageViewTab1.setImageResource(R.drawable.ic_tab1);
                    imageViewTab2.setImageResource(R.drawable.ic_tab2);
                    imageViewTab3.setImageResource(R.drawable.ic_tab3_1);
                }

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
                }else if(tab.getPosition() == 2){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate("setting",FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
