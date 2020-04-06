package lets.vote.generalelection;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private static SettingFragment instance;
    private static SettingAdapter adapter;
    private static RecyclerView recyclerView;
    public SettingFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if(instance == null) {
            instance = new SettingFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        recyclerView = rootView.findViewById(R.id.settingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> settingList = new ArrayList<>();
        settingList.add("후원하기");
        settingList.add("문의하기");
        settingList.add("오픈소스 라이선스");
        settingList.add("앱 버전: 1.0.0");
        adapter = new SettingAdapter(settingList);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return rootView;
    }

    class SettingAdapter extends RecyclerView.Adapter<SettingViewHolder> {
        List<String> mList;
        public SettingAdapter(List<String> list){
            mList = list;
        }

        @NonNull
        @Override
        public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting,parent,false);
            return new SettingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingViewHolder holder, final int position) {
            holder.settingItem.setText(mList.get(position));
            if(position == 3){
                holder.settingArrow.setVisibility(View.INVISIBLE);
            }else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                // 후원하기 페이지
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.defaultSettingContainer,DonationFragment.getInstance()).addToBackStack("setting").commit();
                                break;
                            case 1:
                                // 오류 문의
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                // email setting 배열로 해놔서 복수 발송 가능
                                String[] address = {"ohjooyeo.donam@gmail.com"};
                                email.putExtra(Intent.EXTRA_EMAIL, address);
                                email.putExtra(Intent.EXTRA_SUBJECT,"총선 오류 문의");
                                email.putExtra(Intent.EXTRA_TEXT,"1. 문의분류\n " +
                                        "정보 수정 요청, 기능오류, 제휴/광고문의, 기타문의\n" +
                                        "2. 문의 내용\n");
                                startActivity(email);
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), OssLicensesMenuActivity.class));
                                OssLicensesMenuActivity.setActivityTitle(getString(R.string.custom_license_title));
                                // 오픈소스 라이선스
                                break;
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
    class SettingViewHolder extends RecyclerView.ViewHolder{
        TextView settingItem;
        ImageView settingArrow;
        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            settingItem = itemView.findViewById(R.id.settingItem);
            settingArrow = itemView.findViewById(R.id.settingArrow);

        }
    }
}
