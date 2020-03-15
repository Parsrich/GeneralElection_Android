package lets.vote.generalelection;

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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateListFragment extends Fragment {

    public CandidateListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_candidate_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.candidateList);

        List<CandidateVO> list = new ArrayList<>();

        for (int i = 0 ; i < 20 ; i++){
            CandidateVO temp = new CandidateVO();
            temp.name = "이낙연 ";
            temp.party = "더불어민주당" + i;
            temp.birth = "1952.12.20(67세)";
            temp.address = "서울특별시 종로구 송월길 ";
            temp.gender = "/남";

            list.add(temp);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CandidateAdapter(list));

        return rootView;
    }

    private class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {
        List<CandidateVO> mlist;
        public CandidateAdapter(List<CandidateVO> list){
            mlist = list;
        }

        @NonNull
        @Override
        public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate,viewGroup,false);
            return new CandidateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
            CandidateVO vo = mlist.get(position);
            holder.party.setText(vo.party);
            holder.name.setText(vo.name);
            holder.birth.setText(vo.birth);
            holder.gender.setText(vo.gender);
            holder.address.setText(vo.address);
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }

    private class CandidateViewHolder extends RecyclerView.ViewHolder{
        //        이름
        public TextView name;
        //        사진
        public ImageView image;
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

        public CandidateViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            gender = view.findViewById(R.id.gender);
            party = view.findViewById(R.id.party);
            birth = view.findViewById(R.id.birth);
            address = view.findViewById(R.id.address);

        }
    }
}
