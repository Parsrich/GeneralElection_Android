package lets.vote.generalelection;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeFragment extends Fragment {
    private static NoticeFragment instance;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private List<Map<String,String>> list = new ArrayList<>();
    private NoticeAdapter adapter;


    public NoticeFragment() {
        // Required empty public constructor
    }
    public static NoticeFragment getInstance() {
        if(instance == null) {
            instance = new NoticeFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_notice, container, false);
        NetworkChecker.setup(getContext());
        if (!NetworkChecker.checkOn()){
            NetworkChecker.alert(getContext(), "확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }).show();
        }else {
            RecyclerView noticeRecyclerView = rootView.findViewById(R.id.noticeRecyclerView);
            noticeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new NoticeAdapter(list);
            noticeRecyclerView.setAdapter(adapter);

            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            final FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(getActivity(), new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(@NonNull Task<Boolean> task) {
                    mFirebaseRemoteConfig.getString("introduce_message");
                    list.clear();
                    list.addAll(jsonParser(mFirebaseRemoteConfig.getString("introduce_message")));
                    adapter.notifyDataSetChanged();
                }
            });

        }


        return rootView;
    }

    private List<Map<String,String>> jsonParser(String raw){
        final List<Map<String,String>> result = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(raw);

            for(int i=0; i<array.length(); i++)
            {
                final JSONObject content = array.getJSONObject(i);
                result.add(new HashMap<String,String>(){{
                    put("contents", (String) content.get("contents"));
                    put("underbarContents",(String) content.get("underbarContents"));
                }});
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    class NoticeAdapter extends RecyclerView.Adapter<NoticeViewHolder>{
        List<Map<String,String>> list;
        public NoticeAdapter(List<Map<String,String>> list){
            this.list = list;
        }
        @NonNull
        @Override
        public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflater = LayoutInflater.from(getContext()).inflate(R.layout.item_notice,parent,false);
            return new NoticeViewHolder(inflater);
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
            holder.noticeContent.setText(list.get(position).get("contents"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    class NoticeViewHolder extends RecyclerView.ViewHolder{
        TextView noticeContent;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeContent = itemView.findViewById(R.id.noticeContent);
        }
    }

}
