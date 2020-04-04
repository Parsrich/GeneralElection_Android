package lets.vote.generalelection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DistrictListFragment extends Fragment {
    private static DistrictListFragment instance;
    private RecyclerView recyclerView;
    private DistrictAdapter districtAdapter;
    private Context mContext;
    private List<String> districtList = new ArrayList<>();
    private Map<String,Object> districtMap;

    private TextView navSiText;
    private TextView navGuText;
    private TextView navDongText;

    private LinearLayout guGroup;
    private LinearLayout dongGroup;

    private ProgressBar progressBar;


    private int nowElection = 0;
    private Stack<String> districtHistoryStack = new Stack<>();

    public DistrictListFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new DistrictListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_district_list, container, false);
        progressBar = rootView.findViewById(R.id.districtProgress);

        recyclerView = rootView.findViewById(R.id.districtList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        districtAdapter = new DistrictAdapter(districtList);
        navSiText = rootView.findViewById(R.id.navSi);
        navGuText = rootView.findViewById(R.id.navGu);
        navDongText = rootView.findViewById(R.id.navDong);

        navSiText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
        navSiText.setTextColor(getResources().getColor(R.color.white));
        navGuText.setBackground(getResources().getDrawable(R.drawable.nav_deactivate));
        navGuText.setTextColor(getResources().getColor(R.color.white));
        navDongText.setBackground(getResources().getDrawable(R.drawable.nav_deactivate));
        navDongText.setTextColor(getResources().getColor(R.color.white));

        guGroup = rootView.findViewById(R.id.navGuGroup);
        guGroup.setVisibility(View.INVISIBLE);

        dongGroup = rootView.findViewById(R.id.navDongGroup);
        dongGroup.setVisibility(View.INVISIBLE);

        navSiText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(districtHistoryStack.size()>0){
                    while (districtHistoryStack.size() != 0){
                        districtHistoryStack.pop();
                    }
                    districtList.clear();
                    districtList.addAll(getDistrictList(districtHistoryStack));
                    districtAdapter.notifyDataSetChanged();

                    navSiText.setText(R.string.si_title);
                    navGuText.setText(R.string.gu_title);
                    navDongText.setText(R.string.dong_title);

                    guGroup.setVisibility(View.INVISIBLE);
                    dongGroup.setVisibility(View.INVISIBLE);
                    navSiText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                    navGuText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                }
            }
        });
        navGuText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(districtHistoryStack.size()>1){
                    while (districtHistoryStack.size() != 1){
                        districtHistoryStack.pop();
                    }
                    districtList.clear();
                    districtList.addAll(getDistrictList(districtHistoryStack));
                    districtAdapter.notifyDataSetChanged();

                    navGuText.setText(R.string.gu_title);
                    navDongText.setText(R.string.dong_title);
                    dongGroup.setVisibility(View.INVISIBLE);
                    navGuText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                }
            }
        });


        recyclerView.setAdapter(districtAdapter);

        /* 데이터 가져오기 */
        if (districtMap == null || districtMap.size() == 0 ){
            progressBar.setVisibility(View.VISIBLE);
            FirebaseDistrictManager.getDistrictMap().observe(this, new Observer<Map<String,Object>>() {
                @Override
                public void onChanged(Map<String, Object> resultMap) {
                    Log.d("test","resultMap.toString() "+resultMap.toString());
                    Log.d("test","districtHistoryStack "+districtHistoryStack.toString());
                    districtMap = resultMap;
                    districtList.clear();
                    districtList.addAll(getDistrictList(districtHistoryStack));
                    Log.d("test",districtList.toString());
                    districtAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            districtList.addAll(getDistrictList(districtHistoryStack));
            Log.d("test", districtList.toString());
            districtAdapter.notifyDataSetChanged();

            if(districtHistoryStack.size()>0){
                while (districtHistoryStack.size() != 0){
                    districtHistoryStack.pop();
                }
                districtList.clear();
                districtList.addAll(getDistrictList(districtHistoryStack));
                districtAdapter.notifyDataSetChanged();

                navSiText.setText(R.string.si_title);
                navGuText.setText(R.string.gu_title);
                navDongText.setText(R.string.dong_title);

                guGroup.setVisibility(View.INVISIBLE);
                dongGroup.setVisibility(View.INVISIBLE);
                navSiText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                navGuText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
            }

            if(districtHistoryStack.size()>1){
                while (districtHistoryStack.size() != 1){
                    districtHistoryStack.pop();
                }
                districtList.clear();
                districtList.addAll(getDistrictList(districtHistoryStack));
                districtAdapter.notifyDataSetChanged();

                navGuText.setText(R.string.gu_title);
                navDongText.setText(R.string.dong_title);
                dongGroup.setVisibility(View.INVISIBLE);
                navGuText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
            }
        }

        return rootView;
    }

    public List<String> getDistrictList( Stack<String> history){
        List<String> resultList = new ArrayList<>();
        Map<String,Object> temp = districtMap;

        if (history != null && history.size() < 4){

            for(int i = 0; i < history.size() ; i++) {
                temp = (Map<String, Object>) temp.get(history.get(i));
                Log.d("test","getDistrictList 도는중  : "+temp.toString());
            }
            Log.d("test","getDistrictList 도는중  : "+temp.toString());
            if (history.size() == 3) {
                resultList =  new ArrayList<>();
                resultList.add((String)temp.get(Election.values()[nowElection].toString()));
            }else {
                resultList =  new ArrayList<>(temp.keySet());
            }

            Log.d("test","getDistrictList 결과  : "+resultList.toString());
        }
        Collections.sort(resultList);

        return resultList;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        districtList.clear();
//        candidateList.clear();

        districtAdapter.notifyDataSetChanged();

        recyclerView = null;
    }

    private class DistrictAdapter extends RecyclerView.Adapter<DistrictViewHolder> {
        List<String> mList;
        public DistrictAdapter(List<String> list){
            mList = list;
        }

        @NonNull
        @Override
        public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_disctrict,viewGroup,false);
            return new DistrictViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
            String d = mList.get(position);
            holder.districtName.setText(d);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("test","클릭했을 때 지역이름리스트 ,"+districtList.toString());
                    Log.d("test","클릭했을 때 스택  ,"+districtHistoryStack.toString());
                    if(v != null){

                        districtList.clear();
                        districtHistoryStack.push(((TextView)v.findViewById(R.id.districtName)).getText().toString());
                        districtList.addAll(getDistrictList(districtHistoryStack));

                        if (districtHistoryStack.size() == 1){
                            navSiText.setText(districtHistoryStack.peek());
                            navSiText.setBackground(getResources().getDrawable(R.drawable.nav_deactivate));
                            navGuText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                            guGroup.setVisibility(View.VISIBLE);
                        }
                        else if (districtHistoryStack.size() == 2) {
                            navGuText.setText(districtHistoryStack.peek());
                            navGuText.setBackground(getResources().getDrawable(R.drawable.nav_deactivate));
                            navDongText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                            dongGroup.setVisibility(View.VISIBLE);
                        }
                        else if (districtHistoryStack.size() == 3){
                            Bundle candidateDetailBundle = new Bundle();

                            Stack<String> tempStack = new Stack<>();

                            for(String a : districtHistoryStack){
                                tempStack.push(a);
                            }

                            candidateDetailBundle.putSerializable("stack",tempStack);
                            candidateDetailBundle.putSerializable("districtMap", (Serializable) districtMap);

                            Fragment candidateFragment = CandidateListFragment.getInstance();

                            candidateFragment.setArguments(candidateDetailBundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,candidateFragment).addToBackStack(null).commit();
//                            Log.d("test","클릭후 최종 지역이름리스트 ,"+getActivity().getSupportFragmentManager().toString());

                            while (districtHistoryStack.size() != 2){
                                districtHistoryStack.pop();
                            }
                            districtList.clear();
                            districtList.addAll(getDistrictList(districtHistoryStack));
                            navDongText.setText(R.string.dong_title);
                            navDongText.setBackground(getResources().getDrawable(R.drawable.nav_activate));
                        }


                        if ( districtHistoryStack.size() < 3){
                            districtAdapter.notifyDataSetChanged();
                        }
                    }

                    Log.d("test","클릭후 최종 지역이름리스트 ,"+districtList.toString());
                    Log.d("test","클릭후 최종 스택  ,"+districtHistoryStack.toString());
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class DistrictViewHolder extends RecyclerView.ViewHolder{
        TextView districtName;

        public DistrictViewHolder(View view){
            super(view);
            districtName = view.findViewById(R.id.districtName);
        }
    }
}
