package lets.vote.generalelection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private static Map<String,Object> candidateNameMap;
    private static String[] candidateNameArray;
    public SwipeDisabledViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (candidateNameMap == null || candidateNameMap.size() == 0) {
            FirebaseDistrictManager.getCandidateNameMap().observe(this, new Observer<Map<String, Object>>() {
                @Override
                public void onChanged(Map<String, Object> resultMap) {
                    CandidateDBHelper helper=new CandidateDBHelper(getApplicationContext());
                    SQLiteDatabase db=helper.getWritableDatabase();

                    candidateNameMap = resultMap;
                    Cursor cursor=db.rawQuery("SELECT distinct name FROM "+CandidateContract.CandidateEntry.TABLE_NAME,null);

                    if (candidateNameMap.size() == cursor.getCount()) {
                        Log.d("test", "같음 ");

                    }else {
                        for(String k : candidateNameMap.keySet()){
                            SQLiteDatabase writableDatabase=helper.getWritableDatabase();
                            writableDatabase.rawQuery(CandidateContract.SQL_DELETE_ENTRIES,null);

                            List<Object> tempList = (List<Object>) candidateNameMap.get(k);

                            for(int i = 0 ; i < tempList.size(); i ++){
                                Log.d("test", "newRowId" +tempList.toString());
                                CandidateVO temp = new CandidateVO((Map<String, Object>) tempList.get(i));
                                ContentValues values = new ContentValues();
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_ID, temp.getId());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_NAME, temp.getName());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_NAME_FULL, temp.getNameFull());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_JOB, temp.getJob());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_BIRTH, temp.getBirth());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_ADDRESS, temp.getAddress());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_CAREER, temp.getCareer());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_CRIMINAL, temp.getCriminal());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_DISTRICT, temp.getDistrict());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_EDUCATION, temp.getEducation());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_GENDER, temp.getGender());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_IMAGE_URL, temp.getImageUrl());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_MILITARY, temp.getMilitary());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_NUMBER, temp.getNumber());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_PARTY, temp.getParty());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_PROPERTY, temp.getProperty());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_REG_COUNT, temp.getRegCount());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_SI, temp.getSi());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_ARREARS, temp.getTaxArrears());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_ARREARS5, temp.getTaxArrears5());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_PAYMENT, temp.getTaxPayment());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_STATUS, temp.getStatus());
                                values.put(CandidateContract.CandidateEntry.COLUMN_NAME_RECOMMEND, temp.getRecommend());
                                long newRowId = db.insert(CandidateContract.CandidateEntry.TABLE_NAME, null, values);
//                                Log.d("test", "newRowId" + newRowId+"");
                            }
//                            Log.d("test", "다름 "+candidateNameMap.size());
//                            Log.d("test", "다름 "+cursor.getCount());
                        }
                    }
                    db.close();
                    Log.d("test", "resultMap.toString() " + resultMap.toString());
                    Log.d("test", "resultMap.toString() " + Arrays.toString(candidateNameArray));
                }
            });
        }





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
