package lets.vote.generalelection;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateListFragment extends Fragment {
    private static CandidateListFragment instance;
    private RecyclerView recyclerView;
    private List<CandidateVO> candidateList ;
    private CandidateAdapter candidateAdapter;
    private Context mContext;
    private String nowDistrict = "";
    private Map<String,Object> districtMap;

    private Stack<String> stack = new Stack<>();
    private int nowElection;
    private ProgressBar progressBar;
    private LinearLayout noListLayout;

    private ValueEventListener candidateListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            progressBar.setVisibility(View.VISIBLE);
            ArrayList<Object> value = (ArrayList<Object>) dataSnapshot.getValue();
            candidateList.clear();
            for( Object candidate :value){
                candidateList.add(new CandidateVO((Map<String,Object>)candidate));
            }
            Collections.sort(candidateList);
            progressBar.setVisibility(View.GONE);
            candidateAdapter.notifyDataSetChanged();
            if(candidateList.size() == 0){
                noListLayout.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public CandidateListFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new CandidateListFragment();
        }
        return instance;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        districtMap = (Map<String, Object>) args.getSerializable("districtMap");
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
        NetworkChecker.setup(getContext());
        if (!NetworkChecker.checkOn()){
            NetworkChecker.alert(getContext(), "확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }).show();
        }else {

            nowElection = 0;
            stack = (Stack<String>) getArguments().get("stack");

            //해당지역의 선거 종류 Map
            Map<String, Object> electionMap = (Map<String, Object>) getArguments().get("districtMap");
            for (int i = 0; i < stack.size(); i++) {
                electionMap = (Map<String, Object>) electionMap.get(stack.get(i));
            }

            //대상선거 리스트에 담기
            List<String> selectList = new ArrayList<>();
            for (String k : electionMap.keySet()) {
                if (!electionMap.get(k).equals("")) {
                    selectList.add(Election.valueOf(k).getName());
                }
            }


            progressBar = rootView.findViewById(R.id.districtProgress);
            noListLayout = rootView.findViewById(R.id.noList);
            TextView searchDistrict = rootView.findViewById(R.id.searchDistrict);
            progressBar.setVisibility(View.VISIBLE);

            // 현재 지역구
            searchDistrict.setText(stack.get(0) + " " + electionMap.get(Election.values()[nowElection].toString()).toString());

            /* 선거 선택 */
            Spinner spinner = rootView.findViewById(R.id.electionSpinner);
            final ArrayAdapter<String> selectAdapter
                    = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, selectList) {
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
            spinner.setSelection(nowElection);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        nowElection = position;

                        candidateList.clear();
                        candidateAdapter.notifyDataSetChanged();
                        String path = Election.values()[nowElection].toString() + "/" + stack.get(0) + "_" + getDistrictList(stack).get(0);

                        FirebaseDistrictManager.getDbRef(path).addListenerForSingleValueEvent(candidateListener);
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

            recyclerView.setAdapter(candidateAdapter);


            String path = Election.values()[nowElection].toString() + "/" + stack.get(0) + "_" + getDistrictList(stack).get(0);
            FirebaseDistrictManager.getDbRef(path).addListenerForSingleValueEvent(candidateListener);
        }
        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        candidateList.clear();

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
            String color = PartyInfo.getPartyColor(vo.party);
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.round_corner);
            GradientDrawable numberDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.number_round_corner);

            if (color != null) {
                drawable.setColor(Color.parseColor(color));
                numberDrawable.setColor(Color.parseColor(color));
            }else{
                drawable.setColor(Color.parseColor(PartyInfo.getPartyColor("기본값")));
                numberDrawable.setColor(Color.parseColor(PartyInfo.getPartyColor("기본값")));
            }
            holder.party.setBackground(drawable);
            holder.number.setBackground(numberDrawable);
            String numberText = "기호"+vo.number;
            holder.number.setText(numberText);
            holder.itemView.setTag(vo);
            holder.name.setText(vo.name);
            holder.birth.setText(vo.birth);
            String genderText = "/"+vo.gender;
            holder.gender.setText(genderText);
            holder.address.setText(vo.address);
            Glide.with(holder.itemView.getContext()).load(vo.imageUrl).into(holder.candidateImage);

            if(vo.status.equals("resign")){
                holder.resignLayout.setVisibility(View.VISIBLE);
            }else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v != null){
                            Fragment detailFragment =  new CandidateDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("vo",vo);

                            detailFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,detailFragment).addToBackStack(null).commit();
                        }
                    }
                });
            }



        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    public List<String> getDistrictList( Stack<String> history){
        List<String> resultList = new ArrayList<>();
        Map<String,Object> temp = districtMap;

        if (history != null && history.size() < 4){

            for(int i = 0; i < history.size() ; i++) {
                temp = (Map<String, Object>) temp.get(history.get(i));
            }

            if (history.size() == 3) {
                resultList =  new ArrayList<>();
                resultList.add((String)temp.get(Election.values()[nowElection].toString()));
            }else {
                resultList =  new ArrayList<>(temp.keySet());
            }
        }
        Collections.sort(resultList);

        return resultList;
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
        //        기호
        public TextView number;
        //        정당
        public TextView party;
        //        선거구
        public TextView electionDistrict;
        //        생년월일
        public TextView birth;
        //        주소
        public TextView address;

        public TextView searchDistrict;

        public ConstraintLayout resignLayout;

        public CandidateViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            gender = view.findViewById(R.id.gender);
            party = view.findViewById(R.id.party);
            number = view.findViewById(R.id.number);
            birth = view.findViewById(R.id.birth);
            address = view.findViewById(R.id.address);
            candidateImage = view.findViewById(R.id.candidateImage);
            searchDistrict = view.findViewById(R.id.searchDistrict);
            resignLayout = view.findViewById(R.id.resign);
        }
    }

}
