package lets.vote.generalelection;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;

import androidx.annotation.NonNull;

class FirebaseDistrictManager {
    private static FirebaseDatabase database;
    private static DatabaseReference rootRef;
    private static DatabaseReference dbRef;
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
}
