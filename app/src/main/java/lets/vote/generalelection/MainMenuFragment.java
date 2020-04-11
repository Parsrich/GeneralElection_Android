package lets.vote.generalelection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import lets.vote.generalelection.admob.FullScreenAdManager;


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
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        FullScreenAdManager adInstance = FullScreenAdManager.getInstance(getContext());
        adInstance.initialize(getContext(), new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        ConstraintLayout guideBtn = rootView.findViewById(R.id.mainGuideBtn);
        ConstraintLayout partyBtn = rootView.findViewById(R.id.mainPartyBtn);
        ConstraintLayout districtBtn = rootView.findViewById(R.id.mainDistrictBtn);
        ConstraintLayout candidateBtn = rootView.findViewById(R.id.mainCandidateBtn);

        final Fragment districtFragment = DistrictListFragment.getInstance();
        districtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adInstance.showAd(getContext());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContainer, districtFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        final Fragment partyListFragment = PartyListFragment.getInstance();
        partyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkChecker.setup(getContext());
                if (!NetworkChecker.checkOn()){
                    NetworkChecker.alert(getContext(), "확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }).show();
                } else {
                    adInstance.showAd(getContext());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainContainer, partyListFragment)
                            .addToBackStack(null)
                            .commit();
            }}
        });

        final Fragment searchCandidateFragment = SearchCandidateFragment.getInstance();
        candidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adInstance.showAd(getContext());
                NetworkChecker.setup(getContext());
                if (!NetworkChecker.checkOn()){
                    NetworkChecker.alert(getContext(), "확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }).show();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainContainer, searchCandidateFragment)
                            .addToBackStack(null)
                            .commit();
                }
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

//    @Override
//    public void onBack() {
//        MainActivity activity = (MainActivity) getActivity();
//        activity.setOnKeyBackPressedListener();
//        activity.onBackPressed();
//    }
}
