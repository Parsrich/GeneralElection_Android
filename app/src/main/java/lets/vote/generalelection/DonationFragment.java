package lets.vote.generalelection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationFragment extends Fragment {
    private static DonationFragment instance;
    public DonationFragment() {
        // Required empty public constructor
    }
    public static DonationFragment getInstance(){
        if (instance == null) {
            instance = new DonationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_donation, container, false);


        return rootView;
    }
}
