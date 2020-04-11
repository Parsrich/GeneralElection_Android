package lets.vote.generalelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultSettingFragment extends Fragment {
    private static DefaultSettingFragment instance;
    public DefaultSettingFragment() {
        // Required empty public constructor
    }

    public static DefaultSettingFragment getInstance(){
        if(instance == null){
            instance = new DefaultSettingFragment();
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_default_setting, container, false);
        Fragment settingFragment = new SettingFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.defaultSettingContainer,settingFragment).commit();


        return rootView;
    }
}
