package lets.vote.generalelection.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import static lets.vote.generalelection.BuildConfig.CANDIDATE_SEARCH_AD_ID;

public class CandidateSearchAdManager {

    private AdLoader adLoader;
    private static CandidateSearchAdManager instance = null;
    public static CandidateSearchAdManager getInstance() {
        if (instance == null) {
            instance = new CandidateSearchAdManager();
        }
        return instance;
    }

    public CandidateSearchAdManager() {

    }

    public void initialize(Context context,
                           UnifiedNativeAd.OnUnifiedNativeAdLoadedListener unifiedNativeAdListener,
                           AdListener adListener,
                           NativeAdOptions options) {
        if (adLoader == null) {
            adLoader = new AdLoader.Builder(context, CANDIDATE_SEARCH_AD_ID)
                    .forUnifiedNativeAd(unifiedNativeAdListener)
                    .withAdListener(adListener)
                    .withNativeAdOptions(options)
                    .build();
        }
    }

    public void showAd() {
        adLoader.loadAd(new AdRequest.Builder().build());
    }
}
