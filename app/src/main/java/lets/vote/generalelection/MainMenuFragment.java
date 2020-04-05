package lets.vote.generalelection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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
        Log.d("test", "onCreateView");
        View rootView =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        ImageView guideBtn = rootView.findViewById(R.id.mainGuideBtn);
        ImageView partyBtn = rootView.findViewById(R.id.mainPartyBtn);
        ImageView districtBtn = rootView.findViewById(R.id.mainDistrictBtn);
        ImageView candidateBtn = rootView.findViewById(R.id.mainCandidateBtn);
        final Fragment districtFragment = DistrictListFragment.getInstance();
        districtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,districtFragment).addToBackStack(null).commit();
            }
        });


        final Fragment partyListFragment = PartyListFragment.getInstance();
        partyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,partyListFragment).addToBackStack(null).commit();
            }
        });

        final Fragment searchCandidateFragment = SearchCandidateFragment.getInstance();
        candidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,searchCandidateFragment).addToBackStack(null).commit();
            }
        });

        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewPager)getActivity().findViewById(R.id.viewPager)).setCurrentItem(0);
            }
        });



        return rootView;
    }
}
