package lets.vote.generalelection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lets.vote.generalelection.admob.CandidateListAdManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProportionalListFragment extends Fragment {

    private final int AD_POSITION = 2;
    private static ProportionalListFragment instance;
    private RecyclerView recyclerView;
    private List<CandidateVO> candidateList ;
    private CandidateAdapter candidateAdapter;
    private Context mContext;


    public ProportionalListFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new ProportionalListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_proportional_list, container, false);

        /* 데이터 */

        TextView searchKeyword = rootView.findViewById(R.id.ProportionalName);
        LinearLayout noListLayout = rootView.findViewById(R.id.noList);
        String searchKeywordTitle = getArguments().getString("partyName").trim()+" " + mContext.getResources().getString(R.string.party_list) ;
        searchKeyword.setText(searchKeywordTitle);

        recyclerView = rootView.findViewById(R.id.PartyCandidateList);
        candidateList = new ArrayList<>();
        if(getArguments().getSerializable("list")!=null) {
            candidateList.addAll((List<CandidateVO>) getArguments().getSerializable("list"));
        }


        /* 후보자 Adapter */

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        candidateAdapter = new CandidateAdapter(candidateList);
        recyclerView.setAdapter(candidateAdapter);
        candidateAdapter.notifyDataSetChanged();
        if(candidateList.size() == 0){
            noListLayout.setVisibility(View.VISIBLE);
        }

        if (candidateList.size() > AD_POSITION) {
            candidateList.add(AD_POSITION, new CandidateVO());
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

    public void callAd(View view) {
        CandidateListAdManager.getInstance().initialize(getContext(), new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                ColorDrawable white = new ColorDrawable(Color.WHITE);
                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(white).build();

                TemplateView template = view.findViewById(R.id.adView);
                template.setStyles(styles);
                template.setNativeAd(unifiedNativeAd);

            }
        }, new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Handle the failure by logging, altering the UI, and so on.
                Log.d("Ad fail", "## onAdFailedToLoad: ");
            }
        }, new NativeAdOptions.Builder().build());

        CandidateListAdManager.getInstance().showAd();
    }

    private class CandidateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int VIEW_HEADER = 0;
        private final int VIEW_ITEM = 1;

        List<CandidateVO> mList;
        public CandidateAdapter(List<CandidateVO> list){
            mList = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            RecyclerView.ViewHolder holder;

            if (viewType == VIEW_HEADER) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate_ad,viewGroup,false);
                holder = new HeaderViewHolder(view);
            } else {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate,viewGroup,false);
                holder = new CandidateViewHolder(view);
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position == AD_POSITION) {
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;

                // 광고 초기화
                callAd(viewHolder.itemView);

                return;
            }

            CandidateViewHolder viewHolder = (CandidateViewHolder) holder;

            final CandidateVO vo = mList.get(position);

            viewHolder.party.setText(vo.party);
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
            viewHolder.party.setBackground(drawable);
            viewHolder.number.setBackground(numberDrawable);
            String numberText = "기호"+vo.number;
            if(vo.getSi() == null){
                numberText = "번호"+vo.getRecommend();
            }

            viewHolder.number.setText(numberText);
            viewHolder.itemView.setTag(vo);
            viewHolder.name.setText(vo.name);
            viewHolder.birth.setText(vo.birth);
            String genderText = "/"+vo.gender;
            viewHolder.gender.setText(genderText);
            viewHolder.address.setText(vo.address);
            Glide.with(viewHolder.itemView.getContext()).load(vo.imageUrl).into(viewHolder.candidateImage);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

        @Override
        public int getItemViewType(int position) {
            if (position == AD_POSITION)
                return VIEW_HEADER;
            else
                return VIEW_ITEM;
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

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View view) {
            super(view);
        }
    }
}
