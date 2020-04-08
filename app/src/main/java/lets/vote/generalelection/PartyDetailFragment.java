package lets.vote.generalelection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartyDetailFragment extends Fragment {
    private static PartyDetailFragment instance;
    private static List<PromiseVO> list;
    private PromiseAdapter promiseAdapter;
    private ProgressBar progressBar;
    private LinearLayout noListLayout;
    private ValueEventListener promiseListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String,String> value = (Map<String,String>) dataSnapshot.getValue();
            progressBar.setVisibility(View.VISIBLE);

            if (value != null) {

                for (int i = 1; i <= Integer.parseInt(value.get("prmsCnt")); i++) {
                    // 순서
                    String prmsOrd = "prmsOrd" + i;
                    if (prmsOrd != null) {

                        //분야
                        String prmsRealmName = "prmsRealmName" + i;
                        // title
                        String prmsTitle = "prmsTitle" + i;
                        //내용
                        String prmmCont = "prmmCont" + i;

//                        Log.d("test", value.get(prmsOrd) +
//                                value.get(prmsRealmName) +
//                                value.get(prmsTitle) +
//                                value.get(prmmCont));

                        list.add(new PromiseVO(value.get(prmsOrd),
                                value.get(prmsRealmName),
                                value.get(prmsTitle),
                                value.get(prmmCont)));
                    }
                }
            }

            if(list.size() == 0){
                noListLayout.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.GONE);
            promiseAdapter.notifyDataSetChanged();
//            Log.d("test", "onDataChange: "+list);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public PartyDetailFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new PartyDetailFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_party_detail, container, false);
        TextView partyDetailName = rootView.findViewById(R.id.partyDetailName);
        TextView promiseTitle = rootView.findViewById(R.id.promiseTitle);
        TextView partyDetailNumber = rootView.findViewById(R.id.partyDetailNumber);
        ImageView partyLogo = rootView.findViewById(R.id.partyLogo);


        Button proportionalListButton = rootView.findViewById(R.id.proportionalList);
        Button partyIssueButton = rootView.findViewById(R.id.partyIssue);

        RecyclerView promiseRecyclerView = rootView.findViewById(R.id.promiseRecyclerView);
        progressBar = rootView.findViewById(R.id.districtProgress);
        progressBar.setVisibility(View.VISIBLE);

        noListLayout = rootView.findViewById(R.id.noList);

        final String partyName = getArguments().getString("partName");
        Integer proportional = PartyInfo.getProportionalMap().get(partyName);

        final int partyNumber = getArguments().getInt("partyNumber");

        int drawableId = getResources().getIdentifier( "party"+partyNumber, "drawable", getContext().getPackageName());

        Glide
                .with(getContext())
                .load(drawableId)
                .into(partyLogo);


        partyDetailName.setText(partyName);
        promiseTitle.setText((partyName+" 공약"));


        if (!proportional.toString().equals("0")){
            partyDetailNumber.setText("비례정당번호"+proportional.toString());
        }



        CandidateDBHelper helper = CandidateDBHelper.getInstance(getContext());
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql = "SELECT COUNT(*) AS COUNT FROM "+CandidateContract.CandidateEntry.TABLE_NAME
                +" WHERE party ='"+ partyName
                +"' AND recommend is NOT NULL";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToNext();

        if (cursor.getString(0).equals("0")){
            proportionalListButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
            proportionalListButton.setBackground(getResources().getDrawable(R.drawable.color_button_deactivation));
        } else {
            proportionalListButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    CandidateDBHelper helper = CandidateDBHelper.getInstance(getContext());
                    SQLiteDatabase db=helper.getReadableDatabase();
                    String sql = "SELECT * FROM "+CandidateContract.CandidateEntry.TABLE_NAME
                            +" WHERE party ='"+ partyName
                            +"' AND recommend is NOT NULL";
                    Cursor cursor=db.rawQuery(sql,null);
                    Fragment proportionalListFragment = ProportionalListFragment.getInstance();
                    Bundle bundle = new Bundle();


                    if(cursor.getCount() > 0){
                        List<CandidateVO> voList = new ArrayList<>();
                        while (cursor.moveToNext()){
                            voList.add(new CandidateVO(cursor));
                        }
                        Collections.sort(voList,new Comparator<CandidateVO>(){
                            @Override
                            public int compare(CandidateVO c1, CandidateVO c2) {
                                if (Integer.parseInt(c1.recommend) < Integer.parseInt(c2.getRecommend())) {
                                    return -1;
                                } else if (Integer.parseInt(c1.recommend) > Integer.parseInt(c2.getRecommend())) {
                                    return 1;
                                }
                                return 0;
                            }

                        } );
                        bundle.putSerializable("list", (Serializable) voList);
                    }
                    bundle.putString("partyName", partyName);

                    proportionalListFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,proportionalListFragment).addToBackStack(null).commit();

                }
            });

        }

        partyIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment partyWebInfoFragment = PartyWebInfoFragment.getInstance();
                final Bundle bundle = new Bundle();
                bundle.putString("keyword", partyName);
                ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
                bundle.putInt("top",viewPager.getPaddingTop());
                bundle.putInt("left",viewPager.getPaddingLeft());
                bundle.putInt("right",viewPager.getPaddingRight());
                bundle.putInt("bottom",viewPager.getPaddingBottom());
                partyWebInfoFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,partyWebInfoFragment).addToBackStack(null).commit();
            }
        });

        list = new ArrayList<>();
        String path = "partyPromise/"+partyName;
        FirebaseDistrictManager.getDbRef(path).addListenerForSingleValueEvent(promiseListener);
        promiseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        promiseAdapter = new PromiseAdapter(list);

        promiseRecyclerView.setAdapter(promiseAdapter);

        return rootView;
    }


    class PromiseAdapter extends RecyclerView.Adapter<PromiseViewHolder> {
        List<PromiseVO> list;
        public PromiseAdapter(List<PromiseVO> list ) {
            this.list = list;
        }

        @NonNull
        @Override
        public PromiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party_detail,parent,false);
            return new PromiseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PromiseViewHolder holder, final int position) {
            holder.promiseNumber.setText(list.get(position).prmsOrd);
            holder.promiseContent.setText("["+list.get(position).prmsRealmName+"]\n"+list.get(position).prmsTitle);
            if (position % 2 == 1 ){
                holder.contentBackground.setBackground(getResources().getDrawable(R.color.candidateInfoBackgroundColor));
            }
            holder.promiseDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment promiseDetailFragment = PromiseDetailFragment.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("vo", list.get(position));
                    promiseDetailFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainContainer,promiseDetailFragment).addToBackStack(null).commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    class PromiseViewHolder extends RecyclerView.ViewHolder{
        TextView promiseNumber;
        TextView promiseContent;
        ImageView promiseDetail;
        ConstraintLayout contentBackground;

        public PromiseViewHolder(@NonNull View itemView) {
            super(itemView);
            promiseContent = itemView.findViewById(R.id.promiseContent);
            promiseNumber = itemView.findViewById(R.id.promiseNumber);
            promiseDetail = itemView.findViewById(R.id.detailIcon);
            contentBackground = itemView.findViewById(R.id.contentBackground);
        }
    }

}
