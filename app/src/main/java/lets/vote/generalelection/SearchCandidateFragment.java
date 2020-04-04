package lets.vote.generalelection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCandidateFragment extends Fragment {
    private static SearchCandidateFragment instance;
    private static Map<String,Object> candidateNameMap;
    private static String[] candidateNameArray;
    private Context mContext;
    private static List<String> nameList;
    private static CandidateDBHelper helper;
    private static SQLiteDatabase db;

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
        helper=new CandidateDBHelper(getContext());
        db=helper.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT distinct name FROM "+CandidateContract.CandidateEntry.TABLE_NAME,null);
        nameList = new ArrayList<>();
        while (cursor.moveToNext()){
            nameList.add(cursor.getString(0).trim());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =inflater.inflate(R.layout.fragment_search_candidate, container, false);
        final AutoCompleteTextView candidateSearchTextView = rootView.findViewById(R.id.searchCandidate);

        ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_dropdown_item_1line, nameList);
        candidateSearchTextView.setAdapter(autoAdapter);
        candidateSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(v.getText().length() < 1 || v.getText() == null){
                        Toast.makeText(getContext(),getResources().getString(R.string.search_condition),Toast.LENGTH_SHORT).show();
                    }else {
                        String sql = "SELECT * FROM "+CandidateContract.CandidateEntry.TABLE_NAME+ " WHERE name like '%"+v.getText().toString().trim() +"%'";
                        Cursor cursor = db.rawQuery(sql,null);
                        Log.d("test", cursor.getCount()+" ");

                        if (cursor.getCount() == 0){
                            Toast.makeText(getContext(),getResources().getString(R.string.no_result),Toast.LENGTH_SHORT).show();

                        } else if ( cursor.getCount() == 1 ){
                            Fragment detailFragment =  new CandidateDetailFragment();
                            Bundle bundle = new Bundle();
                            CandidateVO vo = null;
                            while (cursor.moveToNext()){
                                vo = new CandidateVO(cursor);
                            }
                            bundle.putSerializable("vo",vo);
                            detailFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,detailFragment).addToBackStack(null).commit();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            v.clearFocus();
                        } else{
                            Fragment searchCandidateListFragment = SearchCandidateListFragment.getInstance();
                            Bundle bundle = new Bundle();
                            List<CandidateVO> voList = new ArrayList<>();
                            Toast.makeText(getContext(),"여러개",Toast.LENGTH_SHORT).show();
                            while (cursor.moveToNext()){
                                voList.add(new CandidateVO(cursor));
                            }
                            bundle.putSerializable("list", (Serializable) voList);
                            bundle.putString("searchKeyword",v.getText().toString());

                            searchCandidateListFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,searchCandidateListFragment).addToBackStack(null).commit();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            v.clearFocus();
                        }

                    }
                }
                return false;
            }
        });


        return rootView;
    }
}
