package lets.vote.generalelection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
public class PartyListFragment extends Fragment {
    RecyclerView partyRecyclerView;
    private static PartyListFragment instance;

    public PartyListFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new PartyListFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_party_list, container, false);

        List<PartyVO> partyVOList = new ArrayList<>();
        PartyVO tempVO1 = new PartyVO();
        tempVO1.setName("더불어민주당");
        tempVO1.setImageRes(R.drawable.party1);
        partyVOList.add(tempVO1);

        PartyVO tempVO2 = new PartyVO();
        tempVO2.setName("미래통합당");
        tempVO2.setImageRes(R.drawable.party2);
        partyVOList.add(tempVO2);

        PartyVO tempVO3 = new PartyVO();
        tempVO3.setName("미래한국당");
        tempVO3.setImageRes(R.drawable.party3);
        partyVOList.add(tempVO3);

        partyRecyclerView = rootView.findViewById(R.id.partyList);
        partyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        partyRecyclerView.setAdapter(new PartyListAdapter(partyVOList));

        return rootView;
    }

    public class PartyListAdapter extends RecyclerView.Adapter<PartyViewHolder> {
        List<PartyVO> mList;
        public PartyListAdapter(List<PartyVO> list){
            mList = list;
        }


        @NonNull
        @Override
        public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party,parent,false);
            return new PartyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
            PartyVO vo = mList.get(position);
            holder.partyName.setText(vo.getName());
            holder.partyImage.setImageResource(vo.imageRes);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder{
        TextView partyName;
        ImageView partyImage;
        public PartyViewHolder(View view) {
            super(view);
            partyName = view.findViewById(R.id.partyName);
            partyImage = view.findViewById(R.id.partyImage);

        }

    }
}
