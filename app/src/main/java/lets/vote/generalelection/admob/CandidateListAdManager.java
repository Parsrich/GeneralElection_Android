package lets.vote.generalelection.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import static lets.vote.generalelection.BuildConfig.CANDIDATE_LIST_AD_ID;

public class CandidateListAdManager {

    private AdLoader adLoader;

    private static CandidateListAdManager instance = null;
    public static CandidateListAdManager getInstance() {
        if (instance == null) {
            instance = new CandidateListAdManager();
        }
        return instance;
    }

    CandidateListAdManager() {

    }

    public void initialize(Context context,
                           UnifiedNativeAd.OnUnifiedNativeAdLoadedListener unifiedNativeAdListener,
                           AdListener adListener,
                           NativeAdOptions options) {
        if (adLoader == null) {
            adLoader = new AdLoader.Builder(context, CANDIDATE_LIST_AD_ID)
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
