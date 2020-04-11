package lets.vote.generalelection;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static MainFragment instance;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance(){
        if(instance == null){
            instance = new MainFragment();
        }
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, MainMenuFragment.getInstance())
                .commit();

        return rootView;
    }
}
