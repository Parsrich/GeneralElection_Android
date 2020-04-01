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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCandidateListFragment extends Fragment {
    private static SearchCandidateListFragment instance;
    private RecyclerView recyclerView;
    private List<CandidateVO> searchCandidateList ;
    private CandidateAdapter searchCandidateAdapter;
    private Context mContext;

    public SearchCandidateListFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new SearchCandidateListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_search_candidate_list, container, false);

        /* 데이터 */

        TextView searchKeyword = rootView.findViewById(R.id.searchKeyword);
        String searchKeywordTitle = "'" + getArguments().getString("searchKeyword").trim()+"'" + mContext.getResources().getString(R.string.search_keyword) ;
        searchKeyword.setText(searchKeywordTitle);

        recyclerView = rootView.findViewById(R.id.searchCandidateList);
        searchCandidateList = new ArrayList<>();
        searchCandidateList.addAll((List<CandidateVO>) getArguments().getSerializable("list"));


        /* 후보자 Adapter */

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        searchCandidateAdapter = new CandidateAdapter(searchCandidateList);
        recyclerView.setAdapter(searchCandidateAdapter);
        searchCandidateAdapter.notifyDataSetChanged();

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        searchCandidateList.clear();
        searchCandidateAdapter.notifyDataSetChanged();
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
            GradientDrawable numberDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.number_round_corner);

            if (color != null) {
                drawable.setColor(Color.parseColor(color));
                numberDrawable.setColor(Color.parseColor(color));
            }else{
                drawable.setColor(Color.parseColor(PartyColor.getPartyColor("기본값")));
                numberDrawable.setColor(Color.parseColor(PartyColor.getPartyColor("기본값")));
            }
            holder.party.setBackground(drawable);
            holder.number.setBackground(numberDrawable);
            String numberText = "기호"+vo.number;
            if(vo.getSi() == null){
                numberText = "번호"+vo.getRecommend();
            }

            holder.number.setText(numberText);
            holder.itemView.setTag(vo);
            holder.name.setText(vo.name);
            holder.birth.setText(vo.birth);
            String genderText = "/"+vo.gender;
            holder.gender.setText(genderText);
            holder.address.setText(vo.address);
            Glide.with(holder.itemView.getContext()).load(vo.imageUrl).into(holder.candidateImage);


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
        }
    }

}
