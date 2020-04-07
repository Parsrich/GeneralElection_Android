package lets.vote.generalelection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.List;
import java.util.Map;


public class SplashActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        NetworkChecker.setup(this);
        if (NetworkChecker.checkOn()){
            Log.d("test", "### 연결 " );
            dataSetting();
        }else {
            NetworkChecker.alert(this, "확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }
//
//        if (manager.getNetworkInfo(NetworkCapabilities.TRANSPORT_CELLULAR).isConnected()
//                || manager.getNetworkInfo(NetworkCapabilities.TRANSPORT_WIFI).isConnected()){
//
//
//
//        }else {
//            Log.d("test", "### 연결 안됨  " );
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//            alertDialog.setTitle("네트워크 에러").setMessage("네트워크를 확인해주세요.");
//            alertDialog.setPositiveButton("ㅂㅇ", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            alertDialog.show();
//        }

//        NetworkChecker.setup(this);
//        NetworkChecker.checkOn(new ConnectivityManager.NetworkCallback(){
//            @Override
//            public void onAvailable(@NonNull Network network) {
//                // 네트워크를 사용할 준비가 되었을 때
//                Log.d("test", "### onAvailable: 연결  " );
//                NetworkChecker.manager.unregisterNetworkCallback(this);
//            }
//
//            @Override
//            public void onLost(@NonNull Network network) {
//                // 네트워크가 끊겼을 때
//                NetworkChecker.alert(getApplicationContext(), "버튼", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("test", "버튼클릭 " );
//                    }
//                }).show();
//                Log.d("test", "### onLost: 끊김 " );
//            }
//        });

//
//        NetworkRequest.Builder builder = new NetworkRequest.Builder();
//        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
//        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
//
//
//        final ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback(){
//            @Override
//            public void onAvailable(@NonNull Network network) {
//                // 네트워크를 사용할 준비가 되었을 때
//                Log.d("test", "### onAvailable: 연결  " );
//                manager.unregisterNetworkCallback(this);
//            }
//
//            @Override
//            public void onLost(@NonNull Network network) {
//                // 네트워크가 끊겼을 때
//                Log.d("test", "### onLost: 끊김 " );
//            }
//        };
//        manager.registerNetworkCallback(builder.build(), callback);
    }

    private void dataSetting(){
        FirebaseDistrictManager.setup();
        sharedPreferences = getApplicationContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        final FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
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
                        }else {
                            Log.d("test", "같음" );
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
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

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
