package lets.vote.generalelection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.Observer;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.List;
import java.util.Map;

import static lets.vote.generalelection.Util.print;

public class SplashActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static SharedPreferences sharedPreferences;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bar = findViewById(R.id.splashLoadingProgress);
        bar.setVisibility(View.VISIBLE);

        NetworkChecker.setup(this);
        if (NetworkChecker.checkOn()){
            Log.d("test", "### 연결 " );
            dataSetting();
        }else {
            NetworkChecker.alert(this, "확인", (dialog, which) -> finish()).show();
        }
    }

    private void dataSetting(){
        FirebaseDistrictManager.setup();
        sharedPreferences = getApplicationContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        final FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(BuildConfig.REMOTE_CONFIG_REQUEST_TIME)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, task -> {

                    boolean isUpToDated = checkApplicationVersion();
                    if (!isUpToDated) {
                        Log.d("test", "앱 최신아님 :" + mFirebaseRemoteConfig.getString("android_app_version"));
                        return;
                    }

                    Log.d("test", "android_db_version: " +mFirebaseRemoteConfig.getString("android_db_version"));
                    int getVersion = 1;
                    if (!mFirebaseRemoteConfig.getString("android_db_version").equals("")){
                        getVersion = Integer.parseInt(mFirebaseRemoteConfig.getString("android_db_version"));
                    }

                    int localVersion = sharedPreferences.getInt("dbVersion",-1);
                    if (localVersion == -1 ){

                        getData();
                        sharedPreferences.edit().putInt("dbVersion",getVersion).commit();
                        Log.d("test", "최초" );
                    } else if ( getVersion != localVersion ){
                        //다르면 처리하고
////                            CandidateDBHelper helper=CandidateDBHelper.getInstance(getApplicationContext());
                        SQLiteDatabase db=CandidateDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
                        db.execSQL(CandidateContract.SQL_DELETE_ENTRIES);
                        db.execSQL(CandidateContract.SQL_CREATE_ENTRIES);
                        getData();
                        sharedPreferences.edit().putInt("dbVersion",getVersion).commit();
                        Log.d("test", "다름" );
                    } else {
                        bar.setVisibility(View.GONE);
                        Log.d("test", "같음" );
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

//    boolean isAppUpdated() {
//        String[] versionsLocal = BuildConfig.VERSION_NAME.split(".");
//        String[] versionsRemote = mFirebaseRemoteConfig.getString("android_app_version").split(".");
//        print(versionsLocal + "");
//        print(versionsRemote + "");
//        int[] versionLocalIntegers = new int[3];
//        int[] versionRemoteIntegers = new int[3];
//        for (int i=0; i<3; i++) {
//            int localVersion = Integer.parseInt(versionsLocal[i]);
//            int remoteVersion = Integer.parseInt(versionsRemote[i]);
//            versionLocalIntegers[i] = localVersion;
//            versionRemoteIntegers[i] = remoteVersion;
//        }
//
//        if (versionLocalIntegers[0] < versionRemoteIntegers[0]) {
//            return true;
//        } else {
//            if (versionLocalIntegers[1] < versionRemoteIntegers[1]) {
//                return true;
//            }
//            return versionLocalIntegers[2] < versionRemoteIntegers[2];
//        }
//    }
    public boolean checkApplicationVersion() {
        String appVersion = mFirebaseRemoteConfig.getString("android_app_version");

//        if (isAppUpdated()) {
        if (!BuildConfig.VERSION_NAME.equals(appVersion) ) {
            // 확인
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(SplashActivity.this, R.style.DialogStyle));

            builder.setTitle("앱이 업데이트 되었습니다.")
                    .setMessage("업데이트를 위해 Play Store로 이동합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    }).setNegativeButton("종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    }).show();
        }
        return BuildConfig.VERSION_NAME.equals(appVersion);
    }


    public void getData(){

        FirebaseDistrictManager.getCandidateNameMap().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> resultMap) {
                CandidateDBHelper helper=CandidateDBHelper.getInstance(getApplicationContext());
                SQLiteDatabase db=helper.getWritableDatabase();



                for(String k : resultMap.keySet()){


                    List<Object> tempList = (List<Object>) resultMap.get(k);

                    for(int i = 0 ; i < tempList.size(); i ++){
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
                        db.insert(CandidateContract.CandidateEntry.TABLE_NAME, null, values);
//                        Log.d("test", "values: " +values);
                    }
                }

                db.close();
                bar.setVisibility(View.GONE);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
