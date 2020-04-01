package lets.vote.generalelection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateListFragment2 extends Fragment {
    private RecyclerView recyclerView;
    private List<CandidateVO> candidateList ;
    private CandidateAdapter candidateAdapter;
    private DistrictAdapter districtAdapter;
    private Context mContext;
    private List<String> districtList = new ArrayList<>();
    private String nowDistrict = "거주지역을 선택해주세요";
    private Map<String,Object> districtMap;

    private int nowElection = 0;
    private Stack<String> districtHistoryStack = new Stack<>();

    private ValueEventListener candidateListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d("test", "onDataChange: " + dataSnapshot.getValue().toString());
            ArrayList<Object> value = (ArrayList<Object>) dataSnapshot.getValue();

            for( Object candidate :value){
                candidateList.add(new CandidateVO((Map<String,Object>)candidate));
            }
            candidateAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public CandidateListFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_candidate_list, container, false);

        TextView searchDistrict = rootView.findViewById(R.id.searchDistrict);
        searchDistrict.setText(nowDistrict);

        /* 선거 선택 */
        Spinner spinner = rootView.findViewById(R.id.electionSpinner);


        final ArrayAdapter<String> selectAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.selectElection)) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Typeface externalFont = ResourcesCompat.getFont(mContext, R.font.gmarket_sans_medium);

                ((TextView) view).setTypeface(externalFont);
                ((TextView) view).setTextSize(17);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = ResourcesCompat.getFont(mContext, R.font.gmarket_sans_medium);

                ((TextView) view).setTypeface(externalFont);
                ((TextView) view).setTextSize(17);
                return view;
            }
        };

        selectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(selectAdapter);

        spinner.setAdapter(selectAdapter);
        spinner.setSelection(nowElection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    Log.d("test", ((TextView) view).getText().toString());
                    nowElection = position;
                    Log.d("test", (nowElection + ""));

                    if(districtHistoryStack.size() == 3){
                        districtList.clear();
                        districtList.addAll(getDistrictList(districtHistoryStack));
                        candidateList.clear();
                        Log.d("test", "onItemSelected: " +Election.values()[nowElection].toString() );
                        Log.d("test", "onItemSelected: " +districtList.get(0) );

                        if (districtList.get(0).equals("")){
                            Log.d("test","districtList.get(0)"+districtList.get(0));
                            candidateList.clear();
                            candidateAdapter.notifyDataSetChanged();
                        } else {
                            FirebaseDistrictManager.getDbRef(Election.values()[nowElection].toString()+"/"+districtList.get(0)).addListenerForSingleValueEvent(candidateListener);
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                nowElection = 0;
            }
        });


        /* 데이터 */

        recyclerView = rootView.findViewById(R.id.candidateList);

        /* 후보자 Adapter */

        candidateList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        candidateAdapter = new CandidateAdapter(candidateList);
        districtAdapter = new DistrictAdapter(districtList);

        /* 마지막 값인지 확인 */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                Log.d("position", lastPosition + "");
                if (lastPosition == candidateList.size() - 1) {
                    Log.d("position", "마지막 값");
                }
            }
        });


        /* 현재 동까지 검색했을 때 */



        if ( districtHistoryStack.size() == 3 ){
            recyclerView.setAdapter(candidateAdapter);
        } else {
            recyclerView.setAdapter(districtAdapter);
        }

        /* 데이터 가져오기 */
        if (districtMap == null || districtMap.size() == 0 ){
            FirebaseDistrictManager.getDistrictMap().observe(this, new Observer<Map<String,Object>>() {
                @Override
                public void onChanged(Map<String, Object> resultMap) {
                    Log.d("test","resultMap.toString() "+resultMap.toString());
                    Log.d("test","districtHistoryStack "+districtHistoryStack.toString());
                    districtMap = resultMap;

                    if ( districtHistoryStack.size() == 3 ){
                        districtList.addAll(getDistrictList(districtHistoryStack));
                        FirebaseDistrictManager.getDbRef(Election.values()[nowElection].toString()+"/"+districtList.get(0)).addListenerForSingleValueEvent(candidateListener);
                    } else {
                        districtList.addAll(getDistrictList(districtHistoryStack));
                        Log.d("test",districtList.toString());
                        districtAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            if ( districtHistoryStack.size() == 3 ){
                districtList.addAll(getDistrictList(districtHistoryStack));
                if (districtList.get(0).equals("")){
                    candidateList.clear();
                    candidateAdapter.notifyDataSetChanged();
                } else {
                    FirebaseDistrictManager.getDbRef(Election.values()[nowElection].toString() + "/" + districtList.get(0)).addListenerForSingleValueEvent(candidateListener);
                }
            } else {
                districtList.addAll(getDistrictList(districtHistoryStack));
                Log.d("test", districtList.toString());
                districtAdapter.notifyDataSetChanged();
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
                Log.d("test",temp.toString());
            }

            if (history.size() == 3) {
                resultList =  new ArrayList<>();
                Log.d("test", "getDistrictList: " + Election.values()[nowElection]);
                resultList.add((String)temp.get(Election.values()[nowElection].toString()));
            }else {
                resultList =  new ArrayList<>(temp.keySet());
            }
        }
        Collections.sort(resultList);

        return resultList;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        districtList.clear();
        candidateList.clear();

        districtAdapter.notifyDataSetChanged();
        candidateAdapter.notifyDataSetChanged();

        recyclerView = null;
    }

    private class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {
        List<CandidateVO> mList;
        public CandidateAdapter(List<CandidateVO> list){
            mList = list;
        }

        @NonNull
        @Override
        public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate,viewGroup,false);
            return new CandidateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
            final CandidateVO vo = mList.get(position);

            holder.party.setText(vo.party);
            String color = PartyColor.getPartyColor(vo.party);
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.round_corner);

            if (color != null) {
                drawable.setColor(Color.parseColor(color));
            }else{
                drawable.setColor(Color.parseColor(PartyColor.getPartyColor("기본값")));
            }
            holder.party.setBackground(drawable);
            holder.itemView.setTag(vo);
            holder.name.setText(vo.name);
            holder.birth.setText(vo.birth);
            holder.gender.setText(vo.gender);
            holder.address.setText(vo.address);
            Glide.with(holder.itemView.getContext()).load(vo.imageUrl).into(holder.candidateImage);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v != null){
                        Log.d("test",v.getTag().toString());
                        Fragment detailFragment =  CandidateDetailFragment.getInstance();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("vo",vo);

                        detailFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.districtContainer,new CandidateDetailFragment()).commit();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class CandidateViewHolder extends RecyclerView.ViewHolder{
        //        후보Id
        public TextView id;
        //        이름
        public TextView name;
        //        사진
        public ImageView candidateImage;
        //        성별
        public TextView gender;
        //        정당
        public TextView party;
        //        선거구
        public TextView electionDistrict;
        //        생년월일
        public TextView birth;
        //        주소
        public TextView address;

        public TextView searchDistrict;

        public CandidateViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            gender = view.findViewById(R.id.gender);
            party = view.findViewById(R.id.party);
            birth = view.findViewById(R.id.birth);
            address = view.findViewById(R.id.address);
            candidateImage = view.findViewById(R.id.candidateImage);
            searchDistrict = view.findViewById(R.id.searchDistrict);
        }
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
                    if(v != null){

                        districtList.clear();
                        districtHistoryStack.push(((TextView)v.findViewById(R.id.districtName)).getText().toString());
                        districtList.addAll(getDistrictList(districtHistoryStack));
                        Log.d("test",districtHistoryStack.toString());
                        Log.d("test",districtList.toString());

                        nowDistrict = "";
                        for(String d : districtHistoryStack) {
                            if (nowDistrict == ""){
                                nowDistrict = d;
                            }else {
                                nowDistrict = nowDistrict + " > "+ d;
                            }
                        }

                        ((TextView)getActivity().findViewById(R.id.searchDistrict)).setText(nowDistrict);


                        if ( districtHistoryStack.size() < 3){

                            districtAdapter.notifyDataSetChanged();
                        } else {
                            recyclerView.setAdapter(candidateAdapter);
                            candidateList.clear();
                            candidateAdapter.notifyDataSetChanged();
                            FirebaseDistrictManager.getDbRef(Election.values()[nowElection].toString()+"/"+districtList.get(0)).addListenerForSingleValueEvent(candidateListener);

                        }

                    }
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
