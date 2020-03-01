package lets.vote.generalelection;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CandidateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        RecyclerView recyclerView = findViewById(R.id.candidate_list);

        List<CandidateVO> list = new ArrayList<>();

        for (int i = 0 ; i < 20 ; i++){
            CandidateVO temp = new CandidateVO();
            temp.name = "김총선 " + i;
            temp.party = "행복한 우리당 " + i;
            temp.birth = "1992.12.12(29세)";
            temp.address = "서울특별시 종로구 송월길 ";
            temp.gender = "/남";
            temp.job = "정당인";

            list.add(temp);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CandidateAdapter(list));
    }

    private class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder>{
        List<CandidateVO> list ;
        public CandidateAdapter(List<CandidateVO> list){
            this.list = list;
        }

        @Override
        public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_candidate,viewGroup,false);
            return new CandidateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CandidateViewHolder candidateViewHolder, int i) {
            CandidateVO vo = list.get(i);
            candidateViewHolder.party.setText(vo.party);
            candidateViewHolder.name.setText(vo.name);
            candidateViewHolder.birth.setText(vo.birth);
            candidateViewHolder.gender.setText(vo.gender);
            candidateViewHolder.address.setText(vo.address);
            candidateViewHolder.job.setText(vo.job);
        }

        @Override
        public int getItemCount() {
            return list.size();
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
//        직업
        public TextView job;

        public CandidateViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            gender = view.findViewById(R.id.gender);
            party = view.findViewById(R.id.party);
            birth = view.findViewById(R.id.birth);
            address = view.findViewById(R.id.address);
            job = view.findViewById(R.id.job);

        }

    }
}
