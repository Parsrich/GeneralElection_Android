package lets.vote.generalelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromiseDetailFragment extends Fragment {
    private static PromiseDetailFragment instance;
    public PromiseDetailFragment() {
        // Required empty public constructor
    }

    public static PromiseDetailFragment getInstance(){
        if(instance == null){
            instance = new PromiseDetailFragment();
        }
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_promise_detail, container, false);
        ConstraintLayout layout = rootView.findViewById(R.id.promiseDetailBackground);
        TextView closeButton = rootView.findViewById(R.id.closeButton);
        TextView promiseDetailArea = rootView.findViewById(R.id.promiseDetailArea);
        TextView promiseDetailTitle = rootView.findViewById(R.id.promiseDetailTitle);
        TextView promiseDetailText = rootView.findViewById(R.id.promiseDetailText);
        ConstraintLayout promiseDetailBoard = rootView.findViewById(R.id.promiseDetailBoard);

        PromiseVO vo = (PromiseVO) getArguments().getSerializable("vo");
        promiseDetailArea.setText(("["+vo.getPrmsRealmName()+"]"));
        promiseDetailTitle.setText(vo.getPrmsTitle());
        promiseDetailText.setText(vo.getPrmmCont());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        promiseDetailBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}
