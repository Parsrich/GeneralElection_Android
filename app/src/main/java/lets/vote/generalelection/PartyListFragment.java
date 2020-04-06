package lets.vote.generalelection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartyListFragment extends Fragment {
    private RecyclerView partyRecyclerView;
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

        List<PartyVO> partyNameList = new ArrayList<>();
        Map<String, String> logoMap = PartyInfo.getPartyLogoMap();
        Map<String, Integer> numberMap = PartyInfo.getPartyNumberMap();

        for (String name : logoMap.keySet()){
            partyNameList.add(new PartyVO(name, logoMap.get(name), numberMap.get(name)));
        }
        Collections.sort(partyNameList);

        partyRecyclerView = rootView.findViewById(R.id.partyList);
        partyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        partyRecyclerView.setAdapter(new PartyListAdapter(partyNameList));

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

            Glide.with(holder.itemView.getContext()).load(vo.getUrl()).into(holder.partyImage);

            if(vo.getName().equals("가자!평화인권당")){
                holder.partyImage.setBackgroundColor(Color.parseColor("#65A032"));
            }else{
                holder.partyImage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Fragment partyDetailFragment = PartyDetailFragment.getInstance();
                    String partyName = ((TextView)v.findViewById(R.id.partyName)).getText().toString().trim();
                    Bundle bundle = new Bundle();
                    bundle.putString("partName",partyName);
                    partyDetailFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,partyDetailFragment).addToBackStack(null).commit();
                }
            });


//            holder.itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    CandidateDBHelper helper = new CandidateDBHelper(getContext());
//                    SQLiteDatabase db=helper.getReadableDatabase();
//                    String sql = "SELECT * FROM "+CandidateContract.CandidateEntry.TABLE_NAME
//                            +" WHERE party ='"+ ((TextView)v.findViewById(R.id.partyName)).getText().toString().trim()
//                            +"' AND recommend is NOT NULL";
//                    Cursor cursor=db.rawQuery(sql,null);
//                    Fragment proportionalListFragment = ProportionalListFragment.getInstance();
//                    Bundle bundle = new Bundle();
//
//                    Log.d("test", "onClick: " + cursor.getCount());
//
//                    if(cursor.getCount() > 0){
//                        List<CandidateVO> voList = new ArrayList<>();
//                        while (cursor.moveToNext()){
//                            voList.add(new CandidateVO(cursor));
//                        }
//                        Collections.sort(voList,new Comparator<CandidateVO>(){
//                            @Override
//                            public int compare(CandidateVO c1, CandidateVO c2) {
//                                if (Integer.parseInt(c1.recommend) < Integer.parseInt(c2.getRecommend())) {
//                                    return -1;
//                                } else if (Integer.parseInt(c1.recommend) > Integer.parseInt(c2.getRecommend())) {
//                                    return 1;
//                                }
//                                return 0;
//                            }
//
//                        } );
//                        bundle.putSerializable("list", (Serializable) voList);
//                    }
//                    bundle.putString("partyName", ((TextView)v.findViewById(R.id.partyName)).getText().toString());
//
//                    proportionalListFragment.setArguments(bundle);
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,proportionalListFragment).addToBackStack(null).commit();
//
//                }
//            });

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
