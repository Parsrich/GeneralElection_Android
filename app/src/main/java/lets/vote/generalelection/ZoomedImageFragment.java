package lets.vote.generalelection;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZoomedImageFragment extends Fragment {

    public ZoomedImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_zoomed_image, container, false);
        final ImageView zoomedImage = rootView.findViewById(R.id.zoomedImage);
        final ConstraintLayout zoomedImageLayout  = rootView.findViewById(R.id.zoomedImageLayout);

        CandidateVO vo = (CandidateVO) getArguments().getSerializable("vo");
        Glide.with(rootView.getContext()).load(vo.imageUrl).into(zoomedImage);
        zoomedImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomedImageLayout.setVisibility(View.GONE);
//                viewPager.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
//                viewPager.requestLayout();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        return rootView;
    }
}
