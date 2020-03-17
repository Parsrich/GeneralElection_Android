package lets.vote.generalelection;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateDetailFragment extends Fragment {
    public CandidateDetailFragment() {
        // Required empty public constructor
    }

    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<CandidateInfoVO> list = new ArrayList<>();

        // 받아와야하는 데이터
        list.add(new CandidateInfoVO("직업", "정당인"));
        list.add(new CandidateInfoVO("학력", "서울대학교 법학대학 법학과 졸업"));
        list.add(new CandidateInfoVO("경력", "(전)제37대 전라남도지사\n(전)제45대 국무총리"));
        list.add(new CandidateInfoVO("전과기록\n유무(건수)", "없음"));


        View rootView = inflater.inflate(R.layout.fragment_candidate_detail, container, false);

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
