package lets.vote.generalelection;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;

class FirebaseDistrictManager {
    private static FirebaseDatabase database;
    private static DatabaseReference rootRef;
    private static DatabaseReference dbRef;
    private static MutableLiveData<Map<String,Object>> districtMutableLiveData;
    private static MutableLiveData<Map<String,Object>> candidateNameMutableLiveData;


    static void setup() {
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("/");
    }

    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static DatabaseReference getRootRef() {
        return rootRef;
    }

    public static DatabaseReference getDbRef(String path) {
        return FirebaseDistrictManager.dbRef = rootRef.child(path);
    }

    public static MutableLiveData<Map<String,Object>> getDistrictMap() {

        if (districtMutableLiveData == null ) {
            database = FirebaseDatabase.getInstance();
            districtMutableLiveData = new MutableLiveData<>();
            rootRef = database.getReference("/");
            rootRef.child("district")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // How to return this value?
                            Map<String, Object> district = (Map<String, Object>)dataSnapshot.getValue(Object.class);
                            districtMutableLiveData.setValue(district);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
//                            databaseError.getCode()
                            Log.d("onCancelled", "error detail: " + databaseError.getDetails());
                            Log.d("onCancelled", "error msg: " + databaseError.getMessage());
                        }
                    });
        }

        return districtMutableLiveData;
    }

    public static MutableLiveData<Map<String,Object>> getCandidateNameMap() {
        if (candidateNameMutableLiveData == null ) {
            database = FirebaseDatabase.getInstance();
            candidateNameMutableLiveData = new MutableLiveData<>();
            rootRef = database.getReference("/");
            rootRef.child("name")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // How to return this value?
                            Map<String, Object> district = (Map<String, Object>)dataSnapshot.getValue(Object.class);
                            candidateNameMutableLiveData.setValue(district);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        }
        return candidateNameMutableLiveData;
    }
}

