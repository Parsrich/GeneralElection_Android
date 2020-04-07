package lets.vote.generalelection.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;

public class CandidateSearchAdManager implements AdMobImpl {
    private static CandidateSearchAdManager instance = null;
    public static CandidateSearchAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new CandidateSearchAdManager(context);
        }
        return instance;
    }

    public CandidateSearchAdManager(Context context) {
//        mInterstitialAd = new InterstitialAd(context);
//        mInterstitialAd.setAdUnitId(CANDIDATE_SEARCH_AD_ID);

    }

    @Override
    public void initialize(Context context, AdListener adListener) {

    }

    @Override
    public void showAd(Context context) {

    }
}
