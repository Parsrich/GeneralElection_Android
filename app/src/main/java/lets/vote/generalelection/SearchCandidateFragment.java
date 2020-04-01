package lets.vote.generalelection;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Arrays;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCandidateFragment extends Fragment {
    private static SearchCandidateFragment instance;
    private static Map<String,Object> candidateNameMap;
    private static String[] candidateNameArray;
    private Context mContext;

    public SearchCandidateFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new SearchCandidateFragment();
        }
        return instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_search_candidate, container, false);
        final AutoCompleteTextView candidateSearchTextView = rootView.findViewById(R.id.searchCandidate);

        if (candidateNameMap == null || candidateNameMap.size() == 0) {
            FirebaseDistrictManager.getCandidateNameMap().observe(this, new Observer<Map<String, Object>>() {
                @Override
                public void onChanged(Map<String, Object> resultMap) {

                    candidateNameMap = resultMap;
                    candidateNameArray = candidateNameMap.keySet().toArray(new String[resultMap.size()]);
                    Log.d("test", "resultMap.toString() " + resultMap.toString());
                    Log.d("test", "resultMap.toString() " + Arrays.toString(candidateNameArray));

                    ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_dropdown_item_1line, candidateNameArray);
                    candidateSearchTextView.setAdapter(autoAdapter);


                }
            });
        }



        return rootView;
    }
}
