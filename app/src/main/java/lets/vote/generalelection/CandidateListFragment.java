package lets.vote.generalelection;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateListFragment extends Fragment {
    private List<CandidateVO> list ;
    private CandidateAdapter adapter;
    private int totalCount;
    private Context mContext;
    public CandidateListFragment() {
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
        RecyclerView recyclerView = rootView.findViewById(R.id.candidateList);
        Spinner spinner = rootView.findViewById(R.id.electionSpinner);

        List<String> selectList = new ArrayList<>();
        selectList.add("국회의원선거");
        selectList.add("구·시·군의 장선거");
        selectList.add("시·도의회의원선거");
        selectList.add("구·시·군의회의원선거");

        ArrayAdapter<String> selectAdapter
                = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, selectList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Typeface externalFont= ResourcesCompat.getFont(mContext, R.font.gmarket_sans_medium);

                ((TextView) view).setTypeface(externalFont);
                ((TextView) view).setTextSize(17);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                Typeface externalFont= ResourcesCompat.getFont(mContext, R.font.gmarket_sans_medium);

                ((TextView) view).setTypeface(externalFont);
                ((TextView) view).setTextSize(17);
                return view;
            }

        };

        selectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(selectAdapter);


        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CandidateAdapter(list);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("candidate")
                .whereEqualTo("Si","서울특별시")
                .whereEqualTo("Gu","종로구")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            totalCount = task.getResult().size();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("list_test",new CandidateVO(document).toString());
                                list.add(new CandidateVO(document));
                            }
                            adapter.notifyDataSetChanged();
                            Log.d("result",list.toString());
                        } else {
                            Log.w("DB_TEST", "Error getting documents.", task.getException());
                        }
                    }
                });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener (){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                Log.d("position",lastPosition + "");
                if (lastPosition == list.size()-1) {
                    Log.d("position","마지막 값");
                }
            }
        });

        return rootView;
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
            CandidateVO vo = mList.get(position);
            holder.party.setText(vo.party);
            holder.name.setText(vo.name);
            holder.birth.setText(vo.birth);
            holder.gender.setText(vo.gender);
            holder.address.setText(vo.address);
//            Log.d("imgUrl",vo.imageUrl);
//            Log.d("imgUrl",holder.itemView.getContext().toString());
//            Log.d("imgUrl",holder.image.toString());
            Glide.with(holder.itemView.getContext()).load(vo.imageUrl).into(holder.candidateImage);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class CandidateViewHolder extends RecyclerView.ViewHolder{
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

        public CandidateViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            gender = view.findViewById(R.id.gender);
            party = view.findViewById(R.id.party);
            birth = view.findViewById(R.id.birth);
            address = view.findViewById(R.id.address);
            candidateImage = view.findViewById(R.id.candidateImage);

        }
    }
}
