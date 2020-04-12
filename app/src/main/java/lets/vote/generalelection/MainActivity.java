package lets.vote.generalelection;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import static lets.vote.generalelection.Util.print;

public class MainActivity extends AppCompatActivity {
    public SwipeDisabledViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
            Log.d("MainActivity", "광고 준비");
        });

        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(GuideFragment.getInstance());
        viewPagerAdapter.addFragment(MainFragment.getInstance());
        viewPagerAdapter.addFragment(DefaultSettingFragment.getInstance());
        
        viewPager.setAdapter(viewPagerAdapter);

        tabs = findViewById(R.id.tabs);

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
                if(tab.getPosition() == 1) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else if(tab.getPosition() == 2){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate("setting",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

            }
        });
        tabs.getTabAt(1).select();
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



    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            /// 이렇게 하나만 특정해서 하면 안되는데 일단 급하니까...ㅠㅠ
            if (tabs.getSelectedTabPosition() == 1 && getSupportFragmentManager().findFragmentById(R.id.mainContainer) instanceof DistrictListFragment)
                mOnKeyBackPressedListener.onBack();
            else
                super.onBackPressed();

            return;
        }

        if(!isLastFragment()) {
            super.onBackPressed();
        } else {
            if ( pressedTime == 0 ) {
                Toast.makeText(getApplicationContext(), " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Toast.makeText(getApplicationContext(), " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_SHORT).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    finish();
                }
            }
        }
    }
    public interface onKeyBackPressedListener {
        void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    boolean isLastFragment() {
        // 가이드 인경우 무조건 두번 back
        if (tabs.getSelectedTabPosition() == 0) {
            return true;
        } else if (tabs.getSelectedTabPosition() == 1) {
            return getSupportFragmentManager()
                    .findFragmentById(R.id.mainContainer) instanceof MainMenuFragment;

        } else { //if (tabs.getSelectedTabPosition() == 2) {
            return getSupportFragmentManager()
                    .findFragmentById(R.id.defaultSettingContainer) instanceof SettingFragment;
        }
    }
}
