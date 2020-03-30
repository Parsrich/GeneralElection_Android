package lets.vote.generalelection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    private static MainMenuFragment instance;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    public static MainMenuFragment getInstance() {
        if(instance == null) {
            instance = new MainMenuFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        ImageView guideBtn = rootView.findViewById(R.id.mainGuideBtn);
        ImageView partyBtn = rootView.findViewById(R.id.mainPartyBtn);
        ImageView districtBtn = rootView.findViewById(R.id.mainDistrictBtn);
        ImageView candidateBtn = rootView.findViewById(R.id.mainCandidateBtn);
        final Fragment districtFragment = new DistrictListFragment();
        districtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,districtFragment).addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}
