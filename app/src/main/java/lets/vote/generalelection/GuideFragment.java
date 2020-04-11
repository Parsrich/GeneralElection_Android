package lets.vote.generalelection;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aqoong.lib.slidephotoviewer.MaxSizeException;
import com.aqoong.lib.slidephotoviewer.SlidePhotoViewer;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {
    SlidePhotoViewer mSlideViewr;
    private static GuideFragment instance;
    private static boolean checkMode;
    public GuideFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (instance == null) {
            instance = new GuideFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        if (checkMode){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        ImageView image = rootView.findViewById(R.id.youtubeImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),YoutubeActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.youtubelogoImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),YoutubeActivity.class);
                startActivity(intent);
            }
        });

        mSlideViewr = rootView.findViewById(R.id.slideViewer);
        mSlideViewr.setSidePreview(false);
        try {
            mSlideViewr.addResource(R.drawable.img_vote_0, null);
            mSlideViewr.addResource(R.drawable.img_vote_1, null);
            mSlideViewr.addResource(R.drawable.img_vote_2, null);
            mSlideViewr.addResource(R.drawable.img_vote_3, null);
            mSlideViewr.addResource(R.drawable.img_vote_4, null);
            mSlideViewr.addResource(R.drawable.img_vote_5, null);
            mSlideViewr.addResource(R.drawable.img_vote_6, null);
            mSlideViewr.addResource(R.drawable.img_vote_7, null);
            mSlideViewr.addResource(R.drawable.img_vote_8, null);
            mSlideViewr.addResource(R.drawable.img_vote_9, null);
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }
     return rootView;
    }


}
