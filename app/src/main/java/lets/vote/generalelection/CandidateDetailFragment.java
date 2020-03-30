package lets.vote.generalelection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateDetailFragment extends Fragment {
    private static CandidateDetailFragment instance;
    private static Context mContext;
    private static CandidateVO candidateVO;

    public CandidateDetailFragment() {
        // Required empty public constructor
    }


    public static Fragment getInstance() {
        if (instance == null) {
            instance = new CandidateDetailFragment();
        }
        return instance;
    }

    public CandidateVO getCandidateVO() {
        return candidateVO;
    }

    public void setCandidateVO(CandidateVO candidateVO) {
        this.candidateVO = candidateVO;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        candidateVO = (CandidateVO) args.getSerializable("vo");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_candidate_detail, container, false);

        TextView party = rootView.findViewById(R.id.candidateDetailParty);
        TextView name = rootView.findViewById(R.id.candidateDetailName);
        TextView birth = rootView.findViewById(R.id.candidateDetailBirth);
        TextView gender = rootView.findViewById(R.id.candidateDetailGender);
        TextView number = rootView.findViewById(R.id.candidateDetailNumber);
        TextView address = rootView.findViewById(R.id.candidateDetailAddress);
        ImageView candidateImage = rootView.findViewById(R.id.candidateDetailImage);
        TextView district = rootView.findViewById(R.id.candidateDetailDistrict);

        String districtText = candidateVO.getSi() +">" + candidateVO.getDistrict();
        district.setText(districtText);

        String numberText = "기호"+candidateVO.getNumber();
        number.setText(numberText);
        party.setText(candidateVO.getParty());

        String color = PartyColor.getPartyColor(candidateVO.getParty());
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.round_corner);
        if (color != null) {
            drawable.setColor(Color.parseColor(color));
        }else{
            drawable.setColor(Color.parseColor(PartyColor.getPartyColor("기본값")));
        }
        number.setBackground(drawable);
        party.setBackground(drawable);

        name.setText(candidateVO.getName());
        birth.setText(candidateVO.getBirth());
        address.setText(candidateVO.getAddress());

        String genderText = "/"+candidateVO.getGender();
        gender.setText(genderText);
        Glide.with(rootView.getContext()).load(candidateVO.imageUrl).into(candidateImage);

        List<CandidateInfoVO> list = new ArrayList<>();

        // 받아와야하는 데이터
        list.add(new CandidateInfoVO("직업", candidateVO.getJob() ));
        list.add(new CandidateInfoVO("학력", candidateVO.getEducation()));
        list.add(new CandidateInfoVO("경력", candidateVO.getCareer() ));
        list.add(new CandidateInfoVO("전과기록\n유무(건수)", candidateVO.getCriminal() ));
        list.add(new CandidateInfoVO("병역\n신고사항(본인)", candidateVO.getMilitary() ));
        list.add(new CandidateInfoVO("재산\n신고액(천원)", candidateVO.getProperty() ));
        list.add(new CandidateInfoVO("입후보횟수", candidateVO.getRegCount()));
        list.add(new CandidateInfoVO("세금납부액", candidateVO.getTaxPayment() ));
        list.add(new CandidateInfoVO("최근5년간\n체납액", candidateVO.getTaxArrears() ));
        list.add(new CandidateInfoVO("현 체납액", candidateVO.getTaxArrears5()));



        ImageView imageView = rootView.findViewById(R.id.candidateDetailImage);

        RecyclerView recyclerView = rootView.findViewById(R.id.candidateDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CandidateAdapter(list));
        return rootView;
    }

    private class CandidateAdapter extends RecyclerView.Adapter<CandidateDetailViewHolder>{
        List<CandidateInfoVO> list ;
        public CandidateAdapter(List<CandidateInfoVO> list){
            this.list = list;
        }

        @Override
        public CandidateDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate_detail,viewGroup,false);
            return new CandidateDetailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandidateDetailViewHolder holder, int position) {
            CandidateInfoVO vo = list.get(position);
            holder.candidateInfoTitle.setText(vo.candidateInfoTitle);
            holder.candidateInfoContent.setText(vo.candidateInfoContent);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class CandidateDetailViewHolder extends RecyclerView.ViewHolder{
        public TextView candidateInfoTitle;
        public TextView candidateInfoContent;

        public CandidateDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            candidateInfoTitle = itemView.findViewById(R.id.candidateInfoTitle);
            candidateInfoContent = itemView.findViewById(R.id.candidateInfoContent);
        }


    }
}
