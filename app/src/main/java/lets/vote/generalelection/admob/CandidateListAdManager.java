package lets.vote.generalelection.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;

public class CandidateListAdManager implements AdMobImpl {

    String adId = "";

    private static CandidateListAdManager instance = null;
    public static CandidateListAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new CandidateListAdManager(context);
        }
        return instance;
    }

    CandidateListAdManager(Context context) {

//        mInterstitialAd = new InterstitialAd(context);
//        mInterstitialAd.setAdUnitId(CANDIDATE_LIST_AD_ID);
    }

    @Override
    public void initialize(Context context, AdListener adListener) {

    }

    @Override
    public void showAd(Context context) {

    }
}
