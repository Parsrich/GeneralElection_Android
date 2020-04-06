package lets.vote.generalelection;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.Map;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import static lets.vote.generalelection.CandidateContract.SQL_CREATE_ENTRIES;
import static lets.vote.generalelection.CandidateContract.SQL_DELETE_ENTRIES;

public class CandidateDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME = "CandidateReader.db";
    public static CandidateDBHelper instance;
    public static Context mContext;

    public static CandidateDBHelper getInstance(Context context){
        if(instance == null) {
            instance = new CandidateDBHelper(context);
        }
        return instance;
    }

    private CandidateDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext= context;
    }
    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(CandidateContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
